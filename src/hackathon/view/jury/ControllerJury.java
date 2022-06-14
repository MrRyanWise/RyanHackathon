package hackathon.view.jury;

import javax.inject.Inject;

import hackathon.data.Equipe;
import hackathon.data.Participant;
import hackathon.view.EnumView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Polygon;
import jfox.javafx.util.UtilFX;
import jfox.javafx.view.Controller;
import jfox.javafx.view.IManagerGui;

public class ControllerJury extends Controller {

	@FXML
	private ListView<Equipe> lvEquipe;

	@FXML
	private ListView<Integer> lvNote;

	@FXML
	private ListView<Participant> lvMembre;

	@FXML
	private Label lbNomAdmin;

	@FXML
	private Label lbNomHack;

	@FXML
	private Polygon pRetour;

	@FXML
	private ComboBox<Integer> cbNote;

	private final ObservableList<Integer> lvMax = FXCollections.observableArrayList();

	@Inject
	private IManagerGui managerGui;
	@Inject
	private ModelJury modelJury;

	// Methodes globales
	@FXML
	private void initialize() {
		setList();

		lvEquipe.setItems(modelJury.getLvEquipe());
		UtilFX.setCellFactory(lvEquipe, item -> item.getPseudo());

		lvNote.setItems(modelJury.getLvEvaluer());

		lvMembre.setItems(modelJury.getLvParticipant());
		UtilFX.setCellFactory(lvMembre, item -> item.getPersonne().getNom());

		refresh();
	}

	@Override
	public void refresh() {
		modelJury.setEqSelection(lvEquipe.getSelectionModel().getSelectedItem());
		UtilFX.selectRow(lvEquipe, modelJury.getEqSelection());
		UtilFX.selectRow(lvMembre, modelJury.getPartSelectionne());

		modelJury.actualiserListe();
		modelJury.actualiserNoteEquipe();
		lvEquipe.requestFocus();

		cbNote.setItems(modelJury.getNOTES());
		bindBidirectional(cbNote, modelJury.noteCouranteProperty());
		// cbNote.setValue(modelJury.getNoteCourante());

		lvNote.setDisable(true);

		lbNomAdmin.setText(modelJury.getNom());
	}

	@FXML
	public void chargerMembre() {
		modelJury.setEqSelection(lvEquipe.getSelectionModel().getSelectedItem());
		modelJury.actualiserListe();
		lvEquipe.requestFocus();
	}

	// Methodes liés aux bouton

	@FXML
	public void doSoumettre() {
		modelJury.AjouterNote();
		refresh();
	}

	private void setList() {
		for (int i = 2; i < 6; i++) {
			lvMax.add(i);
		}
	}

	@FXML
	private void gererClicSurListe(MouseEvent event) {
		if (event.getButton().equals(MouseButton.PRIMARY)) {
			if (event.getClickCount() == 2) {
				if (lvEquipe.getSelectionModel().getSelectedIndex() == -1) {
					managerGui.showDialogError("Aucun élément n'est sélectionné dans la liste.");
				} else {
					// doModifier();
				}
			}
			// on rafraichit toutes les liste qui dependent de la liste d'equipe
			modelJury.setEqSelection(lvEquipe.getSelectionModel().getSelectedItem());
			modelJury.actualiserListeParticipants();
			modelJury.actualiserNoteEquipe();
		}
	}

	@FXML
	void doRetour() {
		managerGui.showDialogMessage("deconnextez vous normalement");
	}
}