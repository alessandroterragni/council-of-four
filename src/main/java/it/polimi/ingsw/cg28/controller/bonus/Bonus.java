package it.polimi.ingsw.cg28.controller.bonus;

import it.polimi.ingsw.cg28.controller.updaters.BonusTranslator;
import it.polimi.ingsw.cg28.model.Player;

/**
 * Bonus interface that provides the getBonus() method 
 * @author Alessandro
 *
 */
public interface Bonus {
	
		/**
		 * It performs the action of the bonus.
		 * @param p - it's the player who gained the advantages of the bonus 
		 */
		public void getBonus(Player p);
		
		/**
		 * It returns the bonus value.
		 * @return int - bonus value
		 */
		public int getValue();
		
		/**
		 * Method to implements the visitor pattern with the Interface Bonus Translator.
		 * @param bonusTranslator
		 */
		public void translate (BonusTranslator bonusTranslator);
		
}
