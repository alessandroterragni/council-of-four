package it.polimi.ingsw.cg28.controller.actions;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.controller.PlayerTurn;
import it.polimi.ingsw.cg28.controller.bazaar.ProductOnSale;
import it.polimi.ingsw.cg28.controller.bazaar.SoldAssistant;
import it.polimi.ingsw.cg28.controller.bazaar.SoldStrategy;
import it.polimi.ingsw.cg28.controller.bonus.AssistantBonus;
import it.polimi.ingsw.cg28.controller.bonus.Bonus;
import it.polimi.ingsw.cg28.exception.InvalidActionException;
import it.polimi.ingsw.cg28.model.BazaarModel;
import it.polimi.ingsw.cg28.model.BusinessPermitTile;
import it.polimi.ingsw.cg28.model.Councillor;
import it.polimi.ingsw.cg28.model.GameMap;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.PoliticCard;
import it.polimi.ingsw.cg28.model.Town;
import it.polimi.ingsw.cg28.model.parser.ModelTestConfiguration;
import it.polimi.ingsw.cg28.tmodel.TProductCards;
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

public class DefaultActionFactoryTest {

	private ModelStatus model;
	private BazaarModel bazaar;
	private PlayerID [] players;
	private PlayerTurn turn;
	
	@Before
	public void before(){
		
		players = new PlayerID[2];
		players[0] = new PlayerID("Player1");
		players[1] = new PlayerID("Player2");
		
		ModelTestConfiguration config = new ModelTestConfiguration();
		model = config.buildModelTest(players);
		turn = new PlayerTurn(players[0]);
		model.getPlayer(players[0]).setTurn(turn);
		
		bazaar = new BazaarModel();
		
	}
	
	@Test (expected= NullPointerException.class)
	public void testDefaultActionFactoryWithNullParameter() {
		new DefaultActionFactory(null);
	}
	
	@Test 
	public void testDefaultActionFactory() {
		DefaultActionFactory factory = new DefaultActionFactory(model);
		assertNotNull("the factory has been correctly built", factory);
	}

	@Test (expected= NullPointerException.class)
	public void testSetBazaarModelWithNullParameter() {
		DefaultActionFactory factory = new DefaultActionFactory(model);
		factory.setBazaarModel(null);
	}
	
	@Test
	public void testSetBazaarModel() {
		DefaultActionFactory factory = new DefaultActionFactory(model);
		factory.setBazaarModel(new BazaarModel());
		assertTrue(true);
	}
	
	@Test (expected= NullPointerException.class)
	public void testBuildChangeTileActionMsgWithNullParamter() throws InvalidActionException {
		DefaultActionFactory factory = new DefaultActionFactory(model);
		ChangeTileActionMsg msg = null; 
		factory.build(msg);
	}

	@Test 
	public void testBuildChangeTileActionMsg() throws InvalidActionException {
		DefaultActionFactory factory = new DefaultActionFactory(model);
		ChangeTileActionMsg msg = new ChangeTileActionMsg(players[0]); 
		msg.fill(model);
		String[] codes = {"1"};
		assertTrue(msg.setCodes(codes));
		assertNotNull("The action is correctly built", msg.translate(factory));
	}
	
	@Test (expected = InvalidActionException.class)
	public void testBuildChangeTileActionMsgWithNOtEnoughAssistants() throws InvalidActionException {
		DefaultActionFactory factory = new DefaultActionFactory(model);
		model.getPlayer(players[0]).getAssistants().decrement(1);
		ChangeTileActionMsg msg = new ChangeTileActionMsg(players[0]);
		msg.fill(model);
		String[] codes = {"1"};
		msg.setCodes(codes);
		msg.translate(factory);
	}
	

	@Test (expected= NullPointerException.class)
	public void testBuildElectionActionMsgWithNullParameter() throws InvalidActionException {
		DefaultActionFactory factory = new DefaultActionFactory(model);
		ElectionActionMsg msg = null;
		factory.build(msg);
	}
	
