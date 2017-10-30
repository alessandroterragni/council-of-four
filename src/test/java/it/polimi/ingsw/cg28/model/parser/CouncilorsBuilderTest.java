package it.polimi.ingsw.cg28.model.parser;

import static org.junit.Assert.*;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import it.polimi.ingsw.cg28.model.Councillor;
import it.polimi.ingsw.cg28.model.parser.CouncilorsBuilder;

public class CouncilorsBuilderTest {
	
	private static CouncilorsBuilder builder;
	private static File f;
	
	@BeforeClass
	public static void testBefore(){
		
		builder = new CouncilorsBuilder();
		f = new File("src/configurationFilesTest/Councilors.txt");
		
	}
	
	@Test
	public void testNoblesPoolIsNotEmpty() {

		try {
			assert(builder.build(f).size() > 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testNoblesPoolContainsEighteenCouncilors() {
		try {
			assert(builder.build(f).size() == 18);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test (expected = IOException.class)
	public void testIOExceptionParserColor() throws IOException {
		
		builder.parserColor("NotAColor");
		
	}
	
	@Test (expected = IOException.class)
	public void testIOExceptionIfFileDoesntExist() throws IOException {

		builder.build(new File("src/configurationFilesTest/IOexception"));
		
	}
	
	@Test (expected = NullPointerException.class)
	public void testNullPointerExceptionIfFileIsNull() throws IOException {
		
		builder.build(null);
		
	}
	
	@Test
	public void testNoblesPoolIsNotNull() {
		
		try {
			assertNotNull(builder.build(f));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testNoblesPoolContainsRightCouncillors() {
		
		List<Councillor> noblesPool = null;
		
		try {
			noblesPool = builder.build(f);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		int[] colors = new int[6];
		
		for(Councillor councillor: noblesPool){
			if (Color.WHITE.equals(councillor.getColor()))
				colors[0] ++;
			if (Color.PINK.equals(councillor.getColor()))
				colors[1] ++;
			if (Color.ORANGE.equals(councillor.getColor()))
				colors[2] ++;
			if (Color.BLACK.equals(councillor.getColor()))
				colors[3] ++;
			if (Color.MAGENTA.equals(councillor.getColor()))
				colors[4] ++;
			if (Color.BLUE.equals(councillor.getColor()))
				colors[5] ++;
			
		}
			
		assertTrue(colors[0] == 3);
		assertTrue(colors[1] == 3);
		assertTrue(colors[2] == 3);
		assertTrue(colors[3] == 3);
		assertTrue(colors[4] == 3);
		assertTrue(colors[5] == 3);
		
	}

}
