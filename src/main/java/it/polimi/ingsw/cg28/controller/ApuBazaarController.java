package it.polimi.ingsw.cg28.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.actions.DefaultActionFactory;
import it.polimi.ingsw.cg28.controller.bazaar.BazaarPlayerTurn;
import it.polimi.ingsw.cg28.controller.bazaar.BazaarTurnController;
import it.polimi.ingsw.cg28.controller.bazaar.ProductOnSale;
import it.polimi.ingsw.cg28.controller.bazaar.SoldStrategy;
import it.polimi.ingsw.cg28.exception.NoMoreTurnException;
import it.polimi.ingsw.cg28.model.BazaarModel;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.Player;
import it.polimi.ingsw.cg28.view.messages.action.ActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.BazaarStopActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.ShowBazaarStatusMsg;
import it.polimi.ingsw.cg28.view.messages.event.EventMsg;
import it.polimi.ingsw.cg28.view.messages.event.SimpleEventMsg;
import it.polimi.ingsw.cg28.view.messages.event.WelcomeBazaarEventMsg;

/**
 * Game controller (implements GameControllerInterface) to manage a BazaarTurn. It manages a single sell and buy turn 
 * for each player.
 * @author Mario
 *
 */
public class ApuBazaarController extends AbstractGameController {
	
	private static final Logger log = Logger.getLogger(Game.class.getName());
	
	private BazaarTurnController turnController;
	private BazaarModel bazaarModel;
	private BazaarPlayerTurn currentTurn;
	private PlayerID gameID;
	private Set<PlayerID> inactivePlayers;
	
	/**
	 * Constructor of class. Sets the controller and establishes what each player can sell
	 * @param model Model of the game on which bazaar is related to
	 * @param players List of players playing
	 * @param gameID PlayerID related to game (broadcast messages)
	 * @throws NullPointerException if one of the three parameter is null
	 */
	public ApuBazaarController(ModelStatus model, List<PlayerID> players, PlayerID gameID) {
		
		Preconditions.checkNotNull(model, "Model can't be null");
		Preconditions.checkNotNull(players, "Players list can't be null");
		Preconditions.checkNotNull(gameID, "GameID can't be null");
		
		this.model = model;
		this.gameID = gameID;
		
		Map<PlayerID, boolean[]> playersMap = setPlayersMap(model, players);
		inactivePlayers = new HashSet<>();
		
		this.turnController = new BazaarTurnController(players, playersMap);
		actionFactory = new DefaultActionFactory(model);
		
	}

	/**
	 * Initialise a Map to match to each player a boolean array of what can sell.
	 * @param model Model of the game on which bazaar is related to
	 * @param players List of players playing
	 * @return Map as specified
	 */
	private Map<PlayerID, boolean[]> setPlayersMap(ModelStatus model, List<PlayerID> players) {
		
		Map<PlayerID, boolean[]> playersMap = new HashMap<>();
		
		for(PlayerID player : players)
			playersMap.put(player, canSell(model.getPlayer(player)));
			
		return playersMap;
	}

	/**
	 * Establishes for a single player what can sell.
	 * @param player
	 * @return boolean array (boolean[0] is true if player can sell Assistants,
	 * 						  boolean[1] is true if player can sell Politic Cards,
	 * 						  boolean[2] is true if player can sell Permit Tiles)
	 */
	private boolean[] canSell(Player player) {
		
		boolean[] canSell = new boolean[3];
		
		canSell[0] = player.getAssistants().getValue()>0;
		canSell[1] = !player.getPoliticCardsHand().isEmpty();
		canSell[2] = !player.getPossessedTiles().isEmpty();
		
		return canSell;
	}
	
	/**
	 * Returns the welcome event message related to bazaar.
	 */
	@Override
	public EventMsg startGame() {
		
		bazaarModel = new BazaarModel();
		actionFactory.setBazaarModel(bazaarModel);
		return new WelcomeBazaarEventMsg();
		
	}
	
	/**
	 * Check if bazaar game is ended.
	 */
	@Override
	public boolean endGame() {
		
		if (turnController.isEmpty())
			return true;
		return false;
		
	}
	
	/**
	 * Get the message related to the next bazaar turn.
	 */
	@Override
	public EventMsg getNextTurn() {
		
		try{
			
			currentTurn = turnController.nextTurn();
			currentPlayer = currentTurn.getPlayer();
			bazaarModel.setCurrentTurn(currentTurn);
			
		} catch (NoMoreTurnException e){ 
			log.warning("BazaarTurn Queue is Empty!");
			log.log(Level.WARNING, e.getMessage(), e);
			return new SimpleEventMsg("Error!");
		}
			
		EventMsg event = currentTurn.msgRequired();
		event.setPlayer(currentPlayer);
		return event;
		
	}
	
	/**
	 * Returns the ActionMsg to update players on bazaar status after a player action.
	 */
	@Override
	public ActionMsg updateMsg() {
		
		return new ShowBazaarStatusMsg();
		
	}
	
	/**
	 * Returns the eventMsg related to the action bazaar Controller is waiting for.
	 */
	@Override
	public EventMsg toDo() {
		return currentTurn.msgRequired();
	}
	
	/**
	 * Returns PlayerID of current player. 
	 */
	@Override
	public PlayerID getCurrentPlayer() {
		return currentPlayer;
	}
	
	/**
	 * Returns the list of finalised transactions.
	 * @return
	 */
	public String gameStats() {
		return bazaarModel.getTransactions();
	}
	
	/**
	 * Returns the actionMsg to handle to end Bazaar Turn and returns unsold products to the respective player
	 */
	@Override
	public ActionMsg endMsg() {
		
		for(ProductOnSale<?> product : bazaarModel.getShelf()){
			SoldStrategy strategy = product.getSoldStrategy();
			strategy.sold(product, product.getOwner(), product.getOwner());
		}
		
		return new BazaarStopActionMsg();
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean inactivePlayer(PlayerID player) {
		
		inactivePlayers.add(player);
		turnController.deletePlayer(player);
		return false;
		
	}
	
	/**
	 * Returns bazaar Model
	 * @return BazaarModel
	 */
	public BazaarModel getBazaarModel() {
		return bazaarModel;
	}
	
	/**
	 * Returns the current turn
	 * @return BazaarPlayerTurn current turn
	 */
	public BazaarPlayerTurn getCurrentTurn() {
		return currentTurn;
	}
	
	/**
	 * Returns PlayerID related to game to identify broadcast messages
	 * @return PlayerID related to game
	 */
	public PlayerID getGameID() {
		return gameID;
	}
	
	
	/**
	 * Returns the list of players marked as inactive.
	 * @return Set of players marked as inactive.
	 */
	public Set<PlayerID> getInactivePlayers() {
		return inactivePlayers;
	}

	

}
