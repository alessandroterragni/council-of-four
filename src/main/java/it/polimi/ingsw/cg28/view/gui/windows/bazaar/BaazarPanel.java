/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.windows.bazaar;


import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import it.polimi.ingsw.cg28.view.gui.windows.GameBoardWindow;

/**
 * Panel for the bazaar.
 * @author Alessandro
 *
 */
public class BaazarPanel extends JPanel{ 
	
	private static final long serialVersionUID = -2403847136198819828L;
	
	private GameBoardWindow window;
	private JPanel shelvesPanel;

	/**
	 * Constructor of the class.
	 * @param window {@link GameBoardWindow}
	 */
	public BaazarPanel(GameBoardWindow window) {
		super();
		this.setBounds(0, 0, 746, 372);
		this.setLayout(null);
		this.window = window;
		this.setVisible(false);		
	}
	
	/**
	 * Creates and shows all the graphic components of the bazaar.
	 */
	public void startBaazar(){
		this.setVisible(true);
		
		window.getActionPanel().makeSellButtonsVisible(true);
		window.getSubMapPanel().getSubBazaarLabel().setVisible(true);
		window.getKingPanel().getApuLabel().setVisible(true);
		
		shelvesPanel = new JPanel();
		shelvesPanel.setBackground(new Color(237,164,0));
		shelvesPanel.setBounds(0, 0, 746, 372);
		shelvesPanel.setLayout(new BoxLayout(shelvesPanel, BoxLayout.Y_AXIS));
		
		
		JScrollPane jScrollPane = new JScrollPane();
		jScrollPane.setBounds(0, 0, 746, 372);
		jScrollPane.setViewportView(shelvesPanel);
		this.add(jScrollPane);
		
	}

	
	/**
	 * @return the shelvesPane
	 */
	public JPanel getShelvesPanel() {
		return shelvesPanel;
	}
	
	
	
}
