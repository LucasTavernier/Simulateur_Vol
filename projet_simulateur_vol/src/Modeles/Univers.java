package Modeles;

import java.util.ArrayList;
import java.util.List;

public class Univers {

	private final double G; //Constante gravitationnelle METRE/SECONDE
	private double dt;		//pas de temps pour la simulation en SECONDE
	private double fa; 		//facteur accélération pour la simulation en METRE/SECONDE
	private double rayon; 	//rayon du systeme définissant l'étendue (donc ce qu'il faut afficher) en METRE
	private double tempsEcoule;
	private List<Astre> astres;
	private List<Objectif> objectifs;
	private int compteurCollision = 0;

	/**
	 * 
	 * 
	 * @param g -- <u>Double</u> : constante de gravitation
	 * @param dt -- <u>Double</u> : pas de temps de la simulation
	 * @param fa -- <u>Double</u> : facteur d'accélération de la simulation
	 * @param rayon -- <u>Double</u> : rayon du sysème définissant l'étendue à afficher
	 * @param astres -- <u>List d'astres</u> : liste des autres astres composant cet univers
	 */
	public Univers(double g, double dt, double fa, double rayon, List<Astre> astres) {
		this.G=g;
		this.dt=dt;
		this.fa=fa;
		this.rayon=rayon;
		this.tempsEcoule=0;
		this.astres=new ArrayList<Astre>();
		this.objectifs = new ArrayList<Objectif>();
	}

	/**
	 * 
	 * 
	 * @param g -- <u>Double</u> : constante de gravitation
	 * @param dt -- <u>Double</u> : pas de temps de la simulation
	 * @param fa -- <u>Double</u> : facteur d'accélération de la simulation
	 * @param rayon -- <u>Double</u> : rayon du sysème définissant l'étendue à afficher
	 */
	public Univers(double g, double dt, double fa, double rayon) {
		this(g,dt,fa,rayon,new ArrayList<Astre>());
	}

	/*
	 * @param attributs -><u>Double[]</u> : les valeurs nécessaires à la création de l'univers
	 */
	public Univers(double... attributs) {
		this(attributs[0], attributs[1], attributs[2], attributs[3]);
	}

	/**
	 * Renvoie la constante de gravitation
	 * @return <u>Double</u> : la constante de gravitation
	 */
	public double getG() {
		return G;
	}

	/**
	 * Renvoie le pas de temps
	 * @return <u>Double</u> : le pas de temps
	 */
	public double getDt() {
		return dt;
	}
	
	/**
	 * Change le pas de temps
	 * @param dt -- <u>Le Pas de temps</u>
	 */
	public void setDt(Double dt) {
		this.dt = dt;
	}

	/**
	 * Renvoie le facteur d'accélération
	 * @return <u>Double</u> : le facteur d'accélération
	 */
	public double getFa() {
		return fa;
	}

	/**
	 * Divise le pas de temps par le facteur d'accélération pour connaître la rapidité souhaité des calculs.
	 * Si nous avons un pas de temps de 0.025 et fa = 1 par exemple,
	 * on doit alors faire (0.025/1)*1000 = 25 millisecondes. Un calcul tous les 25 millisecondes.
	 * @return <u>Long</u> : la rapidité des calculs
	 */
	public long getCadenceSimulation(){
		return (long) ((dt / fa) * 1000.0);
	}

	/**
	 * Renvoie le rayon dans lequel la simulation se fait par rapport au centre (0,0)
	 * @return <u>Double</u> : le rayon
	 */
	public double getRayon() {
		return rayon;
	}

	/**
	 * Change la liste des astres de l'univers
	 * @param astres -- <u>Liste d'astre</u>
	 * @see Astre
	 */
	public void setAstres(List<Astre> astres) {
		this.astres = astres;
	}

	/**
	 * Renvoie la liste des astres de l'univers
	 * @return <u>Liste d'astre</u> : les astres
	 * @see Astre
	 */
	public List<Astre> getAstres() {
		return astres;
	}

