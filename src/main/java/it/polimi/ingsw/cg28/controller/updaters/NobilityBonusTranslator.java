package it.polimi.ingsw.cg28.controller.updaters;


import java.util.List;

import it.polimi.ingsw.cg28.controller.bonus.Bonus;
import it.polimi.ingsw.cg28.controller.bonus.BonusPack;
import it.polimi.ingsw.cg28.controller.bonus.NobilityBonus;


/**
 * Concrete visit strategy implementation of the BonusTranslator interface.
 * Allows to check NobilityBonus.
 * @author Mario
 * @implements BonusTranslator
 */
public class NobilityBonusTranslator implements BonusTranslator {
	
	private int[] elements;
	private int index;
	
	/**
	 * Class constructor.
	 * @param numberElements elements to check
	 */
	public NobilityBonusTranslator(int numberElements) {
		this.elements = new int[numberElements];
		
		for(int i=0; i<elements.length; i++)
			elements[i] = 0;
	}
	
	/**
	 * Returns elements array. 
	 * @return elements array of int: elements[i] = 1 if i-th Bonus visited is a NobilityBonus 
	 * visited by Visitor. 0 in other positions.
	 */
	public int[] getElements() {
		return elements;
	}
	
	/**
	 * If Bonus visited isn't a NobilityBonus only increments index.
	 */
	@Override
	public void translate(Bonus bonus) {
		index ++;
	}
	
	/**
	 * If Bonus visited is a NobilityBonus sets elements [index] to 1 and increments index.
	 * @param bonus to visit
	 */
	@Override
	public void translate(NobilityBonus bonus) {
		elements[index] = 1;
		index ++;
	}
	
	/**
	 * If Bonus visited is a BonusPack calls visitor method on each BonusPack bonus.
	 */
	@Override
	public void translate(BonusPack bonus) {
		
		List<Bonus> bonuses = bonus.getBonusPack();
		
		NobilityBonusTranslator nobilityBonusTranslator = new NobilityBonusTranslator(bonuses.size());
		
		for(Bonus b : bonuses)
			b.translate(nobilityBonusTranslator);
		
		int[] check = nobilityBonusTranslator.getElements();
		
		for(int i=0; i< check.length; i++)
			if (check[i] == 1)
				elements[index] = 1;

		
	}

}
