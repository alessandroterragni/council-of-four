package it.polimi.ingsw.cg28.view.cli;

import static org.junit.Assert.*;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
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
import it.polimi.ingsw.cg28.tmodel.TBusinessPermitTile;
import it.polimi.ingsw.cg28.tmodel.TProduct;
import it.polimi.ingsw.cg28.view.messages.action.bazaar.BuySalableActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.bazaar.SellAssistantsActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.bazaar.SellPermitTilesActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.bazaar.SellPoliticCardsActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.bonus.ReuseCityBonusActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.bonus.ReusePermitBonusActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.bonus.TakePermitTileBonusActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.ChangeTileActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.ElectionActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.EmporiumKingActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.EmporiumTileActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.HireAssistantActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.OneMoreMainActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.PermitTileActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.SendAssistantActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.TurnActionMsg;
import it.polimi.ingsw.cg28.view.messages.event.BazaarStatusMsg;

public class CliPainterTest {

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
	
	private AsAPrintWriter writer;
	private CliPrinter printer;
	private CliPainter painter;
	private ModelStatus model;
	private PlayerID [] players;
	
	@Before
	public void setTest(){
		
		players = new PlayerID[2];
		players[0] = new PlayerID("Player1");
		players[1] = new PlayerID("Player2");
		
		ModelTestConfiguration config = new ModelTestConfiguration();
		model = config.buildModelTest(players);
		
		writer =  new AsAPrintWriter(System.out);
		printer = new CliPrinter();
		painter = new CliPainter(writer);
		
	}
	
	@Test
	public void testCliPainter(){
		assertNotNull(new CliPainter(writer));
	}
	
	@Test(expected = NullPointerException.class)
	public void testCliPainterWithNullWriter(){
		painter = new CliPainter(null);
	}
	

	@Test
	public void testPaintChangeTileActionMsg() throws InvalidActionException {
		TurnActionMsg msg = new ChangeTileActionMsg(players[0]);
		msg.fill(model);
		msg.getShowChoices(painter);
		
		ChangeTileActionMsg actionMsg = (ChangeTileActionMsg) msg;
		assertTrue(writer.string.equals(printer.printRegions(actionMsg.getRegion(), true, false)));
		
	}
	

	@Test
	public void testPaintEmporiumKingActionMsg() throws InvalidActionException {
		
		TurnActionMsg msg = new EmporiumKingActionMsg(players[0]);
		msg.fill(model);
		msg.getShowChoices(painter);
		
		EmporiumKingActionMsg emporiumKingActionMsg = (EmporiumKingActionMsg) msg;
		StringBuilder sb = new StringBuilder();
		sb.append("Towns\n");
		sb.append(printer.printTowns(emporiumKingActionMsg.getMap(), true));
		sb.append("King Balcony:\n\n");
		sb.append(printer.printBalcony(emporiumKingActionMsg.getMap().getKingCouncil(), false));
		sb.append("Your politic cards:\n");
		sb.append(printer.printPolCardList(emporiumKingActionMsg.getPoliticCards(), true));
		
		assertTrue(writer.string.equals(sb.toString()));

	}
	

	@Test
	public void testPaintElectionActionMsg() throws InvalidActionException {
		
		TurnActionMsg msg = new ElectionActionMsg(players[0]);
		msg.fill(model);
		msg.getShowChoices(painter);
		
		ElectionActionMsg electionActionMsg = (ElectionActionMsg) msg;
		StringBuilder sb = new StringBuilder();
		
		sb.append(printer.printRegionsBalcony(electionActionMsg.getRegions(), electionActionMsg.getMap(),true,false));
		sb.append(printer.printNoblesPool(electionActionMsg.getPool(), true));
		
		assertTrue(writer.string.equals(sb.toString()));
		
	}
	
