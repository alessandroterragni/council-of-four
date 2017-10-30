/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.listeners.actionbuttons;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import it.polimi.ingsw.cg28.view.gui.windows.GameBoardWindow;
import it.polimi.ingsw.cg28.view.messages.action.EndBuyTurnMsg;

/**
 * Listener for the EndBuyTurnActionButton.
 * @author Alessandro
 *
 */
public class EndBuyTurnActionButtonListener extends MouseAdapter {
	
	private GameBoardWindow window;
	
	/**
	 * Constructor of the class. 
	 * @param window - the game Board Window
	 */
	public EndBuyTurnActionButtonListener(GameBoardWindow window) {
		this.window = window;
	}
	
	/**
	 * When clicked, it creates and notify a EndBuyTurnMsg.
	 */
	@Override
	public void mouseClicked(MouseEvent arg0) {
		EndBuyTurnMsg msg = new EndBuyTurnMsg();
		window.getGuiHandler().notify(msg);
	}
	
}
