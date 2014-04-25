package mods.recipear.modules;

import java.util.List;
import java.util.Map;

import thaumcraft.api.ThaumcraftApi;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeOutput;
import ic2.api.recipe.Recipes;
import mods.recipear.BannedRecipes;
import mods.recipear.Recipear;
import mods.recipear.RecipearLogger;
import mods.recipear.api.IRecipear;
import mods.recipear.api.RecipearEvent;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;

public class RecipearTC4 implements IRecipear {
	
	String modid = "Thaumcraft";
	String prefix = "[" + modid + "] ";
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) 
	{
		// Recipear.events.add(this);
		// RecipearLogger.info(modid + " module loaded");
	}

	@Override
	public void trigger(RecipearEvent event) {
		if(Loader.isModLoaded(modid)) {
			if(BannedRecipes.GetBannedRecipeAmount() > 0) {
				long startTime = System.currentTimeMillis();
				RecipearLogger.info(prefix + "Starting in " + event.getSide().toString() + " Mode");
				RecipearLogger.info(RemoveRecipes(ThaumcraftApi.getCraftingRecipes(), event)  + " " + modid + " recipe(s)");
				RecipearLogger.info(prefix + "Finished in " + (System.currentTimeMillis() - startTime) + "ms");
			}
		} else {
			RecipearLogger.info(prefix + "Could not find " + modid);
		}
	}

	private String RemoveRecipes(List craftingRecipes, RecipearEvent event) {
		return null;
	}
}
