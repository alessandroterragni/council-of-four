/**
 * 
 */
package it.polimi.ingsw.cg28.controller.updaters;

import it.polimi.ingsw.cg28.controller.bonus.Bonus;
import it.polimi.ingsw.cg28.controller.bonus.BonusPack;


/**
 * Concrete visit strategy implementation of the BonusTranslator interface.
 * Specifies a "print strategy" for different bonuses kinds (relative String representation). 
 * Each string, related to bonus visited, is concatenated to others, in the visit order. 
 * @author Mario
 * @implements BonusTranslator
 */
public class PrintBonusTranslator implements BonusTranslator {
	
	StringBuilder sb;
	
	/**
	 * Constructor of the class.
	 */
	public PrintBonusTranslator() {
		sb = new StringBuilder();
	}
	
	/**
	 * Defines a visit "strategy" to print Bonus objects
	 * <br><code>BonusName:BonusValue>BonusName:BonusValue</code>
	 */
	@Override
	public void translate(Bonus bonus) {
		sb.append(bonus.getClass().getSimpleName() + ":" + bonus.getValue() + ">");
	}
	
	/**
	 * Defines a visit "strategy" to print BonusPack objects. Visits each bonus contained in the pack
	 * concatenating the relative String representation.
	 */
	@Override
	public void translate(BonusPack bonus) {
		
		for(Bonus b : bonus.getBonusPack())			
			b.translate(this);

	}
	
	/**
	 * Returns a single String: concatenation of the String representations of all the bonuses visited.
	 * (as specified by translate(Bonus) method)
	 * @return String - concatenation of the String representations of all the bonuses visited.
	 * @see #translate(Bonus)
	 */
	public String getString(){
		return sb.toString();
	}

}
