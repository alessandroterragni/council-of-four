/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import it.polimi.ingsw.cg28.view.gui.windows.GameBoardWindow;
import it.polimi.ingsw.cg28.view.gui.windows.submap.DeckRegionLabel;
import it.polimi.ingsw.cg28.view.messages.action.turn.ChangeTileActionMsg;

/**
 * listener for the DeckRegionLabel
 * @author Alessandro
 *
 */
public class DeckRegionLabelListener extends MouseAdapter {

	private GameBoardWindow window;
	
	/**
	 * constructor of the class
	 * @param window the game Board Window
	 */
	public DeckRegionLabelListener(GameBoardWindow window) {
		this.window = window;
	}
	
	/**
	 * when clicked, it fills and sends a ChangeTileActionMsg with the codes of the Deck clicked,
	 * then, it closes the comunicationLabel
	 */
	@Override
	public void mouseClicked(MouseEvent arg0) {
		
		if(window.getGuiHandler().getActionMsg() instanceof ChangeTileActionMsg)
		{
		DeckRegionLabel region = (DeckRegionLabel) arg0.getSource();
		window.getGuiHandler().setCodes(Integer.toString(region.getIndex()), 0);
		window.getGuiHandler().getActionMsg().setCodes(window.getGuiHandler().getCodes());
		window.getGuiHandler().notify(window.getGuiHandler().getActionMsg());
		window.getGuiHandler().setActionMsg(null);
		
		window.getRulesPanel().getComunicationLabel().getComunicationTextArea().setText("");
		window.getRulesPanel().getComunicationLabel().setVisible(false);
		}
	}

}
