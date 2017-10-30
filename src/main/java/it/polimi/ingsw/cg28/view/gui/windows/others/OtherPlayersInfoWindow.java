/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.windows.others;


import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Windows that shows the information about all the players of the game during the match.
 * @author Alessandro
 *
 */
public class OtherPlayersInfoWindow extends JFrame{
	
	private static final long serialVersionUID = -4658395600903002229L;
	private JPanel otherPlayersInfoPanel;
		
	/**
	 * Constructor of the class.
	 */
	public OtherPlayersInfoWindow() {
		
		super();
		this.setBounds(200, 200, 700, 450);
		this.getContentPane().setBackground(new Color(241, 212, 146));
		this.setAlwaysOnTop(true);
		this.setResizable(false);
		this.setVisible(false);
		
		otherPlayersInfoPanel = new JPanel();
		otherPlayersInfoPanel.setLayout(new BoxLayout(otherPlayersInfoPanel, BoxLayout.Y_AXIS));
		otherPlayersInfoPanel.setBackground(new Color(241, 212, 146));
		otherPlayersInfoPanel.setOpaque(true);
		getContentPane().add(otherPlayersInfoPanel);
	
		JScrollPane jScrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jScrollPane.setBounds(0, 0, 746, 372);
		jScrollPane.setViewportView(otherPlayersInfoPanel);
		jScrollPane.setOpaque(false);
		jScrollPane.getViewport().setOpaque(false);
		this.add(jScrollPane);
		
	}
	
	/**
	 * It adds a {@link PlayerInfoPanel} to this window.
	 * @param infoPanel
	 */
	public void addPlayerPanel(PlayerInfoPanel infoPanel){
		otherPlayersInfoPanel.add(infoPanel);
		otherPlayersInfoPanel.repaint();
		otherPlayersInfoPanel.revalidate();
	}

	/**
	 * @return the otherPlayersInfoPanel
	 */
	public JPanel getOtherPlayersInfoPanel() {
		return otherPlayersInfoPanel;
	}
	
	
	
}
