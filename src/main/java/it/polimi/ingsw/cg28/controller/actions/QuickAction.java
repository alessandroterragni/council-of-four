/**
 * 
 */
package it.polimi.ingsw.cg28.controller.actions;

import it.polimi.ingsw.cg28.controller.updaters.PlayerTurnUpdater;
import it.polimi.ingsw.cg28.model.Player;

/**
 * An implementation of the Action interface that will be used for the quick actions.
 * @author Alessandro
 *
 */
public class QuickAction implements Action {

	/**
	 * It removes a quick action from the set of the current turn.
	 * @param player the Player who performs the Action
	 * @return a new PlayerTurnUpdater to manage turn advancements.
	 */
	@Override
	public PlayerTurnUpdater act(Player player) {
		player.getTurn().takeAction(this);   
		return new PlayerTurnUpdater();
	}

}
