package Modeles;

public class Cercle extends Astre{
	
	private String centre;
	/*private double posx;
	private double posy;*/
	private double periode;
	
	/**
	 * 
	 * @param nom -- <u>String</u> : le nom de l'astre
	 * @param posX -- <u>Double</u> : sa position en abscisse 
	 * @param posY -- <u>Double</u> : sa position en ordonnée
	 * @param masse -- <u>Double</u> : sa masse en m^3kg^−1s^−1
	 * @param periode -- <u>Double</u> : période de rotation de l'objet en s
	 * @param centre -- <u>String</u> : nom de l'objet au centre de la trajectoire
	 *
	 */
	public Cercle(String nom, double posX, double posY, double masse, double periode, String centre) {
		super(nom, posX, posY, masse);
		this.periode=periode;
		this.centre=centre;
		// TODO Auto-generated constructor stub
	}
	
}
