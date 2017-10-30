/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.windows.rules;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JPanel;

/**
 * Panel that shows the business Permit Tiles owned by the player.
 * @author Alessandro
 *
 */
public class MyBusinessPermitTilesPanel extends JPanel{

	private static final long serialVersionUID = -3419933588341187424L;

	/**
	 * Constructor of the class.
	 */
	public MyBusinessPermitTilesPanel() {
		super();
		this.setBounds(0, 0, 182, 174);   
		this.setLayout(new FlowLayout());
		this.setVisible(false);
		this.setOpaque(true);
		this.setBackground(new Color(244, 237, 221));
	}
}
