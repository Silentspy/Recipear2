package mods.recipear;

import java.util.logging.Logger;

public class RecipearLogger {

	private static Logger logger;

	public static void info(String message) 
	{
		logger.info(message);
	}

	public static void debug(String message) 
	{
		if(Recipear.outputting)
			RecipearCommandLog.AddLogEntry(message);
		else
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

	/**
	 * @return the logger
	 */
	public static Logger getLogger() {
		return logger;
	}

	/**
	 * @param logger the logger to set
	 */
	public static void setLogger(Logger logger) {
		RecipearLogger.logger = logger;
	}
}
