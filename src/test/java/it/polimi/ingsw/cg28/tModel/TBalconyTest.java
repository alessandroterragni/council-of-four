package it.polimi.ingsw.cg28.tModel;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.tmodel.TBalcony;

public class TBalconyTest {
	
	private Color[] colors = new Color[4];
	
	@Before
	public void before() {
		colors[0] = Color.BLACK;
		colors[1] = Color.ORANGE;
		colors[2] = Color.MAGENTA;
		colors[3] = Color.WHITE;
	}

	@Test (expected = NullPointerException.class)
	public void testTBalconyWithNullParameter() {
		new TBalcony(null);
	}
	
	@Test
	public void testTBalcony() {
		TBalcony tb = new TBalcony(new Color[4]);
		assertTrue(!tb.equals(null));
		assertTrue(tb.getCouncil().length == 4);
	}
	
	@Test
	public void testGetCouncil() {
		Color[] extracted = new TBalcony(colors).getCouncil();
		assertTrue(extracted.equals(colors));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testGetCouncillorByIntIndexWithNegativeParameter() {
		TBalcony tb = new TBalcony(colors);
		tb.getCouncillorColor(-12);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testGetCouncillorByIntIndexWithOverSizeParameter() {
		TBalcony tb = new TBalcony(colors);
		tb.getCouncillorColor(30);
	}
	
	@Test
	public void testGetCouncillorByIntIndex() {
		TBalcony tb = new TBalcony(colors);
		for(int i = 0; i < colors.length; i++){
			Color c = tb.getCouncillorColor(i);
			assertTrue(c.equals(colors[i]));
		}
	}

}
