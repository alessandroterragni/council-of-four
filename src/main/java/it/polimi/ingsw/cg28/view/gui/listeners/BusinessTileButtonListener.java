/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import it.polimi.ingsw.cg28.view.gui.windows.GameBoardWindow;
import it.polimi.ingsw.cg28.view.gui.windows.others.BusinessTileChoiceWindow;
import it.polimi.ingsw.cg28.view.messages.action.bazaar.SellPermitTilesActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.bonus.ReusePermitBonusActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.EmporiumTileActionMsg;

/**
 * listener for the businessTileButton
 * @author Alessandro
 *
 */
public class BusinessTileButtonListener extends MouseAdapter {

	private GameBoardWindow window;
	private int position;
	private BusinessTileChoiceWindow choiceWindow;
	private boolean multipleChoice;
	
	/**
	 * constructor of the class
	 * @param index index of the botton in the window
	 * @param window {@link GameBoardWindow}
	 * @param choiceWindow {@link BusinessTileChoiceWindow}
	 * @param multipleChoice if true, the player can choose multiple bottons, if false, he can't 
	 */
	public BusinessTileButtonListener(int index,GameBoardWindow window, BusinessTileChoiceWindow choiceWindow,boolean multipleChoice) {
		this.position = index;
		this.window = window;
		this.choiceWindow = choiceWindow;
		this.multipleChoice = multipleChoice;
	}
	
	/**
	 * it sets the code of the tile ( or tiles, if  multipleChoice is true) clicked in the actionMsg of the GUiHandler, 
	 * depending on type of the msg stored,
	 * then, it opens the communicationLabel
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		
		if(window.getGuiHandler().getActionMsg() instanceof EmporiumTileActionMsg 
				&& !multipleChoice){
			window.getGuiHandler().setCodes(Integer.toString(position+1), 0);
			choiceWindow.dispose();
	
			window.getRulesPanel().getComunicationLabel().setVisible(true);
			window.getRulesPanel().getComunicationLabel().getComunicationTextArea().setText("");
			window.getRulesPanel().getComunicationLabel().getComunicationTextArea().setText("Click the city where you want to build the emporium");
		}
		
		if(window.getGuiHandler().getActionMsg() instanceof SellPermitTilesActionMsg 
				|| window.getGuiHandler().getActionMsg() instanceof ReusePermitBonusActionMsg
					&& multipleChoice) {
				window.getGuiHandler().getCodeBuffer().append(Integer.toString(position+1)+" "); 
		}
		
	}
}
