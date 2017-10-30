package it.polimi.ingsw.cg28.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import it.polimi.ingsw.cg28.controller.PlayerID;

/**
 * Provides the methods used to compute the scores of players who are participating to a single game.
 * @author Marco
 *
 */
public class ScoreCalculator {

	private final ModelStatus model;
	private final int playerNum;
	
	/**
	 * The constructor of the class, creates a new instance of ScoreCalculator.
	 * @param model - The game model containing data about the players whose score has to be calculated
	 * @throws NullPointerException if the given model is null
	 */
	public ScoreCalculator(ModelStatus model) {
		if(model == null){
			throw new NullPointerException("Can't compute scores based on a null model.");
		}
		this.model = model;
		playerNum = model.getPlayers().length;
	}
	
	/**
	 * Checks the given model's player: finds the player having the highest nobility rank among for this
	 * match or, if it is the case, if there are more players that scored the same highest rank; it then assigns
	 * 5 victory points to each of the latter.
	 * <br>Then proceeds to check again, in the same way, for the second place reward of 2 victory points.
	 * <br>At last, it checks who owns more permit tiles than the others among the players of this match
	 * and assigns a reward of 3 victory points in a similar fashion to every player having that many tiles.
	 * <br>After assigning the end-game rewards, the method builds and returns an array containing the integer
	 * values of each player's score, with the single score's position corresponding to the player's position
	 * in the game model's array.
	 * @return An integer array containing the final player scores in the order assigned by the model
	 */
	public int[] computeScore() {
		
		int[] scores = new int[playerNum];
	
		//find out who has the highest rank
		int maxNobilityRank = -1;
		Player highestRank = null;
		for(Player p : model.getPlayers()){
			if(p.getNobilityRank().getValue() > maxNobilityRank){
				maxNobilityRank = p.getNobilityRank().getValue();
				highestRank = p;
			}
		}
		
		//find out if two or more player are first on the nobility track (there is a tie)
		boolean nobilityRankTie = false;
		for(Player p : model.getPlayers()){
			if(!p.equals(highestRank) && p.getNobilityRank().getValue() == maxNobilityRank){
				nobilityRankTie = true;
			}
		}
		
		//if there is a tie for the first place
		if(nobilityRankTie){
			for(Player p : model.getPlayers()){
				//increment by 5 the score of each of the players that came first on the nobility track
				if(p.getNobilityRank().getValue() == maxNobilityRank){
					p.getScore().increment(5);
				}
			}
		}
		//if there is no tie for the first place
		else {
			//increment by 5 the score of the only player that came first on the nobility track
			highestRank.getScore().increment(5);
			
			//find out who has the second highest nobility rank
			int secondNobilityRank = -1;
			Player secondHighestRank = null;
			for(Player p : model.getPlayers()){
				if(p.getNobilityRank().getValue() > secondNobilityRank &&
						p.getNobilityRank().getValue() < maxNobilityRank){
					secondNobilityRank = p.getNobilityRank().getValue();
					secondHighestRank = p;
				}
			}
			
			//find out if there is a tie for the second place
			boolean secondPlaceTie = false;
			for(Player p : model.getPlayers()){
				if(!p.equals(secondHighestRank) && p.getNobilityRank().getValue() == secondNobilityRank){
					secondPlaceTie = true;
				}
			}
			
			//if there is a tie for the second place
			if(secondPlaceTie){
				for(Player p : model.getPlayers()){
					//increment by 2 the score of each player that came second on the nobility track
					if(p.getNobilityRank().getValue() == secondNobilityRank){
						p.getScore().increment(2);
					}
				}
			}
			//if there is no tie for the second place
			else {
				//increment by 2 the score of the only player that came second on the nobility track
				secondHighestRank.getScore().increment(2);
			}
		}
		
		//find out who owns the highest amount of permit tiles
		int maxPermitTileNum = -1;
		Player owner = null;
		for(Player p : model.getPlayers()){
			int ownedTiles = p.getPossessedTiles().size() + p.getUsedTiles().size();
			if( ownedTiles > maxPermitTileNum ){
				maxPermitTileNum = ownedTiles;
				owner = p;
			}
		}
		if(owner == null){
			owner = model.getPlayers()[0];
		}
		
		boolean permitTileTie = false;
		for(Player p : model.getPlayers()){
			if(!p.equals(owner) && (p.getPossessedTiles().size() + p.getUsedTiles().size()) == maxPermitTileNum){
				permitTileTie = true;
			}
		}
		
		//if there is a tie for the number of permit tiles
		if(permitTileTie){
			//increment by 3 the score of all the players that owned the same highest amount of permit tiles
			for(Player p : model.getPlayers()){
				if( (p.getPossessedTiles().size() + p.getUsedTiles().size()) == maxPermitTileNum){
					p.getScore().increment(3);
				}
			}
		}
		//if there is no tie for the number of permit tiles
		else {
			//increment by 3 the score of the player that owned the highest amount of permit tiles
			owner.getScore().increment(3);
		}
		
		//assign the scores in the array (the index in the scores array is the same as the players array's one!)
		for(int i = 0; i < playerNum; i++){
			scores[i] = model.getPlayers()[i].getScore().getValue();
		}
		
		return scores;
		
	}
	
	/**
	 * Extracts the winner from an input integer array, based on its numerical value.
	 * <br>If there is a tie (two or more players finished with the same score) it checks the number of
	 * assistants and politic cards combined to determine the winner.
	 * @param scores - An array of integers with a set of scores
	 * @return The winner of the match, based on his/her final score or, in case of a tie, on his/her
	 * wealth in terms of politic cards and assistants combined
	 * @throws NullPointerException if the given array of scores is null
	 */
	public Player getWinner(int[] scores) {
		if(scores == null){
			throw new NullPointerException("Can't find a winner in a null ranking array.");
		}
		
		//find out the max value in the scores array
		int maxScore = -1;
		Player winner = null;
		for(int i = 0; i < scores.length; i++){
			if(scores[i] > maxScore){
				maxScore = scores[i];
				winner = model.getPlayers()[i];
			}
		}
		
		//find out if there is a tie on the final score
		Set<Player> inTie = new HashSet<>();
		boolean scoreTie = false;
		for(int i = 0; i < scores.length-1; i++){
			for(int j = i+1; j < scores.length; j++){
				if(scores[i] == scores[j] && scores[i] == maxScore){
					scoreTie = true;
					inTie.add(model.getPlayers()[i]);
					inTie.add(model.getPlayers()[j]);
				}
			}
		}
		
		if(scoreTie){
			//find out who is the wealthiest player (most assistants and politic cards combined)
			int highestWealth = -1;
			Player wealthiest = null;
			for(Player p : inTie){
				int wealth = p.getAssistants().getValue() + p.getPoliticCardsHand().size();
				if( wealth > highestWealth ){
					highestWealth = wealth;
					wealthiest = p;
				}
			}
			
			return wealthiest;
		}
		else {			
			return winner;
		}
	}
	
	/**
	 * Produces and returns a Map containing the couples PlayerID -> Score.
	 * Used to print out results.
	 * @param scores - The scores to base on to produce a ranking
	 * @return A HashMap containing the couples PlayerID -> Integer score
	 */
	public Map<PlayerID, Integer> produceRanking(int[] scores) {
		
		Map<PlayerID, Integer> ranking = new HashMap<>();
		for(int i = 0; i < scores.length; i++){
			ranking.put(model.getPlayers()[i].getID(), new Integer(scores[i]));
		}
		
		return ranking;
	}
}
