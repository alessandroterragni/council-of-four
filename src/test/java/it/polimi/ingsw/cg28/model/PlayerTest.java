package it.polimi.ingsw.cg28.model;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.controller.PlayerTurn;
import it.polimi.ingsw.cg28.controller.bonus.VictoryPointBonus;
import it.polimi.ingsw.cg28.model.parser.ModelTestConfiguration;

public class PlayerTest {
	
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
	public void testPlayerWithNullIDParameter() {
		new Player(null, null, 10, new CoinTrack(10), new PoliticCard[6], new AssistantCounterTrack(1));
	}
	
	@Test (expected = NullPointerException.class)
	public void testPlayerWithNullCoinsParameter() {
		new Player(new PlayerID("CiccioChocobo"), null, 10, null, new PoliticCard[6], new AssistantCounterTrack(1));
	}
	
	@Test (expected = NullPointerException.class)
	public void testPlayerWithNullStarterHandParameter() {
		new Player(new PlayerID("CiccioChocobo"), null, 10, new CoinTrack(10), null, new AssistantCounterTrack(1));
	}
	
	@Test (expected = NullPointerException.class)
	public void testPlayerWithNullAssistantsParameter() {
		new Player(new PlayerID("CiccioChocobo"), null, 10, new CoinTrack(10), new PoliticCard[6], null);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testPlayerWithNegativeEmpNumParameter() {
		new Player(new PlayerID("CiccioChocobo"), null, -10, new CoinTrack(10), new PoliticCard[6], new AssistantCounterTrack(1));
	}
	
	@Test
	public void testPlayer() {
		Player p = new Player(new PlayerID("CiccioChocobo"), null, 10, new CoinTrack(10),
				new PoliticCard[2], new AssistantCounterTrack(1));
		assertTrue(!p.equals(null));
		assertTrue(p.getID().getName().equals(new PlayerID("CiccioChocobo").getName()));
		assertTrue(p.getEmporiumNumber() == 10);
		assertTrue(p.getCoins().getValue() == 10);
		assertTrue(p.getNobilityRank().getValue() == 0);
		assertTrue(p.getScore().getValue() == 0);
		assertTrue(p.getAssistants().getValue() == 1);
		assertTrue(!p.getPossessedTiles().equals(null));
		assertTrue(!p.getUsedTiles().equals(null));
		assertTrue(!p.getPoliticCardsHand().equals(null));
	}
	
	@Test
	public void testGetEmporiumNumber() {
		assertTrue(model.getPlayer(players[0]).getEmporiumNumber() == 10);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testSetEmporiumNumberWithNegativeParameter() {
		model.getPlayer(players[0]).setEmporiumNumber(-200);
	}
	
	@Test
	public void testSetEmporiumNumber() {
		Player test = model.getPlayer(players[0]);
		assertTrue(test.getEmporiumNumber() == 10);
		test.setEmporiumNumber(3);
		assertTrue(test.getEmporiumNumber() == 3);
	}
	
	@Test
	public void testGetCoins() {
		assertTrue(!model.getPlayer(players[0]).getCoins().equals(null));
		assertTrue(model.getPlayer(players[0]).getCoins().getValue() == 10);
	}
	
	@Test
	public void testGetNobilityRank() {
		assertTrue(!model.getPlayer(players[0]).getNobilityRank().equals(null));
		assertTrue(model.getPlayer(players[0]).getNobilityRank().getValue() == 0);
	}
	
	@Test
	public void testGetScore() {
		assertTrue(!model.getPlayer(players[0]).getScore().equals(null));
		assertTrue(model.getPlayer(players[0]).getScore().getValue() == 0);
	}
	
	@Test
	public void testGetAssistants() {
		assertTrue(!model.getPlayer(players[0]).getAssistants().equals(null));
		assertTrue(model.getPlayer(players[0]).getAssistants().getValue() == 1);
	}
	
	@Test
	public void testGetID() {
		assertTrue(model.getPlayer(players[0]).getID().equals(players[0]));
	}
	
	@Test
	public void testGetTurn() {
		assertTrue(model.getPlayer(players[0]).getTurn() == null);
	}
	
	@Test
	public void testSetTurn() {
		PlayerTurn turn = new PlayerTurn(players[0]);
		model.getPlayer(players[0]).setTurn(turn);
		assertTrue(model.getPlayer(players[0]).getTurn().equals(turn));
	}
	
	@Test
	public void testGetPoliticCardsHand() {
		assertTrue(!model.getPlayer(players[0]).getPoliticCardsHand().equals(null));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testGetCardWithNegativeIntIndexParameter() {
		model.getPlayer(players[0]).getCard(-1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testGetCardWithIntIndexParameterOutOfBounds() {
		model.getPlayer(players[0]).getCard(1000);
	}
	
	@Test
	public void testGetCardByIntIndex() {
		PoliticCard card = model.getPlayer(players[0]).getCard(1);
		assertTrue(card.equals(model.getPlayer(players[0]).getPoliticCardsHand().get(1)));
	}
	
	@Test
	public void testGetPossessedTiles() {
		assertTrue(!model.getPlayer(players[0]).getPossessedTiles().equals(null));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testGetTileWithNegativeIntIndexParameter() {
		model.getPlayer(players[0]).getTile(-1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testGetTileWithIntIndexParameterOutOfBounds() {
		model.getPlayer(players[0]).getTile(1000);
	}
	
	@Test
	public void testGetTileByIntIndex() {
		String[] letters = {"Arkon"};
		model.getPlayer(players[0]).takeTile(new BusinessPermitTile(new VictoryPointBonus(4), letters));
		BusinessPermitTile tile = model.getPlayer(players[0]).getTile(0);
		assertTrue(tile.equals(model.getPlayer(players[0]).getPossessedTiles().get(0)));
	}
	
	@Test
	public void testGetUsedTiles() {
		assertTrue(!model.getPlayer(players[0]).getUsedTiles().equals(null));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testGetReusedTileWithNegativeIntIndexParameter() {
		model.getPlayer(players[0]).getReusedTile(-1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testGetReusedTileWithIntIndexParameterOutOfBounds() {
		model.getPlayer(players[0]).getReusedTile(1000);
	}
	
	@Test
	public void testGetReusedTileByIntIndex() {
		String[] letters = {"Arkon"};
		model.getPlayer(players[0]).takeTile(new BusinessPermitTile(new VictoryPointBonus(4), letters));
		model.getPlayer(players[0]).useTile(model.getPlayer(players[0]).getTile(0));
		BusinessPermitTile tile = model.getPlayer(players[0]).getReusedTile(0);
		assertTrue(tile.equals(model.getPlayer(players[0]).getUsedTiles().get(0)));
	}
	
	@Test (expected = NullPointerException.class)
	public void testTakeTileWithNullParameter() {
		model.getPlayer(players[0]).takeTile(null);
	}
	
	@Test
	public void testTakeTile() {
		String[] letters = {"Arkon"};
		BusinessPermitTile tile = new BusinessPermitTile(new VictoryPointBonus(4), letters);
		model.getPlayer(players[0]).takeTile(tile);
		assertTrue(model.getPlayer(players[0]).getPossessedTiles().contains(tile));
	}
	
	@Test (expected = NullPointerException.class)
	public void testBuyTileWithNullParameter() {
		model.getPlayer(players[0]).buyTile(null);
	}
	
	@Test
	public void testBuyTile() {
		String[] letters = {"Arkon"};
		BusinessPermitTile tile = new BusinessPermitTile(new VictoryPointBonus(4), letters);
		model.getPlayer(players[0]).buyTile(tile);
		assertTrue(model.getPlayer(players[0]).getPossessedTiles().contains(tile));
	}
	
	@Test (expected = NullPointerException.class)
	public void testUseTileWithNullParameter() {
		model.getPlayer(players[0]).useTile(null);
	}
	
	@Test
	public void testUseTile() {
		String[] letters = {"Arkon"};
		BusinessPermitTile tile = new BusinessPermitTile(new VictoryPointBonus(4), letters);
		model.getPlayer(players[0]).takeTile(tile);
		assertTrue(model.getPlayer(players[0]).getPossessedTiles().contains(tile));
		model.getPlayer(players[0]).useTile(tile);
		assertTrue(model.getPlayer(players[0]).getUsedTiles().contains(tile));
	}
	
	@Test (expected = NullPointerException.class)
	public void testSellTileWithNullParameter() {
		model.getPlayer(players[0]).sellTile(null);
	}
	
	@Test
	public void testSellTile() {
		String[] letters = {"Arkon"};
		BusinessPermitTile tile = new BusinessPermitTile(new VictoryPointBonus(4), letters);
		model.getPlayer(players[0]).takeTile(tile);
		assertTrue(model.getPlayer(players[0]).getPossessedTiles().contains(tile));
		model.getPlayer(players[0]).sellTile(tile);
		assertTrue(!model.getPlayer(players[0]).getPossessedTiles().contains(tile));
		assertTrue(!model.getPlayer(players[0]).getUsedTiles().contains(tile));
	}
	
	@Test (expected = NullPointerException.class)
	public void testAddCardsWithNullParameter() {
		model.getPlayer(players[0]).addCards((List<PoliticCard>) null);
	}
	
	@Test
	public void testAddCards() {
		List<PoliticCard> cards = new ArrayList<>();
		cards.add(new PoliticCard(Color.BLACK, false));
		cards.add(new PoliticCard(null, true));
		model.getPlayer(players[0]).addCards(cards);
		assertTrue(model.getPlayer(players[0]).getPoliticCardsHand().containsAll(cards));
	}
	
	@Test (expected = NullPointerException.class)
	public void testAddCardWithNullParameter() {
		model.getPlayer(players[0]).addCards((PoliticCard) null);
	}
	
	@Test
	public void testAddCard() {
		PoliticCard card = new PoliticCard(Color.ORANGE, false);
		model.getPlayer(players[0]).addCards(card);
		assertTrue(model.getPlayer(players[0]).getPoliticCardsHand().contains(card));
	}
	
	@Test (expected = NullPointerException.class)
	public void testDiscardCardsWithNullParameter() {
		model.getPlayer(players[0]).discardPoliticCards((List<PoliticCard>) null);
	}

	@Test
	public void testDiscardCards() {
		List<PoliticCard> toDiscard = new ArrayList<>();
		toDiscard.add(model.getPlayer(players[0]).getPoliticCardsHand().get(0));
		model.getPlayer(players[0]).discardPoliticCards(toDiscard);
		assertTrue(!model.getPlayer(players[0]).getPoliticCardsHand().contains(toDiscard));
	}

	@Test (expected = IllegalArgumentException.class)
	public void testRankUpWithNegativeParameter() {
		model.getPlayer(players[0]).rankUp(-1);
	}
	
	@Test
	public void testRankUp() {
		model.getPlayer(players[0]).rankUp(1);
		assertTrue(model.getPlayer(players[0]).getNobilityRank().getValue() == 1);
	}
	
	@Test
	public void testBuildEmporium() {
		model.getPlayer(players[0]).buildEmporium();
		assertTrue(model.getPlayer(players[0]).getEmporiumNumber() == 9);
	}
	
}
