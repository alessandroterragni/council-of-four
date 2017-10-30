package it.polimi.ingsw.cg28.view.cli;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.connections.RequestHandler;
import it.polimi.ingsw.cg28.connections.client.Client;
import it.polimi.ingsw.cg28.controller.ActionMsgHandlerInterface;
import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.view.messages.action.ActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.ChatActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.LeaveGameActionMsg;
import it.polimi.ingsw.cg28.view.messages.event.FilledMsg;

/**
 * Manages the update of a CLI client.
 * @author Alessandro, Marco, Mario
 *
 */
public class CliManager implements Runnable {
	
	private static final Logger log = Logger.getLogger(Client.class.getName());
	
	private RequestHandler requestHandler;
	private PlayerID player;
	private FilledMsg message;
	private AtomicBoolean toFill;
	private CommandReader reader;
	private ActionMsgHandlerInterface handler;
	
	/**
	 * The constructor of the class, creates a new client manager.
	 * @param requestHandler - The associated request handler
	 * @param reader - Reader to manage input from System.In
	 * @param player - The player associated with the client to be managed
	 * @throws NullPointerException if one of the three parameter is null
	 */
	public CliManager(RequestHandler requestHandler, CommandReader reader, PlayerID player) {
		
		Preconditions.checkNotNull(requestHandler, "RequestHandler can't be null");
		Preconditions.checkNotNull(reader, "Reader can't be null");
		Preconditions.checkNotNull(player, "PlayerID can't be null");
		
		this.requestHandler = requestHandler;
		this.player = player;
		this.reader = reader;
		toFill = new AtomicBoolean(false);
		handler = new FilledMsgHandler(this, reader);
		reader.help();
	}
	
	/**
	 * Processes the request via the Request handler.
	 * @param change - ActionMsg request to process.
	 */
	public void processRequest(ActionMsg change) {
		
		if (change == null) {
	        log.warning("Change could not be null!");
	        return;
	    }

		change.setPlayerID(player);

         try {
			requestHandler.processRequest(change);
		} catch (RemoteException e) {
			log.log(Level.WARNING, "Error during processRequest", e);
		}
	}
	
	/**
	 * Runs thread to listen and process input from System.In through CommandReader
	 */
	@Override
	public void run() {
		
		while(true){
			
			String input;
			try {
				
				input = reader.nextLine();
				ActionMsg action = checkStr(input);
				
				if(action != null)
					processRequest(action);
				
			} catch (IOException e) {
				log.log(Level.WARNING, "Error System.In", e);
			}
			
		}
		
	}
	
	/**
	 * Manages the request to send a chat message returning the related ChatActionMsg.
	 * @param strings - chat message split by white space
	 * @return ChatActionMsg containing the message
	 */
	private ActionMsg chat(String[] strings){	
		
		if(strings.length > 1)
			return new ChatActionMsg(reader.buildString(strings));
		return null;
		
	}
	
	/**
	 * Manages the request to leave a Game returning the related LeaveGameActionMsg.
	 * If the player write also a leave message, adds the message to the LeaveGameActionMsg.
	 * @param strings - leave message split by white space
	 * @return LeaveActionMsg eventually containing the leave message
	 */
	private ActionMsg leave(String[] strings){	
		
		if(strings.length > 1)
			return new LeaveGameActionMsg(reader.buildString(strings));
		return new LeaveGameActionMsg("");
		
	}
	
	/**
	 * Parses the action code returning the correct TurnActionMsg related.
	 * @param strings - strings[1] must contain the code
	 * @return TurnActionMsg related to the code
	 */
	private ActionMsg action(String[] strings){	
		
		if(strings.length > 1)
			return reader.parserIndexAction(strings[1], player);
		
		return null;
		
	}
	
	/**
	 * If there is a message to fill, through FilledMsgHandler asks the user to fill the message and
	 * processes it directly returning null. Otherwise warns the player there isn't a message to fill.
	 * @return null
	 */
	private ActionMsg fill(){	
		
		if (toFill.get()){
			message.getActionMsg().handle(handler);
		}
		else 
			reader.errorFilledMsg();
		
		return null;
		
	}
	
	/**
	 * Parses the bazaar sell action code returning the correct TurnActionMsg related.
	 * @param strings - strings[1] must contain the code
	 * @return TurnActionMsg related to the code
	 */
	private ActionMsg sell(String[] strings){	
		
		if(strings.length > 1)
			return reader.parserIndexBazaarAction(strings[1], player);
		
		return null;
		
	}
	
	/**
	 * Parses the bazaar buy action code returning the correct TurnActionMsg related.
	 * @param strings - strings[1] must contain the code
	 * @return TurnActionMsg related to the code
	 */
	private ActionMsg buy(String[] strings){	
		
		if(strings.length > 1)
			return reader.buyCheck(strings, player);
		
		return null;
		
	}
	
	/**
	 * Returns null. Prints the HELP message with command accepted by CLI.
	 * @return null
	 */
	private ActionMsg help(){
		
		reader.help();
		return null;
		
	}
	
	
	/**
	 * Parses the input received from System.In returning the right ActionMsg related.
	 * @param strLine - string read by System.In
	 * @return ActionMsg requested, null if the string doesn't match any message
	 */
	public ActionMsg checkStr(String strLine) {
		
		String[] strings = strLine.split(" ");
		
		switch(strings[0]){

		case "#action": return action(strings);
		
		case "#fill": return fill();
		
		case "#sell": return sell(strings);
		
		case "#buy": return buy(strings);
		
		default: return checkNotGameActions(strings);
			
		}
		
	}
	
	/**
	 * Parses the input received from System.In in case the input doesn't match a
	 * TurnActionMsg.
	 * @param strLine - string read by System.In
	 * @return ActionMsg requested, null if the string doesn't match any message
	 */
	private ActionMsg checkNotGameActions(String[] strings) {
		
		switch(strings[0]){
		
			case "#chat": return chat(strings);
				
			case "#leave": return leave(strings);
		
			case "#help": return help();
			
			default: return null;
			
		}
	}
	
	/**
	 * Sets a filled message to the manager.
	 * @param message - FilledMsg to set, if message is null sets there isn't a filled message to fill.
	 */
	public void setMessage(FilledMsg message) {
		
		this.message = message;
		
		if (message == null)
			toFill.set(false);
		else toFill.set(true);
		
	}
	
	
}
