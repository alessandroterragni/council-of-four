package it.polimi.ingsw.cg28.model.parser;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.bonus.VictoryPointBonus;
import it.polimi.ingsw.cg28.model.KingRewardTile;
import it.polimi.ingsw.cg28.model.parser.KingRewardsBuilder;

public class KingRewardsBuilderTest {

	private static KingRewardsBuilder builder;
	private static File f;
	
	@BeforeClass
	public static void testBefore(){
		
		builder = new KingRewardsBuilder();
		f = new File("src/configurationFilesTest/KingRewards.txt");
		
	}
	
	@Test
	public void testKingRewardsDeckIsNotEmpty() {

		try {
			assert(builder.build(f).size() > 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testKingRewardsDeckContainsTwoTiles() {
		
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
	public void testKingRewardsDeckIsNotNull() {

		try {
			assertNotNull(builder.build(f));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testKingRewardDeckContainsRightKingRewardTile() {
		
		List<KingRewardTile> tiles = null;
		
		try {
			tiles = builder.build(f);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		assertTrue(tiles.get(0).getBonus().getValue() == 25);
		assertTrue(tiles.get(0).getBonus().getClass().equals(VictoryPointBonus.class));
		
		assertTrue(tiles.get(1).getBonus().getValue() == 18);
		assertTrue(tiles.get(1).getBonus().getClass().equals(VictoryPointBonus.class));
		
	}

}
