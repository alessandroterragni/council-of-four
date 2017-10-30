/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.listeners.actionbuttons;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import it.polimi.ingsw.cg28.view.gui.windows.GameBoardWindow;
import it.polimi.ingsw.cg28.view.messages.action.turn.HireAssistantActionMsg;


/**
 * Listener for the HireAssistantActionButton.
 * @author Alessandro
 *
 */
public class HireAssistantActionButtonListener extends MouseAdapter {

	private GameBoardWindow window;
	
	/**
	 * Constructor of the class. 
	 * @param window - the game Board Window
	 */
	public HireAssistantActionButtonListener(GameBoardWindow window) {
		this.window = window;
	}
	
	/**
	 * When the button is clicked, it creates and sends a HireAssistantActionMsg. 
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		HireAssistantActionMsg msg = new HireAssistantActionMsg(window.getPlayer());
		window.getGuiHandler().notify(msg);
		
	}
}
