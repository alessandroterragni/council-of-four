package it.polimi.ingsw.cg28.controller.bonus;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class BonusPackTest {
	
	private BonusPack bonusPack;
	private List<Bonus> bonuses;

	@Before
	public void before() {
		
		bonuses = new ArrayList<>();
		bonuses.add(new DrawCardBonus(5));
		bonuses.add(new AssistantBonus(3));
		
	}
	
	@Test
	public void test() {
		assertNotNull(new BonusPack(bonuses));
	}
	
	@Test (expected = NullPointerException.class)
	public void testBonusPackWithNullParameter() {
		bonusPack = new  BonusPack(null);
	}

	@Test 
	public void testBonusPackGetPack() {
		
		bonusPack = new BonusPack(bonuses);
		List<Bonus> bonusesToCheck = bonusPack.getBonusPack();
		assertTrue(bonusesToCheck.get(0).getClass().equals(DrawCardBonus.class));
		assertTrue(bonusesToCheck.get(0).getValue() == 5);
		
		assertTrue(bonusesToCheck.get(1).getClass().equals(AssistantBonus.class));
		assertTrue(bonusesToCheck.get(1).getValue() == 3);
		
	}

}
