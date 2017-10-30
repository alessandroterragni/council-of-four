package it.polimi.ingsw.cg28.view.gui;
import java.awt.Color;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import it.polimi.ingsw.cg28.connections.RequestHandler;
import it.polimi.ingsw.cg28.connections.client.Client;
import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.tmodel.TBusinessPermitTile;
import it.polimi.ingsw.cg28.tmodel.TPlayer;
import it.polimi.ingsw.cg28.tmodel.TProduct;
import it.polimi.ingsw.cg28.tmodel.TTown;
import it.polimi.ingsw.cg28.view.ViewHandler;
import it.polimi.ingsw.cg28.view.gui.windows.ActionsPanel;
import it.polimi.ingsw.cg28.view.gui.windows.ChatPanel;
import it.polimi.ingsw.cg28.view.gui.windows.FinalWindow;
import it.polimi.ingsw.cg28.view.gui.windows.GameBoardWindow;
import it.polimi.ingsw.cg28.view.gui.windows.GuiClientFrame;
import it.polimi.ingsw.cg28.view.gui.windows.HistoryPanel;
import it.polimi.ingsw.cg28.view.gui.windows.StartWindow;
import it.polimi.ingsw.cg28.view.gui.windows.bazaar.SingleShelfPanel;
import it.polimi.ingsw.cg28.view.gui.windows.others.PlayerInfoPanel;
import it.polimi.ingsw.cg28.view.gui.windows.rules.PoliticCardLabel;
import it.polimi.ingsw.cg28.view.gui.windows.submap.BusinessPermitTileLabel;
import it.polimi.ingsw.cg28.view.messages.action.ActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.TurnActionMsg;
import it.polimi.ingsw.cg28.view.messages.event.BazaarStatusMsg;
import it.polimi.ingsw.cg28.view.messages.event.ChatEventMsg;
import it.polimi.ingsw.cg28.view.messages.event.EndBazaarMsg;
import it.polimi.ingsw.cg28.view.messages.event.EndBuyTurnEventMsg;
import it.polimi.ingsw.cg28.view.messages.event.EndGameEventMsg;
import it.polimi.ingsw.cg28.view.messages.event.EndSellTurnEventMsg;
import it.polimi.ingsw.cg28.view.messages.event.EndTurnEventMsg;
import it.polimi.ingsw.cg28.view.messages.event.FilledMsg;
import it.polimi.ingsw.cg28.view.messages.event.GiveMeActionMsg;
import it.polimi.ingsw.cg28.view.messages.event.GiveMeBuyActionMsg;
import it.polimi.ingsw.cg28.view.messages.event.GiveMeSellActionMsg;
import it.polimi.ingsw.cg28.view.messages.event.PlayerStatusMsg;
import it.polimi.ingsw.cg28.view.messages.event.SimpleEventMsg;
import it.polimi.ingsw.cg28.view.messages.event.StartEventMsg;
import it.polimi.ingsw.cg28.view.messages.event.TableStatusMsg;
import it.polimi.ingsw.cg28.view.messages.event.WelcomeBazaarEventMsg;
import it.polimi.ingsw.cg28.view.messages.event.WelcomeEventMsg;

/**
 * Concrete visit strategy implementation of the ViewHandler interface.
 * Specifies how to handle {@link it.polimi.ingsw.cg28.view.messages.event.EventMsg} for a GUI client.
 * @author Mario, Alessandro
 * @implements ViewHandler
 */
public class GuiHandler implements ViewHandler{
	
	private static final Logger log = Logger.getLogger(Client.class.getName());
	private static final String EDT_EXCEPTION = "EDT Exception";
	
	private GuiManager manager;
	private GameBoardWindow gameBoardWindow;
	private ChatPanel chatPanel;
	private GuiClientFrame startFrame;
	private StartWindow settingWindow;
	private TurnActionMsg actionMsg;
	private String[] codes;
	private StringBuffer codeBuffer = new StringBuffer();
	
