/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.listeners.actionbuttons;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import it.polimi.ingsw.cg28.view.gui.windows.GameBoardWindow;
import it.polimi.ingsw.cg28.view.messages.action.EndTurnMsg;

/**
 * Listener for the EndTurnActionButton.
 * @author Alessandro
 *
 */
public class EndTurnButtonListener extends MouseAdapter{

	private GameBoardWindow window;
	
	/**
	 * Constructor of the class. 
	 * @param window - the game Board Window
	 */
	public EndTurnButtonListener(GameBoardWindow window) {
		this.window = window;
	}
	
	/**
	 * When the button is clicked, a new EndTurnMsg is created and notified.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		EndTurnMsg msg = new EndTurnMsg(window.getPlayer());
		window.getGuiHandler().notify(msg);
	}
}
