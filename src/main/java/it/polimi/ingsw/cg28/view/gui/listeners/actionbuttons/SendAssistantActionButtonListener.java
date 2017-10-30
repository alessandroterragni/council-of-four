/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.listeners.actionbuttons;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import it.polimi.ingsw.cg28.view.gui.windows.GameBoardWindow;
import it.polimi.ingsw.cg28.view.messages.action.turn.SendAssistantActionMsg;

/**
 * Listener for the SendAssistantAction Button.
 * @author Alessandro
 *
 */
public class SendAssistantActionButtonListener extends MouseAdapter{
	
	private GameBoardWindow window;
	
	/**
	 * Constructor of the class. 
	 * @param window - the game Board Window
	 */
	public SendAssistantActionButtonListener(GameBoardWindow window) {
		this.window = window;
	}

	/**
	 * When clicked, it creates and notifies {@link SendAssistantActionButtonListener}.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		SendAssistantActionMsg msg = new SendAssistantActionMsg(window.getPlayer());
		window.getGuiHandler().notify(msg);
			
	}
	
}
