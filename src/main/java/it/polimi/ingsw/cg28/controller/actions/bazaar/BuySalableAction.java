package it.polimi.ingsw.cg28.controller.actions.bazaar;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.bazaar.ProductOnSale;
import it.polimi.ingsw.cg28.controller.bazaar.Salable;
import it.polimi.ingsw.cg28.controller.bazaar.SoldStrategy;
import it.polimi.ingsw.cg28.controller.updaters.BazaarPlayerTurnUpdater;
import it.polimi.ingsw.cg28.controller.updaters.Updater;
import it.polimi.ingsw.cg28.model.BazaarModel;
import it.polimi.ingsw.cg28.model.Player;
import it.polimi.ingsw.cg28.tmodel.TProduct;
import it.polimi.ingsw.cg28.view.cli.CliPrinter;

/**
 * Class to manage a buy salable action.
 * @author Mario
 *
 */
public class BuySalableAction extends BazaarAction {
	
	private ProductOnSale<? extends Salable> productOnSale;
	private TProduct tProduct;
	
	/**
	 * Constructor of the class.
	 * @param productOnSale The product to sell
	 * @param tProduct TProduct related to product
	 * @param bazaarModel Model of Bazaar
	 * @throws NullPointerException if bazaarModel is null
	 * @throws NullPointerException if productOnSale is null
	 * @throws NullPointerException if tProduct is null
	 * @Requires Products given must have the right sold Strategy associated
	 */
	public BuySalableAction(ProductOnSale<? extends Salable> productOnSale, TProduct tProduct, BazaarModel bazaarModel) {
		
		super(bazaarModel);
		
		Preconditions.checkNotNull(productOnSale);
		Preconditions.checkNotNull(tProduct);
		
		this.productOnSale = productOnSale;
		this.tProduct = tProduct;
		
	}
	
	/**
	 * Method to carry out the action. Takes as input the player who wants to buy the product.
	 * Uses the soldStrategy (strategy Pattern) related to productOnSale to transfer the product
	 * to the buyer. Adds the transaction to transactions list in bazaarModel.
	 * Removes product and tProduct from bazaar shelf.
	 */
	@Override
	public Updater act(Player player) {
		
		SoldStrategy soldStrategy = productOnSale.getSoldStrategy();
		Player seller = productOnSale.getOwner();
		
		CliPrinter printer = new CliPrinter();
		StringBuilder transaction = new StringBuilder();
		transaction.append("Player " + player.getID().getName());
		transaction.append(" has bought " + tProduct.print(printer));
		transaction.append(" sold by player " + productOnSale.getOwner().getID().getName() + "\n");
		bazaarModel.addTransaction(transaction.toString());
		
		soldStrategy.sold(productOnSale, player, seller);
		
		bazaarModel.removeItem(productOnSale, tProduct);
		
		return new BazaarPlayerTurnUpdater(bazaarModel, player.getID());
		
	}

}
