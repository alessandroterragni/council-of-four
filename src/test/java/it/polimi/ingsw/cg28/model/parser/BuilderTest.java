package it.polimi.ingsw.cg28.model.parser;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.bonus.AssistantBonus;
import it.polimi.ingsw.cg28.controller.bonus.BonusType;
import it.polimi.ingsw.cg28.controller.bonus.CoinBonus;
import it.polimi.ingsw.cg28.controller.bonus.DrawCardBonus;
import it.polimi.ingsw.cg28.controller.bonus.MainActionBonus;
import it.polimi.ingsw.cg28.controller.bonus.NobilityBonus;
import it.polimi.ingsw.cg28.controller.bonus.ReuseCityBonus;
import it.polimi.ingsw.cg28.controller.bonus.ReusePermitBonus;
import it.polimi.ingsw.cg28.controller.bonus.TakePermitTileBonus;
import it.polimi.ingsw.cg28.controller.bonus.VictoryPointBonus;
import it.polimi.ingsw.cg28.model.parser.BonusTileBuilder;
import it.polimi.ingsw.cg28.model.parser.Builder;

public class BuilderTest {
	
	private static Builder builder;
	
	@BeforeClass
	public static void testBefore(){
		
		builder = new BonusTileBuilder();
		
	}
	
	@Test
	public void testBonusParserCoinBonus() throws IOException{
		
		assertTrue(builder.parserBonus("CB","3").getClass().equals(CoinBonus.class));
		assertTrue(builder.parserBonus("CB","3").getValue() == 3);
		
	}
	

	@Test
	public void testBonusParserDrawCardBonus() throws IOException{
		
		assertTrue(builder.parserBonus("DCB","2").getClass().equals(DrawCardBonus.class));
		assertTrue(builder.parserBonus("DCB","2").getValue() == 2);
		
	}
	

	@Test
	public void testBonusParserMainActionBonus() throws IOException{
		
		assertTrue(builder.parserBonus("MAB","4").getClass().equals(MainActionBonus.class));
		assertTrue(builder.parserBonus("MAB","4").getValue() == 4);
		
	}
	

	@Test
	public void testBonusParserNobilityBonus() throws IOException{
		
		assertTrue(builder.parserBonus("NB","3").getClass().equals(NobilityBonus.class));
		assertTrue(builder.parserBonus("NB","3").getValue() == 3);
		
	}
	

	@Test
	public void testBonusParserVictoryPointBonus() throws IOException{
		
		assertTrue(builder.parserBonus("VPB","3").getClass().equals(VictoryPointBonus.class));
		assertTrue(builder.parserBonus("VPB","3").getValue() == 3);
		
	}
	

	@Test
	public void testBonusParserReusePermitBonus() throws IOException{
		
		assertTrue(builder.parserBonus("RPB","3").getClass().equals(ReusePermitBonus.class));
		assertTrue(builder.parserBonus("RPB","3").getValue() == 3);
		
	}
	

	@Test
	public void testBonusParserAssistantBonus() throws IOException{
		
		assertTrue(builder.parserBonus("AB","3").getClass().equals(AssistantBonus.class));
		assertTrue(builder.parserBonus("AB","3").getValue() == 3);
		
	}
	

	@Test
	public void testBonusParserReuseCityBonus() throws IOException{
		
		assertTrue(builder.parserBonus("RCB","3").getClass().equals(ReuseCityBonus.class));
		assertTrue(builder.parserBonus("RCB","3").getValue() == 3);
		
	}
	

	@Test
	public void testBonusParserTakePermitTileBonus() throws IOException{
		
		assertTrue(builder.parserBonus("TPB","3").getClass().equals(TakePermitTileBonus.class));
		assertTrue(builder.parserBonus("TPB","3").getValue() == 3);
		
	}
	

	@Test (expected = IOException.class)
	public void testBonusParserThrowIOException() throws IOException{
		
		BonusType bonus = builder.parserBonus("AAA","3");
		
		assertTrue(bonus.getValue() == 3);
	}
	

	@Test (expected = NullPointerException.class)
	public void testBonusParserThrowNullPointerExceptionIfToParseIsNull() throws IOException{
		
		BonusType bonus = builder.parserBonus(null,"3");
		
		assertTrue(bonus.getValue() == 3);
		
	}
	
	@Test (expected = NullPointerException.class)
	public void testBonusParserThrowNullPointerExceptionIfBonusValueIsNull() throws IOException{
		
		BonusType bonus = builder.parserBonus("CB", null);
		
		assertTrue(bonus.getValue() == 3);
		
	}
	
	@Test (expected = NumberFormatException.class)
	public void testBonusParserThrowNumberFormatExceptionIfBonusValueStringNotRepresentAnInteger() throws IOException{
		
		BonusType bonus = builder.parserBonus("CB", "A A");
		
		assertTrue(bonus.getValue() == 3);
		
	}

}
