package it.polimi.ingsw.cg28.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.bonus.VictoryPointBonus;

public class BusinessPermitTileTest {
	
	private BusinessPermitTile tile;
	private String[] letters = {"Arkon", "Juvelar", "Graden"};
	
	@Before
	public void before() { 
		tile = new BusinessPermitTile(new VictoryPointBonus(4), letters);
	}

	@Test (expected = NullPointerException.class)
	public void testBusinessPermitTileWithNullBonusesParameter() {
		new BusinessPermitTile(null, letters);
	}
	
	@Test (expected = NullPointerException.class)
	public void testBusinessPermitTileWithNullLettersParameter() {
		new BusinessPermitTile(new VictoryPointBonus(4), null);
	}
	
	@Test
	public void testBusinessPermitTile() {
		BusinessPermitTile newTile = new BusinessPermitTile(new VictoryPointBonus(4), letters);
		assertTrue(!newTile.equals(null));
	}
	
	@Test
	public void testGetBonuses() {
		assertTrue(tile.getBonuses().getClass().equals(new VictoryPointBonus(4).getClass()));
	}
	
	@Test
	public void testGetLetterCodes() {
		assertTrue(tile.getLetterCodes().equals(letters));
	}

}
