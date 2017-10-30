package it.polimi.ingsw.cg28.controller;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.connections.server.ActionMsgController;
import it.polimi.ingsw.cg28.connections.server.Broker;
import it.polimi.ingsw.cg28.connections.server.ControllerSingleGame;
import it.polimi.ingsw.cg28.connections.server.InteractionComponent;
import it.polimi.ingsw.cg28.controller.ActionMsgHandler;
import it.polimi.ingsw.cg28.controller.ApuBazaarController;
import it.polimi.ingsw.cg28.controller.Game;
import it.polimi.ingsw.cg28.controller.GameController;
import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.controller.PlayerTurn;
import it.polimi.ingsw.cg28.controller.actions.MainAction;
import it.polimi.ingsw.cg28.controller.actions.bazaar.SellAssistantsAction;
import it.polimi.ingsw.cg28.controller.bazaar.BazaarBuyPlayerTurn;
import it.polimi.ingsw.cg28.controller.bazaar.BazaarSellPlayerTurn;
import it.polimi.ingsw.cg28.exception.InvalidActionException;
import it.polimi.ingsw.cg28.model.Councillor;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.Player;
import it.polimi.ingsw.cg28.view.messages.action.ActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.BazaarStartActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.BazaarStopActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.ChatActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.EndBuyTurnMsg;
import it.polimi.ingsw.cg28.view.messages.action.EndGameMsg;
import it.polimi.ingsw.cg28.view.messages.action.EndSellTurnMsg;
import it.polimi.ingsw.cg28.view.messages.action.EndTurnMsg;
import it.polimi.ingsw.cg28.view.messages.action.LeaveGameActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.QuitActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.ShowBazaarStatusMsg;
import it.polimi.ingsw.cg28.view.messages.action.ShowPlayerStatusActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.StartMsg;
import it.polimi.ingsw.cg28.view.messages.action.bazaar.BuySalableActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.bazaar.SellAssistantsActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.ElectionActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.EmporiumTileActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.TurnActionMsg;
import it.polimi.ingsw.cg28.view.messages.event.BazaarStatusMsg;
import it.polimi.ingsw.cg28.view.messages.event.ChatEventMsg;
import it.polimi.ingsw.cg28.view.messages.event.EndGameEventMsg;
import it.polimi.ingsw.cg28.view.messages.event.EventMsg;
import it.polimi.ingsw.cg28.view.messages.event.FilledMsg;
import it.polimi.ingsw.cg28.view.messages.event.GiveMeActionMsg;
import it.polimi.ingsw.cg28.view.messages.event.GiveMeBuyActionMsg;
import it.polimi.ingsw.cg28.view.messages.event.GiveMeSellActionMsg;
import it.polimi.ingsw.cg28.view.messages.event.PlayerStatusMsg;
import it.polimi.ingsw.cg28.view.messages.event.SimpleEventMsg;
import it.polimi.ingsw.cg28.view.messages.event.StartEventMsg;

public class ActionMsgHandlerTest {
	
	private static final Logger log = Logger.getLogger(Game.class.getName());
	
	class AsABroker extends Broker {
		
		private EventMsg message;
		
		public synchronized void publish(EventMsg message, String topic) {
			this.message = message;
		};
		
		public void subscribeClientToTopic(String topic, PlayerID player) {
			return;
		}
		
		public EventMsg getMessage(){
			return message;
		}
		
	};
	
	class AsAController extends ActionMsgController {

		public AsAController(ControllerSingleGame singleGame, InteractionComponent interactionComponent) {
			super(singleGame, interactionComponent);
		}
		
		public void handle(LeaveGameActionMsg leaveGameActionMsg){
			return;
		}
		
		public void handle(EndGameMsg endGameMsg){
			return;
		}
		
		public void unsubscribeInactivePlayer(PlayerID player){
			return;
		}
		
	}
	
	private Player[] players;
	private ModelStatus model;
	private List<PlayerID> playerIDs;
	private ActionMsgHandler actionMsgHandler;
	private Game game;
	private AsABroker asABroker;

