package it.polimi.ingsw.cg28.view.gui.listeners.actionbuttons;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import it.polimi.ingsw.cg28.view.gui.windows.GameBoardWindow;
import it.polimi.ingsw.cg28.view.messages.action.turn.OneMoreMainActionMsg;

/**
 * Listener for the OneMoreActionButton.
 * @author Alessandro
 *
 */
public class OneMoreMainActionButtonListener extends MouseAdapter{

	private GameBoardWindow window;
	
	/**
	 * Constructor of the class. 
	 * @param window - the game Board Window
	 */
	public OneMoreMainActionButtonListener(GameBoardWindow window) {
		this.window = window;
	}
	
	/**
	 * When the button is clicked, a new OneMoreMainActionMsg is created and notified.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		OneMoreMainActionMsg msg = new OneMoreMainActionMsg(window.getPlayer());
		window.getGuiHandler().notify(msg);
	}
}
