/**
 * 
 */
package it.polimi.ingsw.cg28.tmodel;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.controller.bonus.Bonus;
import it.polimi.ingsw.cg28.controller.updaters.PrintBonusTranslator;
import it.polimi.ingsw.cg28.model.Balcony;
import it.polimi.ingsw.cg28.model.BonusTile;
import it.polimi.ingsw.cg28.model.BusinessPermitTile;
import it.polimi.ingsw.cg28.model.Councillor;
import it.polimi.ingsw.cg28.model.GameMap;
import it.polimi.ingsw.cg28.model.NobilityTrackBonus;
import it.polimi.ingsw.cg28.model.Player;
import it.polimi.ingsw.cg28.model.Region;
import it.polimi.ingsw.cg28.model.Town;
import it.polimi.ingsw.cg28.model.decks.TileDeck;

/**
 * @author Alessandro
 *
 */
public class TObjectFactory {
	
	
	public TBalcony createTBalcony(Balcony balcony){
		
		Preconditions.checkNotNull(balcony, "The balcony to convert into a TBalcony can't be null.");
		
		Color[] councillors= new Color[balcony.getCouncil().size()];
		
		for(int i = 0;i<balcony.getCouncil().size();i++){
			councillors[i]=balcony.getCouncillor(i).getColor();
		}
		
		return new TBalcony(councillors);
		
	}
	
	public TTown createTtown(Town town){
		
		Preconditions.checkNotNull(town, "The town to convert into a TTown can't be null.");
		
		String name = town.getTownName();
		String alloy = town.getAlloy();
		String region = town.getRegion().getRegionType();
		boolean king = town.isKingHere();
		PlayerID[] emporiums = new PlayerID[town.emporiumNumber()];
		
		for(int i=0;i<town.emporiumNumber();i++)
		{
			emporiums[i] = town.getEmporium(i).getOwner();
		}
		
		TBonus bonus = null;
		
		if (town.getBonus()!= null)
			bonus = createTBonus(town.getBonus());
		
		return new TTown(name, alloy, region, bonus, king, emporiums);
	}
	
	
	public TPlayer createTPlayer(Player player, boolean flagPrivate){
		
		Preconditions.checkNotNull(player, "The player to convert into a TPlayer can't be null.");
		
		String name = player.getID().getName();
		String id = player.getID().getUuidPlayer().toString();
		int empNumber = player.getEmporiumNumber();
		int coins = player.getCoins().getValue();
		int nobilityRank = player.getNobilityRank().getValue();
		int victoryPoints = player.getScore().getValue();
		int assistants = player.getAssistants().getValue();
		Color[] politicCardsHand = new Color[player.getPoliticCardsHand().size()];
		
		if(flagPrivate){
			for(int i=0;i<player.getPoliticCardsHand().size();i++){
				politicCardsHand[i] = player.getCard(i).getHouseColor(); 
			}
		}
		
		List<TBusinessPermitTile> possessedTiles = new ArrayList<>(); 
		for(int i=0;i<player.getPossessedTiles().size();i++){
			possessedTiles.add(createTBusinessPermitTile(player.getTile(i)));
		}
		
		List<TBusinessPermitTile> usedTiles = new ArrayList<>(); 
		for(int i=0;i < player.getUsedTiles().size();i++){
			usedTiles.add(createTBusinessPermitTile(player.getReusedTile(i)));
		}
	
		return new TPlayer(name, id, empNumber, coins, nobilityRank, victoryPoints,assistants, politicCardsHand, possessedTiles, usedTiles);
		
	}
	
	
	public TBusinessPermitTile createTBusinessPermitTile(BusinessPermitTile businessPermitTile){
		
		Preconditions.checkNotNull(businessPermitTile, "The business permit tile to convert"
				+ " into TBusinessPermitTile can't be null.");
		
		TBonus bonus = createTBonus(businessPermitTile.getBonuses());
		String[] letterCodes = new String[businessPermitTile.getLetterCodes().length];
		
		for(int i=0;i<businessPermitTile.getLetterCodes().length;i++){
			letterCodes[i] = businessPermitTile.getLetterCodes()[i];
		}
		
		return new TBusinessPermitTile(bonus, letterCodes);
		
	}
	
	public TBusinessPermitTile[] createTBusinessPermitTileArray(BusinessPermitTile[] businessPermitTiles) {
		
		Preconditions.checkNotNull(businessPermitTiles, "The business permit tile array to convert"
				+ " into TBusinessPermitTile[] can't be null.");
		
		TBusinessPermitTile[] result = new TBusinessPermitTile[businessPermitTiles.length];
		for(int i = 0; i < businessPermitTiles.length; i++){
			result[i] = createTBusinessPermitTile(businessPermitTiles[i]);
		}
		return result;
	}
	
