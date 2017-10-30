/**
 * 
 */
package it.polimi.ingsw.cg28.controller.actions;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.updaters.PlayerTurnUpdater;
import it.polimi.ingsw.cg28.model.Player;

/**
 * It's a QuickAction that allows the player to do a second MainAction in the current Turn.
 * @author Alessandro
 *
 */
public class OneMoreMainAction extends QuickAction {
	
	
	/**
	 * It decrements the Player's number of Assistants by 3 and it adds a Main Action in the current turn
	 * it removes a quick action from the set of the current turn calling the super act method.
	 * @param player the Player who performs the Action
	 * @return a new PlayerTurnUpdater to manage turn advancements
	 * @throws NullPointerException if the player passed is null
	 */
	@Override
	public PlayerTurnUpdater act(Player player){
		Preconditions.checkNotNull(player, "Player can't be null");
		player.getAssistants().decrement(3);
		player.getTurn().addAction(new MainAction());
		super.act(player);
		return new PlayerTurnUpdater();
	}
	
}
