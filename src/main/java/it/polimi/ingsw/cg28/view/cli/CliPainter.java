package it.polimi.ingsw.cg28.view.cli;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.tmodel.TBusinessPermitTile;
import it.polimi.ingsw.cg28.tmodel.TProduct;
import it.polimi.ingsw.cg28.view.Painter;
import it.polimi.ingsw.cg28.view.messages.action.EndGameMsg;
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
import it.polimi.ingsw.cg28.view.messages.event.BazaarStatusMsg;
import it.polimi.ingsw.cg28.view.messages.event.EndGameEventMsg;
import it.polimi.ingsw.cg28.view.messages.event.PlayerStatusMsg;
import it.polimi.ingsw.cg28.view.messages.event.TableStatusMsg;
import it.polimi.ingsw.cg28.view.messages.event.WelcomeEventMsg;

/**
 * Concrete visit strategy implementation of the Painter interface.
 * Specifies how to "paint" TurnActionMsg for a CLI client on the console.
 * It also offers method to "paint" some EventMsg on the console.
 * @author Mario, Alessandro
 * @implements Painter
 */
public class CliPainter implements Painter {
	
	private PrintWriter writer;
	private final CliPrinter printer;
	
	/**
	 * Constructor of the class.
	 * @param writer - writer to print on console
	 */
	public CliPainter(PrintWriter writer) {
		
		Preconditions.checkNotNull(writer);
		
		this.printer = new CliPrinter();
		this.writer = writer;
	}
	
	/**
	 * {@inheritDoc} 
	 * Taking informations from the message, paints regions among which the player can choose the one he wants to change tiles of.
	 */
	@Override
	public void paint(ChangeTileActionMsg changeTileActionMsg) {
		writer.println(printer.printRegions(changeTileActionMsg.getRegion(), true, false));
		writer.flush();
	}
	
	/**
	 * {@inheritDoc}
	 * Taking informations from the message, paints king balcony, towns among which the player can 
	 * choose where wants to build the emporium, and politic cards of the player.
	 */
	@Override
	public void paint(EmporiumKingActionMsg emporiumKingActionMsg) {
		StringBuilder sb = new StringBuilder();
		sb.append("Towns\n");
		sb.append(printer.printTowns(emporiumKingActionMsg.getMap(), true));
		sb.append("King Balcony:\n\n");
		sb.append(printer.printBalcony(emporiumKingActionMsg.getMap().getKingCouncil(), false));
		sb.append("Your politic cards:\n");
		sb.append(printer.printPolCardList(emporiumKingActionMsg.getPoliticCards(), true));
		writer.println(sb.toString());
		writer.flush();	
	}
	
	/**
	 * {@inheritDoc}
	 * Taking informations from the message, paints all the balconies among which the player can 
	 * choose and the nobles pool.
	 */
	@Override
	public void paint(ElectionActionMsg electionActionMsg) {
		StringBuilder sb = new StringBuilder();
		sb.append(printer.printRegionsBalcony(electionActionMsg.getRegions(), electionActionMsg.getMap(),true,false));
		sb.append(printer.printNoblesPool(electionActionMsg.getPool(), true));
		writer.println(sb.toString());
		writer.flush();
	}
	
	/**
	 * {@inheritDoc}
	 * Taking informations from the message, paints tiles among which the player can choose the 
	 * one to use and towns list.
	 */
	@Override
	public void paint(EmporiumTileActionMsg emporiumTileActionMsg) {
		StringBuilder sb = new StringBuilder();
		sb.append("Your business tiles:\n");
		sb.append(printer.printTileList(emporiumTileActionMsg.getTiles(),true));
		sb.append("\n\n");
		sb.append("Towns\n");
		sb.append(printer.printTownList(emporiumTileActionMsg.getTowns(), true));
		writer.println(sb.toString());
		writer.flush();
	}
	
	/**
	 * {@inheritDoc}
	 * Paints nothing.
	 */
	@Override
	public void paint(HireAssistantActionMsg hireAssistantActionMsg) {
		return;
	}
	
