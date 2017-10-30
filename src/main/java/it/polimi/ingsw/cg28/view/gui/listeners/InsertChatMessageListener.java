package it.polimi.ingsw.cg28.view.gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;

import it.polimi.ingsw.cg28.view.gui.GuiManager;
import it.polimi.ingsw.cg28.view.messages.action.ChatActionMsg;

/**
 * listener to send a chat msg
 * @author Alessandro
 *
 */
public class InsertChatMessageListener implements ActionListener {

	private String msg;
	private GuiManager manager;
	
	/**
	 * constructor of the class
	 * @param guiManager the gameBoardWindow
	 */
	public InsertChatMessageListener(GuiManager guiManager) {
		this.manager = guiManager;
	}
	
	/**
	 * when you insert text in the JtextArea and press ENTER , a new ChatActionMsg is created and notified
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JTextField textField = (JTextField) e.getSource();
		msg = e.getActionCommand();
		ChatActionMsg chatMsg = new ChatActionMsg(msg);
		manager.processRequest(chatMsg);
		textField.setText("");
	}

}
