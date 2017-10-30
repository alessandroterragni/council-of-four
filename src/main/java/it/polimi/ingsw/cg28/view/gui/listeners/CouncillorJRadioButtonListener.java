/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import it.polimi.ingsw.cg28.view.gui.windows.GameBoardWindow;
import it.polimi.ingsw.cg28.view.gui.windows.others.CouncillorChoiceWindow;
import it.polimi.ingsw.cg28.view.gui.windows.others.CouncillorJRadioButton;

/**
 * listener for a councillor JRadioButton
 * @author Alessandro
 *
 */
public class CouncillorJRadioButtonListener implements ActionListener {

	private GameBoardWindow window;
	private CouncillorChoiceWindow concWindow;
	
	/**
	 * constructor of the class
	 * @param window the gameBoardWindow
	 * @param concWindow the councillorChoiceWindow
	 */
	public CouncillorJRadioButtonListener(GameBoardWindow window,CouncillorChoiceWindow concWindow) {
		this.window = window;
		this.concWindow = concWindow;
	}
	
	/**
	 * when the Jbutton is clicked, it set the code of the councillor in the GuiHandler Codes, 
	 * then, it closes the councillorChoiceWindow and opens the communicationTextArea
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		CouncillorJRadioButton button =  (CouncillorJRadioButton) e.getSource();
		window.getGuiHandler().setCodes(Integer.toString(button.getPosition()), 1);
		concWindow.dispose();
		window.getRulesPanel().getComunicationLabel().setVisible(true);
		window.getRulesPanel().getComunicationLabel().getComunicationTextArea().setText("");
		window.getRulesPanel().getComunicationLabel().getComunicationTextArea().setText("Click on the balcony you want  to elect the councillor in");
	}

}
