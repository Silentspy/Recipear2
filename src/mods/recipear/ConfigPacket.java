package mods.recipear;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import net.minecraft.network.packet.Packet250CustomPayload;

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