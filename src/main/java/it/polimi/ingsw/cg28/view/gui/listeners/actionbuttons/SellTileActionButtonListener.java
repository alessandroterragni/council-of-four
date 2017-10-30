/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.listeners.actionbuttons;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import it.polimi.ingsw.cg28.view.gui.windows.GameBoardWindow;
import it.polimi.ingsw.cg28.view.messages.action.bazaar.SellPermitTilesActionMsg;

/**
 * Listener for the SellTileActionButton.
 * @author Alessandro
 *
 */
public class SellTileActionButtonListener extends MouseAdapter{
	
	private GameBoardWindow window;
	
	/**
	 * Constructor of the class. 
	 * @param window - the game Board Window
	 */
	public SellTileActionButtonListener(GameBoardWindow window) {
		this.window = window;
	}
	
	/**
	 * When the button is clicked, it creates and sends a {@link SellPermitTilesActionMsg}.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		SellPermitTilesActionMsg msg = new SellPermitTilesActionMsg(window.getPlayer());
		window.getGuiHandler().notify(msg);		
	}
}
