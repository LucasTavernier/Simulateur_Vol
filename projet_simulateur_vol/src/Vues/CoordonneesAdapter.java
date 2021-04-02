package Vues;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.PathElement;

public class CoordonneesAdapter {
	private Pane parent;
	private Node enfant;
	
	private PathElement enfantTrace;
	
	public CoordonneesAdapter() {
		this.enfant = null;
		this.parent = null;
		this.enfantTrace = null;
	}
	
	public CoordonneesAdapter(Node enfant) {
		this.enfant = enfant;
		this.parent = (Pane) enfant.getParent();
	}
	
	public CoordonneesAdapter(MoveTo debut) {
		
	}
	
	/**
	 * 
	 * @param x -- <u>Double</u> : La position en abscisse 
	 * @param y -- <u>Double</u> : La position en ordonnée 
	 */
	public void relocate(double x, double y) {
		double xReel = x + (parent.getWidth() / 2.0);
		double yReel = -y + (parent.getHeight() / 2.0);
		enfant.relocate(xReel, yReel);
	}
	
	public void setEnfant(Node enfant) {
		this.enfant = enfant;
		this.parent = (Pane) enfant.getParent();
	}
	
	public void setEnfant(PathElement enfantTrace) {
		this.enfantTrace = enfantTrace;
		this.parent = (Pane) enfant.getParent();
	}
}
