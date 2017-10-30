/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.windows;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Panel for the history: it has a historyTextArea where are displayed all the simple messages.
 * @author Alessandro
 *
 */
public class HistoryPanel extends JPanel {

	private static final long serialVersionUID = -8135947786665153945L;
	
	private JLabel historyLabel;
	private JTextArea historyTextArea;
	private JScrollPane historyScrollBar;
	
	/**
	 * Constructor of the class.
	 */
	public HistoryPanel() {
		super();
		this.setBounds(755, 279, 253, 202);
		this.setLayout(null);
		
		ImageIcon image = new ImageIcon("src/img/history.png");
		historyLabel = new JLabel(image);
		historyLabel.setBounds(0, 0, 253, 202);
		this.add(historyLabel);
		
		historyTextArea = new JTextArea();
		historyTextArea.setFont(new Font("Dialog", Font.PLAIN, 10));
		historyTextArea.setBackground(new Color(238, 234, 224));
		historyTextArea.setBounds(20, 20, 216, 162);
		historyTextArea.setEditable(false);
		historyTextArea.setLineWrap(true);
		this.add(historyTextArea);
		
		historyScrollBar = new JScrollPane(historyTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		historyScrollBar.setBounds(20, 20, 216, 162);
		this.add(historyScrollBar);
	}
	
	/**
	 * Getter of the historyTextArea.
	 * @return the historyTextArea
	 */
	public JTextArea getHistoryTextArea() {
		return historyTextArea;
	}
	
}
