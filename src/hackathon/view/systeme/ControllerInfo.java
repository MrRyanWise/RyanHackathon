package hackathon.view.systeme;

import javax.inject.Inject;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import jfox.javafx.view.Controller;

public class ControllerInfo extends Controller {

	// Composants de la vue

	@FXML
	private Label lblTitre;
	@FXML
	private Label lblMessage;

	// Autres champs

	@Inject
	private ModelInfo modelInfo;
	
	// Initialisation
	@FXML
	private void initialize() {  
		
		// Data binding
		bindBidirectional(lblTitre, modelInfo.titreProperty());
		bindBidirectional(lblMessage, modelInfo.messageProperty());
	}
}