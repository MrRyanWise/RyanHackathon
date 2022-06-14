package hackathon.data;
 
import java.util.Objects;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Equipe {
	
	//Données observables
	
	private final Property<Integer> 	idEquipe 	    = new SimpleObjectProperty<>();
	private final StringProperty 		pseudo		    = new SimpleStringProperty();
	private final Property<Integer> 	nombreMembre 	= new SimpleObjectProperty<>();
	private final StringProperty 		lienTravaux		= new SimpleStringProperty();
	
	/*
	private final Property<Jury> 		jury 			= new SimpleObjectProperty<>();
	private final Property<Hackathon> 	hackathon 		= new SimpleObjectProperty<>();
	*/
	
	// Constructeurs
	
	public Equipe() {
	}

	public Equipe( final int idEquipe, final String pseudo,final int nombreMembre, final String lienTravaux ) {
		setIdEquipe(idEquipe); 
		setPseudo(pseudo);
		setNombreMembre(nombreMembre);
		setLienTravaux(lienTravaux);
	}
	
	//Getters et Setters
 

	public final Property<Integer> idEquipeProperty() {
		return this.idEquipe;
	}
	

	public final Integer getIdEquipe() {
		return this.idEquipeProperty().getValue();
	}
	

	public final void setIdEquipe(final Integer idEquipe) {
		this.idEquipeProperty().setValue(idEquipe);
	}
	

	public final StringProperty pseudoProperty() {
		return this.pseudo;
	}
	

	public final String getPseudo() {
		return this.pseudoProperty().get();
	}
	

	public final void setPseudo(final String pseudo) {
		this.pseudoProperty().set(pseudo);
	}
	

	public final Property<Integer> nombreMembreProperty() {
		return this.nombreMembre;
	}
	

	public final Integer getNombreMembre() {
		return this.nombreMembreProperty().getValue();
	}
	

	public final void setNombreMembre(final Integer nombreMembre) {
		this.nombreMembreProperty().setValue(nombreMembre);
	}
	

	public final StringProperty lienTravauxProperty() {
		return this.lienTravaux;
	}
	

	public final String getLienTravaux() {
		return this.lienTravauxProperty().get();
	}
	

	public final void setLienTravaux(final String lienTravaux) {
		this.lienTravauxProperty().set(lienTravaux);
	}

	@Override
	public int hashCode() {
		return Objects.hash(idEquipe, lienTravaux, nombreMembre, pseudo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Equipe other = (Equipe) obj;
		return Objects.equals(idEquipe, other.idEquipe);
	}
	
	@Override
	public String toString() {
		return this.getPseudo() ;
	}
}