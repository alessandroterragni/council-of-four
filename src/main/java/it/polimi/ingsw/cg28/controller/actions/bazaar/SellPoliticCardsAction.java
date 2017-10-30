package it.polimi.ingsw.cg28.controller.actions.bazaar;

import java.awt.Color;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.bazaar.ProductOnSale;
import it.polimi.ingsw.cg28.controller.bazaar.SoldPoliticCard;
import it.polimi.ingsw.cg28.controller.bazaar.SoldStrategy;
import it.polimi.ingsw.cg28.controller.updaters.BazaarPlayerTurnUpdater;
import it.polimi.ingsw.cg28.controller.updaters.Updater;
import it.polimi.ingsw.cg28.model.BazaarModel;
import it.polimi.ingsw.cg28.model.Player;
import it.polimi.ingsw.cg28.model.PoliticCard;
import it.polimi.ingsw.cg28.tmodel.TProductCards;

/**
 * Class to manage a sell politic cards action.
 * @author Mario
 *
 */
public class SellPoliticCardsAction extends BazaarAction {

	private List<PoliticCard> cards;
	private int price;
	
	/**
	 * Constructor of the class.
	 * @param tiles List of politic cards the owner wants to sell.
	 * @param price price the owner want sell cards for.
	 * @param bazaarModel Model of Bazaar
	 * @throws NullPointerException if bazaarModel is null
	 * @throws NullPointerException if cards is null
	 * @throws IllegalArgumentException if price is lower than zero
	 */
	public SellPoliticCardsAction(List<PoliticCard> cards, int price, BazaarModel bazaarModel) {
		
		super(bazaarModel);
		
		Preconditions.checkNotNull(cards);
		Preconditions.checkArgument(price >= 0);
		
		this.cards = cards;
		this.price = price;
		
	}
	
	/**
	 * Method to carry out the action. Adds a product and related tProduct on bazaar shelf.
	 * Matches the product with the related soldPoliticCard sold strategy(Strategy pattern).
	 */
	@Override
	public Updater act(Player player) {
		
		SoldStrategy soldStrategy = new SoldPoliticCard();
		
		ProductOnSale<PoliticCard> product = 
				new ProductOnSale<>(cards, price, player, soldStrategy);

		Color[] tCards = new Color[cards.size()];
		
		for(int i=0; i< cards.size(); i++)
			tCards[i] = cards.get(i).getHouseColor();
		
		TProductCards tProductCards = new TProductCards(price, player.getID().getName(), tCards);
		
		bazaarModel.addItem(product, tProductCards);
		
		Iterator<PoliticCard> iterator = cards.iterator();
		while (iterator.hasNext())
			player.getPoliticCardsHand().remove(iterator.next());
		
		if(player.getPoliticCardsHand().isEmpty())
			bazaarModel.getCurrentTurn().update(this);
		
		return new BazaarPlayerTurnUpdater(bazaarModel, player.getID());
		
	}

}
