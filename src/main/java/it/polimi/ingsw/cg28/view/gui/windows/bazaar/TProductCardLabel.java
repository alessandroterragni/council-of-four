/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.windows.bazaar;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import it.polimi.ingsw.cg28.view.gui.windows.others.PoliticCardColorGetter;

/**
 * Label that represents a salable Politic Card on a {@link SingleShelfPanel}.
 * @author Alessandro
 *
 */
public class TProductCardLabel extends JLabel{

	private static final long serialVersionUID = -6759142751586825294L;

	/**
	 * Constructor of the class.
	 * @param color Color of the card on sale 
	 */
	public TProductCardLabel(Color color) {
		super();
		PoliticCardColorGetter cardColorGetter = new PoliticCardColorGetter();
		this.setIcon(new ImageIcon(cardColorGetter.getColorName(color)[0]));
		
	}

} 
