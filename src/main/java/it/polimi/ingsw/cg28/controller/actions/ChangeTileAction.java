/**
 * 
 */
package it.polimi.ingsw.cg28.controller.actions;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.updaters.PlayerTurnUpdater;
import it.polimi.ingsw.cg28.model.Player;
import it.polimi.ingsw.cg28.model.Region;
/**
 * It's a quick action that allows the player to change the two uncovered BusinessPermitTiles of a chosen Region.
 * @author Alessandro
 *
 */
public class ChangeTileAction extends QuickAction {

	private Region region;
	
	/**
	 * It's the constructor of the class
	 * @param region   the region in which I want to change the tiles
	 * @throws NullPointerException if the region passed is null
	 */
	public ChangeTileAction(Region region) {
		Preconditions.checkNotNull(region, "Region can't be null");
		this.region=region;
	}
	
	/**
	 * It decrements the number of Assistants of the Player p by 1 and it changes the BusinessPermitTile of the chosen Region.
	 * It removes a quick action from the set of the current turn calling the super act method 
	 * @param player the Player who performs the Action
	 * @return a new PlayerTurnUpdater to manage turn advancements.
	 * @throws NullPointerException if the player passed is null
	 */
	@Override
	public PlayerTurnUpdater act(Player player) 
	{	
		Preconditions.checkNotNull(player, "Player can't be null");
		super.act(player);
		player.getAssistants().decrement(1);
		
		for(int i=0; i < region.getUnconveredNum();i++){
			region.reshuffleTile(i);
		}
		
		return new PlayerTurnUpdater();
	}
}
