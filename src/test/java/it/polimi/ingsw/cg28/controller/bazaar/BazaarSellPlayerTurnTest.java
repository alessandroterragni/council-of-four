package it.polimi.ingsw.cg28.controller.bazaar;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.controller.actions.bazaar.SellAssistantsAction;
import it.polimi.ingsw.cg28.controller.actions.bazaar.SellPermitTilesAction;
import it.polimi.ingsw.cg28.controller.actions.bazaar.SellPoliticCardsAction;
import it.polimi.ingsw.cg28.model.BazaarModel;
import it.polimi.ingsw.cg28.model.BusinessPermitTile;
import it.polimi.ingsw.cg28.model.PoliticCard;
import it.polimi.ingsw.cg28.view.messages.event.GiveMeSellActionMsg;

public class BazaarSellPlayerTurnTest {

	private BazaarSellPlayerTurn turn;
	private PlayerID player;
	private boolean[] canSell = {true, true, true};
	
	@Before
	public void setTest() {
		
		player = new PlayerID("Player1");
		
	}

	@Test
	public void testBazaarSellPlayerTurn() {
		assertNotNull(new BazaarSellPlayerTurn(player, canSell));
	}
	
	@Test(expected = NullPointerException.class)
	public void testBazaarSellPlayerTurnWithNullPlayer() {
		turn = new BazaarSellPlayerTurn(null, canSell);
	}
	
	@Test(expected = NullPointerException.class)
	public void testBazaarSellPlayerTurnWithNullCanSell() {
		turn = new BazaarSellPlayerTurn(player, null);
	}
	
	@Test
	public void testBazaarSellPlayerTurnGetPlayer() {
		
		turn = new BazaarSellPlayerTurn(player, canSell);
		assertTrue(player.equals(turn.getPlayer()));
		
	}
	
	@Test
	public void testBazaarBuyPlayerTurnIsEnded() {
		
		turn = new BazaarSellPlayerTurn(player, canSell);
		assertFalse(turn.isEnded());
		turn.update(new SellAssistantsAction(2, 3, new BazaarModel()));
		List<BusinessPermitTile> tiles = new ArrayList<>();
		assertFalse(turn.isEnded());
		turn.update(new SellPermitTilesAction(tiles, 2, new BazaarModel()));
		List<PoliticCard> cards = new ArrayList<>();
		assertFalse(turn.isEnded());
		turn.update(new SellPoliticCardsAction(cards, 2, new BazaarModel()));
		assertTrue(turn.isEnded());
		
	}
	

	@Test
	public void testBazaarBuyPlayerTurnMsgRequired() {
		
		turn = new BazaarSellPlayerTurn(player, canSell);
		assertTrue(turn.msgRequired().getClass().equals(GiveMeSellActionMsg.class));
		assertTrue(turn.msgRequired().getPlayer().equals(player));
		assertTrue(((GiveMeSellActionMsg)turn.msgRequired()).canSellAssistant());
		assertTrue(((GiveMeSellActionMsg)turn.msgRequired()).canSellPermitTiles());
		assertTrue(((GiveMeSellActionMsg)turn.msgRequired()).canSellPoliticCards());
		
	}

}
