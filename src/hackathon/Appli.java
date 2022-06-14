package hackathon;

import javax.sql.DataSource;

import org.mapstruct.factory.Mappers;

import hackathon.commun.IMapper;
import hackathon.view.ManagerGui;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import jfox.context.ContextGlobal;
import jfox.context.IContext;
import jfox.javafx.util.UtilFX;
import jfox.jdbc.DataSourceSingleConnection;

public class Appli extends Application {

	// Champs

	private IContext context;

	// Actions

	@Override
	public final void start(Stage stagePrimary) {

		try {

			// JDBC - DataSource
			DataSource dataSource = new DataSourceSingleConnection("jdbc.properties");

			// Context
			context = new ContextGlobal();
			context.addBean(dataSource);
			context.addBean(Mappers.getMapper(IMapper.class));

			// ManagerGui
			ManagerGui managerGui = context.getBean(ManagerGui.class);
			managerGui.setFactoryController(context::getBeanNew);
			managerGui.setStage(stagePrimary);
			managerGui.configureStage();

			// Affiche le stage
			stagePrimary.setResizable(false);

			stagePrimary.show();

		} catch (Exception e) {
			UtilFX.unwrapException(e).printStackTrace();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Impossible de démarrer l'application.");
			alert.showAndWait();
			Platform.exit();
		}
	}

	@Override
	public final void stop() throws Exception {
		if (context != null) {
			context.close();
		}
	}

	// Classe auxiliaire

	public static class MainHackathon {
		public static void main(String[] args) {
			Application.launch(Appli.class, args);
		}
	}
}