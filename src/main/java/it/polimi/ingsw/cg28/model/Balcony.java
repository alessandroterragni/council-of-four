package it.polimi.ingsw.cg28.model;

import java.util.LinkedList;
import java.util.Queue;

import com.google.common.base.Preconditions;

/**
 * Represents a council balcony, containing four councillors.
 * @author Marco
 *
 */
public class Balcony {
	
	private Queue<Councillor> council;
	
	/**
	 * The constructor of the class.
	 * @param nobles - The array of nobles that occupy the initial seats in the council's
	 * balcony
	 * @throws NullPointerException if the nobles parameter is null
	 */
	public Balcony(Councillor[] nobles) {
		if(nobles == null){
			throw new NullPointerException("The councillor array can't be null.");
		}
		this.council = new LinkedList<>();
		
		for(Councillor c : nobles){
			this.council.add(c);
		}
	}

	/**
	 * Fetches this balcony's council.
	 * @return The queue of councillors representing this balcony's council
	 */
	public Queue<Councillor> getCouncil() {
		return council;
	}

	/**
	 * Sets the balcony's council to the desired one.
	 * @param council - The queue of councillors to be set
	 */
	public void setCouncil(Queue<Councillor> council) {
		this.council = council;
	}

	/**
	 * addCouncillor allows to remove the last councillor and to add a new
	 * one to the tail of the queue.
	 * @param councillor - The new councillor to put in the balcony
	 * @return The councillor removed to make place for the newly elected one
	 * @throws NullPointerException if the input councillor is null
	 */
	public Councillor addCouncillor(Councillor councillor){
		Preconditions.checkNotNull(councillor);
		Councillor fallenCouncillor = council.remove();
		council.add(councillor);
		return fallenCouncillor;
	}
	
	/**
	 * getCouncillor allows to retrieve a councillor based on its position offset
	 * in the balcony queue.
	 * @param position - The integer offset of the desired councillor
	 * @return The councillor in the specified position
	 * @throws IllegalArgumentException if the specified offset is negative
	 */
	public Councillor getCouncillor(int position){
		if(position < 0){
			throw new IllegalArgumentException("Can't retrieve councillor from a negative position");
		}
		LinkedList<Councillor> copy = new LinkedList<>(council);
		return copy.get(position);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((council == null) ? 0 : council.hashCode());
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
		Balcony other = (Balcony) obj;
		if (council == null) {
			if (other.council != null)
				return false;
		} else if (!council.equals(other.council))
			return false;
		return true;
	}
}
