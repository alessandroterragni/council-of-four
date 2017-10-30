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
import it.polimi.ingsw.cg28.model.Player;
import it.polimi.ingsw.cg28.tmodel.TBusinessPermitTile;
import it.polimi.ingsw.cg28.tmodel.TObjectFactory;
import it.polimi.ingsw.cg28.view.Painter;
import it.polimi.ingsw.cg28.view.messages.action.turn.TurnActionMsg;

/**
 * BonusActionMsg to interact with a ReusePermitBonus.
 * @author Mario, Alessandro
 * @see TurnActionMsg
 */
public class ReusePermitBonusActionMsg extends BonusActionMsg {

	private static final long serialVersionUID = -4197592054260433812L;
	private List<TBusinessPermitTile> tTiles;
	private int numbReusePermit;
	private String[] codes;
	private final String[] codesRequest;
	private int numbTiles;
	private final String name;
	
	/**
	 * Constructor of the class.
	 * @param numbReusePermit - number of permit tiles possessed or discarded the player can choose to retrieve bonuses of.
	 * @param player - PlayerID making the request.
	 * @throws NullPointerException if player parameter is null.
	 */
	public ReusePermitBonusActionMsg(int numbReusePermit, PlayerID player) {
		
		super(player);
		Preconditions.checkArgument(numbReusePermit > 0, "The number of tiles to be reused must be positive.");
		
		tTiles = new ArrayList<>();
		this.numbReusePermit = numbReusePermit;
		codes = new String[numbReusePermit];
		codesRequest = new String[numbReusePermit];
		
		for(int i = 0; i< numbReusePermit; i++)
			codesRequest[i] = "Insert number of " + ordinal(i) + "bonus (related tile) you want to reuse: \n";
		
		name = "Reuse permit bonus";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Action translate(ActionFactory actionFactory) throws InvalidActionException {
		return actionFactory.build(this);
	}
	
	/**
	 * Returns a list of player's possessed and used tiles.
	 * @param model - ModelStatus model of the game
	 * @return list of player's possessed and used tiles
	 */
	public List<BusinessPermitTile> checkTiles(ModelStatus model){
		
		Player player = model.getPlayer(getPlayerID());
		List<BusinessPermitTile> tiles = new ArrayList<>();
		
		tiles.addAll(player.getPossessedTiles());
		tiles.addAll(player.getUsedTiles());
		
		return tiles;

	}
	
	/**
	 * {@inheritDoc} 
	 * Takes information from the model about tiles between which the player can choose.
	 * If the number of tiles satisfying bonus requirements is less than the number set as numbReusePermit,
	 * updates the number of tiles the player can choose to retrieve bonuses of.
	 * @param model - ModelStatus model of the game 
	 * @throws InvalidActionException if there aren't tiles available to satisfy bonus requirements
	 */
	@Override
	public void fill(ModelStatus model) throws InvalidActionException {
		
		Preconditions.checkNotNull(model, "The model to refer to can't be null.");
		
		List<BusinessPermitTile> tiles = checkTiles(model);
		numbTiles = tiles.size();
		
		if(numbTiles < 1)
			throw new InvalidActionException("Impossible to assign bonus: no tile "
					+ "available to satisfy bonus requirements");
		
		TObjectFactory tObjectFactory = new TObjectFactory();
		
		for(int i=0;i<numbTiles;i++){
			tTiles.add(tObjectFactory.createTBusinessPermitTile(tiles.get(i)));
		}
		
		if(tiles.size() < numbReusePermit){
			codes = new String [tiles.size()];
			numbReusePermit = tiles.size();
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String[] getCodesRequest() {
		return codesRequest;
	}
	
	/**
	 * {@inheritDoc} The ReusePermitBonusActionMsg needs as many code as set as numbReusePermit.
	 * Each code is related to a tile the player wants to retrieve bonuses of.
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
				
			 if(code < 1 || code > numbTiles)
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
	 * Returns tiles between which the player can choose.
	 * @return tiles between which the player can choose
	 */
	public List<TBusinessPermitTile> getTiles() {
		return tTiles;
	}
	
	/**
	 * Returns number of tiles the player can choose to retrieve bonuses of.
	 * @return number of tiles the player can choose to retrieve bonuses of.
	 */
	public int getNumbReusePermit() {
		return numbReusePermit;
	}
	
	
}
