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
 * Bonus to allow the player to obtain a business permit tiles from the uncovered ones.
 * @author Mario
 *
 */
public class TakePermitTileBonus implements BonusType {

	private int numbOfTiles;
	
	/**
	 * It's the constructor of the class.
	 * @param numbOfTiles - number of business permit tiles the player can obtain with the bonus.
	 * @throws IllegalArgumentException if the parameter passed is <=0
	 */
	public TakePermitTileBonus(int numberOfTiles) {
		
		Preconditions.checkArgument(numberOfTiles > 0);
		
		this.numbOfTiles = numberOfTiles;
	}

	/**
	 * This method can't be called because get the bonus requires user interaction and related action.
	 */
	@Override
	public void getBonus(Player p) {
		Logger.getLogger(Game.class.getName()).log(Level.WARNING, "The method getBonus on that bonus shouldn't be executed, use BonusTranslator!");
	}
	
	/**
	 * Returns the number of permit tiles the player can obtain.
	 */
	@Override
	public int getValue() {
		return numbOfTiles;
	}
	
	/**
	 * Method to implements the visitor pattern with the Interface Bonus Translator.
	 */
	@Override
	public void translate (BonusTranslator bonusTranslator){
		
		bonusTranslator.translate(this);
	}


}
