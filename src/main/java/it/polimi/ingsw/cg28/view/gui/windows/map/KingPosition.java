/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.windows.map;

/**
 * Class that associates the name of a town with the related position of the king to display.
 * @author Alessandro
 *
 */
public class KingPosition {
	
	private String townName;
	private int x;
	private int y;
	
	/**
	 * Constructor of the class.
	 * @param townName name of the town
	 * @param x - x coordinate 
	 * @param y - y coordinate
	 */
	public KingPosition(String townName, int x, int y) {
		super();
		this.townName = townName;
		this.x = x;
		this.y = y;
	}

	/**
	 * @return the townName
	 */
	public String getTownName() {
		return townName;
	}

	/**
	 * @return the x coordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y coordinate
	 */
	public int getY() {
		return y;
	}
	
	
	
}
