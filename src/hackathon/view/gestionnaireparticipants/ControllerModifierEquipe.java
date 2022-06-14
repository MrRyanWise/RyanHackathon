package hackathon.view.gestionnaireparticipants;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import jfox.javafx.util.ConverterInteger;
import jfox.javafx.view.Controller;
import jfox.javafx.view.IManagerGui;

import javax.inject.Inject;

import hackathon.data.Equipe;
import hackathon.view.EnumView;

public class ControllerModifierEquipe extends Controller {

	@FXML
	private TextField tfPseudo;

	@FXML
	private TextField tfLien;

	@FXML
	private TextField tfId;

	@FXML
	private ComboBox<Integer> cbNombreMax;

	@FXML
	private Label lbNomAdmin;

	@FXML
	private Label lbNomHack;

	private final ObservableList<Integer> lvMax = FXCollections.observableArrayList();

	@Inject
	private IManagerGui managerGui;
	@Inject
	private ModelGestionnaireParticipants modelGP;

	@FXML
	private void initialize() {
		setList();
		Equipe courant = modelGP.getEqCourant();
		// System.out.println( courant.getPersonne().getIdCompte());
		// Data binding

		lbNomAdmin.setText(modelGP.getNomAdmin());
		lbNomHack.setText(modelGP.getNomHack());
		bindBidirectional(tfPseudo, courant.pseudoProperty());
		bindBidirectional(tfLien, courant.lienTravauxProperty());
		bindBidirectional(tfId, courant.idEquipeProperty(), new ConverterInteger());
		bindBidirectional(cbNombreMax, courant.nombreMembreProperty());
		tfId.setDisable(true);
		cbNombreMax.setItems(lvMax);
		// bindBidirectional(cbEquipe, courant.equipeProperty());
	}

	// Actions
	@FXML
	public void refresh() {
		cbNombreMax.setValue(modelGP.getEqCourant().getNombreMembre());
	}

	@FXML
	void doAbandonner() {
		managerGui.showView(EnumView.GestParticipants);
	}

	@FXML
	void doSauvegarder() {
		int nbMax = cbNombreMax.getSelectionModel().getSelectedItem() ;
		if( nbMax >= modelGP.compterNombreMembre( modelGP.getEqCourant() )) {
			modelGP.chargerEquipe();
			managerGui.showView(EnumView.GestParticipants);
		} else {
			managerGui.showDialogMessage("Cette equipe possede un nombre participant supérieur au nombre choisit");
		}
	}

//	@FXML
//	void doRetour() {
//		managerGui.showView(EnumView.GestParticipants);
//	}

	private void setList() {
		for (int i = 2; i < 11; i++) {
			lvMax.add(i);
		}
	}
}