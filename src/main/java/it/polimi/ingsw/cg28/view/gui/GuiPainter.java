/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui;

import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import it.polimi.ingsw.cg28.view.Painter;
import it.polimi.ingsw.cg28.view.gui.listeners.NamePlateLabelListener;
import it.polimi.ingsw.cg28.view.gui.windows.GameBoardWindow;
import it.polimi.ingsw.cg28.view.gui.windows.bazaar.SingleShelfPanel;
import it.polimi.ingsw.cg28.view.gui.windows.others.BusinessTileChoiceWindow;
import it.polimi.ingsw.cg28.view.gui.windows.others.CouncillorChoiceWindow;
import it.polimi.ingsw.cg28.view.gui.windows.others.PoliticCardsChoiceWindow;
import it.polimi.ingsw.cg28.view.messages.action.bazaar.BuySalableActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.bazaar.SellAssistantsActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.bazaar.SellPermitTilesActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.bazaar.SellPoliticCardsActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.bonus.ReuseCityBonusActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.bonus.ReusePermitBonusActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.bonus.TakePermitTileBonusActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.ChangeTileActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.ElectionActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.EmporiumKingActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.EmporiumTileActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.HireAssistantActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.OneMoreMainActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.PermitTileActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.SendAssistantActionMsg;

/**
 * Concrete visit strategy implementation of the Painter interface.
 * Specifies how to "paint" TurnActionMsgs for a GUI client.
 * @author Alessandro
 * @implements ViewHandler
 */
public class GuiPainter implements Painter{

	private GameBoardWindow gameBoardWindow;
	
	/**
	 * Constructor of the class.
	 * @param gameBoardWindow {@link GameBoardWindow}
	 */
	public GuiPainter(GameBoardWindow gameBoardWindow) {
		this.gameBoardWindow = gameBoardWindow;
	}

	/**
	 * {@inheritDoc} 
	 * It makes the communicationTextArea visible showing a msg related to the msg painted.
	 */
	@Override
	public void paint(ChangeTileActionMsg changeTileActionMsg) {
		gameBoardWindow.getRulesPanel().getComunicationLabel().setVisible(true);
		gameBoardWindow.getRulesPanel().getComunicationLabel().getComunicationTextArea().setText("");
		gameBoardWindow.getRulesPanel().getComunicationLabel().getComunicationTextArea().setText("Click on the deck of the region where you want to change the tiles");
		
	}

	/**
	 * {@inheritDoc} 
	 * It makes a {@link PoliticCardsChoiceWindow} (filled with the cards of the {@link EmporiumKingActionMsg}) 
	 * appear and set the chosen town in the {@link GameBoardWindow} with the list of the {@link EmporiumKingActionMsg}.
	 */
	@Override
	public void paint(EmporiumKingActionMsg emporiumKingActionMsg) {
		PoliticCardsChoiceWindow politicCardsChoiceWindow = new PoliticCardsChoiceWindow(emporiumKingActionMsg.getPoliticCards(),gameBoardWindow);	
		politicCardsChoiceWindow.setVisible(true);
		gameBoardWindow.getMapPanel().setChosenTowns(emporiumKingActionMsg.getMap().getTownList());
	}

	/**
	 * {@inheritDoc} 
	 * It makes a {@link CouncillorChoiceWindow} filled with the councillor of the {@link ElectionActionMsg}.
	 */
	@Override
	public void paint(ElectionActionMsg electionActionMsg) {
		CouncillorChoiceWindow councillorChoiceWindow = new CouncillorChoiceWindow(electionActionMsg.getPool().getPool(),gameBoardWindow);
		councillorChoiceWindow.setVisible(true);
		
	}

	/**
	 * {@inheritDoc} 
	 * It makes a {@link BusinessTileChoiceWindow} appear (filled with the tiles of the 
	 * {@link EmporiumTileActionMsg}, and set the chosen town in the {@link GameBoardWindow}
	 *  with the list of the {@link EmporiumTileActionMsg}.
	 */
	@Override
	public void paint(EmporiumTileActionMsg emporiumTileActionMsg) {
		BusinessTileChoiceWindow choiceWindow = new BusinessTileChoiceWindow(emporiumTileActionMsg.getTiles(), gameBoardWindow,false);
		choiceWindow.setVisible(true);
		gameBoardWindow.getMapPanel().setChosenTowns(emporiumTileActionMsg.getTowns());
	}

	/**
	 * {@inheritDoc}. Nothing to paint.
	 */
	@Override
	public void paint(HireAssistantActionMsg hireAssistantActionMsg) {
		return;
	}

	/**
	 * {@inheritDoc}. Nothing to paint.
	 */
	@Override
	public void paint(OneMoreMainActionMsg oneMoreMainActionMsg) {
		return;
	}

	/**
	 * {@inheritDoc} 
	 * It makes a {@link PoliticCardsChoiceWindow} appear with the politic cards filled in the {@link PermitTileActionMsg}.
	 */
	@Override
	public void paint(PermitTileActionMsg permitTileActionMsg) {
		PoliticCardsChoiceWindow politicCardsChoiceWindow = new PoliticCardsChoiceWindow(permitTileActionMsg.getPoliticCards(),gameBoardWindow);
		politicCardsChoiceWindow.setVisible(true);
	}


	/**
	 * {@inheritDoc} 
	 * It makes a {@link CouncillorChoiceWindow} filled with the councillor of the {@link SendAssistantActionMsg}.
	 */
	@Override
	public void paint(SendAssistantActionMsg sendAssistantActionMsg) {
		CouncillorChoiceWindow choiceWindow = new CouncillorChoiceWindow(sendAssistantActionMsg.getPool().getPool(),gameBoardWindow);
		choiceWindow.setVisible(true);
		
	}

