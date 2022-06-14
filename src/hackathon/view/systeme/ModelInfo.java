package hackathon.view.systeme;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;

public class ModelInfo {

	// Données observables

	private final Property<String> titre = new SimpleObjectProperty<>();
	private final Property<String> message = new SimpleObjectProperty<>();

	// Getters

	public Property<String> titreProperty() {
		return titre;
	}

	public Property<String> messageProperty() {
		return message;
	}
}