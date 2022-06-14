package hackathon.view.superUser;

import javax.inject.Inject;
import hackathon.data.Hackathon;
import hackathon.data.Personne;
import hackathon.view.EnumView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import jfox.javafx.util.UtilFX;
import jfox.javafx.view.Controller;
import jfox.javafx.view.IManagerGui;

public class ControllerGestionHackathon extends Controller {

	@FXML
	private ListView<Personne> lvNom;

	@FXML
	private TextArea txaBrief;

	@FXML
	private Label lbNomAdmin;

	@FXML
	private Label lbNomHack;

	@FXML
	private Button btModifier;

	@FXML
	private Button btSupprimer;

	@FXML
	private Button btDemarrer;
	
	@FXML
	private ComboBox<String> cbRole;

	@Inject
	private IManagerGui managerGui;
	@Inject
	private ModelSuperUser modelSuperUser;

	@FXML
	private void initialize() {
		refresh();
		UtilFX.setCellFactory(lvNom, item -> item.getNom() + " " + item.getPrenom());
		lvNom.setItems(modelSuperUser.getLvPersonne());
		txaBrief.setDisable(true);
	}

	@FXML
	public void refresh() {

		Hackathon courant = modelSuperUser.getHackCourant();

		// on initialise le message
		String brief = courant.getNom() + "\n" + "Lieu :" + courant.getLieu() + "\n";
		brief += "Problematique : " + courant.getProblematique() + "\n";
		brief += "Desciption : " + courant.getDescription() + "\n" + "Nombre jury: " + courant.getNbJury() + "\n";

		lbNomAdmin.setText(modelSuperUser.getNomAdmin());
		lbNomHack.setText(modelSuperUser.getNomHackathon());

		txaBrief.setText(brief);
		modelSuperUser.chargerListePersonne();

		cbRole.setItems(modelSuperUser.getLvRoles());
	}

	@FXML
	void doModifier() {
		managerGui.showView(EnumView.HackathonForm);
	}

	@FXML
	void doSupprimer() {
		// managerGui.showView(EnumView.AddHackaton);
	}
	
	@FXML
	void dochangerRole() {
		String role = cbRole.getValue() ;
		if(role.equals("ADMINBIS")) {
			managerGui.showView(EnumView.GestParticipants);
		} else if(role.equals("CORRECTEUR")) {
			managerGui.showDialogMessage("Vous ne pouvez pas evaluer des participants");
		} else if(role.equals("ADMINISTRATEUR")) {
			managerGui.showDialogMessage("Vous etes déjà super Admin");
		} else if(role.equals("UTILISATEUR")) {
			managerGui.showDialogMessage("L'interface utilisateur n'est pas accessible pour le moment");
		}
	}

	@FXML
	void doDemarrer() {
		// managerGui.showView(EnumView.AddHackaton);
	}

	@FXML
	void doRetour() {
		managerGui.showView(EnumView.AccueilHackathon);
	}
}