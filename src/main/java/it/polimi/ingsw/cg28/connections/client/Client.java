package it.polimi.ingsw.cg28.connections.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.NotBoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.cg28.connections.RequestHandler;
import it.polimi.ingsw.cg28.view.ViewController;
import it.polimi.ingsw.cg28.view.ViewHandler;
import it.polimi.ingsw.cg28.view.cli.CliHandler;
import it.polimi.ingsw.cg28.view.cli.CommandReader;
import it.polimi.ingsw.cg28.view.gui.GuiHandler;

/**
 * Entry point for a single Player by command line. (Allows the player to switch also to gui interface).
 * @author Mario
 *
 */
public class Client {

    private static final Logger log = Logger.getLogger(Client.class.getName());
    
    /**
     * Private constructor to hide the implicit public one. Class Client couldn't be instantiated.
     */
    private Client (){
    }
    
    /**
     * Allows the user to decide name, connection type (RMI or SOCKET) and interface type (CLI or GUI).
     * Sets the connection and initialises objects to manage the game client-side.
     * @param args
     */
    public static void main(String[] args) {
    
    	PrintWriter writer = new PrintWriter(System.out);
    	
    	CommandReader commandReader = new CommandReader(writer);
    	String playerName = null;
    	String gameInterface = "gameInterface";
    	String connectionType = "connectionType";
    	
    	try{
    		
    		do {
    			writer.println("Enter your name: ");
        		writer.flush();
        		
        		playerName = commandReader.nextLine();
    			
    		} while (playerName.matches("^\\s*$"));
    		
    	
    		do {
    			writer.println("Enter GUI or CLI to choose game interface:");
    			writer.flush();
    			
    			gameInterface = commandReader.nextLine();
    			
    		} while (!gameInterface.matches("^(GUI|CLI)$"));
    		
    		 do {
    	        	writer.println("Enter RMI or SOCKET to choose the connection type:");
    	        	writer.flush();
    	            connectionType = commandReader.nextLine();
    	        } while (!connectionType.matches("^(RMI|SOCKET)$"));
    			
    	} catch (IOException e){
    		log.log(Level.WARNING, "IOException Client", e);
    	}
    	
    	ViewHandler handler;
    	
    	if (gameInterface.matches("^(GUI)$")) {
    		handler = new GuiHandler(null);
        } else {
        	handler = new CliHandler(commandReader);
        }
    	
    	ViewController controller = new ViewController(handler);

        try {

            ConnectionTypeFactory connectionTypeFactory;

            if (connectionType.matches("^(RMI)$")) {
            	connectionTypeFactory = new RMIFactory("localhost", controller, playerName);
            } else {
            	connectionTypeFactory = new SocketFactory("localhost", controller, playerName);
            }

            RequestHandler requestHandler = connectionTypeFactory.getRequestHandler();
            
            commandReader.initGame();
            
            handler.initialize(requestHandler, connectionTypeFactory.getPlayerID());
                
        } catch (IOException e) {
        	log.log(Level.WARNING, "Socket Exception", e);
        } catch (NotBoundException e) {
        	log.log(Level.WARNING, "RMI Exception", e);
        }
    }
 }


