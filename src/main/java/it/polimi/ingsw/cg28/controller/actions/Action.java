package it.polimi.ingsw.cg28.controller.actions;

import it.polimi.ingsw.cg28.controller.updaters.Updater;
import it.polimi.ingsw.cg28.model.Player;

/**
 * It's the Action interface that provides the act method required by all actions.
 * @author Alessandro
 *
 */
@FunctionalInterface
public interface Action {
	
	/**
	 * Act method that must be implemented to specify how to perform actions 
	 * @param p the Player who performs the Action
	 */
	public abstract Updater act(Player p);
}
