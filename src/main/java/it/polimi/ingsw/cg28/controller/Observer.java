package it.polimi.ingsw.cg28.controller;

/**
 * Interface Observer, to implement observable-observer and MVC patterns.
 * Re-defines the interface (java.util.Observer) to allow use of generic types 
 * @author Mario
 * 
 */
@FunctionalInterface
public interface Observer<C> {
	
	/**
	 * Method to define observer reaction to change notified by observed objects.
	 * @param change
	 */
	public void update(C change);

}
