package it.polimi.ingsw.cg28.connections.client;

import java.rmi.RemoteException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.polimi.ingsw.cg28.connections.SubscriberInterface;
import it.polimi.ingsw.cg28.view.ViewController;
import it.polimi.ingsw.cg28.view.messages.event.EventMsg;

/**
 * Represents a subscriber.
 * @author Alessandro, Marco, Mario
 *
 */
public class Subscriber implements SubscriberInterface {

    private ViewController viewController;
    private ExecutorService dispatcher;
   
    /**
     * The constructor of the class, creates a new Subscriber.
     * @param viewController - The view controller to associate to this subscriber
     */
    Subscriber(ViewController viewController) {

        this.viewController = viewController;
        this.dispatcher = Executors.newFixedThreadPool(1);
      
    }

    /**
     * {@inheritDoc} - Dispatches an EventMsg through an executor.
     */
	@Override
	public void dispatchMessage(EventMsg message) throws RemoteException {
		
		dispatcher.execute(new Dispatcher(message, viewController));
		
	}
	
	
	

}
