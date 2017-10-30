/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.windows;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import it.polimi.ingsw.cg28.view.gui.GuiHandler;
import it.polimi.ingsw.cg28.view.messages.action.StartMsg;

/**
 * @author Alessandro
 *
 */
public class StartWindow extends JFrame {

	private static final long serialVersionUID = -289235600071965859L;
	
	JPanel panel = new JPanel();
	JLabel welcome;
	JButton yesBazaarButton;
	JButton noBazaarButton;
	
	public StartWindow(GuiHandler guiHandler) {
		
		super("Settings Council of Four");
		this.setBounds(100, 100, 450, 240);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		this.setResizable(false);
		this.getContentPane().setBackground(new Color(241, 212, 146));
		
		panel.setBounds(0, 0, 450, 240);
		panel.setOpaque(false);
		this.getContentPane().add(panel);
		panel.setLayout(null);
		
		welcome = new JLabel("Welcome to the Council of Four", SwingConstants.CENTER);
		welcome.setFont(new Font("Deutsch Gothic", Font.PLAIN, 30));
		welcome.setOpaque(false);
		welcome.setBounds(0, 26, 450, 30);
		panel.add(welcome);
		
		JLabel choice = new JLabel("Do you want to play with the bazaar?", SwingConstants.CENTER);
		choice.setFont(new Font("Dialog", Font.PLAIN, 18));
		choice.setOpaque(false);
		choice.setBounds(0, 56, 450, 30);
		panel.add(choice);
		
		yesBazaarButton = new JButton("Yes");
		noBazaarButton = new JButton("No");
		
		yesBazaarButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
				StartMsg startMsg = new StartMsg();
				startMsg.setBazaar(true);
				guiHandler.notify(startMsg);
			}
		});
		yesBazaarButton.setBounds(111, 125, 89, 23);
		panel.add(yesBazaarButton);
		
		noBazaarButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
				StartMsg startMsg = new StartMsg();
				startMsg.setBazaar(false);
				guiHandler.notify(startMsg);
			}
		});
		noBazaarButton.setBounds(252, 125, 89, 23);
		panel.add(noBazaarButton);
	}

	
}
