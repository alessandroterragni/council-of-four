/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui.listeners;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

import it.polimi.ingsw.cg28.connections.client.Client;
import it.polimi.ingsw.cg28.tmodel.TTown;
import it.polimi.ingsw.cg28.view.gui.windows.GameBoardWindow;
import it.polimi.ingsw.cg28.view.messages.action.bonus.ReuseCityBonusActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.EmporiumKingActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.EmporiumTileActionMsg;


/**
 * this class is used to listen the click on the map, 
 * it saves the position of the click on the image displayed, 
 * it searches the color of the pixel clicked in the ColorMapImg image
 * ( in which every town has a different color), 
 * and, parsing the colorMap.txt file of the ColorCity 
 * (where every city name is associated with the corresponding color on the ColorMap),
 * it discover the name of the town clicked,
 * checking the pixel color
 * @author Alessandro
 *
 */
public class ColorMapListener extends MouseAdapter {
	
	private GameBoardWindow window;
	private BufferedImage img = null;
	private List<TTown> chosenTowns = new ArrayList<>();
	private List<TTown> rightClickedTowns = new ArrayList<>();
	private String townClickedName;
	private String rightClicktownName;
	private List<ColorCity> cities= null;
	
	private Boolean b = true;
	
	private static final Logger log = Logger.getLogger(Client.class.getName());
	
	/**
	 * constructor of the class
	 * @param window {@link GameBoardWindow}
	 * @param colorMapImg the path of the ColorMap image file
	 * @param colorMapTxt the path to the ColorMapTxt file
	 * @throws IOException if the file of the image is corrupted or the path are wrong
	 */
	public ColorMapListener(GameBoardWindow window,String colorMapImg,String colorMapTxt) {
		
		this.window = window;
		
		try {
			img = ImageIO.read(new File(colorMapImg));
		} catch (IOException e) {
			log.log(Level.WARNING, "ImageIo Exception", e);
		}
		
		
		try {
			cities = parseTownsFile(colorMapTxt);
		} catch (IOException e) {
			log.log(Level.WARNING, "Io Exception", e);
		}
	}
	
	/**
	 * listen for the click of the mouse:
	 * if the click is right, it shows the information of the town clicked using TownInformationPanel;
	 * if the click is double right, it makes the TownInformationPanel disappear;
	 * if the click is left : it sets the code of the actionMsg stored in the Gui and notifies it
	 * 
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		
		
		if(!SwingUtilities.isRightMouseButton(e)){
		
				if(window.getGuiHandler().getActionMsg() instanceof EmporiumTileActionMsg){
					
				chosenTowns = window.getMapPanel().getChosenTowns();
				Point p = e.getPoint();
				findTown(p.x, p.y,true);
				
				for(TTown town: chosenTowns){
					if(town.getName().equals(townClickedName)){
						window.getGuiHandler().setCodes(Integer.toString(chosenTowns.indexOf(town)+1), 1);
						b = window.getGuiHandler().getActionMsg().setCodes(window.getGuiHandler().getCodes());
						if(b){
							window.getGuiHandler().notify(window.getGuiHandler().getActionMsg());
							window.getRulesPanel().getComunicationLabel().setVisible(false);
							}
						}
					}
				}
				
				if(window.getGuiHandler().getActionMsg() instanceof EmporiumKingActionMsg){
					
					chosenTowns = window.getMapPanel().getChosenTowns();
					Point p = e.getPoint();
					findTown(p.x, p.y,true);
					
					for(TTown town: chosenTowns){
						if(town.getName().equals(townClickedName)){
							window.getGuiHandler().setCodes(Integer.toString(chosenTowns.indexOf(town)+1), 0);
							b = window.getGuiHandler().getActionMsg().setCodes(window.getGuiHandler().getCodes());
							if(b){
								window.getGuiHandler().notify(window.getGuiHandler().getActionMsg());
								window.getRulesPanel().getComunicationLabel().setVisible(false);
								}
							}
						}
				}
				
				if(window.getGuiHandler().getActionMsg() instanceof ReuseCityBonusActionMsg){
					
					if(window.getGuiHandler().getCodeBuffer().length()!=window.getGuiHandler().getActionMsg().getCodesRequest().length)
					{
						chosenTowns = window.getMapPanel().getChosenTowns();
						Point p = e.getPoint();
						findTown(p.x, p.y,true);
						
						for(TTown town: chosenTowns){
							if(town.getName().equals(townClickedName)){
								window.getGuiHandler().getCodeBuffer().append(Integer.toString(chosenTowns.indexOf(town)));
								}
							}
					}
					
					if(window.getGuiHandler().getCodeBuffer().length()==window.getGuiHandler().getActionMsg().getCodesRequest().length){
						String[] codes = window.getGuiHandler().getCodeBuffer().toString().split("");
						for(int i =0;i<codes.length;i++){
							window.getGuiHandler().setCodes(codes[i], i);
						}
						
						
						b= window.getGuiHandler().getActionMsg().setCodes(window.getGuiHandler().getCodes());
						window.getGuiHandler().getCodeBuffer().setLength(0);
						if(b){		
									window.getGuiHandler().notify(window.getGuiHandler().getActionMsg());
									window.getRulesPanel().getComunicationLabel().getComunicationTextArea().setText("");
									window.getRulesPanel().getComunicationLabel().setVisible(false);
						}
					}
				}
		
		}
		
		if(SwingUtilities.isRightMouseButton(e)){
			if(window.getRulesPanel().getTownInformationPanel().isVisible()) window.getRulesPanel().getTownInformationPanel().setVisible(false);
			else
			{
				rightClickedTowns = window.getMapPanel().getStaticTowns();
				Point p = e.getPoint();
				findTown(p.x, p.y,false);
				
				for(TTown town: rightClickedTowns){
					if(town.getName().equals(rightClicktownName)){
						window.getRulesPanel().getTownInformationPanel().setVisible(true);
						window.getRulesPanel().getTownInformationPanel().fillInfo(town);
						
					}
				}
			}
		}
		
		
	}
	
	/**
	 * it finds the town clicked looking for the color of the pixel clicked
	 * @param x the x coordinate of the pixel clicked
	 * @param y the y coordinate of the pixel clicked
	 * @param clicked true if you want to use it for click detection, false if you want to use for right click detection
	 */
	public void findTown(int x,int y,boolean clicked){
		
		int rgb = img.getRGB(x, y);
		String town = null;
			
		for (ColorCity city : cities) {
			if(city.getRgbColor()==rgb){
				town = city.getName();
			}
		}
		
		if(clicked) townClickedName = town;
		else rightClicktownName = town;
		
		
	}
	
	/**
	 * parser of the file with name and color of the towns
	 * @param colorMapTxtFile the path to the ColorMapTxt file
	 * @return a list of {@link ColorCity} corresponding of the content of the file
	 * @throws IOException if the txt file is corrupted or the path is wrong
	 */
	public List<ColorCity> parseTownsFile(String colorMapTxtFile) throws IOException{

		File map = new File(colorMapTxtFile);
		BufferedReader bufRead;
		String myLine = null;
		List<ColorCity> cities = new ArrayList<>();
		
		try (FileReader input = new FileReader(map)){
			
			bufRead = new BufferedReader(input);
			myLine = null;
			while ( (myLine = bufRead.readLine()) != null)
			{    
			    String[] array = myLine.split(":");
			    cities.add(new ColorCity(Integer.parseInt(array[0]),array[1]));
			}
		
		} catch (Exception e) {
			throw new IOException(myLine, e);
		}
		
		return cities;
	}
}
