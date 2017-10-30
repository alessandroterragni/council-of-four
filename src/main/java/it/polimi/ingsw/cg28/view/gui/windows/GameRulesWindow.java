/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.windows;



import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

/**
 * window to display rules game images
 * @author Alessandro
 *
 */
public class GameRulesWindow extends JFrame {
	
	private static final long serialVersionUID = 7116301644217259542L;
	
	/**
	 * constructor of the class
	 */
	public GameRulesWindow() {
		super();
		this.setBounds(0, 0, 500, 600);
		this.getContentPane().setLayout(null);
		this.setResizable(false);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 500, 600);
		this.getContentPane().add(tabbedPane);
		
		JLabel catroncinoLabel = new JLabel(new ImageIcon("src/img/rules/cartoncinoBig.png"));
		catroncinoLabel.setSize(500,600);
		tabbedPane.addTab("Quick review", null, catroncinoLabel, null);
		
		JLabel page1Label = new JLabel(new ImageIcon("src/img/rules/page1.jpg"));
		page1Label.setSize(500,600);
		tabbedPane.addTab("Page 1", null, page1Label, null);
		
		JLabel page2Label = new JLabel(new ImageIcon("src/img/rules/page2.jpg"));
		page2Label.setSize(500,600);
		tabbedPane.addTab("Page 2", null, page2Label, null);
		
		JLabel page3Label = new JLabel(new ImageIcon("src/img/rules/page3.jpg"));
		page3Label.setSize(500,600);
		tabbedPane.addTab("Page 3", null, page3Label, null);
		
		JLabel page4Label = new JLabel(new ImageIcon("src/img/rules/page4.jpg"));
		page4Label.setSize(500,600);
		tabbedPane.addTab("Page 4", null, page4Label, null);
		
		JLabel page5Label = new JLabel(new ImageIcon("src/img/rules/page5.jpg"));
		page5Label.setSize(500,600);
		tabbedPane.addTab("Page 5", null, page5Label, null);
		
	}
	
}
