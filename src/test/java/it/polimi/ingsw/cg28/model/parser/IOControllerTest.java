package it.polimi.ingsw.cg28.model.parser;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.Region;
import it.polimi.ingsw.cg28.model.parser.IOController;

public class IOControllerTest {
	
	
	private File[] config;
	private ModelStatus model;
	private IOController controller;

	@Before
	public void test() {
		
		config = new File[8];
		File councilors = new File("src/configurationFilesTest/Councilors.txt");
		config[0] = councilors;
		File regions = new File("src/configurationFilesTest/Regions.txt");
		config[1] = regions;
		File map = new File("src/configurationFilesTest/Map0.txt");
		config[2] = map;
		File permitTile = new File("src/configurationFilesTest/PermitTile.txt");
		config[3] = permitTile;
		File bonusTile = new File("src/configurationFilesTest/BonusTile.txt");
		config[4] = bonusTile;
		File kingRewards = new File("src/configurationFilesTest/KingRewards.txt");
		config[5] = kingRewards;
		File politicCard = new File("src/configurationFilesTest/PoliticCard.txt");
		config[6] = politicCard;
		File nobilityTrack = new File("src/configurationFilesTest/NobilityTrack.txt");
		config[7] = nobilityTrack;
		
		controller = new IOController();
		
	}
	
	@Test (expected = NullPointerException.class)
	public void testNullPointerExceptionIfFileIsNull() throws IOException {

		controller.build(null);
	
	}
	
	@Test
	public void testNobilityTrackBonusNotNull() throws IOException{
		
		model = controller.build(config);
		assertNotNull(model.getNobilityTrackBonus());
		
	}
	
	@Test
	public void testPoliticCardsDeckNotNull() throws IOException{
		
		model = controller.build(config);
		assertNotNull(model.getPoliticCardsDeck());
		
	}
	
	@Test
	public void testKingRewardsDeckNotNull() throws IOException{
		
		model = controller.build(config);
		assertNotNull(model.getKingRewards());
		
	}
	
	@Test
	public void testBonusTileDeckNotNull() throws IOException{
		
		model = controller.build(config);
		assertNotNull(model.getBonusTiles());
		
	}
	
	@Test
	public void testPermitTileDeckNotNull() throws IOException{
		
		model = controller.build(config);
		for(Region region: model.getRegions())
			assertNotNull(region.getDeck());
		
	}
	
	@Test
	public void testMapNotNull() throws IOException{
		
		model = controller.build(config);
		assertNotNull(model.getMap());
		
	}
	
	@Test
	public void testRegionsNotNull() throws IOException{
		
		model = controller.build(config);
		assertNotNull(model.getRegions());
		
	}
	
	@Test
	public void testNoblesPoolNotNull() throws IOException{
		
		model = controller.build(config);
		assertNotNull(model.getNoblesPool());
		
	}

}
