package it.polimi.ingsw.cg28.view.messages.action;

import it.polimi.ingsw.cg28.controller.actions.Action;

import it.polimi.ingsw.cg28.controller.actions.ActionFactory;
import it.polimi.ingsw.cg28.exception.InvalidActionException;

/**
 * Functional interface, includes a method to allow the message to be visited by an ActionFactory
 * and translated to an Action.
 * @author Mario
 *
 */
@FunctionalInterface
public interface ToTranslate {
	
	/**
	 * Allows a concrete ActionFactory visitor to visit the message in order to build the related Action.
	 * @param actionFactory - Concrete ActionFactory visitor 
	 */
	public Action translate(ActionFactory actionFactory) throws InvalidActionException;
	
}
