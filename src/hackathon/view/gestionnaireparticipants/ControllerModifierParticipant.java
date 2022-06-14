package hackathon.view.gestionnaireparticipants;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import jfox.javafx.util.ConverterInteger;
import jfox.javafx.view.Controller;
import jfox.javafx.view.IManagerGui;

import javax.inject.Inject;

import hackathon.data.Equipe;
import hackathon.data.Participant;
import hackathon.view.EnumView;

public class ControllerModifierParticipant extends Controller {

	@FXML
	private TextField tfNom;

	@FXML
	private TextField tfPrenom;

	@FXML
	private TextField tfSpecialite;

	@FXML
	private TextField tfId;

	@FXML
	private ComboBox<Equipe> cbEquipe;

	@FXML
	private Label lbNomAdmin;

	@FXML
	private Label lbNomHack;

	@Inject
	private IManagerGui managerGui;
	@Inject
	private ModelGestionnaireParticipants modelGP;

	@FXML
	private void initialize() {
		// System.out.println( courant.getPersonne().getIdCompte());
		setter();
	}

	// Actions
	@FXML
	public void refresh() {
		lbNomAdmin.setText(modelGP.getNomAdmin());
		lbNomHack.setText(modelGP.getNomHack());
		setter();
	}

	@FXML
	void doAbandonner() {
		tfNom.setText(null);
		tfPrenom.setText(null);
		tfId.setText(null);
		managerGui.showView(EnumView.GestParticipants);
	}

	@FXML
	void doSauvegarder() {
		Equipe eq = cbEquipe.getSelectionModel().getSelectedItem() ;
		if( eq.getNombreMembre() > modelGP.compterNombreMembre(eq) ) {
			modelGP.chargerParticipant();
			managerGui.showView(EnumView.GestParticipants);
		}else { 
			managerGui.showDialogMessage("Cette equipe ne peut plus admettre de participant");
		}
	}
	
	
	public void setter() {
		Participant courant = modelGP.getPartCourant();
		//prudence
		tfNom.setText(null);
		tfPrenom.setText(null);
		tfId.setText(null);
		
		//binding 
		bindBidirectional(tfNom, courant.getPersonne().nomProperty());
		bindBidirectional(tfPrenom, courant.getPersonne().prenomProperty());
		bindBidirectional(tfSpecialite, courant.specialiteProperty());
		bindBidirectional(tfId, courant.getPersonne().idProperty(), new ConverterInteger());
		tfId.setDisable(true);
		cbEquipe.setItems(modelGP.getLvEquipe());
		bindBidirectional(cbEquipe, courant.equipeProperty());
		
		//setter de champs
		tfNom.setText( courant.getPersonne().getNom() );
		tfPrenom.setText(courant.getPersonne().getPrenom());
		tfSpecialite.setText(courant.getSpecialite()) ;
		tfId.setText(courant.getPersonne().getId() + "");
		cbEquipe.setValue( modelGP.getPartCourant().getEquipe() );
		
	}

//	@FXML
//	void doRetour() {
//		managerGui.showView(EnumView.GestParticipants);
//	}

}