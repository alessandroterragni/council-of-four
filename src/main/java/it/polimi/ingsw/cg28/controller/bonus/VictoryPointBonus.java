package it.polimi.ingsw.cg28.controller.bonus;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.updaters.BonusTranslator;
import it.polimi.ingsw.cg28.model.Player;

/**
 * Bonus that adds a number of victory point to the player.
 * @author Alessandro
 *
 */

public class VictoryPointBonus implements BonusType {

	int numbOfVictoryPoints;
	/**
	 * It's the constructor for the class.
	 * @param numberOfVictoryPoints - the number of victory points that the bonus adds to the player
	 *  @throws IllegalArgumentException if the parameter passed is <=0
	 */
	public VictoryPointBonus(int numberOfVictoryPoints){
		Preconditions.checkArgument(numberOfVictoryPoints>0);
		this.numbOfVictoryPoints=numberOfVictoryPoints;
	}
	
	/**
	 * It increments the number of victory Points of the player with the parameter specified in the constructor.
	 * @param p - player who gains the bonus
	 * @throws NullPointerException if the player passed is Null
	 */
	@Override
	public void getBonus(Player p){
		Preconditions.checkNotNull(p, "Player can't be null");
		p.getScore().increment(numbOfVictoryPoints);
	}

	/** 
	 * Returns the number of nobility points you gain with this bonus.
	 */
	@Override
	public int getValue() {
		return numbOfVictoryPoints;
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
