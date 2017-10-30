package it.polimi.ingsw.cg28.model.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.bonus.Bonus;
import it.polimi.ingsw.cg28.controller.bonus.BonusPack;
import it.polimi.ingsw.cg28.model.BusinessPermitTile;
import it.polimi.ingsw.cg28.model.Region;
import it.polimi.ingsw.cg28.model.decks.TileDeck;

/**
 * Builds from Input the BusinessPermitTile decks.
 * @author Mario
 *
 */
public class PermitTileBuilder extends Builder{
    
	/**
	 * Method, builds deck from .txt file input.
	 * Syntax of .txt file lines must be
	 * <code><br>[RegionType]
	 * <br>[City] [City]>[BonusID] [bonusValue]:[BonusID] [bonusValue]
	 * <br>[City] [City]>[BonusID] [bonusValue]:[BonusID] [bonusValue]
	 * <br>STOP
	 * <br>[RegionType]
	 * <br>[City] [City]>[BonusID] [bonusValue]:[BonusID] [bonusValue]
	 * <br>...
	 * <br>...
	 * <br>END<br></code>
	 * Uses RegionType string to match region of regions array.
	 * @param file file to parse with related path
	 * @param regions game regions
	 * @throws IOException If there's an issue during file reading process
	 * @throws NullPointerException If param file is null
	 */
	public void build(File file, Region[] regions) throws IOException {
		
		Preconditions.checkNotNull(file, "Configuration File couldn't be null!");
		Preconditions.checkNotNull(file, "Regions Array couldn't be null!");
		
		String myLine = null;
		String regionType;
		BufferedReader bufRead;
		
		List<LinkedList<BusinessPermitTile>> tiles = new ArrayList<>();
		
		for(int i=0; i<regions.length; i++){
			LinkedList<BusinessPermitTile> region = new LinkedList<>();
			tiles.add(region);
		}
		
		int index = 0;

		try (FileReader input = new FileReader(file)){

			bufRead = new BufferedReader(input);
			myLine = null;
			
			regionType = bufRead.readLine();
			
			while ( (myLine = bufRead.readLine()) != null){
				
				if ("STOP".equals(myLine) || "END".equals(myLine)){
					
					Region currentRegion = parserRegion(regions,regionType);
					
					currentRegion.setDeck(new TileDeck<BusinessPermitTile>(tiles.get(index)));
					
					if ("END".equals(myLine)) 
						break;
					
					index++;
					regionType = bufRead.readLine();
					myLine = bufRead.readLine();
					
				}
				
				String[] permitTile = myLine.split(">");
				String[] towns = permitTile[0].split(" ");
				String[] bonus = permitTile[1].split(":");
				
				ArrayList<Bonus> bonusTown = new ArrayList<>();
				
				for (int i = 0; i < bonus.length; i++){
					String[] bonusInfo = bonus[i].split(" ");
		    		bonusTown.add(parserBonus(bonusInfo[0], bonusInfo[1]));
				}
				
				//OPTIMIZATION: Avoid to create bonusPack if there is only one bonus "on the line"
				Bonus pack;
				if(bonus.length == 1)
					pack = bonusTown.get(0);
				else pack = new BonusPack(bonusTown);
				
				BusinessPermitTile tile = new BusinessPermitTile(pack,towns);
				
				tiles.get(index).add(tile);
				
			}
			
		} catch (Exception e) {
			throw new IOException(myLine, e);
		}
	}

}
