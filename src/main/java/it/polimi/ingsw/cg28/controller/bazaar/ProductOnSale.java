/**
 * 
 */
package it.polimi.ingsw.cg28.controller.bazaar;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.model.Player;

/**
 * Class to put one Salable object on sale on the market.
 * @author Mario
 *
 */
public class ProductOnSale<T extends Salable> {
	
	private List<T> products;
	private SoldStrategy soldStrategy;
	private int price;
	private Player owner;
	
	/**
	 * Constructor of class.
	 * @param products List of sub-products of type T to put on sale together
	 * @param price The price you want to sell the product for
	 * @param owner The player owner of the product
	 * @param soldStrategy The strategy to sold the item (strategy pattern)
	 * @throws NullPointerException If products, owner or soldStrategy are null
	 * @throws IllegalArgumentException if price is lower than zero
	 */
	public ProductOnSale(List<T> products, int price,  Player owner, SoldStrategy soldStrategy){
		
		Preconditions.checkNotNull(products);
		Preconditions.checkNotNull(owner);
		Preconditions.checkNotNull(soldStrategy);
		
		Preconditions.checkArgument(price >= 0);
		
		this.soldStrategy = soldStrategy;
		this.products = new ArrayList<>();
		this.products.addAll(products);
		this.price = price;
		this.owner = owner;
	}
	
	/**
	 * Returns the i-th sub-product of list of products of type T on sale by the object.
	 * @param index of the product
	 * @return i-th sub-product
	 */
	public T getProduct(int index){
		return products.get(index);
	}
	
	/**
	 * Returns the list of sub-products of type T on sale by the object.
	 * @return
	 */
	public List<T> getProducts() {
		return products;
	}
	
	/**
	 * Returns the sold-strategy to sold items.
	 * @return
	 */
	public SoldStrategy getSoldStrategy() {
		return soldStrategy;
	}
	
	/**
	 * Returns the price of the object.
	 * @return int price
	 */
	public int getPrice() {
		return price;
	}
	
	/**
	 * Returns the object of class Player representing the owner of products on sale.
	 * @return the Player owner
	 */
	public Player getOwner() {
		return owner;
	}
}
