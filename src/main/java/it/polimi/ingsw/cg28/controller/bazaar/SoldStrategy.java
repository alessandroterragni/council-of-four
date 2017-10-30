/**
 * 
 */
package it.polimi.ingsw.cg28.controller.bazaar;

import it.polimi.ingsw.cg28.model.Player;

/**
 * Interface (Strategy pattern) to determine the soldStrategy to sell one product
 * @author Mario
 *
 */
@FunctionalInterface
public interface SoldStrategy {
	
	/**
	 * Method to sell the product
	 * @param productOnSale Product on sale
	 * @param buyer player who wants to buy the product
	 * @param seller player who sells the product
	 */
	public <T extends Salable> void sold(ProductOnSale<T> productOnSale, Player buyer, Player seller);

}
