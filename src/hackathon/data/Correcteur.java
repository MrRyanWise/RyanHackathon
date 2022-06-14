package hackathon.data;
 
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Correcteur {
	
	//Données observables
	private final StringProperty 		motDePasse 		= new SimpleStringProperty();
	private final StringProperty 		specialite 		= new SimpleStringProperty();
	private final Property<Personne> 	personne 	= new SimpleObjectProperty<>();
	 
	// Constructeurs
	public Correcteur() {
	}
	
	//Getters et Setters
	
	public final StringProperty motDePasseProperty() {
		return this.motDePasse;
	}
	
	public final String getMotDePasse() {
		return this.motDePasseProperty().get();
	}
	
	public final void setMotDePasse(final String motDePasse) {
		this.motDePasseProperty().set(motDePasse);
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