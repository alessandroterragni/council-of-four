package it.polimi.ingsw.cg28.model;

import java.util.List;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.model.decks.CardDeck;
import it.polimi.ingsw.cg28.model.decks.TileDeck;

/**
 * Represent a brief resumée about the game's status.
 * @author Marco
 *
 */
public class ModelStatus {
	
	private final GameMap map;
	private Player[] players;
	private final Region[] regions;
	private CardDeck<PoliticCard> politicCardsDeck;
	private TileDeck<BonusTile> bonusTiles;
	private List<KingRewardTile> kingRewards;
	private List<Councillor> noblesPool;
	private NobilityTrackBonus nobilityTrackBonus;
	private boolean endGame;
	
	/**
	 * The constructor of the class, creates a new instance of ModelStatus.
	 * @param map - The game's map
	 * @param players - The array of players participating in the game
	 * @param regions - The array of the map's regions
	 * @param politicCardsDeck - The CardDeck containing all the politic cards
	 * @param bonusTiles - The TileDeck containing all the bonus tiles
	 * @param kingRewards - The List of king reward tiles
	 * @param noblesPool - A list of all the available councillors
	 * @param nobilityTrackBonus - The NobilityTrackBonus object representing the position of
	 * bonuses on the nobility track
	 */
	public ModelStatus(GameMap map, Player[] players, Region[] regions, 
			CardDeck<PoliticCard> politicCardsDeck, TileDeck<BonusTile> bonusTiles, 
			List<KingRewardTile> kingRewards, List<Councillor> noblesPool, NobilityTrackBonus nobilityTrackBonus){
		
		this.map = map;
		this.players = players;
		this.regions = regions;
		this.politicCardsDeck = politicCardsDeck;
		this.bonusTiles = bonusTiles;
		this.kingRewards = kingRewards;
		this.noblesPool = noblesPool;
		this.setNobilityTrackBonus(nobilityTrackBonus);
		this.endGame = false;
		
		initialize();

	}
	
	/**
	 * Private method to initialise the model status.
	 */
	private void initialize(){
		
		for(Region region : regions)
			for(int i=0; i< region.getUnconveredNum(); i++)
				region.changeTile(i);
		
	}
	/**
	 * getPlayer fetches the Player from its ID.
	 * @param playerID - The ID of the searched player
	 * @return The player whose ID was given in input, null if the given ID is not
	 * associated to any in-game player.
	 */
	public Player getPlayer(PlayerID playerID){
		
		for(Player p : this.players){
			if(p.getID().equals(playerID)) 
				return p;
		}
		return null;
	}
	
	/**
	 * Fetches the players' array.
	 * @return An array containing all of the in-game players
	 */
	public Player[] getPlayers() {
		return players;
	}

	/**
	 * Sets the players' array to the desired values.
	 * @param players - The array of players to be set
	 */
	public void setPlayers(Player[] players) {
		this.players = players;
	}

	/**
	 * Fetches the game's map.
	 * @return The GameMap object associated with this match
	 */
	public GameMap getMap() {
		return map;
	}
	
	/**
	 * getRegion returns a specified region of the array regions[]
	 * @param i the index of the region the method returns
	 * @return the i-th region of the array regions[]
	 */
	public Region getRegion(int i){
		return regions[i];
	}
	
	/**
	 * Method that returns a specified councillor.
	 * @param i the index of the councillor the method returns
	 * @return the i-th councillor in the arrayList noblesPool
	 */
	public Councillor getCouncillor(int i){
		return noblesPool.get(i);
	}

	/**
	 * Fetches the politic cards' deck.
	 * @return The CardDeck containing all of the politic cards
	 */
	public CardDeck<PoliticCard> getPoliticCardsDeck() {
		return politicCardsDeck;
	}

	/**
	 * Fetches the bonus tiles' deck.
	 * @return The TileDeck containing all of the bonus tiles
	 */
	public TileDeck<BonusTile> getBonusTiles() {
		return bonusTiles;
	}

	/**
	 * Fetches the list of king reward tiles.
	 * @return The List of king reward tiles
	 */
	public List<KingRewardTile> getKingRewards() {
		return kingRewards;
	}

	/**
	 * Signals whether the game ended or not.
	 * @return The boolean value indicating the end of the game or less
	 */
	public boolean isEndGame() {
		return endGame;
	}

	/**
	 * Sets the boolean value indicating the end of the game or less to the desired value.
	 * @param endGame - The boolean value to be set
	 */
	public void setEndGame(boolean endGame) {
		this.endGame = endGame;
	}

	/**
	 * Fetches the object indicating the nobility track bonuses.
	 * @return The NobilityTrackBonus object associated with the game
	 */
	public NobilityTrackBonus getNobilityTrackBonus() {
		return nobilityTrackBonus;
	}
	
	/**
	 * Sets the nobility track bonus disposition to the desired value.
	 * @param nobilityTrackBonus - The NobilityTrackBonus to be set
	 */
	private void setNobilityTrackBonus(NobilityTrackBonus nobilityTrackBonus) {
		this.nobilityTrackBonus = nobilityTrackBonus;
	}
	
	/**
	 * Fetches the regions' array.
	 * @return An array containing the map's region
	 */
	public Region[] getRegions() {
		return regions;
	}

	/**
	 * Fetches the available nobles pool.
	 * @return the noblesPool
	 */
	public List<Councillor> getNoblesPool() {
		return noblesPool;
	}
	
	
}