	@Before
	public void before() throws IOException{
		
		log.setUseParentHandlers(false);
		
		playerIDs = new ArrayList<>();
		playerIDs.add(new PlayerID("Player1"));
		playerIDs.add(new PlayerID("Player2"));
		
		asABroker = new AsABroker();
		
		ControllerSingleGame singleGame = new ControllerSingleGame(asABroker, null);
		game = new Game("GAMETEST",playerIDs, new AsAController(singleGame, null));
		
		singleGame.registerObserver(game);
		game.registerObserver(singleGame);
		
		actionMsgHandler = game.getActionMsgHandler();
		
	}
	
	@Test
	public void handleStartMsgTest(){
		
		game.startGame();
		assertNull(game.getGameController());
		
		assertTrue(asABroker.getMessage().getClass().equals(StartEventMsg.class));
		
		ActionMsg actionMsg = new StartMsg();
		actionMsgHandler.handle(actionMsg); //Generic Action Msg not handled
		
		assertTrue(game.getGameController() == null);
		
		assertFalse(asABroker.getMessage().getClass().equals(GiveMeActionMsg.class));
			
		game.update(actionMsg);
		
		assertNotNull(game.getGameController());
		assertNotNull(actionMsgHandler.getGameController().getModel());
		
		assertTrue(asABroker.getMessage().getClass().equals(GiveMeActionMsg.class));
		
	}
	
	@Test
	public void handleTurnActionMsgTest(){
		
		actionMsgHandler.handle(new StartMsg());
		model = actionMsgHandler.getGameController().getModel();
		players = model.getPlayers();
		
		TurnActionMsg actionMsg = new ElectionActionMsg(game.getGameController().getCurrentPlayer());
		
		Councillor exCouncillor = model.getRegion(0).getBalcony().getCouncillor(0);
		
		assertFalse(actionMsg.isFilled());
		game.update(actionMsg);
		assertTrue(actionMsg.isFilled());
		
		assertTrue(asABroker.getMessage().getClass().equals(FilledMsg.class));
		
		TurnActionMsg msg = (TurnActionMsg) ((FilledMsg) asABroker.getMessage()).getActionMsg();
		
		String[] codes ={"1","1"};
		assertTrue(msg.setCodes(codes));
		
		game.update(actionMsg);
		
		assertFalse(exCouncillor == model.getRegion(0).getBalcony().getCouncillor(0));
		
	}
	
	@Test
	public void handleTurnActionMsgTestIsBonus(){
		
		actionMsgHandler.handle(new StartMsg());
		model = actionMsgHandler.getGameController().getModel();
		players = model.getPlayers();
		
		model.getPlayer(game.getGameController().getCurrentPlayer()).getTurn().setBonus(true);
		
		TurnActionMsg actionMsg = new ElectionActionMsg(game.getGameController().getCurrentPlayer());
		
		assertFalse(actionMsg.isFilled());
		game.update(actionMsg);
		assertFalse(actionMsg.isFilled());
		
		assertTrue(asABroker.getMessage().getClass().equals(SimpleEventMsg.class));
		assertTrue(((SimpleEventMsg)asABroker.getMessage()).getString().equals("Fill the bonus msg first"));
		
		
	}
	
	@Test
	public void handleTurnActionMsgTestInvalidFill(){
		
		actionMsgHandler.handle(new StartMsg());
		model = actionMsgHandler.getGameController().getModel();
		players = model.getPlayers();
		
		TurnActionMsg actionMsg = new EmporiumTileActionMsg(game.getGameController().getCurrentPlayer());
		
		assertFalse(actionMsg.isFilled());
		game.update(actionMsg);
		assertFalse(actionMsg.isFilled());
		
		assertTrue(asABroker.getMessage().getClass().equals(GiveMeActionMsg.class));
		
	}

