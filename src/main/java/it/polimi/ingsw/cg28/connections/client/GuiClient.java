/**
 * 
 */
package it.polimi.ingsw.cg28.connections.client;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.NotBoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import it.polimi.ingsw.cg28.connections.RequestHandler;
import it.polimi.ingsw.cg28.view.ViewController;
import it.polimi.ingsw.cg28.view.ViewHandler;
import it.polimi.ingsw.cg28.view.cli.CliHandler;
import it.polimi.ingsw.cg28.view.cli.CommandReader;
import it.polimi.ingsw.cg28.view.gui.GuiHandler;
import it.polimi.ingsw.cg28.view.gui.windows.GuiClientFrame;



/**
 * Entry point for a single Player by GUI. (Allows the player to switch also to command line interface).
 * @author Mario, Alessandro.
 *
 */
public class GuiClient {
	
	private GuiClientFrame frame;
	private JTextField nameTextField;
	private JRadioButton rdbtnGui;
	private JRadioButton rdbtnCli;
	private JRadioButton rdbtnRmi;
	private JRadioButton rdbtnSocket;
	private JButton btnPlay;
	private String playerName = null;
	
   
	private static final Logger log = Logger.getLogger(Client.class.getName());

	 /**
     * Private constructor to hide the implicit public one. Class GuiClient couldn't be instantiated.
     * Launches the application.
     */
	private GuiClient() {
		
		try {
			
		    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/font/deutsch.ttf")));
		    
		} catch (IOException e) {
			log.log(Level.WARNING, "IOException", e);
		} catch(FontFormatException e) {
			log.log(Level.WARNING, "FontFormat Exception", e);
		}

		initialize();
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				
				try {
					GuiClient window = new GuiClient();
					window.frame.setVisible(true);
				} catch (Exception e) {
					log.log(Level.WARNING, "EDT Exception", e);
				}
				
			}
			
		});
		
	}
	
	/**
	 * Initialise a {@link GuiClientFrame} to collect player's informations.
	 */
	private void initialize() {
		
		PrintWriter writer = new PrintWriter(System.out);
    	CommandReader commandReader = new CommandReader(writer);
    	
    	frame = new GuiClientFrame();
		
		JPanel settingsPanel = frame.getSettingsPanel();
		
		ImageIcon council = new ImageIcon("src/img/guiClient/council.png");
		JLabel titleLabel = new JLabel(council);
		titleLabel.setBounds(10, 8, 262, 42);
		settingsPanel.add(titleLabel);

		JLabel lblNewLabel = new JLabel("Name");
		lblNewLabel.setFont(new Font("Deutsch Gothic", Font.PLAIN, 18));
		lblNewLabel.setBounds(18, 61, 97, 14);
		settingsPanel.add(lblNewLabel);
		
		nameTextField = new JTextField();
		nameTextField.setBounds(107, 58, 145, 20);
		settingsPanel.add(nameTextField);
		nameTextField.setColumns(10);
		
		JLabel lblError = new JLabel("<html><font color='#a61212'>Name not acceptable - Retry!</font></html>");
		lblError.setFont(new Font("Deutsch Gothic", Font.PLAIN, 16));
		lblError.setBounds(18, 81, 220, 16);
		lblError.setVisible(false);
		settingsPanel.add(lblError);
		
		JLabel lblGraphicInterface = new JLabel("Graphic Interface");
		lblGraphicInterface.setFont(new Font("Deutsch Gothic", Font.PLAIN, 18));
		lblGraphicInterface.setBounds(15, 104, 140, 20);
		settingsPanel.add(lblGraphicInterface);
		
		ImageIcon guiOff = new ImageIcon("src/img/guiClient/GUIOff.png");
		rdbtnGui = new JRadioButton(guiOff);
		ImageIcon guiOn = new ImageIcon("src/img/guiClient/GUIOn.png");
		rdbtnGui.setSelectedIcon(guiOn);
		rdbtnGui.setBounds(120, 125, 109, 23);
		rdbtnGui.setSelected(true);
		rdbtnGui.setOpaque(false);
		settingsPanel.add(rdbtnGui);
		
		ImageIcon cliOff = new ImageIcon("src/img/guiClient/CLIOff.png");
		rdbtnCli = new JRadioButton(cliOff);
		ImageIcon cliOn = new ImageIcon("src/img/guiClient/CLIOn.png");
		rdbtnCli.setBounds(20, 125, 109, 23);
		rdbtnCli.setOpaque(false);
		rdbtnCli.setSelectedIcon(cliOn);
		
		settingsPanel.add(rdbtnCli);
		
		ButtonGroup graphicBtmGroup = new ButtonGroup();
		graphicBtmGroup.add(rdbtnGui);
		graphicBtmGroup.add(rdbtnCli);
		
		JLabel lblConnection = new JLabel("Connection");
		lblConnection.setFont(new Font("Deutsch Gothic", Font.PLAIN, 18));
		lblConnection.setBounds(18, 171, 115, 18);
		settingsPanel.add(lblConnection);
		
		ImageIcon rmiOff = new ImageIcon("src/img/guiClient/RMIOff.png");
		rdbtnRmi = new JRadioButton(rmiOff);
		ImageIcon rmiOn = new ImageIcon("src/img/guiClient/RMIOn.png");
		rdbtnRmi.setSelectedIcon(rmiOn);
		rdbtnRmi.setOpaque(false);
		rdbtnRmi.setBounds(20, 192, 109, 23);
		settingsPanel.add(rdbtnRmi);
		
		ImageIcon socketOff = new ImageIcon("src/img/guiClient/SocketOff.png");
		rdbtnSocket = new JRadioButton(socketOff);
		ImageIcon socketOn = new ImageIcon("src/img/guiClient/SocketOn.png");
		rdbtnSocket.setSelectedIcon(socketOn);
		rdbtnSocket.setSelected(true);
		rdbtnSocket.setOpaque(false);
		rdbtnSocket.setBounds(120, 192, 109, 23);
		settingsPanel.add(rdbtnSocket);
		
		ButtonGroup connectionBtmGroup = new ButtonGroup();
		connectionBtmGroup.add(rdbtnRmi);
		connectionBtmGroup.add(rdbtnSocket);
		
		btnPlay = new JButton("Play");
		btnPlay.setFont(new Font("Deutsch Gothic", Font.PLAIN, 14));
		btnPlay.setBounds(82, 236, 89, 23);
		settingsPanel.add(btnPlay);
		btnPlay.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				ViewHandler handler;
				
				playerName = nameTextField.getText();
				
				if(!playerName.matches("^\\s*$")){
				
			    	if (rdbtnGui.isSelected()) {
			    		handler = new GuiHandler(frame);
			    		log.info("Gui interface chosen");
			        } else {
			        	handler = new CliHandler(commandReader);
			        	log.info("Cli interface chosen");
			        	frame.dispose();
			        }
			    	
			    	ViewController controller = new ViewController(handler);
			        
			    	try {
	
			            ConnectionTypeFactory connectionTypeFactory;
	
			            if (rdbtnRmi.isSelected()) {
			            	connectionTypeFactory = new RMIFactory("localhost", controller, playerName);
			            } else {
			            	connectionTypeFactory = new SocketFactory("localhost", controller, playerName);
			            }
	
			            RequestHandler requestHandler = connectionTypeFactory.getRequestHandler();
			            
			            handler.initialize(requestHandler, connectionTypeFactory.getPlayerID());
		                
			        } catch (IOException ex) {
			        	log.log(Level.WARNING, "Socket Exception", ex);
			        } catch (NotBoundException ex) {
			        	log.log(Level.WARNING, "RMI Exception", ex);
			        }
				} else {
					lblError.setVisible(true);
				}
			}
		});
		
	}
		
}
