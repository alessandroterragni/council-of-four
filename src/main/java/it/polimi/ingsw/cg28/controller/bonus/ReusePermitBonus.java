package it.polimi.ingsw.cg28.controller.bonus;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.Game;
import it.polimi.ingsw.cg28.controller.updaters.BonusTranslator;
import it.polimi.ingsw.cg28.model.Player;


/**
 * Bonus to allow the player to re-obtain bonuses of possessed permit tiles (used or not).
 * @author Mario
 *
 */
public class ReusePermitBonus implements BonusType {
	
	private int numbOfReusePermit;
	
	/**
	 * It's the constructor of the class.
	 * @param numbOfReusePermit - number of business permit tiles the player can retrieve the bonuses of
	 * @throws IllegalArgumentException if the parameter passed is <=0
	 */
	public ReusePermitBonus(int numberOfReusePermit) {
		
		Preconditions.checkArgument(numberOfReusePermit > 0);
		
		this.numbOfReusePermit = numberOfReusePermit;
	}
	
	/**
	 * This method can't be called because get the bonus requires user interaction and related action.
	 */
	@Override
	public void getBonus(Player p){
		Logger.getLogger(Game.class.getName()).log(Level.WARNING, "The method getBonus on that bonus shouldn't be executed, use BonusTranslator!");
	}
	
	/**
	 * Returns the number of permit tiles the player can retrieve the bonuses of.
	 */
	@Override
	public int getValue() {
		return numbOfReusePermit;
	}
	
	/**
	 * Method to implements the visitor pattern with the Interface Bonus Translator.
	 */
	@Override
	public void translate (BonusTranslator bonusTranslator){
		
		bonusTranslator.translate(this);
	}
	
}
