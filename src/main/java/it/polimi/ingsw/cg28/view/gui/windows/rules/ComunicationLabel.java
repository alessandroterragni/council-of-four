/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.windows.rules;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTextPane;

/**
 * Label that is used to show temporary images or text.
 * @author Alessandro
 *
 */
public class ComunicationLabel extends JLabel {
	
	private static final long serialVersionUID = -8973438580295157133L;
	private JTextPane comunicationTextArea;
	
	/**
	 * Constructor of the class.
	 */
	public ComunicationLabel() {
		super();
		this.setBounds(0, 0, 182, 174);   
		comunicationTextArea = new JTextPane();
		comunicationTextArea.setEditable(false);
		comunicationTextArea.setBounds(6, 0, 174, 174);
		comunicationTextArea.setFont(new Font("Dialog", Font.BOLD, 18));
		comunicationTextArea.setForeground(new Color(201, 127, 8));
		comunicationTextArea.setAlignmentX(CENTER_ALIGNMENT);
		comunicationTextArea.setAlignmentY(CENTER_ALIGNMENT);
		comunicationTextArea.setBackground(new Color(244, 237, 221));
		this.add(comunicationTextArea);
		this.setVisible(false);
	}

	/**
	 * @return the comunicationTextArea
	 */
	public JTextPane getComunicationTextArea() {
		return comunicationTextArea;
	}	
	
}
