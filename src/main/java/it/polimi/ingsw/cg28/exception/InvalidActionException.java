package it.polimi.ingsw.cg28.exception;

/**
 * Exception to report an action that can't be processed by the Model.
 * @author Mario
 *
 */
public class InvalidActionException extends Exception {
	
	private static final long serialVersionUID = 3300138221565160663L;
	
	/**
	 * Constructor of the class.
	 * @param string - Error message related to the exception.
	 */
	public InvalidActionException(String string){
		super(string);
	}

}
