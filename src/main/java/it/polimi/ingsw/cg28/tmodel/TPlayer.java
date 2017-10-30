package it.polimi.ingsw.cg28.tmodel;

import java.awt.Color;
import java.io.Serializable;
import java.util.List;

/**
 * Represents a Player variant which can be transfered from the server to the client without
 * making the real model's rep accessible from a remote client
 * @author Marco
 *
 */
public class TPlayer implements Serializable{

	private static final long serialVersionUID = 991263381953720367L;
	private final String name;
	private final String id;
	private final int empNumber;
	private final int coins;
	private final int nobilityRank;
	private final int victoryPoints;
	private final int assistants;
	private final Color[] politicCardsHand;
	private final List<TBusinessPermitTile> possessedTiles;
	private final List<TBusinessPermitTile> usedTiles;

	/**
	 * The constructor of the class, creates a new TPlayer.
	 * @param name - The player's name
	 * @param string - The player's id
	 * @param emp - The number of emporiums this player has not built yet
	 * @param coins - This player's amount of coins
	 * @param rank - This player's nobility rank
	 * @param points - This player's score on the victory track
	 * @param assistants - This player's number of assistants
	 * @param hand - An array containing the colors of the cards in this player's hand
	 * @param possTiles - A list of TBusinessPermitTiles objects representing the tiles
	 * this player currently owns
	 * @param usedTiles - A list of TBusinessPermitTiles objects representing the tiles
	 * this player used during the match
	 * @throws NullPointerException if any parameter among name, hand, possTiles and
	 * usedTiles is null
	 * @throws IllegalArgumentException if any integer parameter is negative
	 */
	public TPlayer(String name, String id, int emp, int coins, int rank, int points, int assistants, Color[] hand,
			List<TBusinessPermitTile> possTiles, List<TBusinessPermitTile> usedTiles) {
		
		if(name == null || id == null || hand == null || possTiles == null || usedTiles == null){
			throw new NullPointerException("Can't create a TPlayer with any null parameters.");
		}
		
		if(emp < 0 || coins < 0 || rank < 0 || points < 0 || assistants < 0){
			throw new IllegalArgumentException("No track counter for the player can have a negative value.");
		}
		
		this.name = name;
		this.id = id;
		this.empNumber = emp;
		this.coins = coins;
		this.nobilityRank = rank;
		this.victoryPoints = points;
		this.assistants = assistants;
		this.politicCardsHand = hand;
		this.possessedTiles = possTiles;
		this.usedTiles = usedTiles;
	}


	/**
	 * Fetches this player's name.
	 * @return a string containing the player's name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Fetches this player's id.
	 * @return a string containing the player's id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Fetches the number of emporiums this player can still build.
	 * @return the integer amount of emporiums not yet built by this player
	 */
	public int getEmpNumber() {
		return empNumber;
	}

	/**
	 * Fetches the number of coins this player owns.
	 * @return the integer amount of coins owned by this player
	 */
	public int getCoins() {
		return coins;
	}

	/**
	 * Fetches this player's nobility rank.
	 * @return the integer indicating the player's nobility rank
	 */
	public int getNobilityRank() {
		return nobilityRank;
	}

	/**
	 * Fetches this player's score.
	 * @return the integer score of this player
	 */
	public int getVictoryPoints() {
		return victoryPoints;
	}

	/**
	 * Fetches this player's number of assistants.
	 * @return the integer amount of available assistants for this player to use
	 */
	public int getAssistants() {
		return assistants;
	}

	/**
	 * Fetches this player's hand as an array of the cards' colors.
	 * @return an array of Color objects, corresponding to the player's hand card colors
	 */
	public Color[] getPoliticCardsHand() {
		return politicCardsHand;
	}

	/**
	 * Fetches the list of TBusinessPermitTiles owned by this player.
	 * @return a list containing all the TBusinessPermitTile owned and not used by the player
	 */
	public List<TBusinessPermitTile> getPossessedTiles() {
		return possessedTiles;
	}

	/**
	 * Fetches the list of TBusinessPermitTiles used by this player.
	 * @return a list containing all the TBusinessPermitTile used by the player
	 */
	public List<TBusinessPermitTile> getUsedTiles() {
		return usedTiles;
	}	
}
