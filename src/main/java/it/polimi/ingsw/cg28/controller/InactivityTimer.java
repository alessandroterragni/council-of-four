package it.polimi.ingsw.cg28.controller;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Timer to handle inactivity time of current player.
 * @author Mario
 *
 */
public class InactivityTimer extends Timer {
	
    private TimerTask timerTask;
    
    /**
     * Sets the timer on the Game passed by parameter for the given delay.
     * After the time specified, calls by separate thread the inactivePlayer method of Game.
     * @param game Game to set the timer.
     * @param delay
     * @see Game#inactivePlayer()
     */
    public void set(Game game, long delay)
    {
        timerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                game.inactivePlayer();
            }
        };
        
        this.schedule(timerTask, delay);
    }
    
    /**
     * Delete the timerTask and calls the set method with the specified parameters.
     * @see #set(Game, long)
     */
    public void reset(Game game, long delay)
    {	
    	if (timerTask != null)
    		timerTask.cancel();
        set(game, delay);
    }

}
