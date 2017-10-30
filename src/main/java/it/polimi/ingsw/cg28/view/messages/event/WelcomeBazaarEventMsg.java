package it.polimi.ingsw.cg28.view.messages.event;

import it.polimi.ingsw.cg28.view.ViewHandler;

/**
 * Represents an event message containing the execution of the start of the bazaar phase of the game.
 * @author Marco
 *
 */
public class WelcomeBazaarEventMsg extends EventMsg {
	
	private static final long serialVersionUID = 984453431189509380L;
	private String welcome = "\nApu says: Welcome to my Bazaar!\nSHOPLIFTERS WILL BE EXECUTED!\nHere you can sell "
			+ "your items (Assistants, Politic Card and Permit Tiles)\n\n";
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void read(ViewHandler viewHandler) {
		viewHandler.handle(this);
	}

	/**
	 * Fetches the welcome message for the bazaar phase.
	 * @return The string message that welcomes players into the bazaar phase
	 */
	public String getWelcome() {
		return welcome;
	}

	/**
	 * Fetches the welcome message for the bazaar phase.
	 * @return The string message that welcomes players into the bazaar phase
	 */
	public String getString() {
		return welcome;
	}

}
