package it.polimi.ingsw.cg28.controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.parser.ModelTestConfiguration;

public class BazaarTurnTest {
	
	private BazaarTurn bazaarTurn;
	private ModelStatus model;
	private PlayerID [] players;
	
	@Before
	public void before(){
		
		players = new PlayerID[2];
		players[0] = new PlayerID("Player1");
		players[1] = new PlayerID("Player2");
		
		ModelTestConfiguration config = new ModelTestConfiguration();
		model = config.buildModelTest(players);
		model.getPlayer(players[0]).setTurn(new PlayerTurn(players[0]));
		model.getPlayer(players[1]).setTurn(new PlayerTurn(players[1]));
		

	}
	
	@Test
	public void testBazaarTurn() {
		assertNotNull(new BazaarTurn(new PlayerID("Player1")));
	}
	
	@Test(expected = NullPointerException.class)
	public void testBazaarTurnWithNullParameter() {
		bazaarTurn = new BazaarTurn(null);
	}
	
	@Test
	public void testBazaarTurnMsgRequired() {
		bazaarTurn = new BazaarTurn(new PlayerID("Player1"));
		assertTrue(bazaarTurn.msgRequired() == null);
	}
	
	@Test
	public void testBazaarTurnIsEnded() {
		bazaarTurn = new BazaarTurn(new PlayerID("Player1"));
		assertTrue(bazaarTurn.isEnded());
	}
	
	@Test
	public void testBazaarTurnSetTurn() {
		bazaarTurn = new BazaarTurn(new PlayerID("PlayerTest"));
		
		assertFalse(model.getPlayer(players[0]).getTurn() == null);
		assertFalse(model.getPlayer(players[1]).getTurn() == null);
		bazaarTurn.setTurn(model);
		assertTrue(model.getPlayer(players[0]).getTurn() == null);
		assertTrue(model.getPlayer(players[1]).getTurn() == null);
	}
	

}
