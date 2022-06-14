package hackathon.view.jury;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import hackathon.dao.DaoCorrecteur;
import hackathon.dao.DaoEquipe;
import hackathon.dao.DaoEvaluer;
import hackathon.dao.DaoIntervenir;
import hackathon.dao.DaoParticipant;
import hackathon.dao.DaoPersonne;
import hackathon.data.Correcteur;
import hackathon.data.Equipe;
import hackathon.data.Hackathon;
import hackathon.data.Participant;
import hackathon.data.Personne;
import hackathon.view.systeme.ModelConnexion;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ModelJury {

	// Données observables
	private final ObservableList<Equipe> lvEquipe = FXCollections.observableArrayList();
	private final ObservableList<Participant> lvParticipant = FXCollections.observableArrayList();
	private final ObservableList<Integer> lvNotes = FXCollections.observableArrayList();

	private final ObservableList<Integer> NOTES = FXCollections.observableArrayList();

	private final Property<Integer> noteCourante = new SimpleObjectProperty<>();

	// Autres champs

	private Correcteur correcteurCourant;

	private Equipe eqSelection = new Equipe();

	private Participant partSelectionne = new Participant();
	
	private Hackathon hackCourant ;
	
	// private Evaluer NoteCourante = new Evaluer();

	@Inject
	private DaoEvaluer daoEvaluer = new DaoEvaluer();

	@Inject
	private DaoCorrecteur daoCorrecteur = new DaoCorrecteur();
	@Inject
	private DaoParticipant daoParticipant = new DaoParticipant();
	@Inject
	private DaoEquipe daoEquipe = new DaoEquipe();
	@Inject
	private DaoPersonne daoPersonne = new DaoPersonne();
	@Inject
	private DaoIntervenir daoIntervenir = new DaoIntervenir() ;
	
	@Inject
	private ModelConnexion modelConnexion;

	// Contructeur
	public ModelJury() {
		// rien de rien
		for (int i = 0; i <= 20; i++) {
			NOTES.add(i);
		}
	}

	@PostConstruct
	public void init() {
		List<Personne> liste = daoPersonne.listerPourCompte(modelConnexion.getCompteActif()) ;
		if(liste.size() == 0 ) {
			//alors il ne s'agit pas d'un correcteur mais d'un superUser
			//il n'est pas autorisé à corriger 
		} else {
			correcteurCourant = daoCorrecteur
					.retrouver(daoPersonne.listerPourCompte(modelConnexion.getCompteActif()).get(0).getId());
		}
		hackCourant = daoIntervenir.retrouverHackathon( correcteurCourant.getId() ) ;
		//System.out.println(correcteurCourant.getId());
	}

	// getters et setters
	public ObservableList<Integer> getLvEvaluer() {
		return lvNotes;
	}

	public Equipe getEqSelection() {
		return eqSelection;
	}

	public void setEqSelection(Equipe eqSelection) {
		this.eqSelection = eqSelection;
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

	public ObservableList<Integer> getNOTES() {
		return NOTES;
	}

	public final Property<Integer> noteCouranteProperty() {
		return this.noteCourante;
	}

	public final Integer getNoteCourante() {
		return this.noteCouranteProperty().getValue();
	}

	public final void setNoteCourante(final Integer noteCourante) {
		this.noteCouranteProperty().setValue(noteCourante);
	}

	// ACTIONS

	public void actualiserListe() {
		lvEquipe.setAll(daoEquipe.listerTout());

		lvNotes.setAll();
		// listes des notes
		for (Equipe e : lvEquipe) {
			lvNotes.add(daoEvaluer.Retrouver_Note_pourJury(e, correcteurCourant));
		}

		// liaison entre la liste des membres et celles des équipes
		if (eqSelection == null) {
			lvParticipant.setAll();
		} else {
			lvParticipant.setAll(daoParticipant.listerTout(eqSelection.getIdEquipe()));
		}
	}

	public void actualiserListeParticipants() {

		if (eqSelection == null) {
			lvParticipant.setAll();
		} else {
			lvParticipant.setAll(daoParticipant.listerTout(eqSelection.getIdEquipe()));
		}
	}

	public void actualiserNoteEquipe() {
		// daoEvaluer.Retrouver_Note_pourJury();
		if (eqSelection == null) {
			noteCourante.setValue(null);
		} else {
			noteCourante.setValue(daoEvaluer.Retrouver_Note_pourJury(eqSelection, correcteurCourant));
		}

	}

	public void AjouterNote() {
		if (eqSelection != null) {
			// System.out.println(noteCourante);
			if (daoEvaluer.Retrouver_Note_pourJury(eqSelection, correcteurCourant) == null) {
				daoEvaluer.inserer_note(eqSelection, correcteurCourant, noteCourante.getValue());
			} else {
				daoEvaluer.modifier_note(eqSelection, correcteurCourant, noteCourante.getValue());
			}
		}
	}

//	public void validerMiseAJourCorrecteur() {
//		// On vérifie la validité des données pour un correcteur
//		StringBuilder message = new StringBuilder();
//
//		if (correcteurCourant.getId() == null) {
//			message.append("\nL'id du correcteur ne doit pas être vide.");
//		}
//		if (correcteurCourant.getPersonne() == null) {
//			message.append("\nL'id de la personne ne doit pas être vide.");
//		}
//		if (correcteurCourant.getMotDePasse() == null || correcteurCourant.getMotDePasse().isEmpty()) {
//			message.append("\nLe mot de passe ne doit pas être vide.");
//		} else if (correcteurCourant.getMotDePasse().length() > 50) {
//			message.append("\nLe mot de passe ne doit pas excéder 50 caractères.");
//		}
//		if (correcteurCourant.getSpecialite() == null || correcteurCourant.getSpecialite().isEmpty()) {
//			message.append("\nLa spécialité ne doit pas être vide.");
//		} else if (correcteurCourant.getSpecialite().length() > 50) {
//			message.append("\nLa spécialité ne doit pas excéder 50 caractères.");
//		}
//		if (message.length() > 0)
//			throw new ExceptionValidation(message.toString().substring(1));
//	}

	// retourne le nom de l'admin
	public String getNom() {
		return correcteurCourant.getPersonne().getNom();
	}

	// retourne le nom du hackathon
	public String getNomHack() {
		return hackCourant.getNom();
	}

}