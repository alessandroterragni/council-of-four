package it.polimi.ingsw.cg28.controller.bonus;

import static org.junit.Assert.*;

import org.junit.Test;

public class ReuseCityBonusTest {
	
	private ReuseCityBonus bonus;
	
	@Test
	public void test() {
		assertNotNull(new ReuseCityBonus(5));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testReuseCityBonusWithNegativeParameter() {
		bonus = new ReuseCityBonus(-3);
	}

	@Test 
	public void testReuseCityBonusGetValue() {
		
		bonus = new ReuseCityBonus(5);
		assertTrue(bonus.getValue() == 5);
		
	}
	

}
