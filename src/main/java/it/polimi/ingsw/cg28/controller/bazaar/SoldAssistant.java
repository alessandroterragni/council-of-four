/**
 * 
 */
package it.polimi.ingsw.cg28.controller.bazaar;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.model.AssistantCounterTrack;
import it.polimi.ingsw.cg28.model.Player;

/**
 * Implements interface SoldStrategy to define the strategy to sell ProductOnSale[AssistantCounterTrack]
 * @author Mario
 *
 */
public class SoldAssistant implements SoldStrategy {
	
	/**
	 * Method to complete purchase on ProductOnSale[AssistantCounterTrack] between buyer and seller. 
	 * Transfers products and coins.
	 * @throws NullPointerException if productOnSale, buyer or seller are null
	 */
	@Override
	public <T extends Salable> void sold(ProductOnSale<T> productOnSale, Player buyer, Player seller) {
		
			Preconditions.checkNotNull(productOnSale);
			Preconditions.checkNotNull(buyer);
			Preconditions.checkNotNull(seller);
	
			int numbAssistants = ((AssistantCounterTrack)productOnSale.getProduct(0)).getValue();
			
	
			seller.getCoins().increment(productOnSale.getPrice());
			buyer.getCoins().decrement(productOnSale.getPrice());
			
			buyer.getAssistants().increment(numbAssistants);
			
	}
			

}
