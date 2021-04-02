package Vues;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import Controleurs.SliderDtControleur;
import Modeles.Astre;
import Modeles.Cercle;
import Modeles.Fixe;
import Modeles.ObjectifVoyager;
import Modeles.Simule;
import Modeles.Univers;
import Modeles.Vaisseau;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class Dashboard extends Accordion{

	private Univers univers;
	private long chrono;
	private Label labelChrono = new Label("Temps : " + chrono);
	private VBox parent;
	private NumberFormat nbformat = NumberFormat.getInstance();
	private ImagesAstre images;
	private ComboBox<Astre> comboBox;

	private List<Cercle> listCercle = new ArrayList<Cercle>();
	private List<Fixe> listFixe = new ArrayList<Fixe>();
	private List<Simule> listSimule = new ArrayList<Simule>();

	private TabPane tabFixe = new TabPane();
	private TabPane tabSimule = new TabPane();
	private TabPane tabCercle = new TabPane();
	private TabPane tabVaisseau = new TabPane();

	private SliderDtControleur SDtC;

	private VBox vboxObjectifs;
	private ObjectifVoyager obj;
	private int animDoneTime = 0;

	public Dashboard(VBox parent, Univers univers, ImagesAstre images) {
		this.parent = parent;
		parent.getChildren().add(this);
		this.univers = univers;
		this.chrono = 0;
		this.images = images;
		initialiserTableauDeBord();
	}

	/**
	 * En fonction des astres présent dans l'univers, cette méthode va venir changer le tableau de bord
	 * visible à l'affichage. Il crée autant de menu qu'il y a de catégorie d'astre ( Fixe / Simulé / Cercle / Ellipse / Vaisseau )
	 * Puis dans chaque menu il crée autant de fenêtre qu'il y a d'astre dans sa catégorie.
	 */
	private void initialiserTableauDeBord() {
		labelChrono.setId("chrono");
		parent.getChildren().add(labelChrono);
		parent.setAlignment(Pos.TOP_CENTER);

		panneauAstres();

		sliderForDt();

		comboBoxForView();

		if(!this.univers.getObjectifs().isEmpty()) {
			vBoxObjectifs();
		}
	}

	/**
	 * Initialise le panneau des astres où on peut voir les informations détaillés de chaque astre.
	 */
	private void panneauAstres() {
		int nbCercle = 0;
		int nbFixe = 0;
		int nbSimule = 0;

		for(Astre a : univers.getAstres()) {
			if(a instanceof Cercle) {
				nbCercle++;
				listCercle.add((Cercle) a);
			}
			else if(a instanceof Fixe) {
				nbFixe++;
				listFixe.add((Fixe) a);
			}
			else if(a instanceof Simule) {
				nbSimule++;
				listSimule.add((Simule) a);
			}
		}
		if(nbFixe > 0) {
			tabFixe.setId("tabFixe");
			for(Fixe f : listFixe) {
				createTabFixe(tabFixe, f);
			}
			TitledPane paneFixe =  new TitledPane("Fixe(s)", tabFixe);
			paneFixe.setId("titledPaneFixe");
			this.getPanes().add(paneFixe);
		}
		if(nbSimule > 0) {
			tabSimule.setId("tabSimule");
			for(Simule s : listSimule) {
				if(s instanceof Vaisseau) {}
				else {
					createTabSimule(tabSimule, s);
				}
			}
			TitledPane paneSimule =  new TitledPane("Simule(s)", tabSimule);
			paneSimule.setId("titledPaneSimule");
			this.getPanes().add(paneSimule);
		}
		if(nbCercle > 0) {
			tabCercle.setId("tabCercle");
			for(Cercle c : listCercle) {
				createTabCercle(tabCercle, c);
			}
			TitledPane paneCercle =  new TitledPane("Cercle(s)", tabCercle);
			paneCercle.setId("titledPaneCercle");
			this.getPanes().add(paneCercle);
		}
		if(univers.getVaisseau() != null) {
			Vaisseau v = univers.getVaisseau();

			createTabVaisseau(v, tabVaisseau);

			TitledPane paneVaisseau =  new TitledPane("Vaisseau", tabVaisseau);
			paneVaisseau.setId("titledPaneVaisseau");
			this.getPanes().add(paneVaisseau);
		}
	}

	/**
	 * Initialise le slider modifiant le pas de temps
	 */
	private void sliderForDt() {
		VBox vboxForDt = new VBox();
		Slider slider = new Slider(0.0001, 0.1, univers.getDt());
		slider.setOrientation(Orientation.HORIZONTAL);
		slider.setShowTickLabels(true); slider.setShowTickMarks(true);
		slider.setBlockIncrement(0.0001);

		Label labelValueSlider = new Label("Dt : " + slider.getValue());

		vboxForDt.getChildren().addAll(slider, labelValueSlider);
		vboxForDt.setAlignment(Pos.CENTER);
		parent.getChildren().add(vboxForDt);
		parent.setMargin(vboxForDt, new Insets(50, 0, 0, 0));

		SDtC = new SliderDtControleur(slider, univers, labelValueSlider);
	}

	private void createTabVaisseau(Vaisseau v, TabPane tabVaisseau) {
		VBox vBoxVaisseau = new VBox();

		Label labelPosX = new Label("Pos x : " + nbformat.format(v.getPosX()));
		labelPosX.setId("PosX");

		Label labelPosY = new Label("Pos y " + nbformat.format(v.getPosY()));
		labelPosY.setId("PosY");

		Label labelMasse = new Label("Masse : " + nbformat.format(v.getMasse()));
		labelMasse.setId("Masse");

		Label labelVitX = new Label("Vit X : " + nbformat.format(v.getVitX()));
		labelVitX.setId("VitX");

		Label labelVitY = new Label("Vit Y : " + nbformat.format(v.getVitY()));
		labelVitY.setId("VitY");

		vBoxVaisseau.setMinHeight(180);
		vBoxVaisseau.setAlignment(Pos.CENTER);
		vBoxVaisseau.getChildren().addAll(labelPosX, labelPosY, labelMasse,labelVitX,labelVitY);

		v.setVboxLabels(vBoxVaisseau);

		Tab tab = new Tab(""+v.getNom(),vBoxVaisseau);
		tab.setClosable(false);
		tabVaisseau.getTabs().add(tab);
		v.setMyTab(tab);
	}

	private void createTabCercle(TabPane tabCercle, Cercle c) {
		VBox vBoxCercle = new VBox();

		Label labelPosX = new Label("Pos x : " + nbformat.format(c.getPosX()));
		labelPosX.setId("PosX");

		Label labelPosY = new Label("Pos y " + nbformat.format(c.getPosY()));
		labelPosY.setId("PosY");

		Label labelMasse = new Label("Masse : " + nbformat.format(c.getMasse()));
		labelMasse.setId("Masse");

		vBoxCercle.setMinHeight(180);
		vBoxCercle.setAlignment(Pos.CENTER);
		vBoxCercle.getChildren().addAll(labelPosX, labelPosY, labelMasse);

		c.setVboxLabels(vBoxCercle);

		Tab tab = new Tab(""+c.getNom(),vBoxCercle);
		tab.setClosable(false);
		tabCercle.getTabs().add(tab);
		c.setMyTab(tab);
	}

	private void createTabSimule(TabPane tabSimule, Simule s) {
		VBox vBoxSimule = new VBox();

		Label labelPosX = new Label("Pos x : " + nbformat.format(s.getPosX()));
		labelPosX.setId("PosX");

		Label labelPosY = new Label("Pos y " + nbformat.format(s.getPosY()));
		labelPosY.setId("PosY");

		Label labelMasse = new Label("Masse : " + nbformat.format(s.getMasse()));
		labelMasse.setId("Masse");

		Label labelVitX = new Label("Vit X : " + nbformat.format(s.getVitX()));
		labelVitX.setId("VitX");

		Label labelVitY = new Label("Vit Y : " + nbformat.format(s.getVitY()));
		labelVitY.setId("VitY");


		vBoxSimule.setMinHeight(180);
		vBoxSimule.setAlignment(Pos.CENTER);
		vBoxSimule.getChildren().addAll(labelPosX, labelPosY, labelMasse,labelVitX,labelVitY);

		s.setVboxLabels(vBoxSimule);

		Tab tab = new Tab(""+s.getNom(),vBoxSimule);
		tab.setClosable(false);
		tabSimule.getTabs().add(tab);
		s.setMyTab(tab);
	}

	private void createTabFixe(TabPane tabFixe, Fixe f) {
		VBox vBoxFixe = new VBox();

		Label labelPosX = new Label("Pos x : " + nbformat.format(f.getPosX()));
		labelPosX.setId("PosX");

		Label labelPosY = new Label("Pos y " + nbformat.format(f.getPosY()));
		labelPosY.setId("PosY");

		Label labelMasse = new Label("Masse : " + nbformat.format(f.getMasse()));
		labelMasse.setId("Masse");

		vBoxFixe.setMinHeight(180);
		vBoxFixe.setAlignment(Pos.CENTER);
		vBoxFixe.getChildren().addAll(labelPosX, labelPosY, labelMasse);

		f.setVboxLabels(vBoxFixe);

		Tab tab = new Tab(""+f.getNom(), vBoxFixe);
		tab.setClosable(false);
		tabFixe.getTabs().add(tab);
		f.setMyTab(tab);
	}

	private void addBoutonVue(VBox vBox) {
		Image eye = new Image("file:res/pic/eye.jpg");
		ImageView eyeForButton = new ImageView(eye);
		eyeForButton.setFitHeight(40);
		eyeForButton.setFitWidth(40);
		ToggleButton boutonVue = new ToggleButton("", eyeForButton);
		vBox.getChildren().add(boutonVue);
		vBox.setMargin(boutonVue, new Insets(20, 0, 0, 0)); //margin top de 20
	}

	private void comboBoxForView() {
		comboBox = new ComboBox<Astre>();
		comboBox.getItems().add(new Fixe("CENTRE", 0, 0, 1));
		comboBox.getSelectionModel().selectFirst();
		for(Astre a : univers.getAstres()){
			comboBox.getItems().add(a);
		}

		class SimpleAstreListCell extends ListCell<Astre> { 
			@Override 
			protected void updateItem(Astre item, boolean empty) { 
				super.updateItem(item, empty); 
				setText(null); 
				if (!empty && item != null) { 
					final String text = item.getNom();
					setText(text); 
				} 
			} 
		}

		comboBox.setButtonCell(new SimpleAstreListCell());
		comboBox.setCellFactory(listView -> new SimpleAstreListCell());

		parent.getChildren().add(comboBox);
		parent.setMargin(comboBox, new Insets(20, 0, 0, 0));
	}

	/**
	 * Cette méthode vient mettre à jour le tableau de bord.
	 * @param a -- <u>Astre</u> : un astre
	 */
	public void changeDashboard(Astre a) {
		for(int i = 0 ; i < a.getVboxLabels().getChildren().size() ; ++i) {
			Label l = (Label) a.getVboxLabels().getChildren().get(i);
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					if(l.getId().equals("PosX")) {
						l.setText("");
						l.setText("Pos x : " + nbformat.format(a.getPosX()));
					}
					else if(l.getId().equals("PosY")) {
						l.setText("");
						l.setText("Pos y : " + nbformat.format(a.getPosY()));
					}
					else if(l.getId().equals("Masse")) {
						l.setText("");
						l.setText("Masse : " + nbformat.format(a.getMasse()));
					}
					if(a instanceof Simule) {
						if(l.getId().equals("VitX")) {
							Simule s = (Simule) a;
							l.setText("");
							l.setText("Vit X : " + nbformat.format(s.getVitX()));
						}
						if(l.getId().equals("VitY")) {
							Simule s = (Simule) a;
							l.setText("");
							l.setText("Vit Y : " + nbformat.format(s.getVitY()));
						}
					}
				}
			});
		}
	}

	private void vBoxObjectifs() {
		if(univers.getObjectifs().isEmpty()) {}
		else {
			obj = (ObjectifVoyager) univers.getObjectifs().get(0);


			vboxObjectifs = new VBox();
			vboxObjectifs.setBorder(new Border(new BorderStroke(Color.DARKGREY, 
					BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

			Label title = new Label("OBJECTIF");
			Label description = new Label("Vous devez sortir du système solaire (avoir une distance au soleil supérieure à " 
					+ obj.getDistance() + "), avec une vitesse cosmique de plus de 6.8 en moins de " + obj.getTlimite() + " secondes (optionnel)");
			description.setWrapText(true);
			description.setTextAlignment(TextAlignment.CENTER);

			Label labelDistance = new Label("- Distance = " + 
					nbformat.format(univers.getVaisseau().distanceVers(univers.getEtoile())));
			labelDistance.setId("distance");
			labelDistance.setWrapText(true);

			Label labelVitesse = new Label("- Vitesse = " + univers.getVaisseau().troisiemeVitesseCosmiqueValue());
			labelVitesse.setId("vitesse");
			labelVitesse.setWrapText(true);

			Label labelTemps = new Label("- Le temps est inférieur à : "+ obj.getTlimite());
			labelTemps.setId("temps");
			labelTemps.setWrapText(true);

			vboxObjectifs.getChildren().addAll(title, description, labelDistance, labelVitesse, labelTemps);
			vboxObjectifs.setMargin(title, new Insets(20, 0, 0, 50));
			vboxObjectifs.setMargin(labelDistance, new Insets(5, 0, 0, 0));
			vboxObjectifs.setMargin(labelVitesse, new Insets(5, 0, 0, 0));
			vboxObjectifs.setMargin(labelTemps, new Insets(5, 0, 0, 0));

			parent.getChildren().add(vboxObjectifs);
			parent.setMargin(vboxObjectifs, new Insets(20, 0, 0, 0));
		}
	}

	/**
	 * modifie les informations de l'objectif
	 */
	public void changeObjectif() {
		if(univers.getObjectifs().isEmpty()) {}
		else {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					Label labelDistance = null;
					Label labelVitesse = null;
					Label labelTemps = null;
					for(Node child : vboxObjectifs.getChildren()) {
						if(child.getId() != null) {
							switch (child.getId()) {
							case "distance":
								labelDistance = (Label) child;
								break;
							case "vitesse":
								labelVitesse = (Label) child;
								break;
							case "temps":
								labelTemps = (Label) child;
								break;
							}
						}
					}

					if(univers.getTempsEcoule() < obj.getTlimite()) {
						labelTemps.setTextFill(Color.GREEN);
					}
					else {
						labelTemps.setTextFill(Color.RED);
					}

					if(univers.getVaisseau().distanceVers(univers.getEtoile()) > obj.getDistance()) {
						labelDistance.setTextFill(Color.GREEN);
					}
					else {
						labelDistance.setTextFill(Color.RED);
					}

					if(univers.getVaisseau().troisiemeVitesseCosmiqueValue() > 6.8) {
						labelVitesse.setTextFill(Color.GREEN);
					}
					else {
						labelVitesse.setTextFill(Color.RED);
					}

					if(animDoneTime < 1 ) {
						labelDistance.setText(("- Distance = " + nbformat.format(univers.getVaisseau().distanceVers(univers.getEtoile()))));
						labelVitesse.setText("- Vitesse = " + univers.getVaisseau().troisiemeVitesseCosmiqueValue());
					}

					if(labelTemps.getTextFill() == Color.GREEN && labelDistance.getTextFill() == Color.GREEN && labelVitesse.getTextFill() == Color.GREEN && animDoneTime < 1) {
						animDoneTime++;
						Timeline  timeline = new Timeline();
						KeyValue keyBackgroundGreen = new KeyValue(vboxObjectifs.backgroundProperty(), new Background(
								new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
						KeyValue keyBackgroundNormal = new KeyValue(vboxObjectifs.backgroundProperty(), null);
						timeline.getKeyFrames().addAll(
								new KeyFrame(new Duration(250), keyBackgroundGreen),
								new KeyFrame(new Duration(400), keyBackgroundNormal),
								new KeyFrame(new Duration(550), keyBackgroundGreen),
								new KeyFrame(new Duration(800), keyBackgroundNormal),
								new KeyFrame(new Duration(1000), keyBackgroundGreen)
								);
						timeline.play();
					}
				}
			});
		}
	}

	/**
	 * Change le temps sur le tableau de bord
	 */
	public void changeTime() {
		Label labelTime = this.labelChrono;
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				String temps = NumberFormat.getInstance().format(univers.getTempsEcoule());
				//double tps = Double.parseDouble(temps);
				double tps = univers.getTempsEcoule();

				int day = (int) (tps/(3600*24));
				int hour=Dashboard.mod((int)(tps/3600),60);
				int min=Dashboard.mod((int)(tps/60),60);
				int sec=Dashboard.mod((int)tps,60);


				String hourS = (hour<10) ? "0"+hour : ""+hour;
				String minS = (min<10) ? "0"+min : ""+min;
				String secS = (sec<10) ? "0"+sec : ""+sec;

				if(day==0 && hour==0 && min==0) temps=secS+"s";
				else if(day==0 && hour==0) temps=minS+"m"+secS+"s";
				else if(day==0) temps=hourS+"h"+minS+"m"+secS+"s";
				else temps=day+"d"+hourS+"h"+minS+"m"+secS+"s";


				labelTime.setText("Temps : "+temps);
				//labelTime.setText("Temps : " + NumberFormat.getInstance().format(univers.getTempsEcoule()) + "s");
			}
		});
	}

	static int mod(int a, int b) {
		int tmp=a%b;
		if(tmp<0) tmp+=b;
		return tmp;
	}

	public void focusAstre(Astre a) {
		TitledPane titledpaneAstre = null;
		for(TitledPane tp : this.getPanes()) {
			if(tp.getText().contains(a.getClass().getSimpleName())) {
				titledpaneAstre = tp;
			}
		}

		if(titledpaneAstre != null) {
			titledpaneAstre.setExpanded(true);
			TabPane tabPane = a.getMyTab().getTabPane();
			SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();

			selectionModel.select(a.getMyTab());

			Timeline  timeline = new Timeline();
			KeyValue keyBorderRed = new KeyValue(a.getMyTab().getTabPane().borderProperty(), new Border(new BorderStroke(Color.RED, 
					BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
			KeyValue keyBorderNull = new KeyValue(a.getMyTab().getTabPane().borderProperty(), null);
			timeline.getKeyFrames().addAll(
					new KeyFrame(new Duration(250), keyBorderRed),
					new KeyFrame(new Duration(400), keyBorderNull),
					new KeyFrame(new Duration(550), keyBorderRed),
					new KeyFrame(new Duration(800), keyBorderNull)
					);
			timeline.play();
		}
	}

	public void deleteAstreAfterCollisionToDashboard(Astre a) {
		a.getMyTab().getTabPane().getTabs().remove(a.getMyTab());
	}

	public void addAstreAfterCollisionToDashboard(Astre a) {
		if(a instanceof Cercle) {
			createTabCercle(tabCercle, (Cercle) a);
		}
		else if(a instanceof Fixe) {
			createTabFixe(tabFixe, (Fixe) a);
		}
		else if(a instanceof Simule) {
			createTabSimule(tabSimule, (Simule) a);
		}
		else if(a instanceof Vaisseau) {
			createTabVaisseau((Vaisseau) a, tabVaisseau);
		}
	}

	public ComboBox<Astre> getComboBox() {
		return comboBox;
	}

	public void deleteAstreComboBox(Astre a) {
		this.comboBox.getItems().remove(a);
	}

	public void addAstreComboBox(Astre a) {
		this.comboBox.getItems().add(a);
	}

}

