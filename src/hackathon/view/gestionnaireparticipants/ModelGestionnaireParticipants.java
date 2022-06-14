package hackathon.view.gestionnaireparticipants;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import hackathon.commun.IMapper;
import hackathon.dao.DaoAdmin;
import hackathon.dao.DaoEquipe;
import hackathon.dao.DaoIntervenir;
import hackathon.dao.DaoParticipant;
import hackathon.dao.DaoSuperUser;
import hackathon.data.Admin;
import hackathon.data.Compte;
import hackathon.data.Equipe;
import hackathon.data.Hackathon;
import hackathon.data.Participant;
import hackathon.data.Personne;
import hackathon.data.SuperUser;
import hackathon.view.systeme.ModelConnexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jfox.exception.ExceptionValidation;

public class ModelGestionnaireParticipants {

	// Données observables
	private final ObservableList<Equipe> lvEquipe = FXCollections.observableArrayList();
	private final ObservableList<Participant> lvParticipant = FXCollections.observableArrayList();

	// private Admin adminActuel; sera implémenté

	// Autres champs
	private Equipe eqCourant = new Equipe();
	private Equipe eqSelection = new Equipe();

	private Participant partCourant = new Participant();
	private Participant partSelectionne;

	private Personne pers = new Personne();
	private Equipe eq = new Equipe();

	private Admin admin;
	
	private Hackathon hackCourant ;
	
	private SuperUser superUser ;
	
	@Inject
	private IMapper mapper;
	@Inject
	private DaoParticipant daoParticipant = new DaoParticipant();
	@Inject
	private DaoEquipe daoEquipe = new DaoEquipe();
	@Inject
	private DaoAdmin daoAdmin = new DaoAdmin();
	@Inject
	private DaoIntervenir daoIntervenir = new DaoIntervenir() ;
	@Inject
	private DaoSuperUser daoSuperUser = new DaoSuperUser() ;
	@Inject
	private ModelConnexion modelConnexion;

	// Contructeur
	public ModelGestionnaireParticipants() {
		// rien de rien
	}

	@PostConstruct
	public void init() {
		admin = daoAdmin.retrouverPourCompte(modelConnexion.getCompteActif().getId()) ;
		if(admin !=  null) {
			//cas où ce n'est pas un gestionneur qui se connecte
			hackCourant = daoIntervenir.retrouverHackathon( admin.getPersonne().getId() ) ;
		} else {
			superUser = daoSuperUser.retrouver( modelConnexion.getCompteActif().getId() ) ;
 			hackCourant = daoIntervenir.retrouverHackathon( superUser.getPersonne().getId() ) ;
		}
	}
	// getters et setters

	public Equipe getEqCourant() {
		return eqCourant;
	}

	public Equipe getEqSelection() {
		return eqSelection;
	}

	public void setEqSelection(Equipe eqSelection) {
		this.eqSelection = eqSelection;
	}

	public Participant getPartCourant() {
		return partCourant;
	}

	public Participant getPartSelectionne() {
		return partSelectionne;
	}

	public void setPartSelectionne(Participant partSelectionne) {
		this.partSelectionne = partSelectionne;
	}

	public ObservableList<Equipe> getLvEquipe() {
		return lvEquipe;
	}

	public ObservableList<Participant> getLvParticipant() {
		return lvParticipant;
	}

	// ACTIONS

	public void actualiserListe() {
		lvEquipe.setAll(daoEquipe.listerTout());
		// liaison entre la liste des membres et celles des équipes
		lvParticipant.setAll();
	}

	public void actualiserListeParticipants() {
		if (eqSelection == null) {
			lvParticipant.setAll();
		} else {
			lvParticipant.setAll(daoParticipant.listerTout(eqSelection.getIdEquipe()));
		}
	}

	public void actualiserEquipeCourant() {
		if (eqSelection == null) {
			eqSelection = new Equipe();
		} else {
			eqSelection = daoEquipe.retrouver(eqSelection.getIdEquipe());
		}
		mapper.update(eqCourant, eqSelection);
	}

