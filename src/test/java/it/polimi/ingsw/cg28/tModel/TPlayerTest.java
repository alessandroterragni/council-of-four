package it.polimi.ingsw.cg28.tModel;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.tmodel.TBonus;
import it.polimi.ingsw.cg28.tmodel.TBusinessPermitTile;
import it.polimi.ingsw.cg28.tmodel.TPlayer;

public class TPlayerTest {
	
	private Color[] hand = { Color.BLACK, Color.ORANGE, Color.WHITE };
	private String[] codes = { "VPB", "CB" };
	private String[] letters = { "Arkon", "Juvelar", "Graden" };
	private TBonus b1 = new TBonus(codes);
	private TBonus b2 = new TBonus(codes);
	private TBonus b3 = new TBonus(codes);
	private TBusinessPermitTile t1 = new TBusinessPermitTile(b1, letters);
	private TBusinessPermitTile t2 = new TBusinessPermitTile(b2, letters);
	private TBusinessPermitTile t3 = new TBusinessPermitTile(b3, letters);
	private List<TBusinessPermitTile> poss = new ArrayList<>();
	private List<TBusinessPermitTile> used = new ArrayList<>();
	private TPlayer player;
	
	@Before
	public void before() {
		poss.add(t1);
		poss.add(t2);
		poss.add(t3);
		used.add(t2);
		player = new TPlayer("Marco","UUID", 10, 10, 0, 0, 1, hand, poss, used);
	}

	@Test (expected = NullPointerException.class)
	public void testTPlayerWithNullNameParameter() {
		new TPlayer(null,"UUID", 10, 10, 0, 0, 1, hand, poss, used);
	}
	
	@Test (expected = NullPointerException.class)
	public void testTPlayerWithNullIdParameter() {
		new TPlayer("Marco", null, 10, 10, 0, 0, 1, hand, poss, used);
	}
	
	@Test (expected = NullPointerException.class)
	public void testTPlayerWithNullHandParameter() {
		new TPlayer("Marco","UUID", 10, 10, 0, 0, 1, null, poss, used);
	}
	
	@Test (expected = NullPointerException.class)
	public void testTPlayerWithNullPossessedTilesParameter() {
		new TPlayer("Marco","UUID", 10, 10, 0, 0, 1, hand, null, used);
	}

	@Test (expected = NullPointerException.class)
	public void testTPlayerWithNullUsedTilesParameter() {
		new TPlayer("Marco","UUID", 10, 10, 0, 0, 1, hand, poss, null);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testTPlayerWithNegativeEmporiumsParameter() {
		new TPlayer("Marco","UUID", -5, 10, 0, 0, 1, hand, poss, used);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testTPlayerWithNegativeCoinsParameter() {
		new TPlayer("Marco","UUID", 10, -4, 0, 0, 1, hand, poss, used);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testTPlayerWithNegativeRankParameter() {
		new TPlayer("Marco","UUID", 10, 10, -3, 0, 1, hand, poss, used);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testTPlayerWithNegativePointsParameter() {
		new TPlayer("Marco","UUID", 10, 10, 0, -2, 1, hand, poss, used);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testTPlayerWithNegativeAssistantsParameter() {
		new TPlayer("Marco","UUID", 10, 10, 0, 0, -1, hand, poss, used);
	}
	
	@Test
	public void testTPlayer() {
		TPlayer newPlayer = new TPlayer("Marco","UUID", 10, 10, 0, 0, 1, hand, poss, used);
		assertTrue(!newPlayer.equals(null));
		assertTrue(newPlayer.getName().equals("Marco"));
		assertTrue(!newPlayer.getPoliticCardsHand().equals(null));
		assertTrue(!newPlayer.getPossessedTiles().equals(null));
		assertTrue(!newPlayer.getUsedTiles().equals(null));
	}
	
	@Test
	public void testGetName() {
		assertTrue(player.getName().equals("Marco"));
	}
	
	@Test
	public void testGetId() {
		assertTrue(player.getId().equals("UUID"));
	}
	
	@Test
	public void testGetEmpNumber() {
		assertTrue(player.getEmpNumber() == 10);
	}
	
	@Test
	public void testGetCoins() {
		assertTrue(player.getCoins() == 10);
	}
	
	@Test
	public void testGetNobilityRank() {
		assertTrue(player.getNobilityRank() == 0);
	}
	
	@Test
	public void testGetVictoryPoints() {
		assertTrue(player.getVictoryPoints() == 0);
	}
	
	@Test
	public void testGetAssistants() {
		assertTrue(player.getAssistants() == 1);
	}
	
	@Test
	public void testGetPoliticCardsHand() {
		assertTrue(player.getPoliticCardsHand().equals(hand));
		for(int i = 0; i < player.getPoliticCardsHand().length; i++){
			assertTrue(player.getPoliticCardsHand()[i].equals(hand[i]));
		}
		assertTrue(player.getPoliticCardsHand().length == 3);
	}
	
	@Test
	public void testGetPossessedTiles() {
		assertTrue(player.getPossessedTiles().equals(poss));
		for(int j = 0; j < player.getPossessedTiles().size(); j++){
			assertTrue(player.getPossessedTiles().get(j).equals(poss.get(j)));
		}
	}
	
	@Test
	public void testGetUsedTiles() {
		assertTrue(player.getUsedTiles().equals(used));
		for(int k = 0; k < player.getUsedTiles().size(); k++){
			assertTrue(player.getUsedTiles().get(k).equals(used.get(k)));
		}
	}

}
