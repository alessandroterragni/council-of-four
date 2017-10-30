package it.polimi.ingsw.cg28.view.messages.action.bazaar;

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
 * BazaarActionMsg to make a request to sell permit tiles in the bazaar.
 * @author Mario
 * @see TurnActionMsg
 */
public class SellPermitTilesActionMsg extends BazaarActionMsg {

	private static final long serialVersionUID = -3408272752520632339L;
	private TBusinessPermitTile[] tiles;
	private int numbPermitTiles;
	private final String[] codesRequest;
	private String[] codes;
	private final String name;
	
	/**
	 * Constructor of the class.
	 * @param player - PlayerID making the request.
	 * @throws NullPointerException if player parameter is null.
	 */
	public SellPermitTilesActionMsg(PlayerID player) {
		super(player);
		codes = new String[2];
		codesRequest = new String[2];
		codesRequest[0] = "Insert the number of tiles you want to sell (separated with white space):";
		codesRequest[1] = "Insert the price (COINS) you want to sell the tiles for: ";
		
		name = "Sell permit tiles";
		
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
	 * Takes information from the model about player possessed tiles.
	 * @param model - ModelStatus model of the game 
	 * @throws InvalidActionException if player doesn't have any tile.
	 */
	@Override
	public void fill(ModelStatus model) throws InvalidActionException {
		
		Preconditions.checkNotNull(model, "The model to refer to can't be null.");
		
		Player player = model.getPlayer(getPlayerID());
		
		TObjectFactory tObjectFactory = new TObjectFactory();
		
		List<BusinessPermitTile> possessedTiles =	player.getPossessedTiles();
		tiles = new TBusinessPermitTile[possessedTiles.size()];
		
		for(int i=0; i < possessedTiles.size(); i++){
			tiles[i] = tObjectFactory.createTBusinessPermitTile(possessedTiles.get(i));
		}
		
		numbPermitTiles = possessedTiles.size();
		
		if(numbPermitTiles < 1)
			throw new InvalidActionException("Not enough Permit Tiles to complete the action");
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String[] getCodesRequest(){
		return codesRequest;
	}
	
	/**
	 * {@inheritDoc} The SellPermitTilesActionMsg needs two codes, the first is a String with codes of politic cards 
	 * (separated by white space) the player wants to sell and the second is the price the player wants to sell the product for.
	 * @return False and doesn't set the codes if price chosen is lower than zero number or politic cards codes doesn't 
	 * match any politic card owned by the player.
	 * True if codes are accepted and set.
	 */
	@Override
	public boolean setCodes(String[] codes) {
		
		Preconditions.checkNotNull(codes, "Can't set any null code.");
		
		String[] checkPermitCodes = codes[0].split(" ");
		boolean match = true;
		
		try{
		
			HashSet<String> setCodes = new HashSet<>();
			
			for(String permitCode : checkPermitCodes)
				setCodes.add(permitCode);
			
			for(String permitCode : setCodes)
				if (Integer.parseInt(permitCode) < 1 || Integer.parseInt(permitCode) > numbPermitTiles)
					match = false;
			
			if (Integer.parseInt(codes[1]) < 0)
				match = false;
			
			if (match){
				
					String[] correctCodes = new String[setCodes.size() + 1];
					int i=0;
					for(String permitCode : setCodes){
						correctCodes[i] = permitCode;
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
	 * Returns an array of TBusinessPermitTile representing tiles owned by the player.
	 * @return array of TBusinessPermitTile representing tiles owned by the player
	 */
	public TBusinessPermitTile[] getTiles() {
		return tiles;
	}

}