	/**
	 * Constructor of the class.
	 * @param startFrame - starting frame, if null it is ignored.
	 */
	public GuiHandler(GuiClientFrame startFrame){
		this.startFrame = startFrame;
	}
	
	/**
	 * {@inheritDoc}
	 * Creates a {@link StartWindow}.
	 */
	@Override
	public void handle(StartEventMsg startEventMsg) {
		settingWindow = new StartWindow(this);
		settingWindow.setVisible(true);
		settingWindow.setAlwaysOnTop(true);	
	}
	
	/**
	 * {@inheritDoc}
	 * It creates a {@link GameBoardWindow} and starts the music.
	 */
	@Override
	public void handle(WelcomeEventMsg welcomeEventMsg) {
		
		if(startFrame != null)
			startFrame.dispose();
		
		if(settingWindow != null)
			settingWindow.dispose();
		
		gameBoardWindow = new GameBoardWindow(this);
		gameBoardWindow.setTitle("PLAYER: "+manager.getPlayer().getName()+"  Council of four");
		gameBoardWindow.setVisible(true);
		gameBoardWindow.setAlwaysOnTop(true);
		gameBoardWindow.setAlwaysOnTop(false);
		gameBoardWindow.getMusicPlayer().playSound("src/music/theme.wav");
		chatPanel = gameBoardWindow.getChatPanel();
		
		Runnable target = new Runnable() {
			
			@Override
			public void run() {
				updateWindow(welcomeEventMsg.getTableStatus());

			}
		};

		try {
			SwingUtilities.invokeAndWait(target);
			
		} catch (InvocationTargetException e) {
			log.log(Level.WARNING, EDT_EXCEPTION, e);
		} catch (InterruptedException e) {
			log.log(Level.WARNING, EDT_EXCEPTION, e);
			Thread.currentThread().interrupt();
		}
		
	}
	
	/**
	 * {@inheritDoc}
	 * Update the {@link GameBoardWindow} with the new information from the table status.
	 */
	@Override
	public void handle(TableStatusMsg tableStatusMsg) {
		
		Runnable target = new Runnable() {
			
			@Override
			public void run() {
				updateWindow(tableStatusMsg);
			}
		};

		try {
			SwingUtilities.invokeAndWait(target);
			
		} catch (InvocationTargetException e) {
			log.log(Level.WARNING, EDT_EXCEPTION, e);
		} catch (InterruptedException e) {
			log.log(Level.WARNING, EDT_EXCEPTION, e);
			Thread.currentThread().interrupt();
		}
		
		
	}
	
	/**
	 * {@inheritDoc}
	 * Update the {@link GameBoardWindow} with the new information from the player status.
	 */
	@Override
	public void handle(PlayerStatusMsg playerStatusMsg) {
		Runnable target = new Runnable() {
			
			@Override
			public void run() {
				
				updateWindow(playerStatusMsg.getTableStatusMsg());
				updatePlayerInfo(playerStatusMsg);
				
			}
		};
		
		try {
			SwingUtilities.invokeAndWait(target);
		} catch (InvocationTargetException e) {
			log.log(Level.WARNING, EDT_EXCEPTION, e);
		} catch (InterruptedException e) {
			log.log(Level.WARNING, EDT_EXCEPTION, e);
			Thread.currentThread().interrupt();
		}
		
	}

