/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.windows.submap;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Label that host the image of the bazaar stall ( an image taht replace the submap label during the bazaar turn)
 * @author Alessandro
 *
 */
public class SubBazaarLabel extends JLabel {

	private static final long serialVersionUID = 7468360699492530133L;

	/**
	 * Constructor of the class.  
	 * @param bazaarStallImgPath - the path of the image of the bazaar stall
	 */
	public SubBazaarLabel(String bazaarStallImgPath) {
		this.setIcon(new ImageIcon(bazaarStallImgPath));
		this.setBounds(0, 0, 746, 111);
		this.setVisible(false);
	}
}
