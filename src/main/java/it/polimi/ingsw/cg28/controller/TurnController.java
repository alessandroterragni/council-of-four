package it.polimi.ingsw.cg28.controller;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import com.google.common.base.Preconditions;

/**
 * Manages the turns queue. Adds one {@link BazaarTurn} everytime bazaarProgressBar reaches bazaarTrigger.
 * If last round boolean is true, stop stacking new turns.
 * @author Mario
 *
 */
public class TurnController {
	
	private List<PlayerID> players;
	private Set<PlayerID> inactivePlayers;
	private PlayerID marketPlayer;
	private Queue<PlayerTurn> turnList;
	private final int bazaarTrigger;
	private int bazaarProgressBar;
	private boolean lastRound;
	
	/**
	 * Constructor of the class. Fill the queue with one turn for each player.
	 * Also checks progressBar to insert bazaar turn.
	 * @param players Game players
	 * @param bazaarTrigger Trigger for bazaarProgressBar set by the user, if negative bazaar is never played
	 * @param gameID The gameID to send marketPlayer messages to all players
	 * @throws NullPointerException if players parameter is null
	 */
	public TurnController(List<PlayerID> players, int bazaarTrigger, String gameID){
		
		Preconditions.checkNotNull(players);
		
		this.players = players;
		this.bazaarTrigger = bazaarTrigger;
		turnList = new LinkedList<>();
		
		if (bazaarTrigger > 0){
			this.marketPlayer = new PlayerID(gameID);
		}
		
		this.bazaarProgressBar = 0;
		this.lastRound = false;
		
		for(PlayerID player: players){
			this.newPlayerTurn(player);
		}
		
		inactivePlayers = new HashSet<>();
	}
	
	/**
	 * Adds a new PlayerTurn to the queue. Checks bazaarProgressBar.
	 * @param player Player of turn to add.
	 */
	private void newPlayerTurn(PlayerID player){
			turnList.add(new PlayerTurn(player));
	}
	

	
	/**
	 * Poll of queue head. If lastRound is false, adds to turnList another turn for the player or,
	 * if bazaarProgressBar equals to bazaarTrigger a new BazaarTurn. Turns of inactive players
	 * are ignored but not deleted to allow reconnection(not yet supported).
	 * @return Turn head of turnList queue 
	 */
	public GameTurn nextTurn(){
		
		if (bazaarProgressBar == bazaarTrigger){
			bazaarProgressBar = 0;
			return new BazaarTurn(marketPlayer);
		}
		
		GameTurn turn = turnList.poll();
		
		if(!(lastRound))
			newPlayerTurn(turn.getPlayer());
		
		bazaarProgressBar ++;
		
		if(!inactivePlayers.contains(turn.getPlayer()))
			return turn;
		else return nextTurn();
		
	}
	
	/**
	 * Setter boolean value of lastRound.
	 * When last round is true you can only empty the queue. 
	 * If lastRound is set to true it also remove from the list the last turn added.
	 * @param lastRound New value for lastRound boolean
	 */
	public void setLastRound(boolean lastRound) {
		
		this.lastRound = lastRound;
		
		if (lastRound){
			
			Iterator<PlayerTurn> iterator = turnList.iterator();
			for(int i = 0; i < turnList.size() - 1; i++) {
				iterator.next();
			}
        
			turnList.remove(iterator.next());
		}

	}
	
	/**
	 * Getter queue turnList status: returns true if the queue is empty 
	 * @return boolean value: true if queue is empty
	 */
	public boolean isQueueEmpty(){
		if (turnList.peek() == null)
			return true;
		return false;
	}
	
	/**
	 * Getter PlayerID associated to bazaarTurn
	 * @return PlayerID associated to bazaarTurn
	 */
	public PlayerID getMarketPlayer() {
		return marketPlayer;
	}
	
	/**
	 * Adds player to set of inactive Players. Player turns won't be played, but the player isn't 
	 * deleted to allow reconnection(not yet implemented).
	 */
	public void inactivePlayer(PlayerID player) {
		
		inactivePlayers.add(player);
		
	}
	
	/**
	 * Returns set of inactive players.
	 * @return Set<PlayerID> Set of PlayerIDs of inactive players
	 */
	public Set<PlayerID> getInactivePlayers() {
		return inactivePlayers;
	}
	
	/**
	 * Return true if there's only one active player
	 * @return boolean: true if there's only one active player
	 */
	public boolean onlyOnePlayer() {
		
		return inactivePlayers.size() + 1 == players.size();
		
	}
	
	

}
