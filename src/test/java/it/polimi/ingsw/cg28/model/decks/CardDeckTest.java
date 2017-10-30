package it.polimi.ingsw.cg28.model.decks;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.model.PoliticCard;

public class CardDeckTest {
	
	CardDeck<PoliticCard> deck;
	
	@Before
	public void before() {
		LinkedList<PoliticCard> newCardDeck = new LinkedList<>();
		
		newCardDeck.add(new PoliticCard(Color.ORANGE, false));
		newCardDeck.add(new PoliticCard(Color.BLACK, false));
		newCardDeck.add(new PoliticCard(null, true));
		
		deck = new CardDeck<>(newCardDeck);
	}

	@Test (expected = NullPointerException.class)
	public void testCardDeckConstructorWithNullParameter() {
		new CardDeck<>(null);
	}
	
	@Test
	public void testCardDeckConstructor() {
		LinkedList<PoliticCard> cardList = new LinkedList<>();
		cardList.add(new PoliticCard(Color.ORANGE, false));
		CardDeck<PoliticCard> newDeck = new CardDeck<>(cardList);
		assertTrue(!newDeck.equals(null));
		assertTrue(newDeck.drawPile.containsAll(cardList));
		assertTrue(!newDeck.getDiscardPile().equals(null));
	}
	
	@Test
	public void testGetDiscardPile() {
		List<PoliticCard> discPile = deck.getDiscardPile();
		assertTrue(!discPile.equals(null));
		PoliticCard c1 = new PoliticCard(Color.BLACK, false);
		PoliticCard c2 = new PoliticCard(null, true);
		deck.getDiscardPile().add(c1);
		deck.getDiscardPile().add(c2);
		discPile = deck.getDiscardPile();
		assertTrue(discPile.contains(c1));
		assertTrue(discPile.contains(c2));
	}
	
	@Test
	public void testReshuffleDiscards() {
		List<PoliticCard> discPile = new ArrayList<>();
		PoliticCard c1 = new PoliticCard(Color.BLACK, false);
		PoliticCard c2 = new PoliticCard(null, true);
		deck.getDiscardPile().add(c1);
		deck.getDiscardPile().add(c2);
		discPile = deck.getDiscardPile();
		assertTrue(discPile.contains(c1));
		assertTrue(discPile.contains(c2));
		deck.reshuffleDiscards();
		discPile = deck.getDiscardPile();
		assertTrue(!discPile.contains(c1));
		assertTrue(!discPile.contains(c2));
	}

}
