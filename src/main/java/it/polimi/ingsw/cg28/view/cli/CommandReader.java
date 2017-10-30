package it.polimi.ingsw.cg28.view.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.view.StringJar;
import it.polimi.ingsw.cg28.view.messages.action.ActionMsg;
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

/**
 * CommandReader manages reading through System.In, offers methods to parse and manage input and 
 * contains information allowed to the player.
 * @author Mario, Marco
 *
 */
public class CommandReader {
	
    private BufferedReader systemIn;
    private PrintWriter writer;
    private boolean[] actions;
    private StringJar stringJar;
    
    /**
     * The constructor of the class: creates a new command reader setting the input stream,
     * takes the writer to write on the console.
     * @param writer - PrintWriter to write on console
     * @throws NullPointerException if writer parameter is null
     */
    public CommandReader(PrintWriter writer){
    	
    	Preconditions.checkNotNull(writer);
    	
    	systemIn = new BufferedReader(new InputStreamReader(System.in));
    	this.writer = writer;
    	actions = new boolean[6];
    	this.stringJar = new StringJar();
    }
    
    /**
     * Reads and returns the next line from the input stream specified in the constructor.
     * @return the next line from the input stream
     * @throws IOException if any problem occurs during the reading process
     */
    public String nextLine() throws IOException{
    	
    	return systemIn.readLine();
    	
    }
    
    /**
     * Returns true if the player is allowed to do a main action.
     * @return true if the player is allowed to do a main action
     */
    public boolean canDoMainAction(){
    	return actions[0];
    }
    
    /**
     * Returns true if the player is allowed to do a quick action.
     * @return true if the player is allowed to do a quick action
     */
    public boolean canDoQuickAction(){
    	return actions[1];
    }
    
    /**
     * Returns true if the player is allowed to sell assistants.
     * @return true if the player is allowed to sell assistants
     */
    public boolean canSellAssistant(){
    	return actions[2];
    }
    
    /**
     * Returns true if the player is allowed to sell politic cards.
     * @return true if the player is allowed to sell politic cards
     */
    public boolean canSellPoliticCards(){
    	return actions[3];
    }
    
    /**
     * Returns true if the player is allowed to sell permit tiles.
     * @return true if the player is allowed to sell permit tiles
     */
    public boolean canSellPermitTiles(){
    	return actions[4];
    }
    
    /**
     * Returns true if the player is allowed to buy bazaar items.
     * @return true if the player is allowed to buy bazaar items.
     */
    public boolean canBuy(){
    	return actions[5];
    }
    
    /**
     * Allows to set if the player is allowed to do a main action.
     * @param value - true if the player is allowed to do a main action, false otherwise.
     */
    public void setCanDoMainAction(boolean value){
    	actions[0] = value;
    }
    
    /**
     * Allows to set if the player is allowed to do a quick action.
     * @param value - true if the player is allowed to do a quick action, false otherwise.
     */
    public void setCanDoQuickAction(boolean value){
    	actions[1] = value;
    }
    
    /**
     * Allows to set if the player is allowed to sell assistants.
     * @param value - true if the player is allowed to sell assistants, false otherwise.
     */
    public void setCanSellAssistant(boolean value){
    	actions[2] = value;
    }
    
    /**
     * Allows to set if the player is allowed to sell politic cards.
     * @param value - true if the player is allowed to sell politic cards, false otherwise.
     */
    public void setCanSellPoliticCards(boolean value){
    	actions[3] = value;
    }
    
    /**
     * Allows to set if the player is allowed to sell permit tiles.
     * @param value - true if the player is allowed to sell permit tiles, false otherwise.
     */
    public void setCanSellPermitTiles(boolean value){
    	actions[4] = value;
    }
    
    /**
     * Allows to set if the player is allowed to buy bazaar items.
     * @param value - true if the player is allowed to buy bazaar items, false otherwise.
     */
    public void setCanBuy(boolean value){
    	actions[5] = value;
    }

	
	/**
	 * Parses the input command string to extract the desired action message. Used for bazaar commands only.
	 * @param indexAction - The string containing the index of the action to be performed
	 * @param player - The ID of the requesting player
	 * @return the correct action message, or <code>null</code> if the input doesn't match any of the action codes
	 * or action request is not allowed for the player at the moment (in this case also prints an error message on console).
	 */
	public ActionMsg parserIndexBazaarAction(String indexAction, PlayerID player){
		
		if (!correctBazaarSellActionInput(indexAction)){
			writer.println("*** Number inserted not found/Couldn't perform this action now ***");
			writer.flush();
			return null;
		}
	
		switch(indexAction){
		
			case "1": 
				return new SellAssistantsActionMsg(player);
			case "2": 
				return new SellPoliticCardsActionMsg(player);
			case "3": 
				return new SellPermitTilesActionMsg(player);
			case "4":
				return new EndSellTurnMsg();
				
			default: return null;
		} 
		
	}
	
