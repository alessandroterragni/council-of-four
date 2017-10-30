package it.polimi.ingsw.cg28.model;

import java.awt.Color;

/**
 * Represents a councillor of a given color.
 * @author Marco
 *
 */
public class Councillor {

	private final Color houseColor;
	
	/**
	 * The constructor of the class.
	 * @param color - The house color assigned to the nobleman, to be compared to the ones
	 * on politic cards
	 * @throws NullPointerException if the color parameter is null
	 */
	public Councillor(Color color) {
		if(color == null){
			throw new NullPointerException("The councillor color can't be null.");
		}
		this.houseColor = color;
	}

	/**
	 * Fetches the councillor's color.
	 * @return The councillor's color.
	 */
	public Color getColor() {
		return houseColor;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((houseColor == null) ? 0 : houseColor.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Councillor other = (Councillor) obj;
		if (!houseColor.equals(other.houseColor))
			return false;
		return true;
	}
	
	
}
