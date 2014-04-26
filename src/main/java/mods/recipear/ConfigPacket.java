package mods.recipear;

import java.io.Serializable;
import java.util.ArrayList;

public class ConfigPacket implements Serializable {
	boolean debug, removeclient;
	String placeholderDescription;
	ArrayList<BannedRecipe> recipes;

	public ConfigPacket(boolean debug, boolean removeclient, String placeholderdescription,
			ArrayList<BannedRecipe> recipes) {
		
		this.debug = debug;
		this.removeclient = removeclient;
		this.placeholderDescription = placeholderdescription;
		this.recipes = recipes;
	}
}
