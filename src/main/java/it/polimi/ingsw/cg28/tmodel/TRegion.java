package it.polimi.ingsw.cg28.tmodel;

import java.io.Serializable;

/**
 * Represents a Region variant which can be transfered from the server to the client without
 * making the real model's rep accessible from a remote client
 * @author Marco
 *
 */
public class TRegion implements Serializable{

	private static final long serialVersionUID = -5407247482862033996L;
	private final TBalcony council;
	private final String regionType;
	private final TBusinessPermitTile[] uncovered;
	
	/**
	 * The constructor of the class, creates a new TRegion.
	 * @param council - A TBalcony object containing this region's council balcony
	 * @param regionType - A string containing this region's type
	 * @param uncovered - An array of TBusinessPermitTile objects, containing the uncovered tiles of this region
	 * @throws NullPointerException if any of the parameters is null
	 */
	public TRegion(TBalcony council, String regionType, TBusinessPermitTile[] uncovered) {
		if(council == null || regionType == null || uncovered == null){
			throw new NullPointerException("Can't create a TRegion with any null parameter.");
		}
		this.council = council;
		this.regionType = regionType;
		this.uncovered = uncovered;
	}

	/**
	 * Fetches this region's TBalcony.
	 * @return a TBalcony object representing this region's council balcony
	 */
	public TBalcony getCouncil() {
		return council;
	}

	/**
	 * Fetches this region's type.
	 * @return the string containing this region's type
	 */
	public String getRegionType() {
		return regionType;
	}

	/**
	 * Fetches an array containing the uncovered tiles for this region.
	 * @return an array of TBusinessPermitTile objects containing this region's uncovered tiles
	 */
	public TBusinessPermitTile[] getUncovered() {
		return uncovered;
	}
}
