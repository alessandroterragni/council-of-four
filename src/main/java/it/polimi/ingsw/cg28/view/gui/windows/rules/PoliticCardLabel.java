/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.windows.rules;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Label for a small Politic Card image.
 * @author Alessandro
 *
 */
public class PoliticCardLabel extends JLabel{
	
	private static final long serialVersionUID = 8121612790319055554L;

	/**
	 * Constructor of the class.
	 * @param color - of the politic card you want to show
	 */
	public PoliticCardLabel(Color color) {
		this.setBounds(0, 0, 42, 42);
		setIcon(color);
		
	}
	
	/**
	 * It sets the image of the label.
	 * @param c - color of the card you want to set the image of
	 */
	public void setIcon(Color c){

		if(c == (null)){
			this.setIcon(new ImageIcon("src/img/politicCards/allcolorsSmall.png"));
		}
		else
		{
			int rgb = c.getRGB();
			switch(rgb) {
				case -1:
					this.setIcon(new ImageIcon("src/img/politicCards/whiteSmall.png"));
					break;
				case -20561:
					this.setIcon(new ImageIcon("src/img/politicCards/pinkSmall.png"));
					break;
				case -14336:
					this.setIcon(new ImageIcon("src/img/politicCards/orangeSmall.png"));
					break;
				case -16777216:
					this.setIcon(new ImageIcon("src/img/politicCards/blackSmall.png"));
					break;
				case -65281:
					this.setIcon(new ImageIcon("src/img/politicCards/violetSmall.png"));
					break;
				case -16776961:
					this.setIcon(new ImageIcon("src/img/politicCards/blueSmall.png"));
					break;
				default:
					break;
			}
		}
	}

}
