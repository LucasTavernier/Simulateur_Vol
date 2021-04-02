package Modeles;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class FileParser {
	private File astro;

	/**
	 * 
	 * @param astro -- <u>File</u> : Un fichier créé à partir des fichiers .astro fourni / ou ajouté
	 * @see File
	 */
	public FileParser(File astro) {
		this.astro=astro;
	}
	/**
	 * Cette méthode utilise un bufferReader et parcourt le début du fichier astro afin de trouver les
	 * valeurs nécessaires à la création de notre univers
	 * @return <u>Univers</u> : l'univers du fichier astro
	 * @see BufferedReader
	 */
	public Univers createUnivers() {
		Univers univers = null;
		try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(astro), "UTF-8"))){
			String ligneCourante=br.readLine();
			while(ligneCourante != null) {
				if(!ligneCourante.isEmpty()) {
					if(ligneCourante.charAt(0) == '#' || ligneCourante.isEmpty()) {}
					else if(!ligneCourante.startsWith("PARAMS")) {
						System.err.println("La première ligne n'est pas PARAMS");
						break;
					}
					else {
						univers=new Univers(getValues(ligneCourante, "G", "dt", "fa", "rayon"));
						break;
					}
				}
				ligneCourante = br.readLine();
			}
		}

		catch (IOException e) {
			e.printStackTrace();
		}
		return univers;
	}

	/**
	 * Cette méthode utilise un bufferReader et parcourt le reste du fichier astro afin de trouver les
	 * valeurs nécessaires à la création des astres de l'univers.
	 * @return <u>Univers</u> : l'univers du fichier astro
	 * @see BufferedReader
	 */
	public Univers traitementFichier() {
		Univers univers = this.createUnivers();

		try(BufferedReader br = new BufferedReader(new FileReader(astro))){
			String ligneCourante=br.readLine();
			while(ligneCourante != null) {
				if(!ligneCourante.isEmpty()) {
					if(ligneCourante.charAt(0) =='#') {}

					else if(ligneCourante.startsWith("PARAMS")) {}
					else if(ligneCourante.startsWith("OBJECTIF_VOYAGER")) {
						univers.addObjectif(new ObjectifVoyager(univers, getValue(ligneCourante, "distance"), getValue(ligneCourante, "tlimite")));
					}
					else {
						if(ligneCourante.contains("Fixe")) {
							String nom = ligneCourante.substring(0, ligneCourante.indexOf(":"));
							univers.add(new Fixe(nom, getValues(ligneCourante, "posx", "posy", "masse")));
						}
						else if(ligneCourante.contains("Simulé") || ligneCourante.contains("Simule")) {
							String nom = ligneCourante.substring(0, ligneCourante.indexOf(":"));
							univers.add(new Simule(nom, getValues(ligneCourante, "posx", "posy", "masse", "vitx", "vity")));
						}
						else if(ligneCourante.contains("Vaisseau")) {
							if(univers.getVaisseau() == null) {
								String nom = ligneCourante.substring(0, ligneCourante.indexOf(":"));
								univers.add(new Vaisseau(nom, getValues(ligneCourante, "posx", "posy", "masse", "vitx", "vity", "pprincipal", "pretro")));
							}
							else {
								System.err.println("Il y a déjà un vaisseau existant !");
							}
						}
					}
				}
				ligneCourante = br.readLine();
			}
		}

		catch (IOException e) {
			e.printStackTrace();
		}

		return univers;
	}

	private double[] getValues(String ligneCourante, String... nomsVariables) {
		double[] res = new double[nomsVariables.length];
		for(int i = 0; i < nomsVariables.length; i++) {
			double value = getValue(ligneCourante, nomsVariables[i]);
			res[i] = value;
		}
		return res;
	}

	private double getValue(String ligneCourante, String nomVariable) {
		int idxVariable = ligneCourante.indexOf(nomVariable + "=");
		try {
			return Double.parseDouble(ligneCourante.substring(idxVariable + nomVariable.length() + 1, ligneCourante.indexOf(" ", idxVariable)));
		}
		catch(StringIndexOutOfBoundsException e) {
			return Double.parseDouble(ligneCourante.substring(idxVariable + nomVariable.length() + 1, ligneCourante.length()));
		}
		catch (NumberFormatException NFE) {
			System.err.println("La variable " + nomVariable + " est manquante ou incomplète voire incorrecte");
			System.err.println("" + ligneCourante);
			return 0;
		}
	}
}
