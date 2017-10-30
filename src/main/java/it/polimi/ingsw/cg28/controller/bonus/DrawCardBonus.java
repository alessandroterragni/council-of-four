/**
 * 
 */
package it.polimi.ingsw.cg28.controller.bonus;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.updaters.BonusTranslator;
import it.polimi.ingsw.cg28.model.Player;
import it.polimi.ingsw.cg28.model.PoliticCard;
import it.polimi.ingsw.cg28.model.decks.CardDeck;

/**
 * Bonus that allows the player to draw a number of politic cards.
 * @author Alessandro
 *
 */
public class DrawCardBonus implements BonusType {

	int numberOfCards;
	private CardDeck<PoliticCard> cardDeck;
	
	/**
	 * It's the constructor of the class.
	 * @param numberOfCards  the number of politic cards that the player will draw thanks to this bonus
	 * @throws IllegalArgumentException if the parameter passed is <=0
	 */
	public DrawCardBonus(int numberOfCards){
		Preconditions.checkArgument(numberOfCards>0);
		this.numberOfCards=numberOfCards;
	}
	
	/**
	 * Setter method to set the Politic Card Deck.
	 * @param cardDeck the cardDeck to set
	 */
	public void setCardDeck(CardDeck<PoliticCard> cardDeck) {
		this.cardDeck = cardDeck;
	}
	
	
	/**
	 * It adds a number(parameter in the constructor) of Politic cards to the hand of the Player. 
	 * @throws NullPointerException if the player passed is Null
	 */
	@Override
	public void getBonus(Player p){
		
		Preconditions.checkNotNull(p, "Player can't be null");
		List<PoliticCard> drawnCards = new ArrayList<>();
		
		for(int i=0;i<numberOfCards;i++)
			drawnCards.add(cardDeck.draw());	
		
		p.addCards(drawnCards);
		
	}
	
	/**
	 * It returns the number of cards you gain with this bonus.
	 */
	@Override
	public int getValue() {
		return numberOfCards;
	}
	
	/**
	 * Method to implements the visitor pattern with the Interface Bonus Translator.
	 */
	@Override
	public void translate (BonusTranslator bonusTranslator){
		
		bonusTranslator.translate(this);
		
	}

	
	
}
