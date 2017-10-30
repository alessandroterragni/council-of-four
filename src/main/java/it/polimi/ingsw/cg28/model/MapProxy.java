package it.polimi.ingsw.cg28.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.graph.*;

import com.google.common.base.Preconditions;

/**
 * Class to save Town references in order to modify it without crashing Graph hashcode contract.
 * Remove town with getTownOut() method, modify it, reinsert town in townNetwork with putTownIn().
 * @author Mario
 *
 */
public class MapProxy {
	
	private List<Town> townStart;
	private List<Town> townEnd;
	private Town town;
	private Graph<Town,DefaultEdge> townNetwork;
	
	/**
	 * Constructor of the class
	 * @param townNetwork graph containing town
	 */
	public MapProxy(Graph<Town, DefaultEdge> townNetwork) {
		this.townNetwork = townNetwork;
		townStart = new ArrayList<>();
		townEnd = new ArrayList<>();
	}

	
	/**
	 * Memorize references of the town and related edges in the graph
	 * @param town town to remove from the graph
	 */
	public void getTownOut(Town town){
		
		Preconditions.checkArgument(this.town == null);
		
		for(Town otherTown : townNetwork.vertexSet())
				if (!town.equals(otherTown) 
					&& townNetwork.containsEdge(town, otherTown)){
						townStart.add(town);
						townEnd.add(otherTown);
					}
		
		this.town = town;
		
		townNetwork.removeVertex(town);
		
	}
	
	/**
	 * Reinsert town in townNetwork and clear "cache" of town (reference and edges) previously saved.
	 * @throws NullPointerException if there isn't a city "saved" in MapProxy object.
	 */
	public void putTownIn(){
		
		Preconditions.checkNotNull(this.town);
		
		townNetwork.addVertex(town);
		
		for(int i=0; i< townStart.size(); i++)
			townNetwork.addEdge(townStart.get(i), townEnd.get(i));
		
		townStart.clear();
		townEnd.clear();
		this.town=null;
		
	}

}
