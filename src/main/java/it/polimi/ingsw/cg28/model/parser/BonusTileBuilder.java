package it.polimi.ingsw.cg28.model.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.bonus.Bonus;
import it.polimi.ingsw.cg28.controller.bonus.BonusPack;
import it.polimi.ingsw.cg28.model.BonusTile;
import it.polimi.ingsw.cg28.model.decks.TileDeck;

/**
 *  Builds from Input BonusTile deck
 * @author Mario
 *
 */
public class BonusTileBuilder extends Builder {
    
	/**
	 * Builds BonusTile deck from file .txt
	 * Syntax of .txt file lines must be
	 * <code><br>[StringIdentifier]>[BonusID] [bonusValue]:[BonusID] [bonusValue]<br></code>
	 * All bonuses on the same line create a unique bonus packet.
	 * The identifier string identifies the "strategy" to assign the bonus
	 * Bonus codes are CoinBonus CB, DrawCardBonus DCB, MainActionBonus MAB, NobilityBonus NB, ReusePermitBonus RPB
	 * VictoryPointBonus VPB, TakeAssistantBonus AB.
	 * @param file File to parse with related path
	 * @return
	 * @throws IOException 
	 * @throws NullPointerException if param file is null
	 */
	public TileDeck<BonusTile> build(File file) throws IOException {
		
		Preconditions.checkNotNull(file, "Configuration File couldn't be null!");
		
		String myLine = null;
		BufferedReader bufRead;
		LinkedList<BonusTile> bonusTiles = new LinkedList<>();

		try (FileReader input = new FileReader(file)){
			
			bufRead = new BufferedReader(input);
			myLine = null;
			
			while ( (myLine = bufRead.readLine()) != null){
				
				String[] bonusTile = myLine.split(">");
				
				String[] bonus = bonusTile[1].split(":");
				ArrayList<Bonus> pack = new ArrayList<>();
					
				for (int i = 0; i < bonus.length; i++){
					String[] bonusInfo = bonus[i].split(" ");
		    		pack.add(parserBonus(bonusInfo[0], bonusInfo[1]));
				}
				
				//OPTIMIZATION: Avoid to create bonusPack if there is only one bonus "on the line"
				if (bonus.length == 1)
					bonusTiles.add(new BonusTile(bonusTile[0],pack.get(0)));	
				else bonusTiles.add(new BonusTile(bonusTile[0],new BonusPack(pack)));	
		    }
			
         } catch (IOException e) {
        	 throw new IOException(myLine, e);
		}
		
		return new TileDeck<>(bonusTiles);
		
	}

}
