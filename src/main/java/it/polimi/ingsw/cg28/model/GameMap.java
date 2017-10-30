package it.polimi.ingsw.cg28.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.jgrapht.*;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polimi.ingsw.cg28.controller.PlayerID;

/**
 * The game's map, modeled with a JGraphT simple graph.
 * @author Marco
 *
 */
public class GameMap {

	private Graph<Town,DefaultEdge> townNetwork;
	private List<Town> townList;
	private Balcony kingCouncil;
	
	/**
	 * The constructor of the class.
	 * @param towns - The list of towns to be set as vertices of the map graph
	 * @param connections - The list of connections to be set in the graph (adjacency list)
	 * @param council - The balcony hosting the king's council
	 * @throws NullPointerException if one among towns, connections and balcony is null
	 */
	public GameMap(List<Town> towns, List<List<Town>> connections,
			Balcony council) {
		if(towns == null || connections == null || council == null){
			throw new NullPointerException("None of the parameters can be null.");
		}
		
		this.townNetwork = new SimpleGraph<>(DefaultEdge.class);
		for(Town t : towns){
			this.townNetwork.addVertex(t);
		}
		for(List<Town> adjList : connections){	//this double loop is a mess
			for(Town town : adjList){
				if(town == adjList.get(0));	//prevents from adding loops from the town to itself
				else this.townNetwork.addEdge(adjList.get(0), town);
			}
		}
		
		Set<Town> set = new HashSet<>();
		List<Town> result = new ArrayList<>();
		set = this.townNetwork.vertexSet();
		for(Town t : set){
			result.add(t);
		}
		this.townList = result;
		
		this.kingCouncil = council;
	}
	
	/**
	 * Fetches the game map's town list.
	 * @return A List of the towns in the map's network
	 */
	public List<Town> getTownList(){
		return townList;
	}

	/**
	 * Fetches the game map's town network, including towns and connections between them.
	 * @return The Graph containing the map's town network
	 */
	public Graph<Town, DefaultEdge> getTownNetwork() {
		return townNetwork;
	}

	/**
	 * Fetches the king's council's balcony.
	 * @return The Balcony representing the king's council
	 */
	public Balcony getKingCouncil() {
		return kingCouncil;
	}
	
	/**
	 * getTown fetches a town in the map's network based on an integer index.
	 * @param index - The integer index representing the position of the city
	 * @return The town occupying the indexed position
	 * @throws IllegalArgumentException if the specified index is negative
	 */
	public Town getTown(int index){
		if(index < 0){
			throw new IllegalArgumentException("Can't use a negative index to fetch a town from the network.");
		}
		return this.townList.get(index);
		
	}
	
	/**
	 * townSetNeighbors effectively extends the JGraphT method neighborListOf to
	 * return the set of neighbors of a given set of vertices. This implementation
	 * is specific for a Graph<Town,DefaultEdge> as the game's map.
	 * @param graph - The graph containing the set whose neighbors are requested
	 * @param townSet - The set of towns whose neighbors are requested
	 * @return A List containing the neighbors of the input set
	 * @throws NullPointerException if either the town graph or the set whose neighbors are requested
	 * are null
	 */
	public List<Town> townSetNeighbors(Graph<Town,DefaultEdge> graph,
			Set<Town> townSet){
		if(graph == null || townSet == null){
			throw new NullPointerException("Target graph and townSet can't be null.");
		}
		List<Town> result = new CopyOnWriteArrayList<>();
		for(Town t : townSet){
			result.add(t);
		}
		for(Town internal : result){
			List<Town> neighs = new ArrayList<>(Graphs.neighborListOf(graph, internal));
			for(Town n : neighs){
				if(!result.contains(n)){
					result.add(n);
				}
			}
		}
		for(Town orig : townSet){
			result.remove(orig);
		}
		return result;
	}
	
	/**
	 * Adds an emporium for the specified player in the specified town.
	 * Safe for graph hashcode issues with concurrent modification.
	 * If there is already an emporium built by the same player, the method doesn't
	 * do anything.
	 * @param p - The playerID of the player whose emporium will be built in the town
	 * @param town - The town in which the player wishes to build
	 * @throws NullPointerException if either the playerID or the town are null
	 */
	public void addEmporium(PlayerID p, Town town){
		
		if(p == null || town == null){
			throw new NullPointerException("Player and town can't be null.");
		}
		
		if(town.hasEmporium(p)) 
			return;
		else{
			MapProxy mapProxy = new MapProxy(this.getTownNetwork());
			mapProxy.getTownOut(town);
			town.addEmporium(p);
			mapProxy.putTownIn();
		}
	}
	
	/**
	 * Sets the king presence or less in the specified town.
	 * Safe for graph hashcode issues with concurrent modification.
	 * @param king - The boolean value to be set for the king's presence
	 * @param town - The target town
	 * @throws NullPointerException if the specified town is null
	 */
	public void setKingHere(boolean king, Town town) {
		if(town == null) {
			throw new NullPointerException("Town can't be null.");
		}
		MapProxy mapProxy = new MapProxy(this.getTownNetwork());
		mapProxy.getTownOut(town);
		town.setKingHere(king);
		mapProxy.putTownIn();
		
	}
	
	/**
	 * Fetches a town in the network based on its name.
	 * @param townName - The name of the town to be found
	 * @return The requested town, or <code>null</code> if there is no town with such name
	 * @throws NullPointerException if the specified name is null
	 */
	public Town getTown(String townName){
		if(townName == null){
			throw new NullPointerException("Town name can't be null.");
		}
		for(Town town : this.getTownList())
			if(town.getTownName().equals(townName))
				return town;
		
		return null;
	
	}

}
