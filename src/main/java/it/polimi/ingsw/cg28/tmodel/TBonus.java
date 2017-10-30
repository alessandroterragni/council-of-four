package it.polimi.ingsw.cg28.tmodel;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Represents a Bonus variant which can be transfered from the server to the client without
 * making the real model's rep accessible from a remote client
 * @author Marco
 *
 */
public class TBonus implements Serializable {

	private static final long serialVersionUID = 1455775633082179096L;
	private final String[] bonusCodes;
	
	/**
	 * The constructor of the class, creates a new TBonus.
	 * @param bonusCode - An array containing the string codes for the bonuses included in this TBonus
	 * @throws NullPointerException if the input codes array is null
	 */
	public TBonus(String[] bonusCode) {
		if(bonusCode == null){
			throw new NullPointerException("Can't create a TBonus with a null parameter.");
		}
		this.bonusCodes = bonusCode;
	}

	/**
	 * Fetches the array of bonus codes for this TBonus.
	 * @return an array of strings corresponding to the bonus codes for this TBonus
	 */
	public String[] getBonusCode() {
		return bonusCodes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(bonusCodes);
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
		TBonus other = (TBonus) obj;
		if (!Arrays.equals(bonusCodes, other.bonusCodes))
			return false;
		return true;
	}
}
