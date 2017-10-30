/**
 * 
 */
package it.polimi.ingsw.cg28.view.messages.action;

import java.util.Map;

import it.polimi.ingsw.cg28.controller.ActionMsgHandlerInterface;
import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.view.messages.event.EndGameEventMsg;
import it.polimi.ingsw.cg28.view.messages.event.EventMsg;

/**
 * Represents an action message containing the request to end the current match.
 * @author Marco
 *
 */
public class EndGameMsg extends ActionMsg{

	private static final long serialVersionUID = 377272486537943238L;
	private Map<PlayerID, Integer> scores;
	private PlayerID winner;
	
	/**
	 * The constructor of the class, creates a new EndGameMsg.
	 * @param scores - The score ranking for this match
	 * @param winner - The ID of the player who won the game
	 */
	public EndGameMsg(Map<PlayerID, Integer> scores, PlayerID winner) {
		
		this.scores = scores;
		this.winner = winner;

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
	public EventMsg relatedEventMsg() {
		return new EndGameEventMsg(this);
	}
	
	/**
	 * Fetches the ranking contained in this message.
	 * @return The Map containing the ranking for this match
	 */
	public Map<PlayerID, Integer> getScores() {
		return scores;
	}

	/**
	 * Fetches the ID of the winner for this game.
	 * @return The PlayerID of the player who won the match
	 */
	public PlayerID getWinner() {
		return winner;
	}

}