	@Test
	public void handleBuySalableActionMsgTest(){
		
		actionMsgHandler.handle(new StartMsg());
		model = actionMsgHandler.getGameController().getModel();
		players = model.getPlayers();
		
		ActionMsg actionMsg = new BazaarStartActionMsg();
		game.update(actionMsg);
		
		players[0].getAssistants().setValue(10);
		players[1].getAssistants().setValue(10);
		
		actionMsgHandler.getBazaarController().getNextTurn();
		SellAssistantsAction action = new SellAssistantsAction(3, 2, actionMsgHandler.getBazaarController().getBazaarModel());
		action.act(players[0]);
		
		actionMsgHandler.getBazaarController().getNextTurn();
		SellAssistantsAction action2 = new SellAssistantsAction(3, 2, actionMsgHandler.getBazaarController().getBazaarModel());
		action2.act(players[1]);
		
		BuySalableActionMsg actionBuyMsg = new BuySalableActionMsg(game.getGameController().getCurrentPlayer());
		
		assertFalse(actionBuyMsg.isFilled());
		actionBuyMsg.handle(actionMsgHandler);
		assertTrue(actionBuyMsg.isFilled());
		
		assertTrue(asABroker.getMessage().getClass().equals(FilledMsg.class));
		
	}
	
	@Test
	public void handleBuySalableActionMsgTestFilled(){
		
		actionMsgHandler.handle(new StartMsg());
		model = actionMsgHandler.getGameController().getModel();
		players = model.getPlayers();
		
		ActionMsg actionMsg = new BazaarStartActionMsg();
		game.update(actionMsg);
		
		players[0].getAssistants().setValue(10);
		players[1].getAssistants().setValue(10);
		
		actionMsgHandler.getBazaarController().getNextTurn();
		SellAssistantsAction action = new SellAssistantsAction(3, 2, actionMsgHandler.getBazaarController().getBazaarModel());
		action.act(players[0]);
		
		actionMsgHandler.getBazaarController().getNextTurn();
		SellAssistantsAction action2 = new SellAssistantsAction(3, 2, actionMsgHandler.getBazaarController().getBazaarModel());
		action2.act(players[1]);
		
		BuySalableActionMsg actionBuyMsg = new BuySalableActionMsg(game.getGameController().getCurrentPlayer());
		
		assertFalse(actionBuyMsg.isFilled());
		actionBuyMsg.handle(actionMsgHandler);
		assertTrue(actionBuyMsg.isFilled());
		
		String[] codes = {"1"};
		actionBuyMsg.setCodes(codes);
		actionBuyMsg.handle(actionMsgHandler);
		
		assertTrue(asABroker.getMessage().getClass().equals(GiveMeBuyActionMsg.class));
		
	}
	
	@Test
	public void handleBuySalableActionMsgTestInvalidFill(){
		
		actionMsgHandler.handle(new StartMsg());
		model = actionMsgHandler.getGameController().getModel();
		players = model.getPlayers();
		
		ActionMsg actionMsg = new BazaarStartActionMsg();
		game.update(actionMsg);
		
		actionMsgHandler.getBazaarController().getNextTurn();
		actionMsgHandler.getBazaarController().getNextTurn();
		
		BuySalableActionMsg actionBuyMsg = new BuySalableActionMsg(game.getGameController().getCurrentPlayer());
		
		assertFalse(actionBuyMsg.isFilled());
		actionBuyMsg.handle(actionMsgHandler);
		assertFalse(actionBuyMsg.isFilled());
		
		assertTrue(asABroker.getMessage().getClass().equals(GiveMeBuyActionMsg.class));
		
	}
	
	@Test
	public void handleQuitActionMsgTest(){
	
		actionMsgHandler.handle(new StartMsg());
		model = actionMsgHandler.getGameController().getModel();
		players = model.getPlayers();
		
		ActionMsg actionMsg = new QuitActionMsg();
		game.update(actionMsg);
		
		assertTrue(asABroker.getMessage().getClass().equals(GiveMeActionMsg.class));
		
	}
	
	@Test
	public void handleEndTurnMsgTest(){
		
		actionMsgHandler.handle(new StartMsg());
		model = actionMsgHandler.getGameController().getModel();
		players = model.getPlayers();
		
		PlayerID exCurrentPlayer = game.getGameController().getCurrentPlayer();
		PlayerTurn exTurn = ((PlayerTurn) actionMsgHandler.getGameController().getCurrentTurn());
		exTurn.takeAction(new MainAction());
		
		ActionMsg actionMsg = new EndTurnMsg(game.getGameController().getCurrentPlayer());
		game.update(actionMsg);
		
		assertFalse(exCurrentPlayer.equals(game.getGameController().getCurrentPlayer()));
		assertFalse(exTurn.equals(actionMsgHandler.getGameController().getCurrentTurn()));
		
		assertTrue(asABroker.getMessage().getClass().equals(GiveMeActionMsg.class));
		
	}
	
