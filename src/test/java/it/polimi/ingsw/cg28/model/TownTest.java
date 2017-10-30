package it.polimi.ingsw.cg28.model;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.controller.bonus.Bonus;
import it.polimi.ingsw.cg28.controller.bonus.TakePermitTileBonus;
import it.polimi.ingsw.cg28.model.decks.TileDeck;
import it.polimi.ingsw.cg28.model.parser.ModelTestConfiguration;

public class TownTest {
	
	private ModelStatus model;
	PlayerID[] players = new PlayerID[2];
	
	@Before
	public void before(){
		players[0] = new PlayerID("Player 1");
		players[1] = new PlayerID("Player 2");
	
		ModelTestConfiguration config = new ModelTestConfiguration();
		model = config.buildModelTest(players);
		
		model.getMap().getTown("Arkon").addEmporium(players[0]);
		
	}

	@Test (expected = NullPointerException.class)
	public void testTownWithNullNameParameter() {
		new Town(null, "Gold", false, null, new Region("Coast", 2, new TileDeck<>(new LinkedList<BusinessPermitTile>()),
				new Councillor[4]));
	}
	
	@Test (expected = NullPointerException.class)
	public void testTownWithNullAlloyParameter() {
		new Town("Arkon", null, false, null, new Region("Coast", 2, new TileDeck<>(null), new Councillor[4]));
	}
	
	@Test (expected = NullPointerException.class)
	public void testTownWithNullRegionParameter() {
		new Town("Arkon", null, false, null, null);
	}
	
	@Test
	public void testTownConstructor() {
		Town town = new Town("Arkon", "Gold", false, null, new Region("Coast", 2,
				new TileDeck<>(new LinkedList<BusinessPermitTile>()), new Councillor[4]));
		assertTrue(!town.equals(null));
	}
	
	@Test
	public void testIsKingHere() {
		Town arkon = model.getMap().getTown("Arkon");
		Town juvelar = model.getMap().getTown("Juvelar");
		assertTrue(!arkon.isKingHere());
		assertTrue(juvelar.isKingHere());
	}
	
	@Test
	public void testSetKingHere() {
		Town arkon = model.getMap().getTown("Arkon");
		Town juvelar = model.getMap().getTown("Juvelar");
		assertTrue(!arkon.isKingHere());
		assertTrue(juvelar.isKingHere());
		arkon.setKingHere(true);
		juvelar.setKingHere(false);
		assertTrue(arkon.isKingHere());
		assertTrue(!juvelar.isKingHere());
	}
	
	@Test
	public void testGetTownName() {
		String name = model.getMap().getTown("Arkon").getTownName();
		assertTrue(name.equals("Arkon"));
	}
	
	@Test
	public void testGetAlloy() {
		String alloy = model.getMap().getTown("Arkon").getAlloy();
		assertTrue(alloy.equals("Gold"));
	}
	
	@Test
	public void testGetBonus() {
		Town town = new Town("Arkon", "Gold", false, new TakePermitTileBonus(1), new Region("Coast", 2,
				new TileDeck<>(new LinkedList<BusinessPermitTile>()), new Councillor[4]));
		Bonus bonus = town.getBonus();
		assertTrue(bonus.getClass().equals(new TakePermitTileBonus(1).getClass()));
		assertTrue(bonus.getValue() == 1);
	}
	
	@Test
	public void testGetRegion() {
		Region region = model.getMap().getTown("Arkon").getRegion();
		assertTrue(region.getRegionType().equals("Coast"));
	}
	
	@Test (expected = NullPointerException.class)
	public void testAddEmporiumWithNullParameter() {
		model.getMap().getTown("Arkon").addEmporium(null);
	}
	
	@Test
	public void testAddEmporium() {
		assertTrue(model.getMap().getTown("Arkon").hasEmporium(players[0]));
		model.getMap().getTown("Arkon").addEmporium(players[0]);
		model.getMap().getTown("Arkon").addEmporium(players[1]);
		assertTrue(model.getMap().getTown("Arkon").hasEmporium(players[0]));
		assertTrue(model.getMap().getTown("Arkon").hasEmporium(players[1]));
	}
	
	@Test (expected = NullPointerException.class)
	public void testHasEmporiumWithNullParameter() {
		model.getMap().getTown("Arkon").hasEmporium(null);
	}
	
	@Test
	public void testHasEmporium() {
		assertTrue(model.getMap().getTown("Arkon").hasEmporium(players[0]));
		assertTrue(!model.getMap().getTown("Arkon").hasEmporium(players[1]));
	}
	
	@Test
	public void testEmporiumNumber() {
		assertTrue(model.getMap().getTown("Arkon").emporiumNumber() == 1);
		model.getMap().getTown("Arkon").addEmporium(players[1]);
		assertTrue(model.getMap().getTown("Arkon").emporiumNumber() == 2);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testGetEmporiumWithNegativeParameter() {
		model.getMap().getTown("Arkon").getEmporium(-4);
	}
	
	@Test
	public void testGetEmporium() {
		Emporium emp = model.getMap().getTown("Arkon").getEmporium(0);
		assertTrue(emp != null);
		assertTrue(emp.getOwner().equals(players[0]));
		model.getMap().getTown("Arkon").addEmporium(players[1]);
		Emporium emp1 = model.getMap().getTown("Arkon").getEmporium(1);
		assertTrue(emp1 != null);
		assertTrue(emp1.getOwner().equals(players[1]));
	}
}
