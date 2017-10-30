/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.windows.others;

import java.awt.Color;

public class CouncillorGetterImage {

	/**
	 * 
	 * @param color color of the councillor
	 * @return a String[] with the name of the color ( String[0]), and the oath of the related image (String[1]) 
	 */
	public String[] setCouncillorIcon(Color color) {
		
		String[] info = new String[2];
		
		if(color.equals(Color.BLACK)){
			info[0]= "BLACK";
			info[1]= "src/img/submap/councillors/consgliereNeroIcon.png";
	
		}
		
		if(color.equals(Color.BLUE)){
			info[0]= "BLUE";
			info[1]="src/img/submap/councillors/consgliereAzzurroIcon.png";
		}
		
		if(color.equals(Color.PINK)){
			info[0]= "PINK";
			info[1]="src/img/submap/councillors/consglierePinkIcon.png";
		}
		
		if(color.equals(Color.WHITE)){
			info[0]="WHITE";
			info[1]="src/img/submap/councillors/consgliereBiancoIcon.png";
		}
		
		if(color.equals(Color.MAGENTA)){
			info[0]="MAGENTA";
			info[1]="src/img/submap/councillors/consgliereViolaIcon.png";
		}
		
		if(color.equals(Color.ORANGE)){
			info[0]="ORANGE";
			info[1]="src/img/submap/councillors/consgliereArancioneIcon.png";
	
		}
		
		return info;
	}
	
}
