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
 * It's a Quick Action that allows the player to send an assistant to corrupt a councillor.
 * @author Alessandro
 *
 */
public class SendAssistantAction extends QuickAction {
	
	private Balcony balcony;
	private Councillor councillor;
	private List<Councillor> councillors;
	
	/**
	 * It's the constructor of the class
	 * @param balcony the balcony in which I want to put the corrupted councillor 
	 * @param councillor the councillor I want to elect
	 * @param councillors the nobles pool where you put the replaced councillor
	 * @throws NullPointerException if the Balcony or councillor or councillors passed are null
	 */
	public SendAssistantAction(Balcony balcony,Councillor councillor, List<Councillor> councillors){
		Preconditions.checkNotNull(balcony, "Balcony can't be null");
		Preconditions.checkNotNull(councillor, "Councillor can't be null");
		Preconditions.checkNotNull(councillors, "Councillors can't be null");
		this.balcony=balcony;
		this.councillor=councillor;
		this.councillors=councillors;
	}
	
	/**
	 * It decrements the Player's number of Assistants by 1,
	 * it adds the chosen councillor on the balcony,
	 * it add the fallen councillor to the nobles pool councillors passed as a parameter,
	 * it removes a quick action from the set of the current turn calling the super act method.
	 * @param player the Player who performs the Action
	 * @return a new PlayerTurnUpdater to manage turn advancements
	 * @throws NullPointerException if the player passed is null
	 */
	@Override
	public PlayerTurnUpdater act(Player player){
		
		Preconditions.checkNotNull(player, "Player can't be null");
		player.getAssistants().decrement(1);
		Councillor fallenCouncillor = balcony.addCouncillor(councillor);
		councillors.remove(councillor);
		councillors.add(fallenCouncillor);
		super.act(player);
		return new PlayerTurnUpdater();
		
	}

}
