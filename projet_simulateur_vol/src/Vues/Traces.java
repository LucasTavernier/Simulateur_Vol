package Vues;

import java.util.Collection;
import java.util.HashMap;

import Modeles.Astre;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;

public class Traces {
	private static HashMap<Astre, Path> traces;
	private Pane p;
	private int lastIndex = 1;
	private final int TRACESAFFICHEES = 120;
	/**
	 * 
	 * @param p -- <u>Pane</u> : pane sur lequel on veut desssiner les traces
	 * @see Pane
	 */
	public Traces(Pane p) {
		this.traces = new HashMap<Astre, Path>();
		this.p = p;
	}

	/**
	 * Ajoute un astre dont on souhaite afficher les traces.
	 * @param a -- <u>Pane</u> : astre dont on souhaite voir les traces
	 * @see Astre
	 */
	public void add(Astre a) {
		Path trace = new Path(new MoveTo(a.getPosX() + (p.getWidth() / 2), -a.getPosY() + (p.getHeight() / 2)));
		p.getChildren().add(trace);
		trace.setStrokeWidth(1);
		traces.put(a, trace);
	}

	public void update() {
		for(Astre a : traces.keySet()) {
			if(lastIndex == TRACESAFFICHEES) {
				traces.get(a).getElements().remove(1);	
				PathElement pathelt = traces.get(a).getElements().get(1);
				String posPathElt = pathelt.toString();
				Double x = Double.parseDouble(posPathElt.substring(posPathElt.indexOf("x=")+2, posPathElt.indexOf(",")));
				Double y = Double.parseDouble(posPathElt.substring(posPathElt.indexOf("y=")+2, posPathElt.indexOf("]")));
				traces.get(a).getElements().set(1, new MoveTo(x, y));
				traces.get(a).getElements().add(TRACESAFFICHEES-1, new LineTo(a.getPosX() + (p.getWidth() / 2), -a.getPosY() + (p.getHeight() / 2)));
			}else {
				traces.get(a).getElements().add(lastIndex, new LineTo(a.getPosX() + (p.getWidth() / 2), -a.getPosY() + (p.getHeight() / 2)));
			}
		}
		if(lastIndex < TRACESAFFICHEES) {
			lastIndex++;
		}
	}
	
	/**
	 * décale les images
	 * @param decalageX : le décalage de l'image de l'astre en x;
	 * @param decalageY : le décalage de l'image de l'astre en y;
	 */
	public void updateDecalage(double decalageX, double decalageY) {
		for(Path p : traces.values()) {
			p.setTranslateX(p.getLayoutX()+decalageX);
			p.setTranslateY(p.getLayoutY()+decalageY);
		}
	}
	/**
	 * Affiche les traces si @param active vaut true
	 * @param active -- <u>Boolean</u> : un boolean qui indique si les traces doivent être visibles ou non
	 */

	public static void setTraces(boolean active) {
		if(active) {
			for(Astre a : traces.keySet()) {
				traces.get(a).setStroke(Color.WHITE);
			}
		}

		else {
			for(Astre a : traces.keySet()) {
				traces.get(a).setStroke(Color.TRANSPARENT);
			}
		}
	}
	
	public Path get(Astre key) {
		return traces.get(key);
	}

	public void deleteTraces(Astre a) {
		Traces.traces.remove(a);
	}
}