	@Test
	public void handleEndTurnMsgTestCanNotEnd(){
		
		actionMsgHandler.handle(new StartMsg());
		model = actionMsgHandler.getGameController().getModel();
		players = model.getPlayers();
		
		PlayerID exCurrentPlayer = game.getGameController().getCurrentPlayer();
		PlayerTurn exTurn = ((PlayerTurn) actionMsgHandler.getGameController().getCurrentTurn());
		
		ActionMsg actionMsg = new EndTurnMsg(game.getGameController().getCurrentPlayer());
		game.update(actionMsg);
		
		assertTrue(exCurrentPlayer.equals(game.getGameController().getCurrentPlayer()));
		assertTrue(exTurn.equals(actionMsgHandler.getGameController().getCurrentTurn()));
		
		assertTrue(asABroker.getMessage().getClass().equals(SimpleEventMsg.class));
		
	}
	
	
	@Test
	public void handleEndSellTurnMsgTest(){
		
		actionMsgHandler.handle(new StartMsg());
		model = actionMsgHandler.getGameController().getModel();
		players = model.getPlayers();
		
		ActionMsg actionMsg = new BazaarStartActionMsg();
		game.update(actionMsg);
		
		PlayerID exCurrentPlayer = game.getGameController().getCurrentPlayer();
		assertTrue(actionMsgHandler.getBazaarController().getCurrentTurn().getClass().equals(BazaarSellPlayerTurn.class));
		BazaarSellPlayerTurn exTurn = ((BazaarSellPlayerTurn) actionMsgHandler.getBazaarController().getCurrentTurn());
		
		EndSellTurnMsg actionSellMsg = new EndSellTurnMsg();
		actionSellMsg.setPlayerID(actionMsgHandler.getBazaarController().getCurrentPlayer());
		actionSellMsg.handle(actionMsgHandler);
		
		assertFalse(exCurrentPlayer.equals(game.getGameController().getCurrentPlayer()));
		assertFalse(exTurn.equals(actionMsgHandler.getBazaarController().getCurrentTurn()));
		
		assertTrue(asABroker.getMessage().getClass().equals(GiveMeSellActionMsg.class));
		
	}
	
	@Test
	public void handleEndBuyTurnMsgTest(){
		
		actionMsgHandler.handle(new StartMsg());
		model = actionMsgHandler.getGameController().getModel();
		players = model.getPlayers();
		
		ActionMsg actionMsg = new BazaarStartActionMsg();
		game.update(actionMsg);
		
		actionMsgHandler.getBazaarController().getNextTurn();
		actionMsgHandler.getBazaarController().getNextTurn();
		
		PlayerID exCurrentPlayer = game.getGameController().getCurrentPlayer();
		assertTrue(actionMsgHandler.getBazaarController().getCurrentTurn().getClass().equals(BazaarBuyPlayerTurn.class));
		BazaarBuyPlayerTurn exTurn = ((BazaarBuyPlayerTurn) actionMsgHandler.getBazaarController().getCurrentTurn());
		
		EndBuyTurnMsg actionBuyMsg = new EndBuyTurnMsg();
		actionBuyMsg.setPlayerID(actionMsgHandler.getBazaarController().getCurrentPlayer());
		actionBuyMsg.handle(actionMsgHandler);
		
		assertFalse(exCurrentPlayer.equals(game.getGameController().getCurrentPlayer()));
		assertFalse(exTurn.equals(actionMsgHandler.getBazaarController().getCurrentTurn()));
		
		assertTrue(asABroker.getMessage().getClass().equals(GiveMeBuyActionMsg.class));	
		
	}
	
