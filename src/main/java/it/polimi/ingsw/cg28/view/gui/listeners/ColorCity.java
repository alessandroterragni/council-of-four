/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.listeners;


/**
 * class that is used to match the name of the city with the color of that city in the map used by the ColorMapListener 
 * @author Alessandro
 *
 */
public class ColorCity {
	
	int rgbColor;
	String name;
	
	/**
	 * constructor of the class
	 * @param rgbColor Color of the class in RGB 
	 * @param name Name of the City
	 */
	public ColorCity(int rgbColor, String name) {
		this.rgbColor = rgbColor;
		this.name = name;
	}

	/**
	 * @return the rgbColor
	 */
	public int getRgbColor() {
		return rgbColor;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	
}
