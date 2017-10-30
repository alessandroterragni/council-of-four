package it.polimi.ingsw.cg28.view.gui.windows.kingpanel;


import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Label that shows the image of a reward tile or a king tile.
 * @author Alessandro
 *
 */
public class RewardTileLabel extends JLabel {
	
	private static final long serialVersionUID = -2925559370223004290L;

	/**
	 * Constructor of the class.
	 * @param index the index of the tile you want to create 
	 * 1-> iron
	 * 2-> bronze
	 * 3-> silver
	 * 4-> gold
	 */
	public RewardTileLabel(int index) {
		super();
		this.setVisible(false);
		
		switch (index) {
		case 1:
			this.setIcon(new ImageIcon("src/img/bonuses/rewards/ironReward.png"));
			break;
		case 2:
			this.setIcon(new ImageIcon("src/img/bonuses/rewards/bronzeReward.png"));
			break;
		case 3:
			this.setIcon(new ImageIcon("src/img/bonuses/rewards/silverReward.png"));
			break;
		case 4:
			this.setIcon(new ImageIcon("src/img/bonuses/rewards/goldReward.png"));
			break;
		default:
			break;
		}
	}
	
	/**
	 * Constructor for the kingRewardTile that will be filled with an image from the GuiHandler.  
	 */
	public RewardTileLabel() {
		super();
		this.setVisible(true);
	}
}
