/**
 * 
 */
package it.polimi.ingsw.cg28.controller.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.Game;
import it.polimi.ingsw.cg28.controller.actions.bazaar.BuySalableAction;
import it.polimi.ingsw.cg28.controller.actions.bazaar.SellAssistantsAction;
import it.polimi.ingsw.cg28.controller.actions.bazaar.SellPermitTilesAction;
import it.polimi.ingsw.cg28.controller.actions.bazaar.SellPoliticCardsAction;
import it.polimi.ingsw.cg28.controller.bazaar.ProductOnSale;
import it.polimi.ingsw.cg28.exception.InvalidActionException;
import it.polimi.ingsw.cg28.model.Balcony;
import it.polimi.ingsw.cg28.model.BazaarModel;
import it.polimi.ingsw.cg28.model.BusinessPermitTile;
import it.polimi.ingsw.cg28.model.Councillor;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.Player;
import it.polimi.ingsw.cg28.model.PoliticCard;
import it.polimi.ingsw.cg28.model.Region;
import it.polimi.ingsw.cg28.model.Town;
import it.polimi.ingsw.cg28.model.decks.CardDeck;
import it.polimi.ingsw.cg28.tmodel.TProduct;
import it.polimi.ingsw.cg28.tmodel.TTown;
import it.polimi.ingsw.cg28.view.messages.action.bazaar.BuySalableActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.bazaar.SellAssistantsActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.bazaar.SellPermitTilesActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.bazaar.SellPoliticCardsActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.bonus.ReuseCityBonusActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.bonus.ReusePermitBonusActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.bonus.TakePermitTileBonusActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.ChangeTileActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.ElectionActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.EmporiumKingActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.EmporiumTileActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.HireAssistantActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.OneMoreMainActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.PermitTileActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.SendAssistantActionMsg;

/**
 * Concrete visit strategy implementation of the ActionFactory interface.
 * Specifics a strategy to translate messages into actions
 * @author Alessandro, Mario
 *
 */
public class DefaultActionFactory implements ActionFactory {

	private ModelStatus model;
	private BazaarModel bazaarModel;
	
	private static final String ERROR_MESSAGE = "ActionMsg couldn't be null";
	private static final String NOT_ENOUGH_ASSISTANTS = "Not enough Assistants!";
	
	private static final Logger log = Logger.getLogger(Game.class.getName());
	
	/**
	 * It's the constructor of the class
	 * @param model the modelStatus of the game
	 * @throws NullPointerException if the model passed is null
	 */
	public DefaultActionFactory(ModelStatus model) {
		
		Preconditions.checkNotNull(model,"Model can't be null");
		this.model=model;
	}
	
	/**
	 * It sets the BazarModel
	 * @param bazaarModel the bazarModel
	 * @throws NullPointerException if the BazaarModel passed is null
	 */
	@Override
	public void setBazaarModel(BazaarModel bazaarModel) {
		Preconditions.checkNotNull(bazaarModel,"BazaarModel can't be null");
		this.bazaarModel = bazaarModel;
	}
	
	/**
	 * Checks if Player can do a QuickAction
	 * @param player the player to be checked
	 * @throws InvalidActionException if can't do a QuickAction now
	 */
	private void checkQuickAction(Player player) throws InvalidActionException{
		
		if (player.getTurn() == null)
			throw new InvalidActionException("Not your turn!");
		
		if (!player.getTurn().canDoQuickAction())
			throw new InvalidActionException("Can't do a quick action now");
		
	}
	
