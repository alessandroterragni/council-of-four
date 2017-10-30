package it.polimi.ingsw.cg28.view.messages.event;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.tmodel.TBonus;
import it.polimi.ingsw.cg28.tmodel.TObjectFactory;
import it.polimi.ingsw.cg28.view.ViewHandler;

/**
 * Represents an events message containing the execution of the start of the match.
 * @author Marco
 *
 */
public class WelcomeEventMsg extends EventMsg {

	private static final long serialVersionUID = 139587658716588138L;
	private TableStatusMsg tableStatus;
	private TBonus[] nobilityTrack;
	private String welcome;
	
	/**
	 * The constructor of the class, creates a new WelcomeEventMsg.
	 * @param model - The ModelStatus to refer to
	 * @throws NullPointerException if the input model is null
	 */
	public WelcomeEventMsg(ModelStatus model) {
		
		Preconditions.checkNotNull(model, "The model to refer to can't be null.");
	
		tableStatus = new TableStatusMsg(model, new PlayerID("First Turn not yet started"));
		welcome = "The match has begun!";
		
		TObjectFactory factory = new TObjectFactory();
		nobilityTrack = factory.createTNobilityTrack(model.getNobilityTrackBonus());
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void read(ViewHandler viewHandler) {
		
		viewHandler.handle(this);

	}

	/**
	 * Fetches the welcome string for the match beginning.
	 * @return The string that welcomes players into the match
	 */
	public String getWelcome() {
		return welcome;
	}

	/**
	 * Fetches the game table status for this game.
	 * @return The related TableStatusMsg
	 */
	public TableStatusMsg getTableStatus() {
		return tableStatus;
	}
	
	/**
	 * Fetches the bonuses of this match's nobility track.
	 * @return An array of TBonuses containing the nobility track's bonuses
	 */
	public TBonus[] getNobilityTrack() {
		return nobilityTrack;
	}


}