	@Test
	public void testBuildElectionActionMsg() throws InvalidActionException {
		DefaultActionFactory factory = new DefaultActionFactory(model);
		ElectionActionMsg msg = new ElectionActionMsg(players[0]);
		msg.fill(model);
		String[] codes = {"1","1"};
		assertTrue(msg.setCodes(codes));
		assertNotNull("The action is correctly built",msg.translate(factory));
	}


	@Test ( expected = NullPointerException.class)
	public void testBuildEmporiumKingActionMsgWithNullParamter() throws InvalidActionException {
		DefaultActionFactory factory = new DefaultActionFactory(model);
		EmporiumKingActionMsg msg = null;
		factory.build(msg);
	}

	@Test 
	public void testBuildEmporiumKingActionMsg() throws InvalidActionException {
		model.getRegion(0).getBalcony().addCouncillor(new Councillor(Color.BLUE));
		model.getPlayer(players[0]).getPoliticCardsHand().remove(0);
		model.getPlayer(players[0]).getPoliticCardsHand().remove(0);
		model.getPlayer(players[0]).getPoliticCardsHand().add(new PoliticCard(null, true));
		DefaultActionFactory factory = new DefaultActionFactory(model);
		EmporiumKingActionMsg msg = new EmporiumKingActionMsg(players[0]);
		msg.fill(model);
		String[] codes = {"1","1"};
		assertTrue(msg.setCodes(codes));
		model.getPlayer(players[0]).getCoins().increment(50);
		model.getPlayer(players[0]).getAssistants().increment(50);
		assertNotNull("The action is correctly built", msg.translate(factory));
	}
	
	@Test (expected = InvalidActionException.class )
	public void testBuildEmporiumKingActionMsgWithnotEnoughMoney() throws InvalidActionException {
		model.getRegion(0).getBalcony().addCouncillor(new Councillor(Color.BLUE));
		model.getPlayer(players[0]).getPoliticCardsHand().remove(0);
		model.getPlayer(players[0]).getPoliticCardsHand().remove(0);
		model.getPlayer(players[0]).getPoliticCardsHand().add(new PoliticCard(null, true));
		DefaultActionFactory factory = new DefaultActionFactory(model);
		EmporiumKingActionMsg msg = new EmporiumKingActionMsg(players[0]);
		msg.fill(model);
		String[] codes = {"1","1"};
		msg.setCodes(codes);
		model.getPlayer(players[0]).getCoins().decrement(10);
		model.getPlayer(players[0]).getAssistants().increment(50);
		msg.translate(factory);
	}
	
	@Test (expected = InvalidActionException.class )
	public void testBuildEmporiumKingActionMsgWithAlreadyAnEmporium() throws InvalidActionException {
		
		model.getRegion(0).getBalcony().addCouncillor(new Councillor(Color.BLUE));
		model.getPlayer(players[0]).getPoliticCardsHand().remove(0);
		model.getPlayer(players[0]).getPoliticCardsHand().remove(0);
		model.getPlayer(players[0]).getPoliticCardsHand().add(new PoliticCard(null, true));
		DefaultActionFactory factory = new DefaultActionFactory(model);
		EmporiumKingActionMsg msg = new EmporiumKingActionMsg(players[0]);
		msg.fill(model);
		String[] codes = {"1","1"};
		msg.setCodes(codes);
		Town town = model.getMap().getTown(msg.getMap().getTownList().get(0).getName());
		GameMap map = model.getMap();
		map.addEmporium(players[0], town);
		model.getPlayer(players[0]).getCoins().increment(50);
		model.getPlayer(players[0]).getAssistants().increment(50);
		msg.translate(factory);
		
	}
	

