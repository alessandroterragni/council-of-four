package it.polimi.ingsw.cg28.controller.updaters;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.ActionMsgHandler;
import it.polimi.ingsw.cg28.controller.GameController;
import it.polimi.ingsw.cg28.controller.bonus.Bonus;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.Player;

/**
 * Manages updates (concrete Updater interface strategy implementation) for BonusAction.
 * @author Mario
 *
 */
public class BonusActionUpdater extends PlayerTurnUpdater {

	private Bonus bonus;
	
	/**
	 * Constructor of BonusActionUpdater
	 * @param bonus - Bonus to assign to currentPlayer
	 * @throws NullPointerException if parameter is null
	 */
	public BonusActionUpdater(Bonus bonus) {
		Preconditions.checkNotNull(bonus);
		this.bonus = bonus;
	}
	
	/**
	 * Manage updates for every BonusAction on currentPlayer assigning bonuses.
	 */
	@Override
	public void update(ActionMsgHandler actionMsgHandler) {
		
		GameController gameController = actionMsgHandler.getGameController();
		ModelStatus model = gameController.getModel();
		Player player = model.getPlayer(gameController.getCurrentPlayer());
		
		GetBonusTranslator bonusUpdater = new GetBonusTranslator(actionMsgHandler, player);
		
		bonus.translate(bonusUpdater);
		
		super.update(actionMsgHandler);

	}

}
