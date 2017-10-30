/**
 * 
 */
package it.polimi.ingsw.cg28.controller.bonus;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.Game;
import it.polimi.ingsw.cg28.controller.updaters.BonusTranslator;
import it.polimi.ingsw.cg28.model.Player;

/**
 * Bonus to allow the player to re-obtain bonuses of towns where he has an emporium
 * @author Mario
 *
 */
public class ReuseCityBonus implements BonusType {

	private int numbOfReuseTowns;
	
	/**
	 * It's the constructor of the class.
	 * @param numbOfReuseTowns - number of towns the player can retrieve the bonuses of 
	 * @throws IllegalArgumentException if the parameter passed is <=0
	 */
	public ReuseCityBonus(int numbOfReuseTowns) {
		
		Preconditions.checkArgument(numbOfReuseTowns > 0);
		this.numbOfReuseTowns = numbOfReuseTowns;
	}
	
	/**
	 * This method can't be called because get the bonus requires user interaction and related action.
	 */
	@Override
	public void getBonus(Player p) {
		Logger.getLogger(Game.class.getName()).log(Level.WARNING, "The method getBonus on that bonus shouldn't be executed, use BonusTranslator!");
	}
	
	/**
	 * Returns the number of towns the player can retrieve the bonuses of.
	 */
	@Override
	public int getValue() {
		return numbOfReuseTowns;
	}
	
	/**
	 * Method to implements the visitor pattern with the Interface Bonus Translator.
	 */
	@Override
	public void translate (BonusTranslator bonusTranslator){
		
		bonusTranslator.translate(this);
		
	}

}
