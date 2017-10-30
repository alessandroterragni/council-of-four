/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.windows.rules;

import javax.swing.ImageIcon;
import javax.swing.JLabel;


/**
 * Label that host the image of a bonus.
 * @author Alessandro
 *
 */
public class BonusIconLabel extends JLabel{
	 
	private static final long serialVersionUID = 3626499226783369586L;

	/**
	 * Constructor of the class.
	 * @param bonus - bonus you want to obtain the image of
	 */
	public BonusIconLabel(String bonus) {
		super();
		this.setVisible(true);
		String[] splitBonus;
		splitBonus = bonus.split(":");
		buildIcon(splitBonus[0]);
		addNumber(splitBonus[1]);
		
	}
	
	/**
	 * It sets the image.
	 * @param name name of the bonus you want ot set the img of
	 */
	public void buildIcon(String name){
		switch(name){
			case "CoinBonus":
				this.setIcon(new ImageIcon("src/img/bonuses/coin.png"));
				break;
			case "DrawCardBonus":
				this.setIcon(new ImageIcon("src/img/bonuses/card.png"));
				break;
			case "AssistantBonus":
				this.setIcon(new ImageIcon("src/img/bonuses/assistant.png"));
				break;
			case "NobilityBonus":
				this.setIcon(new ImageIcon("src/img/bonuses/nobility.png"));
				break;
			case "VictoryPointBonus":
				this.setIcon(new ImageIcon("src/img/bonuses/victory.png"));
				break;
			case "MainActionBonus":
				this.setIcon(new ImageIcon("src/img/bonuses/oneMore.png"));
				break;
			case "ReuseCityBonus":
				this.setIcon(new ImageIcon("src/img/bonuses/reuseCityBonus.png"));
				break;
			case "ReusePermitBonus":
				this.setIcon(new ImageIcon("src/img/bonuses/reuseTileBonus.png"));
				break;
			case "TakePermitTileBonus":
				this.setIcon(new ImageIcon("src/img/bonuses/takeTile.png"));
				break;
			default:
				break;
			
		}
	}
	
	public void addNumber(String number){
	
		this.setText(number);
		
	}
	
	
}
