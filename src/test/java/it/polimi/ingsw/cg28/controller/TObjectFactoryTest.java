package it.polimi.ingsw.cg28.controller;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.bonus.Bonus;
import it.polimi.ingsw.cg28.controller.bonus.BonusPack;
import it.polimi.ingsw.cg28.controller.bonus.CoinBonus;
import it.polimi.ingsw.cg28.controller.bonus.NobilityBonus;
import it.polimi.ingsw.cg28.controller.bonus.VictoryPointBonus;
import it.polimi.ingsw.cg28.model.AssistantCounterTrack;
import it.polimi.ingsw.cg28.model.Balcony;
import it.polimi.ingsw.cg28.model.BonusTile;
import it.polimi.ingsw.cg28.model.BusinessPermitTile;
import it.polimi.ingsw.cg28.model.CoinTrack;
import it.polimi.ingsw.cg28.model.Councillor;
import it.polimi.ingsw.cg28.model.GameMap;
import it.polimi.ingsw.cg28.model.NobilityTrackBonus;
import it.polimi.ingsw.cg28.model.Player;
import it.polimi.ingsw.cg28.model.PoliticCard;
import it.polimi.ingsw.cg28.model.Region;
import it.polimi.ingsw.cg28.model.Town;
import it.polimi.ingsw.cg28.model.decks.TileDeck;
import it.polimi.ingsw.cg28.tmodel.TBalcony;
import it.polimi.ingsw.cg28.tmodel.TBonus;
import it.polimi.ingsw.cg28.tmodel.TBonusTile;
import it.polimi.ingsw.cg28.tmodel.TBusinessPermitTile;
import it.polimi.ingsw.cg28.tmodel.TMap;
import it.polimi.ingsw.cg28.tmodel.TNoblesPool;
import it.polimi.ingsw.cg28.tmodel.TObjectFactory;
import it.polimi.ingsw.cg28.tmodel.TPlayer;
import it.polimi.ingsw.cg28.tmodel.TRegion;
import it.polimi.ingsw.cg28.tmodel.TTown;

public class TObjectFactoryTest {

	private TObjectFactory factory;
	
	@Before
	public void before() {
		factory = new TObjectFactory();
	}
	
	@Test (expected = NullPointerException.class)
	public void testCreateTBalconyWithNullParameter() {
		factory.createTBalcony(null);
	}
	
	@Test
	public void testCreateTBalcony() {
		Councillor[] councillors = {
			new Councillor(Color.ORANGE),
			new Councillor(Color.BLACK),
			new Councillor(Color.WHITE),
			new Councillor(Color.BLUE)
		};
		Balcony toTransform = new Balcony(councillors);
		
		TBalcony transformed = factory.createTBalcony(toTransform);
		
		assertNotNull(transformed.getCouncil());
		for(int i = 0; i < transformed.getCouncil().length; i++){
			assertTrue(transformed.getCouncillorColor(i).equals(toTransform.getCouncillor(i).getColor()));
		}
	}
	
	@Test (expected = NullPointerException.class)
	public void testCreateTTownWithNullParameter() {
		factory.createTtown(null);
	}
	
	@Test
	public void testCreateTTown() {
		Councillor[] councillors = {
				new Councillor(Color.ORANGE),
				new Councillor(Color.BLACK),
				new Councillor(Color.WHITE),
				new Councillor(Color.BLUE)
			};
		Bonus bonus = new VictoryPointBonus(5);
		Town toTransform = new Town("TestTown", "TestAlloy", false, bonus,
				new Region("TestRegionType", 2, new TileDeck<>(new LinkedList<>()), councillors));
		
		TTown transformed = factory.createTtown(toTransform);
		
		assertNotNull(transformed);
		assertTrue(transformed.getName().equals(toTransform.getTownName()));
		assertTrue(transformed.getAlloy().equals(toTransform.getAlloy()));
		assertTrue(transformed.getRegion().equals(toTransform.getRegion().getRegionType()));
		assertTrue(transformed.isKingHere() == toTransform.isKingHere());
		assertTrue(transformed.getEmporiums().length == toTransform.emporiumNumber());
		for(int i = 0; i < transformed.getEmporiums().length; i++){
			assertTrue(transformed.getEmporium(i).equals(toTransform.getEmporium(i).getOwner()));
		}
	}
	
