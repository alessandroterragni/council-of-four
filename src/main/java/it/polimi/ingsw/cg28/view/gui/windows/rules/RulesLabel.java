/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.windows.rules;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import it.polimi.ingsw.cg28.view.gui.windows.GameRulesWindow;

/**
 * Label that host the image of the quick rules.
 * @author Alessandro
 *
 */
public class RulesLabel extends JLabel {

	private static final long serialVersionUID = -8284120210351526188L;

	/**
	 * Constructor of the class.
	 */
	public RulesLabel() {
		super();
		this.setIcon(new ImageIcon("src/img/rules/cartoncino.png"));
		this.setBounds(6, 0, 174, 174);
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				GameRulesWindow gameRulesWindow = new GameRulesWindow();
				gameRulesWindow.setVisible(true);
			}
		});
	}
}
