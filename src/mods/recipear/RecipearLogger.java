package mods.recipear;

import java.util.logging.Logger;

public class RecipearLogger {
	
	static Logger logger;
	
	public static void info(String message) 
	{
		logger.info(message);
	}
	
	public static void debug(String message) 
	{
		if(RecipearConfig.debug) logger.info("[DEBUG] " + message);
	}
	
	public static void severe(String message)
    {
		logger.severe(message);
    }
	
	public static void warning(String message)
    {
		logger.warning(message);
    }
}
