package it.polimi.ingsw.cg28.controller.updaters;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.Town;
import it.polimi.ingsw.cg28.model.parser.ModelTestConfiguration;

public class TownNetCheckerTest {
	
	private ModelStatus model;
	PlayerID[] players = new PlayerID[2];
	
	@Before
	public void before(){
		players[0] = new PlayerID("Player 1");
		players[1] = new PlayerID("Player 2");
	
		ModelTestConfiguration config = new ModelTestConfiguration();
		model = config.buildModelTest(players);
		
	}

	@Test (expected = NullPointerException.class)
	public void testCheckMapWithNullMapParameter() {
		TownNetChecker tc = new TownNetChecker();
		tc.checkMap(null, model.getMap().getTown("Arkon"), model.getPlayer(players[0]));
	}
	
	@Test (expected = NullPointerException.class)
	public void testCheckMapWithNullStartParameter() {
		TownNetChecker tc = new TownNetChecker();
		tc.checkMap(model.getMap(), null, model.getPlayer(players[0]));
	}
	
	@Test (expected = NullPointerException.class)
	public void testCheckMapWithNullPlayerParameter() {
		TownNetChecker tc = new TownNetChecker();
		tc.checkMap(model.getMap(), model.getMap().getTown("Arkon"), null);
	}
	
	@Test
	public void testCheckMapWithConnectedTowns() {
		TownNetChecker tc = new TownNetChecker();
		
		model.getMap().addEmporium(players[0], model.getMap().getTown("Arkon"));
		model.getMap().addEmporium(players[0], model.getMap().getTown("Graden"));
		
		Set<Town> result = tc.checkMap(model.getMap(), model.getMap().getTown("Arkon"), model.getPlayer(players[0]));
		assertTrue(result.contains(model.getMap().getTown("Arkon")));
		assertTrue(result.contains(model.getMap().getTown("Graden")));
	}
	
	@Test
	public void testCheckMapWithNonConnectedTowns() {
		TownNetChecker tc = new TownNetChecker();
		
		model.getMap().addEmporium(players[0], model.getMap().getTown("Graden"));
		model.getMap().addEmporium(players[0], model.getMap().getTown("Juvelar"));
		model.getMap().addEmporium(players[1], model.getMap().getTown("Arkon"));
		
		Set<Town> result = tc.checkMap(model.getMap(), model.getMap().getTown("Graden"), model.getPlayer(players[0]));
		
		assertTrue(result.contains(model.getMap().getTown("Graden")));
		assertFalse(result.contains(model.getMap().getTown("Juvelar")));
		assertFalse(result.contains(model.getMap().getTown("Arkon")));
	}

}
