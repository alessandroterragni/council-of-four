package it.polimi.ingsw.cg28.tModel;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.tmodel.TBonus;
import it.polimi.ingsw.cg28.tmodel.TBusinessPermitTile;
import it.polimi.ingsw.cg28.tmodel.TProductTiles;

public class TProductTilesTest {

	private int price;
	private TBusinessPermitTile[] tiles;
	private TProductTiles productTiles;
	
	@Before
	public void set(){
		
		tiles = new TBusinessPermitTile[2];
		String[] letters = {"Arkon","Juvelar"};
		String[] bonuses = {"Bonus1","Bonus2"};
		tiles[0] = new TBusinessPermitTile(new TBonus(bonuses), letters);
		tiles[1] = new TBusinessPermitTile(new TBonus(bonuses), letters);
		price = 3;
		
	}

	@Test (expected = IllegalArgumentException.class)
	public void TProductTilesTestWithPriceLowerThanZero() {
		productTiles = new TProductTiles(-3,"Owner", tiles);
	}
	
	@Test (expected = NullPointerException.class)
	public void TProductTilesTestWithTilesNull() {
		productTiles = new TProductTiles(price,"Owner", null);
	}
	
	@Test (expected = NullPointerException.class)
	public void TProductTilesTestWithNullOwner() {
		productTiles = new TProductTiles(price, null , tiles);
	}
	
	@Test
	public void TProductTilesTestGetters() {
		
		productTiles = new TProductTiles(price, "Owner" , tiles);
		TBusinessPermitTile[] tilesReturned = productTiles.getTiles();
		
		assertTrue(productTiles.getTiles().equals(tiles));
		
		assertTrue(tilesReturned[0].getBonuses().getBonusCode()[0].equals("Bonus1"));
		assertTrue(tilesReturned[0].getBonuses().getBonusCode()[1].equals("Bonus2"));
		assertTrue(tilesReturned[0].getLetterCodes()[0].equals("Arkon"));
		assertTrue(tilesReturned[0].getLetterCodes()[1].equals("Juvelar"));
		assertTrue(tilesReturned[1].getBonuses().getBonusCode()[0].equals("Bonus1"));
		assertTrue(tilesReturned[1].getBonuses().getBonusCode()[1].equals("Bonus2"));
		assertTrue(tilesReturned[1].getLetterCodes()[0].equals("Arkon"));
		assertTrue(tilesReturned[1].getLetterCodes()[1].equals("Juvelar"));
		
		assertTrue(productTiles.getPrice() == 3);
		assertTrue(productTiles.getPlayerOwner().equals("Owner"));
		
	}
}
