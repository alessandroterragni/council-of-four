package it.polimi.ingsw.cg28.controller.actions;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.bonus.Bonus;
import it.polimi.ingsw.cg28.controller.bonus.BonusPack;
import it.polimi.ingsw.cg28.controller.updaters.BonusActionUpdater;
import it.polimi.ingsw.cg28.controller.updaters.Updater;
import it.polimi.ingsw.cg28.model.Player;
import it.polimi.ingsw.cg28.model.Town;

/**
 * It's a bonus that allows the player to reuse one or more bonuses of the cities in which he has an emporium.
 * @author Alessandro
 *
 */
public class ReuseCityBonusAction implements Action {

	private List<Town> towns;
	
	/**
	 * It's the constructor of the class
	 * @param towns the list of the towns chosen by the player to retrieve the related bonus.
	 * @throws NullPointerException if towns is null
	 */
	public ReuseCityBonusAction(List<Town> towns) {
		Preconditions.checkNotNull(towns, "Towns can't be null");
		this.towns=towns;
	}
	
	/**
	 * This method performs the bonus action and sets player turn isBonus boolean to false.
	 * @param p is the player who gain the bonus
	 * @return a BonusActionUpdater to activate bonus gained.
	 * @throws NullPointerException if player is null
	 */
	@Override
	public Updater act(Player p){
		
		Preconditions.checkNotNull(p, "Player can't be null");
		
		List<Bonus> bonuses = new ArrayList<>();
		
		for(int i=0;i<towns.size();i++)
			bonuses.add(towns.get(i).getBonus());
		
		if(p.getTurn() != null)
			p.getTurn().setBonus(false);
		
		return new BonusActionUpdater(new BonusPack(bonuses));
	}
}
