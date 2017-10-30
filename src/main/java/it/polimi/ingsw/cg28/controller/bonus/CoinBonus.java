package it.polimi.ingsw.cg28.controller.bonus;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.updaters.BonusTranslator;
import it.polimi.ingsw.cg28.model.Player;

/**
 * Bonus that increments the Player's coins 
 * @author Alessandro
 *
 */
public class CoinBonus implements BonusType {

	int numberOfCoins;
	
	/**
	 * It's the constructor of the class.
	 * @param nmbOfCoins - number of Coins that you add to the player 
	 * @throws IllegalArgumentException if the parameter passed is <=0
	 */
	public CoinBonus(int numberOfCoins){
		Preconditions.checkArgument(numberOfCoins>0);
		this.numberOfCoins=numberOfCoins;
	}
	
	/**
	 * It increments the number of coins of the player with the parameter passed by the constructor.
	 * @param p  - player who gains the bonus
	 * @throws NullPointerException if the player passed is Null
	 */
	@Override
	public void getBonus(Player p){
		p.getCoins().increment(numberOfCoins);
	}
	
	/**
	 * It returns the number of coins you gain with this bonus.
	 */
	@Override
	public int getValue() {
		return numberOfCoins;
	}
	
	/**
	 * Method to implements the visitor pattern with the Interface Bonus Translator.
	 */
	@Override
	public void translate (BonusTranslator bonusTranslator){
		
		bonusTranslator.translate(this);
	}
	
}