	/**
	 * Checks if Player can do a MainAction
	 * @param player the player to be checked
	 * @throws InvalidActionException if can't do a MainAction now
	 */
	private void checkMainAction(Player player) throws InvalidActionException{
		
		if (player.getTurn() == null)
			throw new InvalidActionException("Not your turn!");
		
		if (!player.getTurn().canDoMainAction())
			throw new InvalidActionException("Can't do a main action now");
		
	}
	
	
	/**
	 * It builds a ChangeTileAction from the ChangeTileActionMsg passed as a parameter
	 * @param actionMsg a ChangeTileActionMsg to be translated
	 * @return a corresponding ChangeTileAction
	 * @throws InvalidActionException if the action can't be built because it couldn't be executed: 
	 * 		   when the player can't do a quick action or when the number of the assistants of the 
	 *         player is <1
	 * @throws NullPointerException if the parameter passed is null
	 */
	@Override
	public ChangeTileAction build(ChangeTileActionMsg actionMsg) throws InvalidActionException{
		
		Preconditions.checkNotNull(actionMsg,ERROR_MESSAGE);
		
		Region region = model.getRegion(actionMsg.getRegionCode() - 1);
		Player player = model.getPlayer(actionMsg.getPlayerID());
		
		checkQuickAction(player);
		
		if (player.getAssistants().getValue() < 1)
			throw new InvalidActionException(NOT_ENOUGH_ASSISTANTS);
		
		return new ChangeTileAction(region);
	}
	
	/**
	 * It builds a ElectionAction from the ElectionActionMsg passed as a parameter
	 * @param actionMsg an ElectionActionMsg to be translated
	 * @return a corresponding ElectionAction
	 * @throws InvalidActionException if the action can't be be built because it couldn't be executed:
	 *         when the player can't do a main action
	 * @throws NullPointerException if the parameter passed is null
	 */
	@Override
	public ElectionAction build(ElectionActionMsg actionMsg) throws InvalidActionException{
		
		Preconditions.checkNotNull(actionMsg,ERROR_MESSAGE);
		Player player = model.getPlayer(actionMsg.getPlayerID());
		
		checkMainAction(player);
		
		Balcony balcony;
		if(actionMsg.getBalconyCode()== (actionMsg.getRegionNumber()+1)){
			balcony = model.getMap().getKingCouncil();
		}
		else {
		balcony= model.getRegion(actionMsg.getBalconyCode()-1).getBalcony();
		}
		
		Councillor councillor = model.getCouncillor(actionMsg.getCouncilorCode()-1);
		
		List<Councillor> councillors = model.getNoblesPool();
			
		return new ElectionAction(balcony, councillor, councillors);

	}


	/**
	 * It builds a EmporiumKingAction from the EmporiumKingActionMsg passed as a parameter
	 * @param actionMsg an EmporiumKingActionnMsg to be translated
	 * @return a corresponding EmporiumKingAction
	 * @throws InvalidActionException if the action can't be be built because it couldn't be executed: 
	 * when the player can't do a main action, when the current player has already an emporium in the city
	 * specified in the message, when the number of the assistants required is < of the number of assistants 
	 * of the player, when the number of coins required is < of the number of coins of the player
	 * @throws NullPointerException if the parameter passed is null
	 */
	@Override
	public EmporiumKingAction build(EmporiumKingActionMsg actionMsg) throws InvalidActionException{
		
		Preconditions.checkNotNull(actionMsg,ERROR_MESSAGE);
		
		Player player = model.getPlayer(actionMsg.getPlayerID());
		checkMainAction(player);
		
		Balcony balcony = model.getMap().getKingCouncil();
		
		TTown tTown = actionMsg.getMap().getTownList().get(actionMsg.getTownCode()-1);
		
		Town town = model.getMap().getTown(tTown.getName());
		
		ArrayList<PoliticCard> choosenPoliticCards=new ArrayList<>();
		CardDeck<PoliticCard> deck = model.getPoliticCardsDeck();
		
		if(town.hasEmporium(player.getID()))
			throw new InvalidActionException("You already have an emporium here");
		
		if (town.emporiumNumber() > player.getAssistants().getValue())
			throw new InvalidActionException(NOT_ENOUGH_ASSISTANTS);
		
		for(int i=0;i<actionMsg.getChosenPoliticCardsCode().length;i++){
			PoliticCard politicCard = player.getCard(Integer.parseInt(actionMsg.getChosenPoliticCardsCode()[i])-1);
			choosenPoliticCards.add(politicCard);
		}
		
		EmporiumKingAction action = new EmporiumKingAction(model.getMap(), town, balcony, choosenPoliticCards,deck);
		
		if(player.getCoins().getValue() < action.coinsToPay())
			throw new InvalidActionException("Not enough money");
		
		return action;
	}
	
