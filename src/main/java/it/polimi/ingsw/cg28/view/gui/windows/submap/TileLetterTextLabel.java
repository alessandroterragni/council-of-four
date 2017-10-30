package it.polimi.ingsw.cg28.view.gui.windows.submap;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

/**
 * Label for the text in a tile.
 * @author Alessandro
 *
 */
public class TileLetterTextLabel extends JLabel{

	private static final long serialVersionUID = 5218288807367943976L;
	private BusinessPermitTileLabel tileLabel;
	
	/**
	 * Constructor of the class.
	 */
	public TileLetterTextLabel() {
		this.setFont(new Font("Charlemagne Std", Font.BOLD | Font.ITALIC, 13));
		this.setBackground(new Color(0, 0, 0, 0));
		this.setVisible(true);
		this.setText("");
	}

	/**
	 * Getter of the tileLabel associated.
	 * @return the tileLabel
	 */
	public BusinessPermitTileLabel getTileLabel() {
		return tileLabel;
	}

	/**
	 * Setter of the tileLabel associated.
	 * @param tileLabel - the tileLabel to set
	 */
	public void setTileLabel(BusinessPermitTileLabel tileLabel) {
		this.tileLabel = tileLabel;
	}
	
	
	
	
}