	@Test (expected = NullPointerException.class)
	public void testCreateTPlayerWithNullParameter() {
		factory.createTPlayer(null, true);
	}
	
	@Test
	public void testCreateTPlayer() {
		PoliticCard[] cards = {
				new PoliticCard(Color.BLACK, false),
				new PoliticCard(Color.ORANGE, false),
				new PoliticCard(null, true)
		};
		Player toTransform = new Player(new PlayerID("TestPlayer"), null, 10, new CoinTrack(10),
				cards, new AssistantCounterTrack(10));
		
		TPlayer transformed = factory.createTPlayer(toTransform, true);
		
		assertNotNull(transformed);
		assertTrue(transformed.getName().equals(toTransform.getID().getName()));
		assertTrue(transformed.getId().equals(toTransform.getID().getUuidPlayer().toString()));
		assertTrue(transformed.getEmpNumber() == toTransform.getEmporiumNumber());
		assertTrue(transformed.getCoins() == toTransform.getCoins().getValue());
		assertTrue(transformed.getNobilityRank() == toTransform.getNobilityRank().getValue());
		assertTrue(transformed.getVictoryPoints() == toTransform.getScore().getValue());
		assertTrue(transformed.getAssistants() == toTransform.getAssistants().getValue());
		assertTrue(transformed.getPoliticCardsHand().length == toTransform.getPoliticCardsHand().size());
		for(int i = 0; i < transformed.getPoliticCardsHand().length; i++){
			assertTrue(toTransform.getCard(i).getHouseColor() == transformed.getPoliticCardsHand()[i]);
		}
		for(int i = 0; i < transformed.getPossessedTiles().size(); i++){
			assertTrue(toTransform.getPossessedTiles().get(i).equals(transformed.getPossessedTiles().get(i)));
		}
		for(int i = 0; i < transformed.getUsedTiles().size(); i++){
			assertTrue(toTransform.getUsedTiles().get(i).equals(transformed.getUsedTiles().get(i)));
		}
	}
	
	@Test (expected = NullPointerException.class)
	public void testCreateTBusinessPermitTileWithNullParameter() {
		factory.createTBusinessPermitTile(null);
	}
	
	@Test
	public void testCreateTBusinessPermitTile() {
		Bonus bonus1 = new NobilityBonus(1);
		Bonus bonus2 = new VictoryPointBonus(4);
		List<Bonus> bList = new ArrayList<>();
		bList.add(bonus1);
		bList.add(bonus2);
		Bonus bonuses = new BonusPack(bList);
		String[] letters = {
				"TestTown1",
				"TestTown2",
				"TestTown3"
		};
		BusinessPermitTile toTransform = new BusinessPermitTile(bonuses, letters);
		
		TBusinessPermitTile transformed = factory.createTBusinessPermitTile(toTransform);
		
		assertNotNull(transformed);
		assertTrue(transformed.getBonuses().getBonusCode()[0].equals("NobilityBonus:1"));
		assertTrue(transformed.getBonuses().getBonusCode()[1].equals("VictoryPointBonus:4"));
		for(int i = 0; i < transformed.getLetterCodes().length; i++){
			assertTrue(transformed.getLetterCodes()[i].equals(toTransform.getLetterCodes()[i]));
		}
		
	}
	
	@Test (expected = NullPointerException.class)
	public void testCreateTBusinessPermitTileArrayFromArrayWithNullParameter() {
		factory.createTBusinessPermitTileArray((BusinessPermitTile[]) null);
	}
	
