package it.polimi.ingsw.cg28.model.parser;

import java.io.File;
import java.io.IOException;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.model.AssistantCounterTrack;
import it.polimi.ingsw.cg28.model.CoinTrack;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.Player;
import it.polimi.ingsw.cg28.model.PoliticCard;
import it.polimi.ingsw.cg28.model.parser.IOController;

public class ModelTestConfiguration {
	
	
	public ModelStatus buildModelTest(PlayerID[] players){
		
		File[] config;
		ModelStatus model = null;
		IOController controller;
		
		config = new File[8];
		File councilors = new File("src/configurationFilesTest/Councilors.txt");
		config[0] = councilors;
		File regions = new File("src/configurationFilesTest/Regions.txt");
		config[1] = regions;
		File map = new File("src/configurationFilesTest/Map0.txt");
		config[2] = map;
		File permitTile = new File("src/configurationFilesTest/PermitTile.txt");
		config[3] = permitTile;
		File bonusTile = new File("src/configurationFilesTest/BonusTile.txt");
		config[4] = bonusTile;
		File kingRewards = new File("src/configurationFilesTest/KingRewards.txt");
		config[5] = kingRewards;
		File politicCard = new File("src/configurationFilesTest/PoliticCard.txt");
		config[6] = politicCard;
		File nobilityTrack = new File("src/configurationFilesTest/NobilityTrack.txt");
		config[7] = nobilityTrack;
		
		controller = new IOController();
		
		try {
			model = controller.build(config);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		model.setPlayers(build(players, model));
		
		return model;
		
	}
	
	public Player[] build(PlayerID[] IDs, ModelStatus model){
		
		Player[] players = new Player[IDs.length];
		
		for(int i = 0; i < IDs.length; i++){
			
			PoliticCard[] starterHand = new PoliticCard[2];
			
			for(int j = 0; j < 2; j++)
				starterHand[j] = model.getPoliticCardsDeck().draw();
				
			players[i] = new Player(IDs[i], null, 10, new CoinTrack(10+i),
					starterHand, new AssistantCounterTrack(1+i));
		}
		
		return players;
	}

}