	/**
	 * 
	 * @param nom -- <u>String</u> : le nom de l'astre
	 * @return <u>Astre</u>
	 * @see Astre
	 */
	public Astre getAstre(String nom) {
		for(Astre a : this.getAstres()) {
			if(a.getNom().equals(nom)) {
				return a;
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @return <u>Astre</u> : l'astre selectionné pour le centrage de la vue
	 * @see Modeles.Astre#setSelectedToFocusView(boolean)
	 */
	public Astre getAstreSelectedToFocus() {
		Astre astre = null;
		for(Astre a : this.getAstres()) {
			if(a.isSelectedToFocusView()) {
				astre = a;
			}
		}
		return astre;
	}

	/**
	 * Renvoie le vaisseau de l'univers s'il existe
	 * @return <u>Vaisseau</u> : le vaisseau
	 * @see Vaisseau
	 */
	public Vaisseau getVaisseau() {
		for(Astre a : astres) {
			if(a instanceof Vaisseau) {
				return (Vaisseau) a;
			}
		}
		return null;
	}

	/**
	 * Renvoie le temps écoulé depuis le début de la simulation
	 * @return <u>Double</u> : le temps
	 */
	public double getTempsEcoule() {
		return this.tempsEcoule;
	}

	/**
	 * Change le temps écoulé depuis le début de la simulation
	 * @param tempsEcoule -- <u>Double</u> : le temps écoulé en plus
	 */
	public void setTempsEcoule(double tempsEcoule) {
		this.tempsEcoule = tempsEcoule;
	}

	/**
	 * Incrémente le temps écoulé depuis le début de la simulation
	 * @param temps -- <u>Double</u> : le temps écoulé en plus
	 */
	public void addTemps(double temps) {
		this.tempsEcoule += temps;
	}

	/**
	 * Ajoute un astre à la liste des astres de l'univers
	 * @param a -- <u>Astre</u> : un astre
	 */
	public void add(Astre a) {
		astres.add(a);
	}

	public boolean contains(Object o) {
		return astres.contains(o);
	}

	public String toString() {
		String res = "[";
		for(Astre a : this.astres) {
			if(this.astres.indexOf(a) != this.astres.size()-1) {
				res += a.getNom() + " -> " + a.toString() + "; \n";
			}
			else {
				res += a.getNom() + " ->" + a.toString() + "]";
			}
		}
		return res + "\n";
	}

	/**
	 * @return l'astre de l'univers avec la masse maximale
	 */
	public Astre getAstreMaxMasse() {
		Astre astreMaxMasse = null;
		boolean allSame = true;
		for(Astre a : astres) {
			if(astreMaxMasse == null) {
				astreMaxMasse = a;
			}
			else {
				if(a.getMasse() > astreMaxMasse.getMasse()) {
					astreMaxMasse = a;
					allSame = false;
				}
				if(a.getMasse() < astreMaxMasse.getMasse()) {
					allSame = false;
				}
			}
		}
		if(allSame == true) {
			astreMaxMasse = null;
		}
		return astreMaxMasse;
	}

	/**
	 * Lors d'une collision entre deux astres, si ces deux derniers sont à peu près de masse égales, les deux astres se détruisent et
	 * de cette destruction né un nouvel astre.
	 * Sinon, si on constate une différence notoire de masse entre les deux astres, la masse de l'astre le plus lourd est réduite de la valeur de la masse du moins lourd
	 * @param a1 : l'un des deux astres de la collision
	 * @param a2 : l'autre astre
	 * @return <u>Astre</u> : soit a1, soit a2, soit un nouvel astre.
	 */
	public Astre collision(Astre a1, Astre a2) {
		if(a1.getMasse() > (a2.getMasse()*1.5) ) {
			return collisionAstreBigger(a1, a2);
		}
		else if(a2.getMasse() > (a1.getMasse()*1.5)) {
			return collisionAstreBigger(a2, a1);
		}
		else {
			compteurCollision++;
			Simule newAstre = new Simule("Collision"+compteurCollision,
					a1.getPosX(),
					a1.getPosY(),
					0,
					0,
					a1.getMasse()+a2.getMasse()
					);
			return newAstre;
		}
	}

	/**
	 * 
	 * @param a1 : l'un des deux astres de la collision
	 * @param a2 : l'autre astre
	 * @return <u>Astre</u> : l'astre le plus lourd entre a1 et a2
	 */
	private Astre collisionAstreBigger(Astre a1, Astre a2) {
		a1.setMasse(a1.getMasse()-a2.getMasse());
		Astre toReturn = a2;
		return toReturn;
	}

	public boolean remove(Object o) {
		return astres.remove(o);
	}
	
	/**
	 * Renvois l'étoile du système solaire.
	 * @return <u>Fixe</u> : l'étoile de l'univers
	 */
	public Fixe getEtoile() {
		for(Astre a : this.astres) {
			if(a instanceof Fixe) {
				return (Fixe) a;
			}
		}
		return null;
	}
	
	/**
	 * ajoute un objectif à l'univers
	 * @param o : l'objectif
	 */
	public void addObjectif(Objectif o) {
		this.objectifs.add(o);
	}
	
	/**
	 * 
	 * @return la liste des objectifs de l'univers
	 */
	public List<Objectif> getObjectifs(){
		return this.objectifs;
	}
}
