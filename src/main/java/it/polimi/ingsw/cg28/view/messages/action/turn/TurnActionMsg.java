/**
 * 
 */
package it.polimi.ingsw.cg28.view.messages.action.turn;


import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.ActionMsgHandlerInterface;
import it.polimi.ingsw.cg28.controller.Game;
import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.exception.InvalidActionException;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.view.Painter;
import it.polimi.ingsw.cg28.view.messages.action.ActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.ToTranslate;
import it.polimi.ingsw.cg28.view.messages.event.EventMsg;
import it.polimi.ingsw.cg28.view.messages.event.FilledMsg;

/**
 * Class that will be extended by all the ActionMsg related to game turns advancements.
 * TurnActionMsg needs to be translated to act on the model. TurnActionMsg can be filled or not and may also
 * need codes to specify the request.
 * @author Alessandro, Mario
 * @see ActionMsg
 */
public abstract class TurnActionMsg extends ActionMsg implements ToTranslate {

	private static final long serialVersionUID = -247055466013917363L;
	private boolean filled;
	private boolean needCode;
	
	/**
	 * Constructor of the class. Sets the message as not filled.
	 * @param player - PlayerID making the request.
	 * @throws NullPointerException if player parameter is null.
	 */
	public TurnActionMsg(PlayerID player) {
		
		Preconditions.checkNotNull(player);
		setPlayerID(player);
		this.filled = false;
		
	}
	
	/**
	 * Allows a concrete ActionMsgHandlerInterface visitor to visit the message.
	 * @param actionMsgHandler - Concrete ActionMsgHandlerInterface visitor 
	 */
	@Override
	public void handle(ActionMsgHandlerInterface actionMsgHandler){
			actionMsgHandler.handle(this);
	}
	
	/**
	 * Specifies how to fill the message with Model informations.
	 * @param model game Model
	 */
	public abstract void fill(ModelStatus model) throws InvalidActionException;
	
	/**
	 * Return status of filled attribute.
	 * @return filled If the message needs to be filled or not
	 */
	public boolean isFilled() {
		return filled;
	}

	/**
	 * Sets status of filled attribute
	 * @param filled - boolean value to assign to filled attribute
	 */
	public void setFilled(boolean filled) {
		this.filled = filled;
	}
	
	/**
	 * Methods to get String to specify the needed code to complete ActionMsg request.
	 * @return Array of strings containing requests, one for each code requested
	 */
	public String[] getCodesRequest(){
		Logger.getLogger(Game.class.getName()).log(Level.WARNING,"No codes for that action");
		return new String[0];
	}
	
	/**
	 * Methods to set codes to specify the ActionMsg request.
	 * @param Array of strings containing codes set by player
	 * @return false if codes are not accepted, true otherwise
	 */
	public boolean setCodes(String[] codes){
		Logger.getLogger(Game.class.getName()).log(Level.WARNING,"No codes for that action");
		return false;
	}
	
	/**
	 * Methods to getCodes set by the player to specify the ActionMsg request.
	 * @return Array of strings containing codes set by player
	 */
	public String[] getCodes(){
		Logger.getLogger(Game.class.getName()).log(Level.WARNING,"No codes for that action");
		return new String[0];
	}
	
	/**
	 * Return status of needCode attribute.
	 * @return true if the message needs code to be set
	 */
	public boolean getNeedCode() {
		return needCode;
	}
	
	/**
	 * Sets status of needCode attribute.
	 * @param needCode - boolean value to assign to needCode attribute
	 */
	public void setNeedCode(boolean needCode) {
		this.needCode = needCode;
	}
	
	/**
	 * {@inheritDoc}. For TurnActionMsgs is a FilledMsg containing the message.
	 */
	@Override
	public EventMsg relatedEventMsg() {
		return  new FilledMsg(this);
	}
	
	/**
	 * Allows a concrete Painter visitor to visit the message in order to paint choices he has
	 * to set codes.
	 * @param painter - Concrete Painter visitor 
	 */
	public abstract void getShowChoices(Painter painter);
	
	/**
	 * Returns a string representation of the action requested by the message.
	 * @return String action name
	 */
	public abstract String getName();
	
	/**
	 * Method to get the ordinal String representation of a number
	 * @param i - number you want the ordinal representation of
	 * @return ordinal representation of i
	 */
	public String ordinal(int i) {
	    String[] sufixes = new String[] { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th" };
	    switch (i % 100) {
	    case 11:
	    case 12:
	    case 13:
	        return i + "th";
	    default:
	        return i + sufixes[i % 10];

	    }
	}
	
	
}