	/**
	 * It builds a EmporiumTileAction from the EmporiumTileActionMsg passed as a parameter
	 * @param actionMsg an EmporiumTileActionMsg to be translated
	 * @return a corresponding EmporiumTileAction
	 * @throws InvalidActionException if the action can't be be built because it couldn't be executed: 
	 * when the player can't do a main action, when the current player has already an emporium in that city,
	 * when the number of the assistants required is < of the number of assistants of the player
	 * @throws NullPointerException if the parameter passed is null
	 */
	@Override
	public EmporiumTileAction build(EmporiumTileActionMsg actionMsg) throws InvalidActionException{
		
		Preconditions.checkNotNull(actionMsg,ERROR_MESSAGE);
		
		Town town = actionMsg.checkTowns(model).get(actionMsg.getTownCode()-1);
		Player player = model.getPlayer(actionMsg.getPlayerID());
		checkMainAction(player);
		
		BusinessPermitTile tile = player.getTile(actionMsg.getTileCode()-1);
		
		if(town.hasEmporium(player.getID()))
			throw new InvalidActionException("You already have an emporium here");
		
		if (town.emporiumNumber() > player.getAssistants().getValue())
			throw new InvalidActionException(NOT_ENOUGH_ASSISTANTS);
		
		return new EmporiumTileAction(tile, town, model.getMap());
	}
	
	
	/**
	 * It builds an HireAssistantAction from the HireAssistantActionMsg passed as a parameter
	 * @param actionMsg an HireAssistantActionMsg to be translated
	 * @return a corresponding HireAssistantAction
	 * @throws InvalidActionException if the action can't be be built because it couldn't be executed: 
	 * when the player can't do a quick action or when the the number of coins of the player is <3
	 * @throws NullPointerException if the parameter passed is null
	 */
	@Override
	public HireAssistantAction build(HireAssistantActionMsg actionMsg) throws InvalidActionException{
		
		Preconditions.checkNotNull(actionMsg,ERROR_MESSAGE);
		
		Player player = model.getPlayer(actionMsg.getPlayerID());
		
		checkQuickAction(player);
		
		if (player.getCoins().getValue() < 3)
			throw new InvalidActionException("You've run out of money :(");
		return new HireAssistantAction();
	}

	/**
	 * It builds a OneMoreMainAction from the OneMoreMainMsg passed as a parameter
	 * @param actionMsg an OneMoreMainActionMsg to be translated
	 * @return a corresponding OneMoreMainAction
	 * @throws InvalidActionException if the action can't be be built because it couldn't be executed: 
	 * when the player can't do a quick action or when the the number of assistants of the player is <3
	 * @throws NullPointerException if the parameter passed is null
	 */
	@Override
	public OneMoreMainAction build(OneMoreMainActionMsg actionMsg) throws InvalidActionException{
		
		Preconditions.checkNotNull(actionMsg,ERROR_MESSAGE);
		
		Player player = model.getPlayer(actionMsg.getPlayerID());
		
		checkQuickAction(player);
		
		if (player.getAssistants().getValue() < 3)
				throw new InvalidActionException(NOT_ENOUGH_ASSISTANTS);
		
		return new OneMoreMainAction();
	}

	/**
	 * It builds a PermitTileAction from the PermitTileActionMsg passed as a parameter
	 * @param actionMsg a PermitTileActionMsg to be translated
	 * @return a corresponding PermitTileAction
	 * @throws InvalidActionException if the action can't be be built because it couldn't be executed: 
	 * when the player can't do a main action or when the number of coins required is < of the number of coins of the player
	 * @throws NullPointerException if the parameter passed is null
	 */
	@Override
	public PermitTileAction build(PermitTileActionMsg actionMsg) throws InvalidActionException{
		
		Preconditions.checkNotNull(actionMsg,ERROR_MESSAGE);
		
		Player player = model.getPlayer(actionMsg.getPlayerID());
		checkMainAction(player);
		
		Region region = model.getRegion(actionMsg.getRegionCode() - 1);
		int tilePosition= actionMsg.getTilePositionCode()-1;
		
		ArrayList<PoliticCard> choosenPoliticCards= new ArrayList<>();
		
		CardDeck<PoliticCard> deck = model.getPoliticCardsDeck();
		
		for(int i=0;i<actionMsg.getChosenPoliticCardCodes().length;i++){
			PoliticCard politicCard= player.getCard(Integer.parseInt(actionMsg.getChosenPoliticCardCodes()[i])-1);
			choosenPoliticCards.add(politicCard);
		}
		
		PermitTileAction action = new PermitTileAction(region, tilePosition, choosenPoliticCards,deck);
		
		if(player.getCoins().getValue() < action.coinsToPay())
			throw new InvalidActionException("Not enough money");
		
		return action;

	}
	
