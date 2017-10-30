/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.windows.rules;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import it.polimi.ingsw.cg28.view.gui.windows.submap.BusinessPermitTileLabel;

/**
 * Panel that shows a Zoomed Permit Tile with all the bonuses.
 * @author Alessandro
 *
 */
public class ZoomedBusinessPermitTilePanel extends JLayeredPane{
	
	private static final long serialVersionUID = -224082557623239390L;
	
	private BusinessPermitTileLabel businessPermitTileLabel;
	private JLabel bonusLabel;
	
	/**
	 * Constructor of the class.
	 */
	public ZoomedBusinessPermitTilePanel() {
		super();
		this.setBounds(0, 0, 182, 174);
		this.setVisible(false);
		this.setLayout(null);
		
		businessPermitTileLabel = new BusinessPermitTileLabel();
		this.add(businessPermitTileLabel, new Integer(0));
		
		bonusLabel = new JLabel();
		bonusLabel.setVisible(true);
		bonusLabel.setBounds(6, 51, 174, 123); //6, 80, 174, 94
		bonusLabel.setLayout(new FlowLayout());
		
		this.add(bonusLabel, new Integer(2));
	}
	
	/**
	 * Add the bonus icons to the zoomedPermitTileBonus.
	 * @param bonuses to add to the tileLabel
	 */
	public void addBonusIcon(String[] bonuses){
		for(int i =0;i<bonuses.length;i++){
			bonusLabel.add(new BonusIconLabel(bonuses[i]));
		}
		
	}

	/**
	 * @return the businessPermitTileLabel
	 */
	public BusinessPermitTileLabel getBusinessPermitTileLabel() {
		return businessPermitTileLabel;
	}

	/**
	 * @return the bonusPanel
	 */
	public JLabel getBonusPanel() {
		return bonusLabel;
	}
	
}