	@Test (expected = InvalidActionException.class )
	public void testBuildEmporiumKingActionMsgWithoutEnoughAssistants() throws InvalidActionException {
		model.getRegion(0).getBalcony().addCouncillor(new Councillor(Color.BLUE));
		model.getPlayer(players[0]).getPoliticCardsHand().remove(0);
		model.getPlayer(players[0]).getPoliticCardsHand().remove(0);
		model.getPlayer(players[0]).getPoliticCardsHand().add(new PoliticCard(null, true));
		DefaultActionFactory factory = new DefaultActionFactory(model);
		EmporiumKingActionMsg msg = new EmporiumKingActionMsg(players[0]);
		msg.fill(model);
		String[] codes = {"1","1"};
		msg.setCodes(codes);
		Town town = model.getMap().getTown(msg.getMap().getTownList().get(0).getName());
		GameMap map = model.getMap();
		map.addEmporium(players[1], town);
		model.getPlayer(players[0]).getCoins().increment(50);
		model.getPlayer(players[0]).getAssistants().decrement(1);
		msg.translate(factory);
	}
	

	@Test (expected = NullPointerException.class)
	public void testBuildEmporiumTileActionMsgWitNullParamater() throws InvalidActionException {
		DefaultActionFactory factory = new DefaultActionFactory(model);
		EmporiumTileActionMsg msg = null;
		factory.build(msg);
	}
	
	@Test 
	public void testBuildEmporiumTileActionMsg() throws InvalidActionException {
		Bonus bonuses = new AssistantBonus(85);
		String[] letters = {"Arkon"};
		model.getPlayer(players[0]).getPossessedTiles().add(new BusinessPermitTile(bonuses, letters));
		DefaultActionFactory factory = new DefaultActionFactory(model);
		EmporiumTileActionMsg msg = new EmporiumTileActionMsg(players[0]);
		msg.fill(model);
		String[] codes = {"1","1"};
		assertTrue(msg.setCodes(codes));
		assertNotNull("The action is correctly built", msg.translate(factory));
	}
	
	
	@Test (expected = InvalidActionException.class)
	public void testBuildEmporiumTileActionMsgWithAlreadyAnEmporium() throws InvalidActionException {
		Bonus bonuses = new AssistantBonus(85);
		String[] letters = {"Arkon"};
		model.getPlayer(players[0]).getPossessedTiles().add(new BusinessPermitTile(bonuses, letters));
		DefaultActionFactory factory = new DefaultActionFactory(model);
		EmporiumTileActionMsg msg = new EmporiumTileActionMsg(players[0]);
		msg.fill(model);
		String[] codes = {"1","1"};
		msg.setCodes(codes);
		Town town = model.getMap().getTown(0);
		GameMap map = model.getMap();
		map.addEmporium(players[0], town);
		factory.build(msg);
	}
	
	@Test (expected = InvalidActionException.class)
	public void testBuildEmporiumTileActionMsgWithNotEnoughAssistants() throws InvalidActionException {
		Bonus bonuses = new AssistantBonus(85);
		String[] letters = {"Arkon"};
		model.getPlayer(players[0]).getPossessedTiles().add(new BusinessPermitTile(bonuses, letters));
		DefaultActionFactory factory = new DefaultActionFactory(model);
		EmporiumTileActionMsg msg = new EmporiumTileActionMsg(players[0]);
		msg.fill(model);
		String[] codes = {"1","1"};
		msg.setCodes(codes);
		Town town = model.getMap().getTown(0);
		GameMap map = model.getMap();
		map.addEmporium(players[1], town);
		model.getPlayer(players[0]).getAssistants().decrement(1);
		factory.build(msg);
	}
	


	@Test 
	public void testBuildHireAssistantActionMsg() throws InvalidActionException {
		DefaultActionFactory factory = new DefaultActionFactory(model);
		HireAssistantActionMsg msg = new HireAssistantActionMsg(players[0]);
		assertNotNull("The action is correctly built",factory.build(msg));
	}
	
	@Test (expected = NullPointerException.class)
	public void testBuildHireAssistantActionMsgWithNullParamater() throws InvalidActionException {
		DefaultActionFactory factory = new DefaultActionFactory(model);
		HireAssistantActionMsg msg = null;
		factory.build(msg);
	}
	
