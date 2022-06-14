package hackathon.view.gestionnaireparticipants;

import javax.inject.Inject;

import hackathon.data.Equipe;
import hackathon.data.Participant;
import hackathon.view.EnumView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Polygon;
import jfox.javafx.util.UtilFX;
import jfox.javafx.view.Controller;
import jfox.javafx.view.IManagerGui;

public class ControllerGestionParticipant extends Controller {

	@FXML
	private ListView<Equipe> lvEquipe;
	@FXML
	private ListView<Participant> lvMembre;

	@FXML
	private Label lbNomAdmin;
	@FXML
	private Label lbNomHack;
	@FXML
	private Polygon pRetour;

	@Inject
	private IManagerGui managerGui;
	@Inject
	private ModelGestionnaireParticipants modelGP;

	// Methodes globales
	@FXML
	private void initialize() {
		lbNomAdmin.setText(modelGP.getNomAdmin());
		lbNomHack.setText(modelGP.getNomHack());
		lvEquipe.setItems(modelGP.getLvEquipe());
		UtilFX.setCellFactory(lvEquipe, item -> item.getPseudo());

		lvMembre.setItems(modelGP.getLvParticipant());
		UtilFX.setCellFactory(lvMembre, item -> item.getPersonne().getNom() + " " + item.getPersonne().getPrenom());
		refresh();
	}

	@Override
	public void refresh() {
		modelGP.setEqSelection(null);
		modelGP.setPartSelectionne(null);
		
		modelGP.actualiserListe();
		UtilFX.selectRow(lvEquipe, modelGP.getEqSelection());
		UtilFX.selectRow(lvMembre, modelGP.getPartSelectionne());
		lvEquipe.requestFocus();
	}

	// Méthodes liés aux boutons

	// Pour l'equipe

	@FXML
	public void doAjoutEquipe() {
		modelGP.setEqSelection(null);
		modelGP.actualiserEquipeCourant();
		managerGui.showView(EnumView.ModifierEquipe);
	}

	@FXML
	public void doModifierEquipe() {
		modelGP.setEqSelection(lvEquipe.getSelectionModel().getSelectedItem());
		modelGP.actualiserEquipeCourant();
		if (modelGP.getEqCourant().getIdEquipe() == null)
			managerGui.showDialogMessage("Veullez selectionner une équipe");
		else
			managerGui.showView(EnumView.ModifierEquipe);
	}

	@FXML
	public void doSupprimerEquipe() {
		modelGP.setEqSelection(lvEquipe.getSelectionModel().getSelectedItem());
		modelGP.actualiserEquipeCourant();
		if (modelGP.getEqCourant().getIdEquipe() == null)
			managerGui.showDialogError("Veuillez selectionnez une equipe ");
		else if (managerGui.showDialogConfirm("Etes vous sur de vouloir continuez ?")) {
			modelGP.suppEquipe();
			refresh();
		}
	}

	// Pour le participant

	@FXML
	public void doAjoutParticipant() {
		// on s'assure que courant soit null
		modelGP.setPartSelectionne(null);
		modelGP.actualiserParticipantCourant();
		managerGui.showView(EnumView.ModifierParticipants);
	}

	@FXML
	public void doModifierParticipant() {
		if (lvMembre.getSelectionModel().getSelectedItem() == null) {
			managerGui.showDialogMessage("Veullez selectionner un participant");
		} else {
			modelGP.setEqSelection(lvEquipe.getSelectionModel().getSelectedItem());
			modelGP.actualiserEquipeCourant();
			modelGP.setPartSelectionne(lvMembre.getSelectionModel().getSelectedItem());
			modelGP.actualiserParticipantCourant();
			managerGui.showView(EnumView.ModifierParticipants);
		}
	}

	@FXML
	void doSupprimerParticipant() {
		modelGP.setPartSelectionne(lvMembre.getSelectionModel().getSelectedItem());
		modelGP.actualiserParticipantCourant();
		if (modelGP.getPartCourant().getPersonne().getId() == null)
			managerGui.showDialogError("Veuillez selectionnez une participant ");
		else if (managerGui.showDialogConfirm("Etes vous sur de vouloir continuez ?")) {
			modelGP.suppParticipant();
			refresh();
		}
	}

	@FXML
	void doRetour() {
		if (modelGP.isSuperUser()) {
			managerGui.showView(EnumView.AccueilHackathon);
		} else {
			managerGui.showDialogMessage("Veuillez vous deconnectez normalement!!");
		}
	}

	// Methodes générales

	@FXML
	private void gererClicSurListe(MouseEvent event) {
		if (event.getButton().equals(MouseButton.PRIMARY)) {
			if (event.getClickCount() == 2) {
				if (lvEquipe.getSelectionModel().getSelectedIndex() == -1) {
					managerGui.showDialogError("Aucun élément n'est sélectionné dans la liste.");
				} else {
					doModifierEquipe();
				}
			} else if (event.getClickCount() == 1) {
				// doModifier();
				modelGP.setEqSelection(lvEquipe.getSelectionModel().getSelectedItem());
				modelGP.actualiserListeParticipants();
			}
		}
	}
	
	@FXML
	private void gererClicSurListeMembre(MouseEvent event) {
		if (event.getButton().equals(MouseButton.PRIMARY)) {
			if (event.getClickCount() == 2) {
				if (lvMembre.getSelectionModel().getSelectedIndex() == -1) {
					managerGui.showDialogError("Aucun élément n'est sélectionné dans la liste.");
				} else {
					doModifierParticipant();
				}
			} else if (event.getClickCount() == 1) {
				modelGP.setPartSelectionne(lvMembre.getSelectionModel().getSelectedItem());
				modelGP.actualiserParticipantCourant();
			}
		}
	}

}