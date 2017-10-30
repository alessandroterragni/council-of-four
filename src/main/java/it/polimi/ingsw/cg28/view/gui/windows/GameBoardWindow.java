/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.windows;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.view.gui.GuiHandler;
import it.polimi.ingsw.cg28.view.gui.MusicPlayer;
import it.polimi.ingsw.cg28.view.gui.windows.kingpanel.KingPanel;
import it.polimi.ingsw.cg28.view.gui.windows.map.MapPanel;
import it.polimi.ingsw.cg28.view.gui.windows.rules.RulesPanel;
import it.polimi.ingsw.cg28.view.gui.windows.submap.SubMapPanel;
import it.polimi.ingsw.cg28.view.messages.action.LeaveGameActionMsg;


/**
 * The main window of the game: it host the mapPanel, the subMapPanel,the ActionPanel.the chatPanel,
 * The historyPanel,the  rulesPanel,the kingPanel, the pointsPanel,the menuBar.
 * @author Alessandro
 *
 */
public class GameBoardWindow extends JFrame{
	
	private static final long serialVersionUID = -9148869325818706803L;
	
	private transient MusicPlayer musicPlayer;
	
	private transient GuiHandler guiHandler;
	private PlayerID player;
	
	private MapPanel mapPanel;
	private SubMapPanel subMapPanel;
	private ActionsPanel actionPanel;
	private ChatPanel chatPanel;
	private HistoryPanel historyPanel;
	private RulesPanel rulesPanel;
	private KingPanel kingPanel;
	private PointsPanel pointsPanel;
	private MenuBar mymenuBar;
	
	/**
	 * Constructor of the class.
	 * @param guiHandler - {@link GuiHandler}
	 */
	public GameBoardWindow(GuiHandler guiHandler){
		
		super("Council Of Four");
		this.guiHandler = guiHandler;
		this.getContentPane().setBackground(new Color(244, 237, 221));
		this.setTitle("Council Of Four");
		this.setBounds(100, 100, 1024, 720);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		this.setResizable(false);
		
		musicPlayer = new MusicPlayer(this);
		
		//map
		mapPanel = new MapPanel(this);
		this.getContentPane().add(mapPanel);
		
		//sub map
		subMapPanel = new SubMapPanel(this);
		this.getContentPane().add(subMapPanel);
				
		//action buttons
		
		actionPanel = new ActionsPanel(this);
		this.getContentPane().add(actionPanel);
		
		
		//chat
		chatPanel = new ChatPanel(guiHandler.getGuiManager());
		this.getContentPane().add(chatPanel);
		
		
		// hystory
		historyPanel = new HistoryPanel();
		this.getContentPane().add(historyPanel);

	
		
		// cartoncino
		rulesPanel = new RulesPanel();
		this.getContentPane().add(rulesPanel);
		
		
		// king
		kingPanel = new KingPanel();
		this.getContentPane().add(kingPanel);
		
		// Points
		pointsPanel = new PointsPanel(this);
		this.getContentPane().add(pointsPanel);
		
		//menu
		mymenuBar = new MenuBar(this);
		this.setJMenuBar(mymenuBar);
		
		
		/**
		 * On close, it sends a LeaveGameActionMsg.
		 */
		this.addWindowListener(new WindowAdapter()
	    {
	        @Override
	        public void windowClosing(WindowEvent e)
	        {
	            getGuiHandler().notify(new LeaveGameActionMsg("Don't cry because it's over. Smile because it happened."));
	    		dispose();
	        }
	    });
	}

	

	/**
	 * Getter of the guiHandler associated with the window.
	 * @return the guiHandler
	 */
	public GuiHandler getGuiHandler() {
		return guiHandler;
	}

	/**
	 * It sets the player that is playing with this window.
	 * @param player - the player to set
	 */
	public void setPlayer(PlayerID player) {
		this.player = player;
	}

	/**
	 * Getter of the player who is playing with the window.
	 * @return the player
	 */
	public PlayerID getPlayer() {
		return player;
	}
	
	
	/**
	 * @return the mapPanel
	 */
	public MapPanel getMapPanel() {
		return mapPanel;
	}


	/**
	 * @return the subMapPanel
	 */
	public SubMapPanel getSubMapPanel() {
		return subMapPanel;
	}


	/**
	 * @return the actionPanel
	 */
	public ActionsPanel getActionPanel() {
		return actionPanel;
	}


	
	/**
	 * @return the rulesPanel
	 */
	public RulesPanel getRulesPanel() {
		return rulesPanel;
	}


	/**
	 * @return the chatPanel
	 */
	public ChatPanel getChatPanel() {
		return chatPanel;
	}


	/**
	 * @return the historyPanel
	 */
	public HistoryPanel getHistoryPanel() {
		return historyPanel;
	}


	/**
	 * @return the pointsPanel
	 */
	public PointsPanel getPointsPanel() {
		return pointsPanel;
	}

	/**
	 * 
	 * @return the KingPanel
	 */
	public KingPanel getKingPanel() {
		return kingPanel;
	}



	/**
	 * @return the menuBar
	 */
	public MenuBar getMyMenuBar() {
		return mymenuBar;
	}



	/**
	 * @return the musicPlayer
	 */
	public MusicPlayer getMusicPlayer() {
		return musicPlayer;
	}
		
}
	

