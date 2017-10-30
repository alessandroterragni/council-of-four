package it.polimi.ingsw.cg28.controller.bonus;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.updaters.BonusTranslator;
import it.polimi.ingsw.cg28.model.Player;

/**
 * Bonus that adds a number of assistants to the player.
 * @author Alessandro
 *
 */
public class AssistantBonus implements BonusType {

	int numberOfAssistants;
	
	/**
	 * It's the constructor of the class.
	 * @param numberOfAsssistants - the number of Assistants you want to add to the player
	 * @throws IllegalArgumentException if the parameter passed is <=0
	 */
	public AssistantBonus(int numberOfAssistants){
		Preconditions.checkArgument(numberOfAssistants>0);
		this.numberOfAssistants=numberOfAssistants;
	}
	
	/**
	 * It adds a number(parameter in the constructor) of Assistants to the Player.
	 * @param p - player who gains the bonus
	 * @throws NullPointerException if the player passed is Null
	 */
	@Override
	public void getBonus(Player p){
		Preconditions.checkNotNull(p, "Player can't be null");
		p.getAssistants().increment(numberOfAssistants);
	}
	
	/**
	 * It returns the number of assistants you gain with this bonus.
	 */
	@Override
	public int getValue() {
		return numberOfAssistants;
	}
	
	/**
	 * Method to implements the visitor pattern with the Interface Bonus Translator.
	 */
	@Override
	public void translate (BonusTranslator bonusTranslator){
		
		bonusTranslator.translate(this);
	}
	
}