	/**
	 * {@inheritDoc}
	 * Update the {@link ActionsPanel} with the information of the {@link GiveMeActionMsg}.
	 */
	@Override
	public void handle(GiveMeActionMsg giveMeActionMsg) {
		
		Runnable target = new Runnable() {
			
			@Override
			public void run() {
				if(giveMeActionMsg.canDoMainAction()){
					gameBoardWindow.getActionPanel().enableMainActionButtons(true);
				}
				else
				{
					gameBoardWindow.getActionPanel().getEndTurnActionButton().setVisible(true);
					gameBoardWindow.getActionPanel().getEndTurnActionButton().setEnabled(true);
				}
				if(giveMeActionMsg.canDoQuickAction()){
					gameBoardWindow.getActionPanel().enableQuickActionButtons(true);
				}
			}
		};
		
		try {
			SwingUtilities.invokeAndWait(target);
		} catch (InvocationTargetException e) {
			log.log(Level.WARNING, EDT_EXCEPTION, e);
		} catch (InterruptedException e) {
			log.log(Level.WARNING, EDT_EXCEPTION, e);
			Thread.currentThread().interrupt();
		}
		
		
	}

	
	/**
	 * {@inheritDoc}
	 * Saves the filledMsg in the variable actionMsg, gut the codeBuffer and call the GUiPainter 
	 * in case the filledMsg needs to be filled by the player.
	 */
	@Override
	public void handle(FilledMsg filledMsg) {
		
	Runnable target = new Runnable() {
			
			@Override
			public void run() {
				actionMsg =  (TurnActionMsg) filledMsg.getActionMsg();
				codeBuffer.delete(0, codeBuffer.length());
				
				if(actionMsg.getNeedCode()){
					
					codes = new String[actionMsg.getCodes().length];
					actionMsg.getShowChoices(new GuiPainter(gameBoardWindow));
				}
			}
		};
		
		try {
			SwingUtilities.invokeAndWait(target);
		} catch (InvocationTargetException e) {
			log.log(Level.WARNING, EDT_EXCEPTION, e);
		} catch (InterruptedException e) {
			log.log(Level.WARNING, EDT_EXCEPTION, e);
			Thread.currentThread().interrupt();
		}			
	}

	
	/**
	 * {@inheritDoc}
	 * It prints the strings of the simpleMsg received in the {@link HistoryPanel}.
	 */
	@Override
	public void handle(SimpleEventMsg simpleEventMsg) {
		
		Runnable target = new Runnable() {
			
			@Override
			public void run() {
				gameBoardWindow.getHistoryPanel().getHistoryTextArea().append(simpleEventMsg.getString()+"\n");
				}
		};
		
		try {
			SwingUtilities.invokeAndWait(target);
		} catch (InvocationTargetException e) {
			log.log(Level.WARNING, EDT_EXCEPTION, e);
		} catch (InterruptedException e) {
			log.log(Level.WARNING, EDT_EXCEPTION, e);
			Thread.currentThread().interrupt();
		}	
	}

	/**
	 * {@inheritDoc}
	 * Starts the bazaar.
	 */
	@Override
	public void handle(WelcomeBazaarEventMsg welcomeBazaarEventMsg) {

		Runnable target = new Runnable() {
			
			@Override
			public void run() {
				gameBoardWindow.getRulesPanel().getTownInformationPanel().setVisible(false);
				gameBoardWindow.getMapPanel().getBaazarPanel().startBaazar();
				gameBoardWindow.getMusicPlayer().stopMusic();
				gameBoardWindow.getMusicPlayer().playSound("src/music/bazaar.wav");
				}
		};	
		
		try {
			SwingUtilities.invokeAndWait(target);
		} catch (InvocationTargetException e) {
			log.log(Level.WARNING, EDT_EXCEPTION, e);
		} catch (InterruptedException e) {
			log.log(Level.WARNING, EDT_EXCEPTION, e);
			Thread.currentThread().interrupt();
		}	
	}

