/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.listeners.actionbuttons;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import it.polimi.ingsw.cg28.view.gui.windows.GameBoardWindow;
import it.polimi.ingsw.cg28.view.messages.action.turn.EmporiumTileActionMsg;

/**
 * Listener for the EmporiumTileActionButton.
 * @author Alessandro
 *
 */
public class EmporiumTileActionButtonListener extends MouseAdapter {
	
	private GameBoardWindow window;
	
	/**
	 * Constructor of the class. 
	 * @param window - the game Board Window
	 */
	public EmporiumTileActionButtonListener(GameBoardWindow window) {
		this.window = window;
	}
	
	/**
	 * When clicked, it creates and notify a ChangeTileActionMsg.
	 */
	@Override
	public void mouseClicked(MouseEvent arg0) {
		EmporiumTileActionMsg msg = new EmporiumTileActionMsg(window.getPlayer());
		window.getGuiHandler().notify(msg);	
	}
}
