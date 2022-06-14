package hackathon.view.superUser;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import hackathon.commun.IMapper;
import hackathon.commun.Roles;
import hackathon.dao.DaoHackathon;
import hackathon.dao.DaoPersonne;
import hackathon.dao.DaoSuperUser;
import hackathon.data.Hackathon;
import hackathon.data.Personne;
import hackathon.data.SuperUser;
import hackathon.view.systeme.ModelConnexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jfox.exception.ExceptionValidation;

public class ModelSuperUser {

	// Données observables
	private final ObservableList<Hackathon> lvHack = FXCollections.observableArrayList();

	private final ObservableList<Personne> lvPersonne = FXCollections.observableArrayList();

	private final static ObservableList<String> lvRoles = FXCollections.observableArrayList();

	// Autres champs

	private SuperUser superUserActuel; // l'utilisateur actuel

	private Hackathon hackCourant = new Hackathon();// Le hackathon qui est affiché a l'écran avec ses détails
	private Hackathon hackSelection = new Hackathon();// Le hackathon qui est selectionne dans la liste view

	@Inject
	private IMapper mapper;
	@Inject
	private ModelConnexion modelConnexion;
	@Inject
	private DaoHackathon daoHackathon;
	@Inject
	private DaoSuperUser daoSuperUser = new DaoSuperUser();
	@Inject
	private DaoPersonne daoPersonne = new DaoPersonne();

	// Dans ce constructeur on initialise le superUser

	public ModelSuperUser() {
		lvRoles.setAll(Roles.getRoles());
	}

	@PostConstruct
	public void init() {
		superUserActuel = daoSuperUser.retrouver(modelConnexion.getCompteActif().getId());
		hackCourant.setSuperUser(superUserActuel);
	}

	// Getters & setters

	public Hackathon getHackCourant() {
		return hackCourant;
	}

	public void setHackCourant(Hackathon hackCourant) {
		this.hackCourant = hackCourant;
	}

	public Hackathon getHackSelection() {
		return hackSelection;
	}

	public void setHackSelection(Hackathon hackSelection) {
		this.hackSelection = hackSelection;
	}

	public ObservableList<Hackathon> getLvHack() {
		return lvHack;
	}

	public void chargerListePersonne() {
		lvPersonne.setAll(daoPersonne.listerAdmin());
	}

	public ObservableList<Personne> getLvPersonne() {
		return lvPersonne;
	}

	public ObservableList<String> getLvRoles() {
		return lvRoles;
	}

	// Actions

	public void actualiserListe() {
		lvHack.setAll(daoHackathon.listerTout(this.superUserActuel));
	}

	public void actualiserCourant() {
		if (hackSelection != null) {
			// Dans ce cas on veut mettre dans HackCourant les infos du HackSelectionné
			// Si ce dernier est vide on ne fait rien
			hackSelection = daoHackathon.retrouver(hackSelection.getIdHackathon());
			hackSelection.setSuperUser(superUserActuel); // daoSuperUser.ret
			mapper.update(hackCourant, hackSelection);
		} else {
			// rien
		}
	}

	public void validerMiseAJourHackathon() {
		// On vérifie la validité des données pour un hackathon
		StringBuilder message = new StringBuilder();

		if (hackCourant.getIdHackathon() == null) {
			message.append("\nL'id ne doit pas être vide.");
		}
		if (hackCourant.getSuperUser() == null) {
			message.append("\nL'id du SuperUser ne doit pas être vide.");
		}
		if (hackCourant.getNom() == null || hackCourant.getNom().isEmpty()) {
			message.append("\nLe nom ne doit pas être vide.");
		} else if (hackCourant.getNom().length() > 500) {
			message.append("\nLe nom ne doit pas excéder 500 caractères.");
		}
		if (hackCourant.getTheme() == null || hackCourant.getTheme().length() > 500) {
			message.append("\nLe thème ne doit pas excéder 500 caractères.");
		}
		if (hackCourant.getProblematique() == null || hackCourant.getProblematique().length() > 1000) {
			message.append("\nLa problématique ne doit pas excéder 1000 caractères.");
		}
		if (hackCourant.getLieu() == null || hackCourant.getLieu().length() > 100) {
			message.append("\nLe lieu ne doit pas excéder 100 caractères.");
		}
		if (hackCourant.getDescription() == null || hackCourant.getDescription().length() > 2000) {
			message.append("\nLa description ne doit pas excéder 2000 caractères.");
		}
		if (hackCourant.getDateDebut() == null || hackCourant.getDateFin() == null) {
			message.append("\nLes champs date de début et date de fin ne doivent pas être vides.");
		} else if (hackCourant.getDateFin().isBefore(hackCourant.getDateDebut())) {
			message.append("\nLa date de début doit précéder la date de fin.");
		}
		if (hackCourant.getMaxEquipe() == null || hackCourant.getMinEquipe() == null) {
			message.append("\nLes champs nombre minimum et nombre maximum ne doivent pas être vides.");
		} else if (hackCourant.getMaxEquipe() < hackCourant.getMinEquipe()) {
			message.append("\nLe nombre minimum doit être inférieur au nombre maximum.");
		} else if (hackCourant.getMaxEquipe() > 10) {
			message.append("\nLe nombre maximum doit être inférieur ou égal à 10.");
		}
		if (message.length() > 0)
			throw new ExceptionValidation(message.toString().substring(1));
	}

	public void ajoutHack() {
		// On effectue la verification des données de la variable HackCourant
		validerMiseAJourHackathon();

		// ensuite on ajoute
		daoHackathon.inserer(hackCourant);

	}

	public void modifierHack() {
		// On effectue la verification des données de la variable HackCourant
		validerMiseAJourHackathon();

		// ensuite on modifie
		daoHackathon.modifier(hackCourant);
	}

	// retourne le nom de l'admin courant
	public String getNomAdmin() {
		return this.superUserActuel.getPersonne().getNom();
	}

	// retourne le nom du hackathon courant
	public String getNomHackathon() {
		return this.hackCourant.getNom();
	}

}