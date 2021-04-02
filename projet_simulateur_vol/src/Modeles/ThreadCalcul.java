package Modeles;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ThreadCalcul implements Runnable {

	private Univers u;
	private int nbMethodeCalcul;
	private HashMap<Astre, Double> x0Map;
	private HashMap<Astre, Double> y0Map;

	public ThreadCalcul(Univers u, int nbMethodeCalcul) {
		super();
		this.u = u;
		this.nbMethodeCalcul = nbMethodeCalcul;
		x0Map = new HashMap<Astre, Double>();
		y0Map = new HashMap<Astre, Double>();
		for(Astre a : u.getAstres()) {
			x0Map.put(a, a.getPosX());
			y0Map.put(a, a.getPosY());
		}
	}
	/**
	 * @see Runnable
	 * @see Thread
	 */
	public void run() {
		try {
			//System.out.println(u.toString());
			for(Astre a : u.getAstres()) {
				if(a instanceof Simule || a instanceof Vaisseau) {
					double xTotal = 0;
					double yTotal = 0;
					for(Astre a1 : u.getAstres()) {
						if(a != a1) {
							double distX = a1.getPosX() - a.getPosX();
							double distY = a1.getPosY() - a.getPosY();

							double distance = Math.sqrt(distX * distX + distY * distY);

							double angle = Math.atan2(distY, distX);
							double force = u.getG() * ((a.getMasse() * a1.getMasse()) / (distance * distance));
							xTotal += (Math.cos(angle) * force);
							yTotal += (Math.sin(angle) * force);
						}
					}
					if(a instanceof Vaisseau) {
						Vaisseau v = (Vaisseau) a;
						xTotal += (Math.cos(Math.toRadians(v.getRotation())) * (v.getValuePrincipal()-v.getValueRetroAvant() + v.getValueRetroDroit()-v.getValueRetroGauche()));
						yTotal += (-(Math.sin(Math.toRadians(v.getRotation()))) * (v.getValuePrincipal()-v.getValueRetroAvant() + v.getValueRetroDroit()-v.getValueRetroGauche()));
					}
					switch (nbMethodeCalcul) {
					case 1:
						eulerExplicite(a, xTotal, yTotal);
						break;
					case 2:
						eulerImplicite(u.getDt(), (Simule) a, xTotal, yTotal);
						break;
					case 3: 
						leapfrog(u.getDt(), (Simule) a, xTotal, yTotal);
						break;
					case 4:
						rk4(u.getDt(), (Simule) a, xTotal, yTotal);
						break;
					}
				}
			}
			for(Objectif o : u.getObjectifs()) {
				if(o.estAtteint()) {
					u.getObjectifs().remove(o);
					break;
				}
			}
			u.addTemps(u.getDt());
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * implémentation de la méthode de leapfrog pour la modélisation du système solaire
	 * @param dt : le pas de temps de l'univers
	 * @param s : l'astre à modéliser
	 * @param xtotal : les forces en abscisse appliqués pour la position d'après
	 * @param ytotal : les forces en ordonnée appliqués pour la position d'après
	 */
	private void leapfrog(double dt, Simule s, double xtotal, double ytotal) {
		double posX = s.getPosX();
		double posY = s.getPosY();
		
		double vitX = s.getVitX();
		double vitY = s.getVitY();
		
        double demiVitX = vitX + (dt * xtotal /2);
        double demiVitY = vitY + (dt * ytotal /2);

        s.setX(demiVitX);
        s.setY(demiVitY);
        s.setVitX(vitX + xtotal);
        s.setVitY(vitY + ytotal);
		
	}
	
	/**
	 * implémentation de la méthode d'Euler implicite pour la modélisation du système solaire
	 * @param dt : le pas de temps de l'univers
	 * @param s : l'astre à modéliser
	 * @param xtotal : les forces en abscisse appliqués pour la position d'après
	 * @param ytotal : les forces en ordonnée appliqués pour la position d'après
	 */
	private void eulerImplicite(double dt, Simule s, double xtotal, double ytotal) {
		dt /= 100;
		
		double posX = s.getPosX();
		double posY = s.getPosY();
		
		double vitX = s.getVitX();
		double vitY = s.getVitY();
		
        double prochainePosX = vitX;
        double prochainePosY = vitY;
        
        double prochaineVitX = 0;
        double prochaineVitY = 0;

        for (int i = 0; i < 100; i++) {
        	prochainePosX += posX + dt;
        	prochainePosY += posY + dt;
        	
        	prochaineVitX *= dt + vitX;
        	
        	prochaineVitY *= dt + vitY;
        }

        s.setX(prochainePosX);
        s.setY(prochainePosY);
        s.setVitX(vitX+prochaineVitX);
        s.setVitY(vitY+prochaineVitY);
		
	}
	
	/**
	 * implémentation de la méthode de Runge-Kutta pour la modélisation du système solaire
	 * @param dt : le pas de temps de l'univers
	 * @param s : l'astre à modéliser
	 * @param xtotal : les forces en abscisse appliqués pour la position d'après
	 * @param ytotal : les forces en ordonnée appliqués pour la position d'après
	 */
	private void rk4(double dt, Simule s, double xtotal, double ytotal) {
		dt *= 50;
		double posX = s.getPosX();
		double posY = s.getPosY();
		
		double vitX = s.getVitX();
		double vitY = s.getVitY();
		
        double prochainePosX = posX + dt * (vitX + (2 * (dt / 2 * vitX)) + (2 * (dt / 2 * vitX)) + (dt * vitX)) / 6;
        double prochainePosY = posY + dt * (vitY + (2 * (dt / 2 * vitY)) + (2 * (dt / 2 * vitY)) + (dt * vitY)) / 6;
        
        double prochaineVitX = vitX + dt * (xtotal + (2 * (dt / 2 * xtotal)) + (2 * (dt / 2 * xtotal)) + (dt * xtotal)) / 6;
        double prochaineVitY = vitY + dt * (ytotal + (2 * (dt / 2 * ytotal)) + (2 * (dt / 2 * ytotal)) + (dt * ytotal)) / 6;

        s.setX(prochainePosX);
        s.setY(prochainePosY);
        s.setVitX(prochaineVitX);
        s.setVitY(prochaineVitY);
	}

	/**
	 * implémentation de la méthode d'Euler explicite pour la modélisation du système solaire
	 * @param a : l'astre
	 * @param xTotal : les forces en abscisse appliqués pour la position d'après
	 * @param yTotal : les forces en ordonnée appliqués pour la position d'après
	 */
	private void eulerExplicite(Astre a, double xTotal, double yTotal) {
		((Simule) a).setVitX(((Simule) a).getVitX() + xTotal);
		((Simule) a).setVitY(((Simule) a).getVitY() + yTotal);
		a.setX(a.getPosX() + ((Simule) a).getVitX());
		a.setY(a.getPosY() + ((Simule) a).getVitY());
	}
}
