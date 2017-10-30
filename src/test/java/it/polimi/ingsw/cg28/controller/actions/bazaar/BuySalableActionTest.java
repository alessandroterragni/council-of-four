package it.polimi.ingsw.cg28.controller.actions.bazaar;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.controller.PlayerTurn;
import it.polimi.ingsw.cg28.controller.actions.bazaar.BuySalableAction;
import it.polimi.ingsw.cg28.controller.actions.bazaar.SellPermitTilesAction;
import it.polimi.ingsw.cg28.controller.bazaar.BazaarBuyPlayerTurn;
import it.polimi.ingsw.cg28.controller.bazaar.BazaarSellPlayerTurn;
import it.polimi.ingsw.cg28.controller.bazaar.ProductOnSale;
import it.polimi.ingsw.cg28.controller.bonus.AssistantBonus;
import it.polimi.ingsw.cg28.controller.bonus.DrawCardBonus;
import it.polimi.ingsw.cg28.controller.updaters.BazaarPlayerTurnUpdater;
import it.polimi.ingsw.cg28.controller.updaters.Updater;
import it.polimi.ingsw.cg28.model.BazaarModel;
import it.polimi.ingsw.cg28.model.BusinessPermitTile;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.parser.ModelTestConfiguration;
import it.polimi.ingsw.cg28.tmodel.TProduct;

public class BuySalableActionTest {
	
	private BuySalableAction action;
	private ModelStatus model;
	private BazaarModel bazaar;
	private PlayerID [] players;
	private List<BusinessPermitTile> tiles;
	private ProductOnSale<?> product;
	private TProduct tProduct;
	
	@Before
	public void before(){
		
		players = new PlayerID[2];
		players[0] = new PlayerID("Seller");
		players[1] = new PlayerID("Buyer");
		
		ModelTestConfiguration config = new ModelTestConfiguration();
		model = config.buildModelTest(players);
		model.getPlayer(players[0]).setTurn(new PlayerTurn(players[0]));
		
		bazaar = new BazaarModel();
		boolean [] canSell = {true, true, true};
		
		bazaar.setCurrentTurn(new BazaarSellPlayerTurn(players[0], canSell));
		
		BusinessPermitTile tile1;
		BusinessPermitTile tile2;
		
		String[] towns ={"Arkon","Juvelar"};
		tile1 = new BusinessPermitTile(new DrawCardBonus(2), towns);
		tile2 = new BusinessPermitTile(new AssistantBonus(2), towns);
		model.getPlayer(players[0]).getPossessedTiles().add(tile1);
		model.getPlayer(players[0]).getPossessedTiles().add(tile2);
		
		tiles = new ArrayList<>();
		tiles.add(tile1);
		tiles.add(tile2);
		
		SellPermitTilesAction action = new SellPermitTilesAction(tiles, 2, bazaar);
		action.act(model.getPlayer(players[0]));
		
		product = bazaar.getShelf().get(0);
		tProduct = bazaar.gettShelf().get(0);
		
		bazaar.setCurrentTurn(new BazaarBuyPlayerTurn(players[1]));
		
	}
	
	@Test
	public void BuySalableActionTestNotNull(){
		assertNotNull(new BuySalableAction(product, tProduct, bazaar));
	}
	
	@Test (expected = NullPointerException.class)
	public void BuySalableActionTestWithBazaarNull(){
		action = new BuySalableAction(product, tProduct, null);
	}
	
	@Test (expected = NullPointerException.class)
	public void BuySalableActionTestWithProductNull(){
		action = new BuySalableAction(null, tProduct, bazaar);
	}
	
	@Test (expected = NullPointerException.class)
	public void BuySalableActionTestWithTProductNull(){
		action = new BuySalableAction(product, null, bazaar);
	}
	
	@Test
	public void BuySalableActionTestAct(){
		
		assertFalse(bazaar.getShelf().isEmpty());
		assertFalse(bazaar.gettShelf().isEmpty());
		
		action = new BuySalableAction(product, tProduct, bazaar);
		Updater update = action.act(model.getPlayer(players[1]));
		
		assertTrue(update.getClass().equals(BazaarPlayerTurnUpdater.class));
		assertTrue(bazaar.getTransactions().equals("\nTRANSACTIONS LIST\nPlayer Buyer has bought [ Arkon Juvelar -> DrawCardBonus:2 | ] [ Arkon Juvelar -> "
				+ "AssistantBonus:2 | ] for 2 coins sold by player Seller\n"));
		
		assertTrue(bazaar.getShelf().isEmpty());
		assertTrue(bazaar.gettShelf().isEmpty());
	}

}
