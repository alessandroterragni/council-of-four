/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.windows.rules;

import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import it.polimi.ingsw.cg28.tmodel.TBonus;
import it.polimi.ingsw.cg28.tmodel.TTown;

/**
 * Panel that shows the information of Town clicked.
 * @author Alessandro
 *
 */
public class TownInformationPanel extends JLayeredPane {
	
	private static final long serialVersionUID = 3055798150841105706L;
	
	private JLabel infoLabel;
	private JLabel bonusesLabel;
	
	/**
	 * Constructor of the class.
	 */
	public TownInformationPanel() {
		this.setBounds(0, 0, 180, 174);
		this.setLayout(null);
		this.setVisible(false);
		
		JLabel background = new JLabel(new ImageIcon("src/img/townBackground.png"));
		background.setSize(180,174);
		background.setVisible(true);
		this.add(background, new Integer(0));
		
		infoLabel = new JLabel();
		infoLabel.setVisible(true);
		infoLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		infoLabel.setBounds(10, 0, 180, 93);
		this.add(infoLabel, new Integer(1));
		
		bonusesLabel = new JLabel();
		bonusesLabel.setBounds(5, 94, 180, 80);
		bonusesLabel.setLayout(new FlowLayout());
		this.add(bonusesLabel, new Integer(1));
	}
	
	/**
	 * Fills the info of the townInformationLabel.
	 * @param town associated with the panel
	 */
	public void fillInfo(TTown town){
		fillInfoLabel(town);
		fillBonusLabel(town.getBonus());
	}
	
	/**
	 * Fills the infoTextLabel of the townInformationLabel. 
	 * @param town
	 */
	public void fillInfoLabel(TTown town){
		
		StringBuilder buffer = new StringBuilder();
		buffer.append("<html><font size=+ 4><b>"+ town.getName() + "<b></font>");
		buffer.append("<br>");
		buffer.append("<b>Emporiums:<b>");
		buffer.append("<br>");
		for(int i=0;i<town.getEmporiums().length;i++){
			buffer.append(town.getEmporiums()[i].getName());
			buffer.append("<br>");
		}
		buffer.append("</html>");
		infoLabel.setText(buffer.toString());
	}
	
	/**
	 * Fills the bonus info of the townInformationLabel.
	 * @param bonuses
	 */
	public void fillBonusLabel(TBonus bonuses){
		bonusesLabel.removeAll();
		bonusesLabel.repaint();
		
		if(bonuses!=null){
			for(int i=0;i<bonuses.getBonusCode().length;i++){
				bonusesLabel.add(new BonusIconLabel(bonuses.getBonusCode()[i]));
			}
		}
	}
	
}
