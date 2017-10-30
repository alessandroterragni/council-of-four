package it.polimi.ingsw.cg28.model;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

public class PoliticCardTest {
	
	private PoliticCard card1;
	private PoliticCard card2;
	
	@Before
	public void before() {
		card1 = new PoliticCard(Color.ORANGE, false);
		card2 = new PoliticCard(null, true);
	}

	@Test (expected = NullPointerException.class)
	public void testPoliticCardWithNullColorAndFalse() {
		new PoliticCard(null, false);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testPoliticCardWithNotNullColorAndTrue() {
		new PoliticCard(Color.CYAN, true);
	}
	
	@Test
	public void testPoliticCard() {
		PoliticCard c1 = new PoliticCard(Color.BLACK, false);
		PoliticCard c2 = new PoliticCard(null, true);
		assertTrue(!c1.equals(null));
		assertTrue(!c2.equals(null));
		assertTrue(c1.sameCol(Color.BLACK));
		assertTrue(!c1.isAllColors());
		assertTrue(c2.sameCol(null));
		assertTrue(c2.isAllColors());
	}
	
	@Test
	public void testGetHouseColor() {
		Color c1 = card1.getHouseColor();
		Color c2 = card2.getHouseColor();
		assertTrue(c1.equals(Color.ORANGE));
		assertTrue(c2 == null);
	}
	
	@Test
	public void testIsAllColors() {
		assertTrue(!card1.isAllColors());
		assertTrue(card2.isAllColors());
	}
	
	@Test
	public void testSameCol() {
		assertTrue(card1.sameCol(Color.ORANGE));
		assertTrue(!card1.sameCol(null));
		assertTrue(card2.sameCol(null));
	}
	
	@Test
	public void testCopy() {
		PoliticCard copied = card1.copy();
		assertTrue(copied.sameCol(card1.getHouseColor()));
		assertTrue(copied.isAllColors() == card1.isAllColors());
	}
}