	@Test
	public void handleBazaarStartActionMsg(){
		
		actionMsgHandler.handle(new StartMsg());
		model = actionMsgHandler.getGameController().getModel();
		players = model.getPlayers();
		
		assertTrue(game.getGameController().getClass().equals(GameController.class));
		assertNull(actionMsgHandler.getBazaarController());
		ActionMsg actionMsg = new BazaarStartActionMsg();
		game.update(actionMsg);
		assertNotNull(actionMsgHandler.getBazaarController());
		assertTrue(game.getGameController().getClass().equals(ApuBazaarController.class));
		
		assertTrue(asABroker.getMessage().getClass().equals(GiveMeSellActionMsg.class));	
		
	}
	
	@Test
	public void handleBazaarStartActionMsgWithInactivePlayers(){
		
		actionMsgHandler.handle(new StartMsg());
		model = actionMsgHandler.getGameController().getModel();
		players = model.getPlayers();
		
		assertTrue(game.getGameController().getClass().equals(GameController.class));
		assertNull(actionMsgHandler.getBazaarController());
		
		actionMsgHandler.getGameController().inactivePlayer(players[0].getID());
		
		ActionMsg actionMsg = new BazaarStartActionMsg();
		game.update(actionMsg);
		assertNotNull(actionMsgHandler.getBazaarController());
		assertTrue(game.getGameController().getClass().equals(ApuBazaarController.class));
		
		EventMsg msg = asABroker.getMessage();
		assertFalse(msg.getPlayer().equals(players[0].getID()));
		
		EventMsg msgBuy = actionMsgHandler.getBazaarController().getNextTurn();
		assertFalse(msgBuy.getPlayer().equals(players[0].getID()));
		
		assertTrue(actionMsgHandler.getBazaarController().endGame());	
		
	}
	
	@Test
	public void handleBazaarStopActionMsg(){
		
		actionMsgHandler.handle(new StartMsg());
		model = actionMsgHandler.getGameController().getModel();
		players = model.getPlayers();
		
		actionMsgHandler.handle(new BazaarStartActionMsg());
		assertTrue(game.getGameController().getClass().equals(ApuBazaarController.class));
		assertNotNull(actionMsgHandler.getBazaarController());
		ActionMsg actionMsg = new BazaarStopActionMsg();
		game.update(actionMsg);
		assertNull(actionMsgHandler.getBazaarController());
		assertTrue(game.getGameController().getClass().equals(GameController.class));
		
		assertTrue(asABroker.getMessage().getClass().equals(GiveMeActionMsg.class));	
		
	}
	
	@Test
	public void handleBazaarStopActionMsgWithInactivePlayers(){
		
		actionMsgHandler.handle(new StartMsg());
		model = actionMsgHandler.getGameController().getModel();
		players = model.getPlayers();
		
		actionMsgHandler.getGameController().getNextTurn();
		actionMsgHandler.getGameController().getNextTurn();
		
		actionMsgHandler.handle(new BazaarStartActionMsg());
		assertTrue(game.getGameController().getClass().equals(ApuBazaarController.class));
		assertNotNull(actionMsgHandler.getBazaarController());
		actionMsgHandler.getBazaarController().inactivePlayer(players[0].getID());
		ActionMsg actionMsg = new BazaarStopActionMsg();
		game.update(actionMsg);
		assertNull(actionMsgHandler.getBazaarController());
		assertTrue(game.getGameController().getClass().equals(GameController.class));
		
		actionMsgHandler.getGameController().getNextTurn();
		
		assertFalse(((GiveMeActionMsg)  actionMsgHandler.getGameController().getNextTurn()).getPlayer().equals(players[0].getID()));
		assertTrue(game.getGameController().getNextTurn() == null);
		
		
	}
	
	@Test
	public void handleBazaarActionMsg() throws InvalidActionException{
		
		actionMsgHandler.handle(new StartMsg());
		model = actionMsgHandler.getGameController().getModel();
		players = model.getPlayers();
		
		actionMsgHandler.handle(new BazaarStartActionMsg());
		
		model.getPlayer(game.getGameController().getCurrentPlayer()).getAssistants().setValue(10);
		
		TurnActionMsg actionMsg = new SellAssistantsActionMsg(game.getGameController().getCurrentPlayer());
		
		assertFalse(actionMsg.isFilled());
		game.update(actionMsg);
		assertTrue(actionMsg.isFilled());
		
		String[] codes = {"3","1"};
		assertTrue(actionMsg.setCodes(codes));
		
		actionMsgHandler.handle(actionMsg);
		
		assertTrue(model.getPlayer(game.getGameController().getCurrentPlayer()).getAssistants().getValue() == 7);
		
		assertTrue(asABroker.getMessage().getClass().equals(GiveMeSellActionMsg.class));	
	}

