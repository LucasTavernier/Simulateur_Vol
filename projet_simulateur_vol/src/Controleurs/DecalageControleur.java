package Controleurs;

import Modeles.Astre;
import Modeles.Fixe;
import Vues.CoordonneesAdapter;
import Vues.ImagesAstre;
import Vues.ThreadAffichage;
import Vues.Traces;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class DecalageControleur {

	private Pane pane;
	private ComboBox<Astre> comboBox;
	
	private double xStart;
	private double yStart;

	/**
	 * 
	 * permet le zoom / le décalage des astres en faisant un "drag" de la souris / le centrage de la vue sur un astre en particulier
	 * 
	 * @param pane : le pane où les astres sont ( le système solaire )
	 * @param cb : la combobox permettant de centrer la vue sur un astre
	 */
	public DecalageControleur(Pane pane, ComboBox<Astre> cb) {
		this.pane = pane;
		this.comboBox = cb;
		
		pane.setOnMousePressed(event -> {
            pane.requestFocus();
            xStart = event.getSceneX();
            yStart = event.getSceneY();
            event.consume();
        });

        pane.setOnMouseDragged(event -> {
            ThreadAffichage.decalageX -= (xStart - event.getSceneX());
            ThreadAffichage.decalageY -= (yStart - event.getSceneY());
            xStart = event.getSceneX();
            yStart = event.getSceneY();
        });
        
        pane.setOnScroll(e -> {
        	ThreadAffichage.zoom += (e.getDeltaY()/(20*e.getMultiplierY()));
        });

		comboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) ->{
			if(!newValue.getNom().equals("CENTRE")) {
				oldValue.setSelectedToFocusView(false);
				newValue.setSelectedToFocusView(true);
			}
			else {
				oldValue.setSelectedToFocusView(false);
				
				ThreadAffichage.decalageX = 0;
				ThreadAffichage.decalageY = 0;
				
			}
			pane.requestFocus();
		});
	}
}