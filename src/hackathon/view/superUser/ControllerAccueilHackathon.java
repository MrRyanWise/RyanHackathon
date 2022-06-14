package hackathon.view.superUser;

import javax.inject.Inject;

import hackathon.data.Hackathon;
import hackathon.view.EnumView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import jfox.javafx.util.UtilFX;
import jfox.javafx.view.Controller;
import jfox.javafx.view.IManagerGui;

public class ControllerAccueilHackathon extends Controller {

	@FXML
	private ListView<Hackathon> lvHack;

	@FXML
	private Label lbNom;

	@FXML
	private Button btAdministrer;

	@FXML
	private Button btCreer;

	@Inject
	private IManagerGui managerGui;
	@Inject
	private ModelSuperUser modelSuperUser;

	@FXML
	private void initialize() {
		// list View
		lvHack.setItems(modelSuperUser.getLvHack());
		UtilFX.setCellFactory(lvHack, "nom");
		refresh();
	}

	@Override
	public void refresh() {
		modelSuperUser.actualiserListe();
		UtilFX.selectRow(lvHack, modelSuperUser.getHackSelection());
		lvHack.requestFocus();
		lbNom.setText( modelSuperUser.getNomAdmin() ) ;
	}

	@FXML
	void doAdministrer() {
		modelSuperUser.setHackSelection(lvHack.getSelectionModel().getSelectedItem());

		if (modelSuperUser.getHackSelection() != null) {
			modelSuperUser.actualiserCourant();// permet de mettre dans courant la variable sélectionné
			managerGui.showView(EnumView.GestionHackathon);
		} else {
			managerGui.showDialogError("Aucun élément n'est sélectionné dans la liste.");
		}
	}

	@FXML
	void doCreer() {
		modelSuperUser.setHackSelection(null);
		modelSuperUser.actualiserCourant();
		managerGui.showView(EnumView.HackathonForm);
	}

	@FXML
	void doRetour() {
		managerGui.showDialogMessage("Veuillez vous deconnecter normalement");
	}
}