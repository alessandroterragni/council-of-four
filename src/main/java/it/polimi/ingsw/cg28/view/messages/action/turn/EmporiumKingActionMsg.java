package it.polimi.ingsw.cg28.view.messages.action.turn;

import java.awt.Color;
import java.util.HashSet;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.controller.actions.Action;
import it.polimi.ingsw.cg28.controller.actions.ActionFactory;
import it.polimi.ingsw.cg28.exception.InvalidActionException;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.tmodel.TMap;
import it.polimi.ingsw.cg28.tmodel.TObjectFactory;
import it.polimi.ingsw.cg28.view.Painter;

/**
 * ActionMsg to make a request to perform a EmporiumKing action. 
 * @author Alessandro, Mario
 * @see TurnActionMsg
 */
public class EmporiumKingActionMsg extends MainActionMsg{

	private static final long serialVersionUID = -2041212453786570453L;
	private TMap map;
	private Color[] politicCards;
	private String[] codes;
	private final String[] codesRequest;
	private int numberOfPoliticCards;
	private int townNumber;
	private final String name;
	
	/**
	 * Constructor of the class. Sets need code to true.
	 * @param player - PlayerID making the request.
	 * @throws NullPointerException if player parameter is null.
	 */
	public EmporiumKingActionMsg(PlayerID player) {
		super(player);
		this.setNeedCode(true);
		codes = new String[2];
		codesRequest = new String[2];
		codesRequest[0] = "Insert the town in which you want to move the king: \n";
		codesRequest[1] = "Insert the numbers of the politic cards you want to use (separated by space): \n";
		name = "Build an emporium with the help of the king";
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
	 * Takes information from the model about towns on the map and player's politic card hand.
	 * @param model - ModelStatus model of the game 
	 * @throws InvalidActionException if the player hasn't politic card.
	 */
	@Override
	public void fill(ModelStatus model) throws InvalidActionException {
		
		Preconditions.checkNotNull(model, "The model to refer to can't be null.");
		
		numberOfPoliticCards = model.getPlayer(this.getPlayerID()).getPoliticCardsHand().size();
		
		if(numberOfPoliticCards < 1)
			throw new InvalidActionException("Not enough Politic Cards to complete the action");
		
		townNumber = model.getMap().getTownList().size();
		
		TObjectFactory factory = new TObjectFactory();
		map = factory.createTMap(model.getMap());
		politicCards = new Color[numberOfPoliticCards];
		
		for(int i=0;i<numberOfPoliticCards;i++){
			politicCards[i]= model.getPlayer(this.getPlayerID()).getCard(i).getHouseColor();
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String[] getCodesRequest(){
		return codesRequest;
	}
	
	/**
	 * {@inheritDoc} The EmporiumKingActionMsg needs two codes: the first is the town
	 * where the player wants to move king and build the emporium, the second 
	 * is a String with codes of politic cards (separated by white space) 
	 * the player wants to use to match the king balcony and perform the action.
	 * @return False and doesn't set the codes if any of the codes inserted doesn't
	 * match with any towns/politic card available to be chosen. True if codes are accepted and set.
	 */
	@Override
	public boolean setCodes(String[] codes) {
		
		Preconditions.checkNotNull(codes, "Can't set any null code.");
		
		int code0;
		boolean match;
		
		try{
			
			code0 = Integer.parseInt(codes[0]);
			match = code0 > 0 && code0 <= townNumber;

			String[] checkCardscodes = codes[1].split(" ");
			HashSet<String> setCodes = new HashSet<>();
			
			for(String code :checkCardscodes)
				   setCodes.add(code);

			for(String code : setCodes)
				   if (Integer.parseInt(code) < 1 || Integer.parseInt(code) > numberOfPoliticCards){
				    match = false;
			}
				
		} catch (NumberFormatException e) {
			return false;
		}
		
		if (match)
		{	
			this.codes = codes;
			this.setNeedCode(false);
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
	 * If set, returns the townCode set.
	 * @return townCode
	 * @throws NumberFormatException if message codes are not set
	 */
	public int getTownCode() {
		return Integer.parseInt(codes[0]); 
	}
	
	/**
	 * If set, returns the politic cards codes set.
	 * @return Array of codes related to politic card codes set
	 * @throws NumberFormatException if message codes are not set
	 */
	public String[] getChosenPoliticCardsCode() {
		return codes[1].split(" ");
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
	 * Returns the game map containing also king balcony
	 * @return the game map
	 */
	public TMap getMap() {
		return map;
	}

	/**
	 * Returns player's politic cards hand.
	 * @return player's politic cards hand
	 */
	public Color[] getPoliticCards() {
		return politicCards;
	}

	
}
