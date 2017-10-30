package it.polimi.ingsw.cg28.controller;

import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.Player;
import it.polimi.ingsw.cg28.view.messages.event.EventMsg;

/**
 * Represents a Bazaar complete round of turns in the turn management of the game. 
 * @author Mario
 *
 */
public class BazaarTurn extends GameTurn {
	
	/**
	 * Constructor of the class.
	 * @param PlayerID - ID of turn's player
	 */
	public BazaarTurn(PlayerID player) {
		super(player);
	}
	
	/**
	 * No message required. Returns null.
	 */
	@Override
	public EventMsg msgRequired() {
		return null;
	}
	
	/**
	 * Returns true.
	 */
	@Override
	public boolean isEnded() {
		return true;
	}
	
	/**
	 * Sets at null the turn field of each Player.
	 */
	@Override
	public void setTurn(ModelStatus model) {
		
		for(Player player : model.getPlayers())
			model.getPlayer(player.getID()).setTurn(null);	
		
	}
	

}
