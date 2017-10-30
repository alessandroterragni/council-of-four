/**
 * 
 */
package it.polimi.ingsw.cg28.controller.actions;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.updaters.PlayerTurnUpdater;
import it.polimi.ingsw.cg28.model.Player;

/**
 * It's a Quick Action that allows the player to hire an Assistant.
 * @author Alessandro
 *
 */
public class HireAssistantAction extends QuickAction {
   
	/**
     * It increments the Player's number of Assistants by 1,
     * it decrements the Player's coins by 3,
     * it removes a quick action from the set of the current turn calling the super act method.
     * @param player the Player who performs the Action
	 * @return a new PlayerTurnUpdater to manage turn advancements.
	 * @throws NullPointerException if the player passed is null
     */
	@Override
	public PlayerTurnUpdater act(Player player){
		Preconditions.checkNotNull(player, "Player can't be null");
		player.getAssistants().increment(1);
		player.getCoins().decrement(3);
		super.act(player);
		return new PlayerTurnUpdater();
	}
}
