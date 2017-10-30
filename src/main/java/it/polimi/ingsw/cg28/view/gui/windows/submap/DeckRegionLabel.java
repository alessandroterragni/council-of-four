/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.windows.submap;


import javax.swing.ImageIcon;
import javax.swing.JLabel;

import it.polimi.ingsw.cg28.view.gui.listeners.DeckRegionLabelListener;
import it.polimi.ingsw.cg28.view.gui.windows.GameBoardWindow;

/**
 * Label for a region deck.
 * @author Alessandro
 *
 */
public class DeckRegionLabel extends JLabel {
	
	private static final long serialVersionUID = 7692738497817963044L;
	
	private int index;
	
	/**
	 * Constructor of the class.
	 * @param index - index of the card on the panel
	 * @param subMapPanel - {@link SubMapPanel}
	 * @param gameBoardWindow - {@link GameBoardWindow}
	 */
	public DeckRegionLabel(int index,SubMapPanel subMapPanel,GameBoardWindow gameBoardWindow) {
		
		this.index = index;
		this.addMouseListener(new DeckRegionLabelListener(gameBoardWindow));
		subMapPanel.add(this, new Integer(2));
		
		JLabel regionBackground = new JLabel();
		regionBackground.setIcon(new ImageIcon("src/img/submap/smallBusinesspermittile.png"));
		subMapPanel.add(regionBackground, new Integer(1));
		
		switch(index){
		
			case 0 : this.setIcon(new ImageIcon("src/img/regions/coastIcon.png"));
					 this.setBounds(24, 8, 52, 40);
			         regionBackground.setBounds(24, 8, 52, 40);
					 break;
			
			case 1 : this.setIcon(new ImageIcon("src/img/regions/plainIcon.png"));
					 this.setBounds(267, 8, 52, 40);
					 regionBackground.setBounds(267, 8, 52, 40);
					 break;
					 
			case 2 : this.setIcon(new ImageIcon("src/img/regions/mountainIcon.png"));
					 this.setBounds(537, 8, 52, 40);
					 regionBackground.setBounds(537, 8, 52, 40);
					 break;
			default:
					 break;
		}
		
	}

	/**
	 * @return the index of the card in the panel
	 */
	public int getIndex() {
		return index+1;
	}
	
	
}
