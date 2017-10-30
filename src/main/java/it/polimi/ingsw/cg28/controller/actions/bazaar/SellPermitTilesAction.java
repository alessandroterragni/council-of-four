package it.polimi.ingsw.cg28.controller.actions.bazaar;

import java.util.Iterator;
import java.util.List;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.bazaar.ProductOnSale;
import it.polimi.ingsw.cg28.controller.bazaar.SoldPermitTile;
import it.polimi.ingsw.cg28.controller.bazaar.SoldStrategy;
import it.polimi.ingsw.cg28.controller.updaters.BazaarPlayerTurnUpdater;
import it.polimi.ingsw.cg28.controller.updaters.Updater;
import it.polimi.ingsw.cg28.model.BazaarModel;
import it.polimi.ingsw.cg28.model.BusinessPermitTile;
import it.polimi.ingsw.cg28.model.Player;
import it.polimi.ingsw.cg28.tmodel.TBusinessPermitTile;
import it.polimi.ingsw.cg28.tmodel.TObjectFactory;
import it.polimi.ingsw.cg28.tmodel.TProductTiles;

/**
 * Class to manage a sell permit tiles action.
 * @author Mario
 *
 */
public class SellPermitTilesAction extends BazaarAction {
	
	private List<BusinessPermitTile> tiles;
	private int price;
	
	/**
	 * Constructor of the class.
	 * @param tiles List of Business Permit Tiles the owner wants to sell.
	 * @param price price the owner want sell tiles for
	 * @param bazaarModel Model of Bazaar
	 * @throws NullPointerException if bazaarModel is null
	 * @throws NullPointerException if tiles is null
	 * @throws IllegalArgumentException if price is lower than zero
	 */
	public SellPermitTilesAction(List<BusinessPermitTile> tiles, int price, BazaarModel bazaarModel) {
		
		super(bazaarModel);
		
		Preconditions.checkNotNull(tiles);
		Preconditions.checkArgument(price >= 0);
		
		this.tiles = tiles;
		this.price = price;
		
	}
	
	/**
	 * Method to carry out the action. Adds a product and related tProduct on bazaar shelf.
	 * Matches the product with the related soldPermitTile sold strategy(Strategy pattern).
	 */
	@Override
	public Updater act(Player player) {
		
		SoldStrategy soldStrategy = new SoldPermitTile();
		
		ProductOnSale<BusinessPermitTile> product = 
				new ProductOnSale<>(tiles, price, player, soldStrategy);
		
		TObjectFactory tObjectFactory = new TObjectFactory();
		
		TBusinessPermitTile[] tTiles = tObjectFactory.createTBusinessPermitTileArray(tiles);
		
		TProductTiles tProductTiles = new TProductTiles(price, player.getID().getName(), tTiles);
		
		bazaarModel.addItem(product, tProductTiles);
		
		Iterator<BusinessPermitTile> iterator = tiles.iterator();
		while (iterator.hasNext())
        	player.getPossessedTiles().remove(iterator.next());
		
		if(player.getPossessedTiles().isEmpty())
			bazaarModel.getCurrentTurn().update(this);
		
		return new BazaarPlayerTurnUpdater(bazaarModel, player.getID());
	}

}
