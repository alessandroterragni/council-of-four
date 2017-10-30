/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.listeners.actionbuttons;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import it.polimi.ingsw.cg28.view.gui.windows.GameBoardWindow;
import it.polimi.ingsw.cg28.view.messages.action.bazaar.SellPoliticCardsActionMsg;

/**
 * Listener for the SellPoliticCardActionButton.
 * @author Alessandro
 *
 */
public class SellPoliticCardButtonListener extends MouseAdapter {
	 
	private GameBoardWindow window;
	
	/**
	 * Constructor of the class. 
	 * @param window - the game Board Window
	 */
	public SellPoliticCardButtonListener(GameBoardWindow window) {
		this.window = window;
	}
	
	/**
	 * When the button is clicked, it creates and sends a {@link SellPoliticCardsActionMsg}.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		SellPoliticCardsActionMsg msg = new SellPoliticCardsActionMsg(window.getPlayer());
		window.getGuiHandler().notify(msg);
			
	}
}
