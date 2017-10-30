package it.polimi.ingsw.cg28.tModel;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.tmodel.TBonus;
import it.polimi.ingsw.cg28.tmodel.TBusinessPermitTile;

public class TBusinessPermitTileTest {
	
	private TBonus tb;
	private String[] codes = new String[3];
	
	@Before
	public void before() {
		String[] s = { "VPB", "CB" };
		tb = new TBonus(s);
		codes[0] = "VPB";
		codes[0] = "CB";
		codes[0] = "NB";
	}

	@Test (expected = NullPointerException.class)
	public void testTBusinessPermitTileWithNullTBonusParameter() {
		new TBusinessPermitTile(null, codes);
	}
	
	@Test (expected = NullPointerException.class)
	public void testTBusinessPermitTileWithNullLetterCodesParameter() {
		new TBusinessPermitTile(tb, null);
	}
	
	@Test
	public void testTBusinessPermitTile() {
		TBusinessPermitTile tbpt = new TBusinessPermitTile(tb, codes);
		assertTrue(!tbpt.equals(null));
		assertTrue(tbpt.getLetterCodes().length == 3);		
	}
	
	@Test
	public void testGetBonuses() {
		TBusinessPermitTile tbpt = new TBusinessPermitTile(tb, codes);
		TBonus bon = tbpt.getBonuses();
		assertTrue(bon.equals(tb));
	}
	
	@Test
	public void testGetLetterCodes() {
		TBusinessPermitTile tbpt = new TBusinessPermitTile(tb, codes);
		String[] strs = tbpt.getLetterCodes();
		assertTrue(strs.equals(codes));
	}

}
