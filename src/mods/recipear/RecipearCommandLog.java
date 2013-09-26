package mods.recipear;

import java.util.ArrayList;

public class RecipearCommandLog {
	private static ArrayList<String> log = new ArrayList<String>();

	public static ArrayList<String> getLog() {
		return log;
	}

	public static void AddLogEntry(String entry) {
		log.add(entry);
	}
}
