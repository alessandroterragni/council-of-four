/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.windows.rules;

import javax.swing.JLayeredPane;

/**
 * Panel that host all the panel and label to show temporary label or panel.
 * @author Alessandro
 *
 */

public class RulesPanel extends JLayeredPane{
 
	private static final long serialVersionUID = 9195462256699568082L;
	private RulesLabel rulesLabel;
	private ComunicationLabel comunicationLabel;
	private MyPoliticCardsPanel myPoliticCardsPanel;
	private MyBusinessPermitTilesPanel myBusinessPermitTilesPanel;
	private ZoomedBusinessPermitTilePanel zoomedBusinessPermitTilePanel;
	private TownInformationPanel townInformationPanel;
	
	/**
	 * Constructor of the class.
	 */
	public RulesPanel() {
		this.setBounds(324, 487, 180, 174);
		this.setLayout(null);
		rulesLabel = new RulesLabel();
		comunicationLabel = new ComunicationLabel();
		myPoliticCardsPanel = new MyPoliticCardsPanel();
		myBusinessPermitTilesPanel = new MyBusinessPermitTilesPanel();
		zoomedBusinessPermitTilePanel = new ZoomedBusinessPermitTilePanel();
		townInformationPanel = new TownInformationPanel();
		this.add(rulesLabel, new Integer(0));
		this.add(comunicationLabel, new Integer(1));
		this.add(myPoliticCardsPanel, new Integer(2));
		this.add(myBusinessPermitTilesPanel, new Integer(2));
		this.add(zoomedBusinessPermitTilePanel, new Integer(2));
		this.add(townInformationPanel, new Integer(3));
	}

	/**
	 * @return the rulesLabel
	 */
	public RulesLabel getRulesLabel() {
		return rulesLabel;
	}

	/**
	 * @return the comunicationLabel
	 */
	public ComunicationLabel getComunicationLabel() {
		return comunicationLabel;
	}

	/**
	 * @return the myPoliticCardsPanel
	 */
	public MyPoliticCardsPanel getMyPoliticCardsPanel() {
		return myPoliticCardsPanel;
	}

	/**
	 * @return the myBusinessPermitTilesPanel
	 */
	public MyBusinessPermitTilesPanel getMyBusinessPermitTilesPanel() {
		return myBusinessPermitTilesPanel;
	}

	/**
	 * @return the zoomedBusinessPermitTilePanel
	 */
	public ZoomedBusinessPermitTilePanel getZoomedBusinessPermitTilePanel() {
		return zoomedBusinessPermitTilePanel;
	}

	/**
	 * @return the townInformationPanel
	 */
	public TownInformationPanel getTownInformationPanel() {
		return townInformationPanel;
	}
	
}
