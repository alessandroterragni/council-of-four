package it.polimi.ingsw.cg28.controller.bonus;

import static org.junit.Assert.*;

import org.junit.Test;

public class TakePermitTileBonusTest {

	private TakePermitTileBonus bonus;
	
	@Test
	public void test() {
		assertNotNull(new TakePermitTileBonus(5));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testTakePermitTileBonusWithNegativeParameter() {
		bonus = new TakePermitTileBonus(-3);
	}

	@Test 
	public void testTakePermitTileBonusGetValue() {
		
		bonus = new TakePermitTileBonus(5);
		assertTrue(bonus.getValue() == 5);
		
	}

}
