package it.polimi.ingsw.cg28.model.decks;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.bonus.AssistantBonus;
import it.polimi.ingsw.cg28.controller.bonus.CoinBonus;
import it.polimi.ingsw.cg28.controller.bonus.NobilityBonus;
import it.polimi.ingsw.cg28.controller.bonus.VictoryPointBonus;
import it.polimi.ingsw.cg28.model.BusinessPermitTile;

public class TileDeckTest {
	
	TileDeck<BusinessPermitTile> deck;
	String[] letters = {"Arkon", "Juvelar"};
	
	@Before
	public void before() {
		LinkedList<BusinessPermitTile> newTileDeck = new LinkedList<>();
		
		newTileDeck.add(new BusinessPermitTile(new VictoryPointBonus(4), letters));
		newTileDeck.add(new BusinessPermitTile(new CoinBonus(1), letters));
		newTileDeck.add(new BusinessPermitTile(new NobilityBonus(2), letters));
		
		deck = new TileDeck<>(newTileDeck);
	}

	@Test (expected = NullPointerException.class)
	public void testTileDeckConstructorWithNullParameter() {
		new TileDeck<BusinessPermitTile>(null);
	}
	
	@Test
	public void testTileDeckConstructor() {
		LinkedList<BusinessPermitTile> tileList = new LinkedList<>();
		tileList.add(new BusinessPermitTile(new VictoryPointBonus(100), letters));
		TileDeck<BusinessPermitTile> newDeck = new TileDeck<>(tileList);
		assertTrue(!newDeck.equals(null));
		assertTrue(newDeck.drawPile.containsAll(tileList));
	}
	
	@Test (expected = NullPointerException.class)
	public void testEnqueueWithNullParameter() {
		deck.enqueue(null);
	}
	
	@Test
	public void testEnqueue() {
		BusinessPermitTile peeked = deck.peek();
		deck.enqueue(peeked);
		assertTrue(!deck.peek().getBonuses().getClass().equals(peeked.getBonuses().getClass()));
		assertTrue(!(deck.peek().getBonuses().getValue() == peeked.getBonuses().getValue()));
		assertTrue(deck.drawPile.getLast().equals(peeked));
	}
	
	@Test (expected = NullPointerException.class)
	public void testTakeTileWithNullParameter() {
		deck.takeTile(null);
	}
	
	@Test (expected = NoSuchElementException.class)
	public void testTakeTileWithUnexistingTileParameter() {
		deck.takeTile(new BusinessPermitTile(new AssistantBonus(1), letters));
	}
	
	@Test
	public void testTakeTile() {
		BusinessPermitTile taken = deck.takeTile(deck.peek());
		assertTrue(!deck.drawPile.contains(taken));
	}
	
}