	@Test
	public void testCreateTBusinessPermitTileArrayFromArray() {
		Bonus bonus1 = new NobilityBonus(1);
		Bonus bonus2 = new VictoryPointBonus(4);
		List<Bonus> bList = new ArrayList<>();
		bList.add(bonus1);
		bList.add(bonus2);
		Bonus bonuses = new BonusPack(bList);
		String[] letters = {
				"TestTown1",
				"TestTown2",
				"TestTown3"
		};
		BusinessPermitTile[] toTransform = {
				new BusinessPermitTile(bonuses, letters),
				new BusinessPermitTile(bonuses, letters),
				new BusinessPermitTile(bonuses, letters)
		};
		
		TBusinessPermitTile[] transformed = factory.createTBusinessPermitTileArray(toTransform);
		
		for(int j = 0; j < transformed.length; j++){
			assertNotNull(transformed[j]);
			assertTrue(transformed[j].getBonuses().getBonusCode()[0].equals("NobilityBonus:1"));
			assertTrue(transformed[j].getBonuses().getBonusCode()[1].equals("VictoryPointBonus:4"));
			for(int i = 0; i < transformed.length; i++){
				assertTrue(transformed[j].getLetterCodes()[i].equals(toTransform[j].getLetterCodes()[i]));
			}
		}
	}
	
	@Test (expected = NullPointerException.class)
	public void testCreateTBusinessPermitTileArrayFromListWithNullParameter() {
		factory.createTBusinessPermitTileArray((List<BusinessPermitTile>) null);
	}
	
	@Test
	public void testCreateTBusinessPermitTileArrayFromList() {
		Bonus bonus1 = new NobilityBonus(1);
		Bonus bonus2 = new VictoryPointBonus(4);
		List<Bonus> bList = new ArrayList<>();
		bList.add(bonus1);
		bList.add(bonus2);
		Bonus bonuses = new BonusPack(bList);
		String[] letters = {
				"TestTown1",
				"TestTown2",
				"TestTown3"
		};
		List<BusinessPermitTile> toTransform = new ArrayList<>();
		for(int i = 0; i < 3; i++){
			new BusinessPermitTile(bonuses, letters);
		}
		
		TBusinessPermitTile[] transformed = factory.createTBusinessPermitTileArray(toTransform);
		
		for(int j = 0; j < transformed.length; j++){
			assertNotNull(transformed[j]);
			assertTrue(transformed[j].getBonuses().getBonusCode()[0].equals("NobilityBonus:1"));
			assertTrue(transformed[j].getBonuses().getBonusCode()[1].equals("VictoryPointBonus:4"));
			for(int i = 0; i < transformed.length; i++){
				assertTrue(transformed[j].getLetterCodes()[i].equals(toTransform.get(j).getLetterCodes()[i]));
			}
		}
	}
	
	@Test (expected = NullPointerException.class)
	public void testCreateTBonusWithNullParameter() {
		factory.createTBonus(null);
	}
	
	@Test
	public void testCreateTBonus() {
		Bonus bonus1 = new NobilityBonus(1);
		Bonus bonus2 = new VictoryPointBonus(4);
		List<Bonus> bList = new ArrayList<>();
		bList.add(bonus1);
		bList.add(bonus2);
		Bonus toTransform = new BonusPack(bList);
		
		TBonus transformed = factory.createTBonus(toTransform);
		
		assertNotNull(transformed);
		assertTrue(transformed.getBonusCode()[0].equals("NobilityBonus:1"));
		assertTrue(transformed.getBonusCode()[1].equals("VictoryPointBonus:4"));
	}
	
	@Test (expected = NullPointerException.class)
	public void testCreateTRegionWithNullParameter() {
		factory.createTRegion(null);
	}
	
