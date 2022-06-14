package hackathon.data;
 
import java.util.Objects;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Jury {
	
	//Données observables
	
	private final Property<Integer> 	idjury 			= new SimpleObjectProperty<>();
	private final StringProperty 		nomJury 		= new SimpleStringProperty();
	private final Property<Hackathon> 	hackathon 	= new SimpleObjectProperty<>();
	 
	 
	// Constructeurs
	
	public Jury() {
		
	}
	
	public Jury( final int id, final String nomJury ) {
		setId(id);
		setNomJury(nomJury);
	}
	
	
	//Getters et Setters


	public final Property<Integer> idJuryProperty() {
		return this.idjury;
	}
	


	public final Integer getIdJury() {
		return this.idJuryProperty().getValue();
	}
	


	public final void setIdJury(final Integer idJury) {
		this.idJuryProperty().setValue(idJury);
	}
	


	public final StringProperty nomJuryProperty() {
		return this.nomJury;
	}
	


	public final String getNomJury() {
		return this.nomJuryProperty().get();
	}
	


	public final void setNomJury(final String nomJury) {
		this.nomJuryProperty().set(nomJury);
	}
	


	public final Property<Hackathon> hackathonProperty() {
		return this.hackathon;
	}
	


	public final Hackathon getHackathon() {
		return this.hackathonProperty().getValue();
	}
	


	public final void setHackathon(final Hackathon hackathon) {
		this.hackathonProperty().setValue(hackathon);
	}
	
	// Fonctions utiles

	public void setId(int id) {
		//
	}
	
	public int getIdHackathon() {
		return this.hackathon.getValue().getIdHackathon();
	}
	 
		


		@Override
		public int hashCode() {
			return Objects.hash(hackathon, idjury, nomJury);
		}


		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Jury other = (Jury) obj;
			return Objects.equals(hackathon, other.hackathon) && Objects.equals(idjury, other.idjury)
					&& Objects.equals(nomJury, other.nomJury);
		}
		
		// toString()
		
				@Override
				public String toString() {
					return nomJury.getValue();
				}
}
