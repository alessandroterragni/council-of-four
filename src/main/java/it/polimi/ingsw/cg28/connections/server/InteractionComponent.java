package it.polimi.ingsw.cg28.connections.server;

import it.polimi.ingsw.cg28.controller.PlayerID;

/**
 * InteractionComponent provides an interface to dialogue with a ControllerMultipleGames 
 * @author Mario
 *
 */
public class InteractionComponent {
	
	private ControllerMultipleGames controller;
	
	/**
	 * Constructor of the class.
	 * @param controller ControllerMultipleGames to interact with.
	 */
	public InteractionComponent(ControllerMultipleGames controller) {
		this.controller = controller;
	}
	
	/**
	 * Remove a player from ControllerMultipleGames clients list.
	 * @param player PlayerID of the player to remove
	 */
	public void removePlayer(PlayerID player){
		controller.removePlayer(player);
	}
	
	/**
	 * Manage the end of a game.
	 * @param singleGame - game instance
	 */
	public void endGame(ControllerSingleGame singleGame){
		
		for(PlayerID player: singleGame.getPlayers())
			controller.unsubscribePlayer(player, singleGame.getTopic());
	}
	
	/**
	 * Unsubscribes a player from a Topic.
	 * @param singleGame - game instance
	 * @param player - PlayerID of player to unsubscribe
	 */
	public void unsubscribePlayer(ControllerSingleGame singleGame, PlayerID player){
		
		controller.unsubscribePlayer(player, singleGame.getTopic());
		
	}

}
