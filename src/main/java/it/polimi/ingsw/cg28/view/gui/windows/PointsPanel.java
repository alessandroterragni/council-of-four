package it.polimi.ingsw.cg28.view.gui.windows;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import it.polimi.ingsw.cg28.view.gui.windows.others.OtherPlayersInfoWindow;

/**
 * Panel that shows the Progress bars of the points of the player and the labels that hoovered show some important 
 * informations:
 * when you hoover yourPoliticCardsTextArea: your politic cards are shown in the rulesPanel;
 * when you hoover showYourTilesTextArea: your tiles are shown in the rulesPanel;
 * when you hoover otherPlayersTextArea: {@link OtherPlayersInfoWindow} opens
 * when you hoover the icon Label of the nobility track, the nobilityTrackLabel becomes visible in the SubMapPanels.
 * @author Alessandro
 *
 */
public class PointsPanel extends JPanel {

	private static final long serialVersionUID = -1758393148235418473L;
	
	private JProgressBar coinsProgressBar;
	private JProgressBar nobilityProgressBar;
	private JProgressBar victoryProgressBar;
	private JLabel coinsIconLabel;
	private JLabel nobilityIconLabel;
	private JLabel victoryIconLabel;
	private JLabel yourPoliticCardsTextArea;
	private JLabel otherPlayersTextArea;
	private JLabel showYourTilesTextArea;
	private JProgressBar assistantProgressBar;
	private JLabel assistantIconLabel;
	private OtherPlayersInfoWindow infoWindow;
	
	/**
	 * Constructor of the class.
	 * @param boardWindow - {@link GameBoardWindow}
	 */
	public PointsPanel(GameBoardWindow boardWindow) {
		super();
		this.setBounds(755, 487, 253, 159);
		this.setLayout(null);
		this.setOpaque(false);
		
		assistantProgressBar = new JProgressBar();
		assistantProgressBar.setStringPainted(true);
		assistantProgressBar.setBounds(38, 12, 203, 14);
		this.add(assistantProgressBar);
		
		coinsProgressBar = new JProgressBar();
		coinsProgressBar.setStringPainted(true);
		coinsProgressBar.setBounds(38, 34, 203, 14);
		this.add(coinsProgressBar);
		
		nobilityProgressBar = new JProgressBar();
		nobilityProgressBar.setStringPainted(true);
		nobilityProgressBar.setBounds(38, 60, 203, 14);
		this.add(nobilityProgressBar);
		
		victoryProgressBar = new JProgressBar();
		victoryProgressBar.setForeground(Color.BLACK);
		victoryProgressBar.setStringPainted(true);
		victoryProgressBar.setBounds(38, 86, 203, 14);
		this.add(victoryProgressBar);
		
		coinsIconLabel = new JLabel("");
		coinsIconLabel.setIcon(new ImageIcon("src/img/pointsIcons/coinsIcon.png"));
		coinsIconLabel.setBounds(12, 34, 21, 16);
		this.add(coinsIconLabel);
		
		assistantIconLabel = new JLabel("");
		assistantIconLabel.setIcon(new ImageIcon("src/img/pointsIcons/assistantIcon.png"));
		assistantIconLabel.setBounds(12, 11, 21, 16);
		this.add(assistantIconLabel);
		
		nobilityIconLabel = new JLabel("");
		nobilityIconLabel.setIcon(new ImageIcon("src/img/pointsIcons/nobilityIcon.png"));
		nobilityIconLabel.setBounds(12, 59, 21, 16);
		this.add(nobilityIconLabel);
		nobilityIconLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				boardWindow.getSubMapPanel().getNobilityTrackLabel().setVisible(true);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				boardWindow.getSubMapPanel().getNobilityTrackLabel().setVisible(false);				
			}

		});
		
		victoryIconLabel = new JLabel("");
		victoryIconLabel.setIcon(new ImageIcon("src/img/pointsIcons/victoryIcon.png"));
		victoryIconLabel.setBounds(12, 85, 21, 16);
		this.add(victoryIconLabel);
		
		yourPoliticCardsTextArea = new JLabel("My politic cards",SwingConstants.CENTER);
		yourPoliticCardsTextArea.setFont(new Font("Dialog", Font.BOLD, 12));
		yourPoliticCardsTextArea.setText("My politic cards");
		yourPoliticCardsTextArea.setBorder(null);
		yourPoliticCardsTextArea.setBackground(new Color(0, 0, 0, 0));
		yourPoliticCardsTextArea.setBounds(38, 112, 98, 20);
		this.add(yourPoliticCardsTextArea);
		yourPoliticCardsTextArea.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				boardWindow.getRulesPanel().getMyPoliticCardsPanel().setVisible(true);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				boardWindow.getRulesPanel().getMyPoliticCardsPanel().setVisible(false);
			}
		});
		
		infoWindow = new OtherPlayersInfoWindow();
		
		otherPlayersTextArea = new JLabel("Show other players items and points",SwingConstants.CENTER);
		otherPlayersTextArea.setFont(new Font("Dialog", Font.PLAIN, 12));
		otherPlayersTextArea.setBorder(null);
		otherPlayersTextArea.setBackground(new Color(0, 0, 0, 0));
		otherPlayersTextArea.setBounds(12, 139, 229, 20);
		this.add(otherPlayersTextArea);
		otherPlayersTextArea.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				infoWindow.setVisible(true);
			}
		});
		
		showYourTilesTextArea = new JLabel("My tiles",SwingConstants.CENTER);
		showYourTilesTextArea.setFont(new Font("Dialog", Font.BOLD, 12));
		showYourTilesTextArea.setBorder(null);
		showYourTilesTextArea.setBackground(new Color(0, 0, 0, 0));
		showYourTilesTextArea.setBounds(141, 112, 100, 20);
		this.add(showYourTilesTextArea);
		showYourTilesTextArea.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				boardWindow.getRulesPanel().getMyBusinessPermitTilesPanel().setVisible(true);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				boardWindow.getRulesPanel().getMyBusinessPermitTilesPanel().setVisible(false);
			}
		});
		
	}
	

	/**
	 * @return the coinsProgressBar
	 */
	public JProgressBar getCoinsProgressBar() {
		return coinsProgressBar;
	}

	/**
	 * @return the assistantProgressBar
	 */
	public JProgressBar getAssistantProgressBar() {
		return assistantProgressBar;
	}


	/**
	 * @return the nobilityProgressBar
	 */
	public JProgressBar getNobilityProgressBar() {
		return nobilityProgressBar;
	}


	/**
	 * @return the victoryProgressBar
	 */
	public JProgressBar getVictoryProgressBar() {
		return victoryProgressBar;
	}


	/**
	 * @return the infoWindow
	 */
	public OtherPlayersInfoWindow getInfoWindow() {
		return infoWindow;
	}
	

	
}
