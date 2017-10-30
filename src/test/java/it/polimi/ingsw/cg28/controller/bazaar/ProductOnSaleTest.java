package it.polimi.ingsw.cg28.controller.bazaar;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.model.AssistantCounterTrack;
import it.polimi.ingsw.cg28.model.BusinessPermitTile;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.PoliticCard;
import it.polimi.ingsw.cg28.model.parser.ModelTestConfiguration;

public class ProductOnSaleTest {
	
	private ProductOnSale<AssistantCounterTrack> assistantsProduct;
	private ProductOnSale<PoliticCard> cardsProduct;
	private ProductOnSale<BusinessPermitTile> tilesProduct;
	
	private List<AssistantCounterTrack> assistants;
	private List<PoliticCard> cards;
	private List<BusinessPermitTile> tiles;

	private ModelStatus model;
	private PlayerID [] players;
	
	@Before
	public void before(){
		
		players = new PlayerID[1];
		players[0] = new PlayerID("Player1");
		
		ModelTestConfiguration config = new ModelTestConfiguration();
		model = config.buildModelTest(players);
		
		assistants = new ArrayList<>();
		assistants.add(new AssistantCounterTrack(model.getPlayer(players[0]).getAssistants().getValue()));
		
		cards = new ArrayList<>();
		cards.addAll(model.getPlayer(players[0]).getPoliticCardsHand());
		
		tiles = new ArrayList<>();
		model.getPlayer(players[0]).getPossessedTiles().add(model.getRegion(0).getDeck().draw());
		model.getPlayer(players[0]).getPossessedTiles().add(model.getRegion(0).getDeck().draw());
		tiles.addAll(model.getPlayer(players[0]).getPossessedTiles());
		
	}
	
	
	@Test
	public void ProductOnSaleTestNotNull() {
		assertNotNull(new ProductOnSale<>(assistants, 3, model.getPlayer(players[0]), new SoldAssistant()));
		assertNotNull(new ProductOnSale<>(cards, 3, model.getPlayer(players[0]), new SoldPoliticCard()));
		assertNotNull(new ProductOnSale<>(tiles, 3, model.getPlayer(players[0]), new SoldPermitTile()));
	}
	
	@Test (expected = NullPointerException.class)
	public void ProductOnSaleTestWithNullProducts() {
		assistantsProduct = new ProductOnSale<>(null, 3, model.getPlayer(players[0]), new SoldAssistant());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void ProductOnSaleTestWithPriceLowerThanZero() {
		cardsProduct = new ProductOnSale<>(cards, -2, model.getPlayer(players[0]), new SoldPoliticCard());
	}

	@Test (expected = NullPointerException.class)
	public void ProductOnSaleTestWithNullOwner() {
		tilesProduct = new ProductOnSale<>(tiles, 3,  null, new SoldPermitTile());
	}

	@Test (expected = NullPointerException.class)
	public void ProductOnSaleTestWithNullSoldStrategy() {
		assistantsProduct = new ProductOnSale<>(assistants, 3, model.getPlayer(players[0]), null);
	}
	
	@Test
	public void ProductOnSaleTestPoliticCards() {
		
		cardsProduct = new ProductOnSale<>(cards, 3, model.getPlayer(players[0]), new SoldPoliticCard());
		
		assertTrue(cardsProduct.getOwner().getID().equals(players[0]));
		assertTrue(cardsProduct.getPrice() == 3);
		assertTrue(cardsProduct.getSoldStrategy().getClass().equals(SoldPoliticCard.class));
		assertTrue(cardsProduct.getProduct(0).equals(model.getPlayer(players[0]).getPoliticCardsHand().get(0)));
		assertTrue(cardsProduct.getProduct(1).equals(model.getPlayer(players[0]).getPoliticCardsHand().get(1)));
		
	}

	@Test 
	public void ProductOnSaleTestBusinessPermitTile() {
		tilesProduct = new ProductOnSale<>(tiles, 3,  model.getPlayer(players[0]), new SoldPermitTile());
		
		assertTrue(tilesProduct.getOwner().getID().equals(players[0]));
		assertTrue(tilesProduct.getPrice() == 3);
		assertTrue(tilesProduct.getSoldStrategy().getClass().equals(SoldPermitTile.class));
		assertTrue(tilesProduct.getProduct(0).equals(model.getPlayer(players[0]).getPossessedTiles().get(0)));
		assertTrue(tilesProduct.getProduct(1).equals(model.getPlayer(players[0]).getPossessedTiles().get(1)));
	}

	@Test 
	public void ProductOnSaleTestAssistants() {
		assistantsProduct = new ProductOnSale<>(assistants, 3, model.getPlayer(players[0]), new SoldAssistant());
		
		assertTrue(assistantsProduct.getOwner().getID().equals(players[0]));
		assertTrue(assistantsProduct.getPrice() == 3);
		assertTrue(assistantsProduct.getSoldStrategy().getClass().equals(SoldAssistant.class));
		assertTrue(assistantsProduct.getProduct(0).getValue() == model.getPlayer(players[0]).getAssistants().getValue());
		
	}
	
	


}
