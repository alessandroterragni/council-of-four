package it.polimi.ingsw.cg28.view.cli;

import static org.junit.Assert.*;

import java.io.OutputStream;
import java.io.PrintWriter;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.view.StringJar;
import it.polimi.ingsw.cg28.view.messages.action.EndBuyTurnMsg;
import it.polimi.ingsw.cg28.view.messages.action.EndSellTurnMsg;
import it.polimi.ingsw.cg28.view.messages.action.EndTurnMsg;
import it.polimi.ingsw.cg28.view.messages.action.bazaar.BuySalableActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.bazaar.SellAssistantsActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.bazaar.SellPermitTilesActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.bazaar.SellPoliticCardsActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.ChangeTileActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.ElectionActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.EmporiumKingActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.EmporiumTileActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.HireAssistantActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.OneMoreMainActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.PermitTileActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.SendAssistantActionMsg;

public class CommandReaderTest {
	
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
	
	private CommandReader reader;
	private AsAPrintWriter writer;
	private StringJar stringJar;
	private PlayerID player;
	
	@Before
	public void setTest(){
		
		writer =  new AsAPrintWriter(System.out);
		reader = new CommandReader(writer);
		stringJar = new StringJar();
		player = new PlayerID("Player1");
		
	}
	
	@Test
	public void testCommandReader(){
		assertNotNull(new CommandReader(writer));
	}
	
	@Test(expected = NullPointerException.class)
	public void testCommandReaderWithNullWriter(){
		assertNotNull(new CommandReader(null));
	}

	@Test
	public void testParserIndexActionMainAndQuickActionAllowed() {
		
		reader.setCanDoMainAction(true);
		reader.setCanDoQuickAction(true);
		
		assertTrue(reader.parserIndexAction("1", player).getClass().equals(ElectionActionMsg.class));
		assertTrue(reader.parserIndexAction("2", player).getClass().equals(PermitTileActionMsg.class));
		assertTrue(reader.parserIndexAction("3", player).getClass().equals(EmporiumTileActionMsg.class));
		assertTrue(reader.parserIndexAction("4", player).getClass().equals(EmporiumKingActionMsg.class));
		assertTrue(reader.parserIndexAction("5", player).getClass().equals(HireAssistantActionMsg.class));
		assertTrue(reader.parserIndexAction("6", player).getClass().equals(ChangeTileActionMsg.class));
		assertTrue(reader.parserIndexAction("7", player).getClass().equals(SendAssistantActionMsg.class));
		assertTrue(reader.parserIndexAction("8", player).getClass().equals(OneMoreMainActionMsg.class));
		assertTrue(reader.parserIndexAction("9", player).getClass().equals(EndTurnMsg.class));
		reader.parserIndexAction("10", player);
		assertTrue(writer.string.equals("*** Number inserted not found/Couldn't perform this action now ***"));
		
	}
	
	@Test
	public void testParserIndexActionMainActionAllowed() {
		
		reader.setCanDoMainAction(true);
		reader.setCanDoQuickAction(false);
		
		assertTrue(reader.parserIndexAction("1", player).getClass().equals(ElectionActionMsg.class));
		assertTrue(reader.parserIndexAction("2", player).getClass().equals(PermitTileActionMsg.class));
		assertTrue(reader.parserIndexAction("3", player).getClass().equals(EmporiumTileActionMsg.class));
		assertTrue(reader.parserIndexAction("4", player).getClass().equals(EmporiumKingActionMsg.class));
		
		reader.parserIndexAction("5", player);
		assertTrue(writer.string.equals("*** Number inserted not found/Couldn't perform this action now ***"));
		writer.string = null;
		reader.parserIndexAction("6", player);
		assertTrue(writer.string.equals("*** Number inserted not found/Couldn't perform this action now ***"));
		writer.string = null;
		reader.parserIndexAction("7", player);
		assertTrue(writer.string.equals("*** Number inserted not found/Couldn't perform this action now ***"));
		writer.string = null;
		reader.parserIndexAction("8", player);
		assertTrue(writer.string.equals("*** Number inserted not found/Couldn't perform this action now ***"));
		writer.string = null;
		reader.parserIndexAction("9", player);
		assertTrue(writer.string.equals("*** Number inserted not found/Couldn't perform this action now ***"));
		
	}
	

	@Test
	public void testParserIndexActionQuickActionAllowed() {
		
		reader.setCanDoMainAction(false);
		reader.setCanDoQuickAction(true);
		
		reader.parserIndexAction("1", player);
		assertTrue(writer.string.equals("*** Number inserted not found/Couldn't perform this action now ***"));
		writer.string = null;
		reader.parserIndexAction("2", player);
		assertTrue(writer.string.equals("*** Number inserted not found/Couldn't perform this action now ***"));
		writer.string = null;
		reader.parserIndexAction("3", player);
		assertTrue(writer.string.equals("*** Number inserted not found/Couldn't perform this action now ***"));
		writer.string = null;
		reader.parserIndexAction("4", player);
		assertTrue(writer.string.equals("*** Number inserted not found/Couldn't perform this action now ***"));
		
		assertTrue(reader.parserIndexAction("5", player).getClass().equals(HireAssistantActionMsg.class));
		assertTrue(reader.parserIndexAction("6", player).getClass().equals(ChangeTileActionMsg.class));
		assertTrue(reader.parserIndexAction("7", player).getClass().equals(SendAssistantActionMsg.class));
		assertTrue(reader.parserIndexAction("8", player).getClass().equals(OneMoreMainActionMsg.class));
		assertTrue(reader.parserIndexAction("9", player).getClass().equals(EndTurnMsg.class));
		
		reader.parserIndexAction("10", player);
		assertTrue(writer.string.equals("*** Number inserted not found/Couldn't perform this action now ***"));
		
	}
	
