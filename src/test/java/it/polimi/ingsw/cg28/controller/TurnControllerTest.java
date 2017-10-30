package it.polimi.ingsw.cg28.controller;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TurnControllerTest {
	
	private TurnController turnController;
	private List<PlayerID> players;
	private PlayerID player1;
	private PlayerID player2;
	
	@Before
	public void setTest() {
		
		players = new ArrayList<>();
		player1 = new PlayerID("Player1");
		player2 = new PlayerID("Player2");
		players.add(player1);
		players.add(player2);
		
	}
	
	@Test
	public void testTurnController() {
		assertNotNull(new TurnController(players, 1, "GameTest"));
	}
	
	@Test(expected = NullPointerException.class)
	public void testTurnControllerWithNullPlayersParameter() {
		turnController = new TurnController(null, 1, "GameTest");
	}
	
	@Test
	public void testTurnControllerWithNegativeBazaarTrigger() {
		
		turnController = new TurnController(players, -1, "GameTest");
		assertTrue(turnController.getMarketPlayer() == null);
		assertTrue(turnController.nextTurn().getPlayer().equals(player1));
		assertTrue(turnController.nextTurn().getPlayer().equals(player2));
		assertTrue(turnController.nextTurn().getPlayer().equals(player1));
		assertTrue(turnController.nextTurn().getPlayer().equals(player2));
		
	}
	
	@Test
	public void testTurnControllerWithPositiveBazaarTrigger() {
		
		turnController = new TurnController(players, 2, "GameTest");
		assertFalse(turnController.getMarketPlayer() == null);
		
		GameTurn turn = turnController.nextTurn();
		assertTrue(turn.getPlayer().equals(player1));
		assertTrue(turn.getClass().equals(PlayerTurn.class));
		turn = turnController.nextTurn();
		assertTrue(turn.getPlayer().equals(player2));
		assertTrue(turn.getClass().equals(PlayerTurn.class));
		turn = turnController.nextTurn();
		assertTrue(turn.getPlayer().equals(turnController.getMarketPlayer()));
		assertTrue(turn.getClass().equals(BazaarTurn.class));
		turn = turnController.nextTurn();
		assertTrue(turn.getPlayer().equals(player1));
		assertTrue(turn.getClass().equals(PlayerTurn.class));
		turn = turnController.nextTurn();
		assertTrue(turn.getPlayer().equals(player2));
		assertTrue(turn.getClass().equals(PlayerTurn.class));
		
	}
	
	@Test
	public void testTurnControllerSetLastRound() {
		
		players.add(new PlayerID("Player3"));
		players.add(new PlayerID("Player4"));
		turnController = new TurnController(players, -1, "GameTest");
		
		GameTurn turn = turnController.nextTurn();
		assertTrue(turn.getPlayer().equals(player1));
		assertFalse(turnController.isQueueEmpty());
		
		turnController.setLastRound(false);
		
		turn = turnController.nextTurn();
		assertTrue(turn.getPlayer().equals(player2));
		assertFalse(turnController.isQueueEmpty());
		turn = turnController.nextTurn();
		assertFalse(turnController.isQueueEmpty());
		turn = turnController.nextTurn();
		assertFalse(turnController.isQueueEmpty());
		
		
		turn = turnController.nextTurn();
		assertTrue(turn.getPlayer().equals(player1));
		
		turnController.setLastRound(true);
		
		turn = turnController.nextTurn();
		assertTrue(turn.getPlayer().equals(player2));
		assertFalse(turnController.isQueueEmpty());
		turn = turnController.nextTurn();
		assertFalse(turnController.isQueueEmpty());
		turn = turnController.nextTurn();
		assertTrue(turnController.isQueueEmpty());
	
	}
	
	@Test
	public void testTurnControllerInactivePlayer() {
		
		PlayerID player3 = new PlayerID("Player3");
		players.add(player3);
		PlayerID player4 = new PlayerID("Player4");
		players.add(player4);
		
		turnController = new TurnController(players, -1, "GameTest");
		
		GameTurn turn = turnController.nextTurn();
		assertTrue(turn.getPlayer().equals(player1));
		assertFalse(turnController.isQueueEmpty());
		turn = turnController.nextTurn();
		assertTrue(turn.getPlayer().equals(player2));
		assertFalse(turnController.isQueueEmpty());
		turn = turnController.nextTurn();
		assertFalse(turnController.isQueueEmpty());
		turn = turnController.nextTurn();
		assertFalse(turnController.isQueueEmpty());
		
		
		turnController.inactivePlayer(player1);
		turnController.inactivePlayer(player2);
		
		assertFalse(turnController.onlyOnePlayer());
		
		turn = turnController.nextTurn();
		assertTrue(turn.getPlayer().equals(player3));
		assertFalse(turnController.isQueueEmpty());
		turn = turnController.nextTurn();
		assertTrue(turn.getPlayer().equals(player4));
		
		turnController.inactivePlayer(player3);
		
		assertTrue(turnController.onlyOnePlayer());

	}

}
