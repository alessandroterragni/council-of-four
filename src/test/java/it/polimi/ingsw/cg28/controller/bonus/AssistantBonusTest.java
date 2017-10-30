package it.polimi.ingsw.cg28.controller.bonus;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.controller.PlayerTurn;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.Player;
import it.polimi.ingsw.cg28.model.parser.ModelTestConfiguration;

public class AssistantBonusTest {

	private ModelStatus model;
	private PlayerID [] players;

	
	@Before
	public void before(){
		
		players = new PlayerID[1];
		players[0] = new PlayerID("Player1");
		
		ModelTestConfiguration config = new ModelTestConfiguration();
		model = config.buildModelTest(players);
		model.getPlayer(players[0]).setTurn(new PlayerTurn(players[0]));
		
	}
	
	@Test
	public void testAssistantBonus() {
		assertNotNull("the bonus is correctly constructed",new AssistantBonus(57));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAssistantBonusWithNegativeParameter() {
		new AssistantBonus(-10);
	}

	@Test 
	public void testGetBonus() {
		AssistantBonus bonus = new AssistantBonus(10);
		Player p = model.getPlayer(players[0]);
		int oldNumberOfAssistants = p.getAssistants().getValue();
		bonus.getBonus(p);
		int newNumberOfAssistants = p.getAssistants().getValue();
		assertTrue("The number of assistants of the player is correctly incremented", newNumberOfAssistants==oldNumberOfAssistants+10);
		
	}
	
	@Test (expected = NullPointerException.class)
	public void testGetBonusWithNullParameter() {
		AssistantBonus bonus = new AssistantBonus(10);
		bonus.getBonus(null);
	}

	@Test
	public void testGetValue() {
		AssistantBonus bonus = new AssistantBonus(10);
		assertTrue(bonus.getValue()==10);
	}
}
