/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.windows.rules;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JPanel;

/**
 * Panel that shows the politic cards owned by the player.
 * @author Alessandro
 *
 */
public class MyPoliticCardsPanel extends JPanel{

	private static final long serialVersionUID = -2139381210318177469L;

	/**
	 * Constructor of the class.
	 */
	public MyPoliticCardsPanel() {
		super();
		this.setBounds(0, 0, 182, 174);   
		this.setLayout(new FlowLayout());
		this.setVisible(false);
		this.setOpaque(true);
		this.setBackground(new Color(244, 237, 221));
	}
}
