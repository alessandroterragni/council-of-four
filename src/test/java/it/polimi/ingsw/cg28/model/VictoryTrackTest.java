package it.polimi.ingsw.cg28.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class VictoryTrackTest {

	@Test (expected = IllegalArgumentException.class)
	public void testVictoryTrackWithNegativeParameter() {
		new VictoryTrack(-50);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testVictoryTrackDecrementNotResultingNegativeValue() {
		Track track = new VictoryTrack(5);
		track.decrement(6);
	}
	
	@Test
	public void testVictoryTrackIncrement(){
		Track track = new VictoryTrack(15);
		track.increment(11);
		assertEquals(26, track.getValue());
	}
	
	@Test
	public void testVictoryTrackDecrement(){
		Track track = new VictoryTrack(15);
		track.decrement(11);
		assertEquals(4, track.getValue());
	}

}
