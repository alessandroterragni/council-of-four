package it.polimi.ingsw.cg28.model.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Preconditions;
import it.polimi.ingsw.cg28.controller.bonus.Bonus;
import it.polimi.ingsw.cg28.controller.bonus.BonusPack;
import it.polimi.ingsw.cg28.model.Balcony;
import it.polimi.ingsw.cg28.model.Councillor;
import it.polimi.ingsw.cg28.model.GameMap;
import it.polimi.ingsw.cg28.model.Region;
import it.polimi.ingsw.cg28.model.Town;
/**
 * Builds from Input the game Map(Towns and relative Bonus, Connections between city)
 * @author Mario
 *
 */
public class MapBuilder extends Builder {
	
	private static final String ERROR_FILE_CONFIG =  "Configuration File couldn't be null!";
	
	/**
	 * Static Method, builds Map from file .txt through Map class constructor
	 * File file .txt must contain three lines
	 * <br>First Line: path to bonusFile
	 * <br>Second Line: path to townsFile
	 * <br>Third Line: path to edgeFile
	 * 
	 * @param file to parse with related path
	 * @param regions Array of regions
	 * @param councilors for the King balcony
	 * @param councilorsForBalcony number of councilors for every balcony
	 * @return The Map for the model
	 * @throws IOException If there's an issue during file reading process
	 * @throws NullPointerException if param file is null 
	 */
	public GameMap build(File file, Region[] regions, List<Councillor> councilors, int councilorsForBalcony) throws IOException {
		
		Preconditions.checkNotNull(file, ERROR_FILE_CONFIG);
		Preconditions.checkNotNull(regions, "Regions array couldn't be null!");
		Preconditions.checkNotNull(councilors, "Nobles pool couldn't be null!");
		
		if (councilorsForBalcony <= 0){
			throw new IllegalArgumentException("CouncilorsForBalcony must be greater than 0");
		}
		
		File bonusFile;
		File townsFile;
		File edgeFile;
		
		String myLine = null;
		BufferedReader bufRead;
		
		List<Bonus> bonusList = new ArrayList<>();
		Map<String, Town> towns = new HashMap<>();
		List<List<Town>> edges = new ArrayList<>();
		
		try (FileReader input = new FileReader(file)){

			bufRead = new BufferedReader(input);
			
			myLine = bufRead.readLine();
			bonusFile = new File(myLine);
		
			bonusList = buildBonus(bonusFile);
			
			myLine = bufRead.readLine();
			townsFile = new File(myLine);
			
			towns = buildTowns(townsFile, regions, bonusList);
			
			myLine = bufRead.readLine();
			edgeFile = new File(myLine);
			
			edges = buildEdges(edgeFile, towns);
		
		} catch (IOException e) {
			throw new IOException(myLine, e);
		}
		
		List<Town> modelTowns= new ArrayList<>(towns.values());
		
		Councillor[] balcony = new Councillor[councilorsForBalcony];
		for (int i = 0; i < balcony.length; i++)
			balcony[i] = councilors.remove(0);
		
		return new GameMap(modelTowns,edges,new Balcony(balcony));
		
	}


	/**
	 * Builds List<List<>> to represent connections between towns.
	 * Syntax of EDGES file .txt lines must be
	 * <code><br>[firstTownName]>[connectedTownName]>[connectedTownName]<br></code>
	 * For each town (first Town of each nested List) list all linked town (all towns on the same line).
	 * @param edgeFile File to parse with related path
	 * @param towns List of game Towns
	 * @return ArrayList<ArrayList<Town>> represent connections between towns
	 * @throws IOException If there's an issue during file reading process
	 * @throws NullPointerException if param file is null 
	 */
	public List<List<Town>> buildEdges(File edgeFile, Map<String, Town> towns) throws IOException {
		
		Preconditions.checkNotNull(edgeFile, ERROR_FILE_CONFIG);
		
		String myLine = null;
		BufferedReader bufRead;
        List<List<Town>> edges = new ArrayList<>();
		
		try (FileReader input = new FileReader(edgeFile)){

			bufRead = new BufferedReader(input);
			myLine = null;
		
			while ( (myLine = bufRead.readLine()) != null){
				
				String[] townEdge = myLine.split(">");
			    List<Town> townEdges = new ArrayList<>();
			    
			    for (int i = 0; i < townEdge.length; i++)
			    	townEdges.add(towns.get(townEdge[i]));
			    
			    edges.add(townEdges);
			    
			}
			
		} catch (IOException e) {
			throw new IOException(myLine, e);
		}
		
		return edges;
	}
	
