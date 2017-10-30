package it.polimi.ingsw.cg28.view.messages.event;


import it.polimi.ingsw.cg28.view.ViewHandler;

/**
 * An event message sent by the server indicating the successful subscription after a request.
 * @author Alessandro, Marco, Mario
 *
 */
public class SubscribeEventMsg extends EventMsg {

	private static final long serialVersionUID = -9172151463149350173L;
	boolean isSubscribed;
	
	/**
	 * The constructor of the class.
	 * @param isSubscribed
	 */
	public SubscribeEventMsg(boolean isSubscribed) {
		this.isSubscribed = isSubscribed;
	}
	
	/**
	 * Returns if subscription event message is positive or not
	 * @return boolean value of isSubscribed
	 */
	public boolean isSubscribed() {
		return isSubscribed;
	}

	/**
	 * Subscribe event message doesn't need to be read by ViewHandler
	 */
	@Override
	public void read(ViewHandler viewHandler) {
		return;
	}

}
