package it.polimi.ingsw.cg28.view.gui.windows.bazaar;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Label that represents a salable assistant on a {@link SingleShelfPanel}.
 * @author Alessandro
 *
 */
public class TProductAssistantLabel extends JLabel {
	 
	private static final long serialVersionUID = -1078889232662369439L;
	
	/**
	 * Constructor of the class.
	 */
	public TProductAssistantLabel() {
		super();
		this.setIcon(new ImageIcon("src/img/bonuses/assistant.png"));
	}
}
