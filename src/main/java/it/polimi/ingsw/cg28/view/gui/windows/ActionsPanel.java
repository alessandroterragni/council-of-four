/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.windows;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLayeredPane;

import it.polimi.ingsw.cg28.view.gui.listeners.actionbuttons.BuyActionButtonListener;
import it.polimi.ingsw.cg28.view.gui.listeners.actionbuttons.ChangeTileActionButtonListener;
import it.polimi.ingsw.cg28.view.gui.listeners.actionbuttons.ElectionActionButtonListener;
import it.polimi.ingsw.cg28.view.gui.listeners.actionbuttons.EmporiumKingActionButtonListener;
import it.polimi.ingsw.cg28.view.gui.listeners.actionbuttons.EmporiumTileActionButtonListener;
import it.polimi.ingsw.cg28.view.gui.listeners.actionbuttons.EndBuyTurnActionButtonListener;
import it.polimi.ingsw.cg28.view.gui.listeners.actionbuttons.EndSellTurnActionButtonListsner;
import it.polimi.ingsw.cg28.view.gui.listeners.actionbuttons.EndTurnButtonListener;
import it.polimi.ingsw.cg28.view.gui.listeners.actionbuttons.HireAssistantActionButtonListener;
import it.polimi.ingsw.cg28.view.gui.listeners.actionbuttons.OneMoreMainActionButtonListener;
import it.polimi.ingsw.cg28.view.gui.listeners.actionbuttons.PermitTileActionButtonListener;
import it.polimi.ingsw.cg28.view.gui.listeners.actionbuttons.SellAssistantActionButtonListener;
import it.polimi.ingsw.cg28.view.gui.listeners.actionbuttons.SellPoliticCardButtonListener;
import it.polimi.ingsw.cg28.view.gui.listeners.actionbuttons.SellTileActionButtonListener;
import it.polimi.ingsw.cg28.view.gui.listeners.actionbuttons.SendAssistantActionButtonListener;

/**
 * Panel for the actions buttons : every action buttons is created here and the related listener are added.
 * @author Alessandro
 *
 */
public class ActionsPanel extends JLayeredPane {
	
	private static final long serialVersionUID = -2377742772857361447L;
	
	private JButton electionActionButton;
	private JButton emporiumTileActionButton;
	private JButton hireAssistantActionButton;
	private JButton sendAssistantActionButton;
	private JButton emporiumKingActionButton;
	private JButton permitTileActionButton;
	private JButton changeTileActionButton;
	private JButton oneMoreMainActionButton;
	private JButton endTurnActionButton;

	private JButton sellAssistantButton;
	private JButton sellPoliticCardButton;
	private JButton sellTilesButton;
	private JButton endSellTurnButton;
	
	private JButton buyButton;
	private JButton endBuyButton;
	
