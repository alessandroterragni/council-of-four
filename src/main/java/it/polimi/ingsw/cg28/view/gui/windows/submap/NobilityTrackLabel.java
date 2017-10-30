/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.windows.submap;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Label that host the nobility track image.
 * @author Alessandro
 *
 */
public class NobilityTrackLabel extends JLabel {

	private static final long serialVersionUID = 966712276857206306L;

	/**
	 * Constructor of the class.
	 * @param nobilityImgPath - the path of the image of the nobility track
	 */
	public NobilityTrackLabel(String nobilityImgPath) {
		this.setSize(746, 111);
		this.setIcon(new ImageIcon(nobilityImgPath));
		this.setVisible(false);
	}
}
