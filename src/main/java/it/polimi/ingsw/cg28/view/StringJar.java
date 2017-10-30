
package it.polimi.ingsw.cg28.view;

/**
 * This class contains a set of strings to interact with player by CLI.
 * @author Mario, Alessandro
 *
 */
public class StringJar {
	
	/**
	 * Help instructions to play the game by CLI
	 * @return String with instructions
	 */
	public String help(){
		
		String string =
				
		"******************************************************\n" +            
		"***********  Instructions in a nutshell  *************\n" +
		"******************************************************\n" +
		"Be free to play with these tags:\n\n" +

		"-> Perform an action\n" + 
		"\t#action + number of the action\n" +
		"\texample: #action 2\n\n" +

		"-> Complete an action choosing items to use for\n" +
		"\t#fill\n\n" +

		"-> Send a message\n" +
		"\t#chat + message\n" +
		"\texample: #chat This game is awesome!\n\n" +

		"-> Leave the game\n" +
		"\t#leave + message\n\n" +
		"\texample: #leave Too easy to win against these players!\n\n" +
		
		"-> Sell something on bazaar Turn\n" +
		"\t#sell + number of what you want to sell\n" +
		"\texample: #sell 2\n\n" +
		
		"-> Buy something on bazaar Turn\n" +
		"\t#buy + yes or no\n" +
		"\texample: #buy yes\n\n" +
		
		"-> Read again these instructions\n" +
		"\t#help\n\n" +
		"******************************************************\n" +
		"******************************************************\n" +
		"******************************************************\n";
		
		return string;
		
	}
	
	/**
	 * Returns a String with the message for the game start. 
	 * @return message for the game start
	 */
	public String startMsg() {
		
		StringBuilder sb = new StringBuilder();
		sb.append("Council of Four!\n");
		sb.append("By Mario, Ale, Marco & Apu\n\n");
		sb.append("You are the first player to enter in waiting room.\n"); 
		sb.append("You can configure the game writing "+ 
				"#fill in the next 30 seconds.\n");
		sb.append("Otherwise the game will start with default config! (bazaar = true)\n");
		
		return sb.toString();
		
	}
	
	/**
	 * Returns a String with the possible MainActions(relative codes) the player can perform.
	 * @return possible MainActions the player can perform.
	 */
	private String mainActionRequest(){
		
		String mainActionRequest = new String("\nInsert the number of the action you want to perform (example #action 2):\n\nMain Actions:\n"
			+ "1) ElectionAction\n"
			+ "2) PermitTileAction\n"
			+ "3) EmporiumTileAction\n"
			+ "4) EmporiumKingAction" );
		
		return mainActionRequest;
	}
	
	/**
	 * Returns a String with the possible QuickActions(relative codes) the player can perform.
	 * @return possible QuickActions the player can perform.
	 */
	private String quickActionRequest(){
		
		String quickActionRequest = new String("\nQuick Actions: \n"
				+ "5) HireAssistantAction \n"
				+ "6) ChangeTileAction \n"
				+ "7) SendAssitantAction \n"
				+ "8) OneMoreMainAction");
		
		return quickActionRequest;
		
	}
	

	/**
	 * Returns a String with the possible MainActions(relative codes) the player can perform.
	 * @return String with main actions the player can perform
	 */
	public String getMainActionMessageRequest() {
		return mainActionRequest();
	}


	/**
	 * Returns a String with the possible QuickActions(relative codes) the player can perform.
	 * @return String with quick actions the player can perform
	 */
	public String getQuickActionRequest() {
		return quickActionRequest() + "\n\nTo END TURN type #action 9\n";
	}

	

	/**
	 * Returns a String with the possible Quick e Main Actions (relative codes) the player can perform.
	 * @return String with main and quick actions the player can perform
	 */
	public String getMainAndQuickActionsRequest() {
		
		StringBuilder sb = new StringBuilder();
		sb.append(mainActionRequest());
		sb.append(quickActionRequest());
		
		return sb.toString();
		
	}
	
	/**
	 * Returns a String with the possible Bazaar related Actions (relative codes) the player can perform.
	 * @param assistants - true if player can sell assistants
	 * @param politicCards - true if player can sell politic cards
	 * @param permitTiles - true if player can sell permit tiles
	 * @return String with bazaar actions the player can perform
	 */
	public String getBazaarSellRequest(boolean assistants, boolean politicCards, boolean permitTiles) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("Choose the kind of product (related NUMBER) you want to sell:");
		if (assistants) 
			sb.append("\n1) Assistants");
		if (politicCards) 
			sb.append("\n2) PoliticCards");
		if (permitTiles) 
			sb.append("\n3) BusinessPermit Tiles\n");
		sb.append("\nType #sell 4 to exit your sell Turn\n");
		
		return sb.toString();
		
	}
	
	/**
	 * Returns a string to notify the player to wait the game start.
	 * @return string to notify the player to wait the game start.
	 */
	public String initGame() {
		return "\n\nYou have been added to waiting room.\nYou can chat with other waiting players in the meantime.\n"
         		+ "Wait... the game will start soon!\n\n";
	}
	
	/**
	 * Returns a string to notify the player there is a message to fill.
	 * @return string to notify the player there is a message to fill
	 */
	public String fillMessage(){
		return "Type #fill to complete the action";
	}
	
	/**
	 * Returns a string to notify the player buy action is enabled.
	 * @return string to notify the player buy action is enabled
	 */
	public String buyMessage(){
		return "Type (#buy yes) or (#buy no) if you want or no to buy something";
	}
	
	/**
	 * Returns a string to notify the player bazaar is ended.
	 * @return string to notify the player bazaar is ended
	 */
	public String bazaarEnd() {
		return "Bazaar is ended!";
	}
	
	/**
	 * Returns a string to notify the player there isn't a message to fill.
	 * @return string to notify the player there isn't a message to fill
	 */
	public String errorFilledMsg() {
		return "There isn't a message to fill! Try to type [action]\n";
	}
	
	
}
