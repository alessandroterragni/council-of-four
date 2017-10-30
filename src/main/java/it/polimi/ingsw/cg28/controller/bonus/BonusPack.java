package it.polimi.ingsw.cg28.controller.bonus;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.Game;
import it.polimi.ingsw.cg28.controller.updaters.BonusTranslator;
import it.polimi.ingsw.cg28.model.Player;

/**
 * It implements the Bonus interface.
 * Has an attribute bonus used to create packet of Bonus that the player can gain all together.
 * Allows to put bonus pack inside another bonus pack. 
 * @author Alessandro, Mario
 *
 */
public class BonusPack implements Bonus{

	private List<Bonus> bonus;
	
	/**
	 * It's the constructor of the class.
	 * @param bonus - the array list of bonus that I want to to put in the BonusPack
	 * @throws NullPointerException if bonus is null
	 */
	public BonusPack(List<Bonus> bonus){
		
		Preconditions.checkNotNull(bonus);
		this.bonus=bonus;
		
	}
	
	/**
	 * This method can't be called because bonuses contained in the pack may require user interaction and related action.
	 */
	@Override
	public void getBonus(Player p) {
	
		Logger.getLogger(Game.class.getName()).log(Level.WARNING, "The method getBonus on bonus Pack shouldn't be executed, use BonusTranslator!");
		throw new NoSuchElementException();
	
	}
	
	/**
	 * Returns list of bonuses contained in the pack.
	 * @return list of bonuses contained in the pack.
	 */
	public List<Bonus> getBonusPack() {

		return bonus;
	
	}
	
	/**
	 * BonusPack doesn't has a value associated. Returns zero.
	 */
	@Override
	public int getValue() {
		Logger.getLogger(Game.class.getName()).log(Level.WARNING, "The method getValue on bonus Pack shouldn't be executed, check every bonus!");
		return 0;
	}
	
	/**
	 * Method to implements the visitor pattern with the Interface Bonus Translator
	 */
	@Override
	public void translate (BonusTranslator bonusTranslator){
		
		bonusTranslator.translate(this);
		
	}

}
