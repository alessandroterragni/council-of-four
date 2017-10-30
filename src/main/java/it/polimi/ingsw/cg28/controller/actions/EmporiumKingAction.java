/**
 * 
 */
package it.polimi.ingsw.cg28.controller.actions;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import com.google.common.base.Preconditions;
import it.polimi.ingsw.cg28.controller.Game;
import it.polimi.ingsw.cg28.controller.updaters.BuildUpdater;
import it.polimi.ingsw.cg28.exception.InvalidActionException;
import it.polimi.ingsw.cg28.model.Balcony;
import it.polimi.ingsw.cg28.model.GameMap;
import it.polimi.ingsw.cg28.model.Player;
import it.polimi.ingsw.cg28.model.PoliticCard;
import it.polimi.ingsw.cg28.model.Town;
import it.polimi.ingsw.cg28.model.decks.CardDeck;

/**
 * It's a Main Action that allows the player to build an emporium by sending the king in that city
 * @author Alessandro
 *
 */
public class EmporiumKingAction extends MainAction {
	
	private GameMap map;
	private Town town;
	private List<PoliticCard> chosenPoliticCards;
	private CardDeck<PoliticCard> deck;
	
	/**
	 * It's the constructor of the class.
	 * @param town the town in which I want to send the king and build an emporium 
	 * @param balcony the balcony of the king 
	 * @param choosenPoliticCards  the arrayList of politic cards chosen by the Player to satisfy the council of the king balcony
	 * @param deck deck of politic cards
	 * @throws InvalidActionException 
	 * @throws NullPointerException if one of the parameter is null
	 */
	public EmporiumKingAction(GameMap map, Town town,Balcony balcony, List<PoliticCard> choosenPoliticCards,CardDeck<PoliticCard> deck) throws InvalidActionException {
		
		Preconditions.checkNotNull(map, "Map can't be null");
		Preconditions.checkNotNull(town, "deck can't be null");
		Preconditions.checkNotNull(balcony, "Balcony can't be null");
		Preconditions.checkNotNull(choosenPoliticCards, "chosenPolitCards can't be null");
		Preconditions.checkNotNull(deck, "deck can't be null");
		
		this.map = map;
		this.chosenPoliticCards= choosenPoliticCards;
		this.town=town;
		this.deck=deck;
		
		PermitTileAction action = new PermitTileAction(chosenPoliticCards);
		List<PoliticCard> cardsToDiscard = action.cardsMatchBalcony(balcony);
		
		choosenPoliticCards.clear();
		choosenPoliticCards.addAll(cardsToDiscard);
			
	}
	
	
	/**
	 * It checks if the politic cards chosen by the Player will satisfy the council of the chosen balcony,
	 * it puts the valid politic cars in the discardPile,
	 * it decrements the number Player's assistants depending on the number of emporiums already built in the chosen city by other players
	 * it decrements the number Player's coins depending on the number of councillors satisfied, 
	 * it decrements the number Player's coins by 2 for each town crossed by the king to reach its destination,
	 * it builds an emporium in the chosen city,
	 * it removes a Main action from the set of the current turn calling the super act method.
	 * @param player the Player who performs the Action
	 * @return a new BuildUpdater, in this way, the bonuses eventually gained will be activated
	 * @throws NullPointerException if the player passed is null
	 */
	@Override
	public BuildUpdater act(Player player) {
		
		Preconditions.checkNotNull(player, "Player can't be null");
		
		try{
			player.getCoins().decrement(coinsToPay());
		} catch (InvalidActionException e){
			Logger.getLogger(Game.class.getName()).log(Level.WARNING, e.getMessage(), e);
		}
			
	    
		int decrementValue= town.emporiumNumber();
		player.getAssistants().decrement(decrementValue);
		player.buildEmporium();
		
		player.discardPoliticCards(chosenPoliticCards);
		
		deck.getDiscardPile().addAll(chosenPoliticCards);
		
		map.addEmporium(player.getID(), town);
		
		for(Town t : map.getTownList())
			if(t.isKingHere()){
			
				map.setKingHere(false, t);
				break;
				
			}
		map.setKingHere(true, town);
	
		super.act(player);
		
		return new BuildUpdater(town);
				
	}
	
	/**
	 * It calculates the number of coins needed to perform the EmporiumKingAction: it depends on the number of correct politic
	 * cards chosen by the player and on the number of cities crossed by the king to reach the chosen city
	 * @return the number of coins to be payed
	 * @throws InvalidActionException : it the player has chosen 0 politic card or more than 5 cards
	 */
	public int coinsToPay() throws InvalidActionException{
		
		int index = 4;
		
		index -= chosenPoliticCards.size();
		
		int coins;
		
		switch(index){
			case 0: 
				 coins = 0;
				 break;
			case 1: 
				 coins = 4;
				 break;
			case 2: 
				 coins = 7;
				 break;
			case 3: 
				 coins = 10;
				 break;
			default: throw new InvalidActionException("Number of cards to play: min 1 , max 4");
		}
		
		return coins + kingMoveCost();
		
	}
	
	
	/**
	 * Method that uses Dijkstra algorithm to calculate the number of cities crossed by the king to reach a specified city.
	 * @return (the number of cities calculated )*2 coins to pay for king movements.
	 */
	private int kingMoveCost(){
		
		Town start = null;
		for(Town t : map.getTownList()){
			if(t.isKingHere()){
				start = t;
				break;
			}
		}
		
		DijkstraShortestPath<Town, DefaultEdge> dijkstra =
				new DijkstraShortestPath<>(map.getTownNetwork(), start, town);

		Double d = new Double(2 * dijkstra.getPathLength());
		
		return d.intValue();
	}

}
