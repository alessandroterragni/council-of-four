package it.polimi.ingsw.cg28.controller.updaters;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.Player;
import it.polimi.ingsw.cg28.model.PlayerBuilder;
import it.polimi.ingsw.cg28.model.parser.Configuration;
import it.polimi.ingsw.cg28.model.parser.IOController;
import it.polimi.ingsw.cg28.model.parser.ModelTestConfiguration;
import it.polimi.ingsw.cg28.view.messages.action.StartMsg;

public class BonusTileCheckerTest {
	
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
	public void testSameTownTypeCheckWithNullMapParameter() {
		BonusTileChecker tc = new BonusTileChecker();
		tc.sameTownTypeCheck(null, model.getMap().getTown("Arkon"), model.getPlayer(players[0]));
	}
	
	@Test (expected = NullPointerException.class)
	public void testSameTownTypeCheckWithNullStartParameter() {
		BonusTileChecker tc = new BonusTileChecker();
		tc.sameTownTypeCheck(model.getMap(), null, model.getPlayer(players[0]));
	}
	
	@Test (expected = NullPointerException.class)
	public void testSameTownTypeCheckWithNullPlayerParameter() {
		BonusTileChecker tc = new BonusTileChecker();
		tc.sameTownTypeCheck(model.getMap(), model.getMap().getTown("Arkon"), null);
	}
	
	@Test
	public void testSameTownTypeCheck() throws IOException {
		BonusTileChecker tc = new BonusTileChecker();
		
		List<PlayerID> playerIDs = new ArrayList<>();
		playerIDs.add(new PlayerID("Player1"));
		playerIDs.add(new PlayerID("Player2"));
		  
		Configuration config = new Configuration(new StartMsg());
		IOController controller = new IOController();
		ModelStatus model = controller.build(config.fileConfig());
		  
		PlayerBuilder builder = new PlayerBuilder();
		Player[] players = builder.build(playerIDs, model);

		
		model.getMap().addEmporium(players[0].getID(), model.getMap().getTown("Arkon"));
		model.getMap().addEmporium(players[0].getID(), model.getMap().getTown("Framek"));
		model.getMap().addEmporium(players[0].getID(), model.getMap().getTown("Hellar"));
		model.getMap().addEmporium(players[0].getID(), model.getMap().getTown("Kultos"));
		model.getMap().addEmporium(players[0].getID(), model.getMap().getTown("Osium"));
		
		model.getMap().addEmporium(players[0].getID(), model.getMap().getTown("Esti"));
		
		assertTrue(tc.sameTownTypeCheck(model.getMap(), model.getMap().getTown("Arkon"), players[0]));
		assertFalse(tc.sameTownTypeCheck(model.getMap(), model.getMap().getTown("Esti"), players[0]));
		
	}
	
	@Test (expected = NullPointerException.class)
	public void testSameRegionCheckWithNullMapParameter() {
		BonusTileChecker tc = new BonusTileChecker();
		tc.sameRegionCheck(null, model.getMap().getTown("Arkon"), model.getPlayer(players[0]));
	}
	
	@Test (expected = NullPointerException.class)
	public void testSameRegionCheckWithNullStartParameter() {
		BonusTileChecker tc = new BonusTileChecker();
		tc.sameRegionCheck(model.getMap(), null, model.getPlayer(players[0]));
	}
	
	@Test (expected = NullPointerException.class)
	public void testSameRegionCheckWithNullPlayerParameter() {
		BonusTileChecker tc = new BonusTileChecker();
		tc.sameRegionCheck(model.getMap(), model.getMap().getTown("Arkon"), null);
	}
	
	@Test
	public void testSameRegionCheck() {
		BonusTileChecker tc = new BonusTileChecker();
		
		model.getMap().addEmporium(players[0], model.getMap().getTown("Graden"));
		model.getMap().addEmporium(players[0], model.getMap().getTown("Juvelar"));
		
		assertTrue(tc.sameRegionCheck(model.getMap(), model.getMap().getTown("Graden"), model.getPlayer(players[0])));
		assertFalse(tc.sameRegionCheck(model.getMap(), model.getMap().getTown("Arkon"), model.getPlayer(players[0])));
	}

}
