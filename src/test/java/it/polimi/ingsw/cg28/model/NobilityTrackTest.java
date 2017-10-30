package it.polimi.ingsw.cg28.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class NobilityTrackTest {

	@Test (expected = IllegalArgumentException.class)
	public void testNobilityTrackWithNegativeParameter() {
		new NobilityTrack(-1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testNobilityTrackDecrementNotResultingNegativeValue() {
		Track track = new NobilityTrack(3);
		track.decrement(7);
	}
	
	@Test
	public void testNobilityTrackIncrement() {
		Track track = new NobilityTrack(20);
		track.increment(4);
		assertEquals(24, track.getValue());
	}
	
	@Test
	public void testNobilityTrackDecrement() {
		Track track = new NobilityTrack(5);
		track.decrement(1);
		assertEquals(4, track.getValue());
	}

}
