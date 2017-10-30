package it.polimi.ingsw.cg28.view.cli;

import static org.junit.Assert.*;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.connections.RequestHandler;
import it.polimi.ingsw.cg28.connections.client.Client;
import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.controller.actions.bazaar.SellPermitTilesAction;
import it.polimi.ingsw.cg28.controller.bazaar.BazaarSellPlayerTurn;
import it.polimi.ingsw.cg28.controller.bonus.AssistantBonus;
import it.polimi.ingsw.cg28.controller.bonus.DrawCardBonus;
import it.polimi.ingsw.cg28.exception.InvalidActionException;
import it.polimi.ingsw.cg28.model.BazaarModel;
import it.polimi.ingsw.cg28.model.BusinessPermitTile;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.parser.ModelTestConfiguration;
import it.polimi.ingsw.cg28.view.messages.action.ActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.BazaarStartActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.BazaarStopActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.ChatActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.ConnectionActionMsg;
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
import it.polimi.ingsw.cg28.view.messages.action.turn.ElectionActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.TurnActionMsg;
import it.polimi.ingsw.cg28.view.messages.event.EventMsg;

public class FilledMsgHandlerTest {
	
	private static final Logger log = Logger.getLogger(Client.class.getName());
	
	class AsAPrintWriter extends PrintWriter {

		String string;
		
	public AsAPrintWriter(OutputStream out) {
			super(out);
			string = null;
		}
	
		public void print(String string){
			this.string = string;
		}
		
		public void println(String string){
			this.string = string;
		}
		
		public void flush(){
			return;
		}
		
	};
	
	class AsACommandReader extends CommandReader {
		
		boolean b;
		String input;
		
		public AsACommandReader(PrintWriter writer) {
			super(writer);
			input = "42";
			b = true;
		}
		
		public boolean nextBoolean(){
			return b;
		}
		
		public String nextLine(){
			
			String exInput = input;
			
			if(input.equals("42"))
				input = "1";
			
			return exInput;
			
		}
		
	};
	
	class AsABadCommandReader extends CommandReader {
		
		boolean b;
		String input;
		
		public AsABadCommandReader(PrintWriter writer) {
			super(writer);
			input = "1";
			b = true;
		}
		
		public boolean nextBoolean(){
			throw new InputMismatchException();
		}
		
		public String nextLine(){
			throw new InputMismatchException();
		}
		
	};
	
	class AsARequestHandler implements RequestHandler {
		
		ActionMsg message;
		
		@Override
		public PlayerID connect(String playerName) {
			return null;
		}

		@Override
		public void processRequest(ActionMsg request) {
			message = request;
		}

		@Override
		public EventMsg processConnectRequest(ConnectionActionMsg request) {
			return null;
		}
		
	};
	
	private FilledMsgHandler handler;
	private AsACommandReader reader;
	private AsABadCommandReader readerB;
	private AsAPrintWriter writer;
	private AsARequestHandler requestHandler;
	private CliManager manager;
	private ModelStatus model;
	private PlayerID [] players;
	
	@Before
	public void setTest(){
		
		log.setUseParentHandlers(false);
		
		players = new PlayerID[2];
		players[0] = new PlayerID("Player1");
		players[1] = new PlayerID("Player2");
		
		ModelTestConfiguration config = new ModelTestConfiguration();
		model = config.buildModelTest(players);
		
		requestHandler = new AsARequestHandler();
		writer =  new AsAPrintWriter(System.out);
		reader = new AsACommandReader(writer);
		readerB = new AsABadCommandReader(writer);
		
		manager = new CliManager(requestHandler, reader, players[0]);
		
		
	}

	@Test
	public void testFilledMsgHandler() {
		assertNotNull(new FilledMsgHandler(manager, reader));
	}
	
	@Test(expected = NullPointerException.class)
	public void testFilledMsgHandlerWithNullCliManager() {
		handler = new FilledMsgHandler(null, reader);
	}
	
	@Test(expected = NullPointerException.class)
	public void testFilledMsgHandlerWithNullReader() {
		handler = new FilledMsgHandler(manager, null);
	}
	
	@Test
	public void testFilledMsgHandlerHandleStartMsg() {
		
		handler = new FilledMsgHandler(manager, reader);
		
		ActionMsg msg = new StartMsg();
		msg.handle(handler);
		
		assertTrue(requestHandler.message.getClass().equals(StartMsg.class));
		assertTrue(((StartMsg) requestHandler.message).yesBazaar());
		
	}
	
