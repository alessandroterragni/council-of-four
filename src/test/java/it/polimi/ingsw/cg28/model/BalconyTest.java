package it.polimi.ingsw.cg28.model;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.LinkedList;
import java.util.Queue;

import org.junit.Test;

public class BalconyTest {
	
	Councillor[] nobles = { new Councillor(Color.black), new Councillor(Color.orange), new Councillor(Color.cyan) };

	@Test (expected = NullPointerException.class)
	public void testBalconyWithNullParameter() {
		new Balcony(null);
	}
	
	@Test
	public void testBalconyConstructor() {
		Balcony balcony = new Balcony(this.nobles);
		assertTrue("Balcony has been correctly constructed.", !balcony.equals(null));
		assertTrue(balcony.getCouncil() != null);
		for(int i = 0; i < balcony.getCouncil().size(); i++){
			assertTrue(balcony.getCouncillor(i) == this.nobles[i]);
		}
	}
	
	@Test
	public void testBalconyGetCouncil() {
		Balcony balcony = new Balcony(this.nobles);
		Queue<Councillor> council = new LinkedList<>(balcony.getCouncil());
		for(int i = 0; i < balcony.getCouncil().size(); i++){
			assertTrue(balcony.getCouncillor(i) == council.poll());
		}
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testBalconyGetCouncillorWithNegativeParameter() {
		Balcony balcony = new Balcony(nobles);
		balcony.getCouncillor(-1);
	}
	
	@Test
	public void testBalconyGetCouncillor() {
		Balcony balcony = new Balcony(nobles);
		Councillor chosen = balcony.getCouncillor(2);
		assertTrue(chosen.equals(new Councillor(Color.cyan)));
	}
	
	@Test
	public void testBalconySetCouncil() {
		Balcony balcony = new Balcony(new Councillor[3]);
		Queue<Councillor> newNobles = new LinkedList<>();
		for(Councillor c : nobles){
			newNobles.add(c);
		}
		balcony.setCouncil(newNobles);

		assertTrue(balcony.getCouncil().containsAll(newNobles));
	}
	
	@Test (expected = NullPointerException.class)
	public void testBalconyAddCouncillorWithNullParameter() {
		Balcony balcony = new Balcony(nobles);
		balcony.addCouncillor(null);
	}
	
	@Test
	public void testBalconyAddCouncillor() {
		Balcony balcony = new Balcony(nobles);
		Councillor elected = new Councillor(Color.magenta);
		Councillor fallen = balcony.getCouncil().peek();
		Councillor check = balcony.addCouncillor(elected);
		assertTrue(fallen == check);
		assertTrue("Councillor added successfully.", balcony.getCouncil().contains(elected));
		assertTrue(!balcony.getCouncil().contains(fallen));
	}

}