	/**
	 * It builds a SendAssistantAction from the SendAssistantMsg passed as a parameter
	 * @param actionMsg an SendAssistantActionMsg to be translated
	 * @return a corresponding SendAssistantAction
	 * @throws InvalidActionException if the action can't be be built because it couldn't be executed: 
	 * when the player can't do a quick action or when the the number of assistants of the player is <1
	 * @throws NullPointerException if the parameter passed is null
	 */
	@Override
	public SendAssistantAction build(SendAssistantActionMsg actionMsg) throws InvalidActionException{
		
		Preconditions.checkNotNull(actionMsg,ERROR_MESSAGE);
		
		Player player = model.getPlayer(actionMsg.getPlayerID());
		
		checkQuickAction(player);
		
		Balcony balcony;
		if(actionMsg.getBalconyCode()== (actionMsg.getRegionNumber()+1)){
			balcony = model.getMap().getKingCouncil();
		}
		else {
		balcony= model.getRegion(actionMsg.getBalconyCode()-1).getBalcony();
		}
		
		Councillor councillor = model.getCouncillor(actionMsg.getCouncillorCode()-1);
		
		List<Councillor> councillors = model.getNoblesPool();
		
		if (player.getAssistants().getValue() < 1)
			throw new InvalidActionException(NOT_ENOUGH_ASSISTANTS);
			
		return new SendAssistantAction(balcony, councillor,councillors);
	}
	
	/**
	 * It builds a ReuseCityBonusAction from the ReuseCityBonusMsg passed as a parameter
	 * @param actionMsg an ReuseCityBonusActionMsg to be translated
	 * @return a corresponding ReuseCityBonusAction
	 * @throws InvalidActionException if the action can't be be built because it couldn't be executed: 
	 * when the number of emporium built by the player is < number of cities on the bonus or
	 * when turn related to player has not set isBonus to true
	 * @throws NullPointerException if the parameter passed is null
	 */
	@Override
	public ReuseCityBonusAction build(ReuseCityBonusActionMsg actionMsg) throws InvalidActionException{
		
		Preconditions.checkNotNull(actionMsg,ERROR_MESSAGE);
		
		Player player = model.getPlayer(actionMsg.getPlayerID());
		
		if(!player.getTurn().isBonus())
			throw new InvalidActionException("No bonus to gain!");
		
		if(actionMsg.getNumbReuseCities() > (10-player.getEmporiumNumber()))
			throw new InvalidActionException("Not Enough Emporiums");
		
		List<Town> townsChecked = actionMsg.checkTowns(model);
		List<Town> townsChosen = new ArrayList<>();
		
		int[] codes = actionMsg.getCodesInt(actionMsg.getCodes());
		
		for(int i=0;i<codes.length;i++)
		{
			Town town = townsChecked.get(codes[i]-1); 
			townsChosen.add(town);
		}
		
		return new ReuseCityBonusAction(townsChosen);
	}
	
