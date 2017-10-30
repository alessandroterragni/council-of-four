package it.polimi.ingsw.cg28.view.messages.event;

import java.util.List;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.tmodel.TBalcony;
import it.polimi.ingsw.cg28.tmodel.TBonus;
import it.polimi.ingsw.cg28.tmodel.TBonusTile;
import it.polimi.ingsw.cg28.tmodel.TMap;
import it.polimi.ingsw.cg28.tmodel.TObjectFactory;
import it.polimi.ingsw.cg28.tmodel.TPlayer;
import it.polimi.ingsw.cg28.tmodel.TRegion;
import it.polimi.ingsw.cg28.tmodel.TTown;
import it.polimi.ingsw.cg28.view.ViewHandler;

/**
 * Represents an event message containing the status of the game table for this turn.
 * @author Marco
 *
 */
public class TableStatusMsg extends EventMsg {

	private static final long serialVersionUID = 7381687014130019211L;
	private TMap map;
	private List<TTown> towns;
	private TBalcony[] balcony;
	private TRegion[] regions;
	private TPlayer[] players;
	private TBonusTile[] bonusTiles;
	private TBonus[] kingRewards;

	private String currentTurnPlayer;
	
	/**
	 * The constructor of the class, creates a new TableStatusMsg.
	 * @param model - The ModelStatus to refer to
	 * @param currentPlayer - The player currently playing his/her turn
	 * @throws NullPointerException if any parameter is null
	 */
	public TableStatusMsg(ModelStatus model, PlayerID currentPlayer) {
		
		Preconditions.checkNotNull(model, "The model to refer to can't be null.");
		Preconditions.checkNotNull(currentPlayer, "The associated player can't be null.");
		
		TObjectFactory factory = new TObjectFactory();
		map = factory.createTMap(model.getMap());
		towns = map.getTownList();
		balcony = new TBalcony[model.getRegions().length+1];
		regions = new TRegion[model.getRegions().length];
		players = new TPlayer[model.getPlayers().length];
		currentTurnPlayer = currentPlayer.getName();
		
		int i=0;
		for(i =0;i < model.getRegions().length;i++){
			balcony[i] = factory.createTBalcony(model.getRegions()[i].getBalcony());
			regions[i]= factory.createTRegion(model.getRegion(i));
		}
		balcony[i]= factory.createTBalcony(model.getMap().getKingCouncil());
		
		for(i=0;i<model.getPlayers().length;i++){
			players[i] = factory.createTPlayer(model.getPlayers()[i], false);
		}
		
		bonusTiles = factory.createTBonusTiles(model.getBonusTiles());
		
		kingRewards = new TBonus[model.getKingRewards().size()];
		
		for(i=0;i<model.getKingRewards().size();i++){
			kingRewards[i] = factory.createTBonus(model.getKingRewards().get(i).getBonus());
		}
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void read(ViewHandler viewHandler) {
		viewHandler.handle(this);
	}

	/**
	 * Fetches the transferable map object.
	 * @return The TMap containing the current map status
	 */
	public TMap getMap() {
		return map;
	}

	/**
	 * Fetches the list of transferable town objects.
	 * @return A List of TTowns containing the current towns status
	 */
	public List<TTown> getTowns() {
		return towns;
	}

	/**
	 * Fetches the transferable king balcony object.
	 * @return The TBalcony containing the current king council's balcony status
	 */
	public TBalcony[] getBalcony() {
		return balcony;
	}

	/**
	 * Fetches the transferable regions objects.
	 * @return An array of TRegions containing the current regions status
	 */
	public TRegion[] getRegions() {
		return regions;
	}

	/**
	 * Fetches the transferable player objects.
	 * @return An array of TPlayers containing the current players status
	 */
	public TPlayer[] getPlayers() {
		return players;
	}
	
	/**
	 * Fetches the transferable bonus tiles objects.
	 * @return An array of TBonusTiles containing the current bonus tiles status
	 */
	public TBonusTile[] getBonusTiles() {
		return bonusTiles;
	}
	
	/**
	 * Fetches the transferable king reward tiles objects.
	 * @return An array of TBonusTiles containing the current king reward tiles status
	 */
	public TBonus[] getKingRewards() {
		return kingRewards;
	}
	
	/**
	 * Fetches the name of the current player.
	 * @return The string containing the current player's name
	 */
	public String getCurrentTurnPlayer() {
		return currentTurnPlayer;
	}

}
