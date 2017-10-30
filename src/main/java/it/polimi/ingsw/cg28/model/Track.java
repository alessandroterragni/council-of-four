package it.polimi.ingsw.cg28.model;

/**
 * Track is extended by every type of track used to compute scores and
 * player status details.
 * @author Marco
 *
 */
public abstract class Track {
	
	protected int value;
	
	/**
	 * The constructor of the class.
	 * @param value - The initial value for the counter
	 * @throws IllegalArgumentException if value is negative
	 */
	public Track(int value) {
		if(value < 0){
			throw new IllegalArgumentException("The track's value can't be negative.");
		}
		this.value = value;
	}
	
	/**
	 * Fetches the current value of this track.
	 * @return the current value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Sets the track's value to the specified value.
	 * @param value - The value to be set
	 */
	public void setValue(int value) {
		this.value = value;
	}
	
	/**
	 * Increases the value by the specified delta.
	 * @param delta - The desired value variation
	 */
	public abstract void increment(int delta);
	
	/**
	 * Decreases the value by the specified delta.
	 * @param delta - The desired value decrement
	 */
	public abstract void decrement(int delta);
}