	@Test (expected = InvalidActionException.class)
	public void testBuildHireAssistantActionMsgWithoutEnoughMoney() throws InvalidActionException {
		DefaultActionFactory factory = new DefaultActionFactory(model);
		HireAssistantActionMsg msg = new HireAssistantActionMsg(players[0]);
		model.getPlayer(players[0]).getCoins().decrement(8);
		msg.translate(factory);
		
	}
	
	@Test
	public void testBuildOneMoreMainActionMsg() throws InvalidActionException {
		DefaultActionFactory factory = new DefaultActionFactory(model);
		OneMoreMainActionMsg msg = new OneMoreMainActionMsg(players[0]);
		model.getPlayer(players[0]).getAssistants().increment(8);
		assertNotNull("The action is correctly built",factory.build(msg));	
	}
	
	@Test (expected = NullPointerException.class)
	public void testBuildOneMoreMainActionMsgWithNullParamter() throws InvalidActionException {
		DefaultActionFactory factory = new DefaultActionFactory(model);
		OneMoreMainActionMsg msg = null;
		factory.build(msg);	
	}

	@Test (expected = InvalidActionException.class)
	public void testBuildOneMoreMainActionMsgWithoutEnoughAssistants() throws InvalidActionException {
		DefaultActionFactory factory = new DefaultActionFactory(model);
		OneMoreMainActionMsg msg = new OneMoreMainActionMsg(players[0]);
		msg.translate(factory);	
	}
	
	
	@Test (expected = NullPointerException.class)
	public void testBuildPermitTileActionMsgWithNullParameter() throws InvalidActionException {
		DefaultActionFactory factory = new DefaultActionFactory(model);
		PermitTileActionMsg msg = null;
		factory.build(msg);
	}
	
	@Test
	public void testBuildPermitTileActionMsg() throws InvalidActionException {
		DefaultActionFactory factory = new DefaultActionFactory(model);
		PermitTileActionMsg msg = new PermitTileActionMsg(players[0]);
		model.getRegion(0).getBalcony().addCouncillor(new Councillor(Color.BLUE));
		model.getPlayer(players[0]).getPoliticCardsHand().remove(0);
		model.getPlayer(players[0]).getPoliticCardsHand().remove(0);
		model.getPlayer(players[0]).getPoliticCardsHand().add(new PoliticCard(null, true));
		msg.fill(model);
		String[] codes = {"1","1","1"};
		assertTrue(msg.setCodes(codes));
		assertNotNull("The action is correctly built", msg.translate(factory));
	}
	
	@Test (expected = InvalidActionException.class)
	public void testBuildPermitTileActionMsgWithoutEnoughCoins() throws InvalidActionException {
		DefaultActionFactory factory = new DefaultActionFactory(model);
		PermitTileActionMsg msg = new PermitTileActionMsg(players[0]);
		model.getRegion(0).getBalcony().addCouncillor(new Councillor(Color.BLUE));
		model.getPlayer(players[0]).getPoliticCardsHand().remove(0);
		model.getPlayer(players[0]).getPoliticCardsHand().remove(0);
		model.getPlayer(players[0]).getPoliticCardsHand().add(new PoliticCard(null, true));
		msg.fill(model);
		String[] codes = {"1","1","1"};
		msg.setCodes(codes);
		model.getPlayer(players[0]).getCoins().decrement(10);
		msg.translate(factory);
	}

	@Test (expected = NullPointerException.class)
	public void testBuildSendAssistantActionMsgWthNullParameter() throws InvalidActionException {
		DefaultActionFactory factory = new DefaultActionFactory(model);
		SendAssistantActionMsg msg = null;
		factory.build(msg);
	}

