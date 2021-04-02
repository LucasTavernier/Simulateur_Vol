package Modeles;

public class ObjectifVoyager extends Objectif {
	
	private double distance;
	private double tlimite;
	
	/**
	 * Cet objectif propose de quitter le système solaire en un temps limite avec une certaine vitesse.
	 * @param univers : l'univers dans lequel l'objectif peut être réalisé.
	 * @param distance : la distance qu'on doit faire par rapport à l'étoile du système solaire.
	 * @param tlimite : le temps limite en secondes pour réaliser cet objectif
	 */
	public ObjectifVoyager(Univers univers, double distance, double tlimite) {
		super(univers);
		this.distance = distance;
		this.tlimite = tlimite;
	}
	
	public ObjectifVoyager(Univers u, double distance) {
		this(u, distance, -1);
	}
	
	/**
	 * Indique si l'objectif est réussi ou non en fonction de la validation des conditions demandées.
	 */
	public boolean estAtteint() {
		Vaisseau vaisseau = this.univers.getVaisseau();
		
		return vaisseau.distanceVers(this.univers.getEtoile()) > this.distance
				&& vaisseau.troisiemeVitesseCosmique()
				&& (tlimite == -1 || univers.getTempsEcoule() < tlimite);
	}

	/**
	 * Renvoie la distance entre le vaisseau et l'étoile de l'univers
	 * @return <u>Double</u> : La distance entre le vaisseau et le soleil
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * Renvoie le temps limite pour la réalisation de l'objectif
	 * @return <u>Double</u> : Le temps limite
	 */
	public double getTlimite() {
		return tlimite;
	}
	
	
}
