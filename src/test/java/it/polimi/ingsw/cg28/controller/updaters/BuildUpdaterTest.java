package it.polimi.ingsw.cg28.controller.updaters;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.ActionMsgHandler;
import it.polimi.ingsw.cg28.controller.Game;
import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.model.BonusTile;
import it.polimi.ingsw.cg28.model.KingRewardTile;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.Player;
import it.polimi.ingsw.cg28.view.messages.action.StartMsg;

public class BuildUpdaterTest {
	
	private static final Logger log = Logger.getLogger(Game.class.getName());
	
	private Player[] players;
	private ModelStatus model;
	private BuildUpdater buildUpdater;
	private List<PlayerID> playerIDs;
	private ActionMsgHandler actionMsgHandler;
	
	@Before
	public void before() throws IOException{
		
		log.setUseParentHandlers(false);
		
		playerIDs = new ArrayList<>();
		playerIDs.add(new PlayerID("Player1"));
		playerIDs.add(new PlayerID("Player2"));
		
		
		Game game = new Game("GAMETEST",playerIDs, null);
		
		game.getActionMsgHandler().handle(new StartMsg());
		
		model = game.getActionMsgHandler().getGameController().getModel();
		players = model.getPlayers();
		actionMsgHandler = game.getActionMsgHandler();
		
	}
	
	@Test (expected = NullPointerException.class)
	public void testBuildUpdaterWithNullParameter() {
		buildUpdater = new BuildUpdater(null);
	}
	
	@Test
	public void testBuildUpdaterAlloyCompleted() {
		
		int precScore = players[0].getScore().getValue();
		
		KingRewardTile kingTile = model.getKingRewards().get(0);
		
		BonusTile tile = null; 
		
		for(int i = 0; i < model.getBonusTiles().size(); i++){
			tile = model.getBonusTiles().draw();
			if (tile.getIdentifier().equals("Gold")){
				model.getBonusTiles().enqueue(tile);
				break;
			}
			model.getBonusTiles().enqueue(tile);
		}
		
		model.getMap().addEmporium(players[0].getID(), model.getMap().getTown("Arkon"));
		model.getMap().addEmporium(players[0].getID(), model.getMap().getTown("Framek"));
		model.getMap().addEmporium(players[0].getID(), model.getMap().getTown("Hellar"));
		model.getMap().addEmporium(players[0].getID(), model.getMap().getTown("Kultos"));
		model.getMap().addEmporium(players[0].getID(), model.getMap().getTown("Osium"));
		
		buildUpdater = new BuildUpdater(model.getMap().getTown("Osium"));
		
		buildUpdater.update(actionMsgHandler);
		
		//To check buildUpdater if assigns correctly bonus to players[0], the method assigns 
		//to players[1] the same bonus directly to be sure the increment is right
		
		GetBonusTranslator traslator = new GetBonusTranslator(actionMsgHandler, players[1]);
		
		model.getMap().getTown("Osium").getBonus().translate(traslator);;
		
		assertTrue(players[0].getScore().getValue() == precScore + 45 + players[1].getScore().getValue());
		assertFalse(model.getKingRewards().contains(kingTile));
		
		boolean check = false;
		
		for(int i = 0; i < model.getBonusTiles().size(); i++){
			BonusTile tileCheck = model.getBonusTiles().draw();
			if (tileCheck.equals(tile)){
				check = true;
				break;
			}
			model.getBonusTiles().enqueue(tileCheck);
		}
		
		assertFalse(check);
		
	}
	
