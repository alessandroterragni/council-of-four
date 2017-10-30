package it.polimi.ingsw.cg28.view.messages.action.turn;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.controller.actions.Action;
import it.polimi.ingsw.cg28.controller.actions.ActionFactory;
import it.polimi.ingsw.cg28.exception.InvalidActionException;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.tmodel.TMap;
import it.polimi.ingsw.cg28.tmodel.TNoblesPool;
import it.polimi.ingsw.cg28.tmodel.TObjectFactory;
import it.polimi.ingsw.cg28.tmodel.TRegion;
import it.polimi.ingsw.cg28.view.Painter;


/**
 * ActionMsg to make a request to perform a Election action. 
 * @author Alessandro, Mario
 * @see TurnActionMsg
 */
public class ElectionActionMsg extends MainActionMsg {

	private static final long serialVersionUID = -3941750785419327922L;
	private TRegion[] regions;
	private TNoblesPool pool;
	private TMap map;
	private String[] codes;
	private final String[] codesRequest;
	private int regionsSize;
	private int noblesPoolSize;
	private final String name;
	
	/**
	 * Constructor of the class. Sets need code to true.
	 * @param player - PlayerID making the request.
	 * @throws NullPointerException if player parameter is null.
	 */
	public ElectionActionMsg(PlayerID player) {
		super(player);
		this.setNeedCode(true);
		codes = new String[2];
		codesRequest = new String[2]; 
		codesRequest[0] = "Insert the number of the balcony you want to elect the councillor in: \n";
		codesRequest[1] = "Insert the councillor number (displayed) you want to elect: \n";
		name = "Elect a councillor";
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Action translate(ActionFactory actionFactory) throws InvalidActionException {
			return actionFactory.build(this);
	}
	
	/**
	 * {@inheritDoc} 
	 * Takes information from the model about regions balconies, king balcony and nobles pool.
	 * @param model - ModelStatus model of the game 
	 */
	@Override
	public void fill(ModelStatus model) {
		
		Preconditions.checkNotNull(model, "The model to refer to can't be null.");
		
		regionsSize = model.getRegions().length;
		noblesPoolSize = model.getNoblesPool().size();
		regions = new TRegion[regionsSize];
		
		TObjectFactory factory = new TObjectFactory();
		for(int i=0;i<regionsSize;i++)
		{	
			regions[i] = factory.createTRegion(model.getRegion(i));
		}
		
		map = factory.createTMap(model.getMap());
		pool = factory.createTNoblesPool(model.getNoblesPool());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String[] getCodesRequest(){
		return codesRequest;
	}
	
	/**
	 * {@inheritDoc} The ElectionActionMsg needs two codes: the first related to the balcony chosen,
	 * the second related to the councillor in the nobles pool the player wants to elect.
	 * @return False and doesn't set the codes if if any of the codes inserted doesn't
	 * match with any councillor/balcony available to be chosen. True if codes are accepted and set.
	 */
	@Override
	public boolean setCodes(String[] codes) {
		
		Preconditions.checkNotNull(codes, "Can't set any null code.");
		
		int code0;
		int code1;
		
		try{
			code0 = Integer.parseInt(codes[0]);
			code1 = Integer.parseInt(codes[1]);	
		} catch (NumberFormatException e) {
			return false;
		}
		
		boolean match = (code0 > 0) && (code0 <= regionsSize+1) &&
			(code1 > 0) && (code1 <= noblesPoolSize);
		
		if (match)
		{	
			this.codes = codes;
			this.setNeedCode(false);
		}
		return match;
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String[] getCodes(){
		return codes;
	}
	
	/**
	 * If set, returns the balconyCode set.
	 * @return balconyCode
	 * @throws NumberFormatException if message codes are not set
	 */
	public int getBalconyCode() {
		return Integer.parseInt(codes[0]);
	}
	
	/**
	 * If set, returns the councilorCode set.
	 * @return councilorCode
	 * @throws NumberFormatException if message codes are not set
	 */
	public int getCouncilorCode() {
		return Integer.parseInt(codes[1]);
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
	 * Returns the number of map regions
	 * @return number of map regions
	 */
	public int getRegionNumber() {
		return regionsSize;
	}
	
	/**
	 * Returns map regions.
	 * @return the map regions
	 */
	public TRegion[] getRegions() {
		return regions;
	}


	/**
	 * Returns the noblesPool
	 * @return the noblesPool
	 */
	public TNoblesPool getPool() {
		return pool;
	}
	
	/**
	 * Returns the game map containing also king balcony
	 * @return the game map
	 */
	public TMap getMap() {
		return map;
	}
	
}
