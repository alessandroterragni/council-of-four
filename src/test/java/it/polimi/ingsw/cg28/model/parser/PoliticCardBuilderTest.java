package it.polimi.ingsw.cg28.model.parser;

import static org.junit.Assert.*;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import it.polimi.ingsw.cg28.model.PoliticCard;
import it.polimi.ingsw.cg28.model.decks.CardDeck;
import it.polimi.ingsw.cg28.model.parser.PoliticCardBuilder;

public class PoliticCardBuilderTest {

	private static PoliticCardBuilder builder;
	private static File f;
	
	@BeforeClass
	public static void testBefore(){
		
		builder = new PoliticCardBuilder();
		f = new File("src/configurationFilesTest/PoliticCard.txt");
		
	}
	
	@Test
	public void testPoliticCardsDeckIsNotEmpty() {

		try {
			assert(builder.build(f).size() > 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testPoliticCardsDeckContainsFourteenCards() {
		try {
			assert(builder.build(f).size() == 14);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test (expected = IOException.class)
	public void testIOExceptionIfFileDoesntExist() throws IOException {

		builder.build(new File("src/configurationFilesTest/IOexception"));
		
	}
	
	@Test (expected = NullPointerException.class)
	public void testNullPointerExceptionIfFileIsNull() {

		try {
			builder.build(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testPoliticCardsDeckIsNotNull() {
		
		try {
			assertNotNull(builder.build(f));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testPoliticCardsDeckContainsRightCards() {
		
		CardDeck<PoliticCard> deck = null;
		
		try {
			deck = builder.build(f);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		int[] colors = new int[7];
		
		List<PoliticCard> cards = new ArrayList<>();
		
		while(deck.size() > 0)
			cards.add(deck.draw());
		
		for(PoliticCard card: cards){
			if (Color.WHITE.equals(card.getHouseColor()))
				colors[0] ++;
			if (Color.PINK.equals(card.getHouseColor()))
				colors[1] ++;
			if (Color.ORANGE.equals(card.getHouseColor()))
				colors[2] ++;
			if (Color.BLACK.equals(card.getHouseColor()))
				colors[3] ++;
			if (Color.MAGENTA.equals(card.getHouseColor()))
				colors[4] ++;
			if (Color.BLUE.equals(card.getHouseColor()))
				colors[5] ++;
			if (card.getHouseColor() == null)
				colors[6] ++;
			
		}
			
		assertTrue(colors[0] == 2);
		assertTrue(colors[1] == 2);
		assertTrue(colors[2] == 2);
		assertTrue(colors[3] == 2);
		assertTrue(colors[4] == 2);
		assertTrue(colors[5] == 2);
		assertTrue(colors[6] == 2);
		
	}

}
