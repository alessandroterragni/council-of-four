/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.windows;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JRadioButtonMenuItem;

import it.polimi.ingsw.cg28.view.gui.MusicPlayer;

/**
 * Menu bar of the {@link GameBoardWindow}.
 * @author Alessandro
 *
 */
public class MenuBar extends JMenuBar {

	private static final long serialVersionUID = 5869891300678430675L;

	transient MusicPlayer musicPlayer;
	JCheckBoxMenuItem muteCheckButton;
	
	public MenuBar(GameBoardWindow gameBoardWindow) {
		super();
		
		musicPlayer = gameBoardWindow.getMusicPlayer();
		
		JMenu mnOptions = new JMenu("Options");
		this.add(mnOptions);
		
		JMenu mnCredits = new JMenu("Credits");
		mnCredits.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CreditsWindow creditsWindow = new CreditsWindow();
				creditsWindow.setVisible(true);
			}
		});
		this.add(mnCredits);
		
		JMenu mnRules = new JMenu("Rules");
		mnRules.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				GameRulesWindow gameRulesWindow = new GameRulesWindow();
				gameRulesWindow.setVisible(true);
			}
		});
		this.add(mnRules);
		
		JMenu mnSound = new JMenu("Sound");
		mnOptions.add(mnSound);
		
		muteCheckButton = new JCheckBoxMenuItem("Mute");
		muteCheckButton.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				JCheckBoxMenuItem source = (JCheckBoxMenuItem) e.getItemSelectable();
				if(source.isSelected()){
					musicPlayer.stopMusic();
				}
				else {
					musicPlayer.stopMusic();
					musicPlayer.playPausedSong();
				}
			}
			
		});
		mnSound.add(muteCheckButton);
		
		JMenu mnTheme = new JMenu("theme");
		mnSound.add(mnTheme);
		
		ButtonGroup group = new ButtonGroup();
		
		JRadioButtonMenuItem rdbtnmntmClassic = new JRadioButtonMenuItem("classic");
		rdbtnmntmClassic.setSelected(true);
		group.add(rdbtnmntmClassic);
		rdbtnmntmClassic.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
			
					musicPlayer.stopMusic();
					musicPlayer.playSound("src/music/theme.wav");
					muteCheckButton.setSelected(false);
				
			}
		});
		mnTheme.add(rdbtnmntmClassic);
		
		JRadioButtonMenuItem rdbtnmntmCool = new JRadioButtonMenuItem("cool");
		group.add(rdbtnmntmCool);
		rdbtnmntmCool.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				
					musicPlayer.stopMusic();
					musicPlayer.playSound("src/music/theme2.wav");
					muteCheckButton.setSelected(false);

			}
		});
		mnTheme.add(rdbtnmntmCool);
	}

	/**
	 * @return the muteCheckButton
	 */
	public JCheckBoxMenuItem getMuteCheckButton() {
		return muteCheckButton;
	}
	
}
