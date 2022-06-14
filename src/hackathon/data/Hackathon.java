package hackathon.data;

import java.time.LocalDate;
import java.util.Date;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Hackathon {
	
	//Données observables
	
	private final Property<Integer> 	idHackathon 	= new SimpleObjectProperty<>();
	private final StringProperty 		nom 			= new SimpleStringProperty();
	private final StringProperty 		theme 			= new SimpleStringProperty();
	private final StringProperty 		problematique 	= new SimpleStringProperty();
	private final StringProperty 		description 	= new SimpleStringProperty();
	private final StringProperty 		lieu 			= new SimpleStringProperty();
	private final Property<Integer> 	nbJury 			= new SimpleObjectProperty<>();
	private final Property<Integer> 	minEquipe 		= new SimpleObjectProperty<>();
	private final Property<Integer> 	maxEquipe 		= new SimpleObjectProperty<>();
	private final Property<LocalDate>    	dateDebut 		= new SimpleObjectProperty<>();
	private final Property<LocalDate>    	dateFin 		= new SimpleObjectProperty<>();
	
	private final Property<Date>    	heureDebut		= new SimpleObjectProperty<>();
	private final Property<Date>    	heureFin		= new SimpleObjectProperty<>();
	
	private final Property<SuperUser> 	superUser 	= new SimpleObjectProperty<>();
	
	 
	// Constructeurs
	
	public Hackathon() {
	}
	
	//Getters et Setters
	
	public final Property<Integer> idHackathonProperty() {
		return this.idHackathon;
	}
	

	public final Integer getIdHackathon() {
		return this.idHackathonProperty().getValue();
	}
	

	public final void setIdHackathon(final Integer idHackathon) {
		this.idHackathonProperty().setValue(idHackathon);
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
	

	public final StringProperty themeProperty() {
		return this.theme;
	}
	

	public final String getTheme() {
		return this.themeProperty().get();
	}
	

	public final void setTheme(final String theme) {
		this.themeProperty().set(theme);
	}
	

	public final StringProperty problematiqueProperty() {
		return this.problematique;
	}
	

	public final String getProblematique() {
		return this.problematiqueProperty().get();
	}
	

	public final void setProblematique(final String problematique) {
		this.problematiqueProperty().set(problematique);
	}
	

	public final StringProperty descriptionProperty() {
		return this.description;
	}
	

	public final String getDescription() {
		return this.descriptionProperty().get();
	}
	

	public final void setDescription(final String description) {
		this.descriptionProperty().set(description);
	}
	

	public final StringProperty lieuProperty() {
		return this.lieu;
	}
	

	public final String getLieu() {
		return this.lieuProperty().get();
	}
	

	public final void setLieu(final String lieu) {
		this.lieuProperty().set(lieu);
	}
	

	public final Property<Integer> nbJuryProperty() {
		return this.nbJury;
	}
	

	public final Integer getNbJury() {
		return this.nbJuryProperty().getValue();
	}
	

	public final void setNbJury(final Integer nbJury) {
		this.nbJuryProperty().setValue(nbJury);
	}
	

	public final Property<Integer> minEquipeProperty() {
		return this.minEquipe;
	}
	

	public final Integer getMinEquipe() {
		return this.minEquipeProperty().getValue();
	}
	

	public final void setMinEquipe(final Integer minEquipe) {
		this.minEquipeProperty().setValue(minEquipe);
	}
	

	public final Property<Integer> maxEquipeProperty() {
		return this.maxEquipe;
	}
	

	public final Integer getMaxEquipe() {
		return this.maxEquipeProperty().getValue();
	}
	

	public final void setMaxEquipe(final Integer maxEquipe) {
		this.maxEquipeProperty().setValue(maxEquipe);
	}
	

	public final Property<LocalDate> dateDebutProperty() {
		return this.dateDebut;
	}
	

	public final LocalDate getDateDebut() {
		return this.dateDebutProperty().getValue();
	}
	

	public final void setDateDebut(final LocalDate dateDebut) {
		this.dateDebutProperty().setValue(dateDebut);
	}
	

	public final Property<LocalDate> dateFinProperty() {
		return this.dateFin;
	}
	

	public final LocalDate getDateFin() {
		return this.dateFinProperty().getValue();
	}
	

	public final void setDateFin(final LocalDate dateFin) {
		this.dateFinProperty().setValue(dateFin);
	}
	

	public final Property<Date> heureDebutProperty() {
		return this.heureDebut;
	}
	

	public final Date getHeureDebut() {
		return this.heureDebutProperty().getValue();
	}
	

	public final void setHeureDebut(final Date heureDebut) {
		this.heureDebutProperty().setValue(heureDebut);
	}
	

	public final Property<Date> heureFinProperty() {
		return this.heureFin;
	}
	

	public final Date getHeureFin() {
		return this.heureFinProperty().getValue();
	}
	

	public final void setHeureFin(final Date heureFin) {
		this.heureFinProperty().setValue(heureFin);
	}

	public final Property<SuperUser> superUserProperty() {
		return this.superUser;
	}
	
	public final SuperUser getSuperUser() {
		return this.superUserProperty().getValue();
	}
	
	public final void setSuperUser(final SuperUser superUser) {
		this.superUserProperty().setValue(superUser);
	}
		
}
