package it.polimi.ingsw.cg28.model.parser;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.model.BonusTile;
import it.polimi.ingsw.cg28.model.Councillor;
import it.polimi.ingsw.cg28.model.KingRewardTile;
import it.polimi.ingsw.cg28.model.GameMap;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.NobilityTrackBonus;
import it.polimi.ingsw.cg28.model.PoliticCard;
import it.polimi.ingsw.cg28.model.Region;
import it.polimi.ingsw.cg28.model.decks.CardDeck;
import it.polimi.ingsw.cg28.model.decks.TileDeck;

/**
 * Class with static method used to manage Input.
 * Method to build the model, parseBonus and parse Region from String.
 * @author Mario
 *
 */
public class IOController {

	/**
	 * Creates a new model using the specified configurationFiles.
	 * ModelStatus attribute PlayerID[] is not instantiated (Not from INPUT)
	 * @param configurationFiles files with the model configuration
	 * @return ModelStatus object
	 * @throws IOException
	 * @throws NullPointerException if param configurationFiles is null
	 */
	public ModelStatus build(File[] configurationFiles) throws IOException{
		
		Preconditions.checkNotNull(configurationFiles, "Configuration Files couldn't be null!");
		
		GameMap map;
		Region[] regions;
		CardDeck<PoliticCard> politicCardsDeck;
		TileDeck<BonusTile> bonusTiles;
		List<KingRewardTile> kingRewards;
		List<Councillor> noblesPool;
		int councilorsForBalcony;
		NobilityTrackBonus nobilityTrackBonus;
		
		CouncilorsBuilder councilorsBuilder = new CouncilorsBuilder();
		noblesPool = councilorsBuilder.build(configurationFiles[0]);
		
		RegionBuilder regionBuilder = new RegionBuilder();
		regions = regionBuilder.build(configurationFiles[1], noblesPool);
		
		councilorsForBalcony = regionBuilder.getNumCouncilors(configurationFiles[1]);
		
		MapBuilder mapBuilder = new MapBuilder();
		map = mapBuilder.build(configurationFiles[2], regions, noblesPool, councilorsForBalcony);
		
		PermitTileBuilder permitTileBuilder = new PermitTileBuilder();
		permitTileBuilder.build(configurationFiles[3], regions);
		
		BonusTileBuilder bonusTileBuilder = new BonusTileBuilder();
		bonusTiles = bonusTileBuilder.build(configurationFiles[4]);
		
		KingRewardsBuilder kingRewardsBuilder = new KingRewardsBuilder();
		kingRewards = kingRewardsBuilder.build(configurationFiles[5]);
		
		PoliticCardBuilder politicCardBuilder = new PoliticCardBuilder();
		politicCardsDeck = politicCardBuilder.build(configurationFiles[6]);
		
		NobilityTrackBuilder nobilityTrackBuilder = new NobilityTrackBuilder();
		nobilityTrackBonus = nobilityTrackBuilder.build(configurationFiles[7]);
		
		return new ModelStatus(map, null, regions, politicCardsDeck, bonusTiles, kingRewards, noblesPool, nobilityTrackBonus);

	}

}