	/**
	 * Constructor of the class.
	 * @param gameBoardWindow {@link GameBoardWindow}
	 */
	public ActionsPanel(GameBoardWindow gameBoardWindow) {
		super();
		
		this.setBounds(0, 487, 325, 174);
		this.setLayout(null);
		
		ImageIcon img1 = new ImageIcon("src/img/actionButtons/electCouncillors.png");
		electionActionButton = new JButton(img1);
		electionActionButton.setBounds(0, 0, 163, 44);
		electionActionButton.setOpaque(false);
		electionActionButton.setContentAreaFilled(false);
		electionActionButton.setBorderPainted(false);
		this.add(electionActionButton,new Integer(0));
		electionActionButton.addMouseListener(new ElectionActionButtonListener(gameBoardWindow));
		
		ImageIcon img2 = new ImageIcon("src/img/actionButtons/buildEmporiumTile.png");
		emporiumTileActionButton = new JButton(img2);
		emporiumTileActionButton.setBounds(0, 43, 163, 44);
		emporiumTileActionButton.setOpaque(false);
		emporiumTileActionButton.setContentAreaFilled(false);
		emporiumTileActionButton.setBorderPainted(false);
		this.add(emporiumTileActionButton,new Integer(0));
		emporiumTileActionButton.addMouseListener(new EmporiumTileActionButtonListener(gameBoardWindow));
		
		ImageIcon img3 = new ImageIcon("src/img/actionButtons/engageAssistant.png");
		hireAssistantActionButton = new JButton(img3);
		hireAssistantActionButton.setBounds(0, 87, 163, 44);
		hireAssistantActionButton.setOpaque(false);
		hireAssistantActionButton.setContentAreaFilled(false);
		hireAssistantActionButton.setBorderPainted(false);
		this.add(hireAssistantActionButton,new Integer(0));
		hireAssistantActionButton.addMouseListener(new HireAssistantActionButtonListener(gameBoardWindow));
		
		ImageIcon img4 = new ImageIcon("src/img/actionButtons/changeTiles.png");
        changeTileActionButton = new JButton(img4);
		changeTileActionButton.setBounds(0, 129, 163, 44);
		changeTileActionButton.setOpaque(false);
		changeTileActionButton.setContentAreaFilled(false);
		changeTileActionButton.setBorderPainted(false);
		this.add(changeTileActionButton,new Integer(0));
		changeTileActionButton.addMouseListener(new ChangeTileActionButtonListener(gameBoardWindow));
		
		ImageIcon img5 = new ImageIcon("src/img/actionButtons/buildEmporiumKing.png");
		emporiumKingActionButton = new JButton(img5);
		emporiumKingActionButton.setBounds(162, 0, 163, 44);
		emporiumKingActionButton.setOpaque(false);
		emporiumKingActionButton.setContentAreaFilled(false);
		emporiumKingActionButton.setBorderPainted(false);
		this.add(emporiumKingActionButton,new Integer(0));
		emporiumKingActionButton.addMouseListener(new EmporiumKingActionButtonListener(gameBoardWindow));
		
		ImageIcon img6 = new ImageIcon("src/img/actionButtons/buyTile.png");
		permitTileActionButton = new JButton(img6);
		permitTileActionButton.setBounds(162, 43, 163, 44);
		permitTileActionButton.setOpaque(false);
		permitTileActionButton.setContentAreaFilled(false);
		permitTileActionButton.setBorderPainted(false);
		this.add(permitTileActionButton,new Integer(0));
		permitTileActionButton.addMouseListener(new PermitTileActionButtonListener(gameBoardWindow));
		
		ImageIcon img7 = new ImageIcon("src/img/actionButtons/sendAssistant.png");
        sendAssistantActionButton = new JButton(img7);
		sendAssistantActionButton.setBounds(162, 87, 163, 44);
		sendAssistantActionButton.setOpaque(false);
		sendAssistantActionButton.setContentAreaFilled(false);
		sendAssistantActionButton.setBorderPainted(false);
		this.add(sendAssistantActionButton,new Integer(0));
		sendAssistantActionButton.addMouseListener(new SendAssistantActionButtonListener(gameBoardWindow));
		
		ImageIcon img8 = new ImageIcon("src/img/actionButtons/oneMoreMainAction.png");
        oneMoreMainActionButton = new JButton(img8);
		oneMoreMainActionButton.setBounds(162, 129, 163, 44);
		oneMoreMainActionButton.setOpaque(false);
		oneMoreMainActionButton.setContentAreaFilled(false);
		oneMoreMainActionButton.setBorderPainted(false);
		this.add(oneMoreMainActionButton,new Integer(0));
		oneMoreMainActionButton.addMouseListener(new OneMoreMainActionButtonListener(gameBoardWindow));
		
		ImageIcon img9 = new ImageIcon("src/img/actionButtons/endTurnButton.png");
        endTurnActionButton = new JButton(img9);
		endTurnActionButton.setBounds(0, 0, 325, 86);
		endTurnActionButton.setOpaque(false);
		endTurnActionButton.setContentAreaFilled(false);
		endTurnActionButton.setBorderPainted(false);
		endTurnActionButton.setVisible(false);
		this.add(endTurnActionButton,new Integer(1));
		endTurnActionButton.addMouseListener(new EndTurnButtonListener(gameBoardWindow));
		
		enableMainActionButtons(false);
		enableQuickActionButtons(false);
		
		ImageIcon img10 = new ImageIcon("src/img/actionButtons/sellAssistants.png");
		sellAssistantButton = new JButton(img10);
		sellAssistantButton.setBounds(0, 0, 163, 86);
		sellAssistantButton.setOpaque(false);
		sellAssistantButton.setContentAreaFilled(false);
		sellAssistantButton.setBorderPainted(false);
		sellAssistantButton.setVisible(false);
		this.add(sellAssistantButton,new Integer(3));
		sellAssistantButton.addMouseListener(new SellAssistantActionButtonListener(gameBoardWindow));
		
		ImageIcon img11 = new ImageIcon("src/img/actionButtons/sellCards.png");
		sellPoliticCardButton = new JButton(img11);
		sellPoliticCardButton.setBounds(162, 0, 163, 86);
		sellPoliticCardButton.setOpaque(false);
		sellPoliticCardButton.setContentAreaFilled(false);
		sellPoliticCardButton.setBorderPainted(false);
		sellPoliticCardButton.setVisible(false);
		this.add(sellPoliticCardButton,new Integer(3));
		sellPoliticCardButton.addMouseListener(new SellPoliticCardButtonListener(gameBoardWindow));
		
		ImageIcon img12 = new ImageIcon("src/img/actionButtons/sellTiles.png");
		sellTilesButton = new JButton(img12);
		sellTilesButton.setBounds(0, 87, 163, 86);
		sellTilesButton.setOpaque(false);
		sellTilesButton.setContentAreaFilled(false);
		sellTilesButton.setBorderPainted(false);
		sellTilesButton.setVisible(false);
		this.add(sellTilesButton,new Integer(3));
		sellTilesButton.addMouseListener(new SellTileActionButtonListener(gameBoardWindow));
		
		ImageIcon img13 = new ImageIcon("src/img/actionButtons/endSellTurn.png");
		endSellTurnButton = new JButton(img13);
		endSellTurnButton.setBounds(162, 87, 163, 86);
		endSellTurnButton.setOpaque(false);
		endSellTurnButton.setContentAreaFilled(false);
		endSellTurnButton.setBorderPainted(false);
		endSellTurnButton.setVisible(false);
		this.add(endSellTurnButton,new Integer(3));
		endSellTurnButton.addMouseListener(new EndSellTurnActionButtonListsner(gameBoardWindow));
		
		ImageIcon img14 = new ImageIcon("src/img/actionButtons/buy.png");
		buyButton = new JButton(img14);
		buyButton.setBounds(0, 0, 163, 172);
		buyButton.setOpaque(false);
		buyButton.setContentAreaFilled(false);
		buyButton.setBorderPainted(false);
		buyButton.setVisible(false);
		this.add(buyButton, new Integer(3));
		buyButton.addMouseListener(new BuyActionButtonListener(gameBoardWindow));
		
		ImageIcon img15 = new ImageIcon("src/img/actionButtons/endBuyTurn.png");
		endBuyButton = new JButton(img15);
		endBuyButton.setBounds(162, 0, 163, 172);
		endBuyButton.setOpaque(false);
		endBuyButton.setContentAreaFilled(false);
		endBuyButton.setBorderPainted(false);
		endBuyButton.setVisible(false);
		this.add(endBuyButton, new Integer(3));
		endBuyButton.addMouseListener(new EndBuyTurnActionButtonListener(gameBoardWindow));
	}
	
