/**
 * 
 */
package it.polimi.ingsw.cg28.controller.bonus;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.actions.MainAction;
import it.polimi.ingsw.cg28.controller.updaters.BonusTranslator;
import it.polimi.ingsw.cg28.model.Player;

/**
 * Bonus that allows the player to do a second MainAction in the current Turn.
 * @author Alessandro
 *
 */
public class MainActionBonus implements BonusType {

	int numberOfMainAction;
	
	/**
	 * It's the constructor of the class.
	 * @param numberOfMainActions - number of Main Action to add to the current turn of the Player
	 * @throws IllegalArgumentException if the parameter passed is <=0
	 */
	public MainActionBonus(int numberOfMainActions){
		
		Preconditions.checkArgument(numberOfMainActions>0);
		this.numberOfMainAction=numberOfMainActions;	
	}
	
	/**
	 * It adds a number of MainActions specified in the constructor to the the current turn of the Player.
	 * @param p - player who gains the bonus
	 * @throws NullPointerException if the player passed is Null
	 */
	@Override
	public void getBonus(Player p){
		Preconditions.checkNotNull(p, "Player can't be null");
		for(int i=0;i<numberOfMainAction;i++)
			p.getTurn().addAction(new MainAction());
	}

	/**
	 * Returns the number of main actions you gain with this bonus.
	 */
	@Override
	public int getValue() {
		return numberOfMainAction;
	}
	
	/**
	 * Method to implements the visitor pattern with the Interface Bonus Translator.
	 * @param bonusTranslator
	 */
	@Override
	public void translate (BonusTranslator bonusTranslator){
		
		bonusTranslator.translate(this);
	}
}
