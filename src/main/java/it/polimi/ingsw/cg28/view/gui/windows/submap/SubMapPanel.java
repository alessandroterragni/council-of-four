/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.windows.submap;

import javax.swing.JLayeredPane;
import it.polimi.ingsw.cg28.view.gui.windows.GameBoardWindow;

/**
 * Panel hosted under the map, it shows the subMapLabel, the nobilityTrackLabel or the bazaarStallLabel depending on the moment of the game
 * @author Alessandro
 *
 */
public class SubMapPanel extends JLayeredPane{
	
	private static final long serialVersionUID = 3595057195411804633L;
	
	private SubMapLabel subMapLabel;
	private NobilityTrackLabel nobilityTrackLabel;
	private SubBazaarLabel subBazaarLabel;
	
	/**
	 * Constructor of the class.
	 * @param gameBoardWindow {@link GameBoardWindow}
	 */
	public SubMapPanel(GameBoardWindow gameBoardWindow) {
		super();
		this.setBounds(0, 372, 746, 111); 
		this.setLayout(null);
		this.setOpaque(true);
		
		subMapLabel = new SubMapLabel(gameBoardWindow,this);
		this.add(subMapLabel,new Integer(0));
		
		nobilityTrackLabel = new NobilityTrackLabel("src/img/nobilityTrack.jpg");
		this.add(nobilityTrackLabel, new Integer(5));
		
		subBazaarLabel = new SubBazaarLabel("src/img/bazaar/subbazaar.png");
		this.add(subBazaarLabel, new Integer(6));
		
	}

	/**
	 * getter of the subMapLabel
	 * @return the subMapLabel
	 */
	public SubMapLabel getSubMapLabel() {
		return subMapLabel;
	}

	/**
	 * getter of the NobilityTrackLabel
	 * @return the nobilityTrackLabel
	 */
	public NobilityTrackLabel getNobilityTrackLabel() {
		return nobilityTrackLabel;
	}

	/**
	 * getter of the SubazaarLabel
	 * @return the subBazaarLabel
	 */
	public SubBazaarLabel getSubBazaarLabel() {
		return subBazaarLabel;
	}	
	
}
