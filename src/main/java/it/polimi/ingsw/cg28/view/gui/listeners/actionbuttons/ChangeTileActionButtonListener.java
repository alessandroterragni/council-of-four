/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.listeners.actionbuttons;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import it.polimi.ingsw.cg28.view.gui.windows.GameBoardWindow;
import it.polimi.ingsw.cg28.view.messages.action.turn.ChangeTileActionMsg;

/**
 * Listener for the changeTileActionButton.
 * @author Alessandro
 *
 */
public class ChangeTileActionButtonListener extends MouseAdapter {
	
	private GameBoardWindow window;
	
	/**
	 * Constructor of the class.
	 * @param window - the game Board Window
	 */
	public ChangeTileActionButtonListener(GameBoardWindow window) {
		this.window = window;
	}
	
	/**
	 * When clicked, it creates and notify a ChangeTileActionMsg.
	 */
	@Override
	public void mouseClicked(MouseEvent arg0) {
		ChangeTileActionMsg msg = new ChangeTileActionMsg(window.getPlayer());
		window.getGuiHandler().notify(msg);	
	}

}
