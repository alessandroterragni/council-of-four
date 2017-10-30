package it.polimi.ingsw.cg28.model.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.model.Councillor;
/** 
 * Initialize Councillor Pool
 * @author Mario
 *
 */
public class CouncilorsBuilder extends Builder {
    
	/**
	 * Builds Councillor Pool from file .txt
	 * Syntax of .txt file lines must be <code><br>[color]:[color]<br></code>, each color tag creates a Councillor of the same Color
	 * (you can change or not lines from one color to another).
	 * Colors from java.axt.Color: white,black,blue,magenta,orange,pink
	 * @param File to parse with related path
	 * @return The CardDeck of PoliticCards for the model
	 * @throws IOException 
	 *  @throws NullPointerException if param file is null
	 */
	
	public List<Councillor> build(File file) throws IOException {
		
		Preconditions.checkNotNull(file, "Configuration File couldn't be null!");
		
		String myLine = null;
		BufferedReader bufRead;
		
		List<Councillor> councilors = new ArrayList<>();
		
		try (FileReader input = new FileReader(file)){
			
			bufRead = new BufferedReader(input);
			myLine = null;
			while ( (myLine = bufRead.readLine()) != null)
			{    
			    String[] array = myLine.split(":");
			    for (int i = 0; i < array.length; i++)
			    {
			    	councilors.add(new Councillor(parserColor(array[i])));
			    }
			}
			
		} catch (IOException e) {
			throw new IOException(myLine, e);
		}
		
		Collections.shuffle(councilors);
		
		return councilors;
	}

}
