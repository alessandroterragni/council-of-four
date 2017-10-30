/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.listeners.actionbuttons;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import it.polimi.ingsw.cg28.view.gui.windows.GameBoardWindow;
import it.polimi.ingsw.cg28.view.messages.action.bazaar.BuySalableActionMsg;

/**
 * Listener for the buySalableActionButton.
 * @author Alessandro
 *
 */
public class BuyActionButtonListener extends MouseAdapter{
	
private GameBoardWindow window;
	
	/**
	 * Constructor of the class.
	 * @param window - the game Board Window
	 */
	public BuyActionButtonListener(GameBoardWindow window) {
		this.window = window;
	}
	
	/**
	 * When clicked, it creates and notifies a BuySalableActionMsg.
	 */
	@Override
	public void mouseClicked(MouseEvent arg0) {
		BuySalableActionMsg msg = new BuySalableActionMsg(window.getPlayer());
		window.getGuiHandler().notify(msg);	
	}
	
}
