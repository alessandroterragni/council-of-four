/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.windows.map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import it.polimi.ingsw.cg28.connections.client.Client;

/**
 * Label that display the king on the map the position is parsed from a .txt where every city has an associated 
 * position to display the king.
 * @author Alessandro
 *
 */
public class KingCrownLabel extends JLabel {
	
	private static final long serialVersionUID = 165427953255911177L;
	private static final Logger log = Logger.getLogger(Client.class.getName());
	
	private transient List<KingPosition> cities;
	
	/**
	 * Constructor of the class
	 * @param kingImgPath path of the Image of the king
	 * @param kingFilePath path of the file with the postions of the king
	 */
	public KingCrownLabel(String kingImgPath, String kingFilePath) {
		super();
		this.setIcon(new ImageIcon(kingImgPath));
		this.setVisible(true);
		try {
			cities = parseKingPosition(kingFilePath);
		} catch (IOException e) {
			log.log(Level.WARNING, "ImageIo Exception", e);
		}
	}
	
	/**
	 * It moves the king from the city where is set to the one is passed as a parameter.
	 * @param name
	 */
	public void moveKing(String name){
		
		for (KingPosition kingPosition : cities) {
			if(kingPosition.getTownName().equals(name)){
				this.setBounds(kingPosition.getX(), kingPosition.getY(), 20, 20);
			}
		}
		
	}
	
	/**
	 * Method to parse the file of the position of the king.
	 * @return a list of {@link KingPosition} that will be used to find the position associated to a specified city 
	 * @throws IOException
	 */
	public List<KingPosition> parseKingPosition(String kingFile) throws IOException{

		File map = new File(kingFile);
		BufferedReader bufRead;
		String myLine = null;
		List<KingPosition> cities = new ArrayList<>();
		
		try (FileReader input = new FileReader(map)){
			
			bufRead = new BufferedReader(input);
			myLine = null;
			while ( (myLine = bufRead.readLine()) != null)
			{    
			    String[] array = myLine.split(":");
			    cities.add(new KingPosition(array[0], Integer.parseInt(array[1]), Integer.parseInt(array[2])));
			}
		
		} catch (Exception e) {
			throw new IOException(myLine, e);
		}
		
		return cities;
		
	}
}
