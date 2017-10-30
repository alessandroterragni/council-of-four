package it.polimi.ingsw.cg28.model.parser;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import it.polimi.ingsw.cg28.model.Councillor;
import it.polimi.ingsw.cg28.model.Region;
import it.polimi.ingsw.cg28.model.parser.CouncilorsBuilder;
import it.polimi.ingsw.cg28.model.parser.RegionBuilder;

public class RegionBuilderTest {

	private static RegionBuilder builder;
	private static File f;
	private static List<Councillor> noblesPool;
	
	@BeforeClass
	public static void testBefore(){
		
		builder = new RegionBuilder();
		f = new File("src/configurationFilesTest/Regions.txt");
		
	}
	
	@Before
	public void testNoblesPool(){
		
		CouncilorsBuilder builderCouncilors;
		builderCouncilors = new CouncilorsBuilder();
		File f2 = new File("src/configurationFilesTest/Councilors.txt");
		try {
			noblesPool = builderCouncilors.build(f2);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testRegionsIsNotEmpty() {

		try {
			assert(builder.build(f,noblesPool).length > 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testRegionsContainsThreeRegions() {
		try {
			assert(builder.build(f, noblesPool).length == 3);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test (expected = IOException.class)
	public void testIOExceptionIfFileDoesntExist() throws IOException {

		builder.build(new File("src/configurationFilesTest/IOexception"), noblesPool);
		
	}
	
	@Test (expected = IOException.class)
	public void testIOExceptionParserRegion() throws IOException {

		Region[] regions = null;
		
		regions = builder.build(f, noblesPool);
		
		builder.parserRegion(regions, "NotARegion");
		
	}
	
	@Test (expected = IOException.class)
	public void testIOExceptionGetNumCouncilors() throws IOException {

		builder.getNumCouncilors(new File("src/configurationFilesTest/IOexception"));
		
	}
	
	@Test (expected = NullPointerException.class)
	public void testNullPointerExceptionIfFileIsNull() {

		try {
			builder.build(null, noblesPool);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test (expected = NullPointerException.class)
	public void testNullPointerExceptionIfNoblesPoolIsNull() {

		try {
			builder.build(f, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testRegionsIsNotNull() {
		
		try {
			assertNotNull(builder.build(f, noblesPool));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testRegionsArrayContainsRightRegions() {
		
		Region[] regions = null;
		
		try {
			regions = builder.build(f, noblesPool);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		assertTrue(regions[0].getRegionType().equals("Coast"));
		assertTrue(regions[1].getRegionType().equals("Plains"));
		assertTrue(regions[2].getRegionType().equals("Mountains"));
	
	}
	
	@Test
	public void testRegionsBalconiesAreNotEmpty() {
		
		Region[] regions = null;
		
		try {
			regions = builder.build(f, noblesPool);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		assertNotNull(regions[0].getBalcony());
		assertNotNull(regions[1].getBalcony());
		assertNotNull(regions[2].getBalcony());
			
	}
	
	@Test
	public void testRegionsBalconiesHaveFourCouncillors() {
		
		Region[] regions = null;
		
		try {
			regions = builder.build(f, noblesPool);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		assertTrue(regions[0].getBalcony().getCouncil().size() == 4);
		assertTrue(regions[1].getBalcony().getCouncil().size() == 4);
		assertTrue(regions[2].getBalcony().getCouncil().size() == 4);
			
	}
	
	@Test
	public void testRegionsHaveUnconveredNumbEqualsTwo() {
		
		Region[] regions = null;
		
		try {
			regions = builder.build(f, noblesPool);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		assertTrue(regions[0].getUnconveredNum() == 2);
		assertTrue(regions[1].getUnconveredNum() == 2);
		assertTrue(regions[2].getUnconveredNum() == 2);
		
	}

}
