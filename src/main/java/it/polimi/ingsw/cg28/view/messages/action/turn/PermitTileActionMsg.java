package it.polimi.ingsw.cg28.view.messages.action.turn;

import java.awt.Color;
import java.util.HashSet;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.controller.actions.Action;
import it.polimi.ingsw.cg28.controller.actions.ActionFactory;
import it.polimi.ingsw.cg28.exception.InvalidActionException;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.tmodel.TObjectFactory;
import it.polimi.ingsw.cg28.tmodel.TRegion;
import it.polimi.ingsw.cg28.view.Painter;

/**
 * ActionMsg to make a request to perform a PermitTile action. 
 * @author Alessandro, Mario
 * @see TurnActionMsg
 */
public class PermitTileActionMsg extends MainActionMsg {

	private static final long serialVersionUID = -555991202144640070L;
	private TRegion[] regions;
	private Color[] politicCards;
	private String[] codes;
	private final String[] codesRequest;
	private int regionsSize;
	private int uncoveredTileNumb;
	private int numberOfPoliticCards;
	private final String name;
	
	
	/**
	 * Constructor of the class. Sets need code to true.
	 * @param player - PlayerID making the request.
	 * @throws NullPointerException if player parameter is null.
	 */
	public PermitTileActionMsg(PlayerID player) {
		super(player);
		this.setNeedCode(true);
		codes = new String[3];
		codesRequest = new String[3];
		codesRequest[0] = "Insert the region number: \n";
		codesRequest[1] = "Insert the index number of the tile you want to buy: \n";
		codesRequest[2] = "Insert the numbers of the politic cards you want to use (separated by white space): \n";
		name = "Acquire a business permit tile";
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
	 * Takes information from the model about regions(balconies and uncovered tiles)
	 * and player's politic card.
	 * @param model - ModelStatus model of the game 
	 * @throws InvalidActionException if the player hasn't politic cards
	 */
	@Override
	public void fill(ModelStatus model) throws InvalidActionException{
		
		Preconditions.checkNotNull(model, "The model to refer to can't be null.");
		
		regionsSize = model.getRegions().length;
		uncoveredTileNumb = model.getRegion(0).getUnconveredNum();
		numberOfPoliticCards = model.getPlayer(this.getPlayerID()).getPoliticCardsHand().size();
		
		if(numberOfPoliticCards < 1)
			throw new InvalidActionException("Not enough Politic Cards to complete the action");

		regions = new TRegion[regionsSize];
		politicCards = new Color[numberOfPoliticCards];
		
		TObjectFactory factory = new TObjectFactory();
		
		for(int i=0;i<regionsSize;i++)	
			regions[i] = factory.createTRegion(model.getRegion(i));
		
		for(int i=0;i<numberOfPoliticCards;i++)
			politicCards[i]= model.getPlayer(this.getPlayerID()).getCard(i).getHouseColor();
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String[] getCodesRequest(){
		return codesRequest;
	}
	
	/**
	 * {@inheritDoc} The PermitTileActionMsg needs three codes: the first and the second
	 * related  respectively to the region/number of tile the player wants to take, the third 
	 * is a String with codes of politic cards (separated by white space) 
	 * the player wants to use to match the region balcony and perform the action.
	 * @return False and doesn't set the codes if any of the codes inserted doesn't
	 * match with any region/tile/politic card available to be chosen. True if codes are accepted and set.
	 */
	@Override
	public boolean setCodes(String[] codes) {
		
		Preconditions.checkNotNull(codes, "Can't set any null code.");
		
		int code0;
		int code1;
		boolean match;
		
		try{
			code0 = Integer.parseInt(codes[0]);
			code1 = Integer.parseInt(codes[1]);	
		
			match = code0 > 0 && code0 <=regionsSize
				&& code1 > 0 && code1 <=uncoveredTileNumb;

			String[] checkCardscodes = codes[2].split(" ");
			
			HashSet<String> setCodes = new HashSet<>();
		
			for(String code :checkCardscodes)
			   setCodes.add(code);

			for(String code : setCodes)
			   if (Integer.parseInt(code) < 1 || Integer.parseInt(code) > numberOfPoliticCards)
				   match = false;
			
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
	 * If set, returns the regionCode set.
	 * @return regionCode
	 * @throws NumberFormatException if message codes are not set
	 */
	public int getRegionCode() {
		return Integer.parseInt(codes[0]);
	}
	
	
	/**
	 * If set, returns the tilePositionCode set.
	 * @return tilePositionCode
	 * @throws NumberFormatException if message codes are not set
	 */
	public int getTilePositionCode() {
		return Integer.parseInt(codes[1]);
	}
	
	/**
	 * If set, returns the politic cards codes set.
	 * @return Array of codes related to politic card codes set
	 * @throws NumberFormatException if message codes are not set
	 */
	public String[] getChosenPoliticCardCodes() {
		return codes[2].split(" ");
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
	 * Returns map regions.
	 * @return the map regions
	 */
	public TRegion[] getRegions() {
		return regions;
	}

	/**
	 * Returns player's politic cards hand.
	 * @return player's politic cards hand
	 */
	public Color[] getPoliticCards() {
		return politicCards;
	}
	
}
