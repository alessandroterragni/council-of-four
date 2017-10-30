/**
 * 
 */
package it.polimi.ingsw.cg28.controller.bonus;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.updaters.BonusTranslator;
import it.polimi.ingsw.cg28.model.Player;

/**
 * Bonus that adds nobility points to the player.
 * @author Alessandro
 *
 */

public class NobilityBonus implements BonusType {

	int numbOfNobilityPoints;
	
	/**
	 * It's the constructor of the class.
	 * @param nmbOfNobilityPoints - the number of Nobility points you want to add to the player
	 * @throws IllegalArgumentException if the parameter passed is <=0
	 */
	public NobilityBonus(int nmbOfNobilityPoints){
		Preconditions.checkArgument(nmbOfNobilityPoints>0);
		this.numbOfNobilityPoints=nmbOfNobilityPoints;
	}
	
	/**
	 * It increments the number of Nobility Points of the player with the parameter specified in the constructor.
	 * @param p - player who gains the bonus
	 * @throws NullPointerException if the player passed is Null
	 */
	@Override
	public void getBonus(Player p){
		Preconditions.checkNotNull(p, "Player can't be null");
		p.rankUp(numbOfNobilityPoints);
	}
	
	/**
	 * Returns the number of nobility points you gain with this bonus.
	 */
	@Override
	public int getValue() {
		return numbOfNobilityPoints;
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
