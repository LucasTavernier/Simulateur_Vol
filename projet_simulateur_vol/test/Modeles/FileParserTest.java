package Modeles;


import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;

import org.junit.Test;

public class FileParserTest {

	@Test
	public void testTraitementFichier() {
		FileParser fp = new FileParser(new File("res/exemples/01_CorpsTombeSurSoleil.astro"));
		Univers u = fp.traitementFichier();
		assertEquals(0.01, u.getG(), 0.01);
		assertEquals(0.020, u.getDt(), 0.001);
		assertEquals(1, u.getFa(), 1);
		assertEquals(500, u.getRayon(), 1);
		
		ArrayList<Astre> astres = (ArrayList<Astre>) u.getAstres();
		
		Fixe a = (Fixe) astres.get(0);
		
		assertEquals("Soleil", a.getNom());
		assertEquals(40, a.getMasse(), 1);
		assertEquals(0, a.getPosX(), 0.01);
		assertEquals(0, a.getPosY(), 0.01);
		
		Simule s = (Simule) astres.get(1);
		
		assertEquals("Objet", s.getNom());
		assertEquals(0.01, s.getMasse(), 0.01);
		assertEquals(0, s.getPosX(), 1);
		assertEquals(450, s.getPosY(), 1);
		assertEquals(0, s.getVitX(), 0.01);
		assertEquals(0, s.getVitY(), 0.01);
	}

}