	@Test
	public void testBuildUpdaterAlloyNotCompleted() {
		
		int precScore = players[0].getScore().getValue();
		
		KingRewardTile kingTile = model.getKingRewards().get(0);
		
		BonusTile tile = null; 
		
		for(int i = 0; i < model.getBonusTiles().size(); i++){
			tile = model.getBonusTiles().draw();
			if (tile.getIdentifier().equals("Gold")){
				model.getBonusTiles().enqueue(tile);
				break;
			}
			model.getBonusTiles().enqueue(tile);
		}
		
		
		model.getMap().addEmporium(players[0].getID(), model.getMap().getTown("Arkon"));
		model.getMap().addEmporium(players[0].getID(), model.getMap().getTown("Framek"));
		model.getMap().addEmporium(players[0].getID(), model.getMap().getTown("Hellar"));
		model.getMap().addEmporium(players[0].getID(), model.getMap().getTown("Osium"));
		
		buildUpdater = new BuildUpdater(model.getMap().getTown("Osium"));
		
		buildUpdater.update(actionMsgHandler);
		
		//To check buildUpdater if assigns correctly bonus to players[0], the method assigns 
		//to players[1] the same bonus directly to be sure the increment is right
		
		GetBonusTranslator traslator = new GetBonusTranslator(actionMsgHandler, players[1]);
		
		model.getMap().getTown("Osium").getBonus().translate(traslator);;
		
		assertTrue(players[0].getScore().getValue() == precScore + players[1].getScore().getValue());
		assertTrue(model.getKingRewards().contains(kingTile));
		
		boolean check = false;
		
		for(int i = 0; i < model.getBonusTiles().size(); i++){
			BonusTile tileCheck = model.getBonusTiles().draw();
			if (tileCheck.equals(tile)){
				check = true;
				break;
			}
			model.getBonusTiles().enqueue(tileCheck);	
		}
		
		assertTrue(check);
		
	}
	
	@Test
	public void testBuildUpdaterRegionCompleted() {
		
		int precScore = players[0].getScore().getValue();
		
		KingRewardTile kingTile = model.getKingRewards().get(0);
		
		BonusTile tile = null;  
		
		for(int i = 0; i < model.getBonusTiles().size(); i++){
			tile = model.getBonusTiles().draw();
			if (tile.getIdentifier().equals("Coast")){
				model.getBonusTiles().enqueue(tile);
				break;
			}
			model.getBonusTiles().enqueue(tile);
		}
		
		model.getMap().addEmporium(players[0].getID(), model.getMap().getTown("Arkon"));
		model.getMap().addEmporium(players[0].getID(), model.getMap().getTown("Burgen"));
		model.getMap().addEmporium(players[0].getID(), model.getMap().getTown("Castrum"));
		model.getMap().addEmporium(players[0].getID(), model.getMap().getTown("Dorful"));
		model.getMap().addEmporium(players[0].getID(), model.getMap().getTown("Esti"));
		
		buildUpdater = new BuildUpdater(model.getMap().getTown("Esti"));
		
		buildUpdater.update(actionMsgHandler);
		
		//To check buildUpdater if assigns correctly bonus to players[0], the method assigns 
		//to players[1] the same bonus directly to be sure the increment is right
		
		GetBonusTranslator traslator = new GetBonusTranslator(actionMsgHandler, players[1]);
		
		model.getMap().getTown("Esti").getBonus().translate(traslator);
		model.getMap().getTown("Burgen").getBonus().translate(traslator);
		
		assertTrue(players[0].getScore().getValue() == precScore + 30 + players[1].getScore().getValue());
		assertFalse(model.getKingRewards().contains(kingTile));
		
		boolean check = false;
		
		for(int i = 0; i < model.getBonusTiles().size(); i++){
			BonusTile tileCheck = model.getBonusTiles().draw();
			if (tileCheck.equals(tile)){
				check = true;
				break;
			}
			model.getBonusTiles().enqueue(tileCheck);
		}
		
		assertFalse(check);
		
	}
	
