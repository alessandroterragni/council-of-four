package it.polimi.ingsw.cg28.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.cg28.controller.actions.Action;
import it.polimi.ingsw.cg28.controller.actions.ActionFactory;
import it.polimi.ingsw.cg28.controller.updaters.NotValidMoveUpdater;
import it.polimi.ingsw.cg28.controller.updaters.Updater;
import it.polimi.ingsw.cg28.exception.InvalidActionException;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.view.messages.action.ActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.ToTranslate;
import it.polimi.ingsw.cg28.view.messages.event.EventMsg;

/**
 * Abstract game controller for a turn-based game.
 * @author Mario
 *
 */
public abstract class AbstractGameController {
	
	ActionFactory actionFactory;
	ModelStatus model;
	PlayerID currentPlayer;
	
	/**
	 * Initialise the game and returns the start message related to the game.
	 * @return event start message related to the game
	 */
	public abstract EventMsg startGame();
	
	/**
	 * Return true if game is ended.
	 * @return true if game is ended.
	 */
	public abstract boolean endGame();
	
	/**
	 * Initialise a new turn for the successive player.
	 * @return event message related to the successive turn
	 */
	public abstract EventMsg getNextTurn();
	
	/**
	 * Update about requested ActionMsg. Uses the ActionFactory set as field to translate the
	 * ToTranslate message given as parameter and to carry out its effects on the model.
	 * Returns an Updater (strategy pattern) that defines the strategy to manage action 
	 * side-effects on game status and next interactions required.
	 * @param actionMsg
	 */
	public Updater update(ToTranslate actionMsg){
		
		Action action = null;
		
		try {
			action = actionMsg.translate(actionFactory);
		} catch (InvalidActionException | NumberFormatException |IndexOutOfBoundsException | NullPointerException e){
			Logger.getLogger(Game.class.getName()).log(Level.WARNING, e.getMessage(), e);
			return new NotValidMoveUpdater(e.getMessage());
		}

		return action.act(model.getPlayer(currentPlayer));
		
	}
	
	/**
	 * Returns the player who is playing the current turn.
	 * @return the player who is playing the current turn.
	 */
	public abstract PlayerID getCurrentPlayer();
	
	/**
	 * Returns the request to progress in the game.
	 * @return EventMsg request to progress in the game
	 */
	public abstract EventMsg toDo();
	
	/**
	 * Returns the message related to the end of the game.
	 * @return EventMsg message related to the end of the game
	 */
	public abstract ActionMsg endMsg();
	
	/**
	 * Returns the message to update the players on game status (model and player status)
	 * @return EventMsg to update the players on game status (model and player status)
	 */
	public abstract ActionMsg updateMsg();
	
	/**
	 * Marks the player passed as parameter as inactive.
	 * Returns false if there's only one player active
	 */
	public abstract boolean inactivePlayer(PlayerID player);

}
