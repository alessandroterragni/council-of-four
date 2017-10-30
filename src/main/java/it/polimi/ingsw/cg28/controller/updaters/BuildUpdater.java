package it.polimi.ingsw.cg28.controller.updaters;


import java.util.List;
import java.util.Set;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.ActionMsgHandler;
import it.polimi.ingsw.cg28.controller.GameController;
import it.polimi.ingsw.cg28.controller.bonus.Bonus;
import it.polimi.ingsw.cg28.model.BonusTile;
import it.polimi.ingsw.cg28.model.KingRewardTile;
import it.polimi.ingsw.cg28.model.GameMap;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.Player;
import it.polimi.ingsw.cg28.model.Town;
import it.polimi.ingsw.cg28.model.decks.TileDeck;

/**
 * Class BuildUpdater manages updates to model when a player builds an emporium.
 * @author Mario
 * @implements PlayerTurnUpdater
 */
public class BuildUpdater extends PlayerTurnUpdater {
	
	private Town emporiumBuiltHere;
	
	/**
	 * Constructor of the class.
	 * @param emporiumBuiltHere Town where current player built last emporium
	 * @throws NullPointerException if emporiumBuilHere parameter is null
	 */
	public BuildUpdater(Town emporiumBuiltHere){
		
		Preconditions.checkNotNull(emporiumBuiltHere);
		
		this.emporiumBuiltHere = emporiumBuiltHere;
		
	}
	
	/**
	 * When an emporium was built this method checks and assigns bonus related to the player:
	 * bonuses related to the town network composed by towns in which the player has built an emporium,
	 * and eventually bonuses of BonusTiles or KingRewardsTiles gained.
	 * {@inheritDoc}
	 */
	@Override
	public void update(ActionMsgHandler actionMsgHandler) {
		
		GameController gameController = actionMsgHandler.getGameController();
		ModelStatus model = gameController.getModel();
		Player player = model.getPlayer(gameController.getCurrentPlayer());
		
		checkTownNetwork(actionMsgHandler);
		checkBonusTile(actionMsgHandler);
		checkNumEmporium(model,player);
		
		super.update(actionMsgHandler);	
		
	}
	
	/**
	 * Assigns bonus to the player related to towns linked to emporiumBuiltHere. 
	 * @param actionMsgHandler ActionMsg handler
	 * @see TownNetChecker#checkMap(GameMap, Town, Player)
	 */
	private void checkTownNetwork(ActionMsgHandler actionMsgHandler) {
		
		GameController gameController = actionMsgHandler.getGameController();
		ModelStatus model = gameController.getModel();
		Player player = model.getPlayer(gameController.getCurrentPlayer());
		GameMap map = model.getMap();
		
		GetBonusTranslator bonusTranslator = new GetBonusTranslator(actionMsgHandler, player);
		
		TownNetChecker checker = new TownNetChecker();
		
		Set<Town> linkedTowns = checker.checkMap(map, emporiumBuiltHere, player);
		
		for(Town target : linkedTowns){
			if(target.getBonus() != null)
				target.getBonus().translate(bonusTranslator);
		}
		
	}
	
	/**
	 * Assigns bonus to the player if it is the first to complete all towns of a region  
	 * or of an alloy. If there are kingReward yet, assigns the related bonus to the player.
	 * @param actionMsgHandler ActionMsg handler
	 * @see BonusTileChecker#sameRegionCheck(GameMap, Town, Player)
	 * @see BonusTileChecker#sameTownTypeCheck(GameMap, Town, Player)
	 */
	private void checkBonusTile(ActionMsgHandler actionMsgHandler) {
		
		GameController gameController = actionMsgHandler.getGameController();
		ModelStatus model = gameController.getModel();
		Player player = model.getPlayer(gameController.getCurrentPlayer());
		GameMap map = model.getMap();
		TileDeck<BonusTile> bonusTiles = model.getBonusTiles();
		
		GetBonusTranslator bonusTranslator = new GetBonusTranslator(actionMsgHandler, player);
		
		for(int i = 0; i < bonusTiles.size(); i++){
			
			BonusTile target = bonusTiles.draw();
			String stringCheck = target.getIdentifier();
			
			BonusTileChecker checker = new BonusTileChecker();
			
			boolean remove = false;
			
			for(Town t : map.getTownNetwork().vertexSet()){
				
				if(t.getRegion().getRegionType().equals(stringCheck)
					&& checker.sameRegionCheck(model.getMap(), t, player)){
						target.getBonus().translate(bonusTranslator);
						takeKingReward(actionMsgHandler, player);
						remove = true;
						break;
				}
				
				if(t.getAlloy().equals(stringCheck) 
					&& checker.sameTownTypeCheck(model.getMap(), t, player)){
						target.getBonus().translate(bonusTranslator);
						takeKingReward(actionMsgHandler, player);
						remove = true;
						break;
				}
			}
			
			if (!remove)
				bonusTiles.enqueue(target);
			
		}
		
	}
	
	/**
	 * Checks KingRewards array list. If there are kingReward yet, assigns the related bonus to the player
	 * @param actionMsgHandler ActionMsg handler
	 * @param player current player
	 */
	private void takeKingReward(ActionMsgHandler actionMsgHandler, Player player) {
		
		GameController gameController = actionMsgHandler.getGameController();
		ModelStatus model = gameController.getModel();
		List<KingRewardTile> reward = model.getKingRewards();
		
		GetBonusTranslator bonusTranslator = new GetBonusTranslator(actionMsgHandler, player);
		
		if(!reward.isEmpty()){
		
			Bonus bonus = reward.get(0).getBonus();
			reward.remove(0);
			bonus.translate(bonusTranslator);
			
		}
	
	}
	
	/**
	 * When the current player builds his/her last emporium , this method sets the endGame attribute
	 * to <code>true</code> (if it wasn't yet), then increases that player's score by 3 points.
	 * @param model Game model
	 * @param player current player
	 */
	private void checkNumEmporium(ModelStatus model, Player player) {

		if (player.getEmporiumNumber() == 0 && !model.isEndGame()){
			model.setEndGame(true);
			player.getScore().increment(3);
		}
		
	}

}
