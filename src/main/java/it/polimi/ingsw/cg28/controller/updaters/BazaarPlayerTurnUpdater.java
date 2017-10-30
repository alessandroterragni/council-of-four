package it.polimi.ingsw.cg28.controller.updaters;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.ActionMsgHandler;
import it.polimi.ingsw.cg28.controller.ApuBazaarController;
import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.controller.bazaar.BazaarPlayerTurn;
import it.polimi.ingsw.cg28.model.BazaarModel;
import it.polimi.ingsw.cg28.view.messages.event.BazaarStatusMsg;
import it.polimi.ingsw.cg28.view.messages.event.EventMsg;

/**
 * Manages updates (concrete Updater interface strategy implementation) for every Action related on a BazaarPlayerTurn.
 * @author Mario
 *
 */
public class BazaarPlayerTurnUpdater implements Updater {
	
	private BazaarModel model;
	private PlayerID player;
	
	/**
	 * Constructor of the class.
	 * @param model BazaarModel
	 * @param player - PlayerID of player playing current turn
	 * @throws NullPointerException if model or player parameter are null
	 */
	public BazaarPlayerTurnUpdater(BazaarModel model, PlayerID player) {
		Preconditions.checkNotNull(model);
		Preconditions.checkNotNull(player);
		this.model = model;
		this.player = player;
	}

	
	/**
	 * Manages updates for every Action related on a BazaarPlayerTurn.
	 * Notify to players the new bazaar status and send to the current player the next request.
	 */
	@Override
	public void update(ActionMsgHandler actionMsgHandler) {
		
		ApuBazaarController controller = actionMsgHandler.getBazaarController();
		actionMsgHandler.notify(new BazaarStatusMsg(model, controller.getGameID()));
		
		BazaarPlayerTurn turn = controller.getCurrentTurn();
		EventMsg event = turn.msgRequired();
		event.setPlayer(player);
		actionMsgHandler.notify(event);
		
	}

}
