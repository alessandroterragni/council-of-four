package it.polimi.ingsw.cg28.controller.actions.bazaar;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.actions.Action;
import it.polimi.ingsw.cg28.model.BazaarModel;

/**
 * Abstract class for bazaar action. Extends Action class.
 * @author Mario
 *
 */
public abstract class BazaarAction implements Action {
	
	protected BazaarModel bazaarModel;
	
	/**
	 * Abstract constructor
	 * @param bazaarModel model related to bazaar
	 * @throws NullPointerException if bazaarModel is null
	 */
	protected BazaarAction(BazaarModel bazaarModel){
		
		Preconditions.checkNotNull(bazaarModel);
		
		this.bazaarModel = bazaarModel;
	}

}
