package Modeles;

import java.util.HashMap;
import java.util.List;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;

public abstract class Astre {
	
	private String nom;
	private Position position;
	private HashMap<Double, Position> mesPositions = new HashMap<>();
	private double masse;
	private VBox vboxLabels = null;
	private Tab myTab = null;
	private boolean selectedToFocusView;

	/**
	 * 
	 * @param nom -- <u>String</u> : le nom de l'astre
	 * @param posX -- <u>Double</u> : sa position en abscisse 
	 * @param posY -- <u>Double</u> : sa position en ordonnée
	 * @param masse -- <u>Double</u> : sa masse en m^3kg^−1s^−1
	 */
	public Astre(String nom, double posX, double posY, double masse) {
		this.nom = nom;
		this.position = new Position(posX, posY);
		this.masse = masse;
		this.selectedToFocusView = false;
	}
	
	/**
	 * 
	 * @param nom -- <u>String</u> : le nom de l'astre
	 * @param attributs -- <u>Double[]</u> : tableau de double contenant les autres paramètres nécessaires
	 */
	public Astre(String nom, double... attributs) {
		this.nom = nom;
		this.position = new Position(attributs[0], attributs[1]);
		this.masse = attributs[2];
		this.selectedToFocusView = false;
	}

	/**
	 * Renvoie le nom de l'astre
	 * @return <u>String</u> : Le nom de l'astre
	 */
	public String getNom() {
		return nom;
	}
	
	/**
	 * Renvoie la position en abscisse
	 * @return <u>Double</u> : La position sur l'axe x de l'astre
	 */
	public double getPosX() {
		return position.getX();
	}

	/**
	 * Renvoie la position en ordonnée
	 * @return <u>Double</u> : La position sur l'axe y de l'astre
	 */
	public double getPosY() {
		return position.getY();
	}
	
	/**
	 * Change la position en abscisse de l'astre
	 * @param x -- <u>Double</u> : La nouvelle position en abscisse
	 */
	public void setX(double x) {
		position.setX(x);
	}
	
	/**
	 * Change la position en ordonnée de l'astre
	 * @param y -- <u>Double</u> : La nouvelle position en ordonnée
	 */
	public void setY(double y) {
		position.setY(y);
	}
	
	/**
	 * Renvoie la masse
	 * @return <u>Double</u> : La masse
	 */
	public double getMasse() {
		return masse;
	}
	
	/**
	 * Change la masse de l'astre
	 * @param masse -- <u>Double</u> : La nouvelle masse
	 */
	public void setMasse(double masse) {
		this.masse = masse;
	}

	/**
	 * @see Position
	 * @return <u>List de Position</u> : Une liste de position
	 */
	public List<Position> getMesPositions() {
		return (List<Position>) mesPositions.values();
	}
	
	/**
	 * 
	 * @param index : le temps auquel on souhaite la position
	 * @return <u>Position</u> : Une position à un temps demandé
	 * @see Position
	 */
	public Position getMaPositionEn(double index) {
		return mesPositions.get(index);
	}
	
	/**
	 * 
	 * @param index : le temps auquel on souhaite la position
	 * @return <u>Double</u> : La valeur de la position en abscisse à un temps demandé
	 */
	public double getMaPositionXEn(double index) {
		return mesPositions.get(index).getX();
	}
	
	/**
	 * 
	 * @param index : le temps auquel on souhaite la position
	 * @return <u>Double</u> : La valeur de la position en ordonnée à un temps demandé
	 */
	public double getMaPositionYEn(double index) {
		return mesPositions.get(index).getY();
	}

	/**
	 * 
	 * @param temps : Le temps auquel on souhaite changer / ajouter une position
	 * @param pos : La position qu'on souhaite donner à un certain temps à l'astre
	 */
	public void setMaPositionEn(double temps, Position pos) {
		this.mesPositions.put(temps, pos);
	}

	public String toString() {
		return "Pos x : " + this.getPosX() + ", Pos y : " + this.getPosY() + ", Masse : " + masse;
	}
	
	/**
	 * Chaque astre possède une VBox qui contient des labels contenant les valeurs de l'astre comme sa masse, sa position etc
	 * @return <u>VBox</u> : La VBox cité ci-dessus
	 * @see VBox
	 */
	public VBox getVboxLabels() {
		return vboxLabels;
	}
	
	/**
	 * 
	 * @param vboxLabels : une VBox contenant des labels
	 * @see Astre#getVboxLabels()
	 */
	public void setVboxLabels(VBox vboxLabels) {
		this.vboxLabels = vboxLabels;
	}
	
	/**
	 * Dans le tableau de bord il y a un "Tab" associé à chaque astre, les getters / setters de ce Tab facilite l'ouverture de ce Tab lors d'un
	 * clic sur l'astre
	 * @return <u>Tab</u> : Le Tab de l'astre
	 * @see Tab
	 */
	public Tab getMyTab() {
		return myTab;
	}
	
	/**
	 * 
	 * @param myTab : un Tab
	 * @see Astre#getMyTab()
	 */
	public void setMyTab(Tab myTab) {
		this.myTab = myTab;
	}

	/**
	 * 
	 * @return vrai si l'astre est celui choisi pour le centrage de la vue
	 */
	public boolean isSelectedToFocusView() {
		return selectedToFocusView;
	}

	/**
	 * 
	 * @param selectedToFocusView : le choix pour le centrage de la vue sur cet astre
	 */
	public void setSelectedToFocusView(boolean selectedToFocusView) {
		this.selectedToFocusView = selectedToFocusView;
	}
	
	/**
	 * 
	 * @param a : l'astre auquel on souhaite mesurer la distance avec celui-ci
	 * @return <u>Double</u> : La distance entre cet astre et l'astre a
	 */
	public double distanceVers(Astre a) {
		return Math.sqrt(Math.pow(this.getPosX() - a.getPosX(), 2) + Math.pow(this.getPosY() - a.getPosY(), 2));
	}
}
