package it.polimi.ingsw.cg28.controller.actions;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.controller.PlayerTurn;
import it.polimi.ingsw.cg28.exception.InvalidActionException;
import it.polimi.ingsw.cg28.model.Balcony;
import it.polimi.ingsw.cg28.model.Councillor;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.Player;
import it.polimi.ingsw.cg28.model.PoliticCard;
import it.polimi.ingsw.cg28.model.Town;
import it.polimi.ingsw.cg28.model.decks.CardDeck;
import it.polimi.ingsw.cg28.model.parser.ModelTestConfiguration;

public class EmporiumKingActionTest {
	

	private EmporiumKingAction action;
	private ModelStatus model;
	private PlayerID [] players;
	private CardDeck<PoliticCard> deck;
	private List<PoliticCard> chosen = new ArrayList<>();
	
	@Before
	public void before(){
		
		players = new PlayerID[2];
		players[0] = new PlayerID("Player1");
		players[1] = new PlayerID("Player2");
		
		ModelTestConfiguration config = new ModelTestConfiguration();
		model = config.buildModelTest(players);
		model.getPlayer(players[0]).setTurn(new PlayerTurn(players[0]));
			
		deck = model.getPoliticCardsDeck();
		
		PoliticCard card = new PoliticCard(null, true);
		model.getPlayer(players[0]).getPoliticCardsHand().add(card);
		
		model.getPlayer(players[0]).getCoins().increment(20);
		model.getPlayer(players[1]).getCoins().increment(20);
		
		chosen.add(card);

	}
	
	@Test (expected=NullPointerException.class)
	public void testEmporiumKingActionWithNullParameter() throws InvalidActionException {
		action = new EmporiumKingAction(null, null, null, null,null);
	}
	
	@Test (expected=NullPointerException.class)
	public void testEmporiumKingActionWithNullMap() throws InvalidActionException {
		action = new EmporiumKingAction(null, model.getMap().getTown("Arkon"),
				model.getMap().getKingCouncil(), chosen,deck);
	}
	
	@Test (expected=NullPointerException.class)
	public void testEmporiumKingActionWithNullTown() throws InvalidActionException {
		action = new EmporiumKingAction(model.getMap(), null,
				model.getMap().getKingCouncil(), chosen,deck);
	}
	
	@Test (expected=NullPointerException.class)
	public void testEmporiumKingActionWithNullTKingCouncil() throws InvalidActionException {
		action = new EmporiumKingAction(model.getMap(), model.getMap().getTown("Arkon"),
				null, chosen,deck);
	}
	
	@Test (expected=NullPointerException.class)
	public void testEmporiumKingActionWithNullChosen() throws InvalidActionException {
		action = new EmporiumKingAction(model.getMap(), model.getMap().getTown("Arkon"),
				model.getMap().getKingCouncil(), null ,deck);
	}
	
	@Test (expected=NullPointerException.class)
	public void testEmporiumKingActionWithNullDeck() throws InvalidActionException {
		action = new EmporiumKingAction(model.getMap(), model.getMap().getTown("Arkon"),
				model.getMap().getKingCouncil(), chosen ,null);
	}
	
	@Test
	public void testEmporiumKingAction() throws InvalidActionException {
		action = new EmporiumKingAction(model.getMap(), model.getMap().getTown("Arkon"),
				model.getMap().getKingCouncil(), chosen,deck); 
		assertTrue("the action has been correctly constructed",!action.equals(null));
	}

	@Test (expected=NullPointerException.class)
	public void testActPlayerWithNullParameter() throws InvalidActionException {
		EmporiumKingAction action = new EmporiumKingAction(model.getMap(), model.getMap().getTown("Arkon"),
				model.getMap().getKingCouncil(), chosen,deck); 
		action.act(null);
	}
	
	@Test 
	public void testActWithNoEmporiuminTheTown() throws InvalidActionException {
		
		Player p = model.getPlayer(players[0]);
		EmporiumKingAction action = new EmporiumKingAction(model.getMap(), model.getMap().getTown("Arkon"),
				model.getMap().getKingCouncil(), chosen,deck); 
		action.act(p);
		assertTrue("The number of assistants has unchanged",p.getAssistants().getValue()==1);
	}

	@Test 
	public void testActWith1EmporiuminTheTown() throws InvalidActionException {
		
		Player p1 = model.getPlayer(players[0]);
		Player p2 = model.getPlayer(players[1]);
		
		Town arkon = model.getMap().getTown("Arkon");
		
		model.getMap().addEmporium(p2.getID(), arkon);
		
		EmporiumKingAction action = new EmporiumKingAction(model.getMap(), arkon,
				model.getMap().getKingCouncil(), chosen,deck);
	
		action.act(p1);
		assertTrue("The number of assistants has been correctly decremented", p1.getAssistants().getValue()==0);
	}
	
