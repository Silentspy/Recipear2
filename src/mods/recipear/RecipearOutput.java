package mods.recipear;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class RecipearOutput {

	static String output = "";
	
	public static void add(String msg) {
		output += msg + "\n";
	}
	
	public static void clear() {
		output = "";
	}
	
	public static boolean save() {
		try {
			File file = new File(Recipear.mcDataDir, "Recipear-output.log");
			PrintWriter writer = new PrintWriter(file, "UTF-8");
			writer.write(output);
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