	/**
	 * Parses the input command string to extract the desired action message. Used for turn commands only.
	 * @param indexAction - The string containing the index of the action to be performed
	 * @param player - The ID of the requesting player
	 * @return the correct action message, or <code>null</code> if the input doesn't match any of the action codes
	 * or action request is not allowed for the player at the moment (in this case also prints an error message on console).
	 */
	public ActionMsg parserIndexAction(String indexAction, PlayerID player){
		
		if (!correctActionInput(indexAction)){
			writer.println("*** Number inserted not found/Couldn't perform this action now ***");
			writer.flush();
			return null;
		}
		
		switch(indexAction){
		
			case "1": 
				return new ElectionActionMsg(player);
			case "2": 
				return new PermitTileActionMsg(player);
			case "3": 
				return new EmporiumTileActionMsg(player);
			case "4": 
				return new EmporiumKingActionMsg(player);
			case "5": 
				return new HireAssistantActionMsg(player);
			case "6": 
				return new ChangeTileActionMsg(player);
			case "7": 
				return new SendAssistantActionMsg(player);
			case "8": 
				return new OneMoreMainActionMsg(player);
			case "9": 
				return new EndTurnMsg(player);
				
			default: return null;
		} 
		
	}
	
	/**
	 * Recognizes a true/false input ({@link java.lang.String#equalsIgnoreCase(String)}) and returns the corresponding boolean value.
	 * @return the boolean value indicated in the next input stream line
	 * @throws IOException if any issues during the reading process occurs
	 */
	public boolean nextBoolean() throws IOException{
		
		String strLine = systemIn.readLine();
		
		return !"false".equalsIgnoreCase(strLine);
		
		
	}
	
	/**
	 * Parses a buy request. If strings[1] is equals ({@link java.lang.String#equalsIgnoreCase(String)}) to <code>yes</code>
	 * returns a BuySalableActionMsg, if is equals to <code>no</code> returns a EndBuyTurnMsg. Otherwise returns null.
	 * @param strings - Array of strings to parse
	 * @param player - PlayerID of player requesting the action
	 * @return
	 */
	public ActionMsg buyCheck(String[] strings, PlayerID player) {
		
		if (!canBuy()){
			writer.println("*** Couldn't perform this action now ***");
			writer.flush();
			return null;
		}
		
		if (strings.length != 2)
			return null;
		
		if ("yes".equalsIgnoreCase(strings[1]))
			return new BuySalableActionMsg(player);
		
		if ("no".equalsIgnoreCase(strings[1]))
			return new EndBuyTurnMsg();
		
		writer.println("Insert your choice again\n");
		writer.flush();
		
		return null;
		
	}
	
	/**
	 * Method to rebuild a string split by white space in an array of strings (strings[0] is ignored).
	 * @param strings - Array to join strings of
	 * @return An unique string, concatenation of strings contained in strings parameter (strings[0] is ignored).
	 */
	public String buildString(String[] strings) {
		
		StringBuilder b = new StringBuilder();
		
		for(int i=1; i<strings.length ; i++){
			if(i != 1)
				b.append(" ");
			b.append(strings[i]);
		}
		return b.toString();
		
	}
	
	/**
	 * Method to evaluate if an action request (code related) is allowed.
	 * @param input - code related to turn action
	 * @return true if the action correspondent to the code is allowed, false otherwise
	 */
	private boolean correctActionInput(String input){
		
		if (canDoMainAction() && canDoQuickAction()) 
			return input.matches("[1-9]");
		
		if (canDoMainAction()) 
			return input.matches("[1-4]");
		
		if (canDoQuickAction())
			return input.matches("[5-9]");
	
		return false;
	
	}
	
	/**
	 * Method to evaluate if a bazaar sell action request (code related) is allowed.
	 * @param input - code related to bazaar sell action
	 * @return true if the action correspondent to the code is allowed, false otherwise
	 */
	private boolean correctBazaarSellActionInput(String input) {
		
		if (!input.matches("[1-4]"))
			return false;
		
		if(input.matches("1") && !canSellAssistant()) 
			return false;
		if(input.matches("2") && !canSellPoliticCards()) 
			return false;
		if(input.matches("3") && !canSellPermitTiles()) 
			return false;
		
		return true;
		
	}
	
	/**
	 * Prints on the console an error message <code>There isn't a message to fill! Try to type [action]</code>
	 */
	public void errorFilledMsg() {
		
		writer.println(stringJar.errorFilledMsg());
		writer.flush();
		
	}
	
	/**
	 * Prints on console the help message (commands accepted by command line).
	 */
	public void help() {
		
		writer.println(stringJar.help());
		writer.flush();
		
	}
	
	/**
	 * Prints on console the message of game initialisation.
	 */
	public void initGame() {
		 
		 writer.print(stringJar.initGame());
		 writer.flush();
		
	}
	
	/**
	 * Returns the PrintWriter to write on the console.
	 * @return PrintWriter to write on the console
	 */
	public PrintWriter getWriter() {
		return writer;
	}

	

}
