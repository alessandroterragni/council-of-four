package it.polimi.ingsw.cg28.controller;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.view.messages.event.EventMsg;

/**
 * The abstract class to represents a turn of the game.
 * @author Mario, Marco
 *
 */
public abstract class GameTurn {
	
	private final PlayerID player;
	
	/**
	 * The constructor of the class.
	 * @param player - PlayerID of turn's player
	 * @throws NullPointerException if player parameter is null
	 */
	public GameTurn(PlayerID player){
		Preconditions.checkNotNull(player);
		this.player = player;
	}
	
	/**
	 * Returns the request (EventMsg) for the player for the specific kind of turn.
	 * @return request (EventMsg) for the player for the specific kind of turn
	 */
	public abstract EventMsg msgRequired();

	/**
	 * Returns true if turn can end, false otherwise.
	 * @return
	 */
	public abstract boolean isEnded();
	
	/**
	 * Sets the turn as currentTurn for the model. Act on the model to initialise the turn.
	 * @param model - model of the game
	 */
	public abstract void setTurn(ModelStatus model);
	
	/**
	 * The ID of of turn's player.
	 * @return PlayerID of turn's player
	 */
	public PlayerID getPlayer(){
		return this.player;
	};

	
}
