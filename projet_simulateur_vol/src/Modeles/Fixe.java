package Modeles;

public class Fixe extends Astre{
	
	/**
	 * 
	 * @param nom -- <u>String</u> : le nom de l'astre
	 * @param posX -- <u>Double</u> : sa position en abscisse 
	 * @param posY -- <u>Double</u> : sa position en ordonnée
	 * @param masse -- <u>Double</u> : sa masse en m^3kg^−1s^−1
	 */
	public Fixe(String nom,double posX, double posY, double masse) {
		super(nom,posX,posY,masse);
	}
	
	/**
	 * 
	 * @param nom -- <u>String</u> : le nom de l'astre
	 * @param attributs -- <u>Double[]</u> : tableau de double contenant les autres paramètres nécessaires
	 */
	public Fixe(String nom, double... attributs) {
		super(nom, attributs);
	}
}
