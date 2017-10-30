package it.polimi.ingsw.cg28.model;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

public class CouncillorTest {
	
	private Councillor councillor;
	
	@Before
	public void before() {
		councillor = new Councillor(Color.ORANGE);
	}

	@Test (expected = NullPointerException.class)
	public void testCouncillorWithNullParameter() {
		new Councillor(null);
	}
	
	@Test
	public void testCouncillor() {
		Councillor c = new Councillor(Color.BLACK);
		assertTrue(!c.equals(null));
		assertTrue(c.getColor().equals(Color.BLACK));
	}
	
	@Test
	public void testGetColor() {
		assertTrue(councillor.getColor().equals(Color.ORANGE));
	}

}
