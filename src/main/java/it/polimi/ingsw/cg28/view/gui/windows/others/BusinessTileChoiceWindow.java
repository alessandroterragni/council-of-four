/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.windows.others;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import it.polimi.ingsw.cg28.tmodel.TBusinessPermitTile;
import it.polimi.ingsw.cg28.view.gui.listeners.BusinessTileButtonListener;
import it.polimi.ingsw.cg28.view.gui.windows.GameBoardWindow;
import it.polimi.ingsw.cg28.view.gui.windows.submap.BusinessPermitTileLabel;
import it.polimi.ingsw.cg28.view.messages.action.QuitActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.bonus.ReusePermitBonusActionMsg;

/**
 * Window that allows the player to chose a BusinessTile.
 * @author Alessandro
 *
 */
public class BusinessTileChoiceWindow extends JFrame {
	
	private static final long serialVersionUID = 8988187782102362377L;
	
	private JLabel titleTextLabel;
	
	/**
	 * Constructor of the class.
	 * @param tiles - tiles to want to display
	 * @param window - {@link GameBoardWindow}
	 * @param multipleChoice - true if you want to allow a multiple choice , false otherwise 
	 */
	public BusinessTileChoiceWindow(List<TBusinessPermitTile> tiles,GameBoardWindow window,boolean multipleChoice) {
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
		titleTextLabel.setText("Chose the tile you want to use");
		titleTextLabel.setBounds(0, 0, 434, 72);
		titleTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleTextLabel.setVerticalAlignment(SwingConstants.CENTER);
		titlePanel.add(titleTextLabel);
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setBounds(0, 73, 434, 188);
		buttonsPanel.setLayout(new FlowLayout());
		buttonsPanel.setOpaque(false);
		getContentPane().add(buttonsPanel);
		
		for(int i = 0;i <tiles.size();i++){
			BusinessPermitTileLabel tileLabel = new BusinessPermitTileLabel(tiles.get(i),i);
			buttonsPanel.add(tileLabel);
			tileLabel.addMouseListener(new BusinessTileButtonListener(i, window, this,multipleChoice));
		}
		
		if(multipleChoice){
			
			JPanel donePanel = new JPanel();
			donePanel.setBounds(0, 206, 434, 54);
			donePanel.setOpaque(false);
			getContentPane().add(donePanel);
			
			JButton btnDone = new JButton("DONE");
			btnDone.addMouseListener(new MouseAdapter() {
				
				@Override
				public void mouseClicked(MouseEvent arg0) {
					if(window.getGuiHandler().getActionMsg() instanceof ReusePermitBonusActionMsg)
					{
						boolean b;
						if(window.getGuiHandler().getCodeBuffer().length()!=0){
							String[] codes = window.getGuiHandler().getCodeBuffer().toString().split(" ");
							window.getGuiHandler().getCodeBuffer().setLength(0);
							for(int i =0;i<codes.length;i++){
								window.getGuiHandler().setCodes(codes[i], i);
						}
						
						b=window.getGuiHandler().getActionMsg().setCodes(window.getGuiHandler().getCodes());
						
						if(b){
							window.getGuiHandler().notify(window.getGuiHandler().getActionMsg());
							window.getGuiHandler().getCodeBuffer().setLength(0);
							dispose();
							}
						}
					}
					
					else{
						window.getGuiHandler().setCodes(window.getGuiHandler().getCodeBuffer().toString(), 0);
						dispose();
						window.getGuiHandler().getCodeBuffer().delete(0,window.getGuiHandler().getCodeBuffer().length());
						ImageIcon coin = new ImageIcon("src/img/bonuses/coin.png");
						String price = (String) JOptionPane.showInputDialog(null, "Insert the price","Bazaar",
								JOptionPane.INFORMATION_MESSAGE, coin, null,"");
						window.getGuiHandler().setCodes(price,1);
						window.getGuiHandler().getActionMsg().setCodes(window.getGuiHandler().getCodes());
						window.getGuiHandler().notify(window.getGuiHandler().getActionMsg());
						}
					}
			});
			
			donePanel.add(btnDone);
			
		}
	
		this.setVisible(true);
		
		/**
		 * Listener for the closing window: it doesn't allow the window to close if the player has gained a ReusePermitBonus
		 * otherwise it sends a QuiActionMsg, closes the window and guts the CodeBuffer.
		 */
		this.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {	
            	if(window.getGuiHandler().getActionMsg() instanceof ReusePermitBonusActionMsg){
            		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            	}
            	else {
                window.getGuiHandler().notify(new QuitActionMsg());
        		e.getWindow().dispose();
        		window.getActionPanel().enableMainActionButtons(true);
        		window.getGuiHandler().getCodeBuffer().delete(0,window.getGuiHandler().getCodeBuffer().length());
            	}
            }
        });
			
	}

}