	/**
	 * It builds a ReusePermitBonusAction from the ReusePermitBonusMsg passed as a parameter
	 * @param actionMsg an ReusePermitBonusActionMsg to be translated
	 * @return a corresponding ReusePermitBonusAction
	 * @throws InvalidActionException if the action can't be be built because it couldn't be executed:
	 * when the number of ReusePermit on the bonus is > (number of used Tile of the player + number of possessed tile of the player)
	 * or when turn related to player has not set isBonus to true
	 * @throws NullPointerException if the parameter passed is null
	 */
	@Override
	public ReusePermitBonusAction build(ReusePermitBonusActionMsg actionMsg) throws InvalidActionException{
		
		Preconditions.checkNotNull(actionMsg,ERROR_MESSAGE);
		
		Player player = model.getPlayer(actionMsg.getPlayerID());
		
		if(!player.getTurn().isBonus())
			throw new InvalidActionException("No bonus to gain!");
		
		if(actionMsg.getNumbReusePermit() > player.getUsedTiles().size()+player.getPossessedTiles().size())
			throw new InvalidActionException("Not enough used Permit Tiles");
		
		List<BusinessPermitTile> tilesChecked = actionMsg.checkTiles(model);
		List<BusinessPermitTile> permitTileChosen= new ArrayList<>();
		
		int[] codes = actionMsg.getCodesInt(actionMsg.getCodes());
		
		for(int i=0;i<codes.length;i++)
		{
			BusinessPermitTile tile = tilesChecked.get(codes[i]-1);
			permitTileChosen.add(tile);
		}
		
		return new ReusePermitBonusAction(permitTileChosen);
	}
	
	/**
	 * It builds a TakePermitTileBonusAction from the TakePermitTileBonusMsg passed as a parameter
	 * @param actionMsg an TakePermitTileBonusActionMsg to be translated
	 * @return a corresponding TakePermitTileBonusAction
	 * @throws InvalidActionException if the action can't be be built because it couldn't be executed:
	 * when turn related to player has not set isBonus to true.
	 * @throws NullPointerException if the parameter passed is null
	 */
	@Override
	public TakePermitTileBonusAction build(TakePermitTileBonusActionMsg actionMsg) throws InvalidActionException{
		
		Preconditions.checkNotNull(actionMsg,ERROR_MESSAGE);
		
		Player player = model.getPlayer(actionMsg.getPlayerID());
		
		if(!player.getTurn().isBonus())
			throw new InvalidActionException("No bonus to gain!");
		
		List<BusinessPermitTile> tilesChecked = actionMsg.checkTiles(model);
		ArrayList<BusinessPermitTile> tilesChosen = new ArrayList<>();
		
		int[] codes = actionMsg.getCodesInt(actionMsg.getCodes());
		
		for(int i=0;i<codes.length;i++){
			
			BusinessPermitTile tile = tilesChecked.get(codes[i]-1);
			tilesChosen.add(tile);	
		}
		
		return new TakePermitTileBonusAction(tilesChosen, model.getRegions());
	}

	
	/**
	 * It builds a SellPoliticCardsAction from the SellPoliticCardsActionMsg passed as a parameter
	 * @param actionMsg an SellPoliticCardsActionMsg to be translated
	 * @return a corresponding SellPoliticCardsAction
	 * @throws InvalidActionException if the action can't be be built because it couldn't be executed: 
	 * when the politic Card you want to sell can't be found in the player's hand.
	 * @throws NullPointerException if the parameter passed is null
	 */
	@Override
	public Action build(SellPoliticCardsActionMsg actionMsg) throws InvalidActionException{
		
		Preconditions.checkNotNull(actionMsg,ERROR_MESSAGE);

		Player player = model.getPlayer(actionMsg.getPlayerID());
		
		List<PoliticCard> cards = new ArrayList<>();
		List<PoliticCard> playerCards = player.getPoliticCardsHand();
		
		int[] codes = actionMsg.getCodesInt(actionMsg.getCodes());
		
		try{
			
		for(int i = 0; i< (actionMsg.getCodes().length - 1); i++)
			cards.add(playerCards.get(codes[i] - 1));
		
		} catch (NullPointerException e){
			log.log(Level.WARNING, e.getMessage(), e);
			throw new InvalidActionException("Politic Card Not Found!");
		}
		
		int price = actionMsg.getPrice();
		
		if (price < 0)
			throw new InvalidActionException("Price can't be negative!");
		
		return new SellPoliticCardsAction(cards, price, bazaarModel);
		
	}
	
	
	/**
	 * It builds a SellAssistantsAction from the SellAssistantsActionMsg passed as a parameter
	 * @param actionMsg - an SellAssistantsActionMsg to be translated.
	 * @return a corresponding SellAssistantsAction
	 * @throws InvalidActionException if the action can't be be built because it couldn't be executed: 
	 * when the number of assistants you want to sell is < of the number of assistants of the player 
	 * @throws NullPointerException if the parameter passed is null
	 */
	@Override
	public Action build(SellAssistantsActionMsg actionMsg) throws InvalidActionException{
		
		Preconditions.checkNotNull(actionMsg,ERROR_MESSAGE);
		
		Player player = model.getPlayer(actionMsg.getPlayerID());
		int assistantsToSell = actionMsg.getNumbAssistantsToSell();
		
		if (player.getAssistants().getValue() < assistantsToSell)
			throw new InvalidActionException(NOT_ENOUGH_ASSISTANTS);
		
		int price = actionMsg.getPrice();
		
		if (price < 0)
			throw new InvalidActionException("Price can't be negative!");
		
		return new SellAssistantsAction(assistantsToSell, price, bazaarModel);
	}

	
	/**
	 * It builds a SellPermitTilesAction from the SellPermitTilesActionMsg passed as a parameter
	 * @param actionMsg an SellAssistantsActionMsg to be translated.
	 * @return a corresponding SellPermitTilesAction
	 * @throws InvalidActionException if the action can't be be built because it couldn't be executed: 
	 * when the permitTiles you want to sell can't be found in the player's hand
	 * @throws NullPointerException if the parameter passed is null
	 */
	@Override
	public Action build(SellPermitTilesActionMsg actionMsg) throws InvalidActionException {
		
		Preconditions.checkNotNull(actionMsg,ERROR_MESSAGE);

		Player player = model.getPlayer(actionMsg.getPlayerID());
		
		List<BusinessPermitTile> tiles = new ArrayList<>();
		List<BusinessPermitTile> playerTiles = player.getPossessedTiles();
		
		int[] codes = actionMsg.getCodesInt(actionMsg.getCodes());
		
		try{
			
			for(int i = 0; i< (actionMsg.getCodes().length - 1); i++)
				tiles.add(playerTiles.get(codes[i] - 1));
		
		} catch (NullPointerException e){
			log.log(Level.WARNING, e.getMessage(), e);
			throw new InvalidActionException("PermitTile Not Found");
		}
		
		int price = actionMsg.getPrice();
		
		if (price < 0)
			throw new InvalidActionException("Price can't be negative!");
		
		return new SellPermitTilesAction(tiles, price, bazaarModel);
		
	}


