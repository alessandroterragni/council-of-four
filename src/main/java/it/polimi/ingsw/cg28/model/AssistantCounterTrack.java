package it.polimi.ingsw.cg28.model;

import it.polimi.ingsw.cg28.controller.bazaar.Salable;

/**
 * Represents the counter for the assistants hired by each player.
 * @author Marco
 *
 */
public class AssistantCounterTrack extends Track implements Salable {
	
	/**
	 * The constructor for the class.
	 * @param value - The initial value of the counter for the assistants owned by a player
	 * @throws IllegalArgumentException if the specified initial value is negative
	 */
	public AssistantCounterTrack(int value){
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
			throw new IllegalArgumentException("Final track value cannot be negative.");
		}
		this.value -= delta;
	}

}
