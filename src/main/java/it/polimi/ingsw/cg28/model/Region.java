package it.polimi.ingsw.cg28.model;

import it.polimi.ingsw.cg28.model.decks.TileDeck;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a region of the game's map. Also contains the permit tiles deck.
 * Offers all the methods to retrieve the information about towns and tiles, and
 * to manage said deck.
 * @author Marco
 *
 */
public class Region {

	private final String regionType;
	private final int unconveredNum;
	private BusinessPermitTile[] uncovered;
	private TileDeck<BusinessPermitTile> deck;
	private final List<Town> towns;
	private Balcony balcony;
	
	/**
	 * The constructor of the class.
	 * @param type - The string identifier of the region type
	 * @param uncoveredNum - The number of uncovered tiles on the region board
	 * @param deck - The permit tile deck associated to the region
	 * @param council - The balcony hosting the regional council
	 * @throws NullPointerException if the type or council parameters are null
	 * @throws IllegalArgumentException if uncoveredNum is negative
	 */
	public Region(String type, int uncoveredNum, TileDeck<BusinessPermitTile> deck,
			Councillor[] council) {
		if(type == null || council == null){
			throw new NullPointerException("Region type and regional council can't be null.");
		}
		if(uncoveredNum < 0){
			throw new IllegalArgumentException("Can't have a negative number of uncovered tiles.");
		}
		this.unconveredNum = uncoveredNum;
		this.regionType = type;
		this.uncovered = new BusinessPermitTile[uncoveredNum];
		this.deck = deck;
		this.towns = new ArrayList<>();
		this.balcony = new Balcony(council);
	}

	/**
	 * Fetches the deck of permit tiles for this region.
	 * @return The TileDeck containing the permit tiles for this region
	 */
	public TileDeck<BusinessPermitTile> getDeck() {
		return deck;
	}

	/**
	 * Sets this region's deck to the specified one.
	 * @param deck - The TileDeck to be set
	 * @throws NullPointerException if the deck to be set is null
	 */
	public void setDeck(TileDeck<BusinessPermitTile> deck) {
		if(deck == null){
			throw new NullPointerException("Deck can't be null.");
		}
		this.deck = deck;
	}
	
	/**
	 * Adds a town to the list of this region's towns.
	 * @param town - The town to be added to the list
	 * @throws NullPointerException if the specified town is null
	 */
	public void addTown(Town town){
		if(town == null){
			throw new NullPointerException("Can't add a null town.");
		}
		if(this.towns.contains(town)){
			return;
		}
		this.towns.add(town);
	}

	/**
	 * Fetches the regional council's balcony.
	 * @return The Balcony containing the Councillors of this region
	 */
	public Balcony getBalcony() {
		return balcony;
	}

	/**
	 * Sets the regional council's balcony to the desired one.
	 * @param balcony - The Balcony to be set
	 * @throws NullPointerException if the input balcony is null
	 */
	public void setBalcony(Balcony balcony) {
		if(balcony == null){
			throw new NullPointerException("Input Balcony can't be null.");
		}
		this.balcony = balcony;
	}

	/**
	 * Fetches the region's type.
	 * @return The String containing this region's type
	 */
	public String getRegionType() {
		return regionType;
	}

	/**
	 * Fetches the array of uncovered tiles of this region.
	 * @return An Array of permit tiles, containing the uncovered ones
	 */
	public BusinessPermitTile[] getUncovered() {
		return uncovered;
	}

	/**
	 * Sets the uncovered tiles of this region to the specified ones.
	 * @param uncovered - The array of permit tiles to be set as uncovered for this region
	 * @throws NullPointerException if the specified array is null
	 * @throws IllegalArgumentException if the specified array is longer than the available space for the region
	 */
	public void setUncovered(BusinessPermitTile[] uncovered) {
		if(uncovered == null){
			throw new NullPointerException("Can't set a null array of tiles.");
		}
		if(uncovered.length < this.unconveredNum){
			throw new IllegalArgumentException("Input array has less elements than the actual requested number.");
		}
		this.uncovered = uncovered;
	}
	
	/**
	 * getTile allows to retrieve the uncovered tile based on its position
	 * on the board
	 * @param position - The index representing the tile's position
	 * @return The chosen permit tile
	 * @throws IllegalArgumentException if the specified position is negative or
	 * greater than the number of uncovered tiles
	 */
	public BusinessPermitTile getTile(int position){
		if(position < 0 || position > this.getUnconveredNum()){
			throw new IllegalArgumentException("Specified Position can't be negative or greater than"
					+ " the uncovered tiles number.");
		}
		return uncovered[position];
	}

	/**
	 * Fetches the list of towns in this region.
	 * @return The List of towns in this region
	 */
	public List<Town> getTowns() {
		return towns;
	}
	
	/**
	 * Allows to remove the uncovered tile from the specified position
	 * on the board and to substitute it with a new one, drawn from the deck.
	 * @param position - The index representing the tile's position
	 * @throws IllegalArgumentException if the specified position is negative or
	 * greater than the number of uncovered tiles
	 */
	public void changeTile(int position){
		if(position < 0 || position > this.unconveredNum){
			throw new IllegalArgumentException("Specified position does not correspond to any tile.");
		}
		BusinessPermitTile target = deck.draw();
		uncovered[position] = target;
		
	}
	
	/**
	 * Allows to put a tile that is on the board back to the bottom of the regional tile deck.
	 * Also draws another card, putting it to the empty position
	 * @param position - The index representing the tile's position
	 * @throws IllegalArgumentException if the specified position is negative or
	 * greater than the number of uncovered tiles
	 */
	public void reshuffleTile(int position){
		if(position < 0 || position > this.unconveredNum){
			throw new IllegalArgumentException("Specified position does not correspond to any tile.");
		}
		this.deck.enqueue(uncovered[position]);
		changeTile(position);
	}
	
	/**
	 * Fetches the number of uncovered tiles for this region.
	 * @return The integer number of uncovered tiles
	 */
	public int getUnconveredNum() {
		return unconveredNum;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((regionType == null) ? 0 : regionType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Region other = (Region) obj;
		if (regionType == null) {
			if (other.regionType != null)
				return false;
		} else if (!regionType.equals(other.regionType))
			return false;
		return true;
	}

	
}
