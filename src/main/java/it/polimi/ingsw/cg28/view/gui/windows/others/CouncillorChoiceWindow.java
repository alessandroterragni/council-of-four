/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.windows.others;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import it.polimi.ingsw.cg28.view.gui.listeners.CouncillorJRadioButtonListener;
import it.polimi.ingsw.cg28.view.gui.windows.GameBoardWindow;
import it.polimi.ingsw.cg28.view.messages.action.QuitActionMsg;

import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Window that allows the player to chose a councillor.
 * @author Alessandro
 *
 */
public class CouncillorChoiceWindow extends JFrame{
	
	private static final long serialVersionUID = 1280565697660729843L;
	private ButtonGroup group = new ButtonGroup();
	private JLabel titleTextLabel;
	
	/**
	 * Constructor of the class.
	 * @param pool list of the color of the councillor you want to display
	 * @param window {@link GameBoardWindow}
	 */
	public CouncillorChoiceWindow(List<Color> pool,GameBoardWindow window) {
		
		super();
		this.setBounds(200, 200, 450, 300);
		this.setAlwaysOnTop(true);
		getContentPane().setLayout(null);
		this.getContentPane().setBackground(new Color(241, 212, 146));
		this.setResizable(false);
		
		JPanel titlePanel = new JPanel();
		titlePanel.setBounds(0, 0, 434, 72);
		titlePanel.setOpaque(false);
		getContentPane().add(titlePanel);
		titlePanel.setLayout(null);
		
		titleTextLabel = new JLabel();
		titleTextLabel.setFont(new Font("Dialog", Font.BOLD, 18));
		titleTextLabel.setText("Chose the councillor you want to elect");
		titleTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleTextLabel.setVerticalAlignment(SwingConstants.CENTER);
		titleTextLabel.setBounds(0, 0, 434, 72);
		titlePanel.add(titleTextLabel);
		
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setBounds(0, 73, 434, 188);
		buttonsPanel.setLayout(new FlowLayout());
		buttonsPanel.setOpaque(false);
		getContentPane().add(buttonsPanel);
		
		
		for (int i =0;i < pool.size();i++) {

            CouncillorJRadioButton button = new CouncillorJRadioButton(pool.get(i),i);
            button.addActionListener(new CouncillorJRadioButtonListener(window, this));
            group.add(button);
            buttonsPanel.add(button);   
		} 
		
		this.setVisible(true);
		
		/**
		 * If the player closes the windows : it sends a QuiActionMsg, closes the window and guts the CodeBuffer.
		 */
		this.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                window.getGuiHandler().notify(new QuitActionMsg());
        		e.getWindow().dispose();
        		window.getActionPanel().enableMainActionButtons(true);
            }
        });
		
	}
	
}