	@Test 
	public void testIfTheDiscardCardsArePutInthediscardPile() throws InvalidActionException{
		
		List<PoliticCard> discardPile = model.getPoliticCardsDeck().getDiscardPile();
		Player p1 = model.getPlayer(players[0]);
		Town arkon = model.getMap().getTown("Arkon");
		EmporiumKingAction action = new EmporiumKingAction(model.getMap(), arkon,
				model.getMap().getKingCouncil(), chosen, deck);
		action.act(p1);
		assertTrue("The discard cards are correctly in the discard pile",discardPile.containsAll(chosen));
		
	}

	@Test
	public void TestTheBuildingOftheEmporium() throws InvalidActionException{
		Player p1 = model.getPlayer(players[0]);
		int numbEmpPrec = p1.getEmporiumNumber();
		Town arkon = model.getMap().getTown("Arkon");
		assertTrue("The player p1 has not an emporium in the city",!arkon.hasEmporium(p1.getID()));
		EmporiumKingAction action = new EmporiumKingAction(model.getMap(), arkon,
				model.getMap().getKingCouncil(), chosen, deck);
		action.act(p1);
		assertTrue("Now the player p1 has an emporium in the city",arkon.hasEmporium(p1.getID()));
		assertTrue(p1.getEmporiumNumber() == numbEmpPrec -1);
	}
	
	@Test
	public void TestKingCorrectlyMoved() throws InvalidActionException{
		
		Player p1 = model.getPlayer(players[0]);
		Town arkon = model.getMap().getTown("Arkon");
		assertTrue("The player p1 has not an emporium in the city",!arkon.hasEmporium(p1.getID()));
		assertTrue(model.getMap().getTown("Juvelar").isKingHere());
		assertFalse(model.getMap().getTown("Arkon").isKingHere());
		EmporiumKingAction action = new EmporiumKingAction(model.getMap(), arkon,
				model.getMap().getKingCouncil(), chosen, deck);
		action.act(p1);
		assertFalse(model.getMap().getTown("Juvelar").isKingHere());
		assertTrue(model.getMap().getTown("Arkon").isKingHere());
	}
	
	@Test
	public void testCoinsToPayWithOneCardAllColors() throws InvalidActionException{
		Player p1 = model.getPlayer(players[0]);
		Town arkon = model.getMap().getTown("Arkon");
		EmporiumKingAction action = new EmporiumKingAction(model.getMap(), arkon,
				model.getMap().getKingCouncil(), chosen, deck);
		int coinsToPay = action.coinsToPay();
		
		assertTrue("the coins to Pay are correct",coinsToPay==12);
		action.act(p1);
		assertTrue("the coins are correctly decremented",p1.getCoins().getValue()==18);

	}
	
	@Test
	public void testCoinsToPayWith4CardAllColors() throws InvalidActionException{
		PoliticCard card1 = new PoliticCard(null, true);
		PoliticCard card2 = new PoliticCard(null, true);
		PoliticCard card3 = new PoliticCard(null, true);
		
		chosen.add(card1);
		chosen.add(card2);
		chosen.add(card3);
	
		Town arkon = model.getMap().getTown("Arkon");
		EmporiumKingAction action = new EmporiumKingAction(model.getMap(), arkon,
				model.getMap().getKingCouncil(), chosen, deck);
		int coinsToPay = action.coinsToPay();
		assertTrue("the coins to Pay are correct",coinsToPay==2);
		
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
	
		Town arkon = model.getMap().getTown("Arkon");
		EmporiumKingAction action = new EmporiumKingAction(model.getMap(), arkon,
				model.getMap().getKingCouncil(), chosen, deck);
		action.coinsToPay();
	
	}

	
	@Test (expected =InvalidActionException.class)
	public void testCoinsToPayWithNOCard() throws InvalidActionException{
		
		chosen.clear();
		Town arkon = model.getMap().getTown("Arkon");
		EmporiumKingAction action = new EmporiumKingAction(model.getMap(), arkon,
				model.getMap().getKingCouncil(), chosen, deck);

		action.coinsToPay();
		
		}
	

	@Test 
	public void testCoinsToPayWith2NormalCards() throws InvalidActionException{
		
		Player p1 = model.getPlayer(players[0]);
		chosen.clear();
		p1.getPoliticCardsHand().clear();
		PoliticCard card1 = new PoliticCard(Color.BLACK,false);
		PoliticCard card2 = new PoliticCard(Color.BLUE, false);
		p1.getPoliticCardsHand().add(card1);
		p1.getPoliticCardsHand().add(card2);
		chosen.add(card1);
		chosen.add(card2);
		
		Balcony kingBalcony = model.getMap().getKingCouncil();
		kingBalcony.addCouncillor(new Councillor(Color.BLACK));
		kingBalcony.addCouncillor(new Councillor(Color.BLUE));
		Town arkon = model.getMap().getTown("Arkon");
		EmporiumKingAction action = new EmporiumKingAction(model.getMap(), arkon,
				kingBalcony, chosen, deck);

		int coinsToPay=action.coinsToPay();
		
		assertTrue("the coins to Pay are correct",coinsToPay==9);
		action.act(p1);
		assertTrue("the coins are correctly decremented",p1.getCoins().getValue()==21);
		
		}
		
	
}
