package it.polimi.ingsw.cg28.controller.updaters;


import it.polimi.ingsw.cg28.controller.bonus.Bonus;
import it.polimi.ingsw.cg28.controller.bonus.BonusPack;
import it.polimi.ingsw.cg28.controller.bonus.DrawCardBonus;
import it.polimi.ingsw.cg28.controller.bonus.NobilityBonus;
import it.polimi.ingsw.cg28.controller.bonus.ReuseCityBonus;
import it.polimi.ingsw.cg28.controller.bonus.ReusePermitBonus;
import it.polimi.ingsw.cg28.controller.bonus.TakePermitTileBonus;

/**
 * Interface to implement Visitor Pattern on Bonus objects
 * @author Mario
 *
 */
public interface BonusTranslator {
	
	/**
	 * Defines a visit "strategy" for Bonus objects
	 * @param bonus to visit
	 */
	public void translate(Bonus bonus);
	
	/**
	 * Defines a visit "strategy" for BonusPack objects
	 * @param bonus to visit
	 */
	public void translate(BonusPack bonus);
	
	/**
	 * Defines a visit "strategy" to assign NobilityBonus objects.
	 * Default implementation: visit the bonus as a generic bonus.
	 * @param bonus to visit
	 */
	public default void translate(NobilityBonus bonus){
		
		Bonus b = bonus;
		this.translate(b);
		
	}
	
	/**
	 * Defines a visit "strategy" to assign DrawCardBonus objects.
	 * Default implementation: visit the bonus as a generic bonus.
	 * @param bonus to visit
	 */
	public default void translate(DrawCardBonus bonus){
		
		Bonus b = bonus;
		this.translate(b);
		
	}
	
	/**
	 * Defines a visit "strategy" to assign ReusePermitBonus objects.
	 * Default implementation: visit the bonus as a generic bonus.
	 * @param bonus to visit
	 */
	public default void translate(ReusePermitBonus bonus){
		
		Bonus b = bonus;
		this.translate(b);
		
	}
	
	/**
	 * Defines a visit "strategy" to assign ReuseCityBonus objects.
	 * Default implementation: visit the bonus as a generic bonus.
	 * @param bonus to visit
	 */
	public default void translate(ReuseCityBonus bonus){
		
		Bonus b = bonus;
		this.translate(b);
		
	}
	
	/**
	 * Defines a visit "strategy" to assign TakePermitTileBonus objects.
	 * Default implementation: visit the bonus as a generic bonus.
	 * @param bonus to visit
	 */
	public default void  translate(TakePermitTileBonus bonus){
		
		Bonus b = bonus;
		this.translate(b);
	}

}