	@Test
	public void testPaintEmporiumTileActionMsg() throws InvalidActionException {
		
		model.getPlayer(players[0]).takeTile(model.getRegion(0).getTile(0));
		model.getPlayer(players[0]).takeTile(model.getRegion(0).getTile(1));
		
		TurnActionMsg msg = new EmporiumTileActionMsg(players[0]);
		msg.fill(model);
		msg.getShowChoices(painter);
		
		EmporiumTileActionMsg emporiumTileActionMsg = (EmporiumTileActionMsg) msg;
		StringBuilder sb = new StringBuilder();
		
		sb.append("Your business tiles:\n");
		sb.append(printer.printTileList(emporiumTileActionMsg.getTiles(),true));
		sb.append("\n\n");
		sb.append("Towns\n");
		sb.append(printer.printTownList(emporiumTileActionMsg.getTowns(), true));
		
		assertTrue(writer.string.equals(sb.toString()));
	
	}
	
	@Test
	public void testPaintHireAssistantActionMsg() throws InvalidActionException {
		
		TurnActionMsg msg = new HireAssistantActionMsg(players[0]);
		msg.fill(model);
		msg.getShowChoices(painter);
		
		assertTrue(writer.string == null);
	}
	
	@Test
	public void testPaintOneMoreMainActionMsg() throws InvalidActionException {
		
		TurnActionMsg msg = new OneMoreMainActionMsg(players[0]);
		msg.fill(model);
		msg.getShowChoices(painter);
		
		assertTrue(writer.string == null);
	}
	
	@Test
	public void testPaintPermitTileActionMsg() throws InvalidActionException {
		
		TurnActionMsg msg = new PermitTileActionMsg(players[0]);
		msg.fill(model);
		msg.getShowChoices(painter);
		
		PermitTileActionMsg permitTileActionMsg = (PermitTileActionMsg) msg;
		StringBuilder sb = new StringBuilder();
		
		sb.append(printer.printRegionsBalcony(permitTileActionMsg.getRegions(), null, false, true));
		sb.append("Your politic cards:\n");
		sb.append(printer.printPolCardList(permitTileActionMsg.getPoliticCards(), true));
		
		assertTrue(writer.string.equals(sb.toString()));
	}
	
	@Test
	public void testPaintSendAssistantActionMsg() throws InvalidActionException {
		
		TurnActionMsg msg = new SendAssistantActionMsg(players[0]);
		msg.fill(model);
		msg.getShowChoices(painter);
		
		SendAssistantActionMsg sendAssistantActionMsg = (SendAssistantActionMsg) msg;
		StringBuilder sb = new StringBuilder();
		
		sb.append(printer.printRegionsBalcony(sendAssistantActionMsg.getRegions(),
				sendAssistantActionMsg.getMap(),true,false));
		sb.append(printer.printNoblesPool(sendAssistantActionMsg.getPool(), true));
		
		assertTrue(writer.string.equals(sb.toString()));
		
		
	}
	
	@Test
	public void testPaintReuseCityBonusActionMsg() throws InvalidActionException {
		
		model.getMap().addEmporium(players[0], model.getMap().getTown("Arkon"));
		model.getMap().addEmporium(players[0], model.getMap().getTown("Graden"));
		model.getMap().addEmporium(players[0], model.getMap().getTown("Juvelar"));
		
		TurnActionMsg msg = new ReuseCityBonusActionMsg(1, players[0]);
		msg.fill(model);
		msg.getShowChoices(painter);
		
		ReuseCityBonusActionMsg reuseCityBonusActionMsg = (ReuseCityBonusActionMsg) msg;
		
		assertTrue(writer.string.equals(printer.printTownList(reuseCityBonusActionMsg.getTowns(), true)));
	}
	
	@Test
	public void testPaintReusePermitBonusActionMsg() throws InvalidActionException {
		
		model.getPlayer(players[0]).takeTile(model.getRegion(0).getTile(0));
		model.getPlayer(players[0]).takeTile(model.getRegion(0).getTile(1));
		
		TurnActionMsg msg = new ReusePermitBonusActionMsg(1, players[0]);
		msg.fill(model);
		msg.getShowChoices(painter);
		
		ReusePermitBonusActionMsg reusePermitBonusActionMsg = (ReusePermitBonusActionMsg) msg;
		
		assertTrue(writer.string.equals(printer.printTileList(reusePermitBonusActionMsg.getTiles(), true)));
		
	}
	
