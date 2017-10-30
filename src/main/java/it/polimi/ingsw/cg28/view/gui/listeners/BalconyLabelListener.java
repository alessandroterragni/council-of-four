/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import it.polimi.ingsw.cg28.view.gui.windows.GameBoardWindow;
import it.polimi.ingsw.cg28.view.gui.windows.submap.BalconyLabel;
import it.polimi.ingsw.cg28.view.messages.action.turn.ElectionActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.SendAssistantActionMsg;

/**
 * listener for the balcony
 * @author Alessandro
 *
 */
public class BalconyLabelListener extends MouseAdapter {

	private GameBoardWindow window;
	
	/**
	 * constructor of the class
	 * @param window the GameWindow
	 */
	public BalconyLabelListener(GameBoardWindow window) {
		this.window = window;
	}
	
	/**
	 * when clicked, it fills and sends an ElectionActionMsg or a SendAssistantAction, depending on the 
	 * action button previously pressed
	 * in addition , it also closes the communication Label 
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		
		if(window.getGuiHandler().getActionMsg() instanceof ElectionActionMsg ||
				window.getGuiHandler().getActionMsg() instanceof SendAssistantActionMsg)
		{
		BalconyLabel balcony = (BalconyLabel) e.getSource();
		window.getGuiHandler().setCodes(Integer.toString(balcony.getPosition()), 0);
		window.getGuiHandler().getActionMsg().setCodes(window.getGuiHandler().getCodes());
		window.getGuiHandler().notify(window.getGuiHandler().getActionMsg());
		
		window.getRulesPanel().getComunicationLabel().getComunicationTextArea().setText("");
		window.getRulesPanel().getComunicationLabel().setVisible(false);
		}
		
	}

}
