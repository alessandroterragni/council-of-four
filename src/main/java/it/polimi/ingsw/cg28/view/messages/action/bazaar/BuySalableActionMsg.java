package it.polimi.ingsw.cg28.view.messages.action.bazaar;

import java.util.List;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.ActionMsgHandlerInterface;
import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.controller.actions.Action;
import it.polimi.ingsw.cg28.controller.actions.ActionFactory;
import it.polimi.ingsw.cg28.exception.InvalidActionException;
import it.polimi.ingsw.cg28.model.BazaarModel;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.tmodel.TProduct;
import it.polimi.ingsw.cg28.view.Painter;
import it.polimi.ingsw.cg28.view.messages.action.turn.TurnActionMsg;

/**
 * BazaarActionMsg to make a request to buy a salable bazaar item.
 * @author Mario
 * @see TurnActionMsg
 */
public class BuySalableActionMsg extends BazaarActionMsg {

	private static final long serialVersionUID = -8827347454938738249L;
	private List<TProduct> shelf;
	private String[] codes;
	private final String[] codesRequest;
	private int numbCoins;
	private final String name;
	
	/**
	 * Constructor of the class.
	 * @param player - PlayerID making the request.
	 * @throws NullPointerException if player parameter is null.
	 */
	public BuySalableActionMsg(PlayerID player) {
		super(player);
		codes = new String[1];
		codesRequest = new String[1];
		codesRequest[0] = "Insert the item number (ONE number) you want to buy";
		name = "Buy items";
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
	 */
	@Override
	public void handle(ActionMsgHandlerInterface actionMsgHandler){
		
		actionMsgHandler.handle(this);
		
	}
	
	/**
	 * Fill method can't be used to fill this kind of message. BuySalableActionMsg also 
	 * needs the BazaarModel to be filled.
	 */
	@Override
	public void fill(ModelStatus model) throws InvalidActionException{
		Preconditions.checkNotNull(model, "The model to refer to can't be null.");
		throw new InvalidActionException("Need bazaar to be filled!");
	}
	
	/**
	 * Method to fill the message. Takes information from the model about bazaar shelf and player coins.
	 * @param model - ModelStatus model of the game 
	 * @param bazaarModel - BazaarModel model of the bazaar
	 * @throws InvalidActionException if bazaar shelf is empty or contains only product sold by the player
	 * 		   making the request.
	 */
	public void fill(ModelStatus model, BazaarModel bazaarModel) throws InvalidActionException{
		
		Preconditions.checkNotNull(model, "The model to refer to can't be null.");
		Preconditions.checkNotNull(bazaarModel, "The bazaar model to refer to can't be null.");
		
		shelf = bazaarModel.gettShelf(getPlayerID());
		
		if (shelf.isEmpty())
			throw new InvalidActionException("Apu says: Nothing to buy for you, sorry man!");
		
		numbCoins = model.getPlayer(getPlayerID()).getCoins().getValue();

	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String[] getCodesRequest(){
		return codesRequest;
	}
	
	/**
	 * {@inheritDoc} The BuySalableActionMsg needs only one code, relative to the item the player wants to buy.
	 * @return False and doesn't set the codes if price of the product chosen is greater than player number of coins or the number
	 * inserted doesn't match any product. True if codes are accepted and set.
	 */
	@Override
	public boolean setCodes(String[] codes) {
		
		Preconditions.checkNotNull(codes, "Can't set any null code.");
		
		int code;
		
		try{
			code =  Integer.parseInt(codes[0]);
		} catch (NumberFormatException e) {
			return false;
		}
		
		if (code <0 || code > shelf.size())
				return false;
		
		if (shelf.get(code - 1).getPrice() > numbCoins )
				return false;
		
		
		this.codes[0] = codes[0];
		
		setNeedCode(false);
		
		return true;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String[] getCodes(){
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
	 * Returns a list of TProduct representing bazaar shelf.
	 * @return list of TProduct representing bazaar shelf
	 */
	public List<TProduct> getShelf() {
		return shelf;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return name;
	}

}
