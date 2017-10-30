package it.polimi.ingsw.cg28.view.messages.action.bazaar;

import it.polimi.ingsw.cg28.controller.ActionMsgHandlerInterface;
import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.view.messages.action.turn.TurnActionMsg;

/**
 * Abstract Class to represent an ActionMsg related to a bazaar action.
 * @author Mario
 * @see TurnActionMsg
 */
public abstract class BazaarActionMsg extends TurnActionMsg {

	private static final long serialVersionUID = 3594360937018993962L;
	
	/**
	 * Constructor of the class. Sets need code to true.
	 * @param player - PlayerID making the request.
	 * @throws NullPointerException if player parameter is null.
	 */
	public BazaarActionMsg(PlayerID player) {
		super(player);
		setNeedCode(true);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handle(ActionMsgHandlerInterface actionMsgHandler){
		
		actionMsgHandler.handle(this);
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract String[] getCodes();
	
	/**
	 * Returns a representation by integer of codes set by the player.
	 * @return array of codes set by the player
	 * @throws NumberFormatException if codes is empty
	 */
	public int[] getCodesInt(String[] codes){
		
		int[] codesInt = new int[codes.length];
		for(int i=0; i <codes.length; i++)
			codesInt[i] = Integer.parseInt(codes[i]);
			
		return codesInt;
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract boolean setCodes(String[] codes);
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract String[] getCodesRequest();

}
