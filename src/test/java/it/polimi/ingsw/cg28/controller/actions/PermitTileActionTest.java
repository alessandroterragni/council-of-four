package it.polimi.ingsw.cg28.controller.actions;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.controller.PlayerTurn;
import it.polimi.ingsw.cg28.exception.InvalidActionException;
import it.polimi.ingsw.cg28.model.BusinessPermitTile;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.Player;
import it.polimi.ingsw.cg28.model.PoliticCard;
import it.polimi.ingsw.cg28.model.Region;
import it.polimi.ingsw.cg28.model.decks.CardDeck;
import it.polimi.ingsw.cg28.model.parser.ModelTestConfiguration;

public class PermitTileActionTest {

	private PermitTileAction action;
	private ModelStatus model;
	private PlayerID [] players;
	private CardDeck<PoliticCard> deck;
	private List<PoliticCard> chosen = new ArrayList<>();
	private Region region;
	
	@Before
	public void before(){
		
		
		players = new PlayerID[2];
		players[0] = new PlayerID("Player1");
		players[1] = new PlayerID("Player2");
		
		ModelTestConfiguration config = new ModelTestConfiguration();
		model = config.buildModelTest(players);
		model.getPlayer(players[0]).setTurn(new PlayerTurn(players[0]));
			
		deck = model.getPoliticCardsDeck();
		region = model.getRegion(1);
		
		PoliticCard card = new PoliticCard(null, true);
		model.getPlayer(players[0]).getPoliticCardsHand().add(card);
		
		model.getPlayer(players[0]).getCoins().increment(20);
		model.getPlayer(players[1]).getCoins().increment(20);
		
		chosen.add(card);

	}
	
	@Test (expected=NullPointerException.class)
	public void testPermitTileActionWithNullParameter() throws InvalidActionException {
		action = new PermitTileAction(null, 0, null, null);
	}
	
	@Test (expected=NullPointerException.class)
	public void testPermitTileActionActionWithNullRegion() throws InvalidActionException {
		action = new PermitTileAction(null,0,chosen,deck); 
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testPermitTileActionActionWithTilePositionLowerThanZero() throws InvalidActionException {
		action = new PermitTileAction(region,-2,chosen,deck); 
	}
	
	
	@Test (expected=NullPointerException.class)
	public void testPermitTileActionWithNullchosen() throws InvalidActionException {
		action = new PermitTileAction(region,0,null,deck); 
	}

	
	@Test (expected=NullPointerException.class)
	public void testPermitTileActionWithNullDeck() throws InvalidActionException {
		action = new PermitTileAction(region,0,chosen,null); 
	}
	
	@Test
	public void testEmporiumKingAction() throws InvalidActionException {
		action = new PermitTileAction(region,0,chosen,deck); 
		assertTrue("the action has been correctly constructed",!action.equals(null));
	}
	
	@Test 
	public void testIfTheDiscardCardsArePutInthediscardPile() throws InvalidActionException{
		
		List<PoliticCard> discardPile = model.getPoliticCardsDeck().getDiscardPile();
		Player p1 = model.getPlayer(players[0]);
		PermitTileAction action =new PermitTileAction(region,0,chosen,deck);
		action.act(p1);
		assertTrue("The discard cards are correctly in the discard pile",discardPile.containsAll(chosen));
		
	}
	
	@Test
	public void testCoinsToPayWithOneCard() throws InvalidActionException{
		Player p1 = model.getPlayer(players[0]);
		PermitTileAction action =new PermitTileAction(region,0,chosen,deck);
		int coinsToPay = action.coinsToPay();
	
		assertTrue("the coins to Pay are correct",coinsToPay==10);
		action.act(p1);
		assertTrue("the coins are correctly decremented",p1.getCoins().getValue()==20);

	}
	
	@Test
	public void testCoinsToPayWith4CardAllColors() throws InvalidActionException{
		PoliticCard card1 = new PoliticCard(null, true);
		PoliticCard card2 = new PoliticCard(null, true);
		PoliticCard card3 = new PoliticCard(null, true);
		
		chosen.add(card1);
		chosen.add(card2);
		chosen.add(card3);
	
		PermitTileAction action =new PermitTileAction(region,0,chosen,deck);
		int coinsToPay = action.coinsToPay();
		assertTrue("the coins to Pay are correct",coinsToPay==0);
		
	}
	
	@Test (expected =InvalidActionException.class)
	public void testCoinsToPayWith5Cards() throws InvalidActionException{
		PoliticCard card2 = new PoliticCard(null, true);
		PoliticCard card3 = new PoliticCard(null, true);
		PoliticCard card4 = new PoliticCard(null, true);
		PoliticCard card5 = new PoliticCard(null, true);
		
		chosen.add(card2);
		chosen.add(card3);
		chosen.add(card4);
		chosen.add(card5);
		
		PermitTileAction action =new PermitTileAction(region,0,chosen,deck);
		
		action.coinsToPay();
	
	}
	
	@Test (expected =InvalidActionException.class)
	public void testCoinsToPayWithNOCard() throws InvalidActionException{
		chosen.remove(0);
		
		PermitTileAction action =new PermitTileAction(region,0,chosen,deck);

		action.coinsToPay();
		
		}
	
	@Test 
	public void testIfThePlayerGainTheTile() throws InvalidActionException{
		Player p1 = model.getPlayer(players[0]);
		BusinessPermitTile tile = region.getTile(0);
		PermitTileAction action =new PermitTileAction(region,0,chosen,deck);
		action.act(p1);
		assertTrue("the player has gained the tile",p1.getPossessedTiles().contains(tile));
		
	}
	
	
	@Test 
	public void testIfANewTileisPutOnTheRegion() throws InvalidActionException{
		Player p1 = model.getPlayer(players[0]);
		BusinessPermitTile tile = region.getTile(0);
		PermitTileAction action =new PermitTileAction(region,0,chosen,deck);
		action.act(p1);
		assertTrue("the region Tile is changed",!region.getTile(0).equals(tile));
		
	}
}
