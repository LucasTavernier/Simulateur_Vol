package Modeles;

public class Simule extends Astre {
	
	private double vitX;
	private double vitY;

	/**
	 * 
	 * @param nom -- <u>String</u> : le nom de l'astre
	 * @param posX -- <u>Double</u> : sa position en abscisse 
	 * @param posY -- <u>Double</u> : sa position en ordonnée
	 * @param vitX -- <u>Double</u> : sa vitesse en abscisse
	 * @param vitY -- <u>Double</u> : sa vitesse en ordonnée
	 * @param masse -- <u>Double</u> : sa masse en m^3kg^−1s^−1
	 */
	public Simule(String nom,double posX, double posY, double vitX, double vitY, double masse) {
		super(nom,posX,posY,masse);
		this.vitX = vitX;
		this.vitY = vitY;
	}
	
	/**
	 * 
	 * @param nom -- <u>String</u> : le nom de l'astre
	 * @param attributs -- <u>Double[]</u> : tableau de double contenant les autres paramètres nécessaires
	 */
	public Simule(String nom, double... attributs) {
		super(nom, attributs);
		this.vitX = attributs[3];
		this.vitY = attributs[4];
	}

	/**
	 * Renvoie la valeur de la vitesse en abscisse de cette position
	 * @return <u>Double</u> : la vitesse en abscisse
	 */
	public double getVitX() {
		return vitX;
	}

	/**
	 * Renvoie la valeur de la vitesse en ordonnée de cette position
	 * @return <u>Double</u> : la vitesse en ordonnée
	 */
	public double getVitY() {
		return vitY;
	}
	
	/**
	 * Change la vitesse en abscisse de l'astre
	 * @param vitX -- <u>Double</u> : La nouvelle vitesse en abscisse
	 */
	public void setVitX(double vitX) {
		this.vitX = vitX;
	}

	/**
	 * Change la vitesse en abscisse de l'astre
	 * @param vitY -- <u>Double</u> : La nouvelle vitesse en abscisse
	 */
	public void setVitY(double vitY) {
		this.vitY = vitY;
	}

	public String toString() {
		String res = super.toString();
		
		res += ", Vit x : " + this.vitX + ", Vit y : " + this.vitY;
		return res;
	}
	
	/**
	 * 
	 * @return Vrai si l'astre a une vitesse cosmique supérieure à 6.8
	 * @see #troisiemeVitesseCosmiqueValue() Voir le calcul
	 */
	public boolean troisiemeVitesseCosmique() {
		return this.troisiemeVitesseCosmiqueValue() > 6.8;
	}
	
	/**
	 * Raciné carrée de la somme de la vitesse carré en abscisse et de la vitesse carré en ordonnée : 
	 * Math.sqrt(this.vitX * this.vitX + this.vitY * this.vitY);
	 * @return <u>Double</u> : la vitesse cosmique de l'astre
	 */
	public double troisiemeVitesseCosmiqueValue() {
		return Math.sqrt(this.vitX * this.vitX + this.vitY * this.vitY);
	}
}
