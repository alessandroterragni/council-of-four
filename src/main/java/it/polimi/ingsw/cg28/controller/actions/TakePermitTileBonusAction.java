/**
 * 
 */
package it.polimi.ingsw.cg28.controller.actions;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;
import it.polimi.ingsw.cg28.controller.bonus.Bonus;
import it.polimi.ingsw.cg28.controller.bonus.BonusPack;
import it.polimi.ingsw.cg28.controller.updaters.BonusActionUpdater;
import it.polimi.ingsw.cg28.controller.updaters.Updater;
import it.polimi.ingsw.cg28.model.BusinessPermitTile;
import it.polimi.ingsw.cg28.model.Player;
import it.polimi.ingsw.cg28.model.Region;

/**
 * It's a bonus that allows the player to take one or more chosen Tiles from a chosen Region without paying anything
 * @author Mario, Alessandro
 *
 */
public class TakePermitTileBonusAction implements Action{

	private List<BusinessPermitTile> tilesChosen;
	private Region[] regions;
	
	/**
	 * It's the constructor of the class.
	 * @param tilesChosen the arrayLit of tiles you want to take
	 * @param regions the array of regions of the tiles you want to take
	 */
	public TakePermitTileBonusAction(List<BusinessPermitTile> tilesChosen, Region[] regions) {
		Preconditions.checkNotNull(tilesChosen, "tilesChosen can't be null");
		Preconditions.checkNotNull(regions, "regions can't be null");
		this.tilesChosen=tilesChosen;
		this.regions = regions;
	}
	
	
	/**
	 * This method performs the bonus action and sets player turn isBonus boolean to false. 
	 * @param p is the player who gain the bonus
	 * @return a BonusActionUpdater to activate bonus gained.
	 * @throws NullPointerException if player is null
	 */
	@Override
	public Updater act(Player p){
		
		Preconditions.checkNotNull(p, "player can't be null");
		
		for(Region region: regions){
			BusinessPermitTile[] tiles = region.getUncovered();
			for(int i = 0; i< tiles.length; i++)
				if(tilesChosen.contains(tiles[i])){
					p.takeTile(tiles[i]);
					region.changeTile(i);
				}
		}
					
		ArrayList<Bonus> bonuses = new ArrayList<>();
		
		for(int i=0;i<tilesChosen.size();i++)
			bonuses.add(tilesChosen.get(i).getBonuses());
		
		if(p.getTurn() != null)
			p.getTurn().setBonus(false);
		
		return new BonusActionUpdater(new BonusPack(bonuses));
	}
}
