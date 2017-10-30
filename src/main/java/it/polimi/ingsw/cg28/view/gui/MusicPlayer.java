package it.polimi.ingsw.cg28.view.gui;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import it.polimi.ingsw.cg28.connections.client.Client;
import it.polimi.ingsw.cg28.view.gui.windows.GameBoardWindow;

/**
 * Class used to play and stop music.
 * @author Alessandro
 *
 */
public class MusicPlayer {
	
	private GameBoardWindow gameBoardWindow;
	private Clip clip;
	private String currentSong;
	private static final Logger log = Logger.getLogger(Client.class.getName());
	
	public MusicPlayer(GameBoardWindow window) {
		this.gameBoardWindow = window;
	}
	
	/**
	 * Play the song passed as a path.
	 * @param song you want to play
	 */
	public void playSound(String song)
	 {
		if(!gameBoardWindow.getMyMenuBar().getMuteCheckButton().isSelected()){
				   try 
				   {
					this.currentSong = song;
				    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(song).getAbsoluteFile( ));
				    clip = AudioSystem.getClip( );
				    clip.open(audioInputStream);
				    clip.start();
				    clip.loop(Clip.LOOP_CONTINUOUSLY);
					
				   }
				   catch(Exception ex)
				   {
					   log.log(Level.WARNING, "Music exception", ex);
				   }
		}
	 }
	
	/**
	 * Play the last song played in the music player.
	 */
	public void playPausedSong(){
		playSound(currentSong);
	}
	
	/**
	 * Stop the music.
	 */
	public void stopMusic(){
		clip.stop();
	}

	
}
