package hackathon.data;
 
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
 
public class Participant {
	
	//Données observables
	 
	private final Property<Personne> personne  	= new SimpleObjectProperty<>();
	private final StringProperty 	specialite 	= new SimpleStringProperty();
	private final Property<Equipe> 	equipe 		= new SimpleObjectProperty<>();
	 
	// Constructeurs
	public Participant() {
	}

	//Getters et Setters
	
	public final Property<Personne> personneProperty() {
		return this.personne;
	}
	

	public final Personne getPersonne() {
		return this.personneProperty().getValue();
	}
	

	public final void setPersonne(final Personne personne) {
		this.personneProperty().setValue(personne);
	}
	

	public final StringProperty specialiteProperty() {
		return this.specialite;
	}
	

	public final String getSpecialite() {
		return this.specialiteProperty().get();
	}
	

	public final void setSpecialite(final String specialite) {
		this.specialiteProperty().set(specialite);
	}
	

	public final Property<Equipe> equipeProperty() {
		return this.equipe;
	}
	

	public final Equipe getEquipe() {
		return this.equipeProperty().getValue();
	}
	

	public final void setEquipe(final Equipe equipe) {
		this.equipeProperty().setValue(equipe);
	}
		
}