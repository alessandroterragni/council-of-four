package it.polimi.ingsw.cg28.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;

public class EmporiumTest {
	
	private Emporium emp;
	
	@Before
	public void before() {
		emp = new Emporium(new PlayerID("Marcone"));
	}

	@Test (expected = NullPointerException.class)
	public void testEmporiumWithNullParameter() {
		new Emporium(null);
	}
	
	@Test
	public void testEmporium() {
		Emporium e = new Emporium(new PlayerID("IO"));
		assertTrue(!e.equals(null));
		assertTrue(e.getOwner().getName().equals("IO"));
	}
	
	@Test
	public void testGetOwner() {
		assertTrue(emp.getOwner().getName().equals("Marcone"));
		assertTrue(!emp.getOwner().equals(null));
	}
}
