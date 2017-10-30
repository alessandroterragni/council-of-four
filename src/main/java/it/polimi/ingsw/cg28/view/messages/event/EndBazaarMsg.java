package it.polimi.ingsw.cg28.view.messages.event;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.view.ViewHandler;

/**
 * Represents an event message containing the execution of the closure of the bazaar phase of the game.
 * @author Marco
 *
 */
public class EndBazaarMsg extends EventMsg {

	private static final long serialVersionUID = -7035494814415132277L;
	private String transactions;
	
	/**
	 * The constructor of the class, creates a new EndBazaarMsg.
	 * @param string - The message associated with this event. This represents the transactions that
	 * happened in this bazaar round
	 * @throws NullPointerException if the given string is null
	 */
	public EndBazaarMsg(String string) {
		Preconditions.checkNotNull(string, "The associated message can't be null.");
		
		this.transactions = string;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void read(ViewHandler viewHandler) {
		
		viewHandler.handle(this);
		
	}

	/**
	 * Fetches the string representing the current bazaar round transactions.
 	 * @return A string containing the transactions happened during the current round
	 */
	public String getTransactions() {
		return transactions;
	}

}
