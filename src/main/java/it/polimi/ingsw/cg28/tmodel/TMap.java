package it.polimi.ingsw.cg28.tmodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

/**
 * Represents a Map variant which can be transfered from the server to the client without
 * making the real model's rep accessible from a remote client
 * @author Marco
 *
 */
public class TMap implements Serializable{

	private static final long serialVersionUID = -5383834832863274946L;
	private final SimpleGraph<TTown,DefaultEdge> townNetwork;
	private final TBalcony kingCouncil;
	private final List<TTown> townList;

	/**
	 * The constructor of the class, creates a new TMap.
	 * @param towns - A graph of TTown objects (connected by DefaultEdge objects) representing this TMap's
	 * town network
	 * @param council - The TBalcony representing the King's Council
	 * @throws NullPointerException if any parameter is null
	 */
	public TMap(SimpleGraph<TTown,DefaultEdge> towns, TBalcony council) {
		
		if(towns == null || council == null){
			throw new NullPointerException("Can't create a TMap with any null parameter.");
		}
		
		this.townNetwork = towns;
		for(TTown t0 : towns.vertexSet()){
			for(TTown t1 : towns.vertexSet()){
				if(towns.containsEdge(t0, t1)){
					this.townNetwork.addEdge(t0, t1);
				}
			}
		}
		
		this.kingCouncil = council;
		
		Set<TTown> set = new HashSet<>();
		List<TTown> result = new ArrayList<>();
		set.addAll(this.townNetwork.vertexSet());
		for(TTown t : set){
			result.add(t);
		}
		
		this.townList = result;
	}

	/**
	 * Fetches the town network graph.
	 * @return a Graph object, containing this map's TTown objects
	 */
	public Graph<TTown, DefaultEdge> getTownNetwork() {
		return townNetwork;
	}

	/**
	 * Fetches the TBalcony object for the King's Council.
	 * @return a TBalcony object representing the King's Council for this TMap
	 */
	public TBalcony getKingCouncil() {
		return kingCouncil;
	}
	
	/**
	 * Returns a list of TTown objects from the map's graph.
	 * @return a list of all the TTown objects in this TMap
	 */
	public List<TTown> getTownList(){
		return townList;
	}
}