	@Test (expected = InvalidActionException.class)
	public void testBuildSendAssistantActionMsgWithNotENoughAssistants() throws InvalidActionException {
		DefaultActionFactory factory = new DefaultActionFactory(model);
		SendAssistantActionMsg msg = new SendAssistantActionMsg(players[0]);
		model.getNoblesPool().clear();
		model.getNoblesPool().add(new Councillor(Color.LIGHT_GRAY));
		model.getRegion(0).getBalcony().addCouncillor(new Councillor(Color.LIGHT_GRAY));
		msg.fill(model);
		String[] codes = {"1","1"};
		msg.setCodes(codes);
		model.getPlayer(players[0]).getAssistants().decrement(1);
		msg.translate(factory);
	}
	
	@Test 
	public void testBuildSendAssistantActionMsg() throws InvalidActionException {
		DefaultActionFactory factory = new DefaultActionFactory(model);
		SendAssistantActionMsg msg = new SendAssistantActionMsg(players[0]);
		model.getNoblesPool().clear();
		model.getNoblesPool().add(new Councillor(Color.LIGHT_GRAY));
		model.getRegion(0).getBalcony().addCouncillor(new Councillor(Color.LIGHT_GRAY));
		msg.fill(model);
		String[] codes = {"1","1"};
		assertTrue(msg.setCodes(codes));
		assertNotNull("The action is correctly built",factory.build(msg));
	}

	
	@Test (expected = NullPointerException.class)
	public void testBuildReuseCityBonusActionMsgWithNullParameter() throws InvalidActionException {
		DefaultActionFactory factory = new DefaultActionFactory(model);
		ReuseCityBonusActionMsg msg = null;
		factory.build(msg);
	}

	@Test (expected = InvalidActionException.class)
	public void testBuildReuseCityBonusActionMsgWithoutEnoughEmporiums() throws InvalidActionException {
		DefaultActionFactory factory = new DefaultActionFactory(model);
		ReuseCityBonusActionMsg msg = new ReuseCityBonusActionMsg(100, players[0]);
		msg.fill(model);
		String[] codes = {"1"};
		msg.setCodes(codes);
		msg.translate(factory);
	}
	
	@Test 
	public void testBuildReuseCityBonusActionMsg() throws InvalidActionException {
		DefaultActionFactory factory = new DefaultActionFactory(model);
		ReuseCityBonusActionMsg msg = new ReuseCityBonusActionMsg(1, players[0]);
		model.getMap().addEmporium(players[0], model.getMap().getTown("Arkon"));
		model.getPlayer(players[0]).buildEmporium();
		msg.fill(model);
		String[] codes = {"1"};
		assertTrue(msg.setCodes(codes));
		turn.setBonus(true);
		assertNotNull("The action is correctly built", msg.translate(factory));
	}
	
	@Test (expected = NullPointerException.class)
	public void testBuildReusePermitBonusActionMsgWithNullParameter() throws InvalidActionException {
		DefaultActionFactory factory = new DefaultActionFactory(model);
		ReusePermitBonusActionMsg msg = null;
		factory.build(msg);
	}
	
	@Test (expected = InvalidActionException.class)
	public void testBuildReusePermitBonusActionMsgWithoutEnoughUsedTiles() throws InvalidActionException {
		DefaultActionFactory factory = new DefaultActionFactory(model);
		ReusePermitBonusActionMsg msg = new ReusePermitBonusActionMsg(10, players[0]);
		msg.fill(model);
		msg.translate(factory);
	}
	
	@Test 
	public void testBuildReusePermitBonusAction() throws InvalidActionException {
		DefaultActionFactory factory = new DefaultActionFactory(model);
		ReusePermitBonusActionMsg msg = new ReusePermitBonusActionMsg(1, players[0]);
		Bonus bonuses = new AssistantBonus(5);
		String[] letters = {"ciao"};
		model.getPlayer(players[0]).getUsedTiles().add(new BusinessPermitTile(bonuses, letters));
		msg.fill(model);
		String[] codes = {"1"};
		assertTrue(msg.setCodes(codes));
		turn.setBonus(true);
		assertNotNull("The action is correctly built", msg.translate(factory));
	}
	

