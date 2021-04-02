package Controleurs;

import Modeles.Vaisseau;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;

public class KeyboardHandler {
	Node listener;
	Vaisseau v;

	/**
	 * 
	 * @param listener : node sur lequel on agit
	 * @param v : vaisseau que l'on souhaite déplacer
	 */
	public KeyboardHandler(Node listener, Vaisseau v) {
		this.listener = listener;
		this.v = v;
		listener.setOnKeyPressed(e -> {
			updateVaisseauOn(e);
			// Empêche les touches de changer le focus
			e.consume();
		});
		listener.setOnKeyReleased(e -> {
			updateVaisseauOff(e);
			e.consume();
		});
	}

	/**
	 * Détecte si une flèche directionnelle est pressée, si c'est le cas, agit sur le vaisseau en conséquence
	 * @param e : <u>KeyEvent</u>
	 * @see KeyEvent
	 */
	private void updateVaisseauOn(KeyEvent e) {
		switch(e.getCode()) {
		case DOWN:
			v.setPrincipal(true);
			v.setValueRetroAvant(0);
			if(!v.isRetroAvant()) {
				v.setValuePrincipal(v.getValuePrincipal()+v.getFORCEPRINCIPAL());
			}
			break;
		case UP:
			v.setRetroAvant(true);
			v.setValuePrincipal(0);
			if(!v.isPrincipal()) {
				v.setValueRetroAvant(v.getValueRetroAvant()+v.getFORCEAVANT());
			}
			break;
		case LEFT:
			v.setRetroGauche(true);
			if(!v.isRetroDroit()) {
				//v.setValueRetroGauche(v.getValueRetroGauche()+(0.4*+v.getFORCEGAUCHE()) );
				v.setRotation(v.getRotation()+2);
			}
			break;
		case RIGHT:
			v.setRetroDroit(true);
			if(!v.isRetroGauche()) {
				//v.setValueRetroDroit(v.getValueRetroDroit()+(0.4*v.getFORCEDROIT()) );
				v.setRotation(v.getRotation()-2);
			}
		}
	}

	/**
	 * Détecte si une touche directionnelle est relachée, si c'est le cas, agit sur le vaisseau en conséquence
	 * @param e : <u>KeyEvent</u>
	 * @see KeyEvent
	 */
	private void updateVaisseauOff(KeyEvent e) {
		switch(e.getCode()) {
		case DOWN:
			v.setPrincipal(false);
			v.setValuePrincipal(0);
			break;
		case UP:
			v.setRetroAvant(false);
			v.setValueRetroAvant(0);
			break;
		case LEFT:
			v.setRetroGauche(false);
			v.setValueRetroGauche(0);
			break;
		case RIGHT:
			v.setRetroDroit(false);
			v.setValueRetroDroit(0);
		}
	}
}
