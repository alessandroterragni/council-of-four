/**
 * 
 */
package it.polimi.ingsw.cg28.model;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.controller.bazaar.BazaarPlayerTurn;
import it.polimi.ingsw.cg28.controller.bazaar.ProductOnSale;
import it.polimi.ingsw.cg28.controller.bazaar.Salable;
import it.polimi.ingsw.cg28.tmodel.TProduct;

/**
 * Bazaar Model. Manages product shelf, related tProduct shelf and transactions list.
 * @author Mario
 */
public class BazaarModel {
	
	private List<ProductOnSale<?>> shelf;
	private List<TProduct> tShelf;
	private BazaarPlayerTurn currentTurn;
	private StringBuilder transactions;

	/**
	 * Constructors of the class. Initialises a new shelf and related tShelf.
	 */
	public BazaarModel(){
		
		shelf = new ArrayList<>();
		tShelf = new ArrayList<>();
		
		transactions = new StringBuilder();
		transactions.append("\nTRANSACTIONS LIST\n");
	}
	
	/**
	 * Method to add items to the shelf
	 * @param productOnSale to add to the shelf
	 * @param tProduct related to product on sale to add to the tShelf
	 */
	public <T extends Salable> void addItem(ProductOnSale<T> productOnSale, TProduct tProduct){
		shelf.add(productOnSale);
		tShelf.add(tProduct);
	}
	
	/**
	 * Method to remove items from the shelf
	 * @param productOnSale to remove from the shelf
	 * @param tProduct related to product on sale to remove from the tShelf
	 */
	public <T extends Salable> void removeItem(ProductOnSale<T> productOnSale, TProduct tProduct){
		shelf.remove(productOnSale);
		tShelf.remove(tProduct);
	}
	
	/**
	 * Returns the shelf, removing product owned by player passed as parameter
	 * @param player PlayerID of the player who wants to see the self
	 * @return shelf List of ProductOnSale 
	 */
	public List<ProductOnSale<?>> getShelf(PlayerID player) {
		
		List<ProductOnSale<?>> products = new ArrayList<>();
		products.addAll(shelf);
		
		for (Iterator<ProductOnSale<?>> iterator = products.iterator(); iterator.hasNext();) {
			ProductOnSale<?> p = iterator.next();
		    if (p.getOwner().getID().equals(player)) {
		        iterator.remove();
		    }
		}
		
		return products;
		
	}
	
	/**
	 * Returns the tShelf, removing product owned by player passed as parameter.
	 * @param player PlayerID of the player who wants to see the self
	 * @return shelf List of tProduct 
	 */
	public List<TProduct> gettShelf(PlayerID player) {
		
		List<TProduct> products = new ArrayList<>();
		
		for(int i=0; i<tShelf.size();i++)
			if (!shelf.get(i).getOwner().getID().equals(player))
				products.add(tShelf.get(i));
		
		return products;
	}
	
	/**
	 * Returns the current turn.
	 * @return BazaarPlayerTurn curren turn
	 */
	public BazaarPlayerTurn getCurrentTurn() {
		return currentTurn;
	}

	/**
	 * Set current turn to turn passed by parameter.
	 * @param currentTurn
	 */
	public void setCurrentTurn(BazaarPlayerTurn currentTurn) {
		this.currentTurn = currentTurn;
	}
	
	/**
	 * Returns the shelf.
	 * @return shelf List of ProductOnSale 
	 */
	public List<ProductOnSale<?>> getShelf() {
		return shelf;
	}
	
	/**
	 * Returns the shelf.
	 * @return shelf List of tProduct 
	 */
	public List<TProduct> gettShelf() {
		return tShelf;
	}
	
	/**
	 * Adds a transaction to transactions list.
	 * @param transaction to add
	 */
	public void addTransaction(String transaction){
		transactions.append(transaction);
	}
	
	/**
	 * Returns transactions List.
	 * @return String list of transactions
	 */
	public String getTransactions(){
		return transactions.toString();
	}
	
	
	
}
