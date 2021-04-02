package Modeles;

public class Vaisseau extends Simule {
	
	private final double FORCEPRINCIPAL;
	private final double FORCEAVANT;
	private final double FORCEDROITE = 0.00005;
	private final double FORCEGAUCHE = 0.00005;
	
	private double pprincipal; //poussée du propulseur principal
	private double pretroAvant; //poussée des rétro-propulseurs
	private double pdroit; //poussée du propulseur droit
	private double pgauche; //poussée du propulseur gauche
	
	private boolean principal;
	private boolean retroAvant;
	private boolean retroGauche;
	private boolean retroDroit;
	
	private double rotation;
	
	/**
	 * 
	 * @param nom -- <u>String</u> : le nom de l'astre
	 * @param posX -- <u>Double</u> : sa position en abscisse 
	 * @param posY -- <u>Double</u> : sa position en ordonnée
	 * @param vitX -- <u>Double</u> : sa vitesse en abscisse
	 * @param vitY -- <u>Double</u> : sa vitesse en ordonnée
	 * @param masse -- <u>Double</u> : sa masse en m^3kg^−1s^−1
	 * @param pprincipal -- <u>Double</u> : la valeur de propulsion du propulseur arrière
	 * @param pretro -- <u>Double</u> : la valeur de propulsion du propulseur arrière
	 */
	public Vaisseau(String nom,double posX, double posY, double vitX, double vitY, double masse, double pprincipal, double pretro) {
		super(nom,posX, posY, vitX, vitY, masse);
		this.FORCEPRINCIPAL=pprincipal;
		this.FORCEAVANT=pretro;	
		this.rotation = 0;
		this.principal = false;
		this.retroAvant = false;
		this.retroGauche = false;
		this.retroDroit = false;
	}
	
	/**
	 * 
	 * @param nom -- <u>String</u> : le nom de l'astre
	 * @param attributs -- <u>Double[]</u> : tableau de double contenant les autres paramètres nécessaires
	 */
	public Vaisseau(String nom, double... attributs) {
		super(nom, attributs);
		this.rotation = 0;
		this.FORCEPRINCIPAL=attributs[5];
		this.FORCEAVANT=attributs[6];
	}

	/**
	 * 
	 * @return <u>Boolean</u> : indique si le propulseur arrière est activé
	 */
	public boolean isPrincipal() {
		return principal;
	}

	/**
	 * 
	 * @param principal -- <u>Boolean</u> : Active / Désactive le propulseur arrière
	 */
	public void setPrincipal(boolean principal) {
		this.principal = principal;
	}
	
	/**
	 * 
	 * @param pprincipal -- <u>Double</u> : la valeur de propulsion du propulseur arrière
	 */
	public void setValuePrincipal(double pprincipal) {
		this.pprincipal = pprincipal;
	}
	
	/**
	 * Renvoie la valeur de propulsion du propulseur arrière
	 * @return <u>Double</u> : la valeur de propulsion du propulseur arrière
	 */
	public double getValuePrincipal() {
		return this.pprincipal;
	}

	/**
	 * 
	 * @return <u>Boolean</u> : indique si le propulseur avant est activé
	 */
	public boolean isRetroAvant() {
		return retroAvant;
	}
	
	/**
	 * 
	 * @param retroAvant -- <u>Boolean</u> : Active / Désactive le propulseur avant
	 */
	public void setRetroAvant(boolean retroAvant) {
		this.retroAvant = retroAvant;
	}
	
	/**
	 * 
	 * @param retroAvant -- <u>Double</u> : la valeur de propulsion du propulseur avant
	 */
	public void setValueRetroAvant(double retroAvant) {
		this.pretroAvant = retroAvant;
	}
	
	/**
	 * Renvoie la valeur de propulsion du propulseur avant
	 * @return <u>Double</u> : la valeur de propulsion du propulseur avant
	 */
	public double getValueRetroAvant() {
		return this.pretroAvant;
	}

	/**
	 * 
	 * @return <u>Boolean</u> : indique si le propulseur gauche est activé
	 */
	public boolean isRetroGauche() {
		return retroGauche;
	}

	/**
	 * 
	 * @param retroGauche -- <u>Boolean</u> : Active / Désactive le propulseur gauche
	 */
	public void setRetroGauche(boolean retroGauche) {
		this.retroGauche = retroGauche;
	}
	
	/**
	 * 
	 * @param retroGauche -- <u>Double</u> : la valeur de propulsion du propulseur gauche
	 */
	public void setValueRetroGauche(double retroGauche) {
		this.pgauche = retroGauche;
	}
	
	/**
	 * Renvoie la valeur de propulsion du propulseur avant
	 * @return <u>Double</u> : la valeur de propulsion du propulseur gauche
	 */
	public double getValueRetroGauche() {
		return this.pgauche;
	}

	/**
	 * 
	 * @return <u>Boolean</u> : indique si le propulseur droit est activé
	 */
	public boolean isRetroDroit() {
		return retroDroit;
	}

	/**
	 * 
	 * @param retroDroit -- <u>Boolean</u> : Active / Désactive le propulseur droit
	 */
	public void setRetroDroit(boolean retroDroit) {
		this.retroDroit = retroDroit;
	}
	
	/**
	 * 
	 * @param retroDroit -- <u>Double</u> : la valeur de propulsion du propulseur droit
	 */
	public void setValueRetroDroit(double retroDroit) {
		this.pdroit = retroDroit;
	}
	
	/**
	 * Renvoie la valeur de propulsion du propulseur avant
	 * @return <u>Double</u> : la valeur de propulsion du propulseur droit
	 */
	public double getValueRetroDroit() {
		return this.pdroit;
	}

	/**
	 * Renvoie la rotation du vaisseau
	 * @return <u>Double</u> : la rotation du vaisseau
	 */
	public double getRotation() {
		return rotation;
	}

	/**
	 * Change la rotation du vaisseau
	 * @param rotation -- <u>Double</u> : la nouvelle rotation du vaisseau
	 */
	public void setRotation(double rotation) {
		this.rotation = rotation;
	}
	
	
	public double getFORCEPRINCIPAL() {
		return FORCEPRINCIPAL;
	}

	public double getFORCEAVANT() {
		return FORCEAVANT;
	}
	
	public double getFORCEGAUCHE() {
		return FORCEGAUCHE;
	}

	public double getFORCEDROIT() {
		return FORCEDROITE;
	}

	public String toString() {
		//return "Principal : " + isPrincipal() + " Retro Avant : " + isRetroAvant() + " Retro droit : " + isRetroDroit() + " Retro gauche : " + isRetroGauche();
		return super.toString();
	}
}
