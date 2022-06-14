package hackathon.data;


import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SuperUser {
	private final Property<Personne> personne = new SimpleObjectProperty<>();
	private final StringProperty cle = new SimpleStringProperty();
	
	// Constructeurs
	public SuperUser() {

	}

	// Getters et Setters

	public final Property<Personne> personneProperty() {
		return this.personne;
	}

	public final Personne getPersonne() {
		return this.personneProperty().getValue();
	}

	public final void setPersonne(final Personne Personne) {
		this.personneProperty().setValue(Personne);
	}

	public final StringProperty cleProperty() {
		return this.cle;
	}

	public final String getCle() {
		return this.cleProperty().get();
	}

	public final void setCle(final String cle) {
		this.cleProperty().set(cle);
	}

	// Fonctions utiles

	public void setId(int id) {
		//
	}
	
	public int getId() {
		return this.personne.getValue().getId();
	}

}
