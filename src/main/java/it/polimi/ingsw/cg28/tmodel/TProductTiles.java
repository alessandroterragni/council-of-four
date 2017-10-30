package it.polimi.ingsw.cg28.tmodel;

import java.io.Serializable;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.view.cli.CliPrinter;

/**
 * Throwable object representing a bundle of business permit tiles on sale in the bazaar.
 * @author Mario
 *
 */
public class TProductTiles extends TProduct implements Serializable{

	private static final long serialVersionUID = 2943341937568110715L;
	private final TBusinessPermitTile[] tiles;
	
	/**
	 * Constructor of class
	 * @param price price of the product
	 * @param playerOwner string that identifies the owner of permit tiles
	 * @param cards array of TBusinessPermit tile representing tiles on sale
	 * @throws NullPointerException if playerOwner is null
	 * @throws NullPointerException if tiles is null
	 * @throws IllegalArgumentException if price is lower than zero
	 * @throws IllegalArgumentException if tiles array length is zero
	 */
	public TProductTiles(int price, String playerOwner, TBusinessPermitTile[] tiles) {
		super(price, playerOwner);
		
		Preconditions.checkNotNull(tiles);
		Preconditions.checkArgument(tiles.length > 0);
		
		this.tiles = tiles;
	}
	
	/**
	 * Returns the array of TBusinessPermit tile representing tiles on sale
	 * @return array of TBusinessPermit tile representing tiles on sale
	 */
	public TBusinessPermitTile[] getTiles() {
		return tiles;
	}
	
	/**
	 * Method to return the string representation of the TProductTiles
	 */
	@Override
	public String print(CliPrinter printer) {
		return printer.printTProductTiles(this);
	}
	
	

}
