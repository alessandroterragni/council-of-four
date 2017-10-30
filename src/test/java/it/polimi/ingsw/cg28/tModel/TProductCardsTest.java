package it.polimi.ingsw.cg28.tModel;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.tmodel.TProductCards;

public class TProductCardsTest {

	private int price;
	private Color[] cards;
	private TProductCards productCards;
	
	@Before
	public void set(){
		
		cards = new Color[4];
		cards[0] = Color.BLACK;
		cards[1] = Color.WHITE;
		cards[2] = null;
		cards[3] = Color.BLACK;
		price = 3;
		
	}

	@Test (expected = IllegalArgumentException.class)
	public void TProductCardsTestWithPriceLowerThanZero() {
		productCards = new TProductCards(-3,"Owner", cards);
	}
	
	@Test (expected = NullPointerException.class)
	public void TProductCardsTestWithCardsNull() {
		productCards = new TProductCards(price,"Owner", null);
	}
	
	@Test (expected = NullPointerException.class)
	public void TProductCardsTestWithNullOwner() {
		productCards = new TProductCards(price, null , cards);
	}
	
	@Test
	public void TProductCardsTestGetters() {
		
		productCards = new TProductCards(price, "Owner" , cards);
		Color[] cardsReturned = productCards.getCards();
		assertTrue(productCards.getCards().equals(cards));
		assertTrue(cardsReturned[0].equals(cards[0]));
		assertTrue(cardsReturned[1].equals(cards[1]));
		assertTrue(cardsReturned[2] == null);
		assertTrue(cardsReturned[3].equals(cards[3]));
		assertTrue(productCards.getPrice() == 3);
		assertTrue(productCards.getPlayerOwner().equals("Owner"));
		
	}
}
