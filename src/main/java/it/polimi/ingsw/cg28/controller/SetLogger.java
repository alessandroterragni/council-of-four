package it.polimi.ingsw.cg28.controller;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Class SetLogger offers method to set a logger to log on a .txt file.
 * @author Mario
 *
 */
public class SetLogger {
	
	/**
	 * Sets logger <code>Logger.getLogger(o.getClass().getName())</code> to log on a .txt file.
	 * <br>File path is <code>"src/MyLog" + o.getClass().getName() + ".log"</code>
	 * @param o - Object of the class to instantiate a log of
	 */
	public void setLog(Object o){
		
		Logger log = Logger.getLogger(o.getClass().getName());
		
		try { 
			
	    	log.setLevel(Level.FINE);
			FileHandler fh; 
			
	        fh = new FileHandler("src/log/MyLog" + o.getClass().getName() + ".log");  
	        log.addHandler(fh);
	        SimpleFormatter formatter = new SimpleFormatter();  
	        fh.setFormatter(formatter); 
	        log.setUseParentHandlers(false);
	        
	        log.info(o.getClass().getName() + " Logger" + Thread.currentThread());

	    } catch (SecurityException | IOException e) {  
	    	log.log(Level.WARNING, e.getMessage(), e);
	    }
		
	}

}
