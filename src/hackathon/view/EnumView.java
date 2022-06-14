package hackathon.view;

import jfox.javafx.view.IEnumView;
import jfox.javafx.view.View;

public enum EnumView implements IEnumView {

	Info				( "systeme/ViewInfo.fxml" ),
	Connexion			( "systeme/ViewConnexion.fxml" ),
	
	//TRYG
	AccueilHackathon	( "superUser/ViewAccueilHackathon.fxml"),
	GestionHackathon	( "superUser/ViewGestionHackathon.fxml"),
	HackathonForm		( "superUser/ViewHackathonForm.fxml"),
	JURY				( "jury/ViewJury.fxml"), 
	GestParticipants	( "gestionnaireparticipants/ViewGestionParticipant.fxml"),
	ModifierParticipants	( "gestionnaireparticipants/ViewModifierParticipant.fxml"),
	ModifierEquipe			("gestionnaireparticipants/ViewModifierEquipe.fxml")
	;

	// Champs

	private final View view;

	// Constructeurs

	EnumView(String path, boolean flagReuse) {
		view = new View(path, flagReuse);
	}

	EnumView(String path) {
		view = new View(path);
	}

	// Getters & setters

	@Override
	public View getView() {
		return view;
	}
}