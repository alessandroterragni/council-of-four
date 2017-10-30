package it.polimi.ingsw.cg28.view.messages.action.turn;

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
 * ActionMsg to make a request to perform a ChangeTile action. 
 * @author Alessandro, Mario
 * @see TurnActionMsg
 */
public class ChangeTileActionMsg extends QuickActionMsg {

	private static final long serialVersionUID = -2296438085359766158L;
	private TRegion[] regions;
	private String[] codes;
	private final String[] codesRequest;
	private int regionNumber;
	private final String name;
	
	/**
	 * Constructor of the class. Sets need code to true.
	 * @param player - PlayerID making the request.
	 * @throws NullPointerException if player parameter is null.
	 */
	public ChangeTileActionMsg(PlayerID player) {
		
		super(player);
		this.setNeedCode(true);
		codes = new String[1];
		codesRequest = new String[1]; 
		codesRequest[0] = "Insert the region number (displayed) you want to change tiles of: \n";
		
		name = "Change building permit tiles";
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
	 * Takes information from the model about regions and related uncovered tiles.
	 * @param model - ModelStatus model of the game 
	 */
	@Override
	public void fill(ModelStatus model) {
		
		Preconditions.checkNotNull(model, "The model to refer to can't be null.");
		
		regions = new TRegion[model.getRegions().length];
		regionNumber = model.getRegions().length;
		TObjectFactory tObjectFactory = new TObjectFactory();
	
		for(int i=0;i<model.getRegions().length;i++){
			regions[i]= tObjectFactory.createTRegion(model.getRegion(i));
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
	 * {@inheritDoc} The ChangeTileActionMsg needs only one code to determine the
	 * region the player wants to change tiles of.
	 * @return False and doesn't set the codes if the code inserted doesn't
	 * match with any tile available to be chosen. True if codes are accepted and set.
	 */
	@Override
	public boolean setCodes(String[] codes) {
		
		Preconditions.checkNotNull(codes, "The codes to set can't be null.");
		
		int code;
		
		try{
			code = Integer.parseInt(codes[0]);
		} catch (NumberFormatException e) {
			return false;
		}
		
		boolean match = (code > 0) && (code <= regionNumber);
		
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
	public TRegion[] getRegion() {
		return regions;
	}
	
}