	@Test
	public void handleShowPlayerStatusActionMsgTest(){
		
		actionMsgHandler.handle(new StartMsg());
		model = actionMsgHandler.getGameController().getModel();
		players = model.getPlayers();
		
		ActionMsg actionMsg = new ShowPlayerStatusActionMsg();
		game.update(actionMsg);
		
		assertTrue(asABroker.getMessage().getClass().equals(PlayerStatusMsg.class));
	}
	
	@Test
	public void handleShowBazaarStatusMsgTest(){
		
		actionMsgHandler.handle(new StartMsg());
		model = actionMsgHandler.getGameController().getModel();
		players = model.getPlayers();
		
		actionMsgHandler.handle(new BazaarStartActionMsg()); 
		
		ActionMsg actionMsg = new ShowBazaarStatusMsg();
		game.update(actionMsg);
		
		assertTrue(asABroker.getMessage().getClass().equals(BazaarStatusMsg.class));
	}
	
	@Test
	public void handleLeaveGameActionMsgTestCurrentPlayer(){
		
		actionMsgHandler.handle(new StartMsg());
		model = actionMsgHandler.getGameController().getModel();
		players = model.getPlayers();
		
		ActionMsg actionMsg = new LeaveGameActionMsg("Bye");
		PlayerID exCurrentPlayer = game.getGameController().getCurrentPlayer();
		
		actionMsg.setPlayerID(exCurrentPlayer);
		game.update(actionMsg);
		
		assertTrue(asABroker.getMessage().getClass().equals(EndGameEventMsg.class));
		
	}
	
	@Test
	public void handleLeaveGameActionMsgTestNoCurrentPlayer(){
		
		actionMsgHandler.handle(new StartMsg());
		model = actionMsgHandler.getGameController().getModel();
		players = model.getPlayers();
		
		ActionMsg actionMsg = new LeaveGameActionMsg("Bye");
		PlayerID currentPlayer = game.getGameController().getCurrentPlayer();
		
		for(PlayerID player: game.getPlayers()){
			if (!player.equals(currentPlayer)){
				actionMsg.setPlayerID(player);
				break;
			}
		}
		
		game.update(actionMsg);
		
		assertTrue(currentPlayer.equals(game.getGameController().getCurrentPlayer()));
		assertTrue(asABroker.getMessage().getClass().equals(SimpleEventMsg.class));
		
		PlayerTurn exTurn = ((PlayerTurn) actionMsgHandler.getGameController().getCurrentTurn());
		exTurn.takeAction(new MainAction());
		
		actionMsgHandler.handle(new EndTurnMsg(currentPlayer));
		
		assertTrue(asABroker.getMessage().getClass().equals(EndGameEventMsg.class));
		assertTrue(asABroker.getMessage().getPlayer().equals(game.getGameID()));

	}
	
	@Test
	public void handleChatActionMsgTest(){
		
		actionMsgHandler.handle(new StartMsg());
		model = actionMsgHandler.getGameController().getModel();
		players = model.getPlayers();
		
		ActionMsg actionMsg = new ChatActionMsg("Chat");
		game.update(actionMsg);
		
		assertTrue(asABroker.getMessage().getClass().equals(ChatEventMsg.class));
		assertTrue(((ChatEventMsg)asABroker.getMessage()).getString().equals("Chat"));
		assertTrue(asABroker.getMessage().getPlayer().equals(game.getGameID()));
		
	}
	
	@Test
	public void handleEndGameMsgTest(){
		
		actionMsgHandler.handle(new StartMsg());
		model = actionMsgHandler.getGameController().getModel();
		players = model.getPlayers();
		
		ActionMsg actionMsg = new EndGameMsg(null, null);
		actionMsg.handle(actionMsgHandler);
		
		assertTrue(asABroker.getMessage().getClass().equals(EndGameEventMsg.class));
		assertTrue(true);
		
	}
	
}
