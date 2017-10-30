package it.polimi.ingsw.cg28.view.gui.listeners.actionbuttons;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import it.polimi.ingsw.cg28.view.gui.windows.GameBoardWindow;
import it.polimi.ingsw.cg28.view.messages.action.turn.PermitTileActionMsg;
/**
 * Listener for the permitTileActionButton.
 * @author Alessandro
 *
 */
public class PermitTileActionButtonListener extends MouseAdapter {

	private GameBoardWindow window;
	
	/**
	 * Constructor of the class. 
	 * @param window - the game Board Window
	 */
	public PermitTileActionButtonListener(GameBoardWindow window) {
		this.window = window;
	}
	
	/**
	 * When the button is clicked, it creates and sends a PermitTileActionMsg. 
	 */
	@Override
	public void mouseClicked(MouseEvent arg0) {
		PermitTileActionMsg msg = new PermitTileActionMsg(window.getPlayer());
		window.getGuiHandler().notify(msg);	
	}
}