	/**
	 * It sets the chosen towns in the {@link GameBoardWindow} and makes the communicationLabel visible with an appropriate msg.
	 */
	@Override
	public void paint(ReuseCityBonusActionMsg reuseCityBonusActionMsg) {
			gameBoardWindow.getMapPanel().setChosenTowns(reuseCityBonusActionMsg.getTowns());
			gameBoardWindow.getRulesPanel().getComunicationLabel().setVisible(true);
			gameBoardWindow.getRulesPanel().getComunicationLabel().getComunicationTextArea().setText("");
			gameBoardWindow.getRulesPanel().getComunicationLabel().getComunicationTextArea().setText("Click on the city you want to rerive the bonus of");
	
	}

	
	/**
	 * {@inheritDoc} 
	 * It makes a {@link BusinessTileChoiceWindow} appear (filled with the tiles of the {@link ReusePermitBonusActionMsg}).
	 */
	@Override
	public void paint(ReusePermitBonusActionMsg reusePermitBonusActionMsg) {
			BusinessTileChoiceWindow choiceWindow = new BusinessTileChoiceWindow(reusePermitBonusActionMsg.getTiles(), gameBoardWindow, true);
			choiceWindow.setVisible(true);
	}

	/**
	 * {@inheritDoc} 
	 * It repaints the shelf of the bazaar with the products you can buy and it adds the relates NamePlateListener,
	 * them it also opens and dill the communicationLabel.
	 */
	@Override
	public void paint(BuySalableActionMsg buySalableActionMsg) {
		gameBoardWindow.getMapPanel().getBaazarPanel().getShelvesPanel().removeAll();
		gameBoardWindow.getMapPanel().getBaazarPanel().getShelvesPanel().repaint();
		gameBoardWindow.getMapPanel().getBaazarPanel().getShelvesPanel().revalidate();
		
		for(int i = 0; i< buySalableActionMsg.getShelf().size();i++){
			
			SingleShelfPanel shelf = new SingleShelfPanel(buySalableActionMsg.getShelf().get(i));
			shelf.getNamePlateLabel().addMouseListener(new NamePlateLabelListener(gameBoardWindow, i+1));
			gameBoardWindow.getMapPanel().getBaazarPanel().getShelvesPanel().add(shelf);
			
		}
		
		gameBoardWindow.getRulesPanel().getComunicationLabel().setVisible(true);
		gameBoardWindow.getRulesPanel().getComunicationLabel().getComunicationTextArea().setText("");
		gameBoardWindow.getRulesPanel().getComunicationLabel().getComunicationTextArea().setText("Click on the namePlate of the shelf where are the products you want to buy");
		
	}

	/**
	 * {@inheritDoc} 
	 * It opens a inputDialog asking the number of assistants you want to sell
	 * and the price, then it sets the codes of the {@link SellAssistantsActionMsg} in the gui handler and notifies it.
	 */
	@Override
	public void paint(SellAssistantsActionMsg sellAssistantsActionMsg) {
			
			ImageIcon coin = new ImageIcon("src/img/bonuses/coin.png");
			ImageIcon assistant = new ImageIcon("src/img/bonuses/assistant.png");
			String numberOfAssistants = (String) JOptionPane.showInputDialog(null, "How many assistants do you want to sell?","Bazaar",
					JOptionPane.INFORMATION_MESSAGE, assistant, null,"");
			gameBoardWindow.getGuiHandler().setCodes(numberOfAssistants, 0);
			String price = (String) JOptionPane.showInputDialog(null, "Insert the price","Bazaar",
					JOptionPane.INFORMATION_MESSAGE, coin, null,"");
			gameBoardWindow.getGuiHandler().setCodes(price,1);
			gameBoardWindow.getGuiHandler().getActionMsg().setCodes(gameBoardWindow.getGuiHandler().getCodes());
			gameBoardWindow.getGuiHandler().notify(gameBoardWindow.getGuiHandler().getActionMsg());
			
	}

	/**
	 * {@inheritDoc} 
	 * It makes a {@link BusinessTileChoiceWindow} appear (filled with the tiles of the {@link SellPermitTilesActionMsg}).
	 */
	@Override
	public void paint(SellPermitTilesActionMsg sellPermitTilesActionMsg) {
		BusinessTileChoiceWindow choiceWindow = new BusinessTileChoiceWindow(Arrays.asList(sellPermitTilesActionMsg.getTiles()), gameBoardWindow, true);
		choiceWindow.setVisible(true);
		
	}

	/**
	 * {@inheritDoc} 
	 * It makes a {@link PoliticCardsChoiceWindow} appear with the politic cards filled in the {@link SellPoliticCardsActionMsg}.
	 */
	@Override
	public void paint(SellPoliticCardsActionMsg sellPoliticCardsActionMsg) {
		PoliticCardsChoiceWindow politicCardsChoiceWindow = new PoliticCardsChoiceWindow(sellPoliticCardsActionMsg.getPoliticCards(),gameBoardWindow);
		politicCardsChoiceWindow.setVisible(true);
		
	}
	
	/**
	 * {@inheritDoc} 
	 * It makes the communicationTextArea visible showing a msg related to the msg painted.
	 */
	@Override
	public void paint(TakePermitTileBonusActionMsg takePermitTileBonusActionMsg) {
		gameBoardWindow.getRulesPanel().getComunicationLabel().setVisible(true);
		gameBoardWindow.getRulesPanel().getComunicationLabel().getComunicationTextArea().setText("");
		gameBoardWindow.getRulesPanel().getComunicationLabel().getComunicationTextArea().setText("Click on the tile you want to get");

		
	}
	

}
