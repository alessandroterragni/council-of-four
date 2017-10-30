package it.polimi.ingsw.cg28.model.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.bonus.Bonus;
import it.polimi.ingsw.cg28.controller.bonus.BonusPack;
import it.polimi.ingsw.cg28.model.NobilityTrackBonus;

/**
 * Builds from Input NobilityTrack (links NobilityRank value and related bonuses)
 * @author Mario
 *
 */
public class NobilityTrackBuilder extends Builder{
	
	/**
	 * Builds NobilityTrack from file .txt
	 * Syntax of .txt file lines must be
	 * <code><br>[NobilityRank]>[BonusID] [bonusValue]:[BonusID] [bonusValue]<br></code>
	 * All bonuses on the same line create a unique bonus packet related to NobilityRank value.
	 * Bonus codes are CoinBonus CB, DrawCardBonus DCB, MainActionBonus MAB, NobilityBonus NB, ReusePermitBonus RPB
	 * VictoryPointBonus VPB, TakeAssistantBonus AB.
	 * @param file File to parse with related path
	 * @return NobilityTrackBonus built from file
	 * @throws IOException If there's an issue during file reading process
	 * @throws NullPointerException if param file is null
	 */
	public NobilityTrackBonus build(File file) throws IOException {
		
		Preconditions.checkNotNull(file, "Configuration File couldn't be null!");
		
		String myLine = null;
		BufferedReader bufRead;
		
		NobilityTrackBonus nobilityTrackBonus = new NobilityTrackBonus();

		try (FileReader input = new FileReader(file)) {
			
			bufRead = new BufferedReader(input);
			myLine = null;
			
			while ( (myLine = bufRead.readLine()) != null){
				
				String[] position = myLine.split(">");
				
				String[] bonus = position[1].split(":");
				ArrayList<Bonus> pack = new ArrayList<>();
					
				for (int i = 0; i < bonus.length; i++){
					String[] bonusInfo = bonus[i].split(" ");
		    		pack.add(parserBonus(bonusInfo[0], bonusInfo[1]));
				}
				
				//OPTIMIZATION: Avoid to create bonusPack if there is only one bonus "on the line"
				if (bonus.length == 1)
					nobilityTrackBonus.addValue(Integer.parseInt(position[0]), pack.get(0));
				else nobilityTrackBonus.addValue(Integer.parseInt(position[0]), new BonusPack(pack));	
		    }
			
         } catch (Exception e) {
        	 throw new IOException(myLine, e);
		}
		
		return nobilityTrackBonus;
		
	}

}