	/**
	 * {@inheritDoc}
	 * It updates the SellActionButtons using the information in {@link GiveMeSellActionMsg}.
	 */
	@Override
	public void handle(GiveMeSellActionMsg giveMeSellActionMsg) {
		
		Runnable target = new Runnable() {
			
			@Override
			public void run() {
				gameBoardWindow.getActionPanel().makeSellButtonsVisible(true);
				if(giveMeSellActionMsg.canSellAssistant())
					gameBoardWindow.getActionPanel().getSellAssistantButton().setEnabled(true);
				if(giveMeSellActionMsg.canSellPoliticCards())
					gameBoardWindow.getActionPanel().getSellPoliticCardButton().setEnabled(true);
				if(giveMeSellActionMsg.canSellPermitTiles())
					gameBoardWindow.getActionPanel().getSellTilesButton().setEnabled(true);
				}
		};	
		
		try {
			SwingUtilities.invokeAndWait(target);
		} catch (InvocationTargetException e) {
			log.log(Level.WARNING, EDT_EXCEPTION, e);
		} catch (InterruptedException e) {
			log.log(Level.WARNING, EDT_EXCEPTION, e);
			Thread.currentThread().interrupt();
		}	
		
	}
	
	/**
	 * {@inheritDoc}
	 * It updates the BuyActionButtons using the information in {@link GiveMeBuyActionMsg}.
	 */
	@Override
	public void handle(GiveMeBuyActionMsg giveMeBuyActionMsg) {
		
		Runnable target = new Runnable() {
			
			@Override
			public void run() {
			gameBoardWindow.getActionPanel().makeBuyButtonsVisible(true);
			}
		};	
		
		try {
			SwingUtilities.invokeAndWait(target);
		} catch (InvocationTargetException e) {
			log.log(Level.WARNING, EDT_EXCEPTION, e);
		} catch (InterruptedException e) {
			log.log(Level.WARNING, EDT_EXCEPTION, e);
			Thread.currentThread().interrupt();
		}	
		
	}

	/**
	 * {@inheritDoc}
	 * Ends the bazaar, closing all the bazaar related panels and restoring the normal ones.
	 */
	@Override
	public void handle(EndBazaarMsg endBazaarMsg) {
		Runnable target = new Runnable() {
			
			@Override
			public void run() {
				gameBoardWindow.getSubMapPanel().getSubBazaarLabel().setVisible(false);
				gameBoardWindow.getKingPanel().getApuLabel().setVisible(false);
				gameBoardWindow.getMapPanel().getBaazarPanel().setVisible(false);
				gameBoardWindow.getActionPanel().makeTurnActionButtonsVisible(true);
				gameBoardWindow.getHistoryPanel().getHistoryTextArea().append(endBazaarMsg.getTransactions());
				
				gameBoardWindow.getMusicPlayer().stopMusic();
				gameBoardWindow.getMusicPlayer().playSound("src/music/theme.wav");
				}
		};	
		
		try {
			SwingUtilities.invokeAndWait(target);
		} catch (InvocationTargetException e) {
			log.log(Level.WARNING, EDT_EXCEPTION, e);
		} catch (InterruptedException e) {
			log.log(Level.WARNING, EDT_EXCEPTION, e);
			Thread.currentThread().interrupt();
		}	
		
		
	}

	/**
	 * {@inheritDoc}
	 * Updates the shelves of the bazaar using the information of {@link BazaarStatusMsg}.
	 */
	@Override
	public void handle(BazaarStatusMsg bazaarStatusMsg) {
		
	Runnable target = new Runnable() {
			
			@Override
			public void run() {
				gameBoardWindow.getMapPanel().getBaazarPanel().getShelvesPanel().removeAll();
				gameBoardWindow.getMapPanel().getBaazarPanel().getShelvesPanel().repaint();
				gameBoardWindow.getMapPanel().getBaazarPanel().getShelvesPanel().revalidate();
				
				for(TProduct product: bazaarStatusMsg.getShelf()){
					gameBoardWindow.getMapPanel().getBaazarPanel().getShelvesPanel().add(new SingleShelfPanel(product));
					}
				}
		};	
		
		try {
			SwingUtilities.invokeAndWait(target);
		} catch (InvocationTargetException e) {
			log.log(Level.WARNING, EDT_EXCEPTION, e);
		} catch (InterruptedException e) {
			log.log(Level.WARNING, EDT_EXCEPTION, e);
			Thread.currentThread().interrupt();
		}	
		
		
	}

