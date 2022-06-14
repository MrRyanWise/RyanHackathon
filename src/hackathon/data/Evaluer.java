package hackathon.data;

import javafx.beans.property.Property; 
import javafx.beans.property.SimpleObjectProperty; 

public class Evaluer {
	private final Property<Equipe> equipe = new SimpleObjectProperty<>();
	private final Property<Correcteur> correcteur = new SimpleObjectProperty<>();
	private final Property<Integer> note =  new SimpleObjectProperty<>();
	
	
	// Constructeurs
	public Evaluer() {
	}
 
	// Getters et Setters 
	public final Property<Equipe> equipeProperty() {
		return this.equipe;
	}
	public final Equipe getEquipe() {
		return this.equipeProperty().getValue();
	}
	public final void setEquipe(final Equipe equipe) {
		this.equipeProperty().setValue(equipe);
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
	public final Property<Integer> noteProperty() {
		return this.note;
	}
	

	public final Integer getNote() {
		return this.noteProperty().getValue();
	}
	

	public final void setNote(final Integer note) {
		this.noteProperty().setValue(note);
	}
	 
	
	
 
	// Fonctions utiles

	public void setIdCorrecteur(int id) {
		//
	}
	public void setIdEquipe(int id) {
		//
	}

	public int getIdCorrecteur() {
		return this.correcteur.getValue().getId();
	}
	
	public int getIdEquipe() {
		return this.equipe.getValue().getIdEquipe();
	}	

	
	
} 