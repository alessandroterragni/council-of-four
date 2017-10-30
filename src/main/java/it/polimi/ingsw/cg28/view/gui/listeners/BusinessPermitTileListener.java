/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import it.polimi.ingsw.cg28.view.gui.windows.GameBoardWindow;
import it.polimi.ingsw.cg28.view.gui.windows.submap.TileLetterTextLabel;
import it.polimi.ingsw.cg28.view.messages.action.bonus.TakePermitTileBonusActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.PermitTileActionMsg;

/**
 * listener for the permit Tile
 * @author Alessandro
 *
 */
public class BusinessPermitTileListener extends MouseAdapter{

	private GameBoardWindow window;
	
	/**
	 * constructor of the class
	 * @param window the GameBoardWindow
	 */
	public BusinessPermitTileListener(GameBoardWindow window) {
		this.window = window;
	}
	
	/**
	 * when clicked, it fills and sends a PermitTileActionMsg or a TakePemitTileActionMsg, depending one the action msg in the gui handler
	 * in addition, it also closes the communication label 
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		
		if(window.getGuiHandler().getActionMsg() instanceof PermitTileActionMsg)
		{
			TileLetterTextLabel tileText = (TileLetterTextLabel) e.getSource();
			
			int position = tileText.getTileLabel().getPosition();
			
			window.getGuiHandler().setCodes(Integer.toString((position-1)/2+1),0); //assegno regione
			
			//assegno carta, pero' devo assegnare solo 1 o 2, quindi devo vedere se la posizione è pari o dispari
			
			if((position-1)%2==0){
					window.getGuiHandler().setCodes("1", 1);}  
			if((position-1) %2==1)
					window.getGuiHandler().setCodes("2", 1);
			
			sendAndClear();
		}
		
		if(window.getGuiHandler().getActionMsg() instanceof TakePermitTileBonusActionMsg)
		{
			if(window.getGuiHandler().getCodeBuffer().length()!=window.getGuiHandler().getActionMsg().getCodes().length){
				TileLetterTextLabel tileText = (TileLetterTextLabel) e.getSource();
				int position = tileText.getTileLabel().getPosition();
				window.getGuiHandler().getCodeBuffer().append(Integer.toString(position));
			}
			if(window.getGuiHandler().getCodeBuffer().length()==window.getGuiHandler().getActionMsg().getCodes().length){
				String[] codes = window.getGuiHandler().getCodeBuffer().toString().split("");
				
				for(int i =0;i<codes.length;i++){
					window.getGuiHandler().setCodes(codes[i], i);
				}
				
				sendAndClear();
				window.getGuiHandler().getCodeBuffer().delete(0,window.getGuiHandler().getCodeBuffer().length());
			}

		}
		
	}

	/**
	 * when hoovered, tile is showed in the communication label, zoomed and with all the bonuses, not visible in the smaller version
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		TileLetterTextLabel tileText = (TileLetterTextLabel) e.getSource();
		window.getRulesPanel().getZoomedBusinessPermitTilePanel().setVisible(true);
		
		window.getRulesPanel().getZoomedBusinessPermitTilePanel().getBusinessPermitTileLabel().
				setCardText(tileText.getTileLabel().getLetters());
		
		window.getRulesPanel().getZoomedBusinessPermitTilePanel().getBonusPanel().removeAll();
		window.getRulesPanel().getZoomedBusinessPermitTilePanel().addBonusIcon
				(tileText.getTileLabel().getBonuses());
		
	}

	/**
	 * it makes the zoomed tile disappear from the communication label
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		window.getRulesPanel().getZoomedBusinessPermitTilePanel().setVisible(false);
		
	}
	
	/**
	 * it sets the code (of the tile clicked )in the actionMsg and notify the msg,
	 * then it closes the communicationLabel
	 */
	public void sendAndClear(){
		
		window.getGuiHandler().getActionMsg().setCodes(window.getGuiHandler().getCodes());
		window.getGuiHandler().notify(window.getGuiHandler().getActionMsg());
		window.getRulesPanel().getComunicationLabel().getComunicationTextArea().setText("");
		window.getRulesPanel().getComunicationLabel().setVisible(false);
		
	}
}
