package it.polimi.ingsw.cg28.tmodel;

import java.io.Serializable;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.view.cli.CliPrinter;

/**
 * Abstract Throwable object representing a product on sale in the bazaar.
 * @author Mario
 *
 */
public abstract class TProduct implements Serializable{

	private static final long serialVersionUID = 4605639342924414696L;
	
	private final int price;
	private final String playerOwner;

	/**
	 * Abstract constructor of the class
	 * @param price price of the product
	 * @param playerOwner string that identifies the owner of the product
	 * @throws NullPointerException if playerOwner is null
	 * @throws IllegalArgumentException if price is lower than zero
	 */
	public TProduct(int price, String playerOwner){
		
		Preconditions.checkArgument(price >= 0, "Price couldn't be negative");
		Preconditions.checkNotNull(playerOwner, "Owner couldn't be null");
		
		this.price = price;
		this.playerOwner = playerOwner;
	}
	
	/**
	 * Return the price of the product
	 * @return price of the product
	 */
	public int getPrice() {
		return price;
	}
	
	/**
	 * Return the string that identifies the owner
	 * @return string that identifies the owner
	 */
	public String getPlayerOwner() {
		return playerOwner;
	}
	
	/**
	 * Method to return the string representation of the TProduct
	 * @param printer Printer to print the product
	 * @return string representation of the TProduct
	 */
	public abstract String print(CliPrinter printer);

	

}
