package it.polimi.ingsw.cg28.view.cli;

import java.awt.Color;
import java.util.List;

import it.polimi.ingsw.cg28.tmodel.TBalcony;
import it.polimi.ingsw.cg28.tmodel.TBonus;
import it.polimi.ingsw.cg28.tmodel.TBonusTile;
import it.polimi.ingsw.cg28.tmodel.TBusinessPermitTile;
import it.polimi.ingsw.cg28.tmodel.TMap;
import it.polimi.ingsw.cg28.tmodel.TNoblesPool;
import it.polimi.ingsw.cg28.tmodel.TPlayer;
import it.polimi.ingsw.cg28.tmodel.TProductAssistant;
import it.polimi.ingsw.cg28.tmodel.TProductCards;
import it.polimi.ingsw.cg28.tmodel.TProductTiles;
import it.polimi.ingsw.cg28.tmodel.TRegion;
import it.polimi.ingsw.cg28.tmodel.TTown;

/**
 * This class offers methods to create String objects representing the model for the CLI.
 * @author Marco, Alessandro, Mario
 *
 */
public class CliPrinter {
	
	/**
	 * PrintPlayer builds a String representing the status of the player, including track counter values,
	 * politic cards and business tiles.
	 * @param p - The player to build the string for
	 * @param flagPrivate - if true prints also the private information of the player
	 * @return The String representing the player p's status
	 */
	public String printPlayer(TPlayer p, boolean flagPrivate){
		
		StringBuilder sb = new StringBuilder();
		sb.append(p.getName() + "\n");
		sb.append("Emporiums: " + p.getEmpNumber() + "\n");
		sb.append("Coins: " + printTrack(p.getCoins()) + "\n");
		sb.append("Nobility Rank: " + printTrack(p.getNobilityRank()) + "\n");
		sb.append("Current number of Assistants: " + printTrack(p.getAssistants()) + "\n\n");
		sb.append("CURRENT SCORE: " + printTrack(p.getVictoryPoints()) + "\n\n");
		sb.append("---------------------------------------------------------------------------------------------------------------\n");
		if(flagPrivate) 
			{	sb.append("Your politic cards:\n");
				sb.append(printPolCardList(p.getPoliticCardsHand(), true) + "\n\n");
			}
		if(flagPrivate) 
			sb.append("Your business permit tiles:\n");
		else 
			sb.append("Business permit tiles:\n");
		sb.append(printTileList(p.getPossessedTiles(), true));
		
		sb.append("\n");
		return sb.toString();
		
	}
	
