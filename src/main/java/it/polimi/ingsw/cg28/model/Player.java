package it.polimi.ingsw.cg28.model;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.controller.PlayerTurn;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player entity, whose attributes model the current situation for each
 * player. It offers all the methods to manage, update and refer to the player's
 * status.
 * @author Marco
 *
 */
public class Player {

	private final PlayerID playerID;
	private PlayerTurn turn;
	private int emporiumNumber;
	private final CoinTrack coins;
	private final NobilityTrack nobilityRank;
	private final VictoryTrack score;
	private final AssistantCounterTrack assistants;
	private List<PoliticCard> politicCardsHand;
	private List<BusinessPermitTile> possessedTiles;
	private List<BusinessPermitTile> usedTiles;
	
	/**
	 * The constructor of the class.
	 * @param playerID - The player's identifier (object composed by UUID and Name)
	 * @param turn - The player's current turn object
	 * @param empNum - The number of emporium left and available to build
	 * @param coins - The counter used to store the number of coins owned by the player
	 * @param starterHand - The array of politic cards representing the starter hand
	 * of the player
	 * @param assistants - The counter used to store the number of assistants hired by
	 * the player
	 * @throws NullPointerException if one among id, coins, starterHand and assistants is null
	 * @throws IllegalArgumentException if empNum is negative
	 */
	public Player(PlayerID playerID, PlayerTurn turn, int empNum, CoinTrack coins,
			PoliticCard[] starterHand, AssistantCounterTrack assistants) {
		
		if(playerID == null || coins == null || starterHand == null || assistants == null){
			throw new NullPointerException("Player ID, coin and assistant counters and initial hand"
					+ " can't be null.");
		}
		if(empNum < 0){
			throw new IllegalArgumentException("The number of emporiums can't be negative.");
		}

		this.playerID = playerID;
		this.turn = turn;
		this.emporiumNumber = empNum;
		this.coins = coins;
		this.nobilityRank = new NobilityTrack(0);
		this.score = new VictoryTrack(0);
		this.assistants = assistants;
		this.politicCardsHand = new ArrayList<>();
		for(PoliticCard c : starterHand){
			politicCardsHand.add(c);
		}
		this.possessedTiles = new ArrayList<>();
		this.usedTiles = new ArrayList<>();
	}

	/**
	 * Fetches the number of emporiums still available to the player.
	 * @return The integer number of remaining emporiums
	 */
	public int getEmporiumNumber() {
		return emporiumNumber;
	}

	/**
	 * Sets the remaining emporium to the desired value.
	 * @param empNum - The value to be set
	 * @throws IllegalArgumentException if the input value is negative
	 */
	public void setEmporiumNumber(int empNum) {
		if(empNum < 0){
			throw new IllegalArgumentException("Can't set the player's number of emporium to a negative amount.");
		}
		this.emporiumNumber = empNum;
	}

	/**
	 * Fetches the player's CoinTrack.
	 * @return The player's CoinTrack
	 */
	public CoinTrack getCoins() {
		return coins;
	}

	/**
	 * Fetches the player's NobilityTrack.
	 * @return The player's NobilityTrack
	 */
	public NobilityTrack getNobilityRank() {
		return nobilityRank;
	}

	/**
	 * Fetches the player's VictoryTrack.
	 * @return The player's VictoryTrack
	 */
	public VictoryTrack getScore() {
		return score;
	}

	/**
	 * Fetches the player's AssistantCounterTrack.
	 * @return The player's AssistantCounterTrack
	 */
	public AssistantCounterTrack getAssistants() {
		return assistants;
	}

	/**
	 * Fetches the player's ID.
	 * @return the player's ID
	 */
	public PlayerID getID() {
		return playerID;
	}

	/**
	 * Fetches the player's turn.
	 * @return The PlayerTurn object representing this player's turn
	 */
	public PlayerTurn getTurn() {
		return turn;
	}

	/**
	 * Sets the player's turn to the desired value
	 * @param turn - The turn to be set
	 */
	public void setTurn(PlayerTurn turn) {
		this.turn = turn;
	}

	/**
	 * Fetches this player's politic cards hand.
	 * @return A List containing all of the player's politic cards
	 */
	public List<PoliticCard> getPoliticCardsHand() {
		return politicCardsHand;
	}
	
	/**
	 * Fetches a single card based on its offset in the player's hand.
	 * @param position - The integer position of the desired card
	 * @return The card occupying the input position
	 * @throws IllegalArgumentException if the specified position is negative or greater than the player's
	 * hand's size
	 */
	public PoliticCard getCard(int position){
		if(position < 0 || position > this.politicCardsHand.size()){
			throw new IllegalArgumentException("No card with such index in player's hand.");
		}
		return this.politicCardsHand.get(position);
	}

	/**
	 * Fetches the permit tiles owned by the player.
	 * @return A List of the Business Permit Tiles owned by this player
	 */
	public List<BusinessPermitTile> getPossessedTiles() {
		return possessedTiles;
	}
	
