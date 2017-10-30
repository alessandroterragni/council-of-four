package it.polimi.ingsw.cg28.tModel;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.tmodel.TProductAssistant;

public class TProductAssistantTest {
	
	private int price;
	private int numbAssistants;
	private TProductAssistant assistant;
	
	@Before
	public void set(){
		numbAssistants = 5;
		price = 3;
	}

	@Test (expected = IllegalArgumentException.class)
	public void TProductAssistantTestWithPriceLowerThanZero() {
		assistant = new TProductAssistant(-3,"Owner", numbAssistants);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void TProductAssistantTestWithNumbAssistantsLowerThanZero() {
		assistant = new TProductAssistant(price,"Owner", -3);
	}
	
	@Test (expected = NullPointerException.class)
	public void TProductAssistantTestWithNullOwner() {
		assistant = new TProductAssistant(price, null , numbAssistants);
	}
	
	@Test
	public void TProductAssistantTestGetters() {
		
		assistant = new TProductAssistant(price, "Owner" , numbAssistants);
		assertTrue(assistant.getNumbAssistant() == 5);
		assertTrue(assistant.getPrice() == 3);
		assertTrue(assistant.getPlayerOwner().equals("Owner"));
		
	}
	
	
	
	

}
