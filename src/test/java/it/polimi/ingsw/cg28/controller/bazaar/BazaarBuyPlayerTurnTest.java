package it.polimi.ingsw.cg28.controller.bazaar;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.model.BazaarModel;
import it.polimi.ingsw.cg28.model.BusinessPermitTile;
import it.polimi.ingsw.cg28.model.PoliticCard;
import it.polimi.ingsw.cg28.view.messages.event.GiveMeBuyActionMsg;
import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.controller.actions.bazaar.SellAssistantsAction;
import it.polimi.ingsw.cg28.controller.actions.bazaar.SellPermitTilesAction;
import it.polimi.ingsw.cg28.controller.actions.bazaar.SellPoliticCardsAction;

public class BazaarBuyPlayerTurnTest {
	
	private BazaarBuyPlayerTurn turn;
	private PlayerID player;
	
	@Before
	public void setTest() {
		
		player = new PlayerID("Player1");
		
	}

	@Test
	public void testBazaarBuyPlayerTurn() {
		assertNotNull(new BazaarBuyPlayerTurn(player));
	}
	
	@Test(expected = NullPointerException.class)
	public void testBazaarBuyPlayerTurnWithNullPlayer() {
		turn = new BazaarBuyPlayerTurn(null);
	}
	
	@Test
	public void testBazaarBuyPlayerTurnGetPlayer() {
		
		turn = new BazaarBuyPlayerTurn(player);
		assertTrue(player.equals(turn.getPlayer()));
		
	}
	
	@Test
	public void testBazaarBuyPlayerTurnIsEnded() {
		
		turn = new BazaarBuyPlayerTurn(player);
		assertFalse(turn.isEnded());
		turn.update(new SellAssistantsAction(2, 3, new BazaarModel()));
		List<BusinessPermitTile> tiles = new ArrayList<>();
		turn.update(new SellPermitTilesAction(tiles, 2, new BazaarModel()));
		List<PoliticCard> cards = new ArrayList<>();
		turn.update(new SellPoliticCardsAction(cards, 2, new BazaarModel()));
		assertFalse(turn.isEnded());
		
	}
	

	@Test
	public void testBazaarBuyPlayerTurnMsgRequired() {
		
		turn = new BazaarBuyPlayerTurn(player);
		assertTrue(turn.msgRequired().getClass().equals(GiveMeBuyActionMsg.class));
		assertTrue(turn.msgRequired().getPlayer().equals(player));
		
	}
	
	
	

}
