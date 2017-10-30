package it.polimi.ingsw.cg28.view.messages.event;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.tmodel.TObjectFactory;
import it.polimi.ingsw.cg28.tmodel.TPlayer;
import it.polimi.ingsw.cg28.view.ViewHandler;

/**
 * Represents an event message containing the status and possessions of a player for this turn.
 * @author Marco
 *
 */
public class PlayerStatusMsg extends EventMsg {

	private static final long serialVersionUID = 4993180268117663407L;
	private TableStatusMsg tableStatusMsg;
	private TPlayer player;
	
	/**
	 * The constructor of the class, creates a new PlayerStatusMsg.
	 * @param model - The ModelStatus to refer to
	 * @param receiver - The ID of the receiver of this message
	 * @param currentPlayer - The ID of the player that is currently playing his/her turn
	 * @throws NullPointerException if any parameter is null
	 */
	public PlayerStatusMsg(ModelStatus model, PlayerID receiver,  PlayerID currentPlayer) {
		
		Preconditions.checkNotNull(model, "The model to refer to can't be null.");
		Preconditions.checkNotNull(receiver, "The receiver of this message can't be null.");
		Preconditions.checkNotNull(currentPlayer, "The associated player can't be null.");
		
		TObjectFactory factory = new TObjectFactory();
		this.tableStatusMsg = new TableStatusMsg(model, currentPlayer);
		setPlayer(receiver);
		this.player = factory.createTPlayer(model.getPlayer(receiver), true);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void read(ViewHandler viewHandler) {
		viewHandler.handle(this);

	}

	/**
	 * Fetches the related message about the state of the game table.
	 * @return The related TableStatusMsg
	 */
	public TableStatusMsg getTableStatusMsg() {
		return tableStatusMsg;
	}
	
	/**
	 * Fetches the receiver player.
	 * @return The TPlayer object representing the receiver
	 */
	public TPlayer getTPlayer() {
		return player;
	}
}