	@Test
	public void testBuildUpdaterRegionNotCompleted() {
		
		int precScore = players[0].getScore().getValue();
		
		KingRewardTile kingTile = model.getKingRewards().get(0);
		
		BonusTile tile = null;  
		
		for(int i = 0; i < model.getBonusTiles().size(); i++){
			tile = model.getBonusTiles().draw();
			if (tile.getIdentifier().equals("Coast")){
				model.getBonusTiles().enqueue(tile);
				break;
			}
			model.getBonusTiles().enqueue(tile);
		}
		
		model.getMap().addEmporium(players[0].getID(), model.getMap().getTown("Arkon"));
		model.getMap().addEmporium(players[0].getID(), model.getMap().getTown("Burgen"));
		model.getMap().addEmporium(players[0].getID(), model.getMap().getTown("Dorful"));
		model.getMap().addEmporium(players[0].getID(), model.getMap().getTown("Esti"));
		
		buildUpdater = new BuildUpdater(model.getMap().getTown("Esti"));
		
		buildUpdater.update(actionMsgHandler);
		
		//To check buildUpdater if assigns correctly bonus to players[0], the method assigns 
		//to players[1] the same bonus directly to be sure the increment is right
		
		GetBonusTranslator traslator = new GetBonusTranslator(actionMsgHandler, players[1]);
		
		model.getMap().getTown("Esti").getBonus().translate(traslator);
		model.getMap().getTown("Burgen").getBonus().translate(traslator);
		
		assertTrue(players[0].getScore().getValue() == precScore + players[1].getScore().getValue());
		assertTrue(model.getKingRewards().contains(kingTile));
		
		boolean check = false;
		
		for(int i = 0; i < model.getBonusTiles().size(); i++){
			BonusTile tileCheck = model.getBonusTiles().draw();
			if (tileCheck.equals(tile)){
				check = true;
				break;
			}
			model.getBonusTiles().enqueue(tileCheck);
		}
		
		assertTrue(check);
		
	}
	
	@Test
	public void testBuildUpdaterTownNet() {
		
		int precScore = players[0].getScore().getValue();
		
		model.getMap().addEmporium(players[0].getID(), model.getMap().getTown("Arkon"));
		model.getMap().addEmporium(players[0].getID(), model.getMap().getTown("Castrum"));
		model.getMap().addEmporium(players[0].getID(), model.getMap().getTown("Dorful"));
		model.getMap().addEmporium(players[0].getID(), model.getMap().getTown("Framek"));
		
		buildUpdater = new BuildUpdater(model.getMap().getTown("Dorful"));
		
		buildUpdater.update(actionMsgHandler);
		
		//To check buildUpdater if assigns correctly bonus to players[0], the method assigns 
		//to players[1] the same bonus directly to be sure the increment is right
		
		GetBonusTranslator traslator = new GetBonusTranslator(actionMsgHandler, players[1]);
		
		model.getMap().getTown("Arkon").getBonus().translate(traslator);
		model.getMap().getTown("Castrum").getBonus().translate(traslator);
		model.getMap().getTown("Dorful").getBonus().translate(traslator);
		model.getMap().getTown("Framek").getBonus().translate(traslator);
		
		assertTrue(players[0].getScore().getValue() == precScore + players[1].getScore().getValue());
		
	}
	
	@Test
	public void testBuildUpdaterTownNetWithTownWithBonusNull() {
		
		int precScore = players[0].getScore().getValue();
		
		model.getMap().addEmporium(players[0].getID(), model.getMap().getTown("Graden"));
		model.getMap().addEmporium(players[0].getID(), model.getMap().getTown("Indur"));
		model.getMap().addEmporium(players[0].getID(), model.getMap().getTown("Hellar"));
		model.getMap().addEmporium(players[0].getID(), model.getMap().getTown("Juvelar"));
		
		buildUpdater = new BuildUpdater(model.getMap().getTown("Juvelar"));
		
		buildUpdater.update(actionMsgHandler);
		
		//To check buildUpdater if assigns correctly bonus to players[0], the method assigns 
		//to players[1] the same bonus directly to be sure the increment is right
		
		GetBonusTranslator traslator = new GetBonusTranslator(actionMsgHandler, players[1]);
		
		model.getMap().getTown("Juvelar").getBonus().translate(traslator);
		model.getMap().getTown("Indur").getBonus().translate(traslator);
		model.getMap().getTown("Hellar").getBonus().translate(traslator);
		
		assertTrue(players[0].getScore().getValue() == precScore + players[1].getScore().getValue());
		
	}

}
