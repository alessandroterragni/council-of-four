/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import it.polimi.ingsw.cg28.view.gui.windows.GameBoardWindow;
import it.polimi.ingsw.cg28.view.messages.action.bazaar.BuySalableActionMsg;

/**
 * listener for the namePlate on the shelf of the bazaar
 * @author Alessandro
 *
 */
public class NamePlateLabelListener extends MouseAdapter {
	
	private GameBoardWindow window;
	private int position;
	
	/**
	 * constructor of the class
	 * @param window {@link GameBoardWindow}
	 * @param position index of the shelf of the bazaar
	 */
	public NamePlateLabelListener(GameBoardWindow window, int position) {
		this.position = position;
		this.window = window;
	}
	
	
	/**
	 * when clicked, it sets the corresponding code in the BuySalableActionMsg stored in the Gui Handler, 
	 * it notifies it and closes the communicationTextArea
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if(window.getGuiHandler().getActionMsg() instanceof BuySalableActionMsg){
				window.getGuiHandler().setCodes(Integer.toString(position),0);
				window.getGuiHandler().getActionMsg().setCodes(window.getGuiHandler().getCodes());
				window.getGuiHandler().notify(window.getGuiHandler().getActionMsg());
				window.getRulesPanel().getComunicationLabel().getComunicationTextArea().setText("");
				window.getRulesPanel().getComunicationLabel().setVisible(false);
		}
	}
}
