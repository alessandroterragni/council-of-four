package it.polimi.ingsw.cg28.model.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.model.PoliticCard;
import it.polimi.ingsw.cg28.model.decks.CardDeck;
/**
 * Builds from Input the Politic Card deck.
 * @author Mario
 *
 */
public class PoliticCardBuilder extends Builder{

/**
 * Builds Politic Card deck from file .txt
 * Syntax of .txt file lines must be <code><br>[color]:[color]<br></code>, each color tag creates a PoliticCard of the same Color
 * (you can change or not lines from one color to another).
 * Colors from java.axt.Color: white,black,blue,magenta,orange,pink
 * AllColors identifies a wildcard
 * @param File to parse with related path
 * @return The CardDeck<PoliticCard> for the model
 * @throws IOException If there's an issue during file reading process
 * @throws NullPointerException If param file is null
 */
	public CardDeck<PoliticCard> build(File file) throws IOException {
		
		Preconditions.checkNotNull(file, "Configuration File couldn't be null!");
		
		String myLine = null;
		BufferedReader bufRead;
		
		LinkedList<PoliticCard> deck = new LinkedList<>();
		
		try (FileReader input = new FileReader(file)){
			
			bufRead = new BufferedReader(input);
			myLine = null;
			while ( (myLine = bufRead.readLine()) != null)
			{    
			    String[] array = myLine.split(":");
			    for (int i = 0; i < array.length; i++)
			    {
			    	if("AllColors".equals(array[i]))
			    		deck.add(new PoliticCard(null, true));
			    	else deck.add(new PoliticCard(parserColor(array[i]),false));
			    }
			}
		} catch (Exception e) {
			throw new IOException(myLine, e);
		}
		
		return new CardDeck<>(deck);
	}

}
