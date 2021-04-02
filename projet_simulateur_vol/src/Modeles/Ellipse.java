package Modeles;

public class Ellipse extends Astre{


	private String f1;
	private String f2;
	private double periode;
	
	/**
	 * 
	 * @param nom -- <u>String</u> : nom de l'astre
	 * @param posX -- <u>Double</u> : sa position en abscisse
	 * @param posY -- <u>Double</u> : sa position en ordoonée
	 * @param masse -- <u>Double</u> : sa masse
	 * @param f1 -- <u>String</u> : nom du premier foyer de l'ellipse
	 * @param f2 -- <u>String</u> : nom du second foyer de l'ellipse
	 * @param periode -- <u>Double</u> : periode de rotation de l'astre en s
	 */
	public Ellipse(String nom, double posX, double posY, double masse, String f1, String f2, double periode) {
		super(nom, posX, posY, masse);
		this.f1=f1;
		this.f2=f2;
		this.periode=periode;
	}
	
}
