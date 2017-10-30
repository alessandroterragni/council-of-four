package it.polimi.ingsw.cg28.controller.bazaar;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.exception.NoMoreTurnException;

/**
 * Manages a bazaar turn queue. First, each player has a BazaarSellPlayerTurn turn in the same game order, after those turns, in a casual order
 * each player has a BazaarBuyPlayerTurn turn.
 * @author Mario
 *
 */
public class BazaarTurnController {
	
	private Queue<BazaarPlayerTurn> sellList;
	private Queue<BazaarPlayerTurn> buyList;
	
	
	/**
	 * Constructor of class. Fills queue of bazaar turns. For each player adds one BazaarSellPlayerTurn
	 * and one BazaarBuyPlayerTurn. BazaarSellPlayerTurn are in the same order of gameTurnQueue.  BazaarBuyPlayerTurn
	 * are randomly ordered.
	 * @param players - list of players playing bazaar
	 * @param playersMap - Map: key set PlayerIDs(players playing bazaar), values boolean[](boolean values of what the player can sell: 
	 * 		  canSell[0] - Assistants; canSell[1] - Politic Cards, canSell[2] - Business Permit Tiles)
	 * @throws NullPointerException if players or playersMap parameter are null
	 * @throws IllegalArgumentException if a player in players list doesn't has a value as key in the map playersMap
	 */
	public BazaarTurnController(List<PlayerID> players, Map<PlayerID, boolean[]> playersMap){
		
		Preconditions.checkNotNull(playersMap);
		Preconditions.checkNotNull(players);
		
		sellList = new LinkedList<>();
		buyList = new LinkedList<>();
		
		for(PlayerID player: players){
			boolean[] canSell = playersMap.get(player);
			
			if (canSell == null)
				throw new IllegalArgumentException();
			
			sellList.add(new BazaarSellPlayerTurn(player, canSell));
		}
		
		Collections.shuffle(players);
		
		for(PlayerID player: players)
			buyList.add(new BazaarBuyPlayerTurn(player));
		
	}
	
	/**
	 * Return the next turn.
	 * @return next BazaarPlayerTurn
	 * @throws NoMoreTurnException if turns queue is empty
	 */
	public BazaarPlayerTurn nextTurn() throws NoMoreTurnException{
		
		BazaarPlayerTurn turn;
		
		if(!sellList.isEmpty())
			turn = sellList.poll();
		else
			if(!buyList.isEmpty())
				turn = buyList.poll();
			else throw new NoMoreTurnException();
		
		return turn;
	}

	
	/**
	 * Return true is turn queue is empty
	 * @return boolean
	 */
	public boolean isEmpty() {
		
		if(sellList.isEmpty() && buyList.isEmpty())
			return true;
		return false;
		
	}
	
	/** 
	 * Removes turns of specified Player from the queue.
	 * @param player to delete
	 */
	public void deletePlayer(PlayerID player) {
		
        Iterator<BazaarPlayerTurn> sellIterator = sellList.iterator();
        while (sellIterator.hasNext()) {
            if(sellIterator.next().getPlayer().equals(player))
            	sellIterator.remove();
        }
        
        Iterator<BazaarPlayerTurn> buyIterator = buyList.iterator();
        while (buyIterator.hasNext()) {
            if(buyIterator.next().getPlayer().equals(player))
            	buyIterator.remove();
        }
		
	}

}
