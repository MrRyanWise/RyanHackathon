package hackathon.data;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Admin {

	// Données observables

	private final StringProperty motDePasse = new SimpleStringProperty();
	private final StringProperty type = new SimpleStringProperty();

	private final Property<Personne> personne = new SimpleObjectProperty<>();

	// Constructeurs

	public Admin() {

	}

	// Getters et Setters

	public final StringProperty motDePasseProperty() {
		return this.motDePasse;
	}

	public final String getMotDePasse() {
		return this.motDePasseProperty().get();
	}

	public final void setMotDePasse(final String motDePasse) {
		this.motDePasseProperty().set(motDePasse);
	}

	public final StringProperty typeProperty() {
		return this.type;
	}

	public final String getType() {
		return this.typeProperty().get();
	}

	public final void setType(final String type) {
		this.typeProperty().set(type);
	}

	public final Property<Personne> personneProperty() {
		return this.personne;
	}

	public final Personne getPersonne() {
		return this.personneProperty().getValue();
	}

	public final void setPersonne(final Personne Personne) {
		this.personneProperty().setValue(Personne);
	}

	// Fonctions utiles

	public void setId(int id) {
		//
	}

	public int getId() {
		return this.personne.getValue().getId();
	}

}
