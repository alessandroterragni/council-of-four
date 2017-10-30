/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.windows;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import it.polimi.ingsw.cg28.view.gui.GuiManager;
import it.polimi.ingsw.cg28.view.gui.listeners.InsertChatMessageListener;

/**
 * Panel for the chat.
 * @author Alessandro
 *
 */
public class ChatPanel extends JPanel{
	
	private static final long serialVersionUID = 5414332371443117516L;
	
	private JLayeredPane panel;
	private JTextField insertMsgTextField;
	private JTextArea chatTextArea;
	private JScrollPane chatScrollBar;
	
	/**
	 * Constructor of the class.
	 * @param gameBoardWindow - {@link GameBoardWindow}
	 */
	public ChatPanel(GuiManager guiManager) {
		
		super();
		this.setBounds(755, 0, 253, 270);
		this.setLayout(null);
		this.setOpaque(false);
		
		panel = new JLayeredPane();
		panel.setBounds(0, 0, 253, 270);
		this.add(panel);
		
		ImageIcon image = new ImageIcon("src/img/chat.png");
		JLabel imageLabel = new JLabel(image);
		imageLabel.setBounds(0, 5, 253, 270);
		panel.add(imageLabel, new Integer(-1));
		imageLabel.setVisible(true);
		
		insertMsgTextField = new JTextField();
		insertMsgTextField.setBackground(new Color(228,210,170));
		insertMsgTextField.setBounds(10, 227, 231, 20);
		insertMsgTextField.setColumns(10);
		insertMsgTextField.addActionListener(new InsertChatMessageListener(guiManager));
		insertMsgTextField.setVisible(true);
		insertMsgTextField.setOpaque(true);
		panel.add(insertMsgTextField, new Integer(0));
		
		chatTextArea = new JTextArea();
		chatTextArea.setBackground(new Color(228,210,170, 200));
		chatTextArea.setFont(new Font("Dialog", Font.PLAIN, 10));
		chatTextArea.setBounds(10, 45, 231, 181);
		chatTextArea.setEditable(false);
		chatTextArea.setLineWrap(true);
		chatTextArea.setVisible(true);
		chatTextArea.setOpaque(true);
		panel.add(chatTextArea, new Integer(0));
		
		chatScrollBar = new JScrollPane(chatTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		chatScrollBar.getViewport().setOpaque(false);
		chatScrollBar.setOpaque(false);
		chatScrollBar.setBounds(10, 39, 231, 181);
		panel.add(chatScrollBar, new Integer(0));
		
	}
	

	/**
	 * Getter if the chatTextArea.
	 * @return the chatTextArea
	 */
	public JTextArea getChatTextArea() {
		return chatTextArea;
	}


}
