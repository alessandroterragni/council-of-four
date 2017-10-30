package it.polimi.ingsw.cg28.view.cli;

import static org.junit.Assert.*;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.connections.RequestHandler;
import it.polimi.ingsw.cg28.connections.client.Client;
import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.view.StringJar;
import it.polimi.ingsw.cg28.view.messages.action.ActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.ChatActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.ConnectionActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.EndTurnMsg;
import it.polimi.ingsw.cg28.view.messages.action.LeaveGameActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.StartMsg;
import it.polimi.ingsw.cg28.view.messages.action.bazaar.BuySalableActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.bazaar.SellAssistantsActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.ElectionActionMsg;
import it.polimi.ingsw.cg28.view.messages.event.EventMsg;
import it.polimi.ingsw.cg28.view.messages.event.FilledMsg;

public class CliManagerTest {
	
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
	
	private CliManager manager;
	private AsACommandReader reader;
	private AsAPrintWriter writer;
	private AsARequestHandler requestHandler;
	private StringJar stringJar;
	private PlayerID [] players;
	
	@Before
	public void setTest(){
		
		log.setUseParentHandlers(false);
		
		players = new PlayerID[2];
		players[0] = new PlayerID("Player1");
		players[1] = new PlayerID("Player2");
		
		requestHandler = new AsARequestHandler();
		writer =  new AsAPrintWriter(System.out);
		reader = new AsACommandReader(writer);
		
		manager = new CliManager(requestHandler, reader, players[0]);
		
		stringJar = new StringJar();
		
	}
	
	@Test
	public void testCliManager(){
		assertNotNull(new CliManager(requestHandler, reader, players[0]));
	}
	
	@Test(expected = NullPointerException.class)
	public void testCliManagerWithNullRequestHandler(){
		manager = new CliManager(null, reader, players[0]);
	}
	
	@Test(expected = NullPointerException.class)
	public void testCliManagerWithNullReader(){
		manager = new CliManager(requestHandler, null, players[0]);
	}
	
	@Test(expected = NullPointerException.class)
	public void testCliManagerWithNullPlayer(){
		manager = new CliManager(requestHandler, reader, null);
	}
	
	@Test
	public void testCliManagerProcessRequestNullChange(){
		
		manager.processRequest(null);
		assertTrue(requestHandler.message == null);

	}
	
	@Test
	public void testCliManagerProcessRequest(){
		
		ActionMsg msg = new StartMsg();
		manager.processRequest(msg);
		assertTrue(requestHandler.message.equals(msg));
		assertTrue(requestHandler.message.getPlayerID().equals(players[0]));

	}
	
	@Test
	public void testCliManagerCheckStrAction(){
		
		assertTrue(manager.checkStr("#action") == null);
		assertTrue(manager.checkStr("#action 1") == null);
		
		reader.setCanDoMainAction(true);
		
		assertTrue(manager.checkStr("#action 1").getClass().equals(ElectionActionMsg.class));

	}
	
	@Test
	public void testCliManagerCheckStrFill(){
		
		assertTrue(manager.checkStr("#fill") == null);
		assertTrue(writer.string.equals(stringJar.errorFilledMsg()));
		writer.string = null;
		
		manager.setMessage(new FilledMsg(new EndTurnMsg(players[0])));
		assertTrue(manager.checkStr("#fill") == null);
		assertTrue(writer.string == null);
		
		manager.setMessage(null);
		assertTrue(manager.checkStr("#fill") == null);
		assertTrue(writer.string.equals(stringJar.errorFilledMsg()));
		
	}
	
	@Test
	public void testCliManagerCheckStrSell(){
		
		assertTrue(manager.checkStr("#sell") == null);
		assertTrue(manager.checkStr("#sell 1") == null);
		
		reader.setCanSellAssistant(true);
		
		assertTrue(manager.checkStr("#sell 1").getClass().equals(SellAssistantsActionMsg.class));

	}
	
	@Test
	public void testCliManagerCheckStrBuy(){
		
		assertTrue(manager.checkStr("#buy") == null);
		assertTrue(manager.checkStr("#buy yes") == null);
		assertTrue(manager.checkStr("#buy no") == null);
		
		reader.setCanBuy(true);
		
		assertTrue(manager.checkStr("#buy yes").getClass().equals(BuySalableActionMsg.class));

	}
	
	@Test
	public void testCliManagerCheckStrChat(){
		
		ChatActionMsg msg = (ChatActionMsg) manager.checkStr("#chat Chat Message!");
		assertTrue(msg.getString().equals("Chat Message!"));

		assertTrue(manager.checkStr("#chat") == null);
	}
	
	@Test
	public void testCliManagerCheckStrLeave(){
		
		assertTrue(manager.checkStr("#leave Leave Message!").getClass().equals(LeaveGameActionMsg.class));
		assertTrue(manager.checkStr("#leave").getClass().equals(LeaveGameActionMsg.class));

	}
	
	@Test
	public void testCliManagerCheckStrHelp(){
		
		manager.checkStr("#help");
		assertTrue(writer.string.equals(stringJar.help()));
		writer.string = null;
		
		manager.checkStr("#help 1");
		assertTrue(writer.string.equals(stringJar.help()));

	}


}