	/**
	 * {@inheritDoc}
	 * It prints the chatMsg string in the {@link ChatPanel}.
	 */
	@Override
	public void handle(ChatEventMsg chatEventMsg) {
		
		Runnable target = new Runnable() {
			
			@Override
			public void run() {
				
				chatPanel.getChatTextArea().append(chatEventMsg.getString()+"\n");
				chatPanel.repaint();
				chatPanel.revalidate();
				
				if(gameBoardWindow != null){
					String s = chatEventMsg.getString().substring(chatEventMsg.getString().lastIndexOf(":")+1);
					if(" Say my name".equals(s)){
						
						gameBoardWindow.getMusicPlayer().stopMusic();
						gameBoardWindow.getMusicPlayer().playSound("src/music/surprise.wav");
					}
				}
			}
		};
		
		try {
			SwingUtilities.invokeAndWait(target);
		} catch (InvocationTargetException e) {
			log.log(Level.WARNING, EDT_EXCEPTION, e);
		} catch (InterruptedException e) {
			log.log(Level.WARNING, EDT_EXCEPTION, e);
			Thread.currentThread().interrupt();
		}
			
	}

	/**
	 * It notifies the guiManager with the actionMsg stored.
	 * @param actionMsg
	 */
	public void notify(ActionMsg actionMsg) {
		manager.processRequest(actionMsg);
	}


	/**
	 * {@inheritDoc}
	 * It makes all the turnActionButtons disable.
	 */
	@Override
	public void handle(EndTurnEventMsg endTurnEventMsg) {
		gameBoardWindow.getActionPanel().getEndTurnActionButton().setVisible(false);
		gameBoardWindow.getActionPanel().getEndTurnActionButton().setEnabled(false);
		
	}

	/**
	 * {@inheritDoc}
	 * Makes the sellActionButtons disappear.
	 */
	@Override
	public void handle(EndSellTurnEventMsg endSellTurnEventMsg) {
		gameBoardWindow.getActionPanel().makeSellButtonsVisible(false);
		
	}

	/**
	 * {@inheritDoc}
	 * It makes the bazaar buttons disappear.
	 */
	@Override
	public void handle(EndBuyTurnEventMsg endBuyTurnEventMsg) {
		gameBoardWindow.getActionPanel().makeBuyButtonsVisible(false);
		gameBoardWindow.getActionPanel().enableMainActionButtons(false);
		gameBoardWindow.getActionPanel().enableQuickActionButtons(false);
		
	}
	
	/**
	 * {@inheritDoc} 
	 * Closes the {@link GameBoardWindow} and creates a {@link FinalWindow}.
	 */
	@Override
	public void handle(EndGameEventMsg endGameEventMsg) {
		FinalWindow finalWindow = new FinalWindow(endGameEventMsg);
		finalWindow.setVisible(true);
		gameBoardWindow.getMusicPlayer().stopMusic();
		gameBoardWindow.dispose();
	}
	

	/**
	 * @return the codes
	 */
	public String[] getCodes() {
		return codes;
	}

	/**
	 * @param codes the codes to set
	 */
	public void setCodes(String code,int index) {
		this.codes[index]=code;
	}

	/**
	 * @return the actionMsg
	 */
	public TurnActionMsg getActionMsg() {
		return actionMsg;
	}
	
