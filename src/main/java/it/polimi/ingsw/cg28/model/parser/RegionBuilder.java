package it.polimi.ingsw.cg28.model.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.model.Councillor;
import it.polimi.ingsw.cg28.model.Region;
/**
 * Builds from Input the game Map
 * @author Mario
 *
 */
public class RegionBuilder extends Builder{

	/**
	 * Builds Regions from file .txt
     * Syntax of .txt file lines must be
     * <br>First line: <code>[number]</code> number of councillor for region balcony 
     * <br>Second line: <code>[number]</code> number of uncovered tiles for region
     * <br>Third line: <code>[regionType] [regionType]</code> one string for each regionType, separated by a blank space<br>
	 * @param file to parse with related path
	 * @param councilors Councilors to put in regions balcony
	 * @return Region[] array of regions
	 * @throws IOException If there's an issue during file reading process
	 * @throws NullPointerException If param file is null 
	 */
	public Region[] build(File file, List<Councillor> councilors) throws IOException {
		
		Preconditions.checkNotNull(file, "Configuration File couldn't be null!");
		Preconditions.checkNotNull(councilors, "Councilors array couldn't be null!");

		String unconveredNumber;
		String numCouncilorRegion;
		String types;
		BufferedReader bufRead;
        
		Region[] regions = null;
		
		try (FileReader input = new FileReader(file)){
			
			bufRead = new BufferedReader(input);
			
			numCouncilorRegion = bufRead.readLine();
			unconveredNumber = bufRead.readLine();
		
			types = bufRead.readLine();
			String[] regionTypes = types.split(" ");
			regions = new Region[regionTypes.length];
			for (int i = 0; i < regionTypes.length; i++){
				Councillor[] balcony = new Councillor[Integer.parseInt(numCouncilorRegion)];
				for (int j = 0; j < balcony.length; j++)
					balcony[j] = councilors.remove(0);
				regions[i] = new Region(regionTypes[i], Integer.parseInt(unconveredNumber), null, balcony);
			}
			
		}
		catch (IOException e) {
			throw new IOException("RegionBuilder fails", e);
		}	
		
		return regions;
	}

	/**
	 * Getter number of councilor for region balcony from .txt file
	 * @param file to parse with related path
	 * @return the number of councillor read by file
	 * @throws IOException If there's an issue during file reading process
	 * @see #build(File, List)
	 */
	public int getNumCouncilors(File file) throws IOException {
		
		String numCouncilors = "0";
		BufferedReader bufRead;
		
		try (FileReader input = new FileReader(file)){

			bufRead = new BufferedReader(input);
			
			numCouncilors = bufRead.readLine();

		}
		catch (IOException e) {
			throw new IOException("RegionBuilder fails", e);
		}
		
		return Integer.parseInt(numCouncilors);
	}
		
		

}
