/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.listeners.actionbuttons;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import it.polimi.ingsw.cg28.view.gui.windows.GameBoardWindow;
import it.polimi.ingsw.cg28.view.messages.action.EndSellTurnMsg;

/**
 * Listener for the EndSellTurnActionButton.
 * @author Alessandro
 *
 */
public class EndSellTurnActionButtonListsner extends MouseAdapter {
	
	private GameBoardWindow window;
	
	/**
	 * Constructor of the class. 
	 * @param window - the game Board Window
	 */
	public EndSellTurnActionButtonListsner(GameBoardWindow window) {
		this.window = window;
	}
	
	/**
	 * When clicked, it creates and notify an EndSellTurnMsg.
	 */
	@Override
	public void mouseClicked(MouseEvent arg0) {
		EndSellTurnMsg msg = new EndSellTurnMsg();
		window.getGuiHandler().notify(msg);
		
	}
}