	/**
	 * @param actionMsg the actionMsg to set
	 */
	public void setActionMsg(TurnActionMsg actionMsg) {
		this.actionMsg = actionMsg;
	}
	
	
	/**
	 * @return the codeBuffer
	 */
	public StringBuffer getCodeBuffer() {
		return codeBuffer;
	}
	
	
	/**
	 * Refresh the gameBoard Window.
	 * @param tableStatusMsg
	 */
	public void updateWindow(TableStatusMsg tableStatusMsg){
		
		gameBoardWindow.setPlayer(manager.getPlayer());
		gameBoardWindow.getPointsPanel().getInfoWindow().getOtherPlayersInfoPanel().removeAll();
		gameBoardWindow.getPointsPanel().getInfoWindow().getOtherPlayersInfoPanel().revalidate();
		gameBoardWindow.getPointsPanel().getInfoWindow().getOtherPlayersInfoPanel().repaint();
		
		
		for (TPlayer player : tableStatusMsg.getPlayers()) {
			gameBoardWindow.getPointsPanel().getInfoWindow().addPlayerPanel(new PlayerInfoPanel(player));
		}
		
		
		//faccio scomparire bottone finisci turno
		if(!tableStatusMsg.getCurrentTurnPlayer().equals(manager.getPlayer().toString())){
			gameBoardWindow.getActionPanel().getEndTurnActionButton().setVisible(false);
		}
		

		for(int i=0;i<4;i++)
		{
			for(int j=0;j<4;j++){
				Color color = tableStatusMsg.getBalcony()[i].getCouncillorColor(3-j);
				gameBoardWindow.getSubMapPanel().getSubMapLabel().getBalconyLabel(i+1).getCouncillor(j+1).setCouncillorIcon(color);
			}
		}
		
		gameBoardWindow.getHistoryPanel().getHistoryTextArea().append("Turn player: " + tableStatusMsg.getCurrentTurnPlayer() +"\n");
		
		for(int i=0;i<6;i++) //scorro carte scoperte
		{	
			//salvo i bonus
			String[] bonuses = tableStatusMsg.getRegions()[i/2].getUncovered()[i%2].getBonuses().getBonusCode();
			gameBoardWindow.getSubMapPanel().getSubMapLabel().
					getBusinessPermitTileLabel(i).setBonuses(bonuses);

			//salvo le iniziali
			gameBoardWindow.getSubMapPanel().getSubMapLabel().
				getBusinessPermitTileLabel(i).setCardText(tableStatusMsg.getRegions()[i/2].getUncovered()[i%2].getLetterCodes());;
				
		}	
		
		
		for(int i =1;i<=3;i++){
			gameBoardWindow.getMapPanel().getRegionBonus(i).setVisible(false);
			
			for(int j=0;j < tableStatusMsg.getBonusTiles().length;j++)
			{
				if(tableStatusMsg.getBonusTiles()[j].getIdentifier().equals(tableStatusMsg.getRegions()[i-1].getRegionType()))
					gameBoardWindow.getMapPanel().getRegionBonus(i).setVisible(true);
			}
		}
		
		for(int i =1;i<=4;i++){
			gameBoardWindow.getKingPanel().getRewardTile(i).setVisible(false);
			
			for(int j=0;j < tableStatusMsg.getBonusTiles().length;j++){
				switch(tableStatusMsg.getBonusTiles()[j].getIdentifier())	{
					case "Gold":   gameBoardWindow.getKingPanel().getRewardTile(i).setVisible(true);
						break;
					case "Silver": gameBoardWindow.getKingPanel().getRewardTile(i).setVisible(true);
						break;
					case "Bronze": gameBoardWindow.getKingPanel().getRewardTile(i).setVisible(true);
						break;
					case "Iron": gameBoardWindow.getKingPanel().getRewardTile(i).setVisible(true);
						break;
					default: 
						break;
				}
				
			}
				
		}
		
		String[] codes = tableStatusMsg.getKingRewards()[0].getBonusCode()[0].split(":");
			
			switch(codes[1]){
				case "25":  gameBoardWindow.getKingPanel().getKingTile().setIcon(new ImageIcon("src/img/bonuses/rewards/kingReward1.png"));
					break;
				case "18" : gameBoardWindow.getKingPanel().getKingTile().setIcon(new ImageIcon("src/img/bonuses/rewards/kingReward2.png")); 
					break;
				case "12" : gameBoardWindow.getKingPanel().getKingTile().setIcon(new ImageIcon("src/img/bonuses/rewards/kingReward3.png")); 
					break;
		 		case "7" :  gameBoardWindow.getKingPanel().getKingTile().setIcon(new ImageIcon("src/img/bonuses/rewards/kingReward4.png")); 
					break;
				case "3" :  gameBoardWindow.getKingPanel().getKingTile().setIcon(new ImageIcon("src/img/bonuses/rewards/kingReward5.png")); 
					break;
				default: 
					break;
					
			}
		
		for(TTown town: tableStatusMsg.getTowns()){
			if(town.isKingHere()){
				gameBoardWindow.getMapPanel().getKingCrownLabel().moveKing(town.getName());
				gameBoardWindow.getMapPanel().getKingCrownLabel().repaint();
			}
		}
		
		gameBoardWindow.getMapPanel().setStaticTowns(tableStatusMsg.getTowns()); 
		
	}

	
	/**
	 * Refresh the window with the {@link PlayerStatusMsg} informations.
	 * @param playerStatusMsg
	 */
	public void updatePlayerInfo(PlayerStatusMsg playerStatusMsg){
			
			gameBoardWindow.getActionPanel().enableMainActionButtons(false);
			gameBoardWindow.getActionPanel().enableQuickActionButtons(false);
			gameBoardWindow.setPlayer(playerStatusMsg.getPlayer());
			
			gameBoardWindow.getPointsPanel().getCoinsProgressBar().setString(String.valueOf(playerStatusMsg.getTPlayer().getCoins()));
			gameBoardWindow.getPointsPanel().getAssistantProgressBar().setValue(playerStatusMsg.getTPlayer().getAssistants());
			gameBoardWindow.getPointsPanel().getAssistantProgressBar().setString(String.valueOf(playerStatusMsg.getTPlayer().getAssistants()));
			gameBoardWindow.getPointsPanel().getNobilityProgressBar().setValue(playerStatusMsg.getTPlayer().getNobilityRank());
			gameBoardWindow.getPointsPanel().getNobilityProgressBar().setString(String.valueOf(playerStatusMsg.getTPlayer().getNobilityRank()));
			gameBoardWindow.getPointsPanel().getVictoryProgressBar().setValue(playerStatusMsg.getTPlayer().getVictoryPoints());
			gameBoardWindow.getPointsPanel().getVictoryProgressBar().setString(String.valueOf(playerStatusMsg.getTPlayer().getVictoryPoints()));
			
			gameBoardWindow.getRulesPanel().getMyPoliticCardsPanel().removeAll();
			gameBoardWindow.getRulesPanel().getMyPoliticCardsPanel().revalidate();
			gameBoardWindow.getRulesPanel().getMyPoliticCardsPanel().repaint();
			Color[] cards = playerStatusMsg.getTPlayer().getPoliticCardsHand();
			for(int i = 0;i<cards.length;i++){
				gameBoardWindow.getRulesPanel().getMyPoliticCardsPanel().add
					(new PoliticCardLabel(cards[i]));
			}
			
			gameBoardWindow.getRulesPanel().getMyBusinessPermitTilesPanel().removeAll();
			gameBoardWindow.getRulesPanel().getMyBusinessPermitTilesPanel().revalidate();
			gameBoardWindow.getRulesPanel().getMyBusinessPermitTilesPanel().repaint();
			List<TBusinessPermitTile> tiles = playerStatusMsg.getTPlayer().getPossessedTiles();
			for (TBusinessPermitTile tBusinessPermitTile : tiles) {
				gameBoardWindow.getRulesPanel().getMyBusinessPermitTilesPanel().add
					(new BusinessPermitTileLabel(tBusinessPermitTile,0));
			}
			
	}
	
	/**
	 * {@inheritDoc} 
	 * Creates a new GUIManager.
	 */
	@Override
	public void initialize(RequestHandler requestHandler, PlayerID player) {
			
		manager = new GuiManager(requestHandler, player);
        
        if (startFrame != null){
	        chatPanel = startFrame.startChat(manager);
        }
		
	}

	public GuiManager getGuiManager() {
		return manager;
	}

	
}
