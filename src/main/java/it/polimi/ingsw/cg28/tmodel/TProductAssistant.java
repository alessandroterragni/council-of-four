package it.polimi.ingsw.cg28.tmodel;

import java.io.Serializable;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.view.cli.CliPrinter;

/**
 * Throwable object representing a bundle of assistants on sale in the bazaar.
 * @author Mario
 *
 */
public class TProductAssistant extends TProduct implements Serializable {

	private static final long serialVersionUID = -2031933739049320900L;
	
	private final int numbAssistant;
	
	/**
	 * Constructor of class
	 * @param price price of the product
	 * @param playerOwner string that identifies the owner of assistants
	 * @param numbAssistant number of assistants on sale
	 * @throws NullPointerException if playerOwner is null
	 * @throws NullPointerException if number of assistants is lower than zero
	 * @throws IllegalArgumentException if price is lower than zero
	 */
	public TProductAssistant(int price, String playerOwner, int numbAssistant) {
		
		super(price, playerOwner);
		
		Preconditions.checkArgument(numbAssistant > 0, "Number of assistants couldn't be negative!");
		this.numbAssistant = numbAssistant;
		
	}
	
	/**
	 * Return the number of assistants of the product
	 * @return int number of assistants of the product
	 */
	public int getNumbAssistant() {
		return numbAssistant;
	}

	/**
	 * Method to return the string representation of the TProductAssistant
	 */
	@Override
	public String print(CliPrinter printer) {
		return printer.printTProductAssistant(this);
	}


}
