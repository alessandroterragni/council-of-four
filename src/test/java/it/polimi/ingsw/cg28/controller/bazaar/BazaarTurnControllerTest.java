package it.polimi.ingsw.cg28.controller.bazaar;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.controller.actions.bazaar.SellAssistantsAction;
import it.polimi.ingsw.cg28.controller.actions.bazaar.SellPermitTilesAction;
import it.polimi.ingsw.cg28.controller.actions.bazaar.SellPoliticCardsAction;
import it.polimi.ingsw.cg28.exception.NoMoreTurnException;
import it.polimi.ingsw.cg28.model.BazaarModel;
import it.polimi.ingsw.cg28.model.BusinessPermitTile;
import it.polimi.ingsw.cg28.model.PoliticCard;

public class BazaarTurnControllerTest {
	
	private BazaarTurnController turnController;
	private Map<PlayerID, boolean[]> playersMap;
	private List<PlayerID> players;
	private PlayerID player1;
	private PlayerID player2;
	
	@Before
	public void setTest(){
		
		player1 = new PlayerID("Player 1");
		player2 = new PlayerID("Player 2");
		
		players = new ArrayList<>();
		players.add(player1);
		players.add(player2);
		
		boolean[] canSell1 = {true, false, true};
		boolean[] canSell2 = {false, true, false};
		
		playersMap = new HashMap<>();
		playersMap.put(player1, canSell1);
		playersMap.put(player2, canSell2);
		
	}
	
	@Test
	public void testBazaarTurnController() {
		assertNotNull(new BazaarTurnController(players, playersMap));
	}
	
	@Test(expected = NullPointerException.class)
	public void testBazaarTurnControllerWithNullPlayersParameter() {
		turnController = new BazaarTurnController(players, null);
	}
	
	@Test(expected = NullPointerException.class)
	public void testBazaarTurnControllerWithNullPlayersMapParameter() {
		turnController = new BazaarTurnController(null, playersMap);
	}
	
	@Test
	public void testBazaarTurnControllerCorrectTurnListInitialitasion() throws NoMoreTurnException {
		
		turnController = new BazaarTurnController(players, playersMap);
		BazaarPlayerTurn turn = turnController.nextTurn();
		assertTrue(turn.getClass().equals(BazaarSellPlayerTurn.class));
		assertTrue(turn.getPlayer().equals(player1));
		
		assertFalse(((BazaarSellPlayerTurn)turn).isEnded());
		turn.update(new SellAssistantsAction(2, 3, new BazaarModel()));
		assertFalse(((BazaarSellPlayerTurn)turn).isEnded());
		List<BusinessPermitTile> tiles = new ArrayList<>();
		turn.update(new SellPermitTilesAction(tiles, 2, new BazaarModel()));
		assertTrue(((BazaarSellPlayerTurn)turn).isEnded());
		
		turn = turnController.nextTurn();
		assertTrue(turn.getClass().equals(BazaarSellPlayerTurn.class));
		assertTrue(turn.getPlayer().equals(player2));
		
		assertFalse(((BazaarSellPlayerTurn)turn).isEnded());
		List<PoliticCard> cards = new ArrayList<>();
		turn.update(new SellPoliticCardsAction(cards, 2, new BazaarModel()));
		assertTrue(((BazaarSellPlayerTurn)turn).isEnded());
		
		turn = turnController.nextTurn();
		assertTrue(turn.getClass().equals(BazaarBuyPlayerTurn.class));
		
		if(turn.getPlayer().equals(player1)){
			turn = turnController.nextTurn();
			assertTrue(turn.getClass().equals(BazaarBuyPlayerTurn.class));
			assertTrue(turn.getPlayer().equals(player2));
		}
		else {
			turn = turnController.nextTurn();
			assertTrue(turn.getClass().equals(BazaarBuyPlayerTurn.class));
			assertTrue(turn.getPlayer().equals(player1));
		}
	}
	
	@Test(expected = NoMoreTurnException.class)
	public void testBazaarTurnControllerNextTurnException() throws NoMoreTurnException {
		turnController = new BazaarTurnController(players, playersMap);
		turnController.nextTurn();
		turnController.nextTurn();
		turnController.nextTurn();
		turnController.nextTurn();
		turnController.nextTurn();
	}
	
	@Test
	public void testBazaarTurnControllerIsEmpty() throws NoMoreTurnException {
		turnController = new BazaarTurnController(players, playersMap);
		assertFalse(turnController.isEmpty());
		turnController.nextTurn();
		assertFalse(turnController.isEmpty());
		turnController.nextTurn();
		assertFalse(turnController.isEmpty());
		turnController.nextTurn();
		assertFalse(turnController.isEmpty());
		turnController.nextTurn();
		assertTrue(turnController.isEmpty());
	}
	
	@Test
	public void testBazaarTurnControllerDeletePlayer() throws NoMoreTurnException {
		
		turnController = new BazaarTurnController(players, playersMap);
		
		turnController.deletePlayer(player2);
		
		BazaarPlayerTurn turn = turnController.nextTurn();
		assertTrue(turn.getPlayer().equals(player1));
		assertTrue(turn.getClass().equals(BazaarSellPlayerTurn.class));
		turn = turnController.nextTurn();
		assertTrue(turn.getPlayer().equals(player1));
		assertTrue(turn.getClass().equals(BazaarBuyPlayerTurn.class));
		
		assertTrue(turnController.isEmpty());
		
		turnController = new BazaarTurnController(players, playersMap);
		
		turnController.deletePlayer(player1);
		
		turn = turnController.nextTurn();
		assertTrue(turn.getPlayer().equals(player2));
		assertTrue(turn.getClass().equals(BazaarSellPlayerTurn.class));
		turn = turnController.nextTurn();
		assertTrue(turn.getPlayer().equals(player2));
		assertTrue(turn.getClass().equals(BazaarBuyPlayerTurn.class));
		
		assertTrue(turnController.isEmpty());
		
	}
		

}
