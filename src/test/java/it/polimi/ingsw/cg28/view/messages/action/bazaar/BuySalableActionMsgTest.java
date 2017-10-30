package it.polimi.ingsw.cg28.view.messages.action.bazaar;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.controller.bazaar.ProductOnSale;
import it.polimi.ingsw.cg28.controller.bazaar.Salable;
import it.polimi.ingsw.cg28.controller.bazaar.SoldPoliticCard;
import it.polimi.ingsw.cg28.exception.InvalidActionException;
import it.polimi.ingsw.cg28.model.BazaarModel;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.PoliticCard;
import it.polimi.ingsw.cg28.model.parser.ModelTestConfiguration;
import it.polimi.ingsw.cg28.tmodel.TProductCards;

public class BuySalableActionMsgTest {
	
	private BuySalableActionMsg msg;
	private ModelStatus model;
	PlayerID[] players = new PlayerID[2];
	
	@Before
	public void before() {
		
		players[0] = new PlayerID("TestPlayer1");
		players[1] = new PlayerID("TestPlayer2");
		
		msg = new BuySalableActionMsg(players[0]);
	
		ModelTestConfiguration config = new ModelTestConfiguration();
		model = config.buildModelTest(players);
	}

	@Test (expected = NullPointerException.class)
	public void testConstructorWithNullParameter() {
		new BuySalableActionMsg(null);
	}
	
	@Test
	public void testConstructor() {
		BuySalableActionMsg newMsg = new BuySalableActionMsg(new PlayerID("TestPlayer1"));
		
		assertNotNull(newMsg);
		assertTrue(newMsg.getCodes().length == 1);
		assertTrue(newMsg.getCodesRequest().length == 1);
		assertTrue(newMsg.getName().equals("Buy items"));
	}
	
	@Test (expected = NullPointerException.class)
	public void testFillWithNullParameter() throws InvalidActionException {
		msg.fill(null);
	}
	
	@Test (expected = InvalidActionException.class)
	public void testFillWithOnlyGameModelParameter() throws InvalidActionException {
		msg.fill(model);
	}
	
	@Test (expected = NullPointerException.class)
	public void testFillWithNullGameModelParameter() throws InvalidActionException {
		msg.fill(null, new BazaarModel());
	}
	
	@Test (expected = NullPointerException.class)
	public void testFillWithNullBazaarModelParameter() throws InvalidActionException {
		msg.fill(model, null);
	}
	
	@Test (expected = InvalidActionException.class)
	public void testFillWithNotEnoughItems() throws InvalidActionException {
		msg.fill(model, new BazaarModel());
	}
	
	@Test
	public void testFill() throws InvalidActionException {
		BazaarModel bModel = new BazaarModel();
		List<Salable> products = new ArrayList<>();
		PoliticCard card1 = new PoliticCard(Color.ORANGE, false);
		PoliticCard card2 = new PoliticCard(null, true);
		Color[] cards = { Color.ORANGE, null };
		products.add(card1);
		products.add(card2);
		ProductOnSale<Salable> prod = 
				new ProductOnSale<>(products, 10, model.getPlayer(players[1]), new SoldPoliticCard());
		bModel.addItem(prod, new TProductCards(10, "TestPlayer2", cards));
		msg.fill(model, bModel);

		assertNotNull(msg.getShelf());
	}
	
	@Test (expected = NullPointerException.class)
	public void testSetCodesWithNullParamter() {
		msg.setCodes(null);
	}
	
	@Test
	public void testSetCodesWithRegularParsedInput() throws InvalidActionException {
		String[] toSet = { "1" };
		
		BazaarModel bModel = new BazaarModel();
		List<Salable> products = new ArrayList<>();
		PoliticCard card1 = new PoliticCard(Color.ORANGE, false);
		PoliticCard card2 = new PoliticCard(null, true);
		Color[] cards = { Color.ORANGE, null };
		products.add(card1);
		products.add(card2);
		ProductOnSale<Salable> prod = 
				new ProductOnSale<>(products, 10, model.getPlayer(players[1]), new SoldPoliticCard());
		bModel.addItem(prod, new TProductCards(10, "TestPlayer2", cards));
		msg.fill(model, bModel);
		
		assertTrue(msg.setCodes(toSet));
	}
	
	@Test
	public void testSetCodesWithRegularParsedInputButNotEnoughCoins() throws InvalidActionException {
		String[] toSet = { "1" };
		
		BazaarModel bModel = new BazaarModel();
		List<Salable> products = new ArrayList<>();
		PoliticCard card1 = new PoliticCard(Color.ORANGE, false);
		PoliticCard card2 = new PoliticCard(null, true);
		Color[] cards = { Color.ORANGE, null };
		products.add(card1);
		products.add(card2);
		ProductOnSale<Salable> prod = 
				new ProductOnSale<>(products, 15, model.getPlayer(players[1]), new SoldPoliticCard());
		bModel.addItem(prod, new TProductCards(15, "TestPlayer2", cards));
		msg.fill(model, bModel);
		
		assertFalse(msg.setCodes(toSet));
	}
	
	@Test
	public void testSetCodesWithIrregularParsedInput() throws InvalidActionException {
		String[] toSet = { "4" };
		
		BazaarModel bModel = new BazaarModel();
		List<Salable> products = new ArrayList<>();
		PoliticCard card1 = new PoliticCard(Color.ORANGE, false);
		PoliticCard card2 = new PoliticCard(null, true);
		Color[] cards = { Color.ORANGE, null };
		products.add(card1);
		products.add(card2);
		ProductOnSale<Salable> prod = 
				new ProductOnSale<>(products, 10, model.getPlayer(players[1]), new SoldPoliticCard());
		bModel.addItem(prod, new TProductCards(10, "TestPlayer2", cards));
		msg.fill(model, bModel);
		
		assertFalse(msg.setCodes(toSet));
	}
	
	@Test
	public void testSetCodesWithIrregularFormatInput() throws InvalidActionException {
		String[] toSet = { "q" };
		
		BazaarModel bModel = new BazaarModel();
		List<Salable> products = new ArrayList<>();
		PoliticCard card1 = new PoliticCard(Color.ORANGE, false);
		PoliticCard card2 = new PoliticCard(null, true);
		Color[] cards = { Color.ORANGE, null };
		products.add(card1);
		products.add(card2);
		ProductOnSale<Salable> prod = 
				new ProductOnSale<>(products, 10, model.getPlayer(players[1]), new SoldPoliticCard());
		bModel.addItem(prod, new TProductCards(10, "TestPlayer2", cards));
		msg.fill(model, bModel);
		
		assertFalse(msg.setCodes(toSet));
	}

}
