package it.polimi.ingsw.cg28.controller.updaters;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.ActionMsgHandler;
import it.polimi.ingsw.cg28.controller.Game;
import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.controller.bonus.Bonus;
import it.polimi.ingsw.cg28.controller.bonus.BonusPack;
import it.polimi.ingsw.cg28.controller.bonus.DrawCardBonus;
import it.polimi.ingsw.cg28.controller.bonus.NobilityBonus;
import it.polimi.ingsw.cg28.controller.bonus.ReuseCityBonus;
import it.polimi.ingsw.cg28.controller.bonus.ReusePermitBonus;
import it.polimi.ingsw.cg28.controller.bonus.TakePermitTileBonus;
import it.polimi.ingsw.cg28.exception.InvalidActionException;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.Player;
import it.polimi.ingsw.cg28.view.messages.action.QuitActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.bonus.BonusActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.bonus.ReuseCityBonusActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.bonus.ReusePermitBonusActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.bonus.TakePermitTileBonusActionMsg;
import it.polimi.ingsw.cg28.view.messages.event.EventMsg;
import it.polimi.ingsw.cg28.view.messages.event.SimpleEventMsg;

/**
 * Concrete visit strategy implementation of the BonusTranslator interface.
 * Specifies how to assign bonuses to Player objects.
 * @author Mario
 * @implements BonusTranslator
 */
public class GetBonusTranslator implements BonusTranslator {
	
	private ActionMsgHandler actionMsgHandler;
	private Player player;
	
	private static final Logger log = Logger.getLogger(Game.class.getName());
	
	/**
	 * Constructor of the class.
	 * @param actionMsgHandler ActionMsg handler to allow bonuses interactions with the player
	 * 		  and notify observers of assigned bonuses
	 * @param player currentPlayer to assign bonuses
	 * @throws NullPointerException if one of the two parameter is zero
	 */
	public GetBonusTranslator(ActionMsgHandler actionMsgHandler, Player player){
		
		Preconditions.checkNotNull(actionMsgHandler);
		Preconditions.checkNotNull(player);
		
		this.actionMsgHandler = actionMsgHandler;
		this.player = player;
	}
	
	/**
	 * Default getBonus method for all bonuses
	 * @param bonus to get the bonus
	 */
	private void getBonus(Bonus bonus){
		
		bonus.getBonus(player);
		EventMsg event = new SimpleEventMsg("You gain: " + bonus.getClass().getSimpleName() +" | " + bonus.getValue());
		event.setPlayer(player.getID());
		actionMsgHandler.notify(event);
		
	}
	
	/**
	 * Defines a visit "strategy" to assign Bonus objects
	 */
	@Override
	public void translate(Bonus bonus){
		
		this.getBonus(bonus);
		
	}
	
	/**
	 * Defines a visit "strategy" to assign BonusPack objects.
	 * Translates all bonuses contained in the bonus pack.
	 */
	@Override
	public void translate(BonusPack bonus){
		
		List<Bonus> bonuses = bonus.getBonusPack();
		
		for (Bonus b : bonuses)
			b.translate(this);
		
	}
	
	/**
	 * Defines a visit "strategy" to assign NobilityBonus objects.
	 * Assign the bonus and the related bonus through the NobilityTrackUpdater of the model.
	 * @param bonus to visit
	 */
	@Override
	public void translate(NobilityBonus bonus){
		
		this.getBonus(bonus);
		
		NobilityTrackUpdater nobilityTrackUpdater = 
				new NobilityTrackUpdater(player.getNobilityRank().getValue(), player);
		
		nobilityTrackUpdater.update(actionMsgHandler);
		
	}
	
	/**
	 * Defines a visit "strategy" to assign DrawCardBonus objects.
	 * Assign the bonus.
	 * @param bonus to visit
	 */
	@Override
	public void translate(DrawCardBonus bonus){
		
		ModelStatus model = actionMsgHandler.getGameController().getModel();
		bonus.setCardDeck(model.getPoliticCardsDeck());
		
		this.getBonus(bonus);
		
	}
	
	/**
	 * Defines a visit "strategy" to assign ReusePermitBonus objects.
	 * Ask the user for interaction in order to assign the bonus, sets isBonus for the current turn.
	 * @param bonus to visit
	 */
	@Override
	public void translate(ReusePermitBonus bonus){
		
		PlayerID playerID = player.getID();
		
		BonusActionMsg actionMsg = 
				new ReusePermitBonusActionMsg(bonus.getValue(), playerID);
		
		processBonusMsg(actionMsg);
		
	}
	
	/**
	 * Private method to process a bonus action message. Fills the message and sends it to the player
	 * in order to receive interactions. It also sends a notify (SimpleEventMsg) to the player of the bonus gained.
	 * @param actionMsg
	 */
	private void processBonusMsg(BonusActionMsg actionMsg) {
		
		PlayerID playerID = player.getID();
		
		try{
			actionMsg.fill(actionMsgHandler.getGameController().getModel());
			actionMsg.setFilled(true);
		} catch (InvalidActionException e){
			log.log(Level.WARNING, e.getMessage(), e);
			sendErrorMsg(e.getMessage(), playerID);
			return;
		}
		
		if(player.getTurn() != null)
			player.getTurn().setBonus(true);
		
		EventMsg eventMsg = new SimpleEventMsg("You gain a " + actionMsg.getName() + "!");
		eventMsg.setPlayer(playerID);
		actionMsgHandler.notify(eventMsg);
		
		eventMsg = actionMsg.relatedEventMsg();
		eventMsg.setPlayer(playerID);
		actionMsgHandler.notify(eventMsg);
		
	}

	/**
	 * Defines a visit "strategy" to assign ReuseCityBonus objects.
	 * Ask the user for interaction in order to assign the bonus, sets isBonus for the current turn.
	 * @param bonus to visit
	 */
	@Override
	public void translate(ReuseCityBonus bonus){
		
		PlayerID playerID = player.getID();
		
		BonusActionMsg actionMsg = 
				new ReuseCityBonusActionMsg(bonus.getValue(), playerID);
		
		processBonusMsg(actionMsg);

	}
	
	/**
	 * Defines a visit "strategy" to assign TakePermitTileBonus objects.
	 * Ask the user for interaction in order to assign the bonus, sets isBonus for the current turn.
	 * @param bonus to visit
	 */
	@Override
	public void translate(TakePermitTileBonus bonus){
		
		PlayerID playerID = player.getID();
		
		BonusActionMsg actionMsg = 
				new TakePermitTileBonusActionMsg(bonus.getValue(), playerID);
		
		processBonusMsg(actionMsg);
		
	}
	
	/**
	 * Private method to send a simpleEventMsg with error message to the current player if can't assign a bonus.
	 * @param string - error message
	 * @param playerID - related to player to notify
	 */
	private void sendErrorMsg(String string, PlayerID playerID){
		
		SimpleEventMsg event = new SimpleEventMsg(string);
		event.setPlayer(playerID);
		actionMsgHandler.notify(event);
		actionMsgHandler.handle(new QuitActionMsg());
		
	}

}
