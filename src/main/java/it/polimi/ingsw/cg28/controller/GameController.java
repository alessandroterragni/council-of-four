package it.polimi.ingsw.cg28.controller;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.actions.DefaultActionFactory;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.PlayerBuilder;
import it.polimi.ingsw.cg28.model.ScoreCalculator;
import it.polimi.ingsw.cg28.model.parser.Configuration;
import it.polimi.ingsw.cg28.model.parser.IOController;
import it.polimi.ingsw.cg28.view.messages.action.ActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.BazaarStartActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.EndGameMsg;
import it.polimi.ingsw.cg28.view.messages.action.ShowPlayerStatusActionMsg;
import it.polimi.ingsw.cg28.view.messages.event.EventMsg;
import it.polimi.ingsw.cg28.view.messages.event.WelcomeEventMsg;

/**
 * GameController manages Council of Four game progresses.
 * @author Mario, Alessandro
 */
public class GameController extends AbstractGameController{
	
	private TurnController turnController;
	private GameTurn currentTurn;
	
	private static final Logger log = Logger.getLogger(Game.class.getName());
	
	/**
	 * Constructor of the game-controller. Instantiates a new turns queue and builds the model
	 * of the game using configuration object given by parameter.
	 * @param players - List of PlayerID of players playing the game
	 * @param configuration - Configuration files to build model related to game.
	 * @param gameID - game identifier to identify the Game
	 * @throws NullPointerException if one of the three parameter is null
	 */
	public GameController(List<PlayerID> players, Configuration configuration, PlayerID gameID){
		
		Preconditions.checkNotNull(players, "Players list can't be null");
		Preconditions.checkNotNull(configuration, "Configuration can't be null");
		Preconditions.checkNotNull(gameID, "GameID can't be null");
		
		int bazaarTrigger;
		
		if (!configuration.yesBazaar())
			bazaarTrigger = -1;
		else bazaarTrigger = players.size();
		
		turnController = new TurnController(players, bazaarTrigger, gameID.getName());
		
		try{
			
			IOController builder = new IOController();
			this.model = builder.build(configuration.fileConfig());
		
		} catch (IOException e){
			log.log(Level.SEVERE, "Errore File Configurazione: " + e.getMessage(), e);
		}
		
		PlayerBuilder playerBuilder = new PlayerBuilder();
		model.setPlayers(playerBuilder.build(players, model));
		
		actionFactory = new DefaultActionFactory(model);
		
	}
	
	/**
	 * {@inheritDoc}
	 * Returns a {@link it.polimi.ingsw.cg28.view.messages.event.WelcomeEventMsg}
	 */
	@Override
	public EventMsg startGame() {
		
		return new WelcomeEventMsg(model);
		
	}

	
	/**
	 * Checks boolean EndGame of model.
	 * If it's true initialises the last round (each player has a last round to play and after that the game ends).
	 * If it's true and turns queue is Empty returns true.
	 * Otherwise returns false.
	 */
	@Override
	public boolean endGame(){
		
		if (model.isEndGame()){
			turnController.setLastRound(true);
			if(turnController.isQueueEmpty())
				return true;	
		}
		
		return false;
		
	}
	
	/**
	 * Plays the nextTurn of the game. Sets the new currentTurn and currentPlayer. Initialises the turn 
	 * with {@link GameTurn#setTurn(ModelStatus, PlayerID)} and returns the message required for the 
	 * current Turn {@link GameTurn#msgRequired()}.
	 */
	@Override
	public EventMsg getNextTurn(){
		
		currentTurn = turnController.nextTurn();
		currentPlayer = currentTurn.getPlayer();
		
		currentTurn.setTurn(model);
		
		EventMsg event = currentTurn.msgRequired();
		
		if(event != null)
			event.setPlayer(currentPlayer);
		
		return event;
		
	}
	
	/**
	 * Returns the updateMsg for the game at the current time. {@link it.polimi.ingsw.cg28.view.messages.action.ShowPlayerStatusActionMsg} on a PlayerTurn.
	 * {@link it.polimi.ingsw.cg28.view.messages.action.BazaarStartActionMsg} on BazaarTurn.
	 */
	@Override
	public ActionMsg updateMsg() {
		
		if(currentPlayer.equals(turnController.getMarketPlayer())){
			return new BazaarStartActionMsg();
		}
		
		return new ShowPlayerStatusActionMsg();
		
	}
	
	/**
	 * {@inheritDoc}
	 * Returns {@link it.polimi.ingsw.cg28.view.messages.action.EndGameMsg} with scores and winner.
	 */
	@Override
	public ActionMsg endMsg() {
		
		return gameStats();
		
	}
	
	private EndGameMsg gameStats() {
		
		ScoreCalculator scoreCalculator = new ScoreCalculator(model);
		int [] scores = scoreCalculator.computeScore();
		
		return new EndGameMsg(scoreCalculator.produceRanking(scores), scoreCalculator.getWinner(scores).getID());
		
	}
	
	/**
	 * {@inheritDoc} Returns the current turn message required.
	 */
	@Override
	public EventMsg toDo() {
		
		EventMsg event = currentTurn.msgRequired();
		event.setPlayer(currentPlayer);
		return event;
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean inactivePlayer(PlayerID player) {
		
		turnController.inactivePlayer(player);
		
		return !turnController.onlyOnePlayer();
			
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public PlayerID getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * Returns the current turn.
	 * @return current GameTurn.
	 */
	public GameTurn getCurrentTurn() {
		return currentTurn;
	}
	
	/**
	 * Returns the model related to the game.
	 * @return model related to the game.
	 */
	public ModelStatus getModel() {
		return model;
	}
	
	/**
	 * Returns the list of players marked as inactive.
	 * @return Set of players marked as inactive.
	 */
	public Set<PlayerID> getInactivePlayers(){
		
		return turnController.getInactivePlayers();
		
	}

}
