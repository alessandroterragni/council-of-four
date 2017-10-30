/**
 * 
 */
package it.polimi.ingsw.cg28.controller.actions;
import java.util.List;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.updaters.PlayerTurnUpdater;
import it.polimi.ingsw.cg28.model.Balcony;
import it.polimi.ingsw.cg28.model.Councillor;
import it.polimi.ingsw.cg28.model.Player;

/**
 * It's a Main Action that allows the player to elect a councillor on a specified balcony
 * @author Alessandro
 *
 */

public class ElectionAction extends MainAction {

	private Balcony balcony;
	private Councillor councillor;
	private List<Councillor> councillors;
	
	/**
	 * It's the constructor of the class
	 * @param balcony  the balcony in which I want to elect the councillor
	 * @param councillor   the councillor I want to elect
	 * @param councillors the nobles pool where you put the replaced councillor
	 * @throws NullPointerException if the Balcony or councillor or councillors passed are null
	 */
	public ElectionAction(Balcony balcony,Councillor councillor, List<Councillor> councillors){
		Preconditions.checkNotNull(balcony, "Balcony can't be null");
		Preconditions.checkNotNull(councillor, "councillor can't be null");
		Preconditions.checkNotNull(councillors, "Councillors can't be null");
		this.balcony=balcony;
		this.councillor=councillor;
		this.councillors=councillors;
	}
	
	/**
	 * It adds the chosen councillor on the chosen balcony, removing the the last councillor of the balcony,
	 * it adds the remove one to the noblesPool councillors passed as a parameter,
	 * it increments the Player's coins by 4
	 * it removes a Main action from the set of the current turn calling the super act method.
	 * @param player the Player who performs the Action
	 * @return a new PlayerTurnUpdater to manage turn advancements.
	 * @throws NullPointerException if the player passed is null.
	 */
	@Override
	public PlayerTurnUpdater act(Player player){
		Preconditions.checkNotNull(player, "Player can't be null");
		super.act(player);
		Councillor fallenCouncillor = balcony.addCouncillor(councillor);
		councillors.remove(councillor);
		councillors.add(fallenCouncillor);
		player.getCoins().increment(4);
		return new PlayerTurnUpdater();
	}
	
	
	
}
