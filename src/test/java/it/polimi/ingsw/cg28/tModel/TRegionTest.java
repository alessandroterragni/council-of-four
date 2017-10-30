package it.polimi.ingsw.cg28.tModel;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.tmodel.TBalcony;
import it.polimi.ingsw.cg28.tmodel.TBonus;
import it.polimi.ingsw.cg28.tmodel.TBusinessPermitTile;
import it.polimi.ingsw.cg28.tmodel.TRegion;

public class TRegionTest {
	
	private TBalcony council;
	private Color[] cols = { Color.BLACK, Color.ORANGE, Color.MAGENTA };
	private TBusinessPermitTile[] tiles = new TBusinessPermitTile[3];
	private String[] codes = { "VPB", "CB" };
	private String[] letters = { "Arkon", "Juvelar", "Graden" };
	private TBonus b1 = new TBonus(codes);
	private TBonus b2 = new TBonus(codes);
	private TBonus b3 = new TBonus(codes);
	private TRegion region;
	
	@Before
	public void before() {
		council = new TBalcony(cols);
		tiles[0] = new TBusinessPermitTile(b1, letters);
		tiles[1] = new TBusinessPermitTile(b2, letters);
		tiles[2] = new TBusinessPermitTile(b3, letters);
		region = new TRegion(council, "Mountain", tiles);
	}

	@Test (expected = NullPointerException.class)
	public void testTRegionWithNullCouncilParameter() {
		new TRegion(null, "Mountain", tiles);
	}
	
	@Test (expected = NullPointerException.class)
	public void testTRegionWithNullRegionParameter() {
		new TRegion(council, null, tiles);
	}
	
	@Test (expected = NullPointerException.class)
	public void testTRegionWithNullUncoveredParameter() {
		new TRegion(council, "Mountain", null);
	}
	
	@Test
	public void testTRegion() {
		TRegion newRegion = new TRegion(council, "Mountain", tiles);
		assertTrue(!newRegion.equals(null));
		assertTrue(newRegion.getCouncil().getCouncil().length == 3);
		assertTrue(newRegion.getUncovered().length == 3);
	}
	
	@Test
	public void testGetCouncil() {
		assertTrue(!region.getCouncil().equals(null));
		assertTrue(region.getCouncil().getCouncil().equals(cols));
	}
	
	@Test
	public void testGetRegionType() {
		assertTrue(region.getRegionType().equals("Mountain"));
	}
	
	@Test
	public void testGetUncovered() {
		assertTrue(!region.getUncovered().equals(null));
		assertTrue(region.getUncovered().length == 3);
		assertTrue(region.getUncovered().equals(tiles));
	}

}
