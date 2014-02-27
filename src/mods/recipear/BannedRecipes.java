package mods.recipear;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import net.minecraft.network.packet.Packet250CustomPayload;

public class BannedRecipes {
	private static ArrayList<BannedRecipe> BannedRecipes = new ArrayList<BannedRecipe>();
	private static ArrayList<String> BannedRecipeTypes = new ArrayList<String>();

	public static ArrayList<String> getBannedRecipeTypes() {
		return BannedRecipeTypes;
	}
	
	public static void setBannedRecipes(ArrayList<BannedRecipe> bannedRecipes) {
		BannedRecipes = bannedRecipes;
	}

	public static ArrayList<BannedRecipe> getBannedRecipes() {
		return BannedRecipes;
	}

	public static boolean Check(String NAME, String TYPE) {

		BannedRecipe bannedrecipe = new BannedRecipe(NAME, "DEFAULT");
		if(BannedRecipes.contains(bannedrecipe)) return true;

		bannedrecipe.type = TYPE;
		if(BannedRecipes.contains(bannedrecipe)) return true;

		return false;
	}

	public static boolean Check(int ID, int METADATA, String TYPE) {
		// this method will always check ITEMID no matter what (kinda obvious)
		// Check if the BannedRecipe have ITEMID:METADATA(set):DEFAULT(not set)
		BannedRecipe bannedrecipe = new BannedRecipe(ID, METADATA, "DEFAULT");
		if(BannedRecipes.contains(bannedrecipe)) return true;
		
		// Check if the BannedRecipe have ITEMID:METADATA(set):TYPE(set)
		bannedrecipe.type = TYPE;
		if(BannedRecipes.contains(bannedrecipe)) return true;
		
		//Check if BannedRecipe have ITEMID:-1(not set):DEFAULT(not set)
		bannedrecipe.type = "DEFAULT";
		bannedrecipe.metadata = -1;
		if(BannedRecipes.contains(bannedrecipe)) return true;

		// Check if BannedRecipe have ITEMID:-1(not set):TYPE(set)
		bannedrecipe.type = TYPE;
		if(BannedRecipes.contains(bannedrecipe)) return true;

		return false;
	}

	public static boolean CheckSpecifically(int ID, int METADATA, String TYPE) {

		BannedRecipe bannedrecipe = new BannedRecipe(ID, METADATA, TYPE);
		if(BannedRecipes.contains(bannedrecipe)) return true;

		return false;
	}

	public static boolean CheckSpecifically(String NAME, String TYPE) {

		BannedRecipe bannedrecipe = new BannedRecipe(NAME, TYPE);
		if(BannedRecipes.contains(bannedrecipe)) return true;

		return false;
	}

	public static void AddBannedRecipe(String name, String type) 
	{
		AddBannedRecipe(new BannedRecipe(name, type));
	}

	public static void AddBannedRecipe(int id, int metadata, String type) 
	{
		AddBannedRecipe(new BannedRecipe(id, metadata, type));
	}

	public static void AddBannedRecipe(BannedRecipe bannedrecipe) 
	{
		BannedRecipes.add(bannedrecipe);
	}

	public static int GetBannedRecipeAmount() 
	{
		return BannedRecipes.size();
	}

	public static int GetBannedRecipeAmountByType(String type) 
	{
		int count = 0;

		for (BannedRecipe bannedrecipe : BannedRecipes)
			if(bannedrecipe.type.equals(type)) count++;

		return count;
	}

	public static void AddBannedRecipeType(String... types) 
	{	
		for(String type : types){
			BannedRecipeTypes.add(type);
		}
	}
	
	public static Packet250CustomPayload getPacket() {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		ObjectOutputStream data = null;
		try {
			data = new ObjectOutputStream(bytes);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
        try {
			data.writeObject(getBannedRecipes());
		} catch (IOException e) {
			e.printStackTrace();
		}
        
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "recipear";
		packet.data = bytes.toByteArray();
		packet.length = bytes.size();
		
		return packet;
	}
}


