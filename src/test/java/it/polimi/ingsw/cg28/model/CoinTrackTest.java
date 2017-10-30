package it.polimi.ingsw.cg28.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class CoinTrackTest {

	@Test (expected = IllegalArgumentException.class)
	public void testCoinTrackWithNegativeParameter() {
		new CoinTrack(-7);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCoinTrackDecrementNotResultingNegativeValue() {
		Track track = new CoinTrack(4);
		track.decrement(5);
	}
	
	@Test
	public void testCoinTrackIncrement() {
		Track track = new CoinTrack(6);
		track.increment(4);
		assertEquals(10, track.getValue());
	}
	
	@Test
	public void testCoinTrackDecrement() {
		Track track = new CoinTrack(28);
		track.decrement(21);
		assertEquals(7, track.getValue());
	}
	
	@Test
	public void testCoinTrackConstructor() {
		Track track = new CoinTrack(11);
		assertTrue("Track has been correctly constructed.", !track.equals(null));
		assertTrue(track.getValue() == 11);
	}

}
