package it.polimi.ingsw.cg28.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class AssistantCounterTrackTest {

	@Test (expected = IllegalArgumentException.class)
	public void testAssistantCounterTrackWithNegativeParmeter() {
		new AssistantCounterTrack(-10);
	}
	
	@Test
	public void testAssistantCounterTrackIncrement() {
		Track track = new AssistantCounterTrack(10);
		track.increment(10);
		assertEquals(20, track.getValue());
	}
	
	@Test
	public void testAssistantCounterTrackDecrement() {
		Track track = new AssistantCounterTrack(20);
		track.decrement(15);
		assertEquals(5, track.getValue());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAssistantCounterTrackDecrementNotResultingNegativeValue() {
		Track track = new AssistantCounterTrack(1);
		track.decrement(5);
	}
	
	@Test
	public void testAssistantCounterTrackConstructor() {
		Track track = new AssistantCounterTrack(1);
		assertTrue("Track has been correctly constructed", !track.equals(null));
		assertTrue(track.getValue() == 1);
	}

}
