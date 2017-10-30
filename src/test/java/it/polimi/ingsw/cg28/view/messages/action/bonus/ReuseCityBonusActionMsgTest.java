package it.polimi.ingsw.cg28.view.messages.action.bonus;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.exception.InvalidActionException;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.parser.ModelTestConfiguration;
import it.polimi.ingsw.cg28.tmodel.TObjectFactory;
import it.polimi.ingsw.cg28.tmodel.TTown;

public class ReuseCityBonusActionMsgTest {
	
	private ReuseCityBonusActionMsg msg;
	private ModelStatus model;
	PlayerID[] players = new PlayerID[2];
	
	@Before
	public void before() {
		msg = new ReuseCityBonusActionMsg(2, new PlayerID("TestPlayer1"));
		players[0] = new PlayerID("TestPlayer1");
		players[1] = new PlayerID("TestPlayer2");
	
		ModelTestConfiguration config = new ModelTestConfiguration();
		model = config.buildModelTest(players);
	}

	@Test (expected = NullPointerException.class)
	public void testConstructorWithNullPlayerParameter() {
		new ReuseCityBonusActionMsg(1, null);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testConstructorWithIllegalNumberParameter() {
		new ReuseCityBonusActionMsg(-1, new PlayerID("TestPlayer"));
	}
	
	@Test
	public void testConstructor() {
		ReuseCityBonusActionMsg newMsg = new ReuseCityBonusActionMsg(3, new PlayerID("TestPlayer"));
		
		assertNotNull(newMsg);
		assertTrue(newMsg.getPlayerID().getName().equals("TestPlayer"));
		assertFalse(newMsg.isFilled());
		assertTrue(newMsg.getNeedCode());
		assertNotNull(newMsg.getTowns());
		assertTrue(newMsg.getNumbReuseCities() == 3);
		assertTrue(newMsg.getCodes().length == 3 && newMsg.getCodesRequest().length == 3);
		assertTrue(newMsg.getName().equals("Reuse city bonus"));
	}
	
	@Test (expected = NullPointerException.class)
	public void testFillWithNullModelParameter() throws InvalidActionException {
		msg.fill(null);
	}
	
	@Test
	public void testFill() throws InvalidActionException {		
		TObjectFactory tof = new TObjectFactory();
		
		msg.setPlayerID(players[0]);
		model.getMap().addEmporium(players[0], model.getMap().getTown("Arkon"));
		msg.fill(model);
		
		assertTrue(msg.getTowns().size() == 1);
		assertTrue(msg.getTowns().contains(tof.createTtown(model.getMap().getTown("Arkon"))));
	}
	
	@Test
	public void testGetTowns() {
		List<TTown> towns = msg.getTowns();
		
		assertNotNull(towns);
		assertTrue(towns.containsAll(msg.getTowns()));
	}
	
	@Test
	public void testGetNumbReuseCities() {
		assertTrue(msg.getNumbReuseCities() == 2);
	}
	
	@Test (expected = NullPointerException.class)
	public void testSetCodesWithNullParameter() {
		msg.setCodes(null);
	}
	
	@Test
	public void testSetCodesWithRegularParsedInput() throws InvalidActionException {
		String[] toSet = { "1", "1" };
		msg.setPlayerID(players[0]);
		model.getMap().addEmporium(players[0], model.getMap().getTown("Arkon"));
		msg.fill(model);
		
		assertTrue(msg.setCodes(toSet));
		assertTrue(msg.getCodes().equals(toSet));
	}
	
	@Test
	public void testSetCodesWithIrregularParsedInput() throws InvalidActionException {
		String[] toSet = { "3" };
		msg.setPlayerID(players[0]);
		model.getMap().addEmporium(players[0], model.getMap().getTown("Arkon"));
		msg.fill(model);
		assertFalse(msg.setCodes(toSet));
	}
	
	@Test
	public void testSetCodesWithIrregularFormatInput() throws InvalidActionException {
		String[] toSet = { "1", "k" };
		msg.setPlayerID(players[0]);
		model.getMap().addEmporium(players[0], model.getMap().getTown("Arkon"));
		msg.fill(model);
		assertFalse(msg.setCodes(toSet));
	}
	
	@Test
	public void testGetTownCodes() throws InvalidActionException {
		String[] toSet = { "1", "2" };
		msg.setPlayerID(players[0]);
		model.getMap().addEmporium(players[0], model.getMap().getTown("Arkon"));
		model.getMap().addEmporium(players[0], model.getMap().getTown("Graden"));
		msg.fill(model);
		msg.setCodes(toSet);
		
		int[] codes = msg.getCodesInt(msg.getCodes());
		
		assertTrue(codes[0] == 1);
		assertTrue(codes[1] == 2);
	}
	
	@Test
	public void testGetCodes() throws InvalidActionException {
		String[] toSet = { "1", "1" };
		msg.setPlayerID(players[0]);
		model.getMap().addEmporium(players[0], model.getMap().getTown("Arkon"));
		model.getMap().addEmporium(players[0], model.getMap().getTown("Graden"));
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
		assertTrue(msg.getName().equals("Reuse city bonus"));
	}
	
}
