/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.windows.others;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JRadioButton;

/**
 * Jradio Button with an associated councillor.
 * @author Alessandro
 *
 */
public class CouncillorJRadioButton extends JRadioButton {
	
	private static final long serialVersionUID = -444175597488253606L;
	Color color;
	int position;
	
	/**
	 * Constructor of the class. 
	 * @param color - color of the associated councillor
	 * @param position - index of the councillor in the {@link CouncillorChoiceWindow}
	 */
	public CouncillorJRadioButton(Color color,int position) {
		super();
		this.color = color;
		this.position = position;
		this.setOpaque(false);
		setText(getColorName(color));
	}

	/**
	 * @return the color of the councillor associated
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * Returns the name of the color associated and set the right icon depending on the color.
	 * @param c - color of the councillor
	 * @return color name of the associated councillor
	 */
	public String getColorName(Color c){
		CouncillorGetterImage getterImage = new CouncillorGetterImage();
		this.setIcon(new ImageIcon(getterImage.setCouncillorIcon(c)[1]));
		return getterImage.setCouncillorIcon(c)[0];
		
	}

	/**
	 * @return the index of the button in the {@link CouncillorChoiceWindow}
	 */
	public int getPosition() {
		return position+1;
	}
	
	
	
}
