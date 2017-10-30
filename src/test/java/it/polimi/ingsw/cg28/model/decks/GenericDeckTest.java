package it.polimi.ingsw.cg28.model.decks;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.bonus.CoinBonus;
import it.polimi.ingsw.cg28.controller.bonus.NobilityBonus;
import it.polimi.ingsw.cg28.controller.bonus.VictoryPointBonus;
import it.polimi.ingsw.cg28.model.BusinessPermitTile;
import it.polimi.ingsw.cg28.model.PoliticCard;

public class GenericDeckTest {
	
	GenericDeck<BusinessPermitTile> tileDeck;
	GenericDeck<PoliticCard> cardDeck;
	String[] letters = {"Arkon", "Juvelar"};
	
	@Before
	public void before() {
		LinkedList<BusinessPermitTile> newTileDeck = new LinkedList<>();
		LinkedList<PoliticCard> newCardDeck = new LinkedList<>();
		
		newTileDeck.add(new BusinessPermitTile(new VictoryPointBonus(4), letters));
		newTileDeck.add(new BusinessPermitTile(new CoinBonus(1), letters));
		newTileDeck.add(new BusinessPermitTile(new NobilityBonus(2), letters));
		newCardDeck.add(new PoliticCard(Color.ORANGE, false));
		newCardDeck.add(new PoliticCard(Color.BLACK, false));
		newCardDeck.add(new PoliticCard(null, true));
		
		tileDeck = new TileDeck<>(newTileDeck);
		cardDeck = new CardDeck<>(newCardDeck);
	}

	@Test
	public void testGenericDeckDraw() {
		BusinessPermitTile drawnTile = tileDeck.draw();
		PoliticCard drawnCard = cardDeck.draw();
		assertTrue(drawnTile.getBonuses().getClass().equals(new VictoryPointBonus(4).getClass())
				|| drawnTile.getBonuses().getClass().equals(new CoinBonus(1).getClass())
				|| drawnTile.getBonuses().getClass().equals(new NobilityBonus(2).getClass()));
		assertTrue(drawnTile.getBonuses().getValue() == 4
				|| drawnTile.getBonuses().getValue() == 1
				|| drawnTile.getBonuses().getValue() == 2);
		assertTrue(drawnTile.getLetterCodes().equals(letters));
		assertTrue(drawnCard.sameCol(Color.ORANGE) || drawnCard.sameCol(Color.BLACK) || drawnCard.isAllColors());
		assertTrue(!tileDeck.peek().equals(drawnTile));
		assertTrue(!cardDeck.peek().equals(drawnCard));
	}
	
	@Test
	public void testGenericDeckPeek() {
		BusinessPermitTile drawnTile = tileDeck.peek();
		PoliticCard drawnCard = cardDeck.peek();
		assertTrue(drawnTile.getBonuses().getClass().equals(new VictoryPointBonus(4).getClass())
				|| drawnTile.getBonuses().getClass().equals(new CoinBonus(1).getClass())
				|| drawnTile.getBonuses().getClass().equals(new NobilityBonus(2).getClass()));
		assertTrue(drawnTile.getBonuses().getValue() == 4
				|| drawnTile.getBonuses().getValue() == 1
				|| drawnTile.getBonuses().getValue() == 2);
		assertTrue(drawnTile.getLetterCodes().equals(letters));
		assertTrue(drawnCard.sameCol(Color.ORANGE) || drawnCard.sameCol(Color.BLACK) || drawnCard.isAllColors());
		assertTrue(tileDeck.peek().equals(drawnTile));
		assertTrue(cardDeck.peek().equals(drawnCard));
	}
	
	@Test
	public void testGenericDeckSize() {
		assertTrue(tileDeck.size() == 3);
		assertTrue(cardDeck.size() == 3);
	}
	
}