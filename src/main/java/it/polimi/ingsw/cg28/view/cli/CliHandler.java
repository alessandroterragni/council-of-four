package it.polimi.ingsw.cg28.view.cli;

import java.io.PrintWriter;
import java.util.logging.Logger;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.connections.RequestHandler;
import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.view.StringJar;
import it.polimi.ingsw.cg28.view.ViewHandler;
import it.polimi.ingsw.cg28.view.messages.action.StartMsg;
import it.polimi.ingsw.cg28.view.messages.event.BazaarStatusMsg;
import it.polimi.ingsw.cg28.view.messages.event.ChatEventMsg;
import it.polimi.ingsw.cg28.view.messages.event.EndBazaarMsg;
import it.polimi.ingsw.cg28.view.messages.event.EndBuyTurnEventMsg;
import it.polimi.ingsw.cg28.view.messages.event.EndGameEventMsg;
import it.polimi.ingsw.cg28.view.messages.event.EndSellTurnEventMsg;
import it.polimi.ingsw.cg28.view.messages.event.EndTurnEventMsg;
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

/**
 * Concrete visit strategy implementation of the ViewHandler interface.
 * Specifies how to handle {@link it.polimi.ingsw.cg28.view.messages.event.EventMsg} for a CLI client.
 * It must be initialised through {@link #initialize(RequestHandler, PlayerID)} to work properly.
 * @author Mario, Alessandro
 * @implements ViewHandler
 */
public class CliHandler implements ViewHandler {
	
	private PrintWriter writer;
	private CliManager cliManager;
	private CliPainter cliPainter;
	private CommandReader reader;
	StringJar stringJar = new StringJar();
	
	private static final Logger log = Logger.getLogger(CliHandler.class.getName());
	
	/**
	 * Constructor of the class.
	 * @param reader - CommandReader to set actions allowed to player
	 * @throws NullPointerException if reader parameter is null
	 */
	public CliHandler(CommandReader reader){
		
		Preconditions.checkNotNull(reader);
		
		this.reader = reader;
		this.writer = reader.getWriter();
		this.cliPainter = new CliPainter(writer);
		
	}
	
	/**
	 * {@inheritDoc}
	 * Print the start message to console, transfers a FilledMsg filled with StartMsg to CliManager.
	 */
	@Override
	public void handle(StartEventMsg eventMsg) {
		
		StartMsg startMsg = new StartMsg();
		writer.println(stringJar.startMsg());
		writer.flush();
		
		FilledMsg filled = new FilledMsg(startMsg);
		cliManager.setMessage(filled);
	
	}
	
	/**
	 * {@inheritDoc}
	 * Sets actions allowed to CommandReader. Prints actions allowed to console.
	 */
	@Override
	public void handle(GiveMeActionMsg eventMsg) {
		
		writer.println(parserActionAllowed(eventMsg));
		writer.flush();
		
		reader.setCanDoMainAction(eventMsg.canDoMainAction());
		reader.setCanDoQuickAction(eventMsg.canDoQuickAction());
		
	}
	
	/**
	 * Private method to determine the correct String related to a GiveMeActionMsg.
	 * @param eventMsg - GiveMeActionMsg to define actions allowed
	 * @return String to explain actions allowed
	 */
	private String parserActionAllowed(GiveMeActionMsg eventMsg){
		
		if (eventMsg.canDoMainAction() && eventMsg.canDoQuickAction()) 
			return stringJar.getMainAndQuickActionsRequest();
		if (eventMsg.canDoMainAction())
			return stringJar.getMainActionMessageRequest();
		if (eventMsg.canDoQuickAction()) 
			return stringJar.getQuickActionRequest();
		
		log.warning("No ACTIONS allowed " +  eventMsg.getPlayer().getName());
		return null;
		
	}
	
	/**
	 * {@inheritDoc}
	 * Paints the message through {@link CliPainter#paint(WelcomeEventMsg)}
	 */
	@Override
	public void handle(WelcomeEventMsg eventMsg) {
		
		cliPainter.paint(eventMsg);
		
		handle(eventMsg.getTableStatus());
		
	}
	
	/**
	 * {@inheritDoc}
	 * Paints the message through {@link CliPainter#paint(TableStatusMsg)}
	 */
	@Override
	public void handle(TableStatusMsg eventMsg){
	
		cliPainter.paint(eventMsg);
		
	}
	
	/**
	 * {@inheritDoc}
	 * Prints to console to notify the player a message to fill is received.
	 * Transfers the FilledMsg to CliManager.
	 */
	@Override
	public void handle(FilledMsg filledMsg) {
		
		writer.println(stringJar.fillMessage());
		writer.flush();
		
		cliManager.setMessage(filledMsg);
		
	}
	
