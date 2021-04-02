package Controleurs;

import Modeles.Univers;
import Modeles.Vaisseau;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

public class SliderDtControleur {

	Slider listener;
	Univers u;
	Label dt;

	/**
	 * 
	 * @param listener : node sur lequel on agit
	 * @param u : l'univers
	 * @param l : le label indiquant le pas de temps
	 */
	public SliderDtControleur(Slider listener, Univers u, Label l) {
		this.listener = listener;
		this.u = u;
		this.dt = l;
		listener.valueProperty().addListener(e->{
			u.setDt(listener.getValue());
			l.setText("Dt : " + listener.getValue());
		});
	}
	
}
