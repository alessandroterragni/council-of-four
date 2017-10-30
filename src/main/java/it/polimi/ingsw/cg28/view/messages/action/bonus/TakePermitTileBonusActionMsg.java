/**
 * 
 */
package it.polimi.ingsw.cg28.view.messages.action.bonus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.controller.actions.Action;
import it.polimi.ingsw.cg28.controller.actions.ActionFactory;
import it.polimi.ingsw.cg28.exception.InvalidActionException;
import it.polimi.ingsw.cg28.model.BusinessPermitTile;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.Region;
import it.polimi.ingsw.cg28.tmodel.TBusinessPermitTile;
import it.polimi.ingsw.cg28.tmodel.TObjectFactory;
import it.polimi.ingsw.cg28.view.Painter;
import it.polimi.ingsw.cg28.view.messages.action.turn.TurnActionMsg;

/**
 * BonusActionMsg to interact with a TakePermitTileBonus.
 * @author Mario, Alessandro
 * @see TurnActionMsg
 */
public class TakePermitTileBonusActionMsg extends BonusActionMsg {

	private static final long serialVersionUID = -358821093088897706L;
	private int numbTilesToTake;
	private List<TBusinessPermitTile> tiles;
	private String[] codes;
	private final String[] codesRequest;
	private int numbTiles;
	private final String name;
	
	/**
	 * Constructor of the class.
	 * @param numbTilesToTake - number of permit tiles uncovered the player can choose to take.
	 * @param player - PlayerID making the request.
	 * @throws NullPointerException if player parameter is null.
	 */
	public TakePermitTileBonusActionMsg(int numbTilesToTake, PlayerID player) {
		
		super(player);
		Preconditions.checkArgument(numbTilesToTake > 0, "The number of tiles to be taken must be positive.");
		
		this.numbTilesToTake = numbTilesToTake;
		tiles = new ArrayList<>();
		codes = new String[numbTilesToTake];
		codesRequest = new String[numbTilesToTake];
		
		for(int i = 0; i< numbTilesToTake; i++)
			codesRequest[i] = "Insert number of " + ordinal(i + 1) + " tile you want to reuse: \n";
		
		name = "Take permit tile bonus";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Action translate(ActionFactory actionFactory) throws InvalidActionException {
		return actionFactory.build(this);
	}
	
	/**
	 * Returns the list of uncovered regions tiles.
	 * @param model - ModelStatus model of the game
	 * @return list of uncovered regions tiles
	 */
	public List<BusinessPermitTile> checkTiles(ModelStatus model){
		
		List<BusinessPermitTile> checkedTiles = new ArrayList<>();
		
		for(Region region : model.getRegions())
			for(BusinessPermitTile tile: region.getUncovered())
				checkedTiles.add(tile);
		
		return checkedTiles;
		
	}
	
	/**
	 * {@inheritDoc} 
	 * Takes information from the model about tiles between which the player can choose (regions
	 * uncovered tiles).
	 * @param model - ModelStatus model of the game 
	 */
	@Override
	public void fill(ModelStatus model){
		
		Preconditions.checkNotNull(model, "The model to refer to can't be null,");

		tiles = new ArrayList<>();
		
		TObjectFactory factory = new TObjectFactory();
		
		for(BusinessPermitTile tile: checkTiles(model))
				tiles.add(factory.createTBusinessPermitTile(tile));
				
		numbTiles = tiles.size();
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String[] getCodesRequest() {
		return codesRequest;
	}
	
	/**
	 * {@inheritDoc} The TakePermitTileBonusActionMsg needs as many code as set as numbTilesToTake.
	 * Each code is related to a tile the player wants to take.
	 * @return False and doesn't set the codes if one of the code inserted doesn't
	 * match with any tile available to be chosen. True if codes are accepted and set.
	 */
	@Override
	public boolean setCodes(String[] codes) {
		
		Preconditions.checkNotNull(codes, "The codes to set can't be null.");
		
		HashSet<String> setCodes = new HashSet<>();
		
		for(String code : codes)
			setCodes.add(code);
		
		try{
			for(String c : setCodes){
				
				int code = Integer.parseInt(c);
				
				if(code < 1 || code> numbTiles)
					return false;
			}		
		} catch (NumberFormatException e) {
			return false;
		}
		
		this.codes = codes;
		
		return true;
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String[] getCodes() {
		return codes;
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
	 * Returns number of tiles the player can choose to take.
	 * @return number of tiles the player can choose to take.
	 */
	public int getNumbTilesToTake() {
		return numbTilesToTake;
	}
	
	/**
	 * Returns tiles between which the player can choose.
	 * @return tiles between which the player can choose
	 */
	public List<TBusinessPermitTile> getTiles() {
		return tiles;
	}

}
