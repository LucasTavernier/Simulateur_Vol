package Controleurs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Modeles.Astre;
import Modeles.Cercle;
import Modeles.Fixe;
import Modeles.Simule;
import Modeles.Univers;
import Vues.Traces;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class FxmlControleur {
	@FXML
	BorderPane principal;

	@FXML
	Accordion dashboard;

	@FXML
	ToggleButton ButtonTraces;

	public FxmlControleur() {
	}

	public void initialize () {}

	/**
	 * Ouvre un FileChooser qui permet à l'utilisateur d'ouvrir un fichier avec une extension .astro (uniquement)
	 * 
	 */
	public void ouvrir() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Ouvrir un fichier .astro");
		fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter("Astro Files", "*.astro"));
		fileChooser.initialDirectoryProperty().setValue(new File("exemples/"));
		File selectedFile = fileChooser.showOpenDialog(null);
		if (selectedFile == null) {}
		else {
			afficherAstro(selectedFile.getPath());
		}		
	}
	public void quitter() {System.exit(0);}


	public void afficherExemple01() { afficherAstro("exemples/01_CorpsTombeSurSoleil.astro");}
	public void afficherExemple02() { afficherAstro("exemples/02_ComèteSéchappe.astro");}
	public void afficherExemple03() { afficherAstro("exemples/02_ComèteTourne.astro");}
	public void afficherExemple04() { afficherAstro("exemples/02_PlanèteTourne.astro");}
	public void afficherExemple05() { afficherAstro("exemples/02_SondeAccélère.astro");}
	public void afficherExemple06() { afficherAstro("exemples/03_DeuxPlanètes.astro");}
	public void afficherExemple07() { afficherAstro("exemples/04_ExempleDuSujet.astro");}
	public void afficherExemple08() { afficherAstro("exemples/04_ExempleCollisions.astro");}

	public void traces(){
		if(ButtonTraces.isSelected()) {
			Traces.setTraces(true);
		}
		else {
			Traces.setTraces(false);
		}
	}
	private void afficherAstro(String chemin) {
		try {
			List<String> command = new ArrayList<String>();

			command.add("java");
			command.add("-jar");
			command.add("./Simastro.jar");
			command.add("4");

			ProcessBuilder builder = new ProcessBuilder(command);		    
			Process process = builder.start();		

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
