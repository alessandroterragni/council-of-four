/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import it.polimi.ingsw.cg28.view.gui.windows.GameBoardWindow;
import it.polimi.ingsw.cg28.view.gui.windows.others.PoliticCardJRadioButton;

/**
 * listener for the politic card JButton
 * @author Alessandro
 *
 */
public class PoliticCardJRadioButtonListener implements ActionListener{

	private GameBoardWindow window;
	
	/**
	 * constructor of the class
	 * @param window {@link GameBoardWindow}
	 */
	public PoliticCardJRadioButtonListener(GameBoardWindow window) {
		this.window = window;
	}
	
	/**
	 * when clicked, it a adds the code of the card to the code Buffer of the gui handler,
	 * then, it disable the button clicked
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
				PoliticCardJRadioButton button =   (PoliticCardJRadioButton) e.getSource();
				window.getGuiHandler().getCodeBuffer().append(button.getPosition()+" ");
				button.setEnabled(false);
			}

}