	/**
	 * Builds towns.
	 * The collection of Bonus is randomly associated to towns.
	 * Syntax of townsFile .txt file lines must be
	 * <code><br>[townName]:[initial letter] [true/false] [regionType] [Alloy]<br></code>
	 * Refers towns to Region of regions array and viceversa
	 * Boolean true or false to identify king's town
	 * @param townsFile File to parse with related path
	 * @param regions Regions to refers
	 * @param bonusList Bonus to assign to towns
	 * @return HashMap<String, Town>, towns of game map
	 * @throws IOException If there's an issue during file reading process
	 * @throws NullPointerException if param file is null 
	 */
	public Map<String, Town> buildTowns(File townsFile, Region[] regions, List<Bonus> bonusList) throws IOException {
		
		Preconditions.checkNotNull(townsFile, ERROR_FILE_CONFIG);
		
		String myLine = null;
		BufferedReader bufRead;
		Map<String, Town> towns = new HashMap<>();
		
		try (FileReader input = new FileReader(townsFile)){

			bufRead = new BufferedReader(input);
			myLine = null;
			
			while ( (myLine = bufRead.readLine()) != null){
				
				String[] town = myLine.split(":");
				String[] townInfo = town[1].split(" ");
				
			    if("true".equals(townInfo[1]))
			    	towns.put(town[0],new Town(town[0], townInfo[3],true, null, parserRegion(regions, townInfo[2])));
			    else towns.put(town[0],new Town(town[0], townInfo[3],false, bonusList.remove(0), parserRegion(regions, townInfo[2])));
			    
			    parserRegion(regions, townInfo[2]).addTown(towns.get(town[0]));
			} 
			
		} catch (IOException e) {
			
			throw new IOException(myLine, e);
			
		}
		
		return towns;
		
	}
	
	/**
	 * Builds bonusList, towns bonuses.
	 * Syntax of bonusFile .txt file lines must be
	 * <code><br>[BonusID] [bonusValue]:[BonusID] [bonusValue]<br><code>
	 * All bonuses on the same line create a unique bonus packet.
	 * Bonus codes are CoinBonus CB, DrawCardBonus DCB, MainActionBonus MAB, NobilityBonus NB, ReusePermitBonus RPB
	 * VictoryPointBonus VPB, TakeAssistantBonus AB.
	 * Bonus must be one less than the Town (King's town doesn't have the bonus)
	 * @param bonusFile File to parse with related path
	 * @return ArrayList of bonuses bonusList, towns bonuses.
	 * @throws IOException If there's an issue during file reading process
	 * @throws NullPointerException if param file is null 
	 */
	public List<Bonus> buildBonus(File bonusFile) throws IOException {
		
		Preconditions.checkNotNull(bonusFile, ERROR_FILE_CONFIG);
		
		String myLine = null;
		BufferedReader bufRead;
	
		List<Bonus> bonusList = new ArrayList<>();
	
		try (FileReader input = new FileReader(bonusFile)){

			bufRead = new BufferedReader(input);
			myLine = null;

			while ( (myLine = bufRead.readLine()) != null){
			
				String[] bonus = myLine.split(":");
				List<Bonus> bonusPacket = new ArrayList<>();
			
				for (int i = 0; i < bonus.length; i++){
					String[] bonusInfo = bonus[i].split(" ");
					bonusPacket.add(parserBonus(bonusInfo[0], bonusInfo[1]));
				}
			
				//OPTIMIZATION: Avoid to create bonusPack if there is only one bonus "on the line"
				if (bonus.length == 1) 
					bonusList.add(bonusPacket.get(0));
				else bonusList.add(new BonusPack(bonusPacket));
			
			}
		} catch (IOException e) {
			throw new IOException(myLine, e);
		}
		
		Collections.shuffle(bonusList);
		
		return bonusList;
		
    }
	
}