	/**
	 * Fetches a single permit tile based on its offset in the player's set of possessed tiles.
	 * @param position - The integer position of the desired tile
	 * @return The tile occupying the input position
	 * @throws IllegalArgumentException if the specified position is negative or greater than the player's
	 * list of possessed tile's size
	 */
	public BusinessPermitTile getTile(int position){
		if(position < 0 || position > this.possessedTiles.size()){
			throw new IllegalArgumentException("No tile with such index in player's possessed tiles list.");
		}
		return this.possessedTiles.get(position);
	}

	/**
	 * Fetches the permit tiles used by the player.
	 * @return A List of all the permit tiles already used by the player
	 */
	public List<BusinessPermitTile> getUsedTiles() {
		return usedTiles;
	}
	
	/**
	 * Fetches a single permit tile based on its offset in the player's set of used tiles.
	 * @param position - The integer position of the desired tile
	 * @return The tile occupying the input position
	 * @throws IllegalArgumentException if the specified position is negative or greater than the player's
	 * list of used tile's size
	 */
	public BusinessPermitTile getReusedTile(int position){
		if(position < 0 || position > this.usedTiles.size()){
			throw new IllegalArgumentException("No tile with such index in player's used tiles list.");
		}
		return usedTiles.get(position);
	}
	
	/**
	 * takeTile is used to add the tile chosen via view to the player's
	 * possessedTiles list. The method notifies the relative bonus updater
	 * to assign bonuses.
	 * @param chosenTile - The tile to add to possessedTiles
	 * @throws NullPointerException if the specified tile is null
	 */
	public void takeTile(BusinessPermitTile chosenTile){
		if(chosenTile == null){
			throw new NullPointerException("Chosen tile can't be null.");
		}
		possessedTiles.add(chosenTile);
	}
	
	/**
	 * buyTile is used to add the tile chosen in the bazaar to the player's
	 * possessedTiles list. It does NOT notify any updater, because the bonus
	 * has already been assigned once to the seller player.
	 * @param chosenTile - The tile to add to possessedTiles
	 * @throws NullPointerException if the specified tile is null
	 */
	public void buyTile(BusinessPermitTile chosenTile){
		if(chosenTile == null){
			throw new NullPointerException("Chosen tile can't be null.");
		}
		possessedTiles.add(chosenTile);
	}
	
	/**
	 * useTile adds the specified tile to the usedTiles list and removes it
	 * from possessedTiles. 
	 * @param chosenTile - The tile to move to usedTiles
	 * @throws NullPointerException if the specified tile is null
	 */
	public void useTile(BusinessPermitTile chosenTile){
		if(chosenTile == null){
			throw new NullPointerException("Chosen tile can't be null.");
		}
		usedTiles.add(chosenTile);
		possessedTiles.remove(chosenTile);
	}
	
	/**
	 * sellTile removes the tile to sell from the possessedTiles list
	 * WITHOUT adding it to the usedTiles list.
	 * @param chosenTile - The tile to sell in the bazaar
	 * @throws NullPointerException if the specified tile is null
	 */
	public void sellTile(BusinessPermitTile chosenTile){
		if(chosenTile == null){
			throw new NullPointerException("Chosen tile can't be null.");
		}
		possessedTiles.remove(chosenTile);
	}
	
	/**
	 * This addCards adds a list of multiple drawn cards to the player's hand.
	 * @param draw - The list of cards to be added
	 * @throws NullPointerException if the list of cards to draw is null
	 */
	public void addCards(List<PoliticCard> draw){
		if(draw == null){
			throw new NullPointerException("Card list to add can't be a null list.");
		}
		for(PoliticCard c : draw){
			politicCardsHand.add(c);
		}
	}
	
	/**
	 * This addCards adds a single card to the player's hand.
	 * @param card - The card to be added
	 * @throws NullPointerException if the specified card is null
	 */
	public void addCards(PoliticCard card){
		if(card == null){
			throw new NullPointerException("The card can't be null.");
		}
		politicCardsHand.add(card);
	}
	
	/**
	 * discardPoliticCards takes the specified set of cards and removes
	 * it from the player's hand.
	 * @param cards - The ArrayList containing all the cards to discard
	 * @throws NullPointerException if the list of cards to discard is null
	 */
	public void discardPoliticCards(List<PoliticCard> cards){
		if(cards == null){
			throw new NullPointerException("Can't discard a null list of cards.");
		}
		for(PoliticCard c : cards){
			politicCardsHand.remove(c);
		}
	}
	
	/**
	 * Increases the nobility rank of this player of the specified amount.
	 * @param degree - The amount of degrees to increase
	 * @throws IllegalArgumentException if the increase value is negative
	 */
	public void rankUp(int degree){

		if(degree < 0){
			throw new IllegalArgumentException("Can't increase the nobility rank by a negative amount.");
		}
		
		this.nobilityRank.increment(degree);		
	}
	
	/**
	 * Decrements the number of emporiums still available to the player.
	 */
	public void buildEmporium(){
		this.emporiumNumber--;
	}
}
