package it.polimi.ingsw.cg28.controller;


import it.polimi.ingsw.cg28.controller.actions.*;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.Player;
import it.polimi.ingsw.cg28.model.PoliticCard;
import it.polimi.ingsw.cg28.view.messages.event.EventMsg;
import it.polimi.ingsw.cg28.view.messages.event.GiveMeActionMsg;

/**
 * Represent a PlayerTurn of Council of Four game.
 * @author Mario
 */
public class PlayerTurn extends GameTurn {
	
	private int mainActionCounter;
	private int quickActionCounter;
	private boolean bonus;

	/**
	 * Constructor of PlayerTurn.
	 * Initialise mainActionCounter and quickActionCounter to 1.
	 * @param player PlayerID of turn's player.
	 */
	public PlayerTurn(PlayerID player) {
		super(player);
		this.mainActionCounter = 1;
		this.quickActionCounter = 1;
	}
	
	
	/**
	 * Decrement mainActionCounter
	 * @param actionUsed type of action used
	 */
	public void takeAction(MainAction actionUsed){
			mainActionCounter--;
	}
	
	/**
	 * Decrement quickActionCounter
	 * @param actionUsed type of action used
	 */
	public void takeAction(QuickAction actionUsed){
			quickActionCounter--;
	}

	/**
	 * Increment mainActionCounter
	 * @param actionUsed type of action received by bonus
	 */
	public void addAction(MainAction action){
			mainActionCounter++;
	}
	
	/**
	 * Check if turn is ended.
	 * @return true if turn is ended.
	 */
	@Override
	public boolean isEnded(){
		if((mainActionCounter == 0) && (quickActionCounter == 0) && !bonus)
			return true;
		return false;
	}
	
	/**
	 * Checks if the player can ask to end the turn.
	 * @return true if player can end the turn.
	 */
	public boolean canEnd(){
		if(mainActionCounter == 0 && !bonus)
			return true;
		return false;
	}
	
	/**
	 * Returns true if the player is allowed to perform a Main Action on this turn.
	 * @return true if the player is allowed to perform a Main Action on this turn.
	 */
	public boolean canDoMainAction(){
		
		return mainActionCounter > 0;
		
	}
	
	/**
	 * Returns true if the player is allowed to perform a Quick Action on this turn.
	 * @return true if the player is allowed to perform a Quick Action on this turn.
	 */
	public boolean canDoQuickAction(){
		
		return quickActionCounter > 0;
		
	}
	
	/**
	 * {@inheritDoc}
	 * Returns a {@link it.polimi.ingsw.cg28.view.messages.event.GiveMeActionMsg} with this turn action allowed set.
	 */
	@Override
	public EventMsg msgRequired(){
		
		boolean[] actionAllowed = new boolean[2];
		
		if (mainActionCounter > 0)
			actionAllowed[0] = true;
		if(quickActionCounter > 0)
			actionAllowed[1] = true;
		
		return new GiveMeActionMsg(getPlayer(), actionAllowed);
		
	}
	
	/**
	 * {@inheritDoc}. Sets the turn for the {@link it.polimi.ingsw.cg28.model.Player} corresponding to 
	 * the currentPlayer PlayerID. Player playing this turn draws a PoliticCard from deck.
	 */
	@Override
	public void setTurn(ModelStatus model){
		
		for(Player player : model.getPlayers())
			model.getPlayer(player.getID()).setTurn(null);
		
		model.getPlayer(getPlayer()).setTurn(this);
		
		startTurn(model);
	
	}
	
	/**
	 * Start Turn: player playing this turn draws a PoliticCard from deck.
	 * @param model Game Model
	 * @param player Current player PlayerID
	 */
	private void startTurn(ModelStatus model) {
		
		PoliticCard politicCard = model.getPoliticCardsDeck().draw();
		Player p = model.getPlayer(getPlayer());
		
		p.addCards(politicCard);
		
	}
	
	/**
	 * Returns true if turn is waiting for a bonus interaction.
	 * @return bonus value
	 */
	public boolean isBonus() {
		return bonus;
	}
	
	/**
	 * Sets if turn must wait for a bonus interaction.
	 * @param bonus - true if turn must wait for a bonus interaction, false otherwise
	 */
	public void setBonus(boolean bonus) {
		this.bonus = bonus;
	}
}
