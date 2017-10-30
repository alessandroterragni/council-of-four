package it.polimi.ingsw.cg28.model.parser;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.bonus.AssistantBonus;
import it.polimi.ingsw.cg28.controller.bonus.Bonus;
import it.polimi.ingsw.cg28.controller.bonus.BonusPack;
import it.polimi.ingsw.cg28.controller.bonus.DrawCardBonus;
import it.polimi.ingsw.cg28.controller.bonus.MainActionBonus;
import it.polimi.ingsw.cg28.model.Councillor;
import it.polimi.ingsw.cg28.model.GameMap;
import it.polimi.ingsw.cg28.model.Region;
import it.polimi.ingsw.cg28.model.Town;
import it.polimi.ingsw.cg28.model.parser.CouncilorsBuilder;
import it.polimi.ingsw.cg28.model.parser.MapBuilder;
import it.polimi.ingsw.cg28.model.parser.RegionBuilder;

public class MapBuilderTest {

	private static MapBuilder builder;
	private Region[] regions;
	private List<Councillor> noblesPool;
	private int councilorsForBalcony;
	private static File f;
	
	@BeforeClass
	public static void testBefore(){
	
		builder = new MapBuilder();
		f = new File("src/configurationFilesTest/Map0.txt");
		
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
			councilorsForBalcony = regionBuilder.getNumCouncilors(f3);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testMapIsNotEmpty() {

		try {
			assert(builder.build(f, regions, noblesPool, councilorsForBalcony)
					.getTownNetwork().vertexSet().size() > 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testMapContainsTwoTowns() {
		
		try {
			assert(builder.build(f, regions, noblesPool, councilorsForBalcony)
					.getTownNetwork().vertexSet().size() == 3);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test (expected = IOException.class)
	public void testIOExceptionIfFileDoesntExist() throws IOException {

		builder.build(new File("src/configurationFilesTest/IOexception"),
				regions, noblesPool, councilorsForBalcony);
		
	}
	
	@Test (expected = IOException.class)
	public void testBuildBonusIOExceptionIfFileDoesntExist() throws IOException {

		builder.buildBonus(new File("src/configurationFilesTest/IOexception"));
		
	}
	
	@Test (expected = IOException.class)
	public void testBuildEdgesIOExceptionIfFileDoesntExist() throws IOException {

		builder.buildEdges(new File("src/configurationFilesTest/IOexception"), null);
		
	}
	
	@Test (expected = IOException.class)
	public void testBuildTownsIOExceptionIfFileDoesntExist() throws IOException {

		builder.buildTowns(new File("src/configurationFilesTest/IOexception"), null, null);
		
	}
	
	@Test (expected = NullPointerException.class)
	public void testNullPointerExceptionIfFileIsNull() {

		try {
			builder.build(null, regions, noblesPool, councilorsForBalcony);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test (expected = NullPointerException.class)
	public void testNullPointerExceptionIfRegionsArrayIsNull() {

		try {
			builder.build(f, null, noblesPool, councilorsForBalcony);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	@Test (expected = NullPointerException.class)
	public void testNullPointerExceptionIfNoblesPoolIsNull() {

		try {
			builder.build(f, regions, null, councilorsForBalcony);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	@Test (expected = IllegalArgumentException.class)
	public void testIllegalArgumentExceptionIfCouncilorsForBalconyEqualsToZero() {

		try {
			builder.build(f, regions, noblesPool, 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testMapIsNotNull() {

		try {
			assertNotNull(builder.build(f, regions, noblesPool, councilorsForBalcony));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testMapContainsRightTownsName() {
		
		GameMap map = null;
		
		try {
			map = builder.build(f, regions, noblesPool, councilorsForBalcony);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		int countArkon = 0;
		
		for(Town town: map.getTownList())
			if (town.getTownName().equals("Arkon"))
				countArkon++;
		
		int countGraden = 0;
		
		for(Town town: map.getTownList())
			if (town.getTownName().equals("Graden"))
				countGraden++;
		
		int countJuvelar = 0;
		
		for(Town town: map.getTownList())
			if (town.getTownName().equals("Juvelar"))
				countJuvelar++;
		
		assertTrue(countArkon == 1 && countGraden == 1 && countJuvelar == 1);
		
	}
	
	@Test
	public void testMapContainsRightTownsAlloy() {
		
		GameMap map = null;
		
		try {
			map = builder.build(f, regions, noblesPool, councilorsForBalcony);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		int countGold = 0;
		
		for(Town town: map.getTownList())
			if (town.getAlloy().equals("Gold"))
				countGold++;
		
		int countSilver = 0;
		
		for(Town town: map.getTownList())
			if (town.getAlloy().equals("Silver"))
				countSilver++;
		
		int countKing = 0;
		
		for(Town town: map.getTownList())
			if (town.getAlloy().equals("King"))
				countKing++;
		
		assertTrue(countGold == 1 && countSilver == 1 && countKing == 1);
		
	}
	
	@Test
	public void testMapContainsRightTownsRegion() {
		
		GameMap map = null;
		
		try {
			map = builder.build(f, regions, noblesPool, councilorsForBalcony);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		int countPlains = 0;
		
		for(Town town: map.getTownList())
			if (town.getRegion().getRegionType().equals("Plains"))
				countPlains++;
		
		int countCoast = 0;
		
		for(Town town: map.getTownList())
			if (town.getRegion().getRegionType().equals("Coast"))
				countCoast++;
	
		assertTrue(countPlains == 2 && countCoast == 1);
		
	}
	
	@Test
	public void testMapContainsTownsWithSameRegionOfRegionsArray() {
		
		GameMap map = null;
		
		try {
			map = builder.build(f, regions, noblesPool, councilorsForBalcony);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		boolean check = false;
		
		for(Region region : regions)
			if (region.equals(map.getTownList().get(0).getRegion()))
				check = true;
		
		assertTrue(check);
		
		check = false;
		
		for(Region region : regions)
			if (region.equals(map.getTownList().get(1).getRegion()))
				check = true;
		
		assertTrue(check);
		
		check = false;
		
		for(Region region : regions)
			if (region.equals(map.getTownList().get(2).getRegion()))
				check = true;
		
		assertTrue(check);
		
	}
	
	@Test
	public void testMapContainsRightEdges() {
		
		GameMap map = null;
		
		try {
			map = builder.build(f, regions, noblesPool, councilorsForBalcony);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		assertTrue(map.getTownNetwork()
				.containsEdge(map.getTown("Arkon"), map.getTown("Graden")));
		
		assertTrue(map.getTownNetwork()
				.containsEdge(map.getTown("Juvelar"),map.getTown("Arkon")));
		
		assertTrue(!map.getTownNetwork()
				.containsEdge(map.getTown("Juvelar"),map.getTown("Graden")));

		
	}	
	
	@Test
	public void testMapContainsRightBonuses() {
		
		GameMap map = null;
		
		try {
			map = builder.build(f, regions, noblesPool, councilorsForBalcony);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		for(Town town : map.getTownList())
			if(town.getBonus() != null)
				if(town.getBonus().getClass().equals(BonusPack.class)){
					List<Bonus> bonuses = ((BonusPack)town.getBonus()).getBonusPack();
					assertTrue(bonuses.get(0).getClass().equals(DrawCardBonus.class));
					assertTrue(bonuses.get(0).getValue() == 2);
					assertTrue(bonuses.get(1).getClass().equals(MainActionBonus.class));
					assertTrue(bonuses.get(1).getValue() == 1);
				} else {
					assertTrue(town.getBonus().getClass().equals(AssistantBonus.class));
					assertTrue(town.getBonus().getValue() == 1);
				}
		
	}
	
	@Test
	public void testMapBalconyIsNotEmpty() {
		
		GameMap map = null;
		
		try {
			map = builder.build(f, regions, noblesPool, councilorsForBalcony);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		assertNotNull(map.getKingCouncil());
	}
	
	@Test
	public void testMapBalconyHaveFourCouncillors() {
	
		GameMap map = null;
		
		try {
			map = builder.build(f, regions, noblesPool, councilorsForBalcony);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		assertTrue(map.getKingCouncil().getCouncil().size() == 4);
			
	}
	
	@Test
	public void testKingIsHere() {
		
		GameMap map = null;
		
		try {
			map = builder.build(f, regions, noblesPool, councilorsForBalcony);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		for(Town town : map.getTownList())
			if(town.getAlloy().equals("King"))
				assertTrue(town.isKingHere());
		
	}
	
	

}
