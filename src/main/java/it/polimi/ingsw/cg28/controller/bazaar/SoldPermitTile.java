/**
 * 
 */
package it.polimi.ingsw.cg28.controller.bazaar;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.model.BusinessPermitTile;
import it.polimi.ingsw.cg28.model.Player;

/**
 * Implements interface SoldStrategy to define the strategy to sell ProductOnSale[BusinessPermitTiles]
 * @author Mario
 *
 */
public class SoldPermitTile implements SoldStrategy {
	
	/**
	 * Method to complete purchase on ProductOnSale[BusinessPermitTile] between buyer and seller.
	 * Transfers products and coins.
	 * @throws NullPointerException if productOnSale, buyer or seller are null
	 */
	@Override
	public <T extends Salable> void sold(ProductOnSale<T> productOnSale, Player buyer, Player seller) {
		
		Preconditions.checkNotNull(productOnSale);
		Preconditions.checkNotNull(buyer);
		Preconditions.checkNotNull(seller);
		
		List<BusinessPermitTile> tiles = new ArrayList<>();
		
		for(int i=0; i<productOnSale.getProducts().size(); i++){
			tiles.add((BusinessPermitTile)productOnSale.getProduct(i));
		}
		
		seller.getCoins().increment(productOnSale.getPrice());
		buyer.getCoins().decrement(productOnSale.getPrice());
		
		for(BusinessPermitTile tile : tiles)
			buyer.getPossessedTiles().add(tile);

	}

}
