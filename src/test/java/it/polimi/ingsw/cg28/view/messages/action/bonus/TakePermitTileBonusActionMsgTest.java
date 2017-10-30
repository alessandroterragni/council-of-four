package it.polimi.ingsw.cg28.view.messages.action.bonus;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.controller.bonus.Bonus;
import it.polimi.ingsw.cg28.controller.bonus.VictoryPointBonus;
import it.polimi.ingsw.cg28.exception.InvalidActionException;
import it.polimi.ingsw.cg28.model.BusinessPermitTile;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.parser.ModelTestConfiguration;
import it.polimi.ingsw.cg28.tmodel.TObjectFactory;

public class TakePermitTileBonusActionMsgTest {

	private TakePermitTileBonusActionMsg msg;
	private ModelStatus model;
	PlayerID[] players = new PlayerID[2];
	
	@Before
	public void before() {
		msg = new TakePermitTileBonusActionMsg(2, new PlayerID("TestPlayer1"));
		players[0] = new PlayerID("TestPlayer1");
		players[1] = new PlayerID("TestPlayer2");
	
		ModelTestConfiguration config = new ModelTestConfiguration();
		model = config.buildModelTest(players);
	}
	
	@Test (expected = NullPointerException.class)
	public void testConstructorWithNullPlayerParameter() {
		new TakePermitTileBonusActionMsg(1, null);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testConstructorWithIllegalNumberParameter() {
		new TakePermitTileBonusActionMsg(-1, new PlayerID("TestPlayer"));
	}
	
	@Test
	public void testConstructor() {
		TakePermitTileBonusActionMsg newMsg = new TakePermitTileBonusActionMsg(3, new PlayerID("TestPlayer"));
		
		assertNotNull(newMsg);
		assertTrue(newMsg.getPlayerID().getName().equals("TestPlayer"));
		assertFalse(newMsg.isFilled());
		assertTrue(newMsg.getNeedCode());
		assertNotNull(newMsg.getTiles());
		assertTrue(newMsg.getNumbTilesToTake() == 3);
		assertTrue(newMsg.getCodes().length == 3 && newMsg.getCodesRequest().length == 3);
		assertTrue(newMsg.getName().equals("Take permit tile bonus"));
	}
	
	@Test (expected = NullPointerException.class)
	public void testFillWithNullParameter() throws InvalidActionException {
		msg.fill(null);
	}
	
	@Test
	public void testFill() throws InvalidActionException {		
		TObjectFactory tof = new TObjectFactory();
		Bonus bonuses = new VictoryPointBonus(1);
		String[] letters1 = { "Arkon", "Graden" };
		String[] letters2 = { "Juvelar" };
		BusinessPermitTile tile1 = new BusinessPermitTile(bonuses, letters1);
		BusinessPermitTile tile2 = new BusinessPermitTile(bonuses, letters2);
		
		msg.setPlayerID(players[0]);
		model.getRegions()[0].getUncovered()[0] = tile1;
		model.getRegions()[0].getUncovered()[1] = tile2;
		model.getRegions()[1].getUncovered()[0] = tile1;
		model.getRegions()[1].getUncovered()[1] = tile2;
		model.getRegions()[2].getUncovered()[0] = tile1;
		model.getRegions()[2].getUncovered()[1] = tile2;
		msg.fill(model);
		
		assertTrue(msg.getTiles().size() == 6);
		for(int i = 0; i < msg.getTiles().size(); i+=2){
			assertTrue(msg.getTiles().get(i).getBonuses().getBonusCode()[0]
					.equals(tof.createTBusinessPermitTile(tile1).getBonuses().getBonusCode()[0]));
			assertTrue(msg.getTiles().get(i).getLetterCodes()[0]
					.equals(tof.createTBusinessPermitTile(tile1).getLetterCodes()[0]));
			assertTrue(msg.getTiles().get(i).getLetterCodes()[1]
					.equals(tof.createTBusinessPermitTile(tile1).getLetterCodes()[1]));

			assertTrue(msg.getTiles().get(i+1).getBonuses().getBonusCode()[0]
					.equals(tof.createTBusinessPermitTile(tile2).getBonuses().getBonusCode()[0]));
			assertTrue(msg.getTiles().get(i+1).getLetterCodes()[0]
					.equals(tof.createTBusinessPermitTile(tile2).getLetterCodes()[0]));
		}
	}
	
	@Test
	public void testGetNumbTilesToTake() {
		assertTrue(msg.getNumbTilesToTake() == 2);
	}
	
	@Test (expected = NullPointerException.class)
	public void testSetCodesWithNullParameter() {
		msg.setCodes(null);
	}
	
	@Test
	public void testSetCodesWithRegularParsedInput() throws InvalidActionException {
		Bonus bonuses = new VictoryPointBonus(1);
		String[] letters1 = { "Arkon", "Graden" };
		String[] letters2 = { "Juvelar" };
		BusinessPermitTile tile1 = new BusinessPermitTile(bonuses, letters1);
		BusinessPermitTile tile2 = new BusinessPermitTile(bonuses, letters2);
		String[] toSet = { "1", "5" };
		
		msg.setPlayerID(players[0]);
		model.getRegions()[0].getUncovered()[0] = tile1;
		model.getRegions()[0].getUncovered()[1] = tile2;
		model.getRegions()[1].getUncovered()[0] = tile1;
		model.getRegions()[1].getUncovered()[1] = tile2;
		model.getRegions()[2].getUncovered()[0] = tile1;
		model.getRegions()[2].getUncovered()[1] = tile2;
		msg.fill(model);
		
		assertTrue(msg.setCodes(toSet));
		assertTrue(msg.getCodes().equals(toSet));
	}
	
	@Test
	public void testSetCodesWithIrregularParsedInput() throws InvalidActionException {
		Bonus bonuses = new VictoryPointBonus(1);
		String[] letters1 = { "Arkon", "Graden" };
		String[] letters2 = { "Juvelar" };
		BusinessPermitTile tile1 = new BusinessPermitTile(bonuses, letters1);
		BusinessPermitTile tile2 = new BusinessPermitTile(bonuses, letters2);
		String[] toSet = { "8" };
		
		msg.setPlayerID(players[0]);
		model.getRegions()[0].getUncovered()[0] = tile1;
		model.getRegions()[0].getUncovered()[1] = tile2;
		model.getRegions()[1].getUncovered()[0] = tile1;
		model.getRegions()[1].getUncovered()[1] = tile2;
		model.getRegions()[2].getUncovered()[0] = tile1;
		model.getRegions()[2].getUncovered()[1] = tile2;
		msg.fill(model);
		
		assertFalse(msg.setCodes(toSet));
	}
	
	@Test
	public void testSetCodesWithIrregularFormatInput() throws InvalidActionException {
		Bonus bonuses = new VictoryPointBonus(1);
		String[] letters1 = { "Arkon", "Graden" };
		String[] letters2 = { "Juvelar" };
		BusinessPermitTile tile1 = new BusinessPermitTile(bonuses, letters1);
		BusinessPermitTile tile2 = new BusinessPermitTile(bonuses, letters2);
		String[] toSet = { "4", "j" };
		
		msg.setPlayerID(players[0]);
		model.getRegions()[0].getUncovered()[0] = tile1;
		model.getRegions()[0].getUncovered()[1] = tile2;
		model.getRegions()[1].getUncovered()[0] = tile1;
		model.getRegions()[1].getUncovered()[1] = tile2;
		model.getRegions()[2].getUncovered()[0] = tile1;
		model.getRegions()[2].getUncovered()[1] = tile2;
		msg.fill(model);
		
		assertFalse(msg.setCodes(toSet));
	}
	
	@Test
	public void testGetPermitTileChosenCode() throws InvalidActionException {
		Bonus bonuses = new VictoryPointBonus(1);
		String[] letters1 = { "Arkon", "Graden" };
		String[] letters2 = { "Juvelar" };
		BusinessPermitTile tile1 = new BusinessPermitTile(bonuses, letters1);
		BusinessPermitTile tile2 = new BusinessPermitTile(bonuses, letters2);
		String[] toSet = { "1", "5" };
		
		msg.setPlayerID(players[0]);
		model.getRegions()[0].getUncovered()[0] = tile1;
		model.getRegions()[0].getUncovered()[1] = tile2;
		model.getRegions()[1].getUncovered()[0] = tile1;
		model.getRegions()[1].getUncovered()[1] = tile2;
		model.getRegions()[2].getUncovered()[0] = tile1;
		model.getRegions()[2].getUncovered()[1] = tile2;
		msg.fill(model);
		msg.setCodes(toSet);
		
		int[] codes = msg.getCodesInt(msg.getCodes());
		
		assertTrue(codes[0] == 1);
		assertTrue(codes[1] == 5);
	}
	
	@Test
	public void testGetCodes() throws InvalidActionException {
		Bonus bonuses = new VictoryPointBonus(1);
		String[] letters1 = { "Arkon", "Graden" };
		String[] letters2 = { "Juvelar" };
		BusinessPermitTile tile1 = new BusinessPermitTile(bonuses, letters1);
		BusinessPermitTile tile2 = new BusinessPermitTile(bonuses, letters2);
		String[] toSet = { "1", "5" };
		
		msg.setPlayerID(players[0]);
		model.getRegions()[0].getUncovered()[0] = tile1;
		model.getRegions()[0].getUncovered()[1] = tile2;
		model.getRegions()[1].getUncovered()[0] = tile1;
		model.getRegions()[1].getUncovered()[1] = tile2;
		model.getRegions()[2].getUncovered()[0] = tile1;
		model.getRegions()[2].getUncovered()[1] = tile2;
		msg.fill(model);
		msg.setCodes(toSet);
		
		assertTrue(msg.getCodes().equals(toSet));
	}
	
	@Test
	public void testGetCodesRequest() {
		assertTrue(msg.getCodesRequest().length == 2);
	}
	
	@Test
	public void testGetName() {
		assertTrue(msg.getName().equals("Take permit tile bonus"));
	}

}