	/**
	 * It builds a BuySalableAction from the BuySalableActionMsg passed as a parameter.
	 * @param actionMsg - a BuySalableActionMsg to be translated
	 * @return a corresponding BuySalableAction
	 * @throws InvalidActionException if the action can't be be built because it couldn't be executed:
	 * when the item the player wants to buy is not on the shelf or when the players has not enough coins to buy the item
	 * @throws NullPointerException if the parameter passed is null
	 */
	@Override
	public Action build(BuySalableActionMsg actionMsg) throws InvalidActionException {
		
		Preconditions.checkNotNull(actionMsg,ERROR_MESSAGE);
		
		List<ProductOnSale<?>> shelf = bazaarModel.getShelf(actionMsg.getPlayerID());
		List<TProduct> tShelf = bazaarModel.gettShelf(actionMsg.getPlayerID());
		
		int[] codes = actionMsg.getCodesInt(actionMsg.getCodes());
		
		if (codes[0] < 1 || codes[0] > shelf.size())
			throw new InvalidActionException("Apu says: Item not found!");
		
		int numbCoins = model.getPlayer(actionMsg.getPlayerID()).getCoins().getValue();
		
		if (shelf.get(codes[0] - 1).getPrice() > numbCoins)
			throw new InvalidActionException("Apu says: Not Enough Money!");
		
		ProductOnSale<?> product = shelf.get(codes[0] - 1);
		TProduct tProduct = tShelf.get(codes[0] -1);
		
		return new BuySalableAction(product, tProduct, bazaarModel);
		
	}

}
