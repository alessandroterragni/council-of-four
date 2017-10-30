/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.windows;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.view.messages.action.EndGameMsg;
import it.polimi.ingsw.cg28.view.messages.event.EndGameEventMsg;

import java.awt.Color;
import java.awt.Font;

/**
 * Final window frame : it's the window that appears at the end of the game with the winner and the final ranking.
 * @author Alessandro
 *
 */
public class FinalWindow extends JFrame {
	
	private static final long serialVersionUID = -8144900133178355573L;
	
	private JTextArea textArea;
	
	/**
	 * Constructor of the class.
	 * @param eventMsg - {@link EndGameEventMsg}
	 */
	public FinalWindow(EndGameEventMsg eventMsg) {
		
		this.setBounds(300, 300, 340, 300);
		this.setResizable(false);
		this.getContentPane().setBackground(new Color(241, 212, 146));
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(20, 20, 320, 280);
		panel.setOpaque(false);
		this.getContentPane().add(panel);
		
		textArea = new JTextArea();
		textArea.setFont(new Font("Deutsch Gothic", Font.PLAIN, 23));
		textArea.setBounds(0, 0, 494, 348);
		textArea.setOpaque(false);
		panel.add(textArea);
		textArea.setColumns(10);
		textArea.setEditable(false);
		
		
		EndGameMsg msg = eventMsg.getEndGameMsg();
		
		PlayerID winner = msg.getWinner();
		
		textArea.append("Final Ranking\n");
		textArea.append("Player " + winner.getName() + " WINS!\n\n");
		
		textArea.append("Scores:\n");
		textArea.append(winner.getName() + ": " + msg.getScores().get(winner).intValue() + "\n");
		msg.getScores().remove(winner);
		
		msg
			.getScores()
			.entrySet()
			.stream()
			.sorted((p1,p2) -> Integer.compare(p2.getValue(), p1.getValue()))
			.forEach(p -> textArea.append(p.getKey().getName() + ": " + p.getValue() + "\n"));
	
	}
}