	/**
	 * Builds a string representing the map's regions
	 * @param regions - The regions to be put in string form
	 * @param numberedRegion - A boolean flag indicating whether the string has to include region index numbers or not
	 * @param numberedTile - A boolean flag indicating whether the string has to include uncovered
	 * tile index numbers or not
	 * @return The string containing the map's regions
	 */
	public String printRegions(TRegion[] regions, boolean numberedRegion, boolean numberedTile){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i<regions.length; i++){
			if (numberedRegion){
				sb.append( i+1 + ") ");
			}
			sb.append(printRegion(regions[i], numberedTile));
		}
		return sb.toString();
	}
	
	/**
	 * Builds a string representing a region with related uncovered tiles.
	 * @param region - The region to be put in string form
	 * @param numberedTile - A boolean flag indicating whether the string has to include region tiles index numbers or not
	 * @return The string representing the region
	 */
	public String printRegion(TRegion region,  boolean numberedTile) {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(region.getRegionType() + "\n");
		
		TBusinessPermitTile[] tiles = region.getUncovered();
		
		sb.append("------------------------\n");
		
		for(int j = 0; j<tiles.length; j++){

			if (numberedTile){
				sb.append( j+1 + ") ");
			}
			sb.append(printTile(tiles[j])+"\n");
		}
		sb.append("\n");
		
		return sb.toString();
	}
	
	/**
	 * Builds a String containing a track counter value.
	 * @param track - The track to build the string for
	 * @return The string containing the track's value
	 */
	public String printTrack(int trackValue){
		StringBuilder sb = new StringBuilder();
		sb.append(trackValue);
		return sb.toString();
	}
	
	/**
	 * Builds a string containing a list of politic cards.
	 * @param elements - The list to be put into a string
	 * @param numbered - A boolean flag indicating whether the string has to include index numbers or not
	 * @return The string containing the list of cards
	 * @see #printPolCard(Color)
	 */
	public String printPolCardList(Color[] elements, boolean numbered){
		StringBuilder sb = new StringBuilder();
		int i = 0;
		for(Color t : elements){
			if(numbered){
				sb.append(i+1 + ") ");
			}
			sb.append(printPolCard(t));
			i++;
		}
		return sb.toString();
	}
	
	/**
	 * Builds a string representing a single politic card. If card parameter is
	 * null prints an AllColors card.
	 * @param card - The {@link java.awt.Color} of the card to be put into a string.
	 * @return The string representing the politic card.
	 */
	public String printPolCard(Color card){
		StringBuilder sb = new StringBuilder();
		sb.append("[ " + (card != null ? getColorName(card) : "ALL COLORS") + " ] ");
		return sb.toString();
	}
	
	/**
	 * Builds a string containing a list of business permit tile.
	 * @param elements - The list to be put into a string
	 * @param numbered - A boolean flag indicating whether the string has to include index numbers or not
	 * @return The string containing the list of tiles
	 */
	public String printTileList(List<TBusinessPermitTile> elements, boolean numbered){
		StringBuilder sb = new StringBuilder();
		int i = 0;
		for(TBusinessPermitTile t : elements){
			if(numbered){
				sb.append(i+1 + ") ");
			}
			sb.append(printTile(t) + "\n");
			i++;
		}
		return sb.toString();
	}
	
	/**
	 * Builds a string representation (letterCodes and bonuses) of a single business permit tile.
	 * @param tile - The TBusinessPermitTile to represent.
	 * @return The string representing the tile
	 */
	public String printTile(TBusinessPermitTile tile){
		StringBuilder sb = new StringBuilder();
		sb.append("[ "+ printLetterCodes(tile) + "->");
		for(String str : tile.getBonuses().getBonusCode()){
			sb.append(" " + str + " |");
		}
		sb.append(" ]");
		return sb.toString();
	}
	
	/**
	 * Builds a representation of a tile letter codes.
	 * @param tile - The TBusinessPermitTile to represent the letter codes of.
	 * @return The string representing the tile letter codes.
	 */
	public String printLetterCodes(TBusinessPermitTile tile){
		StringBuilder sb = new StringBuilder();
		for(String s : tile.getLetterCodes()){
			sb.append(s + " ");
		}
		return sb.toString();
	}
	
	/**
	 * Builds a String representation of some {@link java.awt.Color} (Color.WHITE, Color.MAGENTA,
	 * Color.PINK, Color.ORANGE, Color.BLUE, Color.BLACK)
	 * @param c - The {@link java.awt.Color} to represent
	 * @return The string representation of the color
	 */
	public String getColorName(Color c){
		String name;
		int rgb = c.getRGB();
		switch(rgb) {
			case -1:
				name = "WHITE";
				break;
			case -20561:
				name = "PINK";
				break;
			case -14336:
				name = "ORANGE";
				break;
			case -16777216:
				name = "BLACK";
				break;
			case -65281:
				name = "MAGENTA";
				break;
			case -16776961:
				name = "BLUE";
				break;
			default:
				name = null;
				break;
		}
		return name;
	}
	
	/**
	 * Builds a string representation of a TBalcony.
	 * @param balcony - TBalcony to represent
	 * @param flag - A boolean flag indicating whether the string has to include councillors index numbers or not
	 * @return The string representation of the TBalcony
	 */
	public String printBalcony(TBalcony balcony, boolean flag){
			
		StringBuilder sb = new StringBuilder();
		for(int i = balcony.getCouncil().length - 1; i >= 0 ;i--){
			
			if(flag) 
				sb.append(balcony.getCouncil().length -i + 1 + ") ");
			
			sb.append(printCouncillor(balcony.getCouncillorColor(i)));
			sb.append(" | ");
		}
		sb.append("\n");
		return sb.toString();
	}
	
	/**
	 * Builds a String representation of a councillor.
	 * @param councillor - The {@link java.awt.Color} of the councillor to be put into a string
	 * @return The string representation of the councillor
	 */
	public String printCouncillor(Color councillor){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(getColorName(councillor));
		sb.append(" Councillor");
		
		return sb.toString();
	}
	
	/**
	 * Builds a String representation of a TNoblesPool.
	 * @param noblesPool - The TNoblesPool to represent
	 * @param flag - A boolean flag indicating whether the string has to include councillors index numbers or not
	 * @return The string representation of the nobles pool
	 */
	public String printNoblesPool(TNoblesPool noblesPool, boolean flag){
		
		StringBuilder sb = new StringBuilder();
		sb.append("nobles avaiable:\n");
		for(int i=0;i<noblesPool.size();i++){
			
			if (flag)
				sb.append(i+1 + ") ");
			
			sb.append(printCouncillor(noblesPool.getPool(i)));
			sb.append("\n");
		}
		
		return sb.toString();
	}
	
	/**
	 * Builds a detailed string representation of a town (name, alloy, bonuses, region, emporiums, if the king
	 * is or not in town).
	 * @param town - The TTown to represent
	 * @param numberedEmporiums -  A boolean flag indicating whether the string has to include emporiums index numbers or not
	 * @return The string representation of the town
	 */
	public String printDetailedTown(TTown town, boolean numberedEmporiums){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(town.getName() + "\nTown alloy: " + town.getAlloy().toUpperCase() + "\n");
		if(town.getBonus() != null){
			sb.append("Associated bonus: ");
			for(String str : town.getBonus().getBonusCode()){
				sb.append("[ " + str + " ] ");
			}
		}
		sb.append("\nIn region: [ " + town.getRegion() + " ]");
		sb.append("\nExisting emporiums:\n");
		for(int i = 0; i < town.getEmporiums().length; i++){
			if(numberedEmporiums){
				sb.append(i+1 + ") ");
			}
			sb.append("[ " + (town.getEmporium(i) == null ? "-" : town.getEmporium(i).getName())
					+ " ]\n");
		}
		sb.append("\nThe king " + (town.isKingHere() ? "is " : "is not ") + "in town.\n\n");
		
		return sb.toString();
		
	}
	
	/**
	 * Builds a string representation of a town (only town name).
	 * @param town - The TTown to represent
	 * @return The string representation of the town
	 */
	public String printTown(TTown town){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(town.getName());
		
		return sb.toString();
	}
	
	/**
	 * Builds a string representation of a list of TTown (detailed representation {@link #printDetailedTown(TTown, boolean)}).
	 * @param towns - The list of TTowns to represent.
	 * @param numbered - A boolean flag indicating whether the string has to include towns index numbers or not
	 * @return The string representation of the towns list
	 */
	public String printTownList(List<TTown> towns, boolean numbered){
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < towns.size(); i++){
			if(numbered){
				sb.append(i+1 + ") "); 
			}
			sb.append(printDetailedTown(towns.get(i), numbered));
		}
		
		return sb.toString();
	}
	
	/**
	 * Builds a string representation of towns in a TMap {@link #printTownList(List, boolean)}.
	 * @param map - The Map to represent towns of
	 * @param numbered - A boolean flag indicating whether the string has to include towns index numbers or not
	 * @return The string representation of the towns list
	 */
	public String printTowns(TMap map, boolean numbered){
		StringBuilder sb = new StringBuilder();
		
		sb.append(printTownList(map.getTownList(), numbered));
		
		return sb.toString();
	}
	
	/**
	 * Builds a string representation of connections between towns in a TMap (representation by adjacency lists).
	 * @param map - The TMap to represent
	 * @return The string representation of connections between towns in the TMap
	 */
	public String printTownConnections(TMap map){
		
		StringBuilder sb = new StringBuilder();
		List<TTown> towns = map.getTownList();
		sb.append("TOWN CONNECTIONS: \n");
		
		for(TTown town0: towns){
			sb.append(printTown(town0) + ": ");
			for(TTown town1: towns)
				if(map.getTownNetwork().containsEdge(town0, town1))
					sb.append(printTown(town1) + " | ");
			sb.append("\n");
		}
		
		return sb.toString();
		
	}
	
	/**
	 * Builds a string representation of regions balconies (if set by parameter also represents king balcony
	 * and uncovered tiles of each region).
	 * @param regions - The TRegion to represent the balcony of
	 * @param map - The TMap with the king balcony to represent
	 * @param king - Boolean flag, if false king balcony is not represented.
	 * @param tiles - Boolean flag, if true also represents uncovered tiles of each region
	 * @return The string representation of regions as set by parameter.
	 */
	public String printRegionsBalcony(TRegion[] regions, TMap map, boolean king, boolean tiles){
		
		StringBuilder sb = new StringBuilder();
		int i;
		
		for(i=0;i<regions.length;i++){
			sb.append("Region number: " + (i+1) + "\n");
			sb.append(printBalcony(regions[i].getCouncil(), false) );
			sb.append("-----------------------------------------------------------------------\n");
			
			if(tiles){
				for(int j = 0; j < regions[i].getUncovered().length; j++){
					sb.append((j+1)+")");
					sb.append(printTile(regions[i].getUncovered()[j])+"\n");
				}
				sb.append("-----------------------------------------------------------------------\n");
			}
		}
		if(king) 
		{	sb.append("King balcony: "+ (i+1) + "\n");
			sb.append(printBalcony(map.getKingCouncil(), false));
			sb.append("-----------------------------------------------------------------------\n");
		}
		
		sb.append("\n");
		
		return sb.toString();
		
	}
	
	/**
	 * Builds a string to represent a TBonusTile (identifier and relative bonuses).
	 * @param tile - The TBonusTile to represent
	 * @return The string representation of the TBonusTile
	 */
	public String printTBonusTile(TBonusTile tile){
		StringBuilder sb = new StringBuilder();
		sb.append("Bonus Tile [ " + tile.getIdentifier() + " ] : ");
		for(String str : tile.getBonus().getBonusCode()){
			sb.append(str + "  ");
		}
		return sb.toString();
	}
	
	/**
	 * Builds a string to represent an array of TBonusTile {@link #printTBonusTile(TBonusTile)}
	 * @param tiles - The array of TBonusTiles to represent
	 * @return The string representation of the array of TBonusTile
	 */
	public String printTBonusTiles(TBonusTile[] tiles){
		StringBuilder sb = new StringBuilder();
		sb.append("BONUS TILES\n");
		for(TBonusTile tile : tiles){
			sb.append(printTBonusTile(tile));
			sb.append("\n");
		}
		return sb.toString();
	}
	
	/**
	 * Builds a string to represent a TProductAssistant (number of assistants and price).
	 * @param tProductAssistant - The TProductAssistant to represent
	 * @return The string representation of the TProductAssistant
	 */
	public String printTProductAssistant(TProductAssistant tProductAssistant) {
		
		StringBuilder sb = new StringBuilder();
		sb.append(tProductAssistant.getNumbAssistant() + " assistants ");
		sb.append("for " + tProductAssistant.getPrice() + " coins");
		return sb.toString();
	}
	
	/**
	 * Builds a string to represent a TProductCards (cards {@link #printPolCard(Color)} and price).
	 * @param tPoliticCard - The TProductCards to represent
	 * @return The string representation of the TProductCards
	 */
	public String printTProductCards(TProductCards tPoliticCard) {
		
		StringBuilder sb = new StringBuilder();
		for(Color card: tPoliticCard.getCards())
			sb.append(printPolCard(card) + " ");
		sb.append("for " + tPoliticCard.getPrice() + " coins");
		return sb.toString();
		
	}
	
	/**
	 * Builds a string to represent a TProductTiles (cards {@link #printTile(TBusinessPermitTile)} and price).
	 * @param tProductTiles - The TProductTiles to represent
	 * @return The string representation of the TProductTiles
	 */
	public String printTProductTiles(TProductTiles tProductTiles) {
		
		StringBuilder sb = new StringBuilder();
		for(TBusinessPermitTile tile: tProductTiles.getTiles())
			sb.append(printTile(tile) + " ");
		sb.append("for " + tProductTiles.getPrice() + " coins");
		return sb.toString();
	}
	
	/**
	 * Builds a string to represent a nobility track.
	 * @param nobilityTrack - Array of TBonus, array i-th position must represent the i-th position on the nobility track
	 * @return The string representation of the nobility track
	 */
	public String printTNobilityTrack(TBonus[] nobilityTrack) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("\nNOBILITY TRACK:\n");
		
		for(int i=0; i < nobilityTrack.length; i++){
			if (nobilityTrack[i] != null){
				sb.append("Rank " + i +") ");
				for(String bonus : nobilityTrack[i].getBonusCode())
					sb.append(bonus + " | ");
				sb.append("\n");
			}
		}
			
		return sb.toString();
	}
	
	/**
	 * Builds a string to represent king reward tiles.
	 * @param kingRewards - Array of TBonus, 0 position higher king reward, descending order.
	 * @return The string representation of king reward tiles.
	 */
	public String printTKingRewards(TBonus[] kingRewards) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("\nKING REWARD TILES:\n");
		
		for(int i=0; i < kingRewards.length; i++){
				sb.append(i+1 +") ");
				for(String bonus : kingRewards[i].getBonusCode())
					sb.append(bonus + "   ");
				sb.append("\n");
		}
			
		return sb.toString();
		
	}
	
	
}