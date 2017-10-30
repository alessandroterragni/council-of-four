package it.polimi.ingsw.cg28.view.messages.action.bonus;

import it.polimi.ingsw.cg28.controller.ActionMsgHandlerInterface;
import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.view.messages.action.turn.TurnActionMsg;

/**
 * Abstract Class to represent an ActionMsg related to a bonus action.
 * Allows a user to interact and to choose between bonus choices.
 * @author Mario
 * @see TurnActionMsg
 */
public abstract class BonusActionMsg extends TurnActionMsg{

	private static final long serialVersionUID = 5175637917395965769L;

	/**
	 * Constructor of the class. Set NeedCode to true.
	 * @param player - PlayerID making the request.
	 * @throws NullPointerException if player parameter is null.
	 */
	public BonusActionMsg(PlayerID player){
		
		super(player);
		this.setNeedCode(true);
		
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
	public abstract String[] getCodesRequest();
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract boolean setCodes(String[] codes);
	
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
	
}
