package hackathon.data;


import javafx.beans.property.Property; 
import javafx.beans.property.SimpleObjectProperty; 

public class Composer {
	private final Property<Jury> jury = new SimpleObjectProperty<>();
	private final Property<Correcteur> correcteur = new SimpleObjectProperty<>();
	
 
	// Constructeurs
	public Composer() {

	}

	// Getters et Setters

	public final Property<Jury> juryProperty() {
		return this.jury;
	}

	public final Jury getJury() {
		return this.juryProperty().getValue();
	}
	
	public final void setJury(final Jury jury) {
		this.juryProperty().setValue(jury);
	}
	
	public final Property<Correcteur> correcteurProperty() {
		return this.correcteur;
	}
	
	public final Correcteur getCorrecteur() {
		return this.correcteurProperty().getValue();
	}
	
	public final void setCorrecteur(final Correcteur correcteur) {
		this.correcteurProperty().setValue(correcteur);
	}

}