	@Test (expected = NullPointerException.class)
	public void testBuildTakePermitTileBonusActionMsgWithNullParameter() throws InvalidActionException {
		DefaultActionFactory factory = new DefaultActionFactory(model);
		TakePermitTileBonusActionMsg msg = null;
		factory.build(msg);
	}
	
	@Test 
	public void testBuildTakePermitTileBonusActionMsg() throws InvalidActionException {
		DefaultActionFactory factory = new DefaultActionFactory(model);
		TakePermitTileBonusActionMsg msg = new TakePermitTileBonusActionMsg(1, players[0]);
		msg.fill(model);
		String[] codes = {"1"};
		assertTrue(msg.setCodes(codes));
		turn.setBonus(true);
		assertNotNull("The action is correctly built", msg.translate(factory));
	}
	
	@Test
	public void testBuildSellAssistantsActionMsg() throws InvalidActionException {
		
		DefaultActionFactory factory = new DefaultActionFactory(model);
		factory.setBazaarModel(bazaar);
		model.getPlayer(players[0]).getAssistants().increment(2);
		SellAssistantsActionMsg msg = new SellAssistantsActionMsg(players[0]);
		msg.fill(model);
		String[] codes = {"1","2"};
		assertTrue(msg.setCodes(codes));
		assertNotNull("The action is correctly built", msg.translate(factory));
		
	}
	
	@Test (expected = NullPointerException.class)
	public void testBuildSellAssistantsActionMsgWithNullParameter() throws InvalidActionException {
		DefaultActionFactory factory = new DefaultActionFactory(model);
		factory.setBazaarModel(bazaar);
		SellAssistantsActionMsg msg = null;
		factory.build(msg);
	}
	
	@Test (expected = InvalidActionException.class)
	public void testBuildSellAssistantsActionMsgWithNotEnoughAssistants() throws InvalidActionException {
		
		DefaultActionFactory factory = new DefaultActionFactory(model);
		factory.setBazaarModel(bazaar);
		model.getPlayer(players[0]).getAssistants().setValue(0);
		SellAssistantsActionMsg msg = new SellAssistantsActionMsg(players[0]);
		msg.fill(model);
		String[] codes = {"1","2"};
		assertTrue(msg.setCodes(codes));
		msg.translate(factory);
	}
	
	

	@Test
	public void  testBuildSellPoliticCardsActionMsg() throws InvalidActionException {
		
		DefaultActionFactory factory = new DefaultActionFactory(model);
		factory.setBazaarModel(bazaar);
		SellPoliticCardsActionMsg msg = new SellPoliticCardsActionMsg(players[0]);
		msg.fill(model);
		String[] codes = {"1","2"};
		assertTrue(msg.setCodes(codes));
		assertNotNull("The action is correctly built", msg.translate(factory));
		
	}
	
	@Test (expected = NullPointerException.class)
	public void testBuildSellPoliticCardsActionMsgWithNullParameter() throws InvalidActionException {
		DefaultActionFactory factory = new DefaultActionFactory(model);
		factory.setBazaarModel(bazaar);
		SellPoliticCardsActionMsg msg = null;
		factory.build(msg);
	}
	
	@Test (expected = InvalidActionException.class)
	public void testBuildSellPoliticCardsActionMsgWithNotEnoughPoliticCards() throws InvalidActionException {

		DefaultActionFactory factory = new DefaultActionFactory(model);
		factory.setBazaarModel(bazaar);
		model.getPlayer(players[0]).getPoliticCardsHand().clear();;
		SellPoliticCardsActionMsg msg = new SellPoliticCardsActionMsg(players[0]);
		msg.fill(model);
		String[] codes = {"1","2"};
		assertTrue(msg.setCodes(codes));
		msg.translate(factory);
		
	}

