package it.polimi.ingsw.cg28.model;

import java.util.List;

import it.polimi.ingsw.cg28.controller.PlayerID;

/**
 * PlayerBuilder offers the method build to create an array of correctly
 * initialized Player objects.
 * @author Marco
 *
 */
public class PlayerBuilder {

	/**
	 * The method build returns an array of Player objects, initialized with data from
	 * the given ModelStatus object.
	 * @param listId - An array containing the players' IDs
	 * @param model - The model containing some of the information needed
	 * to initialize the various players
	 * @return An array of Player objects, correctly initialized.
	 * @throws NullPointerException if the list of PlayerIDs or the model are null
	 */
	public Player[] build(List<PlayerID> listId, ModelStatus model){
		
		if(listId == null || model == null){
			throw new NullPointerException("PlayerBuilder failure: can't build a set of players with any null parameter.");
		}
		
		Player[] players = new Player[listId.size()];
		
		for(int i = 0; i < listId.size(); i++){
			
			PoliticCard[] starterHand = new PoliticCard[6];
			
			for(int j = 0; j < 6; j++)
				starterHand[j] = model.getPoliticCardsDeck().draw();
				
			players[i] = new Player(listId.get(i), null, 10, new CoinTrack(10+i),
					starterHand, new AssistantCounterTrack(1+i));
		}
		
		if (listId.size() == 2)
			setModelFor2Players(model);
		
		return players;
	}

	/**
	 * Provides an ad-hoc setup for the game in case of only 2 participants.
	 * @param model - The model to set up
	 */
	private void setModelFor2Players(ModelStatus model) {
		
		Region[] regions = model.getRegions();
		GameMap map = model.getMap();
		PlayerID notAPlayer = new PlayerID("NotAPlayer");
		
		for(Region region: regions){
			
			BusinessPermitTile tile = region.getDeck().draw();
			
			for(String town: tile.getLetterCodes())
				map.addEmporium(notAPlayer, map.getTown(town));
			
			region.getDeck().enqueue(tile);
			region.getDeck().shuffle();
			
		}
		
	}
	
}