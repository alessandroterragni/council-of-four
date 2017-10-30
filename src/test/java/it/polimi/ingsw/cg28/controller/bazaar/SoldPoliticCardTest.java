package it.polimi.ingsw.cg28.controller.bazaar;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.controller.PlayerTurn;
import it.polimi.ingsw.cg28.controller.actions.bazaar.SellPoliticCardsAction;
import it.polimi.ingsw.cg28.model.BazaarModel;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.PoliticCard;
import it.polimi.ingsw.cg28.model.parser.ModelTestConfiguration;

public class SoldPoliticCardTest {
	
	private ModelStatus model;
	private BazaarModel bazaar;
	private SoldPoliticCard soldStrategy;
	private PlayerID [] players;
	private int price;
	private List<PoliticCard> cards;
	private PoliticCard card1;
	private PoliticCard card2;
	
	@Before
	public void before(){
		
		players = new PlayerID[2];
		players[0] = new PlayerID("Player1");
		players[1] = new PlayerID("Player2");
		
		ModelTestConfiguration config = new ModelTestConfiguration();
		model = config.buildModelTest(players);
		model.getPlayer(players[0]).setTurn(new PlayerTurn(players[0]));
		
		bazaar = new BazaarModel();
		boolean [] canSell = {true, true, true};
		
		bazaar.setCurrentTurn(new BazaarSellPlayerTurn(players[0], canSell));
		
		price = 2;
		
		model.getPlayer(players[0]).getPoliticCardsHand().clear();
		card1 = new PoliticCard(Color.BLUE, false);
		card2 = new PoliticCard(null, true);
		model.getPlayer(players[0]).getPoliticCardsHand().add(card1);
		model.getPlayer(players[0]).getPoliticCardsHand().add(card2);
		
		cards = new ArrayList<>();
		cards.add(card1);
		cards.add(card2);
		
		SellPoliticCardsAction action = new SellPoliticCardsAction(cards, price, bazaar);
		action.act(model.getPlayer(players[0]));
		
		soldStrategy = new SoldPoliticCard();
	
	}
	
	@Test
	public void SoldPoliticCardTestNotNull(){	
		assertNotNull(new SoldPoliticCard());
	}
	
	@Test (expected = NullPointerException.class)
	public void SoldPoliticCardTestWithNullProduct(){	
		soldStrategy.sold(null , model.getPlayer(players[1]), model.getPlayer(players[0]));
	}
	
	@Test (expected = NullPointerException.class)
	public void SoldPoliticCardTestWithNullBuyer(){	
		soldStrategy.sold(bazaar.getShelf().get(0), null, model.getPlayer(players[0]));
	}
	
	@Test (expected = NullPointerException.class)
	public void SoldPoliticCardTestWithNullSeller(){	
		soldStrategy.sold(bazaar.getShelf().get(0), model.getPlayer(players[1]), null);
	}
	
	@Test
	public void SoldPoliticCardTestRightCoinsTransfer(){	
		
		int coinBeforePlayer1 = model.getPlayer(players[0]).getCoins().getValue();
		int coinBeforePlayer2 = model.getPlayer(players[1]).getCoins().getValue();
		
		soldStrategy.sold(bazaar.getShelf().get(0), model.getPlayer(players[1]), model.getPlayer(players[0]));
		
		assertTrue(model.getPlayer(players[0]).getCoins().getValue() == coinBeforePlayer1 + price);
		assertTrue(model.getPlayer(players[1]).getCoins().getValue() == coinBeforePlayer2 - price);

	}
	
	@Test
	public void SoldPoliticCardTestCardsTransfer(){	
		
		soldStrategy.sold(bazaar.getShelf().get(0), model.getPlayer(players[1]), model.getPlayer(players[0]));
		
		boolean check = true;
		
		for(PoliticCard card : model.getPlayer(players[0]).getPoliticCardsHand())
			if (card == card1 || card == card2)
				check = false;
		
		assertTrue(check);
		
		check = false;
		
		for(PoliticCard card : model.getPlayer(players[1]).getPoliticCardsHand())
			if (card == card1)
				check = true;
		
		assertTrue(check);
		
		check = false;
		
		for(PoliticCard card : model.getPlayer(players[1]).getPoliticCardsHand())
			if (card == card2)
				check = true;
		
		assertTrue(check);
		
	}

}
