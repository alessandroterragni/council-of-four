/**
 * 
 */
package it.polimi.ingsw.cg28.controller.actions;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.updaters.BuildUpdater;
import it.polimi.ingsw.cg28.model.BusinessPermitTile;
import it.polimi.ingsw.cg28.model.GameMap;
import it.polimi.ingsw.cg28.model.Player;
import it.polimi.ingsw.cg28.model.Town;

/**
 * It's a Main Action that allows the player to build an emporium with a BusinessPermitTile
 * @author Alessandro
 *
 */
public class EmporiumTileAction extends MainAction {
	
	private BusinessPermitTile tile; 
	private Town town;
	private GameMap map;
	
	/**
	 * It's the constructor of the class
	 * @param tile the tile I want to use to build the emporium 
	 * @param town the town I want to build the emporium in
	 * @throws NullPointerException if one of the parameter is null
	 */
	public EmporiumTileAction(BusinessPermitTile tile, Town town, GameMap map){
		Preconditions.checkNotNull(tile, "tile can't be null");
		Preconditions.checkNotNull(town, "town can't be null");
		Preconditions.checkNotNull(map, "map can't be null");
		this.tile=tile;
		this.town=town;
		this.map = map;
	}
	
	/**
	 * It checks the equality of the initial letter of name of the city and the letter on the card,
	 * it checks the existence of other emporiums of the same player in the chosen city,
	 * it counts the number of emporium in the city,
	 * it decrements by that number the Player's assistants,
	 * it builds an emporium in the city
	 * it removes a Main action from the set of the current turn calling the super act method.
	 * @param player the Player who performs the Action
	 * @return a new BuildUpdater, in this way, the bonuses eventually gained will be activated
	 */
	@Override
	public BuildUpdater act(Player player){
		
		for(int i=0;i<tile.getLetterCodes().length;i++)
				if( town.getTownName().equals(tile.getLetterCodes()[i])
						&& !town.hasEmporium(player.getID())){
							
							int decrementValue= town.emporiumNumber();
							player.getAssistants().decrement(decrementValue);
							player.buildEmporium();
							
							map.addEmporium(player.getID(), town);

							player.useTile(tile);
							
							super.act(player);
							break;
						}
				
		return new BuildUpdater(town);
	}
	
}