	@Test
	public void testCreateTRegion() {
		Councillor[] council = {
				new Councillor(Color.ORANGE),
				new Councillor(Color.BLACK),
				new Councillor(Color.WHITE),
				new Councillor(Color.BLUE)
		};
		Bonus bonus1 = new NobilityBonus(1);
		Bonus bonus2 = new VictoryPointBonus(4);
		List<Bonus> bList = new ArrayList<>();
		bList.add(bonus1);
		bList.add(bonus2);
		Bonus bPack = new BonusPack(bList);
		String[] letters = {
				"TestTown1",
				"TestTown2",
				"TestTown3"
		};
		LinkedList<BusinessPermitTile> deck = new LinkedList<>();
		deck.add(new BusinessPermitTile(bPack, letters));
		deck.add(new BusinessPermitTile(bPack, letters));
		deck.add(new BusinessPermitTile(bPack, letters));
		Region toTransform = new Region("TestRegionType", 2, new TileDeck<>(deck), council);
		
		BusinessPermitTile[] unc = {
				deck.removeFirst(),
				deck.removeFirst()
		};
		toTransform.setUncovered(unc);
		
		TRegion transformed = factory.createTRegion(toTransform);
		
		assertNotNull(transformed);
		for(int i = 0; i < transformed.getCouncil().getCouncil().length; i++){
			assertTrue(transformed.getCouncil().getCouncil()[i].equals(toTransform.getBalcony().getCouncillor(i).getColor()));			
		}
		assertTrue(transformed.getRegionType().equals(toTransform.getRegionType()));
		assertTrue(transformed.getUncovered().length == toTransform.getUnconveredNum());
		for(int i = 0; i < transformed.getUncovered().length; i++){
			assertTrue(transformed.getUncovered()[i].getBonuses().getBonusCode()[0].equals("NobilityBonus:1"));
			assertTrue(transformed.getUncovered()[i].getBonuses().getBonusCode()[1].equals("VictoryPointBonus:4"));
			for(int j = 0; j < transformed.getUncovered()[i].getLetterCodes().length; j++){
				assertTrue(transformed.getUncovered()[i].getLetterCodes()[j]
						.equals(toTransform.getUncovered()[i].getLetterCodes()[j]));
			}			
		}
	}
	
	@Test (expected = NullPointerException.class)
	public void testCreateTMapWithNullParameter() {
		factory.createTMap(null);
	}
	
	@Test
	public void testCreateTMap() {
		Councillor[] councillors = {
				new Councillor(Color.ORANGE),
				new Councillor(Color.BLACK),
				new Councillor(Color.WHITE),
				new Councillor(Color.BLUE)
			};
		Balcony balcony = new Balcony(councillors);
		List<Town> towns = new ArrayList<>();
		List<List<Town>> connections = new ArrayList<List<Town>>();
		Town testTown1 = new Town("TestTown1", "TestAlloy1", false, new VictoryPointBonus(1),
				new Region("TestRegionType1", 2, new TileDeck<>(new LinkedList<>()), councillors));
		Town testTown2 = new Town("TestTown2", "TestAlloy2", false, new VictoryPointBonus(1),
				new Region("TestRegionType2", 2, new TileDeck<>(new LinkedList<>()), councillors));
		Town testTown3 = new Town("TestTown3", "TestAlloy3", false, new VictoryPointBonus(1),
				new Region("TestRegionType3", 2, new TileDeck<>(new LinkedList<>()), councillors));
		towns.add(testTown1);
		towns.add(testTown2);
		towns.add(testTown3);
		List<Town> testConn1 = new ArrayList<>();
		testConn1.add(testTown1);
		testConn1.add(testTown2);
		List<Town> testConn2 = new ArrayList<>();
		testConn2.add(testTown2);
		testConn2.add(testTown3);
		List<Town> testConn3 = new ArrayList<>();
		testConn3.add(testTown1);
		testConn3.add(testTown3);
		connections.add(testConn1);
		connections.add(testConn2);
		connections.add(testConn3);
		GameMap toTransform = new GameMap(towns, connections, balcony);
		
		TMap transformed = factory.createTMap(toTransform);
		TTown testTTown1 = transformed.getTownList().get(0);
		TTown testTTown2 = transformed.getTownList().get(1);
		TTown testTTown3 = transformed.getTownList().get(2);
		List<TTown> ttownList = new ArrayList<>();
		ttownList.add(testTTown1);
		ttownList.add(testTTown2);
		ttownList.add(testTTown3);
		
		assertNotNull(transformed);
		assertTrue(transformed.getTownNetwork().vertexSet().containsAll(ttownList));
		assertTrue(transformed.getTownNetwork().containsEdge(testTTown1, testTTown2));
		assertTrue(transformed.getTownNetwork().containsEdge(testTTown2, testTTown3));
		assertTrue(transformed.getTownNetwork().containsEdge(testTTown1, testTTown3));
	}
	
