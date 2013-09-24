package mods.recipear;

import java.util.ArrayList;

public class BannedRecipes {
	private static ArrayList<BannedRecipe> BannedRecipes = new ArrayList<BannedRecipe>();
	
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
		
		BannedRecipe bannedrecipe = new BannedRecipe(ID, METADATA, "DEFAULT");
		if(BannedRecipes.contains(bannedrecipe)) return true;
		
		bannedrecipe.type = TYPE;
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
}


