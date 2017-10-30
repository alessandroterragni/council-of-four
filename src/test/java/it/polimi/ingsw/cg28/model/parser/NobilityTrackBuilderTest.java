package it.polimi.ingsw.cg28.model.parser;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.bonus.BonusPack;
import it.polimi.ingsw.cg28.controller.bonus.CoinBonus;
import it.polimi.ingsw.cg28.controller.bonus.ReuseCityBonus;
import it.polimi.ingsw.cg28.controller.bonus.VictoryPointBonus;
import it.polimi.ingsw.cg28.model.NobilityTrackBonus;
import it.polimi.ingsw.cg28.model.parser.NobilityTrackBuilder;

public class NobilityTrackBuilderTest {

	private static NobilityTrackBuilder builder;
	private static File f;
	
	@BeforeClass
	public static void testBefore(){
		
		builder = new NobilityTrackBuilder();
		f = new File("src/configurationFilesTest/NobilityTrack.txt");
		
	}
	
	@Test (expected = IOException.class)
	public void testIOExceptionIfFileDoesntExist() throws IOException {

		builder.build(new File("src/configurationFilesTest/IOexception"));
		
	}
	
	@Test (expected = NullPointerException.class)
	public void testNullPointerExceptionIfFileIsNull() {

		try {
			builder.build(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testNobilityTrackBonusIsNotNull() {

		try {
			assertNotNull(builder.build(f));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testNobilityTrackBonusContainsRightBonus() {
		
		NobilityTrackBonus track = new NobilityTrackBonus();
		
		try {
			track = builder.build(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertTrue(track.getTrackBonus(2).getClass().equals(BonusPack.class));
		
		BonusPack bonuses = (BonusPack)track.getTrackBonus(2);
		
		assertTrue(bonuses.getBonusPack().get(0).getClass().equals(VictoryPointBonus.class));
		assertTrue(bonuses.getBonusPack().get(0).getValue() == 2);
		assertTrue(bonuses.getBonusPack().get(1).getClass().equals(CoinBonus.class));
		assertTrue(bonuses.getBonusPack().get(1).getValue() == 2);
		
		assertTrue(track.getTrackBonus(4).getClass().equals(ReuseCityBonus.class));
		assertTrue(track.getTrackBonus(4).getValue() == 1);
		
		assertTrue(track.getTrackBonus(1) == null);
		
				
	}

}
