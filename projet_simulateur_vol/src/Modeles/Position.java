package Modeles;

public class Position {
	private double x;
	private double y;
	
	/**
	 * 
	 * @param x -- <u>Double</u> : coordonnée en abscisse
	 * @param y -- <u>Double</u> : coordonnée en ordonnée
	 */
	public Position(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Renvoie la valeur de la coordonnée en abscisse de cette position
	 * @return <u>Double</u> : l'abscisse
	 */
	public double getX() {
		return this.x;
	}
	/**
	 * Renvoie la valeur de la coordonnée en ordonnée de cette position
	 * @return <u>Double</u> : l'ordonnée
	 */
	public double getY() {
		return this.y;
	}

	/**
	 * Attribue une valeur en abscisse à la position
	 * @param x -- <u>Double</u> : la valeur de la coordonnée en abscisse 
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * Attribue une valeur en ordonnée à la position
	 * @param y -- <u>Double</u> : la valeur de la coordonnée en ordonnée
	 */
	public void setY(double y) {
		this.y = y;
	}
	
	public String toString() {
		return "x : " + this.x + "; y : " + this.y;
	}
}
