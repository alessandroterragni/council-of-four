package it.polimi.ingsw.cg28.view.gui.windows.submap;


import javax.swing.ImageIcon;
import javax.swing.JLabel;

import it.polimi.ingsw.cg28.view.gui.listeners.BalconyLabelListener;
import it.polimi.ingsw.cg28.view.gui.windows.GameBoardWindow;

/**
 * Label that host a balcony image and where are put the councillor labels.
 * @author Alessandro
 *
 */
public class BalconyLabel extends JLabel{

	private static final long serialVersionUID = 39161923146570022L;
	private int position;
	private CouncillorLabel councillor1;
	private CouncillorLabel councillor2;
	private CouncillorLabel councillor3;
	private CouncillorLabel councillor4;
	 
	
	 /**
	  * Constructor of the class.
	  * @param i - index of the balcony in the map
	  * @param subMapPanel - {@link SubMapLabel}
	  * @param gameBoardWindow - {@link GameBoardWindow}
	  */
	public BalconyLabel(int i,SubMapPanel subMapPanel,GameBoardWindow gameBoardWindow) {
		super();
		this.position =i;
		this.addMouseListener(new BalconyLabelListener(gameBoardWindow));
		subMapPanel.add(this,new Integer(1));
		this.setIcon(new ImageIcon("src/img/submap/balcony.png"));
		
		councillor1 = new CouncillorLabel();
		subMapPanel.add(councillor1,new Integer(1));
		councillor2 = new CouncillorLabel();
		subMapPanel.add(councillor2,new Integer(1));
		councillor3 = new CouncillorLabel();
		subMapPanel.add(councillor3,new Integer(1));
		councillor4 = new CouncillorLabel();
		subMapPanel.add(councillor4,new Integer(1));
		
		if(i==1){
			
			this.setBounds(82, 63, 84, 13);	
			councillor1.setBounds(82, 50, 18, 26);
			councillor2.setBounds(104, 50, 18, 26);
			councillor3.setBounds(126, 50, 18, 26);
			councillor4.setBounds(148, 50, 18, 26);
		}
		
		if(i==2){
			
			this.setBounds(325, 65, 84, 13);
			councillor1.setBounds(326, 51, 18, 26);
			councillor2.setBounds(346, 51, 18, 26);
			councillor3.setBounds(368, 51, 18, 26);
			councillor4.setBounds(389, 51, 18, 26);
		}
		
		if(i==3){
			
			this.setBounds(598, 65, 84, 13);
			councillor1.setBounds(599, 49, 18, 26);
			councillor2.setBounds(620, 49, 18, 26);
			councillor3.setBounds(642, 49, 18, 26);
			councillor4.setBounds(663, 49, 18, 26);
		}
		
		if(i==4){
			
			this.setBounds(483, 92, 84, 13);
			councillor1.setBounds(482, 79, 18, 26);
			councillor2.setBounds(503, 79, 18, 26);
			councillor3.setBounds(525, 79, 18, 26);
			councillor4.setBounds(548, 79, 18, 26);
		}
	}

	/**
	 * i = number of balcony  j= number of councillor in the j-balcony.
	 * @return the j-councillor
	 */
	public CouncillorLabel getCouncillor(int j) {
			switch(j) {
				case 1: return councillor1;
				case 2: return councillor2;
				case 3: return councillor3;
				case 4: return councillor4;
				default : return null;
			}	
	}

	
	/**
	 * @return the index of the balcony in the map
	 */
	public int getPosition() {
		return position;
	}

}
