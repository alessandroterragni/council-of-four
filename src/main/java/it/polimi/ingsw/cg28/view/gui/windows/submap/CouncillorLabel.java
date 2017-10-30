/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.windows.submap;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import it.polimi.ingsw.cg28.view.gui.windows.others.CouncillorGetterImage;

/**
 * Label that display a councillor.
 * @author Alessandro
 *
 */
public class CouncillorLabel extends JLabel {
	
	private static final long serialVersionUID = 6741743779079262246L;

	/**
	 * Constructor of the class, it creates an empty label that will be filled with an image using setCouncillorColor method.
	 */
	public CouncillorLabel() {
		super();
		this.setIcon(null);
	}

	/**
	 * It fills the label with the right image depending on the color of the councillor.
	 * @param icon - the icon to set
	 */	
	public void setCouncillorIcon(Color color){
		CouncillorGetterImage getterImage = new CouncillorGetterImage();
		this.setIcon(new ImageIcon(getterImage.setCouncillorIcon(color)[1]));
		this.setVisible(true);
	}
	
	
}
