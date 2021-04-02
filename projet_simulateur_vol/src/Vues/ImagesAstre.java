package Vues;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import Modeles.Astre;
import Modeles.Vaisseau;
import javafx.scene.DepthTest;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImagesAstre {
	private static final Random RAND = new Random();
	private static ImagesAstre instance;

	private HashMap<String, ArrayList<Image>> imagesPossibles;
	
	private HashMap<Astre, ImageView> images;

	private ImagesAstre() {
		
		imagesPossibles = new HashMap<>();
		ArrayList<Image> imagesPlanete = new ArrayList<Image>();
		ArrayList<Image> imagesSoleil = new ArrayList<Image>();
		ArrayList<Image> imagesComète = new ArrayList<Image>();
		ArrayList<Image> imagesSonde = new ArrayList<Image>();
		ArrayList<Image> imagesVaisseau = new ArrayList<Image>();
		ArrayList<Image> imagesObjet = new ArrayList<Image>();

		imagesPlanete.add(new Image("file:res/pic/Mercure.png"));
		imagesPlanete.add(new Image("file:res/pic/Venus.png"));
		imagesPlanete.add(new Image("file:res/pic/Terre.png"));
		imagesPlanete.add(new Image("file:res/pic/Mars.png"));
		imagesPlanete.add(new Image("file:res/pic/Jupiter.png"));
		imagesPlanete.add(new Image("file:res/pic/Uranus.png"));
		imagesPlanete.add(new Image("file:res/pic/Neptune.png"));

		imagesPossibles.put("Planète", imagesPlanete);

		imagesSoleil.add(new Image("file:res/pic/soleil.png"));
		imagesPossibles.put("Soleil", imagesSoleil);

		imagesComète.add(new Image("file:res/pic/Comete.png"));
		imagesPossibles.put("Comète", imagesComète);

		imagesSonde.add(new Image("file:res/pic/Sonde.png"));
		imagesPossibles.put("Sonde", imagesSonde);
		
		imagesVaisseau.add(new Image("file:res/pic/Vaisseau.png"));
		imagesPossibles.put("Vaisseau", imagesVaisseau);

		for(ArrayList<Image> array : imagesPossibles.values()) {
			if(imagesSoleil != array && imagesVaisseau != array) {
				for(Image i : array) {
					imagesObjet.add(i);
				}
			}
		}
		imagesObjet.add(new Image("file:res/pic/objet.png"));
		imagesPossibles.put("Objet", imagesObjet);
		
		this.images = new HashMap<Astre, ImageView>();
	}

	public static ImagesAstre getInstance() {
		if (instance == null) {
			instance = new ImagesAstre();
		}
		return instance;
	}
	
	public ImageView setImage(Astre a) {
		ImageView img;
		if(a instanceof Vaisseau)
			img = new ImageView(getImageAleatoire("Vaisseau"));
		else
			img = new ImageView(getImageAleatoire(a.getNom()));
		images.put(a, img);
		return img;
	}
	
	public boolean checkCollision(Astre a1, Astre a2) {
		ImageView iv1 = images.get(a1);
		ImageView iv2 = images.get(a2);
		
		if(iv1.getBoundsInParent().intersects(iv2.getBoundsInParent())){
			return true;
		}
		return false;
	}
	
	public ImageView getImage(Astre a) {
		return images.get(a);
	}
	
	public HashMap<Astre, ImageView> getMapping(){
		return this.images;
	}

	private Image getImageAleatoire(String cle) {
		String realKey = verifName(cle);

		if (imagesPossibles.containsKey(realKey)) {
			ArrayList<Image> imgs = imagesPossibles.get(realKey);
			return imgs.get(RAND.nextInt(imgs.size()));
		} else {
			ArrayList<Image> imgs = imagesPossibles.get("Objet");
			return imgs.get(RAND.nextInt(imgs.size()));
		}
	}

	private String verifName(String cle) {
		String cleMin = cle.toLowerCase();
		cleMin = Normalizer.normalize(cleMin, Normalizer.Form.NFD).replaceAll("[\u0300-\u036F]", "");

		
		if (cleMin.contains("objet")) {
			return "Objet";
		} else if (cleMin.contains("planete")) {
			return "Planète";
		} else if (cleMin.contains("soleil")) {
			return "Soleil";
		} else if (cleMin.contains("sonde")) {
			return "Sonde";
		} else if (cleMin.contains("comete")) {
			return "Comète";
		} else if(cleMin.contains("vaisseau")) {
			return "Vaisseau";
		}
		return "Objet";
	}
	
	public void deleteAstre(Astre a) {
		this.images.remove(a);
	}
}