	@Test
	public void testBuildSellPermitTilesActionMsg() throws InvalidActionException {
		
		DefaultActionFactory factory = new DefaultActionFactory(model);
		factory.setBazaarModel(bazaar);
		SellPermitTilesActionMsg msg = new SellPermitTilesActionMsg(players[0]);
		model.getPlayer(players[0]).getPossessedTiles().add(model.getRegion(0).getTile(0));
		msg.fill(model);
		String[] codes = {"1","2"};
		assertTrue(msg.setCodes(codes));
		assertNotNull("The action is correctly built", msg.translate(factory));
		
	}
	
	@Test (expected = NullPointerException.class)
	public void testBuildSellPermitTilesActionMsgWithNullParameter() throws InvalidActionException {
		DefaultActionFactory factory = new DefaultActionFactory(model);
		factory.setBazaarModel(bazaar);
		SellPermitTilesActionMsg msg = null;
		factory.build(msg);
	}
	
	@Test (expected = InvalidActionException.class)
	public void testBuildSellPermitTilesActionMsgWithNotEnoughPermitTiles() throws InvalidActionException {
		
		DefaultActionFactory factory = new DefaultActionFactory(model);
		factory.setBazaarModel(bazaar);
		SellPermitTilesActionMsg msg = new SellPermitTilesActionMsg(players[0]);
		msg.fill(model);
		String[] codes = {"1","2"};
		assertTrue(msg.setCodes(codes));
		msg.translate(factory);
		
	}
	
	@Test
	public void testBuildBuySalableActionMsg() throws InvalidActionException {
		
		BazaarModel bazaarModel = new BazaarModel();
		List<PoliticCard> cards = model.getPlayer(players[1]).getPoliticCardsHand();
		SoldStrategy howToSold = new SoldAssistant();
		ProductOnSale<PoliticCard> product = new ProductOnSale<PoliticCard>(cards, 2, model.getPlayer(players[1]), howToSold);
		
		Color[] tCards = new Color[cards.size()];
		
		for(int i=0; i< cards.size(); i++)
			tCards[i] = cards.get(i).getHouseColor();
		
		TProductCards tProductCards = new TProductCards(2, players[1].getName(), tCards);
		
		bazaarModel.addItem(product, tProductCards);
		
		BuySalableActionMsg actionMsg = new BuySalableActionMsg(players[0]);
		actionMsg.fill(model, bazaarModel);
		String[] codes = {"1"};
		actionMsg.setCodes(codes);
		
		DefaultActionFactory factory = new DefaultActionFactory(model);
		factory.setBazaarModel(bazaarModel);
		assertNotNull("The action is correctly built", actionMsg.translate(factory));
		
	}
	
	@Test (expected = InvalidActionException.class)
	public void testBuildBuySalableActionMsgWithNotEnoughMoney() throws InvalidActionException {
		
		BazaarModel bazaarModel = new BazaarModel();
		List<PoliticCard> cards = model.getPlayer(players[1]).getPoliticCardsHand();
		SoldStrategy howToSold = new SoldAssistant();
		ProductOnSale<PoliticCard> product = new ProductOnSale<PoliticCard>(cards, 2, model.getPlayer(players[1]), howToSold);
		
		Color[] tCards = new Color[cards.size()];
		
		for(int i=0; i< cards.size(); i++)
			tCards[i] = cards.get(i).getHouseColor();
		
		TProductCards tProductCards = new TProductCards(2, players[1].getName(), tCards);
		
		bazaarModel.addItem(product, tProductCards);
		
		BuySalableActionMsg actionMsg = new BuySalableActionMsg(players[0]);
		actionMsg.fill(model, bazaarModel);
		String[] codes = {"1"};
		actionMsg.setCodes(codes);

		model.getPlayer(players[0]).getCoins().setValue(0);
		
		DefaultActionFactory factory = new DefaultActionFactory(model);
		factory.setBazaarModel(bazaarModel);
		assertNotNull("The action is correctly built", actionMsg.translate(factory));
		
	}

}
