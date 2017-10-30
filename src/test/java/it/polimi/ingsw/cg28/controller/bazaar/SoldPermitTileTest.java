package it.polimi.ingsw.cg28.controller.bazaar;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.controller.PlayerTurn;
import it.polimi.ingsw.cg28.controller.actions.bazaar.SellPermitTilesAction;
import it.polimi.ingsw.cg28.controller.bonus.AssistantBonus;
import it.polimi.ingsw.cg28.controller.bonus.DrawCardBonus;
import it.polimi.ingsw.cg28.model.BazaarModel;
import it.polimi.ingsw.cg28.model.BusinessPermitTile;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.parser.ModelTestConfiguration;

public class SoldPermitTileTest {

	private ModelStatus model;
	private BazaarModel bazaar;
	private SoldPermitTile soldStrategy;
	private PlayerID [] players;
	private int price;
	private List<BusinessPermitTile> tiles;
	private BusinessPermitTile tile1;
	private BusinessPermitTile tile2;
	
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
		
		String[] towns ={"Arkon","Juvelar"};
		
		tile1 = new BusinessPermitTile(new DrawCardBonus(2), towns);
		tile2 = new BusinessPermitTile(new AssistantBonus(2), towns);
		model.getPlayer(players[0]).getPossessedTiles().add(tile1);
		model.getPlayer(players[0]).getPossessedTiles().add(tile2);
		
		tiles = new ArrayList<>();
		tiles.add(tile1);
		tiles.add(tile2);
		
		SellPermitTilesAction action = new SellPermitTilesAction(tiles, price, bazaar);
		action.act(model.getPlayer(players[0]));
		
		soldStrategy = new SoldPermitTile();
	
	}
	
	@Test
	public void SoldPermitTileTestNotNull(){	
		assertNotNull(new SoldPermitTile());
	}
	
	@Test (expected = NullPointerException.class)
	public void SoldPermitTileTestWithNullProduct(){	
		soldStrategy.sold(null , model.getPlayer(players[1]), model.getPlayer(players[0]));
	}
	
	@Test (expected = NullPointerException.class)
	public void SoldPermitTileTestWithNullBuyer(){	
		soldStrategy.sold(bazaar.getShelf().get(0), null, model.getPlayer(players[0]));
	}
	
	@Test (expected = NullPointerException.class)
	public void SoldPermitTileTestWithNullSeller(){	
		soldStrategy.sold(bazaar.getShelf().get(0), model.getPlayer(players[1]), null);
	}
	
	@Test
	public void SoldPermitTileTestRightCoinsTransfer(){	
		
		int coinBeforePlayer1 = model.getPlayer(players[0]).getCoins().getValue();
		int coinBeforePlayer2 = model.getPlayer(players[1]).getCoins().getValue();
		
		soldStrategy.sold(bazaar.getShelf().get(0), model.getPlayer(players[1]), model.getPlayer(players[0]));
		
		assertTrue(model.getPlayer(players[0]).getCoins().getValue() == coinBeforePlayer1 + price);
		assertTrue(model.getPlayer(players[1]).getCoins().getValue() == coinBeforePlayer2 - price);

	}
	
	@Test
	public void SoldPermitTileTestTilesTransfer(){	
		
		soldStrategy.sold(bazaar.getShelf().get(0), model.getPlayer(players[1]), model.getPlayer(players[0]));
		
		assertFalse(model.getPlayer(players[0]).getPossessedTiles().contains(tile1));
		assertFalse(model.getPlayer(players[0]).getPossessedTiles().contains(tile2));
		assertTrue(model.getPlayer(players[1]).getPossessedTiles().contains(tile1));
		assertTrue(model.getPlayer(players[1]).getPossessedTiles().contains(tile2));
		
	}

}
