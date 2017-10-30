package it.polimi.ingsw.cg28.view.messages.action.bonus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.controller.actions.Action;
import it.polimi.ingsw.cg28.controller.actions.ActionFactory;
import it.polimi.ingsw.cg28.controller.bonus.NobilityBonus;
import it.polimi.ingsw.cg28.controller.updaters.NobilityBonusTranslator;
import it.polimi.ingsw.cg28.exception.InvalidActionException;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.Player;
import it.polimi.ingsw.cg28.model.Town;
import it.polimi.ingsw.cg28.tmodel.TObjectFactory;
import it.polimi.ingsw.cg28.tmodel.TTown;
import it.polimi.ingsw.cg28.view.Painter;
import it.polimi.ingsw.cg28.view.messages.action.turn.TurnActionMsg;

/**
 * BonusActionMsg to interact with a ReuseCityBonus.
 * @author Mario, Alessandro
 * @see TurnActionMsg
 */
public class ReuseCityBonusActionMsg extends BonusActionMsg{

	private static final long serialVersionUID = -7383995692348484471L;
	private List<TTown> tTowns;
	private int numbReuseCities;
	private String[] codes;
	private final String[] codesRequest;
	private int numbTowns;
	private final String name;
	
	/**
	 * Constructor of the class.
	 * @param numbReuseCities - number of cities the player can choose to retrieve bonuses of.
	 * @param player - PlayerID making the request.
	 * @throws NullPointerException if player parameter is null.
	 */
	public ReuseCityBonusActionMsg(int numbReuseCities, PlayerID player) {
		
		super(player);
		Preconditions.checkArgument(numbReuseCities > 0, "The number of chosen towns must be positive.");
		
		tTowns = new ArrayList<>();
		this.numbReuseCities = numbReuseCities;
		codes = new String[numbReuseCities];
		codesRequest = new String[numbReuseCities];
		
		for(int i = 0; i< numbReuseCities; i++)
			codesRequest[i] = "Insert number of " + ordinal(i) + "bonus (related city) you want to reuse: \n";
		
		name = "Reuse city bonus";
			
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Action translate(ActionFactory actionFactory) throws InvalidActionException {
		
		return actionFactory.build(this);
		
	}
	
	/**
	 * Checks towns on model map: returns a list of towns(towns with a player's emporium built and without 
	 * {@link NobilityBonus} as town bonus).
	 * @param model - ModelStatus model of the game
	 * @return list of towns (filtered as specified)
	 */
	public List<Town> checkTowns(ModelStatus model){
		
		Player player = model.getPlayer(getPlayerID());
		List<Town> towns = new ArrayList<>();
		
		NobilityBonusTranslator checker = 
				new NobilityBonusTranslator(model.getMap().getTownList().size());
		
		for(Town town : model.getMap().getTownList()){
			
			if (town.hasEmporium(player.getID())
				&& town.getBonus() != null){
					towns.add(town);
					town.getBonus().translate(checker);
			}
		}
		
		int[] elements = checker.getElements();
		
		for(int i=0; i<towns.size(); i++)
			if (elements[i] == 1)
				towns.remove(i);
		
		return towns;
		
	}
	
	/**
	 * {@inheritDoc} 
	 * Takes information from the model about towns between which the player can choose.
	 * If the number of towns satisfying bonus requirements is less than the number set as numbReuseCities,
	 * updates the number of cities the player can choose to retrieve bonuses of.
	 * @param model - ModelStatus model of the game 
	 * @throws InvalidActionException if there aren't towns available to satisfy bonus requirements
	 */
	@Override
	public void fill(ModelStatus model) throws InvalidActionException{
		
		Preconditions.checkNotNull(model, "The model to refer to can't be null.");
		
		List<Town> towns = checkTowns(model);
		numbTowns = towns.size();
		
		if(towns.isEmpty())
			throw new InvalidActionException("Impossible to assign bonus: no town "
					+ "available to satisfy bonus requirements");
		
		TObjectFactory tObjectFactory = new TObjectFactory();
		for(int i=0;i<numbTowns;i++){
			tTowns.add(tObjectFactory.createTtown(towns.get(i)));
		}
		
		if(towns.size() < numbReuseCities){
			codes = new String [towns.size()];
			numbReuseCities = towns.size();
		}
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String[] getCodesRequest() {
		return codesRequest;
	}
	
	/**
	 * {@inheritDoc} The ReuseCityBonusActionMsg needs as many code as set as numbReuseCities.
	 * Each code is related to a town the player wants to retrieve bonuses of.
	 * @return False and doesn't set the codes if one of the code inserted doesn't
	 * match with any town available to be chosen. True if codes are accepted and set.
	 */
	@Override
	public boolean setCodes(String[] codes) {
		
		Preconditions.checkNotNull(codes, "Codes to set can't be null.");
		
		HashSet<String> setCodes = new HashSet<>();
		
		for(String code : codes)
			setCodes.add(code);
	
		try{	

		for(String c : setCodes){
			
			int code = Integer.parseInt(c);
			
			if(code < 1 || code > numbTowns)
				return false;
			
		}
		
		} catch (NumberFormatException e) {
			return false;
		} 
		
		this.codes = codes;
		
		return true;
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String[] getCodes() {
		return codes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void getShowChoices(Painter painter) {
		painter.paint(this);
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return name;
	}
	
	/**
	 * Returns number of towns the player can choose to retrieve bonuses of.
	 * @return number of towns the player can choose to retrieve bonuses of
	 */
	public int getNumbReuseCities() {
		return numbReuseCities;
	}
	
	/**
	 * Returns towns between which the player can choose.
	 * @return towns between which the player can choose
	 */
	public List<TTown> getTowns() {
		return tTowns;
	}



}
