package it.polimi.ingsw.cg28.tmodel;

import java.awt.Color;
import java.io.Serializable;

/**
 * Represents a Balcony variant which can be transfered from the server to the client without
 * making the real model's rep accessible from a remote client
 * @author Marco
 *
 */
public class TBalcony implements Serializable {

	private static final long serialVersionUID = 7266767758606319162L;
	private final Color[] council;
	
	/**
	 * The constructor of the class, creates a new TBalcony.
	 * @param color - An array of colors, representing the house colors of the corresponding Balcony
	 * in the model
	 * @throws NullPointerException if the input array is null
	 */
	public TBalcony(Color[] color) {
		if(color == null){
			throw new NullPointerException("Can't create a TBalcony with a null parameter.");
		}
		this.council = color;
	}

	/**
	 * Fetches the array of colors that symbolizes the Balcony's council.
	 * @return the Balcony's council as an array of colors
	 */
	public Color[] getCouncil() {
		return council;
	}
	
	/**
	 * Fetches a councillor's color based on the councillor's position in the council.
	 * @param index - The integer offset of the councillor's position
	 * @return The target councillor's color
	 * @throws IllegalArgumentException if the input index is negative or bigger than the
	 * array's size
	 */
	public Color getCouncillorColor(int index) {
		if(index < 0 || index >= this.council.length){
			throw new IllegalArgumentException("Input index is out of legal bounds.");
		}
		return council[index];
	}
}
