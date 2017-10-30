package it.polimi.ingsw.cg28.model;

/**
 * Represents the victory points counter for each player.
 * @author Marco
 *
 */
public class VictoryTrack extends Track {
	
	/**
	 * The constructor of the class.
	 * @param value - The initial value of the counter for the victory points gained by the
	 * player
	 * @throws IllegalArgumentException if the specified initial value is negative
	 */
	public VictoryTrack(int value){
		super(value);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void increment(int delta){
		this.value += delta;
	}

	/**
	 * {@inheritDoc}
	 * @throws IllegalArgumentException if this method's call would result in the track having a negative value
	 */
	@Override
	public void decrement(int delta){
		if(this.value - delta < 0){
			throw new IllegalArgumentException("Final track value can't be negative.");
		}
		this.value -= delta;
	}
}
