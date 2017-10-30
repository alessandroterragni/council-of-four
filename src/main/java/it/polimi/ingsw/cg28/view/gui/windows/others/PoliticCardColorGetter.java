/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.windows.others;

import java.awt.Color;


/**
 * It hosts a method that returns the name and the img path of a politic card.
 * @author Alessandro
 *
 */
public class PoliticCardColorGetter {
	  
	/**
	 * Returns the name and the img path of a politic card.
	 * @param c - Color of the card you want to obtain the name and image path of
	 * @return name (strings[1]) and image path (strings[0]) of the card you want to obtain
	 */
	public String[] getColorName(Color c){
		
		String[] info = new String[2];
		
		if(c == (null)){
			info[0] = "src/img/politicCards/allcolors.png";
			info[1] = "RAINBOW";
		}
		else
		{
			int rgb = c.getRGB();
			switch(rgb) {
				case -1:
					info[0] = "src/img/politicCards/white.png";
					info[1] = "WHITE";
					break;
				case -20561:
					info[0] ="src/img/politicCards/pink.png";
					info[1] = "PINK";
					break;
				case -14336:
					info[0] = "src/img/politicCards/orange.png";
					info[1] = "ORANGE";
					break;
				case -16777216:
					info[0] = "src/img/politicCards/black.png";
					info[1] = "BLACK";
					break;
				case -65281:
					info[0] = "src/img/politicCards/violet.png";
					info[1] = "VIOLET";
					break;
				case -16776961:
					info[0] ="src/img/politicCards/blue.png";
					info[1] = "BLUE";
					break;
				default:
					break;
			}
		}
		return info;
	}

}
