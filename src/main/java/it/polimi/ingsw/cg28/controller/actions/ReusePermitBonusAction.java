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

/**
 * It's a bonus that allows the player to re-gain one or more BusinessPermitTile bonuses that he has already gained.
 * @author Alessandro
 *
 */
public class ReusePermitBonusAction implements Action{
	
	private List<BusinessPermitTile> tiles;
	
	/**
	 * It's the constructor of the class
	 * @param tile List of Business Permit Tile that the player wants to reuse
	 * @throws NullPointerException if the parameter passed is null
	 */
	public ReusePermitBonusAction(List<BusinessPermitTile> tile) {
		Preconditions.checkNotNull(tile, "tile can't be null");
		this.tiles=tile;
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
		
		List<Bonus> bonuses = new ArrayList<>();
		
		for(int i=0;i<tiles.size();i++)
			bonuses.add(tiles.get(i).getBonuses());
		
		if(p.getTurn() != null)
			p.getTurn().setBonus(false);
	
		return new BonusActionUpdater(new BonusPack(bonuses));
		
	}

}
