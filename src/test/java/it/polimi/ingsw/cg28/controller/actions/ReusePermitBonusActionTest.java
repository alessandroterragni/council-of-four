package it.polimi.ingsw.cg28.controller.actions;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.controller.PlayerTurn;
import it.polimi.ingsw.cg28.controller.updaters.BonusActionUpdater;
import it.polimi.ingsw.cg28.model.BusinessPermitTile;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.Player;
import it.polimi.ingsw.cg28.model.parser.ModelTestConfiguration;

public class ReusePermitBonusActionTest {

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
	public void testReusePermitBonusActionWithNUllParameter() {
		new ReusePermitBonusAction(null);
	}
	
	@Test 
	public void testReusePermitBonusAction() {
		List<BusinessPermitTile> list = new ArrayList<>();
		list.add(model.getRegion(0).getTile(0));
		ReusePermitBonusAction bonus = new ReusePermitBonusAction(list);
		assertNotNull("the bonus is correctly built",bonus);
	}
	
	
	@Test (expected = NullPointerException.class)
	public void testActWithNullParamter() {
		List<BusinessPermitTile> list = new ArrayList<>();
		list.add(model.getRegion(0).getTile(0));
		ReusePermitBonusAction bonus = new ReusePermitBonusAction(list);
		bonus.act(null);
	}
	
	@Test 
	public void testAct() {
		Player p1 = model.getPlayer(players[0]);
		List<BusinessPermitTile> list = new ArrayList<>();
		list.add(model.getRegion(0).getTile(0));
		ReusePermitBonusAction bonus = new ReusePermitBonusAction(list);
		BonusActionUpdater bau =(BonusActionUpdater) bonus.act(p1);
		
		assertNotNull("The bonusActionUpdater is correclty returned",bau);
	}
	

}
