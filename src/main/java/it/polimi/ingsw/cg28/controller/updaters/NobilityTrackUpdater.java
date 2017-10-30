package it.polimi.ingsw.cg28.controller.updaters;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.ActionMsgHandler;
import it.polimi.ingsw.cg28.controller.GameController;
import it.polimi.ingsw.cg28.controller.bonus.Bonus;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.NobilityTrackBonus;
import it.polimi.ingsw.cg28.model.Player;

/**
 * Updater to assign bonus matching currentPlayer updated rank and related bonus on a NobilityTrackBonus
 * (concrete Updater interface strategy implementation).
 * @author Mario
 *
 */
public class NobilityTrackUpdater implements Updater {

	private int newNobilityValue;
	private Player player;
	
	/**
	 * Class constructor
	 * @param newNobilityValue updated rank of currentPlayer
	 * @param Player - the player to update
	 * @throws NullPointerException if player parameter is null
	 * @throws IllegalArgumentException if parameter newNobilityValue is lower than zero
	 */
	public NobilityTrackUpdater(int newNobilityValue, Player player){
		
		Preconditions.checkNotNull(player);
		Preconditions.checkArgument(newNobilityValue >= 0);
		
		this.newNobilityValue = newNobilityValue;
		this.player = player;
		
	}
	
	/**
	 * Assigns bonus to currentPlayer relying on updated rank
	 */
	@Override
	public void update(ActionMsgHandler actionMsgHandler) {
		
		
		GameController gameController = actionMsgHandler.getGameController();
		ModelStatus model = gameController.getModel();

		GetBonusTranslator bonusTranslator = new GetBonusTranslator(actionMsgHandler, player);
		
		NobilityTrackBonus nobilityTrackBonus = model.getNobilityTrackBonus();
		
		Bonus bonus = nobilityTrackBonus.getTrackBonus(newNobilityValue);
		
		if(bonus != null)
			bonus.translate(bonusTranslator);
		
	}

}