	@Test (expected = NullPointerException.class)
	public void testCreateTBonusTileWithNullParameter() {
		factory.createTBonusTile(null);
	}
	
	@Test
	public void testCreateTBonusTile() {
		Bonus bonus = new CoinBonus(4);
		BonusTile toTransform = new BonusTile("TestBonusTileID", bonus);
		
		TBonusTile transformed = factory.createTBonusTile(toTransform);
		
		assertNotNull(transformed);
		assertTrue(transformed.getIdentifier().equals(toTransform.getIdentifier()));
		assertTrue(transformed.getBonus().equals(factory.createTBonus(bonus)));
	}
	
	@Test (expected = NullPointerException.class)
	public void testCreateTBonusTilesWithNullParameter() {
		factory.createTBonusTiles(null);
	}
	
	@Test
	public void testCreateTBonusTiles() {
		Bonus bonus1 = new CoinBonus(4);
		Bonus bonus2 = new VictoryPointBonus(2);
		Bonus bonus3 = new NobilityBonus(1);
		BonusTile tile1 = new BonusTile("TestBonusTileID", bonus1);
		BonusTile tile2 = new BonusTile("TestBonusTileID", bonus2);
		BonusTile tile3 = new BonusTile("TestBonusTileID", bonus3);
		
		LinkedList<BonusTile> tileDeck = new LinkedList<>();
		tileDeck.add(tile1);
		tileDeck.add(tile2);
		tileDeck.add(tile3);
		
		TileDeck<BonusTile> toTransform = new TileDeck<>(tileDeck);
		
		TBonusTile[] transformed = factory.createTBonusTiles(toTransform);
		
		assertNotNull(transformed);
		assertTrue(transformed[0].getBonus().equals(factory.createTBonus(toTransform.draw().getBonus())));
		assertTrue(transformed[1].getBonus().equals(factory.createTBonus(toTransform.draw().getBonus())));
		assertTrue(transformed[2].getBonus().equals(factory.createTBonus(toTransform.draw().getBonus())));
	}
	
	@Test (expected = NullPointerException.class)
	public void testCreateTNoblesPoolWithNullParameter() {
		factory.createTNoblesPool(null);
	}
	
	@Test
	public void testCreateTNoblesPool() {
		List<Councillor> toTransform = new ArrayList<>();
		toTransform.add(new Councillor(Color.BLACK));
		toTransform.add(new Councillor(Color.ORANGE));
		toTransform.add(new Councillor(Color.WHITE));
		toTransform.add(new Councillor(Color.BLUE));
		
		TNoblesPool transformed = factory.createTNoblesPool(toTransform);
		
		assertNotNull(transformed);
		for(int i = 0; i < transformed.getPool().size(); i++){
			assertTrue(transformed.getPool(i).equals(toTransform.get(i).getColor()));			
		}
	}
	
	@Test (expected = NullPointerException.class)
	public void testCreateTNobilityTrackWithNullParameter() {
		factory.createTNobilityTrack(null);
	}
	
	@Test
	public void testCreateTNobilityTrack() {
		NobilityTrackBonus toTransform = new NobilityTrackBonus();
		toTransform.addValue(1, new VictoryPointBonus(3));
		toTransform.addValue(4, new CoinBonus(2));
		toTransform.addValue(10, new NobilityBonus(1));
		
		TBonus[] transformed = factory.createTNobilityTrack(toTransform);
		
		assertNotNull(transformed);
		for(int i = 0; i < transformed.length; i++){
			if(toTransform.getTrackBonus(i) == null){
				assertTrue(true);
			}
			else{
				assertTrue(transformed[i].equals(factory.createTBonus(toTransform.getTrackBonus(i))));
			}
		}
	}

}
