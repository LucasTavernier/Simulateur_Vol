package Vues;

import java.io.File;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import Controleurs.FxmlControleur;
import Modeles.FileParser;
import Modeles.ThreadCalcul;
import Modeles.Univers;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.DepthTest;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
	public static String[] arguments;

	public void start(Stage primaryStage) throws Exception {
		File Filefxml = new File("res/fxml/InterfacePrincipale.fxml");
		FXMLLoader loader = new FXMLLoader(Filefxml.toURI().toURL());
		BorderPane root = (BorderPane) loader.load();

		Scene scene = new Scene(root, 1600, 900);

		Pane p = (Pane) root.getCenter();
		p.setStyle("-fx-background-image: url(\"file:res/pic/space2.png\") ; -fx-background-repeat : repeat repeat;");
		p.setOnMouseClicked(e->{
			p.requestFocus();
		});

		File f = null;
		try {
			f=new File(arguments[0]);
		}catch (NullPointerException NPE) {
			System.err.println("Aucun argument trouvé qui indiquerait le chemin du fichier astro");
			System.err.println("Veuillez lancer le programme comme ceci : java -jar Simastro.jar fichier.astro numeroPourLaMethodeDeCalcul (optionnel voir la doc)");
		}
		
		int nbMethodeCalcul = 4; //methode par défaut = rk4
		
		if(arguments.length == 2) {
			nbMethodeCalcul = Integer.parseInt(arguments[1]);
		}

		FileParser fp=new FileParser(f);
		Univers u=fp.traitementFichier();
		primaryStage.setMaximized(true);
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Simastro");
		primaryStage.show();
		
		ScheduledThreadPoolExecutor stpe = new ScheduledThreadPoolExecutor(2);
		stpe.scheduleAtFixedRate(new ThreadAffichage(p, u), 0, 40, TimeUnit.MILLISECONDS);

		stpe.scheduleAtFixedRate(new ThreadCalcul(u, nbMethodeCalcul), 0, u.getCadenceSimulation(), TimeUnit.MILLISECONDS);
	}


	public static void main(String[] args) {
		System.setProperty("file.encoding","UTF-8");
		arguments = args;
		launch(args);
	}
}
