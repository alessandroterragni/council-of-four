package it.polimi.ingsw.cg28.controller.actions;

import static org.junit.Assert.*;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.controller.PlayerTurn;
import it.polimi.ingsw.cg28.controller.updaters.BonusActionUpdater;
import it.polimi.ingsw.cg28.model.BusinessPermitTile;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.Player;
import it.polimi.ingsw.cg28.model.Region;
import it.polimi.ingsw.cg28.model.parser.ModelTestConfiguration;

public class TakePermitTileBonusActionTest {
	
	private ModelStatus model;
	private PlayerID [] players;
	
	@Before
	public void before(){
		
		players = new PlayerID[1];
		players[0] = new PlayerID("Player1");
		
		ModelTestConfiguration config = new ModelTestConfiguration();
		model = config.buildModelTest(players);
		model.getPlayer(players[0]).setTurn(new PlayerTurn(players[0]));
		
	}
	
	@Test (expected = NullPointerException.class)
	public void testTakePermitTileBonusActionWithNullParameters() {
		new TakePermitTileBonusAction(null, null);
	}
	
	@Test (expected = NullPointerException.class)
	public void testTakePermitTileBonusActionWithNUllTiles() {
		Region[] regions = new Region[1];
		regions[0] = model.getRegion(0);
		new TakePermitTileBonusAction(null, regions);
	}
	
	@Test (expected = NullPointerException.class)
	public void testTakePermitTileBonusActionWithNUllRegion() {
		ArrayList<BusinessPermitTile> list = new ArrayList<>();
		list.add(model.getRegion(0).getTile(0));
		new TakePermitTileBonusAction(list, null);
	}
	
	@Test 
	public void testTakePermitTileBonusAction() {
		Region[] regions = new Region[1];
		regions[0] = model.getRegion(0);
		ArrayList<BusinessPermitTile> list = new ArrayList<>();
		list.add(model.getRegion(0).getTile(0));
		TakePermitTileBonusAction bonus =new TakePermitTileBonusAction(list, regions);
		assertNotNull("the bonus is correctly built",bonus);
	}
	
	@Test
	public void testAct() {
		Player p1 = model.getPlayer(players[0]);
		Region[] regions = new Region[1];
		regions[0] = model.getRegion(0);
		ArrayList<BusinessPermitTile> list = new ArrayList<>();
		BusinessPermitTile tile = model.getRegion(0).getTile(0);
		list.add(tile);
		TakePermitTileBonusAction bonus = new TakePermitTileBonusAction(list, regions);
		BonusActionUpdater bau =(BonusActionUpdater) bonus.act(p1); 
		
		assertFalse(model.getRegion(0).getTile(0).equals(tile));
		assertTrue(p1.getPossessedTiles().contains(tile));
		
		assertNotNull("The bonusActionUpdater is correclty returned",bau);
	}
	
	@Test (expected = NullPointerException.class)
	public void testActWithNullParameter() {
		Region[] regions = new Region[1];
		regions[0] = model.getRegion(0);
		ArrayList<BusinessPermitTile> list = new ArrayList<>();
		list.add(model.getRegion(0).getTile(0));
		TakePermitTileBonusAction bonus = new TakePermitTileBonusAction(list, regions);
		bonus.act(null);
	}



}
