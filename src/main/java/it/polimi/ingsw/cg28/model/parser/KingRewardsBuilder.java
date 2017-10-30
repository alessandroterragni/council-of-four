package it.polimi.ingsw.cg28.model.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.bonus.Bonus;
import it.polimi.ingsw.cg28.controller.bonus.BonusPack;
import it.polimi.ingsw.cg28.model.KingRewardTile;

/**
 * Builds from Input KingRewards deck
 * @author Mario
 *
 */
public class KingRewardsBuilder extends Builder {
	
	/**
	 * Builds KingRewards deck from file .txt
	 * Syntax of .txt file lines must be
	 * <code><br>[BonusID] [bonusValue]:[BonusID] [bonusValue]<br></code>
	 * All bonuses on the same line create a unique bonus packet.
	 * Bonus codes are CoinBonus CB, DrawCardBonus DCB, MainActionBonus MAB, NobilityBonus NB, ReusePermitBonus RPB
	 * VictoryPointBonus VPB, TakeAssistantBonus AB.
	 * @param file File to parse with related path
	 * @return
	 * @throws IOException
	 *  @throws NullPointerException if param file is null 
	 */
	public List<KingRewardTile> build(File file) throws IOException {
		
		Preconditions.checkNotNull(file, "Configuration File couldn't be null!");
		
		String myLine = null;
		BufferedReader bufRead;
		List<KingRewardTile> kingRewards = new ArrayList<>();;
	
		try (FileReader input = new FileReader(file)){

			bufRead = new BufferedReader(input);
			myLine = null;
			
			while ( (myLine = bufRead.readLine()) != null){
				
				String[] bonus = myLine.split(":");
				ArrayList<Bonus> kingBonus = new ArrayList<>();
				
				for (int i = 0; i < bonus.length; i++){
					String[] bonusInfo = bonus[i].split(" ");
		    		kingBonus.add(parserBonus(bonusInfo[0], bonusInfo[1]));
				}
				
				//OPTIMIZATION: Avoid to create bonusPack if there is only one bonus "on the line"
				if(bonus.length == 1)
					kingRewards.add(new KingRewardTile(kingBonus.get(0)));	
				else kingRewards.add(new KingRewardTile(new BonusPack(kingBonus)));	
				
		    }
			
         } catch (IOException e) {
        	 throw new IOException(myLine, e);
		 }
		
		return kingRewards;
	}

}
