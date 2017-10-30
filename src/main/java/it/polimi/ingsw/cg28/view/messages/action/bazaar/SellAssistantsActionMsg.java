package it.polimi.ingsw.cg28.view.messages.action.bazaar;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.controller.actions.Action;
import it.polimi.ingsw.cg28.controller.actions.ActionFactory;
import it.polimi.ingsw.cg28.exception.InvalidActionException;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.Player;
import it.polimi.ingsw.cg28.view.Painter;
import it.polimi.ingsw.cg28.view.messages.action.turn.TurnActionMsg;

/**
 * BazaarActionMsg to make a request to sell assistants in the bazaar.
 * @author Mario
 * @see TurnActionMsg
 */
public class SellAssistantsActionMsg extends BazaarActionMsg{
	
	private static final long serialVersionUID = -635851500816239339L;
	private int numbAssistants;
	private final String[] codesRequest;
	private String[] codes;
	private final String name;
	
	/**
	 * Constructor of the class.
	 * @param player - PlayerID making the request.
	 * @throws NullPointerException if player parameter is null.
	 */
	public SellAssistantsActionMsg(PlayerID player) {
		super(player);
		codes = new String[2];
		codesRequest = new String[2];
		codesRequest[0] = "Insert the number of assistants you want to sell:";
		codesRequest[1] = "Insert the price (COINS) you want to sell the assistants for: ";
		
		name = "Sell assistants";
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
	 * Takes information from the model about player number of assistants.
	 * @param model - ModelStatus model of the game 
	 * @throws InvalidActionException if player doesn't have any assistant.
	 */
	@Override
	public void fill(ModelStatus model) throws InvalidActionException{
		
		Preconditions.checkNotNull(model, "The model to refer to can't be null.");
		
		Player player = model.getPlayer(getPlayerID());
		numbAssistants = player.getAssistants().getValue();
		
		if (numbAssistants < 1)
			throw new InvalidActionException("Not enough assistants");
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String[] getCodesRequest(){
		return codesRequest;
	}
	
	/**
	 * {@inheritDoc} The SellAssistantsActionMsg needs two codes, the first is the number of assistants the player
	 * wants to sell and the second is the price the player wants to sell the product for.
	 * @return False and doesn't set the codes if price chosen is lower than zero number or the number
	 * of assistants chosen is lower than zero or greater than the number of assistants owned by the player.
	 * True if codes are accepted and set.
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
		
		boolean match = code0 > 0 && code0 <= numbAssistants 
				&& code1 > 0;
		
		if (match){
			this.codes = codes;
			setNeedCode(false);
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
	 * Returns the integer relative to the number of assistants the player wants to sell.
	 * @return integer relative to the number of assistants the player wants to sell
	 * @throws NumberFormatException if message codes are not set
	 */
	public int getNumbAssistantsToSell(){
		return Integer.parseInt(codes[0]);
	}
	
	/**
	 * Returns the integer relative to the price the player wants to sell the product for.
	 * @return integer relative the price the player wants to sell the product for
	 * @throws NumberFormatException if message codes are not set
	 */
	public int getPrice(){
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
	 * Returns the number of assistants owned by the player.
	 * @return number of assistants owned by the player.
	 * 		   0 if the message is not filled.
	 */
	public int getNumbAssistants() {
		return numbAssistants;
	}

}
