package it.polimi.ingsw.cg28.model.parser;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.bonus.VictoryPointBonus;
import it.polimi.ingsw.cg28.model.BonusTile;
import it.polimi.ingsw.cg28.model.decks.TileDeck;
import it.polimi.ingsw.cg28.model.parser.BonusTileBuilder;

public class BonusTileBuilderTest {
	
	private static BonusTileBuilder builder;
	private static File f;
	
	@BeforeClass
	public static void testBefore(){
		
		builder = new BonusTileBuilder();
		f = new File("src/configurationFilesTest/BonusTile.txt");
		
	}
	
	@Test
	public void testBonusTileDeckIsNotEmpty() {

		try {
			assert(builder.build(f).size() > 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testBonusTileDeckContainsTwoTiles() {
		
		try {
			assert(builder.build(f).size() == 2);
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
	public void testBonusTileDeckIsNotNull() {

		try {
			assertNotNull(builder.build(f));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testBonusTileDeckContainsRightBonusTile() {
		
		TileDeck<BonusTile> deck = null;
		
		try {
			deck = builder.build(f);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		List<BonusTile> tiles = new ArrayList<>();
		
		while(deck.size() > 0)
			tiles.add(deck.draw());
		
		for(BonusTile tile: tiles){
			if(tile.getIdentifier().equals("Mountains")){
				assertTrue(tiles.get(0).getBonus().getValue() == 5);
				assertTrue(tiles.get(0).getBonus().getClass().equals(VictoryPointBonus.class));
			}
			if(tile.getIdentifier().equals("Plains")){
				assertTrue(tiles.get(1).getBonus().getValue() == 5);
				assertTrue(tiles.get(1).getBonus().getClass().equals(VictoryPointBonus.class));
			}
		}
				
	}

}
