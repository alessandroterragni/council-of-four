package it.polimi.ingsw.cg28.view.messages.action.turn;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.controller.actions.Action;
import it.polimi.ingsw.cg28.controller.actions.ActionFactory;
import it.polimi.ingsw.cg28.exception.InvalidActionException;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.view.Painter;

/**
 * ActionMsg to make a request to perform a OneMoreMainAction action. 
 * @author Alessandro, Mario
 * @see TurnActionMsg
 */
public class OneMoreMainActionMsg extends QuickActionMsg {

	private static final long serialVersionUID = -3020816546244947271L;
	private final String name;

	/**
	 * Constructor of the class. Sets need code to false.
	 * @param player - PlayerID making the request.
	 * @throws NullPointerException if player parameter is null.
	 */
	public OneMoreMainActionMsg(PlayerID player) {
		super(player);
		this.setNeedCode(false);
		this.setFilled(true);
		name = "Perform an additional main action";
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
	 * Simply returns.
	 */
	@Override
	public void fill(ModelStatus model) {
		return;
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
	
	
}

