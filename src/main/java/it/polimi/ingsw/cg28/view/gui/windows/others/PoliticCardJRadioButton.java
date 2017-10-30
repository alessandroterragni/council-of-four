/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.windows.others;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JRadioButton;

/**
 * Jradio Button with an associated Politic Card. 
 * @author Alessandro
 *
 */
public class PoliticCardJRadioButton extends JRadioButton{

	private static final long serialVersionUID = -6892178112041701808L;
	
	Color color;
	int position;
	
	/**
	 * Constructor of the class. 
	 * @param color - color of the associated politic card
	 * @param position - index of the politic card in the {@link CouncillorChoiceWindow}
	 */
	 public PoliticCardJRadioButton(Color color,int position) {
		 	super();
			this.color = color;
			this.position = position;
			this.setOpaque(false);
			PoliticCardColorGetter cardColorGetter = new PoliticCardColorGetter();
			setIcon(new ImageIcon(cardColorGetter.getColorName(color)[0]));
			setText(cardColorGetter.getColorName(color)[1]);
	}
 
     /**
	 * @return the color of the politic card associated
	 */
	public Color getColor() {
		return color;
	}
	

	/**
	 * @return the position index of the card associated
	 */
	public int getPosition() {
		return position+1;
	}
		
}
