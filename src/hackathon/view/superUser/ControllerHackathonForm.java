package hackathon.view.superUser;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import jfox.javafx.util.ConverterInteger;
import jfox.javafx.view.Controller;
import javax.inject.Inject;
import hackathon.data.Hackathon;
import hackathon.view.EnumView;
import javafx.scene.control.TextField;
import javafx.scene.shape.Polygon;
import jfox.javafx.view.IManagerGui;
import javafx.scene.control.TextArea;

public class ControllerHackathonForm extends Controller {

	@FXML
	private Label lbNomAdmin;

	@FXML
	private Label lbNomHack;

	@FXML
	private TextField NomHack;

	@FXML
	private TextField ThemeHack;

	@FXML
	private TextField ProblematiqueHack;

	@FXML
	private TextField LieuHack;

	@FXML
	private TextField DateHeureHack;

	@FXML
	private TextArea DescriptionHack;

	@FXML
	private TextField NbmaxEquipe;

	@FXML
	private TextField NbminEquipe;

	@FXML
	private TextField nbJuryHack;

	@FXML
	private Button btModifier;

	@FXML
	private DatePicker dtDateDebut;

	@FXML
	private DatePicker dtDateFin;

	@FXML
	private Polygon pRetour;

	// variable induite
	@Inject
	private ModelSuperUser modelSuperUser;
	@Inject
	private IManagerGui managerGui;

	// Actions

	@FXML
	private void initialize() {

		Hackathon courant = modelSuperUser.getHackCourant();

		// Data binding
		bindBidirectional(NomHack, courant.nomProperty());
		bindBidirectional(ThemeHack, courant.themeProperty());
		bindBidirectional(ProblematiqueHack, courant.problematiqueProperty());
		bindBidirectional(LieuHack, courant.lieuProperty());
		bindBidirectional(DescriptionHack, courant.descriptionProperty());
		bindBidirectional(NbmaxEquipe, courant.maxEquipeProperty(), new ConverterInteger());
		bindBidirectional(NbminEquipe, courant.minEquipeProperty(), new ConverterInteger());
		bindBidirectional(nbJuryHack, courant.nbJuryProperty(), new ConverterInteger());
		bindBidirectional(dtDateDebut, courant.dateDebutProperty());
		bindBidirectional(dtDateFin, courant.dateFinProperty());

//		 bindBidirectional( DateHeureHack , courant.dateDebutProperty(), new
//		 ConverterLocalDate());

	}

	@FXML
	void doModifier() {
		if(modelSuperUser.getHackSelection() == null)
			modelSuperUser.ajoutHack();
		else 
			modelSuperUser.modifierHack();
		managerGui.showView(EnumView.GestionHackathon);
	}

	@FXML
	void doRetour() {
		if(modelSuperUser.getHackSelection() == null)
			managerGui.showView(EnumView.AccueilHackathon);
		else
			managerGui.showView(EnumView.GestionHackathon);
	}
}