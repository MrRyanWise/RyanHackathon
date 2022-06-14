package hackathon.data;


import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;

public class Intervenir {
	private final Property<Hackathon> hackathon	 = new SimpleObjectProperty<>();
	private final Property<Personne> personne	 = new SimpleObjectProperty<>();
	
	// Constructeurs
	public Intervenir() {

	}
	
	// Getters et Setters

	public final Property<Hackathon> hackathonProperty() {
		return this.hackathon;
	}
	

	public final Hackathon getHackathon() {
		return this.hackathonProperty().getValue();
	}
	

	public final void setHackathon(final Hackathon hackathon) {
		this.hackathonProperty().setValue(hackathon);
	}
	

	public final Property<Personne> personneProperty() {
		return this.personne;
	}
	

	public final Personne getPersonne() {
		return this.personneProperty().getValue();
	}
	

	public final void setPersonne(final Personne personne) {
		this.personneProperty().setValue(personne);
	}
	

	
 
	
	
	

}