	@Test
	public void testPaintTakePermitTileBonusActionMsg() throws InvalidActionException {
		
		TurnActionMsg msg = new TakePermitTileBonusActionMsg(1, players[0]);
		msg.fill(model);
		msg.getShowChoices(painter);
		
		TakePermitTileBonusActionMsg takePermitTileBonusActionMsg = (TakePermitTileBonusActionMsg) msg;
		
		List<TBusinessPermitTile> tiles = new ArrayList<>();
		
		for(TBusinessPermitTile tile: takePermitTileBonusActionMsg.getTiles())
			tiles.add(tile);
		
		assertTrue(writer.string.equals(printer.printTileList(tiles, true)));
		
	}

	@Test
	public void testPaintBuySalableActionMsg() throws InvalidActionException {
		
		BazaarModel bazaar = new BazaarModel();
		
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
		
		BuySalableActionMsg msgBuy = new BuySalableActionMsg(players[0]);
		msgBuy.fill(model, bazaar);
		
		TurnActionMsg msg = msgBuy;
		msg.getShowChoices(painter);
		
		BuySalableActionMsg buySalableActionMsg = (BuySalableActionMsg) msg;
		
		List<TProduct> tShelf = buySalableActionMsg.getShelf();
		
		assertTrue(writer.string.equals(tShelf.get(0).print(printer) + "\n"));
		
		
	}

	@Test
	public void testPaintSellAssistantsActionMsg() throws InvalidActionException {
		
		model.getPlayer(players[0]).getAssistants().setValue(10);
		
		TurnActionMsg msg = new SellAssistantsActionMsg(players[0]);
		msg.fill(model);
		msg.getShowChoices(painter);
		
		SellAssistantsActionMsg actionMsg = (SellAssistantsActionMsg) msg;
		
		assertTrue(writer.string.equals("You have: " + actionMsg.getNumbAssistants() + " assistants"));
	}
	
	@Test
	public void testPaintSellPermitTilesActionMsg() throws InvalidActionException {
		
		model.getPlayer(players[0]).takeTile(model.getRegion(0).getTile(0));
		model.getPlayer(players[0]).takeTile(model.getRegion(0).getTile(1));
		
		TurnActionMsg msg = new SellPermitTilesActionMsg(players[0]);
		msg.fill(model);
		msg.getShowChoices(painter);
		
		SellPermitTilesActionMsg actionMsg = (SellPermitTilesActionMsg) msg;
		
		List<TBusinessPermitTile> tiles = new ArrayList<>();
		
		for(TBusinessPermitTile tile: actionMsg.getTiles())
			tiles.add(tile);
		
		assertTrue(writer.string.equals(printer.printTileList(tiles, true)));
	}
	
	@Test
	public void testPaintSellPoliticCardsActionMsg() throws InvalidActionException {
		TurnActionMsg msg = new SellPoliticCardsActionMsg(players[0]);
		msg.fill(model);
		msg.getShowChoices(painter);
		
		SellPoliticCardsActionMsg actionMsg = (SellPoliticCardsActionMsg) msg;
		
		assertTrue(writer.string.equals(printer.printPolCardList(actionMsg.getPoliticCards(), true)));
	}
	
	@Test
	public void testPaintBazaarStatusMsg() {
		
		BazaarModel bazaar = new BazaarModel();
		
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
		
		BazaarStatusMsg msg = new BazaarStatusMsg(bazaar, players[0]);
		painter.paint(msg);
		
		List<TProduct> tShelf = msg.getShelf();
		
		assertTrue(writer.string.equals(tShelf.get(0).print(printer) +"\n"));
		
	}

}
