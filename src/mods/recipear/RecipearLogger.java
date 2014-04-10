package mods.recipear;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import mods.recipear.api.RecipearEvent;

public class RecipearLogger {

	private static Logger logger;

	public static void info(String message) 
	{
		logger.info(message);
	}

	public static void debug(String message) 
	{
		if(RecipearConfig.debug) {
			logger.info("[DEBUG] " + message);
		}
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
	public static void setLogger(Logger logger, String path) {
		logger.setUseParentHandlers(false);
		Handler fh = null;
		try {
			fh = new FileHandler(path, 0, 3);
			fh.setFormatter(new RecipearFormatter());
			logger.addHandler(fh);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		RecipearLogger.logger = logger;
	}
}
