package it.polimi.ingsw.cg28.tModel;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.tmodel.TBonus;

public class TBonusTest {
	
	private String[] codes = new String[3];
	
	@Before
	public void before() {
		codes[0] = "VPB";
		codes[1] = "CB";
		codes[2] = "NB";
	}

	@Test (expected = NullPointerException.class)
	public void testTBonusWithNullParameter() {
		new TBonus(null);
	}
	
	@Test
	public void testTBonus() {
		TBonus b = new TBonus(codes);
		assertTrue(!b.equals(null));
		assertTrue(b.getBonusCode().length == 3);
	}
	
	@Test
	public void testGetBonuses() {
		TBonus b = new TBonus(codes);
		String[] cs = b.getBonusCode();
		assertTrue(cs.equals(codes));
	}

}
