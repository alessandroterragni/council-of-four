/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.windows.submap;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import it.polimi.ingsw.cg28.tmodel.TBusinessPermitTile;
import it.polimi.ingsw.cg28.view.gui.listeners.BusinessPermitTileListener;
import it.polimi.ingsw.cg28.view.gui.windows.GameBoardWindow;

/**
 * Label for a business Tile Label with letters and/or bonus, depending on the tile dimensions.
 * @author Alessandro
 *
 */
public class BusinessPermitTileLabel extends JLabel{

	private static final long serialVersionUID = 1498077125485490733L;
	
	private TileLetterTextLabel letteraTextLabel;
	private int position;
	private String[] bonuses;
	private String[] letters;
	
	/**
	 * Small business permit tile fixed on the board, the text will be added later calling setCardText,
	 * @param position - index of the card on the panel
	 * @param subMapPanel - {@link SubMapLabel}
	 * @param gameBoardWindow - {@link GameBoardWindow}
	 */
	public BusinessPermitTileLabel(int position, SubMapPanel subMapPanel, GameBoardWindow gameBoardWindow) {
		
		this.setIcon(new ImageIcon("src/img/submap/smallBusinesspermittile.png"));
		this.position = position;
		subMapPanel.add(this,new Integer(1));
		
		letteraTextLabel = new TileLetterTextLabel();
		letteraTextLabel.addMouseListener(new BusinessPermitTileListener(gameBoardWindow));
		letteraTextLabel.setTileLabel(this);
		letteraTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
	 	letteraTextLabel.setVerticalAlignment(SwingConstants.CENTER);
		subMapPanel.add(letteraTextLabel,new Integer(2));
		
		switch(position){
		case 1: this.setBounds(85, 8, 52, 40);
				letteraTextLabel.setBounds(85, 8, 52, 40);
				break;
		case 2: this.setBounds(145, 8, 52, 40);
				letteraTextLabel.setBounds(145, 8, 52, 40);
				break;
		case 3: this.setBounds(327, 8, 52, 40);
				letteraTextLabel.setBounds(327, 8, 52, 40);
				break;
		case 4: this.setBounds(387, 8, 52, 40);
				letteraTextLabel.setBounds(387, 8, 52, 40);
				break;
		case 5: this.setBounds(597, 8, 52, 40);
				letteraTextLabel.setBounds(597, 8, 52, 40);	
				break;
		case 6: this.setBounds(657, 8, 52, 40);
				letteraTextLabel.setBounds(657, 8, 52, 40);
				break;
		default: 
			break;
		}
	}

	/**
	 * Small businessPermitTile.
	 * Constructor when you already have a tBusinessPermitTile.
	 * @param tBusinessPermitTile tile you want to display
	 */
	public BusinessPermitTileLabel(TBusinessPermitTile tBusinessPermitTile, int position) {
		
		this.position = position;
		
		letteraTextLabel = new TileLetterTextLabel();
		letteraTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
	 	letteraTextLabel.setVerticalAlignment(SwingConstants.CENTER);
		
		this.setIcon(new ImageIcon("src/img/submap/smallBusinesspermittile.png"));
		this.setBounds(0, 0, 52, 40);
		letteraTextLabel.setBounds(0,0,52,40);	
		setCardText(tBusinessPermitTile.getLetterCodes());
		this.add(letteraTextLabel);
			
	}
	
	/**
	 * Big BusinessPermitTile that will be filled with both text and bonus images.
	 */
	public BusinessPermitTileLabel() {
		
		this.setIcon(new ImageIcon("src/img/submap/zoomedBusinesspermittile.png"));
	 	this.setBounds(0, 0, 182, 174);
	 	letteraTextLabel = new TileLetterTextLabel();
	 	letteraTextLabel.setBounds(6, 12, 174, 53);//6, 0, 174, 75
	 	letteraTextLabel.setFont(new Font("Charlemagne Std", Font.BOLD | Font.ITALIC, 34));//45
	 	letteraTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
	 	letteraTextLabel.setVerticalAlignment(SwingConstants.CENTER);
	 	this.add(letteraTextLabel);
	 	
	}
	
	/**
	 * It sets the text on the card.
	 * @param letterCodes - the text you want to make visible on the tile
	 */
	public void setCardText(String[] letterCodes){
		this.letters = letterCodes;
		StringBuilder startLetters = new StringBuilder();
		int length = letterCodes.length;
		
		for(int i =0;i<length;i++){
			startLetters.append(letterCodes[i].substring(0, 1)+" ");
		}
		letteraTextLabel.setText(startLetters.toString());
	}
	
	/**
	 * Getter of the textLabel of the tile.
	 * @return  textLabel of the tile
	 */
	public TileLetterTextLabel getTileLetterTextLabel() {
		return letteraTextLabel;
	}

	/**
	 * @return the position index of the card on the panel
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * Getter of the string bonuses attached on the tile.
	 * @return the bonuses
	 */
	public String[] getBonuses() {
		return bonuses;
	}

	/**
	 * Setter of the bonus on the tile.
	 * @param bonuses the bonuses to set
	 */
	public void setBonuses(String[] bonuses) {
		this.bonuses = bonuses;
	}

	/**
	 * Getter of the text on the tile.
	 * @return the letters
	 */
	public String[] getLetters() {
		return letters;
	}
	
	
	
}