	/**
	 * It enables or disables all the mainActionButtons depending on the boolean passed.
	 * @param b - true if you want to enable the buttons, false otherwise
	 */
	public void enableMainActionButtons(boolean b){
		electionActionButton.setEnabled(b);
		emporiumKingActionButton.setEnabled(b);
		emporiumTileActionButton.setEnabled(b);
		permitTileActionButton.setEnabled(b);
		
	}
	
	/**
	 * It enables or disables all the quick.
	 * ActionButtons depending on the boolean passed
	 * @param b - true if you want to enable the buttons, false otherwise
	 */
	public void enableQuickActionButtons(boolean b){
		hireAssistantActionButton.setEnabled(b);
		changeTileActionButton.setEnabled(b);
		sendAssistantActionButton.setEnabled(b);;
		oneMoreMainActionButton.setEnabled(b);
	
	}

	/**
	 * Getter of the endTurnActionButton.
	 * @return the endTurnActionButton
	 */
	public JButton getEndTurnActionButton() {
		return endTurnActionButton;
	}
	
	/**
	 * It set visible or invisible all the TurnActionButtons depending on the boolean passed.
	 * @param b - true if you want to make the buttons visible, false otherwise
	 */
	public void makeTurnActionButtonsVisible(boolean b){
		electionActionButton.setVisible(b);
		emporiumKingActionButton.setVisible(b);
		emporiumTileActionButton.setVisible(b);
		permitTileActionButton.setVisible(b);
		hireAssistantActionButton.setVisible(b);
		changeTileActionButton.setVisible(b);
		sendAssistantActionButton.setVisible(b);;
		oneMoreMainActionButton.setVisible(b);
		
	}
		
	/**
	 * It set visible or invisible all the SellButtons depending on the boolean passed.
	 * @param b  - true if you want to make the buttons visible, false otherwise
	 */
	public void makeSellButtonsVisible(boolean b){
		makeTurnActionButtonsVisible(false);
		sellAssistantButton.setVisible(b);
		sellAssistantButton.setEnabled(false);
		sellPoliticCardButton.setVisible(b);
		sellPoliticCardButton.setEnabled(false);
		sellTilesButton.setVisible(b);
		sellTilesButton.setEnabled(false);
		endSellTurnButton.setVisible(b);
	}
	
	/**
	 * It set visible or invisible all the BuyButtons depending on the boolean passed.
	 * @param b - true if you want to make the buttons visible, false otherwise
	 */
	public void makeBuyButtonsVisible(boolean b){
		buyButton.setVisible(b);
		endBuyButton.setVisible(b);
	}

	/**
	 * Getter of the SellassistantButton.
	 * @return the sellAssistantButton
	 */
	public JButton getSellAssistantButton() {
		return sellAssistantButton;
	}

	/**
	 * Getter of the SellPoliticCardButton.
	 * @return the sellPoliticCardButton
	 */
	public JButton getSellPoliticCardButton() {
		return sellPoliticCardButton;
	}

	/**
	 * Getter of the sellTileButton.
	 * @return the sellTilesButton
	 */
	public JButton getSellTilesButton() {
		return sellTilesButton;
	}

	/**
	 * Getter of the buyButton.
	 * @return the buyButton
	 */
	public JButton getBuyButton() {
		return buyButton;
	}

	/**
	 * Getter of the endBuyButton.
	 * @return the endBuyButton
	 */
	public JButton getEndBuyButton() {
		return endBuyButton;
	}
	
	
	
	
}
