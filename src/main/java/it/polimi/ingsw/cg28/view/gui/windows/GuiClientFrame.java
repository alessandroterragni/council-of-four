package it.polimi.ingsw.cg28.view.gui.windows;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import it.polimi.ingsw.cg28.view.StringJar;
import it.polimi.ingsw.cg28.view.gui.GuiManager;

/**
 * Starting frame for a GuiClient.
 * @author Mario
 *
 */
public class GuiClientFrame extends JFrame {

	private static final long serialVersionUID = 4366549994694523398L;
	private JLayeredPane panel;
	private JPanel chatPanel;
	private JPanel settingsPanel;
	private transient StringJar stringJar;
	
	/**
	 * Constructor of the class.
	 */
	public GuiClientFrame(){
		
		super();
    	stringJar = new StringJar();
    	
		this.setBounds(100, 100, 530, 303);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		this.setResizable(false);
		
		panel = new JLayeredPane();
		panel.setBounds(0, 0, 530, 303);
		this.getContentPane().add(panel);
		
		ImageIcon imageBack = new ImageIcon("src/img/guiClient/back.png");
		JLabel backLabel = new JLabel(imageBack);
		backLabel.setBounds(0, 0, 530, 303);
		backLabel.setOpaque(true);
		panel.add(backLabel, new Integer(-1));
		
		
		chatPanel = new JPanel();
		chatPanel.setBounds(261, 0, 269, 303);
		chatPanel.setOpaque(false);
		panel.add(chatPanel, new Integer(0));
		
		settingsPanel = new JPanel();
		settingsPanel.setOpaque(false);
		settingsPanel.setBounds(0, 0, 261, 303);
		panel.add(settingsPanel, new Integer(0));
		settingsPanel.setLayout(null);
		
	}
	
	/**
	 * Puts a {@ChatPanel} on the frame.
	 * @param manager - manager to dispatch chat messages.
	 * @return the ChatPanel put on the frame
	 */
	public ChatPanel startChat(GuiManager manager){
		
		chatPanel.removeAll();
		chatPanel.updateUI();
		settingsPanel.removeAll();
		settingsPanel.updateUI();
		
		JLabel lblInitGame = new JLabel("<html><font color='#a61212'>"+ stringJar.initGame() + "</font></html>");
		lblInitGame.setFont(new Font("Deutsch Gothic", Font.PLAIN, 18));
		lblInitGame.setBounds(25, 41, 240, 100);
		settingsPanel.add(lblInitGame);
		settingsPanel.setVisible(true);
		
		ChatPanel panel = new ChatPanel(manager);
		
		chatPanel = panel;
		chatPanel.setBounds(275, 5, 268, 274);
		chatPanel.setOpaque(false);
		chatPanel.setVisible(true);
		this.panel.add(chatPanel, new Integer(0));
		this.setVisible(true);
		
		chatPanel.revalidate();
		chatPanel.repaint();
		
		return panel;
		
	}
	
	/**
	 * getter of the chatPanel
	 * @return
	 */
	public JPanel getChatPanel() {
		return chatPanel;
	}
	
	/**
	 * setter of the chatPanel
	 * @return
	 */
	public JPanel getSettingsPanel() {
		return settingsPanel;
	}

}
