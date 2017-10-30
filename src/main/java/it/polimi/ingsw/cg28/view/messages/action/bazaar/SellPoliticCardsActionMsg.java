package it.polimi.ingsw.cg28.view.messages.action.bazaar;

import java.awt.Color;
import java.util.HashSet;
import java.util.List;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.controller.actions.Action;
import it.polimi.ingsw.cg28.controller.actions.ActionFactory;
import it.polimi.ingsw.cg28.exception.InvalidActionException;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.Player;
import it.polimi.ingsw.cg28.model.PoliticCard;
import it.polimi.ingsw.cg28.view.Painter;
import it.polimi.ingsw.cg28.view.messages.action.turn.TurnActionMsg;

/**
 * BazaarActionMsg to make a request to sell politic cards in the bazaar.
 * @author Mario
 * @see TurnActionMsg
 */
public class SellPoliticCardsActionMsg extends BazaarActionMsg {

	private static final long serialVersionUID = 1812586456213461438L;
	private Color[] politicCards;
	private int numbPoliticCards;
	private final String[] codesRequest;
	private String[] codes;
	private final String name;
	
	/**
	 * Constructor of the class.
	 * @param player - PlayerID making the request.
	 * @throws NullPointerException if player parameter is null.
	 */
	public SellPoliticCardsActionMsg(PlayerID player) {
		super(player);
		codes = new String[2];
		codesRequest = new String[2];
		codesRequest[0] = "Insert the numbers of the Politic Cards you want to sell (separated with white space):";
		codesRequest[1] = "Insert the price (COINS) you want to sell the cards for: ";
		name = "Sell Politic Cards";
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Action translate(ActionFactory actionFactory) throws InvalidActionException {
		return actionFactory.build(this);
	}
	
	/**
	 * {@inheritDoc} 
	 * Takes information from the model about player possessed politic cards.
	 * @param model - ModelStatus model of the game 
	 * @throws InvalidActionException if player doesn't have any politic card.
	 */
	@Override
	public void fill(ModelStatus model) throws InvalidActionException {
		
		Preconditions.checkNotNull(model, "The model to refer to can't be null.");
		
		Player player = model.getPlayer(getPlayerID());
		
		List<PoliticCard> hand = player.getPoliticCardsHand();
		politicCards = new Color[hand.size()];
		
		for(int i=0; i < hand.size(); i++){
			politicCards[i] = hand.get(i).getHouseColor();
		}
		
		numbPoliticCards = hand.size();
		
		if(numbPoliticCards < 1)
			throw new InvalidActionException("Not enough Politic Cards to complete the action");
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String[] getCodesRequest(){
		return codesRequest;
	}
	
	/**
	 * {@inheritDoc} The SellPoliticCardsActionMsg needs two codes, the first is a String with codes of permit tiles 
	 * (separated by white space) the player wants to sell and the second is the price the player wants to sell the product for.
	 * @return False and doesn't set the codes if price chosen is lower than zero number or permit tiles codes doesn't 
	 * match any permit tiles owned by the player.
	 * True if codes are accepted and set.
	 */
	@Override
	public boolean setCodes(String[] codes) {
		
		Preconditions.checkNotNull(codes, "Can't set any null code.");
		
		String[] checkCardsCodes = codes[0].split(" ");
		boolean match = true;
		
		try{
			
			HashSet<String> setCodes = new HashSet<>();
			
			for(String code : checkCardsCodes)
				setCodes.add(code);
			
			for(String code : setCodes){
				if (Integer.parseInt(code) < 1 || Integer.parseInt(code) > numbPoliticCards){
					match = false;
				}
			}
			
			if (Integer.parseInt(codes[1]) < 0)
				match = false;
			
			if (match){
				
				String[] correctCodes = new String[setCodes.size() + 1];
				int i=0;
				for(String code : setCodes){
					correctCodes[i] = code;
					i++;
				}
				correctCodes[i] = codes[1];	
				this.codes = correctCodes;
				setNeedCode(false);
			}
		} catch (NumberFormatException e) {
			return false;
		}
			
		return match;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String[] getCodes(){
		return codes;
	}
	
	/**
	 * Returns the integer relative to the price the player wants to sell the product for.
	 * @return integer relative the price the player wants to sell the product for
	 * @throws NumberFormatException if message codes are not set
	 */
	public int getPrice(){
		return Integer.parseInt(codes[codes.length - 1]);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void getShowChoices(Painter painter) {
		painter.paint(this);
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return name;
	}
	
	/**
	 * Returns an array of {@link java.awt.Color} representing politic cards owned by the player.
	 * @return array of {@link java.awt.Color} representing politic cards owned by the player
	 */
	public Color[] getPoliticCards() {
		return politicCards;
	}
}
