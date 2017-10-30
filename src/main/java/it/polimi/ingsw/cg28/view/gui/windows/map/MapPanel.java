/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.windows.map;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import it.polimi.ingsw.cg28.tmodel.TTown;
import it.polimi.ingsw.cg28.view.gui.listeners.ColorMapListener;
import it.polimi.ingsw.cg28.view.gui.windows.GameBoardWindow;
import it.polimi.ingsw.cg28.view.gui.windows.bazaar.BaazarPanel;

/**
 * Map Panel that shows the mapLabel, the regionBonusLabels, the {@link BaazarPanel}, the {@link KingCrownLabel}.
 * @author Alessandro
 *
 */
public class MapPanel extends JLayeredPane {

	private static final long serialVersionUID = 263807301553029174L;
	private JLabel mapLabel;
	private RegionBonusLabel regionBonusLabel1;
	private RegionBonusLabel regionBonusLabel2;
	private RegionBonusLabel regionBonusLabel3;
	private BaazarPanel baazarPanel;
	private KingCrownLabel kingCrownLabel;
	
	private List<TTown> chosenTowns = new ArrayList<>();
	private List<TTown> staticTowns = new ArrayList<>();
	
	/**
	 * Constructor of the class.
	 * @param window - {@link GameBoardWindow}
	 */
	public MapPanel(GameBoardWindow window) {
		super();
		this.setBounds(0, 0, 746, 372);
		this.setLayout(null);
		
		mapLabel = new JLabel("");
		mapLabel.setIcon(new ImageIcon("src/img/board/mapDefault.jpg")); 
		mapLabel.setBounds(0, 0, 746, 372);
		
		regionBonusLabel1 = new RegionBonusLabel(1);
		regionBonusLabel2 = new RegionBonusLabel(2);
		regionBonusLabel3 = new RegionBonusLabel(3);
		
		
		mapLabel.addMouseListener(new ColorMapListener(window,"src/img/mappaColori.png","src/img/map.txt"));
		
		this.add(mapLabel, new Integer(1));
		this.add(regionBonusLabel1, new Integer(2));
		this.add(regionBonusLabel2, new Integer(2));
		this.add(regionBonusLabel3, new Integer(2));
	
		baazarPanel = new BaazarPanel(window);
		this.add(baazarPanel, new Integer(4));
		
		kingCrownLabel = new KingCrownLabel("src/img/board/king.png","src/img/king.txt");
		this.add(kingCrownLabel, new Integer(2));
		
			
	}
	
	/**
	 * Getter of the region Bonuses.
	 * @param index inex of the bonus on the map
	 * @return the corresponding region Bonus
	 */
	public RegionBonusLabel getRegionBonus(int index){
		switch(index){
		case 1: return regionBonusLabel1;
		case 2: return regionBonusLabel2;
		case 3: return regionBonusLabel3;
		default : return null;
		}
	}

	
	/**
	 * @return the towns set by a filledMsg
	 */
	public List<TTown> getChosenTowns() {
		return chosenTowns;
	}

	/**
	 * @param towns - the towns to set
	 */
	public void setChosenTowns(List<TTown> towns) {
		this.chosenTowns = towns;
	}
	
	/**
	 * @return the staticTowns all the towns of the map 
	 */
	public List<TTown> getStaticTowns() {
		return staticTowns;
	}

	/**
	 * @param staticTowns the staticTowns to set
	 */
	public void setStaticTowns(List<TTown> staticTowns) {
		this.staticTowns = staticTowns;
	}

	/**
	 * @return the shelfLabel
	 */
	public BaazarPanel getBaazarPanel() {
		return baazarPanel;
	}

	/**
	 * @return the kingCrownLabel
	 */
	public KingCrownLabel getKingCrownLabel() {
		return kingCrownLabel;
	}
	
	
}
