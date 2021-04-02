package Modeles;

public abstract class Objectif {
	
	protected Univers univers;
	
	/**
	 * Un objectif est un défi proposé à l'utilisateur dans le fichier .astro
	 * Ca peut être par exemple quitter le système solaire
	 * @param univers : l'univers dans lequel l'objectif est proposé
	 */
	public Objectif(Univers univers) {
		this.univers = univers;
	}
	
	public abstract boolean estAtteint();
}
