/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.windows.kingpanel;

import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;


/**
 * King Panel: it shows all the bonus tile of the king ( reward tiles and king tile).
 * @author Alessandro
 *
 */
public class KingPanel extends JLayeredPane {
	
	private static final long serialVersionUID = -6732589371554633818L;

	private RewardTileLabel rewardTileIron;
	private RewardTileLabel rewardTileBronze;
	private RewardTileLabel rewardTileSilver;
	private RewardTileLabel rewardTileGold;
	private RewardTileLabel kingTile;
	private JLabel apuLabel;
	
	/**
	 * Constructor of the class.
	 */
	public KingPanel() {
		super();
		this.setBounds(511, 487, 235, 174);
		this.setVisible(true);
		this.setOpaque(false);
		this.setLayout(new FlowLayout());

		rewardTileIron = new RewardTileLabel(1);
		rewardTileBronze = new RewardTileLabel(2);
		rewardTileSilver = new RewardTileLabel(3);
		rewardTileGold = new RewardTileLabel(4);
		kingTile = new RewardTileLabel();
		
		apuLabel = new JLabel();
		apuLabel.setIcon(new ImageIcon("src/img/bazaar/apu.png"));
		apuLabel.setVisible(false);
		apuLabel.setBounds(0, 0, 235, 174);
		this.add(apuLabel, new Integer(2));
		
		this.add(rewardTileIron,new Integer(0));
		this.add(rewardTileBronze,new Integer(0));
		this.add(rewardTileSilver,new Integer(0));
		this.add(rewardTileGold,new Integer(0));
		this.add(kingTile,new Integer(0));
		

	}
	
	/**
	 * Getter of the reward tile.
	 * @param index number of the reward tile you want to get
	 * @return corresponding reward tile
	 */
	public RewardTileLabel getRewardTile(int index){
		switch (index) {
		case 1:
			return rewardTileIron;
		case 2:
			return rewardTileBronze;
		case 3:
			return rewardTileSilver;
		case 4:
			return rewardTileGold;
		default:
			return null;
		}
	}
	
	/**
	 * Getter of the king tile.
	 * @return the king tile
	 */
	public RewardTileLabel getKingTile(){
		return kingTile;
	}

	/**
	 * @return the apuLabel
	 */
	public JLabel getApuLabel() {
		return apuLabel;
	}
	
	
	
}