	public TBusinessPermitTile[] createTBusinessPermitTileArray(List<BusinessPermitTile> businessPermitTiles) {
		
		Preconditions.checkNotNull(businessPermitTiles, "The business permit tile array to convert"
				+ " into TBusinessPermitTile[] can't be null.");
		
		TBusinessPermitTile[] result = new TBusinessPermitTile[businessPermitTiles.size()];
		
		for(int i = 0; i < businessPermitTiles.size(); i++)
			result[i] = createTBusinessPermitTile(businessPermitTiles.get(i));
		
		return result;
	}
	
	public TBonus createTBonus(Bonus bonus){
		
		Preconditions.checkNotNull(bonus, "The bonus to convert into TBonus can't be null.");
		
		PrintBonusTranslator bonusTranslator = new PrintBonusTranslator();
		bonus.translate(bonusTranslator);
		String[] bonusCodes = bonusTranslator.getString().split(">");
		
		return new TBonus(bonusCodes);
	}
	
	public TRegion createTRegion(Region region){
		
		Preconditions.checkNotNull(region, "The region to convert into TRegion can't be null.");
		
		TBalcony councillors=createTBalcony(region.getBalcony());
		
		String regionType = region.getRegionType();
		
		TBusinessPermitTile[] uncovered = new TBusinessPermitTile[region.getUnconveredNum()]; 
		
		for(int i=0; i < region.getUnconveredNum();i++){
			uncovered[i] = createTBusinessPermitTile(region.getUncovered()[i]);
		}
		
		return new TRegion(councillors, regionType, uncovered);
		
	}
	
	public TMap createTMap(GameMap map){
		
		Preconditions.checkNotNull(map, "The map to convert into TMap can't be null.");
		
		
		TBalcony kingCouncil = createTBalcony(map.getKingCouncil()); 
		SimpleGraph<TTown,DefaultEdge> townNetwork = new SimpleGraph<>(DefaultEdge.class);
	
		for(Town town0: map.getTownNetwork().vertexSet()){
  			TTown tTown0 = createTtown(town0);
			if(!townNetwork.vertexSet().contains(tTown0)){
				townNetwork.addVertex(tTown0);
			}
			
			TTown tTown1;
			
			for(Town town1: map.getTownNetwork().vertexSet()){
				tTown1 = createTtown(town1);
				if(!townNetwork.vertexSet().contains(tTown1)){
					townNetwork.addVertex(tTown1);
				}
				if(map.getTownNetwork().containsEdge(town0, town1) && townNetwork.vertexSet().contains(tTown0) &&
						townNetwork.vertexSet().contains(tTown1)){
					townNetwork.addEdge(tTown0, tTown1);
				}
			}
		}
		 
		return new TMap(townNetwork, kingCouncil);
	}
	
	public TBonusTile createTBonusTile(BonusTile bonusTile){
		
		Preconditions.checkNotNull(bonusTile, "The bonus tile to convert into TBonusTile can't be null.");
		
		TBonus bonus = createTBonus(bonusTile.getBonus());
		
		return new TBonusTile(bonus, bonusTile.getIdentifier());
		
	}
	
	public TNoblesPool createTNoblesPool(List<Councillor> pool){
		
		Preconditions.checkNotNull(pool, "The list of councillors to convert into TNoblesPool can't be null.");
		
		List<Color> colors = new ArrayList<>();	
		
		for(Councillor c: pool){
				colors.add(c.getColor());
			}
		
		return new TNoblesPool(colors); 
	}

	public TBonus[] createTNobilityTrack(NobilityTrackBonus nobilityTrackBonus) {
		
		Preconditions.checkNotNull(nobilityTrackBonus, "The nobility track bonus set to convert"
				+ " into TBonus[] can't be null.");
		
		TBonus[] nobilityRank = new TBonus[nobilityTrackBonus.getMaxValue()+1];
		
		for(int i=0; i<=nobilityTrackBonus.getMaxValue(); i++){
			
			Bonus bonus = nobilityTrackBonus.getTrackBonus(i);
			
			if (bonus != null){
				nobilityRank[i] = createTBonus(bonus);
			} else nobilityRank[i] = null;
		}
		
		return nobilityRank;
			
	}

	public TBonusTile[] createTBonusTiles(TileDeck<BonusTile> bonusTiles) {
		
		Preconditions.checkNotNull(bonusTiles, "The bonus tiles to convert into TBonusTile[] can't be null.");

		TBonusTile[] tTiles = new TBonusTile[bonusTiles.size()];
		
		for(int i=0; i < bonusTiles.size(); i++){
			BonusTile tile = bonusTiles.draw();
			tTiles[i] = createTBonusTile(tile);
			bonusTiles.enqueue(tile);
		}
		
		return tTiles;	
		
	}
	
}
