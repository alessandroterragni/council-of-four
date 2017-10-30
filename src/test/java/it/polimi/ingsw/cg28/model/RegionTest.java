package it.polimi.ingsw.cg28.model;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.controller.bonus.VictoryPointBonus;
import it.polimi.ingsw.cg28.model.decks.TileDeck;
import it.polimi.ingsw.cg28.model.parser.ModelTestConfiguration;

public class RegionTest {
	
	private ModelStatus model;
	PlayerID[] players = new PlayerID[2];
	
	@Before
	public void before(){
		players[0] = new PlayerID("Player 1");
		players[1] = new PlayerID("Player 2");
	
		ModelTestConfiguration config = new ModelTestConfiguration();
		model = config.buildModelTest(players);
	}

	@Test (expected = NullPointerException.class)
	public void testRegionWithNullTypeParameter() {
		new Region(null, 2, new TileDeck<>(new LinkedList<>()), new Councillor[4]);
	}
	
	@Test (expected = NullPointerException.class)
	public void testRegionWithNullCouncilParameter() {
		new Region("Coast", 2, new TileDeck<>(new LinkedList<>()), null);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testRegionWithNegativeUncoveredNumParameter() {
		new Region("Coast", -3, new TileDeck<>(new LinkedList<>()), new Councillor[4]);
	}
	
	@Test
	public void testRegionConstructor() {
		Region reg = new Region("Coast", 2, new TileDeck<>(new LinkedList<>()), new Councillor[4]);
		assertTrue(!reg.equals(null));
	}
	
	@Test
	public void testGetDeck() {
		TileDeck<BusinessPermitTile> deck = model.getRegion(0).getDeck();
		assertTrue(!deck.equals(null));
		//test che il mazzo sia quello giusto???
	}
	
	@Test (expected = NullPointerException.class)
	public void testSetDeckWithNullParameter() {
		model.getRegions()[0].setDeck(null);
	}
	
	@Test
	public void testSetDeck() {
		List<BusinessPermitTile> tiles = new LinkedList<>();
		String[] letters = {"Arkon"};
		tiles.add(new BusinessPermitTile(new VictoryPointBonus(4), letters));
		model.getRegions()[0].setDeck(new TileDeck<BusinessPermitTile>(new LinkedList<BusinessPermitTile>(tiles)));
		assertTrue(model.getRegions()[0].getDeck().peek().getBonuses().getClass() ==
				new BusinessPermitTile(new VictoryPointBonus(4), letters).getBonuses().getClass());
		for(int i = 0; i < letters.length; i++){
			assertTrue(model.getRegions()[0].getDeck().peek().getLetterCodes()[i]
				.equals(new BusinessPermitTile(new VictoryPointBonus(4), letters).getLetterCodes()[i]));
		}
	}

	@Test (expected = NullPointerException.class)
	public void testAddTownWithNullParameter() {
		model.getRegions()[0].addTown(null);
	}
	
	@Test
	public void testAddTown() {
		Town bormio = new Town("Bormio", "Silver", true, new VictoryPointBonus(4), model.getRegions()[0]);
		model.getRegions()[0].addTown(bormio);
		assertTrue(model.getRegions()[0].getTowns().contains(bormio));
	}
	
	@Test
	public void testGetBalcony() {
		Balcony council = model.getRegions()[0].getBalcony();
		assertTrue(!council.equals(null));
	}
	
	@Test (expected = NullPointerException.class)
	public void testSetBalconyWithNullParameter() {
		model.getRegions()[0].setBalcony(null);
	}
	
	@Test
	public void testSetBalcony() {
		Councillor[] council = new Councillor[4];
		council[0] = new Councillor(Color.BLACK);
		council[1] = new Councillor(Color.BLUE);
		council[2] = new Councillor(Color.ORANGE);
		council[3] = new Councillor(Color.WHITE);
		Balcony toSet = new Balcony(council);
		
		model.getRegions()[0].setBalcony(toSet);
		assertTrue(model.getRegions()[0].getBalcony().equals(toSet));
		for(int i = 0; i < council.length; i++){
			assertTrue(model.getRegions()[0].getBalcony().getCouncillor(i).equals(council[i]));
		}
	}
	
	@Test
	public void testGetUncoveredTiles() {
		BusinessPermitTile[] tiles = model.getRegions()[0].getUncovered();
		assertTrue(!tiles.equals(null));
		assertTrue(tiles.length == model.getRegions()[0].getUnconveredNum());
	}
	
	@Test (expected = NullPointerException.class)
	public void testSetUncoveredTilesWithNullParameter() {
		model.getRegions()[0].setUncovered(null);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testSetUncoveredTilesWithArrayParameterNotLongEnough() {
		BusinessPermitTile[] tile = new BusinessPermitTile[1];
		model.getRegions()[0].setUncovered(tile);
	}
	
	@Test
	public void testSetUncoveredTiles() {
		BusinessPermitTile[] uncovered = new BusinessPermitTile[2];
		String[] letters1 = {"Arkon"};
		String[] letters2 = {"Juvelar"};
		uncovered[0] = new BusinessPermitTile(new VictoryPointBonus(4), letters1);
		uncovered[1] = new BusinessPermitTile(new VictoryPointBonus(4), letters2);
		model.getRegions()[0].setUncovered(uncovered);
		assertTrue(model.getRegions()[0].getTile(0).equals(uncovered[0]));
		assertTrue(model.getRegions()[0].getTile(1).equals(uncovered[1]));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testGetTileByIntIndexWithNegativeParameter() {
		model.getRegions()[0].getTile(-1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testGetTileByIntIndexWithParameterAboveMaxIndex(){
		model.getRegions()[0].getTile(11);
	}
	
	@Test
	public void testGetTileByIntIndex() {
		for(int i = 0; i < model.getRegions()[0].getUnconveredNum(); i++){
			assertTrue(!model.getRegions()[0].getTile(i).equals(null));
		}
	}
	
	@Test
	public void testGetTowns(){
		Region r1 = model.getRegions()[0];
		Region r2 = model.getRegions()[1];
		List<Town> t1 = r1.getTowns();
		List<Town> t2 = r2.getTowns();
		List<Town> test1 = new ArrayList<>();
		List<Town> test2 = new ArrayList<>();
		test1.add(model.getMap().getTown("Arkon"));
		test2.add(model.getMap().getTown("Graden"));
		test2.add(model.getMap().getTown("Juvelar"));
		
		assertTrue(t1.equals(test1));
		assertTrue(t2.equals(test2));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testChangeTileWithNegativeParameter() {
		model.getRegions()[0].changeTile(-1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testChangeTileWithParameterAboveMax() {
		model.getRegions()[0].changeTile(33);
	}
	
	@Test (expected = NoSuchElementException.class)
	public void testChangeTileWithEmptyDeck() {
		Region reg = model.getRegions()[0];
		reg.setDeck(new TileDeck<>(new LinkedList<>()));
		reg.changeTile(1);		
	}
	
	@Test
	public void testChangeTile(){
		Region reg = model.getRegions()[0];
		BusinessPermitTile tile = reg.getUncovered()[0];
		reg.changeTile(0);
		assertTrue(!tile.equals(reg.getUncovered()[0]));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testReshuffleTileWithNegativeParameter() {
		model.getRegions()[0].reshuffleTile(-1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testReshuffleTileWithParameterAboveMax() {
		model.getRegions()[0].reshuffleTile(33);
	}
	
	@Test
	public void testReshuffleTile(){
		Region reg = model.getRegions()[0];
		BusinessPermitTile tile = reg.getUncovered()[0];
		reg.reshuffleTile(0);
		assertTrue(!tile.equals(reg.getUncovered()[0]));
	}
	
	@Test
	public void testGetUncoveredNumber() {
		assertTrue(model.getRegions()[0].getUnconveredNum() == 2);
	}
	
	

}
