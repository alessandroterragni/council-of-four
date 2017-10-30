package it.polimi.ingsw.cg28.controller.actions.bazaar;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.controller.PlayerTurn;
import it.polimi.ingsw.cg28.controller.actions.bazaar.SellPoliticCardsAction;
import it.polimi.ingsw.cg28.controller.bazaar.BazaarSellPlayerTurn;
import it.polimi.ingsw.cg28.controller.bazaar.ProductOnSale;
import it.polimi.ingsw.cg28.controller.bazaar.SoldPoliticCard;
import it.polimi.ingsw.cg28.controller.updaters.BazaarPlayerTurnUpdater;
import it.polimi.ingsw.cg28.controller.updaters.Updater;
import it.polimi.ingsw.cg28.model.BazaarModel;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.PoliticCard;
import it.polimi.ingsw.cg28.model.parser.ModelTestConfiguration;
import it.polimi.ingsw.cg28.tmodel.TProduct;
import it.polimi.ingsw.cg28.tmodel.TProductCards;

public class SellPoliticCardsActionTest {

	private SellPoliticCardsAction action;
	private ModelStatus model;
	private BazaarModel bazaar;
	private PlayerID [] players;
	private List<PoliticCard> cards;
	private PoliticCard card1;
	private PoliticCard card2;
	
	@Before
	public void before(){
		
		players = new PlayerID[1];
		players[0] = new PlayerID("Player1");
		
		ModelTestConfiguration config = new ModelTestConfiguration();
		model = config.buildModelTest(players);
		model.getPlayer(players[0]).setTurn(new PlayerTurn(players[0]));
		
		bazaar = new BazaarModel();
		boolean [] canSell = {true, true, true};
		
		bazaar.setCurrentTurn(new BazaarSellPlayerTurn(players[0], canSell));
		
		model.getPlayer(players[0]).getPoliticCardsHand().clear();
		card1 = new PoliticCard(Color.BLUE, false);
		card2 = new PoliticCard(null, true);
		model.getPlayer(players[0]).getPoliticCardsHand().add(card1);
		model.getPlayer(players[0]).getPoliticCardsHand().add(card2);
		
		cards = new ArrayList<>();
		cards.add(card1);
		cards.add(card2);
		
	}
	
	@Test
	public void SellPoliticCardsActionTestNotNull(){
		action = new SellPoliticCardsAction(cards, 2, bazaar);
		assertNotNull(action);
	}
	
	@Test (expected = NullPointerException.class)
	public void SellPoliticCardsActionTestWithCardsNull(){
		action = new SellPoliticCardsAction(null, 2, bazaar);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void SellPoliticCardsActionTestWithPriceLowerThanZero(){
		action = new SellPoliticCardsAction(cards, -2, bazaar);
	}
	
	@Test (expected = NullPointerException.class)
	public void SellPoliticCardsActionTestWithBazaarNull(){
		action = new SellPoliticCardsAction(cards, 2, null);
	}
	
	@Test
	public void SellPoliticCardsActionTestAct(){
		
		action = new SellPoliticCardsAction(cards, 2, bazaar);
		Updater update = action.act(model.getPlayer(players[0]));
		
		assertTrue(update.getClass().equals(BazaarPlayerTurnUpdater.class));
		
		ProductOnSale<?> cards = bazaar.getShelf().get(0);
		assertTrue(((PoliticCard) cards.getProduct(0)) == card1);
		assertTrue(((PoliticCard) cards.getProduct(1)) == card2);
		assertTrue(cards.getPrice() == 2);
		assertTrue(cards.getOwner().equals(model.getPlayer(players[0])));
		assertTrue(cards.getSoldStrategy().getClass().equals(SoldPoliticCard.class));
		
		TProduct product = bazaar.gettShelf().get(0);
		assertTrue(product.getClass().equals(TProductCards.class));
		assertTrue(((TProductCards)product).getCards()[0].equals(Color.BLUE));
		assertTrue(((TProductCards)product).getCards()[1] == null);
		assertTrue(product.getPrice() == 2);
		assertTrue(product.getPlayerOwner().equals("Player1"));
		
		boolean check = true;
		
		for(PoliticCard card : model.getPlayer(players[0]).getPoliticCardsHand())
			if (card == card1 || card == card2)
				check = false;
		
		assertTrue(check);
			
	}

}
