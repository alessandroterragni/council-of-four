/**
 * 
 */
package it.polimi.ingsw.cg28.controller.actions;


import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.Game;
import it.polimi.ingsw.cg28.controller.updaters.BonusActionUpdater;
import it.polimi.ingsw.cg28.exception.InvalidActionException;
import it.polimi.ingsw.cg28.model.Balcony;
import it.polimi.ingsw.cg28.model.BusinessPermitTile;
import it.polimi.ingsw.cg28.model.Player;
import it.polimi.ingsw.cg28.model.PoliticCard;
import it.polimi.ingsw.cg28.model.Region;
import it.polimi.ingsw.cg28.model.decks.CardDeck;

/**
 * It's a Main action that allows the player to buy a permit tile. 
 * @author Alessandro
 *
 */
public class PermitTileAction extends MainAction {
	
	private Region region;
	private int tilePosition;
	private List<PoliticCard> chosenPoliticCards;
	private CardDeck<PoliticCard> deck;
	
	/**
	 * Constructor to use the action as matcher between PoliticCards and a Balcony.
	 * @param chosenPoliticCards the array list of Politic cards chosen by the Player to satisfy the balcony of the chosen region
	 */
	public PermitTileAction(List<PoliticCard> chosenPoliticCards){
		
		this.chosenPoliticCards = chosenPoliticCards;
		
	}
	
	
	/**
	 * It's the constructor of the class
	 * @param region the region in which I want to buy the Business Permit Tile 
	 * @param tilePosition the position of the Tile I want to buy (it's just an index to chose which tile the Players wants )
	 * @param choosenPoliticCards  the array list of Politic cards chosen by the Player to satisfy the balcony of the chosen region
	 * @param deck deck of politic Cards
	 * @throws InvalidActionException if chosen cards don't match balcony
	 * @throws NullPointerException if region, chosenPoliticCards or deck are null.
	 * @throws IllegalArgumentException if tile position is lower than zero.
	 */
	public PermitTileAction(Region region,int tilePosition,List<PoliticCard> chosenPoliticCards,CardDeck<PoliticCard> deck) throws InvalidActionException{
		
		Preconditions.checkNotNull(region, "region can't be null");
		Preconditions.checkNotNull(chosenPoliticCards, "chosenPoliticCards can't be null");
		Preconditions.checkNotNull(deck, "deck can't be null");
		Preconditions.checkArgument(tilePosition >= 0);
		
		this.region=region;
		this.tilePosition=tilePosition;
		this.chosenPoliticCards=chosenPoliticCards;	
		this.deck=deck;
		
		List<PoliticCard> cardsToDiscard = new ArrayList<>();
		
		cardsToDiscard = cardsMatchBalcony(region.getBalcony());
		
		chosenPoliticCards.clear();
		chosenPoliticCards.addAll(cardsToDiscard);
		
	}
	
	/**
	 * Returns the list of cards (sublist of PoliticCards given by constructor to the action) matching
	 * the councillor colors on the given balcony.
	 * @param balcony Balcony to match the cards with
	 * @return list of cards (sublist of PoliticCards given by constructor to the action) matching
	 * the councillor colors on the given balcony
	 * @throws InvalidActionException if there aren't cards matching the balcony.
	 */
	public List<PoliticCard> cardsMatchBalcony(Balcony balcony) throws InvalidActionException{
		
		ArrayList<PoliticCard> cardsToDiscard = new ArrayList<>();
		
		for(int i=0;i < balcony.getCouncil().size();i++) 
			for(int j=0;j<chosenPoliticCards.size();j++)
			{
				if(chosenPoliticCards.get(j).sameCol(balcony.getCouncillor(i).getColor())
						&& !chosenPoliticCards.get(j).isAllColors()){
						cardsToDiscard.add(chosenPoliticCards.get(j));
						chosenPoliticCards.remove(chosenPoliticCards.get(j)); 
						break;
				}
			}
		
		for(PoliticCard card : chosenPoliticCards)
			if(card.isAllColors())
				cardsToDiscard.add(card);
		
		if(cardsToDiscard.isEmpty())
			throw new InvalidActionException("Chosen cards don't match balcony!");
		
		return cardsToDiscard;		
	}
	
	
	/**
	 * It checks if the politic cards chosen by the Player will satisfy the council of the chosen balcony,
	 * it puts the valid politic cars in the discardPile,
	 * it decrements the number Player's coins depending on the number of councillors satisfied,
	 * it gives the tile to the Player,
	 * it puts a new tile on the region,
	 * it removes a main action from the set of the current turn calling the super act method
	 * @param player the Player who performs the Action
	 * @return a new BonusUpdater, in this way, the bonuses eventually gained will be activate
	 * @throws NullPointerException if action is not correctly initialised.
	 */
	@Override
	public BonusActionUpdater act(Player player){
		
		int decValue=0;
		
		try{
			decValue = coinsToPay();
		} catch (InvalidActionException e) {
			Logger.getLogger(Game.class.getName()).log(Level.WARNING, e.getMessage(), e);
		}
		
		player.getCoins().decrement(decValue);
		player.discardPoliticCards(chosenPoliticCards);
		deck.getDiscardPile().addAll(chosenPoliticCards);
		BusinessPermitTile tile =region.getTile(tilePosition);
		player.takeTile(tile);
		region.changeTile(tilePosition); 
		
		super.act(player);
		
		return new BonusActionUpdater(tile.getBonuses());

	}
	
	/**
	 * It calculates the number of the coins needed to perform the PermitTileAction: it depends on the number of correct politic
	 * cards chosen by the player.
	 * @return the number of coins to be payed
	 * @throws InvalidActionException : it the player has chosen 0 politic card 
	 */
	public int coinsToPay() throws InvalidActionException{
		
		int index = 4;
		
		index -= chosenPoliticCards.size();
		
		switch(index){ 
			case 0: 
				 return 0;
			case 1: 
				 return 4;
			case 2: 
				 return 7;
			case 3: 
				return 10;
		default: throw new InvalidActionException("Number of cards to play: min 1 , max 4");
	}
		
	}

}
