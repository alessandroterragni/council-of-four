package it.polimi.ingsw.cg28.view.gui.windows.submap;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import it.polimi.ingsw.cg28.view.gui.windows.GameBoardWindow;

/**
 * Label hosted under the map, it contains the balcony labels, the tile labels and the DeckRegionsLabel.
 * @author Alessandro
 *
 */
public class SubMapLabel extends JLabel {
	
	private static final long serialVersionUID = -3664496770396683010L;
	private BalconyLabel balcony1Label;
	private BalconyLabel balcony2Label;
	private BalconyLabel balcony3Label;
	private BalconyLabel balconyKingLabel;
	
	private BusinessPermitTileLabel tile1;
	private BusinessPermitTileLabel tile2;
	private BusinessPermitTileLabel tile3;
	private BusinessPermitTileLabel tile4;
	private BusinessPermitTileLabel tile5;
	private BusinessPermitTileLabel tile6;
	
	
	public SubMapLabel(GameBoardWindow gameBoardWindow, SubMapPanel subMapPanel) {
		super();
		
		this.setIcon(new ImageIcon("src/img/submap/subMap.jpg"));
		this.setBounds(0, 0, 746, 111);
		
		balcony1Label = new BalconyLabel(1,subMapPanel,gameBoardWindow);
		balcony2Label = new BalconyLabel(2,subMapPanel,gameBoardWindow);
		balcony3Label = new BalconyLabel(3,subMapPanel,gameBoardWindow);
		balconyKingLabel = new BalconyLabel(4,subMapPanel,gameBoardWindow);
		
		tile1 = new BusinessPermitTileLabel(1,subMapPanel,gameBoardWindow);
		tile2 = new BusinessPermitTileLabel(2,subMapPanel,gameBoardWindow);
		tile3 = new BusinessPermitTileLabel(3,subMapPanel,gameBoardWindow);
		tile4 = new BusinessPermitTileLabel(4,subMapPanel,gameBoardWindow);
		tile5 = new BusinessPermitTileLabel(5,subMapPanel,gameBoardWindow);
		tile6 = new BusinessPermitTileLabel(6,subMapPanel,gameBoardWindow);
		
		DeckRegionLabel regionLabel1 = new DeckRegionLabel(0,subMapPanel,gameBoardWindow);
		DeckRegionLabel regionLabel2 = new DeckRegionLabel(1,subMapPanel,gameBoardWindow);
		DeckRegionLabel regionLabel3 = new DeckRegionLabel(2,subMapPanel,gameBoardWindow);
		regionLabel1.setVisible(true);
		regionLabel2.setVisible(true);
		regionLabel3.setVisible(true);
		
	}
	
	/**
	 * @return the i-balconyPanel
	 */
	public BalconyLabel getBalconyLabel(int i) {
		if(i==1) return balcony1Label;
		if(i==2) return balcony2Label;
		if(i==3) return balcony3Label;
		if(i==4) return balconyKingLabel;
		return null;
	}
	
	
	public BusinessPermitTileLabel getBusinessPermitTileLabel(int i){
		if(i==0) return tile1;
		if(i==1) return tile2;
		if(i==2) return tile3;
		if(i==3) return tile4;
		if(i==4) return tile5;
		if(i==5) return tile6;
		return null;
	}
	
	
}
