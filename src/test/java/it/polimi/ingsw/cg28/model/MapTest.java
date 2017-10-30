package it.polimi.ingsw.cg28.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.model.parser.ModelTestConfiguration;

public class MapTest {
	
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
	public void testMapWithNullTownsParameter() {
		List<List<Town>> edges = new ArrayList<>();
		new GameMap(null, edges, new Balcony(new Councillor[4]));
	}
	
	@Test (expected = NullPointerException.class)
	public void testMapWithNullConnectionsParameter() {
		new GameMap(new ArrayList<Town>(), null, new Balcony(new Councillor[4]));
	}
	
	@Test (expected = NullPointerException.class)
	public void testMapWithNullCouncilParameter() {
		List<List<Town>> edges = new ArrayList<>();
		new GameMap(new ArrayList<Town>(), edges, null);
	}
	
	@Test
	public void testMapConstructor() {
		List<Town> towns = new ArrayList<>();
		List<List<Town>> connections = new ArrayList<>();
		Balcony council = new Balcony(new Councillor[4]);
		
		GameMap map = new GameMap(towns, connections, council);
		assertTrue("Map correctly constructed.", !map.equals(null));
	}
	
	@Test
	public void testGetTownList() {
		List<Town> towns = new ArrayList<>();
		List<Town> toTest = model.getMap().getTownList();
		towns.add(model.getMap().getTown("Arkon"));
		towns.add(model.getMap().getTown("Graden"));
		towns.add(model.getMap().getTown("Juvelar"));
		
		for(int i = 0; i < toTest.size(); i++){
			assertTrue(toTest.contains(towns.get(i)));
		}
	}
	
	@Test
	public void testGetTownNetwork() {
		Graph<Town, DefaultEdge> networkToTest = model.getMap().getTownNetwork();
		Graph<Town, DefaultEdge> net = new SimpleGraph<>(DefaultEdge.class);
		net.addVertex(model.getMap().getTown("Arkon"));
		net.addVertex(model.getMap().getTown("Graden"));
		net.addVertex(model.getMap().getTown("Juvelar"));
		net.addEdge(model.getMap().getTown("Arkon"), model.getMap().getTown("Graden"));
		net.addEdge(model.getMap().getTown("Arkon"), model.getMap().getTown("Juvelar"));
		net.addEdge(model.getMap().getTown("Graden"), model.getMap().getTown("Arkon"));
		
		assertTrue(networkToTest.vertexSet().containsAll(net.vertexSet()));
		for(DefaultEdge edge : networkToTest.edgeSet()){
			assertTrue(networkToTest.containsEdge(edge));
		}
	}
	
	@Test
	public void testMapGetKingCouncil() {
		Balcony councilToTest = model.getMap().getKingCouncil();
		assertTrue(!councilToTest.equals(null));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testMapGetTownByIntIndexWithNegativeParameter() {
		model.getMap().getTown(-2);
	}
	
	@Test
	public void testMapGetTownByIntIndex() {
		Town toTest = model.getMap().getTown(1);
		assertTrue(toTest.equals(model.getMap().getTown("Juvelar")));
	}
	
	@Test (expected = NullPointerException.class)
	public void testMapSetNeighborsWithNullGraphParameter() {
		model.getMap().townSetNeighbors(null, new HashSet<Town>());
	}
	
	@Test (expected = NullPointerException.class)
	public void testMapSetNeighborsWithNullTownSetParameter() {
		model.getMap().townSetNeighbors(new SimpleGraph<>(DefaultEdge.class), null);
	}

	@Test
	public void testMapSetNeighbors() {
		Set<Town> testSet = new HashSet<>();
		List<Town> result = new CopyOnWriteArrayList<>();
		Set<Town> assertion = new HashSet<>();
		testSet.add(model.getMap().getTown("Graden"));
		testSet.add(model.getMap().getTown("Arkon"));
		assertion.add(model.getMap().getTown("Juvelar"));
		
		result = model.getMap().townSetNeighbors(model.getMap().getTownNetwork(), testSet);
		assertTrue(result.containsAll(assertion));
	}
	
	@Test (expected = NullPointerException.class)
	public void testMapAddEmporiumWithNullPlayerParameter() {
		model.getMap().addEmporium(null, model.getMap().getTown("Arkon"));
	}
	
	@Test (expected = NullPointerException.class)
	public void testMapAddEmporiumWithNullTownParameter() {
		model.getMap().addEmporium(players[0], null);
	}
	
	@Test
	public void testMapAddEmporium() {
		model.getMap().addEmporium(players[0], model.getMap().getTown("Arkon"));
		model.getMap().addEmporium(players[1], model.getMap().getTown("Arkon"));
		model.getMap().addEmporium(players[0], model.getMap().getTown("Graden"));
		
		assertTrue(model.getMap().getTown("Arkon").hasEmporium(players[0]) &&
				model.getMap().getTown("Arkon").hasEmporium(players[1]));
		assertTrue(model.getMap().getTown("Graden").hasEmporium(players[0]));
	}
	
	@Test (expected = NullPointerException.class)
	public void testSetKingHereWithNullTownParameter() {
		model.getMap().setKingHere(true, null);
	}
	
	@Test
	public void testMapSetKingHere() {
		model.getMap().setKingHere(true, model.getMap().getTown("Arkon"));
		model.getMap().setKingHere(false, model.getMap().getTown("Juvelar"));
		assertTrue(model.getMap().getTown("Arkon").isKingHere());
		assertTrue(!model.getMap().getTown("Juvelar").isKingHere());
	}
	
	@Test (expected = NullPointerException.class)
	public void testMapGetTownByNameStrWithNullParameter() {
		model.getMap().getTown(null);
	}
	
	@Test
	public void testMapGetTownByNameStr() {
		Town target = model.getMap().getTown("Arkon");
		assertTrue(target.getTownName().equals("Arkon"));
		assertTrue(target.getAlloy().equals("Gold"));
		assertTrue(target.getRegion().getRegionType().equals("Coast"));
		assertTrue(model.getMap().getTown("Bormio") == null);
	}

}