	@Test
	public void testFilledMsgHandlerHandleStartMsgTestExceptions() {
		
		handler = new FilledMsgHandler(manager, readerB);
		
		ActionMsg msg = new StartMsg();
		msg.handle(handler);
		
		assertTrue(writer.string.equals("Invalid input! Type #fill again!"));
		
	}
	
	@Test
	public void testFilledMsgHandlerHandleTurnActionMsg() throws InvalidActionException {
		
		handler = new FilledMsgHandler(manager, reader);
		
		TurnActionMsg msg = new ElectionActionMsg(players[0]);
		msg.fill(model);
		
		msg.handle(handler);
		
		assertTrue(requestHandler.message.getClass().equals(ElectionActionMsg.class));
		assertTrue(((ElectionActionMsg) requestHandler.message).getBalconyCode() == 1);
		assertTrue(((ElectionActionMsg) requestHandler.message).getCouncilorCode() == 1);
		
	}
	
	@Test
	public void testFilledMsgHandlerHandleTurnActionMsgTestException() throws InvalidActionException {
		
		handler = new FilledMsgHandler(manager, readerB);
		
		TurnActionMsg msg = new ElectionActionMsg(players[0]);
		msg.fill(model);
		
		msg.handle(handler);
		
		assertTrue(writer.string.equals("Type another command!"));
		
		
	}
	
	@Test
	public void testFilledMsgHandlerBuySalableActionMsg() throws InvalidActionException {
		
		handler = new FilledMsgHandler(manager, reader);
		
		BazaarModel bazaar= new BazaarModel();
		BusinessPermitTile tile1;
		BusinessPermitTile tile2;
		
		String[] towns ={"Arkon","Juvelar"};
		tile1 = new BusinessPermitTile(new DrawCardBonus(2), towns);
		tile2 = new BusinessPermitTile(new AssistantBonus(2), towns);
		model.getPlayer(players[1]).getPossessedTiles().add(tile1);
		model.getPlayer(players[1]).getPossessedTiles().add(tile2);
		
		List<BusinessPermitTile> tiles = new ArrayList<>();
		tiles.add(tile1);
		tiles.add(tile2);
		
		boolean[] canSell = {true, true, true};
		bazaar.setCurrentTurn(new BazaarSellPlayerTurn(players[1], canSell));
		SellPermitTilesAction action = new SellPermitTilesAction(tiles, 2, bazaar);
		action.act(model.getPlayer(players[1]));
		
		BuySalableActionMsg msg = new BuySalableActionMsg(players[0]);
		msg.fill(model, bazaar);
		
		msg.handle(handler);
		
		assertTrue(requestHandler.message.getClass().equals(BuySalableActionMsg.class));
		assertTrue(((BuySalableActionMsg) requestHandler.message).getCodes()[0].equals("1"));
		
	}
	
	@Test
	public void testFilledMsgHandlerMsgNotToFill() {
		
		handler = new FilledMsgHandler(manager, reader);
		
		ActionMsg msg = new StartMsg();
		handler.handle(msg);
		
		msg = new QuitActionMsg();
		msg.handle(handler);
		assertTrue(requestHandler.message == null);
		
		msg = new EndTurnMsg(players[0]);
		msg.handle(handler);
		assertTrue(requestHandler.message == null);
		
		msg = new EndSellTurnMsg();
		msg.handle(handler);
		assertTrue(requestHandler.message == null);
		
		msg = new EndBuyTurnMsg();
		msg.handle(handler);
		assertTrue(requestHandler.message == null);
		
		msg = new BazaarStartActionMsg();
		msg.handle(handler);
		assertTrue(requestHandler.message == null);
		
		msg = new BazaarStopActionMsg();
		msg.handle(handler);
		assertTrue(requestHandler.message == null);
		
		msg = new ShowPlayerStatusActionMsg();
		msg.handle(handler);
		assertTrue(requestHandler.message == null);
		
		msg = new ShowBazaarStatusMsg();
		msg.handle(handler);
		assertTrue(requestHandler.message == null);
		
		msg = new ChatActionMsg("ChatMsg");
		msg.handle(handler);
		assertTrue(requestHandler.message == null);
		
		Map<PlayerID,Integer> map = new HashMap<>();
		map.put(players[0], 3);
		map.put(players[1], 2);
		EndGameMsg end = new EndGameMsg(map, players[0]);
		
		msg = end;
		msg.handle(handler);
		assertTrue(requestHandler.message == null);
		
		msg = new LeaveGameActionMsg("Leave");
		msg.handle(handler);
		assertTrue(requestHandler.message == null);
		
	}

}
