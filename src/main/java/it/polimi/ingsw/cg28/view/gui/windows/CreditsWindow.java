/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.windows;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * it displays the credits of the game in a window
 * @author Alessandro
 *
 */
public class CreditsWindow extends JFrame {

	private static final long serialVersionUID = 7742382165653310553L;
	
	/**
	 * construcot of the class, it just creates the window and sets the image 
	 */
	public CreditsWindow() {
		super();
		this.setResizable(false);
		this.setTitle("Credits");
		this.setBounds(100,100,500,600);
		
		JLabel creditsLabel = new JLabel(new ImageIcon("src/img/credits.png"));
		this.getContentPane().add(creditsLabel);
	}
}
