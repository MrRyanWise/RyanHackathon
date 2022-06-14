package hackathon.data;

import java.util.Objects;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Personne {

	// Données observables

	private final Property<Integer> id = new SimpleObjectProperty<>();
	private final StringProperty nom = new SimpleStringProperty();
	private final StringProperty prenom = new SimpleStringProperty();
	private final StringProperty courriel = new SimpleStringProperty();
	private final StringProperty mail = new SimpleStringProperty();
	private final Property<Compte> compte = new   SimpleObjectProperty<>();
 
	// Constructeurs
	public Personne() {
	}

	// Getters & setters
	public final Property<Integer> idProperty() {
		return this.id;
	}
	

	public final Integer getId() {
		return this.idProperty().getValue();
	}
	

	public final void setId(final Integer id) {
		this.idProperty().setValue(id);
	}
	

	public final StringProperty nomProperty() {
		return this.nom;
	}
	

	public final String getNom() {
		return this.nomProperty().get();
	}
	

	public final void setNom(final String nom) {
		this.nomProperty().set(nom);
	}
	

	public final StringProperty prenomProperty() {
		return this.prenom;
	}
	

	public final String getPrenom() {
		return this.prenomProperty().get();
	}
	

	public final void setPrenom(final String prenom) {
		this.prenomProperty().set(prenom);
	}
	

	public final StringProperty courrielProperty() {
		return this.courriel;
	}
	

	public final String getCourriel() {
		return this.courrielProperty().get();
	}
	

	public final void setCourriel(final String courriel) {
		this.courrielProperty().set(courriel);
	}
	

	public final StringProperty mailProperty() {
		return this.mail;
	}
	

	public final String getMail() {
		return this.mailProperty().get();
	}
	

	public final void setMail(final String mail) {
		this.mailProperty().set(mail);
	}
	

	public final Property<Compte> compteProperty() {
		return this.compte;
	}
	

	public final Compte getCompte() {
		return this.compteProperty().getValue();
	}
	

	public final void setCompte(final Compte compte) {
		this.compteProperty().setValue(compte);
	}
	
	// hashCode() & equals()

		@Override
		public int hashCode() {
			return Objects.hash(id.getValue());
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Personne other = (Personne) obj;
			return Objects.equals(id.getValue(), other.id.getValue());
		}
	

}
