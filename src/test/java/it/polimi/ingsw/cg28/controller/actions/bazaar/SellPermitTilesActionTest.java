package it.polimi.ingsw.cg28.controller.actions.bazaar;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.controller.PlayerTurn;
import it.polimi.ingsw.cg28.controller.actions.bazaar.SellPermitTilesAction;
import it.polimi.ingsw.cg28.controller.bazaar.BazaarSellPlayerTurn;
import it.polimi.ingsw.cg28.controller.bazaar.ProductOnSale;
import it.polimi.ingsw.cg28.controller.bazaar.SoldPermitTile;
import it.polimi.ingsw.cg28.controller.bonus.AssistantBonus;
import it.polimi.ingsw.cg28.controller.bonus.DrawCardBonus;
import it.polimi.ingsw.cg28.controller.updaters.BazaarPlayerTurnUpdater;
import it.polimi.ingsw.cg28.controller.updaters.Updater;
import it.polimi.ingsw.cg28.model.BazaarModel;
import it.polimi.ingsw.cg28.model.BusinessPermitTile;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.parser.ModelTestConfiguration;
import it.polimi.ingsw.cg28.tmodel.TProduct;
import it.polimi.ingsw.cg28.tmodel.TProductTiles;

public class SellPermitTilesActionTest {

	private SellPermitTilesAction action;
	private ModelStatus model;
	private BazaarModel bazaar;
	private PlayerID [] players;
	private List<BusinessPermitTile> tiles;
	private BusinessPermitTile tile1;
	private BusinessPermitTile tile2;
	
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
		
		String[] towns ={"Arkon","Juvelar"};
		tile1 = new BusinessPermitTile(new DrawCardBonus(2), towns);
		tile2 = new BusinessPermitTile(new AssistantBonus(2), towns);
		model.getPlayer(players[0]).getPossessedTiles().add(tile1);
		model.getPlayer(players[0]).getPossessedTiles().add(tile2);
		
		tiles = new ArrayList<>();
		tiles.add(tile1);
		tiles.add(tile2);
		
	}
	
	@Test
	public void SellPermitTilesActionTestNotNull(){
		action = new SellPermitTilesAction(tiles, 2, bazaar);
		assertNotNull(action);
	}
	
	@Test (expected = NullPointerException.class)
	public void SellPermitTilesActionTestWithTilesNull(){
		action = new SellPermitTilesAction(null, 2, bazaar);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void SellPermitTilesActionTestWithPriceLowerThanZero(){
		action = new SellPermitTilesAction(tiles, -2, bazaar);
	}
	
	@Test (expected = NullPointerException.class)
	public void SellPermitTilesActionTestWithBazaarNull(){
		action = new SellPermitTilesAction(tiles, 2, null);
	}
	
	@Test
	public void SellPermitTilesActionTestAct(){
		
		action = new SellPermitTilesAction(tiles, 2, bazaar);
		Updater update = action.act(model.getPlayer(players[0]));
		
		assertTrue(update.getClass().equals(BazaarPlayerTurnUpdater.class));
		
		ProductOnSale<?> tiles = bazaar.getShelf().get(0);
		assertTrue(((BusinessPermitTile) tiles.getProduct(0)) == tile1);
		assertTrue(((BusinessPermitTile) tiles.getProduct(1)) == tile2);
		assertTrue(tiles.getPrice() == 2);
		assertTrue(tiles.getOwner().equals(model.getPlayer(players[0])));
		assertTrue(tiles.getSoldStrategy().getClass().equals(SoldPermitTile.class));
		
		TProduct product = bazaar.gettShelf().get(0);
		assertTrue(product.getClass().equals(TProductTiles.class));
		assertTrue(((TProductTiles)product).getTiles()[0].getBonuses().getBonusCode()[0].equals("DrawCardBonus:2"));
		assertTrue(((TProductTiles)product).getTiles()[0].getLetterCodes()[0].equals("Arkon"));
		assertTrue(((TProductTiles)product).getTiles()[0].getLetterCodes()[1].equals("Juvelar"));
		assertTrue(((TProductTiles)product).getTiles()[1].getBonuses().getBonusCode()[0].equals("AssistantBonus:2"));
		assertTrue(((TProductTiles)product).getTiles()[1].getLetterCodes()[0].equals("Arkon"));
		assertTrue(((TProductTiles)product).getTiles()[1].getLetterCodes()[1].equals("Juvelar"));
		assertTrue(product.getPrice() == 2);
		assertTrue(product.getPlayerOwner().equals("Player1"));
		
		assertFalse(model.getPlayer(players[0]).getPossessedTiles().contains(tile1));
		assertFalse(model.getPlayer(players[0]).getPossessedTiles().contains(tile2));
			
	}

}
