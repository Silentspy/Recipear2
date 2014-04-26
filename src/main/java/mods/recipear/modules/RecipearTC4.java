package mods.recipear.modules;

import java.util.Iterator;
import java.util.List;

import mods.recipear.BannedRecipes;
import mods.recipear.Recipear;
import mods.recipear.RecipearLogger;
import mods.recipear.RecipearOutput;
import mods.recipear.RecipearUtil;
import mods.recipear.api.IRecipear;
import mods.recipear.api.RecipearEvent;
import net.minecraft.item.ItemStack;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.crafting.ShapedArcaneRecipe;
import thaumcraft.api.crafting.ShapelessArcaneRecipe;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;

@Mod(modid = "Recipear2|TC4", name = "TC4", version = "2.3.1", dependencies="required-after:Recipear2@[2.3,)")
public class RecipearTC4 implements IRecipear {
	
	String prefix = "[" + getName() + "] ";
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) 
	{
		Recipear.events.add(this);
	}

	@Override
	public void trigger(RecipearEvent event) {
		if(Loader.isModLoaded(getModID())) {
			if(event.isOutput()) 
			{
				RemoveRecipes(event, "THAUMCRAFT");
			} 
			else if (BannedRecipes.GetBannedRecipeAmount() > 0)
			{
				RecipearLogger.info(prefix + RemoveRecipes(event, "THAUMCRAFT"));
			}
		} else {
			RecipearLogger.info(prefix + "Could not find " + getModID());
		}
	}

	private String RemoveRecipes(RecipearEvent event, String type) {
		
		int deleted = 0, index = 0;
		ItemStack RECIPE_OUTPUT = null;
		String RECIPE_TYPE;
		
		for (Iterator<Object> itr = ThaumcraftApi.getCraftingRecipes().iterator(); itr.hasNext();) {
			Object recipe = itr.next();
			boolean match = false;
			
			RECIPE_OUTPUT = null;
			RECIPE_TYPE = "None";
			
			if(recipe == null) {
				index++;
				continue;
			}
			
			if(recipe instanceof CrucibleRecipe) 
			{	
				CrucibleRecipe recipe_real = (CrucibleRecipe)recipe;
				RECIPE_OUTPUT = recipe_real.recipeOutput;
				RECIPE_TYPE = "CrucibleRecipe";
			} 
			else if (recipe instanceof InfusionRecipe) 
			{
				InfusionRecipe recipe_real = (InfusionRecipe)recipe;
				if(recipe_real.getRecipeOutput() instanceof ItemStack) {
					RECIPE_OUTPUT = (ItemStack)recipe_real.getRecipeOutput();
				}
				RECIPE_TYPE = "InfusionRecipe";
			}
			else if (recipe instanceof ShapedArcaneRecipe) 
			{
				ShapedArcaneRecipe recipe_real = (ShapedArcaneRecipe)recipe;
				RECIPE_OUTPUT = recipe_real.getRecipeOutput();
				RECIPE_TYPE = "ShapedArcaneRecipe";
			}
			else if (recipe instanceof ShapelessArcaneRecipe) 
			{
				ShapelessArcaneRecipe recipe_real = (ShapelessArcaneRecipe)recipe;
				RECIPE_OUTPUT = recipe_real.getRecipeOutput();
				RECIPE_TYPE = "ShapelessArcaneRecipe";
			}
			
			if(RECIPE_OUTPUT == null) {
				index++;
				continue;
			}

			if(BannedRecipes.Check(RECIPE_OUTPUT.itemID, RECIPE_OUTPUT.getItemDamage(), type) || 
					BannedRecipes.Check(RecipearUtil.getLanguageRegistryEntry(RECIPE_OUTPUT), type)) {
				match = true;
			}
			
			if(event.isOutput() || match) 
			{
				if(event.isOutput()) {
					RecipearOutput.add("i(" + index + ") " + RECIPE_TYPE + " Recipe");
					RecipearOutput.add("OUTPUT[" + RecipearUtil.getFancyItemStackInfo(RECIPE_OUTPUT) + "]");
				} 
				else 
				{
					RecipearLogger.info("i(" + index + ") " + RECIPE_TYPE + " Recipe");
					RecipearLogger.info("OUTPUT[" + RecipearUtil.getFancyItemStackInfo(RECIPE_OUTPUT) + "]");
					deleted++;
					itr.remove();
				}
			}
			
			index++;
		}
			
		return "Removed " + deleted + " " + type + " recipe(s)";
	}

	@Override
	public String[] getTypes() {
		return new String[] {"THAUMCRAFT"};
	}
	
	@Override
	public String getModID() {
		return "Thaumcraft";
	}

	@Override
	public String getFullName() {
		return "Thaumcraft4";
	}
	
	@Override
	public String getName() {
		return "TC4";
	}
}
