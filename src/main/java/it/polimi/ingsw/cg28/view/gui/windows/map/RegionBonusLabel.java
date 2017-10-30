package it.polimi.ingsw.cg28.view.gui.windows.map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Label that shows a RegionBonus Image.
 * @author Alessandro
 *
 */
public class RegionBonusLabel extends JLabel {
	
	private static final long serialVersionUID = 2711555402079150476L;

	/**
	 * Constructor of the class.
	 * @param position of the label you want to build
	 * 1 - Hills
	 * 2 - Sea
	 * 3 - Mountains
	 */
	public RegionBonusLabel(int position) {
			super();
			this.setVisible(false);
			
			switch(position){
			case 1 : this.setBounds(174, 335, 46, 27);
					 this.setIcon(new ImageIcon("src/img/bonuses/regionBonuses/bonusHills.png"));
				break;
			case 2 : this.setBounds(418, 335, 46, 27);
					 this.setIcon(new ImageIcon("src/img/bonuses/regionBonuses/bonusSea.png"));
				break;
			case 3 : this.setBounds(689, 335, 46, 27);
					 this.setIcon(new ImageIcon("src/img/bonuses/regionBonuses/bonusMountains.png"));
				break;
			default:
				break;
			}
	}
}