	@Test
	public void testParserIndexBazaarActionCanDoAll() {
		
		reader.setCanSellAssistant(true);
		reader.setCanSellPermitTiles(true);
		reader.setCanSellPoliticCards(true);
		
		assertTrue(reader.parserIndexBazaarAction("1", player).getClass().equals(SellAssistantsActionMsg.class));
		assertTrue(reader.parserIndexBazaarAction("2", player).getClass().equals(SellPoliticCardsActionMsg.class));
		assertTrue(reader.parserIndexBazaarAction("3", player).getClass().equals(SellPermitTilesActionMsg.class));
		assertTrue(reader.parserIndexBazaarAction("4", player).getClass().equals(EndSellTurnMsg.class));
		
	}
	
	@Test
	public void testParserIndexBazaarActionCanNotSellPermitTiles() {
		
		reader.setCanSellAssistant(true);
		reader.setCanSellPermitTiles(false);
		reader.setCanSellPoliticCards(true);
		
		assertTrue(reader.parserIndexBazaarAction("1", player).getClass().equals(SellAssistantsActionMsg.class));
		assertTrue(reader.parserIndexBazaarAction("2", player).getClass().equals(SellPoliticCardsActionMsg.class));
		reader.parserIndexBazaarAction("3", player);
		assertTrue(writer.string.equals("*** Number inserted not found/Couldn't perform this action now ***"));
		writer.string = null;
		assertTrue(reader.parserIndexBazaarAction("4", player).getClass().equals(EndSellTurnMsg.class));
		
	}
	
	@Test
	public void testParserIndexBazaarActionCanNotSellAssistants() {
		
		reader.setCanSellAssistant(false);
		reader.setCanSellPermitTiles(true);
		reader.setCanSellPoliticCards(true);
		
		reader.parserIndexBazaarAction("1", player);
		assertTrue(writer.string.equals("*** Number inserted not found/Couldn't perform this action now ***"));
		writer.string = null;
		assertTrue(reader.parserIndexBazaarAction("2", player).getClass().equals(SellPoliticCardsActionMsg.class));
		assertTrue(reader.parserIndexBazaarAction("3", player).getClass().equals(SellPermitTilesActionMsg.class));
		assertTrue(reader.parserIndexBazaarAction("4", player).getClass().equals(EndSellTurnMsg.class));
		
	}
	
	@Test
	public void testParserIndexBazaarActionCanNotSellPoliticCards() {
		
		reader.setCanSellAssistant(true);
		reader.setCanSellPermitTiles(true);
		reader.setCanSellPoliticCards(false);
		
		assertTrue(reader.parserIndexBazaarAction("1", player).getClass().equals(SellAssistantsActionMsg.class));
		reader.parserIndexBazaarAction("2", player);
		reader.parserIndexBazaarAction("10", player);
		assertTrue(writer.string.equals("*** Number inserted not found/Couldn't perform this action now ***"));
		writer.string = null;
		assertTrue(reader.parserIndexBazaarAction("3", player).getClass().equals(SellPermitTilesActionMsg.class));
		assertTrue(reader.parserIndexBazaarAction("4", player).getClass().equals(EndSellTurnMsg.class));
		
	}
	
	@Test
	public void testBuyCheck() {
		
		String[] stringsYes = {"buy","yes"};
		String[] stringsNo = {"buy","no"};
		
		assertTrue(reader.buyCheck(stringsYes, player) == null);
		assertTrue(writer.string.equals("*** Couldn't perform this action now ***"));
		writer.string = null;
		
		reader.setCanBuy(true);
		
		String[] moreStrings = {"buy", "yes", "ops"};
		assertTrue(reader.buyCheck(moreStrings, player) == null);
		
		assertTrue(reader.buyCheck(stringsYes, player).getClass().equals(BuySalableActionMsg.class));
		assertTrue(reader.buyCheck(stringsNo, player).getClass().equals(EndBuyTurnMsg.class));
		
		String[] stringsOps = {"buy","buy"};
		
		assertTrue(reader.buyCheck(stringsOps, player) == null);
		assertTrue(writer.string.equals("Insert your choice again\n"));
		
	}
	
	@Test
	public void testBuildString(){
		
		String string = "#chat This is a String! Can you rebuild this string?";
		String[] strings = string.split(" ");
		
		assertTrue(reader.buildString(strings).equals("This is a String! Can you rebuild this string?"));
		
	}
	
	@Test
	public void testErrorFilledMsg(){
		
		reader.errorFilledMsg();
		assertTrue(writer.string.equals(stringJar.errorFilledMsg()));
		
	}
	
	@Test
	public void testInitGame(){
		
		reader.initGame();
		assertTrue(writer.string.equals(stringJar.initGame()));
		
	}
	
	@Test
	public void testHelp(){
		
		reader.help();
		assertTrue(writer.string.equals(stringJar.help()));
		
	}
	
	@Test
	public void testGetWriter(){
	
		assertTrue(reader.getWriter().equals(writer));
		
	}
	

}
