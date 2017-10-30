package it.polimi.ingsw.cg28.view.cli;

import static org.junit.Assert.*;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.connections.RequestHandler;
import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.model.BazaarModel;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.parser.ModelTestConfiguration;
import it.polimi.ingsw.cg28.view.StringJar;
import it.polimi.ingsw.cg28.view.ViewHandler;
import it.polimi.ingsw.cg28.view.messages.action.ActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.ConnectionActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.EndGameMsg;
import it.polimi.ingsw.cg28.view.messages.action.StartMsg;
import it.polimi.ingsw.cg28.view.messages.event.BazaarStatusMsg;
import it.polimi.ingsw.cg28.view.messages.event.ChatEventMsg;
import it.polimi.ingsw.cg28.view.messages.event.EndBazaarMsg;
import it.polimi.ingsw.cg28.view.messages.event.EndBuyTurnEventMsg;
import it.polimi.ingsw.cg28.view.messages.event.EndGameEventMsg;
import it.polimi.ingsw.cg28.view.messages.event.EndSellTurnEventMsg;
import it.polimi.ingsw.cg28.view.messages.event.EndTurnEventMsg;
import it.polimi.ingsw.cg28.view.messages.event.EventMsg;
import it.polimi.ingsw.cg28.view.messages.event.FilledMsg;
import it.polimi.ingsw.cg28.view.messages.event.GiveMeActionMsg;
import it.polimi.ingsw.cg28.view.messages.event.GiveMeBuyActionMsg;
import it.polimi.ingsw.cg28.view.messages.event.GiveMeSellActionMsg;
import it.polimi.ingsw.cg28.view.messages.event.PlayerStatusMsg;
import it.polimi.ingsw.cg28.view.messages.event.SimpleEventMsg;
import it.polimi.ingsw.cg28.view.messages.event.StartEventMsg;
import it.polimi.ingsw.cg28.view.messages.event.TableStatusMsg;
import it.polimi.ingsw.cg28.view.messages.event.WelcomeBazaarEventMsg;
import it.polimi.ingsw.cg28.view.messages.event.WelcomeEventMsg;

public class CliHandlerTest {
	
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

