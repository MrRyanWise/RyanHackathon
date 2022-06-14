package hackathon.view;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import jfox.javafx.view.IManagerGui;
import hackathon.commun.Roles;
import hackathon.data.Compte;
import hackathon.report.EnumReport;
import hackathon.report.ManagerReport;
import hackathon.view.systeme.ModelConnexion;

public class MenuBarAppli extends MenuBar {

	private MenuItem itemDeconnecter;
	
	private Menu menuEtats;

	@Inject
	private IManagerGui managerGui;
	@Inject
	private ManagerReport managerReport;
	@Inject
	private ModelConnexion modelConnexion;

	// Initialisation

	@PostConstruct
	public void init() {

		// Variables de travail
		Menu menu;
		MenuItem item;

		// Manu Système

		menu = new Menu("Système");
		;
		this.getMenus().add(menu);

		item = new MenuItem("Se déconnecter");
		item.setOnAction(e -> managerGui.showView(EnumView.Connexion));
		menu.getItems().add(item);
		itemDeconnecter = item;

		item = new MenuItem("Quitter");
		item.setOnAction(e -> managerGui.exit());
		menu.getItems().add(item);

		// Menu Données


		// Manu Etats
		
		menu = new Menu( "Etats" );;
		this.getMenus().add(menu);
		menuEtats = menu;
		
		item = new MenuItem( "Liste des participants (PDF)" );
		item.setOnAction( e ->
		 managerReport.openFilePdf( EnumReport.ListesParticipants, null ) );
		menu.getItems().add( item );
		
		item = new MenuItem( "Liste des participants (Viewer)" );
		item.setOnAction( e ->
		 managerReport.showViewer( EnumReport.ListesParticipants, null ) );
		menu.getItems().add( item );
		
		item = new MenuItem( "Classement (PDF)" );
		item.setOnAction( e ->
		 managerReport.openFilePdf( EnumReport.ClassementActuel, null ) );
		menu.getItems().add( item );
		
		item = new MenuItem( "Classement (Viewer)" );
		item.setOnAction( e ->
		 managerReport.showViewer( EnumReport.ClassementActuel, null ) );
		menu.getItems().add( item );
		
		item = new MenuItem( "Liste Notes (PDF)" );
		item.setOnAction( e ->
		 managerReport.openFilePdf( EnumReport.ListesNotes, null ) );
		menu.getItems().add( item );
		
		item = new MenuItem( "Liste Notes (Viewer)" );
		item.setOnAction( e ->
		 managerReport.showViewer( EnumReport.ListesNotes, null ) );
		menu.getItems().add( item );
		
		// Configuration initiale du menu
		configurerMenu(modelConnexion.getCompteActif());

		// Le changement du compte connecté modifie automatiquement le menu
		modelConnexion.compteActifProperty().addListener((obs) -> {
			Platform.runLater(() -> {
				configurerMenu(modelConnexion.getCompteActif());
				configurerView(modelConnexion.getCompteActif());
			});
		});
	}

	// Méthodes auxiliaires

	private void configurerMenu(Compte compteActif) {
		menuEtats.setVisible(false);
		itemDeconnecter.setDisable(true);
		if (compteActif != null) {
			itemDeconnecter.setDisable(false);
			menuEtats.setVisible(true);
		}
	}

	// FONCTION ECRITE POUR CONFIGURER LES DIFFERENTES VUES
	private void configurerView(Compte compteActif) {
		if (compteActif != null) {	
			if (compteActif.isInRole(Roles.ADMINISTRATEUR)) {
				
				managerGui.showView(EnumView.AccueilHackathon);
			} else if ( compteActif.isInRole(Roles.CORRECTEUR)) {
				
				managerGui.showView(EnumView.JURY);
			} else if ( compteActif.isInRole(Roles.ADMINBIS)) {
				managerGui.showView(EnumView.GestParticipants);
			}
		}
	}
}