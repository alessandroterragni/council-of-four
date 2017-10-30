package it.polimi.ingsw.cg28.controller.bazaar;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.controller.PlayerTurn;
import it.polimi.ingsw.cg28.controller.actions.bazaar.SellAssistantsAction;
import it.polimi.ingsw.cg28.model.BazaarModel;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.parser.ModelTestConfiguration;

public class SoldAssistantTest {

	private ModelStatus model;
	private BazaarModel bazaar;
	private SoldAssistant soldStrategy;
	private PlayerID [] players;
	private int assistantsSold;
	private int price;
	
	@Before
	public void before(){
		
		players = new PlayerID[2];
		players[0] = new PlayerID("Player1");
		players[1] = new PlayerID("Player2");
		
		ModelTestConfiguration config = new ModelTestConfiguration();
		model = config.buildModelTest(players);
		model.getPlayer(players[0]).setTurn(new PlayerTurn(players[0]));
		model.getPlayer(players[0]).getAssistants().setValue(10);
		
		bazaar = new BazaarModel();
		boolean [] canSell = {true, true, true};
		
		bazaar.setCurrentTurn(new BazaarSellPlayerTurn(players[0], canSell));
		
		assistantsSold = 3;
		price = 2;
		SellAssistantsAction action = new SellAssistantsAction(assistantsSold, price, bazaar);
		action.act(model.getPlayer(players[0]));
		
		soldStrategy = new SoldAssistant();
	
	}
	
	@Test
	public void SellAssistantsActionTestNotNull(){	
		assertNotNull(new SoldAssistant());
	}
	
	@Test (expected = NullPointerException.class)
	public void SoldAssistantTestWithNullProduct(){	
		soldStrategy.sold(null , model.getPlayer(players[1]), model.getPlayer(players[0]));
	}
	
	@Test (expected = NullPointerException.class)
	public void SoldAssistantTestWithNullBuyer(){	
		soldStrategy.sold(bazaar.getShelf().get(0), null, model.getPlayer(players[0]));
	}
	
	@Test (expected = NullPointerException.class)
	public void SoldAssistantTestWithNullSeller(){	
		soldStrategy.sold(bazaar.getShelf().get(0), model.getPlayer(players[1]), null);
	}
	
	@Test
	public void SoldAssistantTestRightCoinsTransfer(){	
		
		int coinBeforePlayer1 = model.getPlayer(players[0]).getCoins().getValue();
		int coinBeforePlayer2 = model.getPlayer(players[1]).getCoins().getValue();
		
		soldStrategy.sold(bazaar.getShelf().get(0), model.getPlayer(players[1]), model.getPlayer(players[0]));
		
		assertTrue(model.getPlayer(players[0]).getCoins().getValue() == coinBeforePlayer1 + price);
		assertTrue(model.getPlayer(players[1]).getCoins().getValue() == coinBeforePlayer2 - price);

	}
	
	@Test
	public void SellAssistantsActionTestRightAssistantsTransfer(){	
		
		int assistantsBeforePlayer2 = model.getPlayer(players[1]).getAssistants().getValue();
		
		soldStrategy.sold(bazaar.getShelf().get(0), model.getPlayer(players[1]), model.getPlayer(players[0]));
		
		assertTrue(model.getPlayer(players[1]).getAssistants().getValue() == assistantsBeforePlayer2 + assistantsSold);
		
	}

}