	/**
	 * {@inheritDoc}
	 * Paints nothing.
	 */
	@Override
	public void paint(OneMoreMainActionMsg oneMoreMainActionMsg) {
		return;
	}
	
	/**
	 * {@inheritDoc}
	 * Taking informations from the message, paints regions balconies among which the player 
	 * can choose the one he wants to match and regions tiles.
	 */
	@Override
	public void paint(PermitTileActionMsg permitTileActionMsg) {
		StringBuilder sb = new StringBuilder();
		sb.append(printer.printRegionsBalcony(permitTileActionMsg.getRegions(), null, false, true));
		sb.append("Your politic cards:\n");
		sb.append(printer.printPolCardList(permitTileActionMsg.getPoliticCards(), true));
		writer.println(sb.toString());
		writer.flush();
	}
	
	/**
	 * {@inheritDoc}
	 *  Taking informations from the message, paints all the balconies among which the player 
	 *  can choose and the nobles pool.
	 * 
	 */
	@Override
	public void paint(SendAssistantActionMsg sendAssistantActionMsg) {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(printer.printRegionsBalcony(sendAssistantActionMsg.getRegions(),
				sendAssistantActionMsg.getMap(),true,false));
		sb.append(printer.printNoblesPool(sendAssistantActionMsg.getPool(), true));
		
		writer.println(sb.toString());
		writer.flush();
	}
	
	/**
	 * {@inheritDoc}
	 *  Taking informations from the message, paints towns among which the player can 
	 *  choose the bonuses to retrieve.
	 */
	@Override
	public void paint(ReuseCityBonusActionMsg reuseCityBonusActionMsg) {
		writer.println(printer.printTownList(reuseCityBonusActionMsg.getTowns(), true));
		writer.flush();
	}
	
	/**
	 * {@inheritDoc}
	 * Taking informations from the message, paints tiles among which the player can 
	 * choose the bonuses to retrieve.
	 */
	@Override
	public void paint(ReusePermitBonusActionMsg reusePermitBonusActionMsg) {
		writer.println(printer.printTileList(reusePermitBonusActionMsg.getTiles(), true));
		writer.flush();
	}
	
	/**
	 * {@inheritDoc}
	 * Taking informations from the message, paints tiles among which the player can 
	 * choose the ones to obtain.
	 */
	@Override
	public void paint(TakePermitTileBonusActionMsg actionMsg) {

		List<TBusinessPermitTile> tiles = new ArrayList<>();
		
		for(TBusinessPermitTile tile: actionMsg.getTiles())
			tiles.add(tile);
		
		writer.println(printer.printTileList(tiles, true));
		writer.flush();
		
	}

	/**
	 * {@inheritDoc}
	 * Taking informations from the message, paints the shelf among which the player 
	 * can choose what he wants to buy.
	 */
	@Override
	public void paint(BuySalableActionMsg buySalableActionMsg) {

		List<TProduct> tShelf = buySalableActionMsg.getShelf();
		
		for(int i=0; i <tShelf.size(); i++){
			writer.print(i+1 + ") ");
			writer.print("Player: " + tShelf.get(i).getPlayerOwner() + " sells: ");
			writer.print(tShelf.get(i).print(printer) + "\n");
			writer.flush();
		}
		
	}
	
	/**
	 * {@inheritDoc}
	 * Taking informations from the message, paints the number of 
	 * assistants of the player.
	 */
	@Override
	public void paint(SellAssistantsActionMsg actionMsg) {
		writer.println("You have: " + actionMsg.getNumbAssistants() + " assistants");
		writer.flush();
		
	}
	
	/**
	 * {@inheritDoc}
	 * Taking informations from the message, paints permit tiles owned by the player.
	 */
	@Override
	public void paint(SellPermitTilesActionMsg actionMsg) {
	
		List<TBusinessPermitTile> tiles = new ArrayList<>();
	
		for(TBusinessPermitTile tile: actionMsg.getTiles())
			tiles.add(tile);
		
		writer.println(printer.printTileList(tiles, true));
		writer.flush();
		
	}
	
