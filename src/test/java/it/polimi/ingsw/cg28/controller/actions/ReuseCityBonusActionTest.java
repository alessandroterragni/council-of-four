package it.polimi.ingsw.cg28.controller.actions;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.controller.PlayerTurn;
import it.polimi.ingsw.cg28.controller.updaters.BonusActionUpdater;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.Player;
import it.polimi.ingsw.cg28.model.Town;
import it.polimi.ingsw.cg28.model.parser.ModelTestConfiguration;

public class ReuseCityBonusActionTest {

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
	public void testReuseCityBonusActionWithNullParameter() {
		new ReuseCityBonusAction(null);
	}
	
	@Test 
	public void testReuseCityBonusAction() {
		List<Town> towns = new ArrayList<>();
		towns.add(model.getMap().getTown(0));
		towns.add(model.getMap().getTown(1));
		ReuseCityBonusAction bonus = new ReuseCityBonusAction(towns);
		assertNotNull("the bonus is correctly built",bonus);
	}
	
	@Test (expected =  NullPointerException.class)
	public void testActWithNullParameter() {
		List<Town> towns = new ArrayList<>();
		towns.add(model.getMap().getTown(0));
		towns.add(model.getMap().getTown(1));
		ReuseCityBonusAction bonus = new ReuseCityBonusAction(towns);
		bonus.act(null);
	}
	
	@Test 
	public void testAct() {
		Player p1 = model.getPlayer(players[0]);
		List<Town> towns = new ArrayList<>();
		Town town1 = model.getMap().getTown(0);
		Town town2 = model.getMap().getTown(1);
		towns.add(town1);
		towns.add(town2);
		ReuseCityBonusAction bonus = new ReuseCityBonusAction(towns);
		BonusActionUpdater bau =(BonusActionUpdater) bonus.act(p1); 
		
		assertNotNull("The bonusActionUpdater is correclty returned",bau);
	}
	
	

	

}
