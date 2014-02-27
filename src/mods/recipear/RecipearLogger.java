package mods.recipear;

import java.util.logging.Logger;

import mods.recipear.api.RecipearEvent;
import net.minecraft.logging.ILogAgent;

public class RecipearLogger {

	private static ILogAgent logger;

	public static void info(String message) 
	{
		logger.logInfo(message);
	}

	public static void debug(String message, RecipearEvent event) 
	{
		if(RecipearConfig.debug) {
			logger.logInfo("[DEBUG] " + message);
		} else if(!event.isModify()) {
			logger.logInfo("[OUTPUT] " + message);
		}
	}

	public static void severe(String message)
	{
		logger.logSevere(message);
	}

	public static void warning(String message)
	{
		logger.logWarning(message);
	}

	/**
	 * @return the logger
	 */
	public static ILogAgent getLogger() {
		return logger;
	}

	/**
	 * @param logger the logger to set
	 */
	public static void setLogger(ILogAgent logger) {
		RecipearLogger.logger = logger;
	}
}