	public void actualiserParticipantCourant() {
		if (partSelectionne == null) {
			partSelectionne = new Participant();
			partSelectionne.setEquipe(eq);
			partSelectionne.setPersonne(pers);
			partSelectionne.getPersonne().setCompte(new Compte());
		} else {
			partSelectionne = daoParticipant.retrouver(partSelectionne.getPersonne().getId());
			// System.out.println(partCourant.getPersonne().getNom() );
		}
		mapper.update(partCourant, partSelectionne);
	}

	public void chargerEquipe() {
		validerMiseAJourEquipe();
		if (eqCourant.getIdEquipe() == null) {
			daoEquipe.inserer(eqCourant);
		} else {
			daoEquipe.modifier(eqCourant);
		}
	}

	public void chargerParticipant() {
		validerMiseAJourParticipant();
		if (partCourant.getPersonne().getId() == null) {
			//verification du nombre de membres dans l'equipe
			daoParticipant.inserer(partCourant); // la methode inserer ajoute aussi l'id 
			daoIntervenir.inserer( hackCourant, partCourant.getPersonne());
		} else {
			daoParticipant.modifier(partCourant);
		}
	}

	public void validerMiseAJourEquipe() {
		// On vérifie la validité des données pour une équipe
		StringBuilder message = new StringBuilder();

		if (eqCourant.getLienTravaux() == null || eqCourant.getLienTravaux().isEmpty()) {
			message.append("\nLe lien vers les travaux ne doit pas être vide.");
		} else if (eqCourant.getLienTravaux().length() > 500) {
			message.append("\nLe lien vers les travaux ne doit pas excéder 500 caractères.");
		}
		if (eqCourant.getNombreMembre() == null) {
			message.append("\nLe nombre de membres ne doit pas être vide.");
		}
		if (eqCourant.getPseudo() == null || eqCourant.getPseudo().isEmpty()) {
			message.append("\nLe pseudo ne doit pas être vide.");
		} else if (eqCourant.getPseudo().length() > 20) {
			message.append("\nLe pseudo ne doit pas excéder 20 caractères.");
		}
		if (message.length() > 0)
			throw new ExceptionValidation(message.toString().substring(1));
	}

	public void validerMiseAJourParticipant() {
		// On vérifie la validité des données pour un participant
		StringBuilder message = new StringBuilder();

		if (partCourant.getEquipe() == null) {
			message.append("\nL'id de l'équipe ne doit pas être vide.");
		}
		if (partCourant.getPersonne() == null) {
			message.append("\nL'id de la personne ne doit pas être vide.");
		}
		if (partCourant.getSpecialite() == null || partCourant.getSpecialite().isEmpty()) {
			message.append("\nLa spécialité ne doit pas être vide.");
		} else if (partCourant.getSpecialite().length() > 50) {
			message.append("\nLa spécialité ne doit pas excéder 50 caractères.");
		}
		if (message.length() > 0)
			throw new ExceptionValidation(message.toString().substring(1));
	}

	public void suppEquipe() {
		daoParticipant.supprimerPourEquipe(eqCourant.getIdEquipe());
		daoEquipe.supprimer(eqCourant.getIdEquipe());
	}

	public void suppParticipant() {
		daoIntervenir.supprimer( hackCourant, partCourant.getPersonne());
		daoParticipant.supprimer(partCourant.getPersonne().getId());
	}
	
	public int compterNombreMembre(Equipe eq) {
		System.out.println( daoParticipant.compterPourEquipe(eq) );
		return daoParticipant.compterPourEquipe(eq) ;
	}

	// retourne le nom de l'admin
	public String getNomAdmin() {
		if(admin !=  null)
			return admin.getPersonne().getNom();
		else
			return superUser.getPersonne().getNom() ;
	}

	// retourne le nom du hackathon
	public String getNomHack() {
		return hackCourant.getNom();
	}
	
	public boolean isSuperUser() {
		return admin == null ;
	}
	// validerMiseAjourEquipe(){
	// eqCourant
}