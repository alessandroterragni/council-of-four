/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.windows.others;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JTextPane;

import it.polimi.ingsw.cg28.tmodel.TBusinessPermitTile;
import it.polimi.ingsw.cg28.tmodel.TPlayer;
import it.polimi.ingsw.cg28.view.gui.windows.submap.BusinessPermitTileLabel;

/**
 * Panel that shows the info of a player.
 * @author Alessandro
 *
 */
public class PlayerInfoPanel extends JPanel{
	
	private static final long serialVersionUID = -6360873920402498381L;

	/**
	 * Constructor of the class.
	 * @param player - {@link TPlayer} to show the info of
	 */
	public PlayerInfoPanel(TPlayer player) {
		super();
		this.setSize(695, 150);
		this.setVisible(true);
		this.setOpaque(false);
		this.setLayout(new FlowLayout(FlowLayout.LEADING));
		
		JTextPane playerInfo = new JTextPane();
		playerInfo.setSize(700,150);
		playerInfo.setBackground(new Color(0, 0, 0, 0));
		playerInfo.setFont(new Font("Deutsch Gothic", Font.BOLD, 20));
		playerInfo.setAlignmentX(CENTER_ALIGNMENT);
		playerInfo.setAlignmentY(CENTER_ALIGNMENT);
		playerInfo.setBackground(new Color(241, 212, 146));
		playerInfo.setEditable(false);
		playerInfo.setVisible(true);
		
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append(player.getName()+":   "+"\n");
		stringBuilder.append(player.getAssistants()+ "  Assistants\n");
		stringBuilder.append(player.getCoins() + "  Coins\n");
		stringBuilder.append(player.getEmpNumber() + "  Emporiums left\n");
		stringBuilder.append(player.getNobilityRank() + "  Nobility points\n");
		stringBuilder.append(player.getVictoryPoints() + "  Victory points\n");
		
		playerInfo.setText(stringBuilder.toString());
		this.add(playerInfo);
		
		for(TBusinessPermitTile tile : player.getPossessedTiles()){
			this.add(new BusinessPermitTileLabel(tile,0));
		}
	
	}
}
