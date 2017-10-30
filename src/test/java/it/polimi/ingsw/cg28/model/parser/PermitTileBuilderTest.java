package it.polimi.ingsw.cg28.model.parser;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.bonus.AssistantBonus;
import it.polimi.ingsw.cg28.controller.bonus.BonusPack;
import it.polimi.ingsw.cg28.controller.bonus.CoinBonus;
import it.polimi.ingsw.cg28.controller.bonus.DrawCardBonus;
import it.polimi.ingsw.cg28.controller.bonus.NobilityBonus;
import it.polimi.ingsw.cg28.controller.bonus.VictoryPointBonus;
import it.polimi.ingsw.cg28.model.BusinessPermitTile;
import it.polimi.ingsw.cg28.model.Councillor;
import it.polimi.ingsw.cg28.model.Region;
import it.polimi.ingsw.cg28.model.decks.TileDeck;
import it.polimi.ingsw.cg28.model.parser.CouncilorsBuilder;
import it.polimi.ingsw.cg28.model.parser.PermitTileBuilder;
import it.polimi.ingsw.cg28.model.parser.RegionBuilder;

public class PermitTileBuilderTest {

	private static PermitTileBuilder builder;
	private Region[] regions;
	private List<Councillor> noblesPool;
	private static File f;
	
	@BeforeClass
	public static void testBefore(){
		
		builder = new PermitTileBuilder();
		f = new File("src/configurationFilesTest/PermitTile.txt");
		
	}
	
	@Before
	public void testNoblesPoolAndRegions(){
		
		CouncilorsBuilder builderCouncilors;
		builderCouncilors = new CouncilorsBuilder();
		File f2 = new File("src/configurationFilesTest/Councilors.txt");
		try {
			noblesPool = builderCouncilors.build(f2);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		RegionBuilder regionBuilder = new RegionBuilder();
		
		File f3 = new File("src/configurationFilesTest/Regions.txt");
		try {
			regions = regionBuilder.build(f3, noblesPool);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testPermitTileDeckIsNotEmpty() {

		try {
			builder.build(f, regions);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(Region region : regions){
			assert(region.getDeck().size() > 0);
		}
		
	}
	
	@Test
	public void testEachRegionPermitTileDeckContainsTwoTiles() {
		
		try {
			builder.build(f, regions);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(Region region : regions){
			assert(region.getDeck().size() == 4);
		}
		
	}
	
	@Test (expected = IOException.class)
	public void testIOExceptionIfFileDoesntExist() throws IOException {

		builder.build(new File("src/configurationFilesTest/IOexception"), regions);
		
	}
	
	@Test (expected = NullPointerException.class)
	public void testNullPointerExceptionIfFileIsNull() {

		try {
			builder.build(null, regions);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test (expected = NullPointerException.class)
	public void testNullPointerExceptionIfRegionsArrayIsNull() {

		try {
			builder.build(f, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testPermitTileDeckIsNotNull() {

		try {
			builder.build(f, regions);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(Region region : regions){
			assertNotNull(region.getDeck());
		}
		
	}
	
	@Test
	public void testPermitTileDeckContainsRightPermitTiles() {
		
		TileDeck<BusinessPermitTile> deckRegion0 = null;
		TileDeck<BusinessPermitTile> deckRegion1 = null;
		TileDeck<BusinessPermitTile> deckRegion2 = null;
		
		try {
			builder.build(f, regions);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		deckRegion0 = regions[0].getDeck();
		deckRegion1 = regions[1].getDeck();
		deckRegion2 = regions[2].getDeck();
		
		List<BusinessPermitTile> tiles = new ArrayList<>();
		
		while(deckRegion0.size() > 0)
			tiles.add(deckRegion0.draw());
		
		assertTrue(checkCityonTiles(tiles, "Arkon"));
		assertTrue(checkCityonTiles(tiles, "Burgen"));
		
		BonusPack bonuses = (BonusPack) takeTileWithName(tiles, "Arkon").getBonuses();
		
		assertTrue(bonuses.getBonusPack().get(0).getClass().equals(VictoryPointBonus.class));
		assertTrue(bonuses.getBonusPack().get(0).getValue() == 4);
		assertTrue(bonuses.getBonusPack().get(1).getClass().equals(CoinBonus.class));
		assertTrue(bonuses.getBonusPack().get(1).getValue() == 3);
		
		bonuses = (BonusPack) takeTileWithName(tiles, "Burgen").getBonuses();
		
		assertTrue(bonuses.getBonusPack().get(0).getClass().equals(DrawCardBonus.class));
		assertTrue(bonuses.getBonusPack().get(0).getValue() == 3);
		assertTrue(bonuses.getBonusPack().get(1).getClass().equals(AssistantBonus.class));
		assertTrue(bonuses.getBonusPack().get(1).getValue() == 1);
		
		tiles.clear();
		
		while(deckRegion1.size() > 0)
			tiles.add(deckRegion1.draw());
		
		assertTrue(checkCityonTiles(tiles, "Framek"));
		assertTrue(checkCityonTiles(tiles, "Graden"));
		
		tiles.clear();
		
		while(deckRegion2.size() > 0)
			tiles.add(deckRegion2.draw());
		
		assertTrue(checkCityonTiles(tiles, "Merkatim"));
		assertTrue(checkCityonTiles(tiles, "Lyram"));
		assertTrue(checkCityonTiles(tiles, "Naris"));
		assertTrue(checkCityonTiles(tiles, "Osium"));
		
		bonuses = (BonusPack) takeTileWithName(tiles, "Osium").getBonuses();
		
		assertTrue(bonuses.getBonusPack().get(0).getClass().equals(NobilityBonus.class));
		assertTrue(bonuses.getBonusPack().get(0).getValue() == 1);
		assertTrue(bonuses.getBonusPack().get(1).getClass().equals(VictoryPointBonus.class));
		assertTrue(bonuses.getBonusPack().get(1).getValue() == 1);
			
	}
	
	private boolean checkCityonTiles(List<BusinessPermitTile> tiles, String letterCodes){
		
		for(BusinessPermitTile tile: tiles)
			for(String string : tile.getLetterCodes())
				if(string.equals(letterCodes))
					return true;
		
		return false;
		
	}
	
	private BusinessPermitTile takeTileWithName(List<BusinessPermitTile> tiles, String letterCodes){
		
		for(BusinessPermitTile tile: tiles)
			for(String string : tile.getLetterCodes())
				if(string.equals(letterCodes))
					return tile;
		
		return null;
		
	}

}
