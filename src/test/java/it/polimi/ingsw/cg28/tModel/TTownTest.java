package it.polimi.ingsw.cg28.tModel;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.tmodel.TBonus;
import it.polimi.ingsw.cg28.tmodel.TTown;

public class TTownTest {
	
	private TBonus bonus;
	private PlayerID[] ids = new PlayerID[4];
	private String[] bonCode = { "VPB", "CB" };
	private TTown town;
	
	@Before
	public void before() {
		bonus = new TBonus(bonCode);
		ids[0] = new PlayerID("P1");
		ids[1] = new PlayerID("P2");
		ids[2] = new PlayerID("P3");
		ids[3] = new PlayerID("P4");
		
		town = new TTown("Arkon", "Gold", "Coast", bonus, false, ids);
	}

	@Test (expected = NullPointerException.class)
	public void testTTownWithNullNameParameter() {
		new TTown(null, "Gold", "Coast", bonus, false, ids);
	}
	
	@Test (expected = NullPointerException.class)
	public void testTTownWithNullAlloyParameter() {
		new TTown("Arkon", null, "Coast", bonus, false, ids);
	}

	@Test (expected = NullPointerException.class)
	public void testTTownWithNullRegionParameter() {
		new TTown("Arkon", "Gold", null, bonus, false, ids);
	}
	
	@Test (expected = NullPointerException.class)
	public void testTTownWithNullEmporiumsParameter() {
		new TTown("Arkon", "Gold", "Coast", bonus, false, null);
	}
	
	@Test
	public void testTTown() {
		TTown createdTown = new TTown("Arkon", "Gold", "Coast", bonus, false, ids);
		assertTrue(!createdTown.equals(null));
		assertTrue(createdTown.getBonus().getBonusCode().length == 2);
		assertTrue(createdTown.getEmporiums().length == 4);
	}
	
	@Test
	public void testGetName() {
		assertTrue(town.getName().equals("Arkon"));
	}
	
	@Test
	public void testGetAlloy() {
		assertTrue(town.getAlloy().equals("Gold"));
	}
	
	@Test
	public void testGetRegion() {
		assertTrue(town.getRegion().equals("Coast"));
	}
	
	@Test
	public void testGetBonus() {
		assertTrue(town.getBonus().equals(bonus));
		assertTrue(town.getBonus().getBonusCode().equals(bonCode));
		for(int i = 0; i < town.getBonus().getBonusCode().length; i++){
			assertTrue(town.getBonus().getBonusCode()[i].equals(bonCode[i]));
		}
	}
	
	@Test
	public void testIsKingHere() {
		assertTrue(!town.isKingHere());
	}
	
	@Test
	public void testGetEmporiums() {
		assertTrue(town.getEmporiums().equals(ids));
		for(int j = 0; j < town.getEmporiums().length; j++){
			assertTrue(town.getEmporiums()[j].equals(ids[j]));
		}
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testGetEmporiumByIntIndexWithNegativeParameter() {
		town.getEmporium(-2);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testGetEmporiumByIntIndexWithOffBoundsParameter() {
		town.getEmporium(10);
	}
	
	@Test
	public void testGetEmporiumByIntIndex() {
		for(int k = 0; k < town.getEmporiums().length; k++){
			assertTrue(town.getEmporium(k).equals(town.getEmporiums()[k]));
		}
	}

}
