package it.polimi.ingsw.cg28.controller.bonus;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.controller.PlayerTurn;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.Player;
import it.polimi.ingsw.cg28.model.PoliticCard;
import it.polimi.ingsw.cg28.model.decks.CardDeck;
import it.polimi.ingsw.cg28.model.parser.ModelTestConfiguration;

public class DrawCardBonusTest {

	
	private ModelStatus model;
	private PlayerID [] players;
	private CardDeck<PoliticCard> cardDeck;

	
	@Before
	public void before(){
		
		players = new PlayerID[1];
		players[0] = new PlayerID("Player1");
		
		ModelTestConfiguration config = new ModelTestConfiguration();
		model = config.buildModelTest(players);
		model.getPlayer(players[0]).setTurn(new PlayerTurn(players[0]));
		cardDeck = model.getPoliticCardsDeck();
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testDrawCardBonusWithNegativeParameter() {
		new DrawCardBonus(-50);
	}
	
	@Test
	public void testDrawCardBonus() {
		assertNotNull("the bonus is correctly constructed",new DrawCardBonus(57));
	}

	@Test
	public void testGetBonus() {
		DrawCardBonus bonus = new DrawCardBonus(10);
		bonus.setCardDeck(cardDeck);
		Player p = model.getPlayer(players[0]);
		int oldNumberOfCards = p.getPoliticCardsHand().size();
		bonus.getBonus(p);
		int newNumberOfCards = p.getPoliticCardsHand().size();
		assertTrue("The number of cards of the player is correctly incremented", newNumberOfCards==oldNumberOfCards+10);
	}
	
	@Test (expected = NullPointerException.class)
	public void testGetBonusWithNullParameter() {
		Bonus bonus = new DrawCardBonus(10);
		bonus.getBonus(null);
	}

	@Test
	public void testGetValue() {
		Bonus bonus = new DrawCardBonus(10);
		assertTrue(bonus.getValue()==10);
	}
	
	@Test
	public void testSetCardDeck() {
		DrawCardBonus bonus = new DrawCardBonus(10);
		bonus.setCardDeck(cardDeck);
		assertNotNull(bonus);
	}
}
