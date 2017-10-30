package it.polimi.ingsw.cg28.view.messages.event;

import java.util.List;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.model.BazaarModel;
import it.polimi.ingsw.cg28.tmodel.TProduct;
import it.polimi.ingsw.cg28.view.ViewHandler;

/**
 * Represents the event message containing the status of the bazaar for this round.
 * @author Marco
 *
 */
public class BazaarStatusMsg extends EventMsg {

	private static final long serialVersionUID = 8769427408018082193L;
	private List<TProduct> shelf;
	
	/**
	 * The constructor of the class, creates a new BazaarStatusMsg.
	 * @param bazaarModel - The bazaar model to base the status representation on
	 * @param currentPlayer - The player related to this request
	 * @throws NullPointerException if any parameter is null
	 */
	public BazaarStatusMsg(BazaarModel bazaarModel, PlayerID currentPlayer) {
		Preconditions.checkNotNull(bazaarModel, "The bazaar model to refer to can't be null.");
		Preconditions.checkNotNull(currentPlayer, "The associated player can't be null.");
		
		this.shelf = bazaarModel.gettShelf();
		setPlayer(currentPlayer);
	}

	/**
	 * Fetches the list of products on sale.
	 * @return A List of TProducts containing all products on sale
	 */
	public List<TProduct> getShelf() {
		return shelf;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void read(ViewHandler viewHandler) {
		viewHandler.handle(this);
	}

}
