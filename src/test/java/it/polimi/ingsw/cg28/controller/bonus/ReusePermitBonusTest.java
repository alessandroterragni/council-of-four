package it.polimi.ingsw.cg28.controller.bonus;

import static org.junit.Assert.*;

import org.junit.Test;

public class ReusePermitBonusTest {

	private ReusePermitBonus bonus;
	
	@Test
	public void test() {
		assertNotNull(new ReuseCityBonus(5));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testReusePermitBonusWithNegativeParameter() {
		bonus = new ReusePermitBonus(-3);
	}

	@Test 
	public void testReusePermitBonusGetValue() {
		
		bonus = new ReusePermitBonus(5);
		assertTrue(bonus.getValue() == 5);
		
	}

}
