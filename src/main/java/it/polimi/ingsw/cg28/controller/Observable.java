package it.polimi.ingsw.cg28.controller;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract Class Observable, to implement observable-observer and MVC patterns.
 * Re-defines the class (java.util.Observable) to allow use of generic types 
 * @author Mario
 */
public abstract class Observable<C> {
	
	private List<Observer<C>> observers;
	
	/**
	 * Constructor of the class
	 * Inizialize observers list as a ArrayList
	 */
	public Observable(){
		observers=new ArrayList<>();
		
	}
	
	/**
	 * Allow an observer to register to the observable object which extends this class
	 * @param o Observer to register
	 */
	public void registerObserver(Observer<C> o){
		observers.add(o);
	}
	
	/**
	 * Allow an observer to unregister to the observable object which extends this class
	 * @param o Observer to unregister
	 */
	public void unregisterObserver(Observer<C> o){
		observers.remove(o);
	}
	
	/**
	 * Notify Observers about a specific change
	 * @param c Object to specify the change
	 */
	public void notifyObservers(C c){
		for(Observer<C> o: this.observers){
			o.update(c);
		}
	}
	
	/**
	 * Returns the list of observers registered to the observable object.
	 * @return list of observers registered to the observable object.
	 */
	public List<Observer<C>> getObservers(){
		
		return this.observers;
		
	}
	
	

}
