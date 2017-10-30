/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.windows.others;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.WindowEvent;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import it.polimi.ingsw.cg28.view.gui.listeners.PoliticCardJRadioButtonListener;
import it.polimi.ingsw.cg28.view.gui.windows.GameBoardWindow;
import it.polimi.ingsw.cg28.view.messages.action.QuitActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.bazaar.SellPoliticCardsActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.EmporiumKingActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.PermitTileActionMsg;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;

/**
 * Window that allows the player to chose a Politic Card.
 * @author Alessandro
 *
 */
public class PoliticCardsChoiceWindow extends JFrame {
	
	private static final long serialVersionUID = 7297622006200419626L;
	private JLabel titleTextLabel;
	
	public PoliticCardsChoiceWindow(Color[] cards,GameBoardWindow window) {
		super();
		this.setBounds(200, 200, 450, 450);
		this.getContentPane().setBackground(new Color(241, 212, 146));
		this.setAlwaysOnTop(true);
		getContentPane().setLayout(null);
		this.setResizable(false);
		
		JPanel titlePanel = new JPanel();
		titlePanel.setBounds(0, 0, 444, 72);
		getContentPane().add(titlePanel);
		titlePanel.setLayout(null);
		titlePanel.setOpaque(false);
		
		titleTextLabel = new JLabel();
		titleTextLabel.setFont(new Font("Dialog", Font.BOLD, 18));
		titleTextLabel.setText("Chose the politic cards you want to use");
		titleTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleTextLabel.setVerticalAlignment(SwingConstants.CENTER);
		titleTextLabel.setBounds(0, 0, 434, 72);
		titlePanel.add(titleTextLabel);
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setBounds(0, 73, 444, 271);
		buttonsPanel.setLayout(new FlowLayout());
		buttonsPanel.setOpaque(false);
		getContentPane().add(buttonsPanel);
		
		JPanel donePanel = new JPanel();
		donePanel.setBounds(0, 343, 444, 78);
		donePanel.setOpaque(false);
		getContentPane().add(donePanel);
		
		JButton btnDone = new JButton("DONE");
		btnDone.addMouseListener(new MouseAdapter() {
			
			/**
			 * When the end button is clicked , the codes of the politic cards clicked are set and notified,
			 * depending on the type of ActionMSg stored in the GuiHandler.
			 */
			@Override
			public void mouseClicked(MouseEvent arg0) {
					
					if(window.getGuiHandler().getActionMsg() instanceof PermitTileActionMsg){
						window.getGuiHandler().setCodes(window.getGuiHandler().getCodeBuffer().toString(), 2);
						dispose();
						window.getRulesPanel().getComunicationLabel().setVisible(true);
						window.getRulesPanel().getComunicationLabel().getComunicationTextArea().setText("");
						window.getRulesPanel().getComunicationLabel().getComunicationTextArea().setText("Click on the tile you want to buy");
						window.getGuiHandler().getCodeBuffer().delete(0,window.getGuiHandler().getCodeBuffer().length());
					}
					
					if(window.getGuiHandler().getActionMsg() instanceof EmporiumKingActionMsg){
						window.getGuiHandler().setCodes(window.getGuiHandler().getCodeBuffer().toString(), 1);
						dispose();
						window.getRulesPanel().getComunicationLabel().setVisible(true);
						window.getRulesPanel().getComunicationLabel().getComunicationTextArea().setText("");
						window.getRulesPanel().getComunicationLabel().getComunicationTextArea().setText("Click on the town where you want to move the king");
						window.getGuiHandler().getCodeBuffer().delete(0,window.getGuiHandler().getCodeBuffer().length());
						}	
					
					if(window.getGuiHandler().getActionMsg() instanceof SellPoliticCardsActionMsg){
						window.getGuiHandler().setCodes(window.getGuiHandler().getCodeBuffer().toString(), 0);
						dispose();
						ImageIcon coin = new ImageIcon("src/img/bonuses/coin.png");
						String price = (String) JOptionPane.showInputDialog(null, "Insert the price","Bazaar",
								JOptionPane.INFORMATION_MESSAGE, coin, null,"");
						window.getGuiHandler().setCodes(price, 1);
						window.getGuiHandler().getActionMsg().setCodes(window.getGuiHandler().getCodes());
						window.getGuiHandler().notify(window.getGuiHandler().getActionMsg());
						window.getGuiHandler().getCodeBuffer().delete(0,window.getGuiHandler().getCodeBuffer().length());
						}
				}
		});
		
		donePanel.add(btnDone);
		
		
		for (int i =0;i < cards.length;i++) {

            PoliticCardJRadioButton button = new PoliticCardJRadioButton(cards[i], i);
            button.addActionListener(new PoliticCardJRadioButtonListener(window));
            buttonsPanel.add(button);   
		} 
		
		
		this.setVisible(true);
		
		/**
		 * If the player closes the windows : it sends a QuiActionMsg,closes the window and guts the CodeBuffer.
		 */
		this.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                window.getGuiHandler().notify(new QuitActionMsg());
        		e.getWindow().dispose();
        		window.getActionPanel().enableMainActionButtons(true);
        		window.getGuiHandler().getCodeBuffer().delete(0,window.getGuiHandler().getCodeBuffer().length());
            }
        });
		
	}
	
	
}
