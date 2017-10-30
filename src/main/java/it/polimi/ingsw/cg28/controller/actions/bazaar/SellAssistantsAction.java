package it.polimi.ingsw.cg28.controller.actions.bazaar;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.bazaar.ProductOnSale;
import it.polimi.ingsw.cg28.controller.bazaar.SoldAssistant;
import it.polimi.ingsw.cg28.controller.bazaar.SoldStrategy;
import it.polimi.ingsw.cg28.controller.updaters.BazaarPlayerTurnUpdater;
import it.polimi.ingsw.cg28.controller.updaters.Updater;
import it.polimi.ingsw.cg28.model.AssistantCounterTrack;
import it.polimi.ingsw.cg28.model.BazaarModel;
import it.polimi.ingsw.cg28.model.Player;
import it.polimi.ingsw.cg28.tmodel.TProductAssistant;

/**
 * Class to manage a sell assistants action.
 * @author Mario
 *
 */
public class SellAssistantsAction extends BazaarAction {
	
	private int numbAssistant;
	private int price;

	/**
	 * Constructor of the class
	 * @param numbAssistant number of assistants the owner wants to sell
	 * @param price price the owner want sell assistants for
	 * @param bazaarModel Model of Bazaar
	 * @throws NullPointerException if bazaarModel is null
	 * @throws IllegalArgumentException if numbAssistant is lower or equal to zero
	 * @throws IllegalArgumentException if price is lower than zero
	 */
	public SellAssistantsAction(int numbAssistant, int price, BazaarModel bazaarModel) {
		
		super(bazaarModel);
		
		Preconditions.checkArgument(numbAssistant > 0);
		Preconditions.checkArgument(price >= 0);
		
		this.numbAssistant = numbAssistant;
		this.price = price;
		
	}
	
	/**
	 * Method to carry out the action. Adds a product and related tProduct on bazaar shelf.
	 * Matches the product with the related soldAssistant sold strategy(Strategy pattern).
	 */
	@Override
	public Updater act(Player player) {
		
		List<AssistantCounterTrack> assistantsList = new ArrayList<>();
		AssistantCounterTrack assistants = new AssistantCounterTrack(numbAssistant);
		TProductAssistant tProductAssistant = new TProductAssistant(price, player.getID().getName(), numbAssistant);
		
		assistantsList.add(assistants);
		SoldStrategy soldStrategy = new SoldAssistant();
		ProductOnSale<AssistantCounterTrack> product = 
				new ProductOnSale<>(assistantsList, price, player, soldStrategy);
		
		bazaarModel.addItem(product, tProductAssistant);
		
		player.getAssistants().decrement(numbAssistant);
		
		if(player.getAssistants().getValue() < 1)
			bazaarModel.getCurrentTurn().update(this);
		
		return new BazaarPlayerTurnUpdater(bazaarModel, player.getID());
		
	}

}