	/**
	 * {@inheritDoc}
	 * Paints the message through {@link CliPainter#paint(PlayerStatusMsg)}
	 */
	@Override
	public void handle(PlayerStatusMsg eventMsg) {
		
		cliPainter.paint(eventMsg);
	}
	
	/**
	 * {@inheritDoc}
	 * Simply prints to console the string contained in the SimpleEventMsg.
	 */
	@Override
	public void handle(SimpleEventMsg eventMsg) {
		
		writer.println(eventMsg.getString());
		writer.flush();
	}
	
	/**
	 * {@inheritDoc}
	 * Sets bazaar (sell) Actions allowed to CommandReader. Prints actions allowed to console.
	 */
	@Override
	public void handle(GiveMeSellActionMsg eventMsg) {
		
		writer.println("Player: " + eventMsg.getPlayer().getName());
		writer.println(stringJar.getBazaarSellRequest(eventMsg.canSellAssistant(),
				eventMsg.canSellPoliticCards(), eventMsg.canSellPermitTiles()));
		writer.flush();
		
		reader.setCanSellAssistant(eventMsg.canSellAssistant());
		reader.setCanSellPermitTiles(eventMsg.canSellPermitTiles());
		reader.setCanSellPoliticCards(eventMsg.canSellPoliticCards());
		
	}
	
	/**
	 * {@inheritDoc}
	 * Sets bazaar (buy) Actions allowed to CommandReader. Prints actions allowed to console.
	 */
	@Override
	public void handle(GiveMeBuyActionMsg eventMsg) {
		
		writer.println("Player: " + eventMsg.getPlayer().getName());
		reader.setCanBuy(true);
		writer.println(stringJar.buyMessage());
		writer.flush();
		
	}

	/**
	 * {@inheritDoc}
	 * Simply prints to console the transactions List contained in the message.
	 */
	@Override
	public void handle(EndBazaarMsg eventMsg) {
		
		writer.println(stringJar.bazaarEnd());
		writer.println(eventMsg.getTransactions());
		writer.flush();
		
	}
	
	/**
	 * {@inheritDoc}
	 * Simply prints to console the welcome message contained in the message.
	 */
	@Override
	public void handle(WelcomeBazaarEventMsg eventMsg) {
		
		writer.println(eventMsg.getString());
		writer.flush();
		
	}
	
	/**
	 * {@inheritDoc}
	 * Paints the message through {@link CliPainter#paint(BazaarStatusMsg)}
	 * 
	 */
	@Override
	public void handle(BazaarStatusMsg eventMsg) {
		
		cliPainter.paint(eventMsg);
		
	}
	
	/**
	 * {@inheritDoc}
	 * Simply prints to console the chat message received.
	 */
	@Override
	public void handle(ChatEventMsg eventMsg) {
		
		writer.println(eventMsg.getString());
		writer.flush();
		
	}
	
	/**
	 * {@inheritDoc}
	 * Prints to console the end turn message contained in the message and sets 
	 * CommandReader to not allow turn related actions.
	 */
	@Override
	public void handle(EndTurnEventMsg eventMsg) {
		
		writer.println(eventMsg.getString());
		writer.flush();
		reader.setCanDoMainAction(false);
		reader.setCanDoQuickAction(false);	
		
	}
	
	/**
	 * {@inheritDoc}
	 * Prints to console the end sell turn message and sets 
	 * CommandReader to not allow sell turn related actions.
	 */
	@Override
	public void handle(EndSellTurnEventMsg endSellTurnEventMsg) {
		reader.setCanSellAssistant(false);
		reader.setCanSellPoliticCards(false);
		reader.setCanSellPermitTiles(false);
		writer.println(endSellTurnEventMsg.getString());
		writer.flush();
	}
	
	/**
	 * {@inheritDoc}
	 * Prints to console the end buy turn message and sets 
	 * CommandReader to not allow buy turn related actions.
	 */
	@Override
	public void handle(EndBuyTurnEventMsg endBuyTurnEventMsg) {
		reader.setCanBuy(false);
		writer.println(endBuyTurnEventMsg.getString());
		writer.flush();
	}
	
	/**
	 * {@inheritDoc}
	 * Paints the message through {@link CliPainter#paint(EndGameEventMsg)}
	 */
	@Override
	public void handle(EndGameEventMsg endGameEventMsg) {
		
		cliPainter.paint(endGameEventMsg);
		
	}
	
	/**
	 * {@inheritDoc} 
	 * Creates a new CliManager and starts a new thread to manage System.in input.
	 */
	@Override
	public void initialize(RequestHandler requestHandler, PlayerID player) {
			
		cliManager = new CliManager(requestHandler, reader, player);
    	Thread client = new Thread(cliManager);
    	client.start();
        	
	}

	
}