		public AsACommandReader(PrintWriter writer) {
			super(writer);
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
	
	private ViewHandler handler;
	private AsACommandReader reader;
	private AsAPrintWriter writer;
	private AsARequestHandler requestHandler;
	private CliPrinter printer;
	private CliPainter painter;
	private StringJar stringJar;
	private ModelStatus model;
	private PlayerID [] players;
	
	@Before
	public void setTest(){
		
		players = new PlayerID[2];
		players[0] = new PlayerID("Player1");
		players[1] = new PlayerID("Player2");
		
		ModelTestConfiguration config = new ModelTestConfiguration();
		model = config.buildModelTest(players);
		
		requestHandler = new AsARequestHandler();
		writer =  new AsAPrintWriter(System.out);
		reader = new AsACommandReader(writer);
		printer = new CliPrinter();
		painter = new CliPainter(writer);
		handler = new CliHandler(reader);
		handler.initialize(requestHandler, players[0]);
		
		stringJar = new StringJar();
		
	}
	
	@Test
	public void testCliHandler(){
		assertNotNull(new CliHandler(reader));
	}
	
	@Test(expected = NullPointerException.class)
	public void testCliHandlerWithNullReader(){
		handler = new CliHandler(null);
	}
	
	@Test
	public void testHandleStartEventMsg(){
		
		EventMsg msg = new StartEventMsg();
		msg.read(handler);
		
		assertTrue(writer.string.equals(stringJar.startMsg()));
	
	}
	
	@Test
	public void testHandleGiveMeActionMsg() {
		
		boolean[] canDo = {true, false};
		EventMsg msg = new GiveMeActionMsg(players[0], canDo);
		msg.read(handler);
		
		assertTrue(reader.canDoMainAction());
		assertFalse(reader.canDoQuickAction());
		
		assertTrue(writer.string.equals(stringJar.getMainActionMessageRequest()));
		
		boolean[] canDoAll = {true, true};
		msg = new GiveMeActionMsg(players[0], canDoAll);
		msg.read(handler);
		
		assertTrue(reader.canDoMainAction());
		assertTrue(reader.canDoQuickAction());
		
		assertTrue(writer.string.equals(stringJar.getMainAndQuickActionsRequest()));
		
		boolean[] canDoOnlyQuick = {false, true};
		msg = new GiveMeActionMsg(players[0], canDoOnlyQuick);
		msg.read(handler);
		
		assertFalse(reader.canDoMainAction());
		assertTrue(reader.canDoQuickAction());
		
		assertTrue(writer.string.equals(stringJar.getQuickActionRequest()));
			
	}

	@Test
	public void testHandleWelcomeEventMsg() {
		
		EventMsg msg = new WelcomeEventMsg(model);
		msg.read(handler);
		
		WelcomeEventMsg welcome = (WelcomeEventMsg) msg;
		painter.paint(welcome);
		assertTrue(writer.string.equals(printer.printTNobilityTrack(welcome.getNobilityTrack())));

	}

	@Test
	public void testHandleTableStatusMsg() {
		
		EventMsg msg = new TableStatusMsg(model, players[0]);
		msg.read(handler);
		
		TableStatusMsg table = (TableStatusMsg) msg;
		assertTrue(writer.string.equals("Current Turn PLAYER: " + table.getCurrentTurnPlayer()));
		
	}

	@Test
	public void testHandleFilledMsg() {
		
		EventMsg msg = new FilledMsg(new StartMsg());
		msg.read(handler);
		
		assertTrue(writer.string.equals(stringJar.fillMessage()));
		
	}
	
	@Test
	public void testHandlePlayerStatusMsg() {
		
		EventMsg msg = new PlayerStatusMsg(model, players[0], players[0]);
		msg.read(handler);
		
		PlayerStatusMsg playerMsg = (PlayerStatusMsg) msg;
		
		StringBuilder sb = new StringBuilder();
		sb.append("TURN PLAYER: " + playerMsg.getPlayer().getName() +"\n\n");
		sb.append(printer.printPlayer(playerMsg.getTPlayer(), true));
		
		assertTrue(writer.string.equals(sb.toString()));
		
	}
	
	@Test
	public void testHandleSimpleEventMsg() {
		
		EventMsg msg = new SimpleEventMsg("Test");
		msg.read(handler);
		
		assertTrue(writer.string.equals("Test"));
	}
	
	@Test
	public void testHandleGiveMeSellActionMsg() {
		
		boolean[] canSell = {true, false, true};
		EventMsg msg = new GiveMeSellActionMsg(players[0], canSell);
		msg.read(handler);
		
		GiveMeSellActionMsg eventMsg = (GiveMeSellActionMsg) msg;
		assertTrue(writer.string.equals(stringJar.getBazaarSellRequest(eventMsg.canSellAssistant(),
				eventMsg.canSellPoliticCards(), eventMsg.canSellPermitTiles())));
		
		assertTrue(reader.canSellAssistant());
		assertFalse(reader.canSellPoliticCards());
		assertTrue(reader.canSellPermitTiles());
		
		boolean[] canSellSecond = {false, true, false};
		msg = new GiveMeSellActionMsg(players[0], canSellSecond);
		msg.read(handler);
		
		eventMsg = (GiveMeSellActionMsg) msg;
		assertTrue(writer.string.equals(stringJar.getBazaarSellRequest(eventMsg.canSellAssistant(),
				eventMsg.canSellPoliticCards(), eventMsg.canSellPermitTiles())));
		
		assertFalse(reader.canSellAssistant());
		assertTrue(reader.canSellPoliticCards());
		assertFalse(reader.canSellPermitTiles());
		
	}

	@Test
	public void testHandleGiveMeBuyActionMsg() {
		
		EventMsg msg = new GiveMeBuyActionMsg(players[0]);
		msg.read(handler);
		
		assertTrue(writer.string.equals(stringJar.buyMessage()));
		
		assertTrue(reader.canBuy());
	}
	
	@Test
	public void testHandleEndBazaarMsg() {
		
		EventMsg msg = new EndBazaarMsg("Transactions List");
		msg.read(handler);
		
		assertTrue(writer.string.equals("Transactions List"));
		
	}
	@Test
	public void testHandleWelcomeBazaarEventMsg() {
		
		EventMsg msg = new WelcomeBazaarEventMsg();
		msg.read(handler);
		
		WelcomeBazaarEventMsg welcome = (WelcomeBazaarEventMsg) msg;
		assertTrue(writer.string.equals(welcome.getString()));
		
	}
	
	@Test
	public void testHandleBazaarStatusMsg() {
		
		EventMsg msg = new BazaarStatusMsg(new BazaarModel(), players[0]);
		msg.read(handler);
		
		assertTrue(writer.string.equals("\nSHELF:"));
		
	}

	@Test
	public void testHandleChatEventMsg() {
		
		EventMsg msg = new ChatEventMsg("Prova");
		msg.read(handler);
		
		ChatEventMsg chat = (ChatEventMsg) msg;
		assertTrue(writer.string.equals(chat.getString()));
		
	}
	
	@Test
	public void testHandleEndTurnEventMsg() {
		
		EventMsg msg = new EndTurnEventMsg(players[0], "Your turn is ended!");
		msg.read(handler);
		
		EndTurnEventMsg end = (EndTurnEventMsg) msg;
		assertTrue(writer.string.equals(end.getString()));
		
		assertFalse(reader.canDoMainAction());
		assertFalse(reader.canDoQuickAction());
		
	}
	
	@Test
	public void testHandleEndSellTurnEventMsg() {
		
		reader.setCanSellAssistant(true);
		reader.setCanSellPermitTiles(true);
		reader.setCanSellPoliticCards(true);
		
		assertTrue(reader.canSellAssistant());
		assertTrue(reader.canSellPermitTiles());
		assertTrue(reader.canSellPoliticCards());
		
		EventMsg msg = new EndSellTurnEventMsg(players[0], "Your sell turn is ended!");
		msg.read(handler);
		
		EndSellTurnEventMsg end = (EndSellTurnEventMsg) msg;
		assertTrue(writer.string.equals(end.getString()));
		
		assertFalse(reader.canSellAssistant());
		assertFalse(reader.canSellPermitTiles());
		assertFalse(reader.canSellPoliticCards());
		
	}
	
	@Test
	public void testHandleEndBuyTurnEventMsg() {
		
		reader.setCanBuy(true);
		assertTrue(reader.canBuy());
		
		EventMsg msg = new EndBuyTurnEventMsg(players[0], "Your buy turn is ended!");
		msg.read(handler);
		
		EndBuyTurnEventMsg end = (EndBuyTurnEventMsg) msg;
		assertTrue(writer.string.equals(end.getString()));
		
		assertFalse(reader.canBuy());
		
	}
	
	@Test
	public void testHandleEndGameEventMsg() {
		
		Map<PlayerID,Integer> map = new HashMap<>();
		map.put(players[0], 3);
		map.put(players[1], 2);
		EndGameMsg end = new EndGameMsg(map, players[0]);
		
		EventMsg msg = new EndGameEventMsg(end);
		msg.read(handler);
		
		assertTrue(writer.string.equals(players[1].getName() + ": " + 2));
		
	}

}
