package it.polimi.ingsw.cg28.controller.updaters;

import java.util.HashSet;
import java.util.Set;

import it.polimi.ingsw.cg28.model.GameMap;
import it.polimi.ingsw.cg28.model.Player;
import it.polimi.ingsw.cg28.model.Region;
import it.polimi.ingsw.cg28.model.Town;

/**
 * The class containing the methods to check the map in order to assign bonuses related
 * to building emporiums in all the towns of the same alloy type of in all the towns in a
 * region.
 * @author Marco
 *
 */
public class BonusTileChecker {

	/**
	 * sameTownTypeCheck finds out whether a player has built emporiums in every city of a given town's type.
	 * @param map - The town network to check
	 * @param start - The town that triggers the checking process, its type is the one the method is going to
	 * check on other towns in the network
	 * @param p - The player who has built an emporium in town "start", the method will check if all other towns
	 * that share type with "start" have an emporium belonging to p
	 * @return true if all towns of the same type of town "start" have an emporium belonging to p, false otherwise.
	 */
	public boolean sameTownTypeCheck(GameMap map, Town start, Player p){
		if(map == null || start == null || p == null){
			throw new NullPointerException("None of the parameters can be null for this check.");
		}
		Set<Town> sameType = new HashSet<>();
		String refType = start.getAlloy();
		
		if(!start.hasEmporium(p.getID())){
			return false;
		}
		
		for(Town t : map.getTownNetwork().vertexSet()){
			if(t.getAlloy().equals(refType)){
				sameType.add(t);
			}
		}
		
		for(Town stt : sameType){
			if(!stt.hasEmporium(p.getID())){
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * sameRegion Check finds out whether a player has built emporiums in every city of a given town's region.
	 * @param map - The town network to check
	 * @param start - The town that triggers the checking process, its region is the one the method is going to
	 * check on other towns in the network
	 * @param p - The player who has built an emporium in town "start", the method will check if all other towns
	 * that share region with "start" have an emporium belonging to p
	 * @return true if all towns of the same region of town "start" have an emporium belonging to p, false otherwise.
	 */
	public boolean sameRegionCheck(GameMap map, Town start, Player p){
		if(map == null || start == null || p == null){
			throw new NullPointerException("None of the parameters can be null for this check.");
		}
		Set<Town> sameType = new HashSet<>();
		Region refReg = start.getRegion();
		
		if(!start.hasEmporium(p.getID())){
			return false;
		}
		
		for(Town t : map.getTownNetwork().vertexSet()){
			if(t.getRegion().equals(refReg)){
				sameType.add(t);
			}
		}
		
		for(Town stt : sameType){
			if(!stt.hasEmporium(p.getID())){
				return false;
			}
		}
		
		return true;
	}
}
