package it.polimi.ingsw.cg28.model;

import java.util.HashMap;
import it.polimi.ingsw.cg28.controller.bonus.Bonus;

/**
 * Object containing the Nobility Track, matches rank value to associated bonus.
 * @author Mario
 *
 */
public class NobilityTrackBonus {
	
	private HashMap<Integer,Bonus> bonus;
	private int maxValue;
	
	/**
	 * Constructor of the class
	 */
	public NobilityTrackBonus(){
		bonus = new HashMap<>();
	}
	
	/**
	 * Allows to add a relation rank-value to the track
	 * @param key rank value
	 * @param b bonus associated to key rank value
	 */
	public void addValue(int key,Bonus b){
		
		bonus.put(key, b);
		
		if (key > maxValue)
			maxValue = key;
	}
	
	/**
	 * Return the bonus associated to rank (position) given by parameter
	 * @param position rank
	 * @return Bonus related to position (null if there isn't a bonus related to the given position)
	 */
	public Bonus getTrackBonus(int position){
		return bonus.get(position);
	}
	
	/**
	 * Return the max rank value that has a bonus associated to
	 * @return max rank value that has a bonus associated to
	 */
	public int getMaxValue() {
		return maxValue;
	}

}
