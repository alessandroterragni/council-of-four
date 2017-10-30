package it.polimi.ingsw.cg28.controller.updaters;

import it.polimi.ingsw.cg28.controller.ActionMsgHandler;

/**
 * Updates Game, GameController and Model managing Actions effects and side-effects like bonuses.
 * Implements a Strategy Pattern, Actions return an Updater Object choosing automatically
 * update Strategy for game progress.
 * @author Mario
 *
 */
@FunctionalInterface
public interface Updater {
	
	/**
	 * Updates Game, GameController and Model managing Actions effects and side-effects like bonuses
	 * @param actionMsgHandler Handler of action effects
	 */
	public void update(ActionMsgHandler actionMsgHandler);

}
