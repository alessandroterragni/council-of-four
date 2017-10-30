package it.polimi.ingsw.cg28.tModel;

import static org.junit.Assert.*;

import java.awt.Color;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.tmodel.TBalcony;
import it.polimi.ingsw.cg28.tmodel.TBonus;
import it.polimi.ingsw.cg28.tmodel.TMap;
import it.polimi.ingsw.cg28.tmodel.TTown;

public class TMapTest {
	
	private SimpleGraph<TTown, DefaultEdge> network = new SimpleGraph<>(DefaultEdge.class);
	private TBalcony balc;
	private String[] bcodes = { "VPB", "CB", "NB" };
	private TTown a = new TTown("Arkon", "Silver", "Coast", new TBonus(bcodes), false, new PlayerID[4]);
	private TTown j = new TTown("Juvelar", "King", "Plains", new TBonus(bcodes), false, new PlayerID[4]);
	private TTown g = new TTown("Graden", "Gold", "Mountain", new TBonus(bcodes), false, new PlayerID[4]);
	private Color[] colors = new Color[4];
	
	@Before
	public void before() {
		network.addVertex(a);
		network.addVertex(j);
		network.addVertex(g);
		network.addEdge(a, j);
		network.addEdge(g, a);
		colors[0] = Color.BLACK;
		colors[1] = Color.ORANGE;
		colors[2] = Color.WHITE;
		colors[3] = Color.MAGENTA;
		balc = new TBalcony(colors);
	}

	@Test (expected = NullPointerException.class)
	public void testTMapWithNullGraphParameter() {
		new TMap(null, balc);
	}
	
	@Test (expected = NullPointerException.class)
	public void testTMapWithNullBalconyParameter() {
		new TMap(network, null);
	}
	
	@Test
	public void testTMap() {
		TMap map = new TMap(network, balc);
		assertTrue(!map.equals(null));
		assertTrue(map.getKingCouncil().equals(balc));
		assertTrue(map.getTownNetwork().equals(network));
	}
	
	@Test
	public void testGetTownNetwork() {
		TMap map = new TMap(network, balc);
		assertTrue(!map.getTownList().equals(null));
		for(TTown i : map.getTownNetwork().vertexSet()){
			for(TTown j : map.getTownNetwork().vertexSet()){
				assertTrue(network.containsEdge(i, j) ? map.getTownNetwork().containsEdge(i, j) : true);
			}
		}
	}

	@Test
	public void testGetKingCouncil() {
		TMap map = new TMap(network, balc);
		assertTrue(!map.getKingCouncil().equals(null));
		for(int i = 0; i < colors.length; i++){
			assertTrue(map.getKingCouncil().getCouncillorColor(i).equals(colors[i]));
		}
	}
	
	@Test
	public void testGetTownList() {
		TMap map = new TMap(network, balc);
		assertTrue(!map.getTownList().equals(null));
		for(TTown i : network.vertexSet()){
			assertTrue(map.getTownList().contains(i));
		}
	}
}