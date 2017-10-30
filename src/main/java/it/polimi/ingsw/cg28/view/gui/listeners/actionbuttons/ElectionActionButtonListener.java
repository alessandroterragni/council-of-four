/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.listeners.actionbuttons;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import it.polimi.ingsw.cg28.view.gui.windows.GameBoardWindow;
import it.polimi.ingsw.cg28.view.messages.action.turn.ElectionActionMsg;

/**
 * Listener for the ElectionActionButton.
 * @author Alessandro
 *
 */
public class ElectionActionButtonListener extends MouseAdapter{

	private GameBoardWindow window;
	
	/**
	 * Constructor of the class.
	 * @param window - the gameBoardWindow
	 */
	public ElectionActionButtonListener(GameBoardWindow window) {
		this.window = window;
	}
	
	/**
	 * When the button is clicked, a new ElectionActionMsg is created and notified.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		ElectionActionMsg msg = new ElectionActionMsg(window.getPlayer());
		window.getGuiHandler().notify(msg);
			
	}
}
