package it.polimi.ingsw.cg28.controller.updaters;

import it.polimi.ingsw.cg28.controller.ActionMsgHandler;
import it.polimi.ingsw.cg28.controller.GameController;
import it.polimi.ingsw.cg28.controller.GameTurn;
import it.polimi.ingsw.cg28.view.messages.action.EndTurnMsg;
import it.polimi.ingsw.cg28.view.messages.action.ShowPlayerStatusActionMsg;
import it.polimi.ingsw.cg28.view.messages.event.EventMsg;

/**
 * Manages updates (concrete Updater interface strategy implementation) for every TurnAction on a PlayerTurn.
 * @author Mario
 *
 */
public class PlayerTurnUpdater implements Updater {
	

	/**
	 * Manage updates for every TurnAction on a PlayerTurn.
	 */
	@Override
	public void update(ActionMsgHandler actionMsgHandler){
		
		GameController gameController = actionMsgHandler.getGameController();
		GameTurn currentTurn = gameController.getCurrentTurn();
		
		actionMsgHandler.handle(new ShowPlayerStatusActionMsg());
		
		if(!currentTurn.isEnded()){
			
			EventMsg event = currentTurn.msgRequired();
			event.setPlayer(gameController.getCurrentPlayer());
			
			actionMsgHandler.notify(event);
		}
		else{
			actionMsgHandler.handle(new EndTurnMsg(gameController.getCurrentPlayer()));
		}
	}

}
