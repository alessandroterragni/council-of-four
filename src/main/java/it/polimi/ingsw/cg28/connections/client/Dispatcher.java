package it.polimi.ingsw.cg28.connections.client;

import it.polimi.ingsw.cg28.view.ViewController;
import it.polimi.ingsw.cg28.view.messages.event.EventMsg;

/**
 * Runnable object to dispatch messages received using a separate thread
 * @author Mario
 *
 */
public class Dispatcher implements Runnable{
	
	private EventMsg msg;
	private ViewController view;
	
	/**
	 * Constructor of the class
	 * @param msg to dispatch
	 * @param view viewController to dispatch the message
	 */
	public Dispatcher(EventMsg msg, ViewController view){
		this.view = view;
		this.msg = msg;
	}
	
	/**
	 * Run method to dispatch the message using a separate thread
	 */
	@Override
	public void run(){
		
		view.readMsg(msg);
		
	}

}
