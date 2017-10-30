package it.polimi.ingsw.cg28.controller.actions.bazaar;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.controller.PlayerTurn;
import it.polimi.ingsw.cg28.controller.actions.bazaar.SellAssistantsAction;
import it.polimi.ingsw.cg28.controller.bazaar.BazaarSellPlayerTurn;
import it.polimi.ingsw.cg28.controller.bazaar.ProductOnSale;
import it.polimi.ingsw.cg28.controller.bazaar.SoldAssistant;
import it.polimi.ingsw.cg28.controller.updaters.BazaarPlayerTurnUpdater;
import it.polimi.ingsw.cg28.controller.updaters.Updater;
import it.polimi.ingsw.cg28.model.AssistantCounterTrack;
import it.polimi.ingsw.cg28.model.BazaarModel;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.parser.ModelTestConfiguration;
import it.polimi.ingsw.cg28.tmodel.TProduct;
import it.polimi.ingsw.cg28.tmodel.TProductAssistant;

public class SellAssistantsActionTest {

	private SellAssistantsAction action;
	private ModelStatus model;
	private BazaarModel bazaar;
	private PlayerID [] players;
	
	@Before
	public void before(){
		
		players = new PlayerID[1];
		players[0] = new PlayerID("Player1");
		
		ModelTestConfiguration config = new ModelTestConfiguration();
		model = config.buildModelTest(players);
		model.getPlayer(players[0]).setTurn(new PlayerTurn(players[0]));
		model.getPlayer(players[0]).getAssistants().setValue(10);
		
		bazaar = new BazaarModel();
		boolean [] canSell = {true, true, true};
		
		bazaar.setCurrentTurn(new BazaarSellPlayerTurn(players[0], canSell));
	
	}
	
	@Test
	public void SellAssistantsActionTestNotNull(){
		action = new SellAssistantsAction(3, 2, bazaar);
		assertNotNull(action);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void SellAssistantsActionTestWithNumbAssistantEqualToZero(){
		action = new SellAssistantsAction(0, 2, bazaar);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void SellAssistantsActionTestWithNumbAssistantLowerThanZero(){
		action = new SellAssistantsAction(-2, 2, bazaar);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void SellAssistantsActionTestWithPriceLowerThanZero(){
		action = new SellAssistantsAction(3, -2, bazaar);
	}
	
	@Test (expected = NullPointerException.class)
	public void SellAssistantsActionTestWithBazaarNull(){
		action = new SellAssistantsAction(3, 2, null);
	}
	
	@Test
	public void SellAssistantsActionTestAct(){
		
		action = new SellAssistantsAction(3, 2, bazaar);
		int numbAssistant = model.getPlayer(players[0]).getAssistants().getValue();
		Updater update = action.act(model.getPlayer(players[0]));
		
		assertTrue(update.getClass().equals(BazaarPlayerTurnUpdater.class));
		
		ProductOnSale<?> assistants = bazaar.getShelf().get(0);
		assertTrue(((AssistantCounterTrack) assistants.getProduct(0)).getValue() == 3);
		assertTrue(assistants.getPrice() == 2);
		assertTrue(assistants.getOwner().equals(model.getPlayer(players[0])));
		assertTrue(assistants.getSoldStrategy().getClass().equals(SoldAssistant.class));
		
		TProduct product = bazaar.gettShelf().get(0);
		assertTrue(product.getClass().equals(TProductAssistant.class));
		assertTrue(((TProductAssistant)product).getNumbAssistant() == 3);
		assertTrue(product.getPrice() == 2);
		assertTrue(product.getPlayerOwner().equals("Player1"));
		
		assertTrue( model.getPlayer(players[0]).getAssistants().getValue() == numbAssistant - 3);
		
		
	}

}