	/**
	 * {@inheritDoc}
	 * Taking informations from the message, paints politic cards owned by the player.
	 */
	@Override
	public void paint(SellPoliticCardsActionMsg actionMsg) {
		
		writer.println(printer.printPolCardList(actionMsg.getPoliticCards(), true));
		writer.flush();
		
	}
	
	/**
	 * Taking informations from the message, paints a complete model status String
	 * representation.
	 * @param eventMsg - TableStatusMsg
	 */
	public void paint(TableStatusMsg eventMsg){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(printer.printTownConnections(eventMsg.getMap())+ "\n");
		sb.append(printer.printTowns(eventMsg.getMap(), true) + "\n");
		
		for(int i=0;i<eventMsg.getRegions().length;i++)
			sb.append(printer.printRegion(eventMsg.getRegions()[i], false));
		
		sb.append(printer.printRegionsBalcony(eventMsg.getRegions(), eventMsg.getMap(), false, false) + "\n");
		sb.append("KING BALCONY:\n");
		sb.append(printer.printBalcony(eventMsg.getMap().getKingCouncil(), false));
		
		sb.append("\n" +printer.printTBonusTiles(eventMsg.getBonusTiles()));
		
		sb.append(printer.printTKingRewards(eventMsg.getKingRewards()) + "\n");
		
		for(int i=0;i<eventMsg.getPlayers().length;i++)
			sb.append(printer.printPlayer(eventMsg.getPlayers()[i], false) + "\n");
		
		writer.println(sb +"\n");
		writer.println("Current Turn PLAYER: " + eventMsg.getCurrentTurnPlayer());
		writer.flush();

	}
	
	/**
	 * Taking informations from the message, paints a complete player status String
	 * representation.
	 * @param eventMsg - PlayerStatusMsg
	 */
	public void paint(PlayerStatusMsg eventMsg){
		
		StringBuilder sb = new StringBuilder();
		sb.append("TURN PLAYER: " + eventMsg.getPlayer().getName() +"\n\n");
		paint(eventMsg.getTableStatusMsg());
		sb.append(printer.printPlayer(eventMsg.getTPlayer(), true));

		writer.println(sb.toString());
		writer.flush();
	}

	/**
	 * Taking informations from the message, paints a complete bazaar shelf status String
	 * representation.
	 * @param eventMsg - BazaarStatusMsg
	 */
	public void paint(BazaarStatusMsg bazaarStatusMsg) {
		
		writer.println("\nSHELF:");
		
		List<TProduct> tShelf = bazaarStatusMsg.getShelf();
		
		for(int i=0; i <tShelf.size(); i++){
			writer.print("Player: " + tShelf.get(i).getPlayerOwner() + " sells: ");
			writer.print(tShelf.get(i).print(printer) +"\n");
			writer.flush();
		}
		
	}
	
	/**
	 * Taking informations from the message, paints the winner (winner is chosen in accord to Council of Four rules)
	 * and the score of each player (Final ranking note: In case two player have the some score the method randomly 
	 * order the players)
	 * @param eventMsg - EndGameEventMsg
	 */
	public void paint(EndGameEventMsg eventMsg) {
		
		EndGameMsg msg = eventMsg.getEndGameMsg();
		
		PlayerID winner = msg.getWinner();
		
		writer.println("\nFINAL RANKING");
		writer.println("\nPlayer " + winner.getName() + " WINS!\n");
		
		
		writer.println(winner.getName() + ": " + msg.getScores().get(winner).intValue());
		msg.getScores().remove(winner);
		
		msg
			.getScores()
			.entrySet()
			.stream()
			.sorted((p1,p2) -> Integer.compare(p2.getValue(), p1.getValue()))
			.forEach(p -> writer.println(p.getKey().getName() + ": " + p.getValue()));
		
		writer.flush();
	
	}
	
	/**
	 * Taking informations from the message, paints the welcome event message and the nobility Track.
	 * @param eventMsg - WelcomeEventMsg
	 */
	public void paint(WelcomeEventMsg eventMsg) {
		
		writer.println(eventMsg.getWelcome() + "\n\n");
		writer.println(printer.printTNobilityTrack(eventMsg.getNobilityTrack()));
		writer.flush();
		
	}
	
}
