package it.polimi.ingsw.cg28.view.messages.action.turn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.controller.actions.Action;
import it.polimi.ingsw.cg28.controller.actions.ActionFactory;
import it.polimi.ingsw.cg28.exception.InvalidActionException;
import it.polimi.ingsw.cg28.model.BusinessPermitTile;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.Player;
import it.polimi.ingsw.cg28.model.Town;
import it.polimi.ingsw.cg28.tmodel.TBusinessPermitTile;
import it.polimi.ingsw.cg28.tmodel.TObjectFactory;
import it.polimi.ingsw.cg28.tmodel.TTown;
import it.polimi.ingsw.cg28.view.Painter;


/**
 * ActionMsg to make a request to perform a EmporiumTile action. 
 * @author Alessandro, Mario
 * @see TurnActionMsg
 */
public class EmporiumTileActionMsg extends MainActionMsg {

	private static final long serialVersionUID = 1654514039822747930L;
	private String[] codes;
	private final String[] codesRequest;
	private List<TTown> towns;
	private TBusinessPermitTile[] tiles;
	
	private int townNumber;
	private int tileNumber;
	private final String name;
	

	/**
	 * Constructor of the class. Sets need code to true.
	 * @param player - PlayerID making the request.
	 * @throws NullPointerException if player parameter is null.
	 */
	public EmporiumTileActionMsg(PlayerID player) {
		super(player);
		this.setNeedCode(true);
		codes = new String[2];
		codesRequest = new String[2]; 
		codesRequest[0] = "Insert the number of the business permit tile you want to use: \n";
		codesRequest[1] = "Insert the number of the town you want to build an emporium in: \n";
		name = "Build an emporium using a permit tile";
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Action translate(ActionFactory actionFactory) throws InvalidActionException {
			return actionFactory.build(this);
	}
	
	public List<Town> checkTowns(ModelStatus model){
		
		Player player = model.getPlayer(getPlayerID());
		List<Town> filteredTowns = new ArrayList<>();
		
		for(BusinessPermitTile tile : player.getPossessedTiles()){
			for(String s : tile.getLetterCodes()){
				for(Town t : model.getMap().getTownList()){
					if(s.equals(t.getTownName()) && !filteredTowns.contains(t)){
						filteredTowns.add(t);
					}
				}
			}
		}
		
		return filteredTowns;

	}
	
	/**
	 * {@inheritDoc} 
	 * Takes information from the model about player's permit tiles and towns on the map 
	 * related to player's tiles letter codes.
	 * @param model - ModelStatus model of the game 
	 * @throws InvalidActionException if the player hasn't tiles
	 */
	@Override
	public void fill(ModelStatus model) throws InvalidActionException {
		
		Preconditions.checkNotNull(model, "The model to refer to can't be null.");
		
		towns = new ArrayList<>();
		tileNumber = model.getPlayer(getPlayerID()).getPossessedTiles().size();
		
		if(tileNumber < 1)
			throw new InvalidActionException("Not enough business permit tiles to complete the action");
		
		tiles = new TBusinessPermitTile[tileNumber];
		
		List<Town> filteredtowns;
		filteredtowns = checkTowns(model);
		townNumber = filteredtowns.size();
		
		TObjectFactory factory = new TObjectFactory();
		
		for(int i = 0; i < townNumber; i++){
			towns.add(factory.createTtown(filteredtowns.get(i)));
		}
		
		for(int i = 0; i < tileNumber; i++){
			this.tiles[i] =
					factory.createTBusinessPermitTile(model.getPlayer(getPlayerID()).getPossessedTiles().get(i));
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
	 * {@inheritDoc} The EmporiumTileActionMsg needs two codes: the first
	 * related to the tile the player wants to use, the second is the town 
	 * where the player wants to build the emporium.
	 * @return False and doesn't set the codes if any of the codes inserted doesn't
	 * match with any towns/tile available to be chosen. True if codes are accepted and set.
	 */
	@Override
	public boolean setCodes(String[] codes) {
		
		Preconditions.checkNotNull(codes, "Can't set any null code.");
		
		int code0;
		int code1;
		
		try{
			code0 = Integer.parseInt(codes[0]);
			code1 = Integer.parseInt(codes[1]);	
		} catch (NumberFormatException e) {
			return false;
		}
		
		boolean match = code0 > 0 && code0 <= tileNumber 
							&& code1 > 0 && code1 <= townNumber;
			
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
	 * If set, returns the tileCode set.
	 * @return tileCode
	 * @throws NumberFormatException if message codes are not set
	 */
	public int getTileCode() {
		return Integer.parseInt(codes[0]);
	}
	
	/**
	 * If set, returns the townCode set.
	 * @return townCode
	 * @throws NumberFormatException if message codes are not set
	 */
	public int getTownCode() {
		return Integer.parseInt(codes[1]);
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
	 * Returns list of towns between which the player can choose, filtered by owned tile letter codes.
	 * @return list of towns, filtered as specified.
	 */
	public List<TTown> getTowns() {
		return towns;
	}
	
	/**
	 * Returns permit tiles owned by the player.
	 * @return tiles owned by the player
	 */
	public List<TBusinessPermitTile> getTiles() {
		return Arrays.asList(tiles);
	}

	
	
}
