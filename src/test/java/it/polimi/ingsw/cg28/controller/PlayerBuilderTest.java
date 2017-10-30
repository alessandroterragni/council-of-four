package it.polimi.ingsw.cg28.controller;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.Player;
import it.polimi.ingsw.cg28.model.PlayerBuilder;
import it.polimi.ingsw.cg28.model.PoliticCard;
import it.polimi.ingsw.cg28.model.parser.IOController;

public class PlayerBuilderTest {
	
	private ModelStatus model;
	private List<PlayerID> players;
	
	public ModelStatus buildModelTestWithNoPlayers() {
		
		File[] config;
		ModelStatus model = null;
		IOController controller;
		
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
		
		try {
			model = controller.build(config);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(int i=0; i <20; i++)
			model.getPoliticCardsDeck().getDiscardPile().add(new PoliticCard(null, true));
		
		model.getPoliticCardsDeck().reshuffleDiscards();
		
		return model;
		
	}
	
	@Before
	public void before() {
		model = buildModelTestWithNoPlayers();
		players = new ArrayList<>();
		players.add(new PlayerID("Player1"));
		players.add(new PlayerID("Player2"));
		players.add(new PlayerID("Player3"));
	}
	
	@Test (expected = NullPointerException.class)
	public void testBuildWithNullIDsParameter() {
		new PlayerBuilder().build(null, model);
	}
	
	@Test (expected = NullPointerException.class)
	public void testBuildWithNullModelParameter() {
		new PlayerBuilder().build(new ArrayList<PlayerID>(), null);
	}
	
	@Test
	public void testBuild() {
		
		Player[] realPls = new PlayerBuilder().build(players, model);
		
		assertTrue(!realPls.equals(null));
		assertTrue(realPls.length == 3);
		
		
		for(int i = 0; i < 2; i++){
			assertTrue(!realPls[i].getPoliticCardsHand().equals(null));
			assertTrue(realPls[i].getPoliticCardsHand().size() == 6);
			assertTrue(!realPls[i].getPoliticCardsHand().isEmpty());
			assertTrue(realPls[i].getID().equals(players.get(i)));
			assertTrue(realPls[i].getEmporiumNumber() == 10);
			assertTrue(realPls[i].getCoins().getValue() == 10+i);
			assertTrue(realPls[i].getAssistants().getValue() == 1+i);
			assertTrue(realPls[i].getScore().getValue() == 0);
			assertTrue(realPls[i].getNobilityRank().getValue() == 0);
		}
	}

}
