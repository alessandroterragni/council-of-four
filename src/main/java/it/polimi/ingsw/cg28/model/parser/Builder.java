package it.polimi.ingsw.cg28.model.parser;

import java.awt.Color;
import java.io.IOException;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.bonus.AssistantBonus;
import it.polimi.ingsw.cg28.controller.bonus.BonusType;
import it.polimi.ingsw.cg28.controller.bonus.CoinBonus;
import it.polimi.ingsw.cg28.controller.bonus.DrawCardBonus;
import it.polimi.ingsw.cg28.controller.bonus.MainActionBonus;
import it.polimi.ingsw.cg28.controller.bonus.NobilityBonus;
import it.polimi.ingsw.cg28.controller.bonus.ReuseCityBonus;
import it.polimi.ingsw.cg28.controller.bonus.ReusePermitBonus;
import it.polimi.ingsw.cg28.controller.bonus.TakePermitTileBonus;
import it.polimi.ingsw.cg28.controller.bonus.VictoryPointBonus;
import it.polimi.ingsw.cg28.model.Region;

/**
 * Abstract Builder.
 * @author Mario
 *
 */
public abstract class Builder {
	
	/**
	 * Method to Parse Bonus taking its code and its value.
	 * Bonus codes are CoinBonus CB, DrawCardBonus DCB, MainActionBonus MAB, NobilityBonus NB, ReusePermitBonus RPB
     * VictoryPointBonus VPB, TakeAssistantBonus AB.
	 * @param toParse Code to specify the bonus
	 * @param bonusValue Value of bonus
	 * @return bonus specified
	 * @throws IOException error in configFile (String not recognized, bonus value <1
	 * @throws NullPointerException if one argument is null
	 * @throws NumberFormatException if bonusValue is not "parsable" to integer
	 */
	public BonusType parserBonus(String toParse,String bonusValue) throws IOException{
		
		Preconditions.checkNotNull(toParse, "String toParse is null");
		Preconditions.checkNotNull(bonusValue, "String bonusValue is null");
		
		int value;
		
		value = Integer.parseInt(bonusValue);
		
		if (value < 1)
			throw new IOException("Error file config BONUS: " + toParse + " || " + "bonusValue" + bonusValue);
		
		switch (toParse){
		case "CB":
			return new CoinBonus(value);
		case "DCB":
			return new DrawCardBonus(value);
		case "MAB":
			return new MainActionBonus(value);
		case "NB":
			return new NobilityBonus(value);
		case "VPB":
			return new VictoryPointBonus(value);
		case "RPB":
			return new ReusePermitBonus(value);
		case "AB":
			return new AssistantBonus(value);
		case "RCB":
			return new ReuseCityBonus(value);
		case "TPB":
			return new TakePermitTileBonus(value);
		default:
			throw new IOException("Error file config BONUS: " + toParse + " || " + "bonusValue" + bonusValue);
	    }
	}
	
	/**
	 * Method to take Region object from regions array with its regionType by String.
	 * @param regions Region of the game
	 * @param regionType Specified region
	 * @return The selected region
	 * @throws IOException error in configFile
	 */
	public Region parserRegion(Region[] regions, String regionType) throws IOException {
		
		for(Region r : regions)
			if(regionType.equals(r.getRegionType()))
				return r;
		
		throw new IOException("Error file config REGION: " + regionType);
		
	}
	
	/**
	 * Method to parse color.
	 * @param string to identify the Color
	 * @return {@link java.awt.Color} matching the String
	 * @throws IOException if the string can't be identified as a Color
	 */
	public Color parserColor(String string) throws IOException {
		
		switch (string){
			case "Black": 
				return Color.black;
			case "Pink":
				return Color.pink;
			case "Magenta":
				return Color.magenta;
			case "White":
				return Color.white;
			case "Orange":
				return Color.orange;
			case "Blue":
				return Color.blue;
			default: throw new IOException("Error file config: " + string);
		}
		
	}
	
	

}
