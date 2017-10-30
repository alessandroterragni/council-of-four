package it.polimi.ingsw.cg28.model;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.controller.bonus.Bonus;
import it.polimi.ingsw.cg28.controller.bonus.CoinBonus;
import it.polimi.ingsw.cg28.model.parser.ModelTestConfiguration;

public class ScoreCalculatorTest {

	private ModelStatus model;
	private PlayerID[] players = new PlayerID[2];
	private ScoreCalculator scoreCalc;
	
	@Before
	public void before(){
		players[0] = new PlayerID("Player 1");
		players[1] = new PlayerID("Player 2");
	
		ModelTestConfiguration config = new ModelTestConfiguration();
		model = config.buildModelTest(players);
		
		scoreCalc = new ScoreCalculator(model);
	}
	
	@Test (expected = NullPointerException.class)
	public void testScoreCalculatorWithNullModelParameter() {
		new ScoreCalculator(null);
	}
	
	@Test
	public void testScoreCalculatorConstructor() {
		ScoreCalculator newScoreCalc = new ScoreCalculator(model);
		assertNotNull(newScoreCalc);
	}
	
	@Test
	public void testComputeScoreWithNoTie() {
		model.getPlayer(players[0]).getScore().setValue(47);;
		model.getPlayer(players[1]).getScore().setValue(31);
		
		model.getPlayer(players[0]).getNobilityRank().increment(5);
		model.getPlayer(players[1]).getNobilityRank().increment(2);
		
		Bonus bonus = new CoinBonus(1);
		String[] let = { "Arkon" };
		model.getPlayer(players[1]).getPossessedTiles().add(new BusinessPermitTile(bonus, let));
		
		int[] scores = scoreCalc.computeScore();
		
		assertTrue(scores.length == 2);
		assertTrue(scores[0] == 47+5);
		assertTrue(scores[1] == 31+2+3);
	}
	
	@Test
	public void testComputeScoreWithNobilityFirstPlaceTie() {
		model.getPlayer(players[0]).getScore().setValue(47);;
		model.getPlayer(players[1]).getScore().setValue(31);
		
		model.getPlayer(players[0]).getNobilityRank().increment(5);
		model.getPlayer(players[1]).getNobilityRank().increment(5);
		
		Bonus bonus = new CoinBonus(1);
		String[] let = { "Arkon" };
		model.getPlayer(players[1]).getPossessedTiles().add(new BusinessPermitTile(bonus, let));
		
		int[] scores = scoreCalc.computeScore();
		
		assertTrue(scores.length == 2);
		assertTrue(scores[0] == 47+5);
		assertTrue(scores[1] == 31+5+3);
	}
	
	@Test
	public void testComputeScoreWithNobilitySecondPlaceTie() {
		PlayerID[] players = new PlayerID[3];
		
		players[0] = new PlayerID("Player 1");
		players[1] = new PlayerID("Player 2");
		players[2] = new PlayerID("Player 3");
	
		ModelTestConfiguration config = new ModelTestConfiguration();
		model = config.buildModelTest(players);
		
		scoreCalc = new ScoreCalculator(model);
		model.getPlayer(players[0]).getScore().setValue(47);
		model.getPlayer(players[1]).getScore().setValue(31);
		model.getPlayer(players[2]).getScore().setValue(11);
		
		model.getPlayer(players[0]).getNobilityRank().increment(5);
		model.getPlayer(players[1]).getNobilityRank().increment(5);
		model.getPlayer(players[2]).getNobilityRank().increment(6);
		
		Bonus bonus = new CoinBonus(1);
		String[] let = { "Arkon" };
		model.getPlayer(players[1]).getPossessedTiles().add(new BusinessPermitTile(bonus, let));
		
		int[] scores = scoreCalc.computeScore();
		
		assertTrue(scores.length == 3);
		assertTrue(scores[0] == 47+2);
		assertTrue(scores[1] == 31+2+3);
		assertTrue(scores[2] == 11+5);
	}
	
	@Test
	public void testComputeScoreWithTileTie() {
		model.getPlayer(players[0]).getScore().setValue(47);;
		model.getPlayer(players[1]).getScore().setValue(31);
		
		model.getPlayer(players[0]).getNobilityRank().increment(5);
		model.getPlayer(players[1]).getNobilityRank().increment(6);
		
		Bonus bonus = new CoinBonus(1);
		String[] let = { "Arkon" };
		model.getPlayer(players[0]).getPossessedTiles().add(new BusinessPermitTile(bonus, let));
		model.getPlayer(players[1]).getPossessedTiles().add(new BusinessPermitTile(bonus, let));
		
		
		int[] scores = scoreCalc.computeScore();
		
		assertTrue(scores.length == 2);
		assertTrue(scores[0] == 47+2+3);
		assertTrue(scores[1] == 31+5+3);
	}
	
	@Test (expected = NullPointerException.class)
	public void testGetWinnerWithNullScoresParameter() {
		scoreCalc.getWinner(null);
	}
	
