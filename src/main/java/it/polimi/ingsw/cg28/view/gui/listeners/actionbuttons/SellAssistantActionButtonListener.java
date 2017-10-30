/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.listeners.actionbuttons;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import it.polimi.ingsw.cg28.view.gui.windows.GameBoardWindow;
import it.polimi.ingsw.cg28.view.messages.action.bazaar.SellAssistantsActionMsg;

/**
 * Listener for the SellAssistantActionButton.
 * @author Alessandro
 *
 */
public class SellAssistantActionButtonListener extends MouseAdapter{
	
	private GameBoardWindow window;
	
	/**
	 * Constructor of the class. 
	 * @param window - the game Board Window
	 */
	public SellAssistantActionButtonListener(GameBoardWindow window) {
		this.window = window;
	}
	
	/**
	 * When the button is clicked, it creates and sends a {@link SellAssistantsActionMsg}.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		SellAssistantsActionMsg msg = new SellAssistantsActionMsg(window.getPlayer());
		window.getGuiHandler().notify(msg);
			
	}

}
