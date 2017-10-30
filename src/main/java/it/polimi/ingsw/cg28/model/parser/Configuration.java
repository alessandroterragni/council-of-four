package it.polimi.ingsw.cg28.model.parser;

import java.io.File;

import it.polimi.ingsw.cg28.view.messages.action.StartMsg;

/**
 * Offers different files for different configuration of the Game
 * @author Mario
 *
 */
public class Configuration {
	
	private boolean bazaar;
	private String configurationFiles;
	private String mapChosen;
	
	/**
	 * Constructor of the class. Extracts the configuration settings from StartMsg.
	 * @param startMsg - player chosen configuration settings.
	 */
	public Configuration(StartMsg startMsg){
		
		this.bazaar = startMsg.yesBazaar();
		
		configurationFiles = "Default";
		mapChosen = "Default";
		
	}
	
	/**
	 * Returns configuration files to build the model.
	 * @return array of File to build the model.
	 * 			<br>File[0] file related to Councilors {@link CouncilorsBuilder}
	 * 			<br>File[1] file related to Regions {@link RegionBuilder}
	 *          <br>File[2] file related to GameMap {@link MapBuilder}
	 *          <br>File[3] file related to PermitTiles {@link PermitTileBuilder}
	 *          <br>File[4] file related to BonusTile {@link BonusTileBuilder}
	 *          <br>File[5] file related to KingRewards {@link KingRewardsBuilder}
	 *          <br>File[6] file related to PoliticCard {@link PoliticCardBuilder}
	 *          <br>File[7] file related to NobilityTrack {@link NobilityTrackBuilder}
	 */
	public File[] fileConfig(){
		
		if("Default".equals(configurationFiles))
				return defaultConfig();
		
		return defaultConfig();
	}
	
	/**
	 * Offers the default game configuration.
	 * @return
	 */
	private File[] defaultConfig(){
		
		File[] config = new File[8];
		File councilors = new File("src/configurationFiles/Councilors.txt");
		config[0] = councilors;
		File regions = new File("src/configurationFiles/Regions.txt");
		config[1] = regions;
		
		File map = new File("src/configurationFiles/Map" + mapChosen + ".txt");
		config[2] = map;
		
	    File permitTile = new File("src/configurationFiles/PermitTile.txt");
		config[3] = permitTile;
		File bonusTile = new File("src/configurationFiles/BonusTile.txt");
		config[4] = bonusTile;
		File kingRewards = new File("src/configurationFiles/KingRewards.txt");
		config[5] = kingRewards;
		File politicCard = new File("src/configurationFiles/PoliticCard.txt");
		config[6] = politicCard;
		File nobilityTrack = new File("src/configurationFiles/NobilityTrack.txt");
		config[7] = nobilityTrack;
		
		return config;
		
	}
	
	/**
	 * Returns configuration setting about bazaar.
	 * @return Returns true if bazaar must be played.
	 */
	public boolean yesBazaar() {
		return bazaar;
	}
	
	/**
	 * Returns the String representing the map chosen.
	 * @return String representing the map chosen
	 */
	public String getMapChosen() {
		return mapChosen;
	}

}
