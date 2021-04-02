package Vues;

import java.util.ArrayList;

import Controleurs.DecalageControleur;
import Controleurs.KeyboardHandler;
import Modeles.Astre;
import Modeles.Univers;
import Modeles.Vaisseau;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.Accordion;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.PathElement;
import javafx.scene.shape.Rectangle;

public class ThreadAffichage implements Runnable {

	private ImagesAstre images;
	private Traces traces;
	private Univers univers;
	private Pane pane;
	private KeyboardHandler kbh;

	private double tailleMin = 15;
	private double tailleMax = 40;

	public static double decalageX;
	public static double decalageY;
	public static double zoom;

	private BorderPane root;
	private VBox vbox;
	private Dashboard dashboard;

	private CoordonneesAdapter imgAdaptee = new CoordonneesAdapter();

	public ThreadAffichage(Pane pane, Univers univers) {
		super();
		this.univers = univers;

		this.pane = pane;
		Rectangle clip = new Rectangle();
		clip.widthProperty().bind(pane.widthProperty());
		clip.heightProperty().bind(pane.heightProperty());
		pane.setClip(clip);
		this.root = (BorderPane) pane.getParent();
		this.vbox = (VBox) root.getChildren().get(1);
		dashboard = new Dashboard(vbox, univers, images);

		decalageX = 0;
		decalageY = 0;
		zoom = 1;

		DecalageControleur ComboboxControleur = new DecalageControleur(pane, dashboard.getComboBox());

		pane.requestFocus();
		this.traces = new Traces(pane);

		Vaisseau v = univers.getVaisseau();
		if(v != null)
			this.kbh = new KeyboardHandler(pane, v);

		this.images = ImagesAstre.getInstance();
		for(Astre a : univers.getAstres()) {
			ImageView img = images.setImage(a);
			img.setOnMouseClicked(e->{
				this.dashboard.focusAstre(a);
			});
			calibrerImage(img, a);
			traces.add(a);
		}
		traces.setTraces(true);
		initialiserAffichage();
	}

	public void initialiserAffichage() {
		for(Astre a : univers.getAstres()) {
			pane.getChildren().add(images.getImage(a));
		}
	}

	public void run() {
		ArrayList<ArrayList<Astre>> astreToModif = new ArrayList<ArrayList<Astre>>();
		
		for(Astre a : univers.getAstres()) {
			if(a.isSelectedToFocusView()) {
				this.decalageX = 0-a.getPosX();
				this.decalageY = 0+a.getPosY();
			}
		}
		
		for(Astre a : univers.getAstres()) {
			ImageView img = images.getImage(a);
			imgAdaptee.setEnfant(img);
			imgAdaptee.relocate(a.getPosX()+decalageX, a.getPosY()-decalageY);
			img.setScaleX(zoom);
			img.setScaleY(zoom);
			
			if(a instanceof Vaisseau) {
				img.setRotate(((Vaisseau) a).getRotation());
			}
			this.dashboard.changeDashboard(a);
			for(Astre a2 : univers.getAstres()) {
				if(a != a2) {
					ArrayList<Astre> astreCollision = null;
					if(images.checkCollision(a, a2)) {
						astreCollision = new ArrayList<Astre>();
						Astre collisionChild = univers.collision(a, a2);
						if(this.univers.contains(collisionChild)) {
							if(collisionChild == a) {
								astreCollision.add(a);
							}
							else if(collisionChild == a2) {
								astreCollision.add(a2);
							}
						}
						else {
							astreCollision.add(collisionChild);
							astreCollision.add(a);
							astreCollision.add(a2);
						}
					}
					if(astreCollision != null) {
						astreToModif.add(astreCollision);
					}
				}
			}
		}
		for(ArrayList<Astre> lists : astreToModif){
			if(!lists.isEmpty()) {
				if(lists.size() == 1) {
					removeAstreAfterCollision(lists.get(0));
				}
				else {
					for(Astre a : lists) {
						if(univers.contains(a)) {
							removeAstreAfterCollision(a);
						}
						else {
							addAstreAfterCollision(a);
						}
					}
				}
			}
		}
		this.dashboard.changeTime();
		this.dashboard.changeObjectif();
		traces.update();
		traces.updateDecalage(decalageX, decalageY);
	}

	/**
	 * Effectue une multitude d'action afin de supprimer proprement un astre après une collision.
	 * @param a : l'astre à supprimer
	 */
	private void removeAstreAfterCollision(Astre a) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				pane.getChildren().remove(images.getImage(a));
				images.deleteAstre(a);

				pane.getChildren().remove(traces.get(a));
				traces.deleteTraces(a);

				dashboard.deleteAstreAfterCollisionToDashboard(a);
				dashboard.deleteAstreComboBox(a);

				univers.remove(a);
			}
		});
	}

	/**
	 * Effectue une multitude d'action afin d'ajouter proprement un astre après une collision.
	 * @param a : l'astre à ajouter
	 */
	private void addAstreAfterCollision(Astre a) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				images.setImage(a);
				ImageView imageA = images.getImage(a);
				imageA.setOnMouseClicked(e->{
					dashboard.focusAstre(a);
				});
				calibrerImage(imageA, a);
				//traces.add(a);
				pane.getChildren().add(images.getImage(a));

				dashboard.addAstreAfterCollisionToDashboard(a);
				dashboard.addAstreComboBox(a);

				univers.add(a);
			}
		});
	}

	/**
	 * Cette méthode viens positionner correctement par rapport au centre du Pane les images des astres
	 * @param img -> <u>ImageView</u> : une image
	 * @param a -> <u>Astre</u> : un astre
	 */
	private void calibrerImage(ImageView img, Astre a) {
		double masseAstreMax = 0;
		if(univers.getAstreMaxMasse() == null) {
			masseAstreMax = a.getMasse();
		}
		else {
			masseAstreMax = univers.getAstreMaxMasse().getMasse();
		}
		double differenceMasse = a.getMasse() / masseAstreMax;

		// Modification du translate de base des images pour qu'elles se positionnent par rapport à leur centre
		if(univers.getAstreMaxMasse() != null) {
			if(univers.getAstreMaxMasse().equals(a)) {
				img.setFitWidth(tailleMax);
				img.setFitHeight(tailleMax);
			}
			else {
				img.setFitWidth(tailleMin+(differenceMasse*tailleMin));
				img.setFitHeight(tailleMin+(differenceMasse*tailleMin));
			}
		}
		else {
			img.setFitWidth(tailleMin);
			img.setFitHeight(tailleMin);
		}
		img.setTranslateX(-1*(img.getFitWidth()/2));
		img.setTranslateY(-1*(img.getFitHeight()/2));
	}
}

