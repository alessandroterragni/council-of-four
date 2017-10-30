package it.polimi.ingsw.cg28.view.cli;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.connections.client.Client;
import it.polimi.ingsw.cg28.controller.ActionMsgHandlerInterface;
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
import it.polimi.ingsw.cg28.view.messages.action.bazaar.BazaarActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.bazaar.BuySalableActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.TurnActionMsg;

/**
 * Concrete visit strategy implementation of ActionMsgHandlerInterface.
 * Specifies how to manage different ActionMsg for a Cli Client: how to fill them correctly.
 * @author Mario
 *
 */
public class FilledMsgHandler implements ActionMsgHandlerInterface {
	
	private static final Logger log = Logger.getLogger(Client.class.getName());
	
	private CliManager manager;
	private CommandReader reader;
	private PrintWriter writer;
	
	/**
	 * Constructor of the class.
	 * @param manager - CliManager to process messages.
	 * @param reader - CommandReader to manage input.
	 * @throws NullPointerException if one of the two parameter is null.
	 */
	public FilledMsgHandler(CliManager manager, CommandReader reader) {
		
		Preconditions.checkNotNull(manager);
		Preconditions.checkNotNull(reader);
		
		this.manager = manager;
		this.reader = reader;
		this.writer = reader.getWriter();
	}
	
	/**
	 * {@inheritDoc}
	 * Generic ActionMsg can't be filled. Logs to the log an error message and returns.
	 */
	@Override
	public void handle(ActionMsg actionMsg) {
		log.fine("Generic message handled");
	}
	
	/**
	 * Specifies how to fill a StartMsg, ask to the player configuration parameter, set the message and ask the CliManager
	 * to process the message (also removes the filled message on CliManager).
	 */
	@Override
	public void handle(StartMsg startMsg) {
		
		try{
				writer.println("Do you want to play with the Bazaar? (true or false)");
				writer.flush();
				boolean b = reader.nextBoolean();
			    startMsg.setBazaar(b);
			
		} catch (InputMismatchException | IOException e) {
				log.log(Level.WARNING, "Exception Input",e);
				writer.println("Invalid input! Type #fill again!");
				writer.flush();
				return;
			}
		
		manager.setMessage(null);
		manager.processRequest(startMsg);

	}
	
	/**
	 * Private method to print a request and to return a response read by input.
	 * @param codeRequest - The request to print on console
	 * @return response read by input through CommandReader
	 */
	private String takeCode(String codeRequest){
		
		String input = null;
		
		try{
			
			writer.println(codeRequest);
			writer.flush();
			input = reader.nextLine();
			
		} catch (InputMismatchException | IOException e) {
			writer.println("Invalid input!");
			writer.flush();
			log.log(Level.WARNING, "Exception Input",e);
			return "Q";
		}
		
		return input;
		
	}

	/**
	 * Specifies how to fill a TurnActionMsg, ask to the player codes to identify game objects he/she
	 * wants to use to perform an action, set the message with codes and ask the CliManager
	 * to process the message (also removes the filled message on CliManager).
	 */
	@Override
	public void handle(TurnActionMsg actionMsg) {
		
		if(actionMsg.getNeedCode()){
			
			actionMsg.getShowChoices(new CliPainter(writer));
			
			String[] codes = new String[actionMsg.getCodes().length];
			String[] codesRequest = actionMsg.getCodesRequest();
			
			writer.println("Press Q to quit action!");
			
			boolean check = false;
			
			while(!check){
				for (int i =0; i<codes.length ; i++){
					
					String input = takeCode(codesRequest[i]);
					
					if (input.matches("Q")){
						writer.println("Type another command!");
						writer.flush();
						return;
					}
					
					codes[i] = input;
					
				}
				
				check = true;
				
				if(!actionMsg.setCodes(codes)){
					check = false;
					writer.println("Insert numbers agains:\n");
					writer.flush();
				}
			}
		}
		
		manager.setMessage(null);
		manager.processRequest(actionMsg);
		
	}
	
	/**
	 * Specifies how to fill a BuySalableActionMsg, handles it like a BazaarActionMsg
	 * {@link #handle(BazaarActionMsg)}.
	 */
	@Override
	public void handle(BuySalableActionMsg actionMsg) {
		
		BazaarActionMsg action = actionMsg;
		handle(action);

	}
	
	/**
	 * Specifies how to fill a BazaarActionMsg, handles it like a TurnActionMsg
	 * {@link #handle(TurnActionMsg)}.
	 */
	@Override
	public void handle(BazaarActionMsg actionMsg) {
		TurnActionMsg action = actionMsg;
		handle(action);
	}
	
	/**
	 * {@inheritDoc}
	 * QuitActionMsg can't be filled, simply returns.
	 */
	@Override
	public void handle(QuitActionMsg actionMsg) {
		return;
	}
	
	/**
	 * {@inheritDoc}
	 * EndTurnMsg can't be filled, simply returns.
	 */
	@Override
	public void handle(EndTurnMsg actionMsg) {
		return;
	}
	
	/**
	 * {@inheritDoc}
	 * EndSellTurnMsg can't be filled, simply returns.
	 */
	@Override
	public void handle(EndSellTurnMsg actionMsg) {
		return;
	}
	
	/**
	 * {@inheritDoc}
	 * EndBuyTurnMsg can't be filled, simply returns.
	 */
	@Override
	public void handle(EndBuyTurnMsg actionMsg) {
		return;
	}
	
	/**
	 * {@inheritDoc}
	 * BazaarStartActionMsg can't be processed by a Cli client, simply returns.
	 */
	@Override
	public void handle(BazaarStartActionMsg actionMsg) {
		return;
	}
	
	/**
	 * {@inheritDoc}
	 * BazaarStopActionMsg can't be processed by a Cli client, simply returns.
	 */
	@Override
	public void handle(BazaarStopActionMsg actionMsg) {
		return;
	}
	
	/**
	 * {@inheritDoc}
	 * ShowPlayerStatusActionMsg can't be filled, simply returns.
	 */
	@Override
	public void handle(ShowPlayerStatusActionMsg actionMsg) {
		return;
	}
	
	/**
	 * {@inheritDoc}
	 * ShowBazaarStatusMsg can't be filled, simply returns.
	 */
	@Override
	public void handle(ShowBazaarStatusMsg actionMsg) {
		return;
	}
	
	/**
	 * {@inheritDoc}
	 * ChatActionMsg can't be filled, simply returns.
	 */
	@Override
	public void handle(ChatActionMsg chat) {
		return;
	}
	
	/**
	 * {@inheritDoc}
	 * EndGameMsg can't be processed by a Cli client, simply returns.
	 */
	@Override
	public void handle(EndGameMsg actionMsg) {
		return;
	}
	
	/**
	 * {@inheritDoc}
	 * LeaveGameActionMsg can't be filled, simply returns.
	 */
	@Override
	public void handle(LeaveGameActionMsg actionMsg) {
		return;
	}

}