	@Test
	public void testGetWinnerWithNoTie() {
		model.getPlayer(players[0]).getScore().setValue(47);;
		model.getPlayer(players[1]).getScore().setValue(31);
		
		model.getPlayer(players[0]).getNobilityRank().increment(5);
		model.getPlayer(players[1]).getNobilityRank().increment(2);
		
		Bonus bonus = new CoinBonus(1);
		String[] let = { "Arkon" };
		model.getPlayer(players[1]).getPossessedTiles().add(new BusinessPermitTile(bonus, let));
		
		int[] scores = scoreCalc.computeScore();
		
		Player winner = scoreCalc.getWinner(scores);
		
		assertTrue(winner.equals(model.getPlayer(players[0])));
	}
	
	@Test
	public void testGetWinnerWithTieResolvedOnAssistantNumber() {
		model.getPlayer(players[0]).getScore().setValue(47);;
		model.getPlayer(players[1]).getScore().setValue(47);
		
		model.getPlayer(players[0]).getNobilityRank().increment(5);
		model.getPlayer(players[1]).getNobilityRank().increment(2);
		
		Bonus bonus = new CoinBonus(1);
		String[] let = { "Arkon" };
		model.getPlayer(players[1]).getPossessedTiles().add(new BusinessPermitTile(bonus, let));
		
		model.getPlayer(players[0]).getAssistants().increment(4);
		model.getPlayer(players[1]).getAssistants().increment(7);
		
		model.getPlayer(players[0]).getPoliticCardsHand().add(new PoliticCard(Color.ORANGE, false));
		model.getPlayer(players[1]).getPoliticCardsHand().add(new PoliticCard(null, true));
		
		int[] scores = scoreCalc.computeScore();
		
		Player winner = scoreCalc.getWinner(scores);
		
		assertTrue(winner.equals(model.getPlayer(players[1])));
	}
	
	@Test
	public void testGetWinnerWithTieResolvedOnPoliticCardsNumber() {
		model.getPlayer(players[0]).getScore().setValue(47);;
		model.getPlayer(players[1]).getScore().setValue(47);
		
		model.getPlayer(players[0]).getNobilityRank().increment(5);
		model.getPlayer(players[1]).getNobilityRank().increment(2);
		
		Bonus bonus = new CoinBonus(1);
		String[] let = { "Arkon" };
		model.getPlayer(players[1]).getPossessedTiles().add(new BusinessPermitTile(bonus, let));
		
		model.getPlayer(players[0]).getAssistants().increment(4);
		model.getPlayer(players[1]).getAssistants().increment(4);
		
		model.getPlayer(players[0]).getPoliticCardsHand().add(new PoliticCard(Color.ORANGE, false));
		model.getPlayer(players[1]).getPoliticCardsHand().add(new PoliticCard(null, true));
		model.getPlayer(players[1]).getPoliticCardsHand().add(new PoliticCard(Color.BLACK, false));
		
		int[] scores = scoreCalc.computeScore();
		
		Player winner = scoreCalc.getWinner(scores);
		
		assertTrue(winner.equals(model.getPlayer(players[1])));
	}
	
	@Test
	public void testProduceRankingWithNoFinalTie() {
		PlayerID[] players = new PlayerID[3];
		
		players[0] = new PlayerID("Giovanna");
		players[1] = new PlayerID("Panegirico");
		players[2] = new PlayerID("Aston Martin");
	
		ModelTestConfiguration config = new ModelTestConfiguration();
		model = config.buildModelTest(players);
		
		scoreCalc = new ScoreCalculator(model);
		model.getPlayer(players[0]).getScore().setValue(23);
		model.getPlayer(players[1]).getScore().setValue(19);
		model.getPlayer(players[2]).getScore().setValue(19);
		
		model.getPlayer(players[0]).getNobilityRank().increment(5);
		model.getPlayer(players[1]).getNobilityRank().increment(5);
		model.getPlayer(players[2]).getNobilityRank().increment(6);
		
		Bonus bonus = new CoinBonus(1);
		String[] let = { "Arkon" };
		model.getPlayer(players[1]).getPossessedTiles().add(new BusinessPermitTile(bonus, let));
		
		model.getPlayer(players[0]).getAssistants().setValue(0);
		model.getPlayer(players[2]).getPoliticCardsHand().add(new PoliticCard(Color.BLACK, false));
		model.getPlayer(players[2]).getAssistants().setValue(10);
		
		int[] scores = scoreCalc.computeScore();
		
		Map<PlayerID, Integer> ranking = scoreCalc.produceRanking(scores);
		
		assertNotNull(ranking);
		assertTrue(ranking.keySet().size() == 3 && ranking.values().size() == 3);
		for(Integer score : ranking.values()){
			assertTrue(ranking.get(scoreCalc.getWinner(scores).getID()) >= score);
		}
	}

}
