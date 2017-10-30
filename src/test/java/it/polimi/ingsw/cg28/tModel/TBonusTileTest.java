package it.polimi.ingsw.cg28.tModel;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.tmodel.TBonus;
import it.polimi.ingsw.cg28.tmodel.TBonusTile;

public class TBonusTileTest {
	
	private TBonusTile tile;
	private TBonus bonus;
	private String identifier;
	private String[] codes = { "VPB", "CB", "NB" };
	
	@Before
	public void before() {
		bonus = new TBonus(codes);
		identifier = "Identifier";
	}
	
	@Test (expected = NullPointerException.class)
	public void testTBonusTileWithNullParameterTBonus() {
		tile = new TBonusTile(null, identifier);
	}
	
	@Test (expected = NullPointerException.class)
	public void testTBonusTileWithNullParameterIdentifier() {
		tile = new TBonusTile(bonus, null);
	}
	
	@Test
	public void testTKingRewardTile() {
		assertNotNull(new TBonusTile(bonus, identifier));
	}
	
	@Test
	public void testGetBonus() {
		tile = new TBonusTile(bonus, identifier);
		assertTrue(tile.getBonus().equals(bonus));
	}
	
	@Test
	public void testGetIdentifier() {
		tile = new TBonusTile(bonus, identifier);
		assertTrue(tile.getIdentifier().equals(identifier));
	}

}
