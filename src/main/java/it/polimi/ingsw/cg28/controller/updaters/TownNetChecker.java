package it.polimi.ingsw.cg28.controller.updaters;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.*;
import org.jgrapht.graph.DefaultEdge;

import it.polimi.ingsw.cg28.model.Town;
import it.polimi.ingsw.cg28.model.GameMap;
import it.polimi.ingsw.cg28.model.Player;

/**
 * The class used to search the game's map for sets of cities that are connected
 * and have an emporium owned by a specified player. It is purely functional to
 * return to the bonus controlling classes the set of Town objects whose bonuses
 * have to be applied to said player.
 * @author Marco
 *
 */
public class TownNetChecker {

	/**
	 * checkMap returns a set of towns that are linked and all contains an emporium
	 * owned by the specified player.
	 * @param map - The map to check for the linked sets
	 * @param start - The city to start from for looking for the linked set
	 * @param p - The player for whom to check the map
	 * @return A set containing linked towns that all have an emporium owned by p
	 */
	public Set<Town> checkMap(GameMap map, Town start, Player p){
		if(map == null || start == null || p == null){
			throw new NullPointerException("None of the parameters can be null for this check.");
		}
		Graph<Town,DefaultEdge> mapGraph = map.getTownNetwork();
		HashSet<Town> prec = new HashSet<>();
		HashSet<Town> result = new HashSet<>();
		result.add(start);
		while(!result.equals(prec)){
			for(Town t : result){
				prec.add(t);
			}
			List<Town> neighs;
			
			neighs = map.townSetNeighbors(mapGraph, result);
			
			for(Town n : neighs){
				if(n.hasEmporium(p.getID())){
					result.add(n);
				}
			}
		}
		return result;
	}
}
