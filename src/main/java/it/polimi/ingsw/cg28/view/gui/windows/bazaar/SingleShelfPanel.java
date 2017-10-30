package it.polimi.ingsw.cg28.view.gui.windows.bazaar;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;


import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.polimi.ingsw.cg28.tmodel.TProduct;
import it.polimi.ingsw.cg28.tmodel.TProductAssistant;
import it.polimi.ingsw.cg28.tmodel.TProductCards;
import it.polimi.ingsw.cg28.tmodel.TProductTiles;
import it.polimi.ingsw.cg28.view.gui.windows.submap.BusinessPermitTileLabel;

/**
 * Single shelf panel in the bazaar,
 * it displays the products the player wants to sell.
 * @author Alessandro
 *
 */
public class SingleShelfPanel extends JPanel {
	
	private static final long serialVersionUID = 3273909639873250243L;
	
	private JLabel namePlateLabel;
	
	/**
	 * Constructor of the class.
	 * @param product the tProduct the player want to sell
	 */
	public SingleShelfPanel(TProduct product) {
		
		this.setBounds(53, 0, 640, 124);
		this.setVisible(true);
		this.setOpaque(false);
		
		JLabel backGroundLabel = new JLabel();
		backGroundLabel.setBounds(53, 0, 640, 124);
		backGroundLabel.setIcon(new ImageIcon("src/img/bazaar/shelf.png"));
		this.add(backGroundLabel);
		
		JLabel productLabel = new JLabel();
		productLabel.setBounds(53, 42, 418, 65);
		productLabel.setBackground(new Color(0, 0, 0, 0));
		productLabel.setLayout(new FlowLayout(FlowLayout.CENTER,0,10));
		backGroundLabel.add(productLabel);
		
		if(product instanceof TProductAssistant){
			
			int numbAssistants = ((TProductAssistant) product).getNumbAssistant();
			for(int i =0; i < numbAssistants; i++){
				TProductAssistantLabel assistantLabel = new TProductAssistantLabel();
				productLabel.add(assistantLabel);	
				}
		}
		
		if(product instanceof TProductCards){
			
			int numCards= ((TProductCards) product).getCards().length;
			
			for(int i=0;i< numCards; i++){
				TProductCardLabel cardLabel = new TProductCardLabel(((TProductCards) product).getCards()[i]);
				productLabel.add(cardLabel);
			}
		}
		
		if(product instanceof TProductTiles){
			
			int numTiles = ((TProductTiles) product).getTiles().length;
			
			for(int i=0;i<numTiles;i++){
				BusinessPermitTileLabel tileLabel = new BusinessPermitTileLabel(((TProductTiles) product).getTiles()[i],0);
				productLabel.add(tileLabel);
			}
		}
		
		namePlateLabel = new JLabel();
		namePlateLabel.setFont(new Font("Deutsch Gothic", Font.PLAIN, 13));  
		namePlateLabel.setBounds(444, 11, 81, 60);
		namePlateLabel.setBackground(new Color(0,0,0,0));
		namePlateLabel.setBorder(null);
		
		String name= "Player:" + product.getPlayerOwner();
		String price = "Price: " + product.getPrice() + "$";
		namePlateLabel.setText("<html>"+name+"<br>"+price+"</html>");
		backGroundLabel.add(namePlateLabel);
	
	}

	/**
	 * @return the namePlateLabel
	 */
	public JLabel getNamePlateLabel() {
		return namePlateLabel;
	}
	
}
