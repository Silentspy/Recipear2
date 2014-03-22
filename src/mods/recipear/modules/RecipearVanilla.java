package mods.recipear.modules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mods.recipear.BannedRecipes;
import mods.recipear.RecipearConfig;
import mods.recipear.RecipearLogger;
import mods.recipear.RecipearOutput;
import mods.recipear.RecipearUtil;
import mods.recipear.api.IRecipear;
import mods.recipear.api.RecipearEvent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;

public class RecipearVanilla implements IRecipear {
	
	public void trigger(RecipearEvent event) 
	{
		if(event.isOutput()) 
		{
				RemoveRecipes(event);
				RemoveFurnaceRecipes(event);
		} 
		else if (BannedRecipes.GetBannedRecipeAmount() > 0)
		{
			long startTime = System.currentTimeMillis();
			
			RecipearLogger.info("Starting in " + event.getSide().toString() + " Mode");
			RecipearLogger.info("Removed " + RemoveRecipes(event) + " Crafting recipe(s)");
			RecipearLogger.info("Removed " + RemoveFurnaceRecipes(event) + " Furnace recipe(s)");
			RecipearLogger.info("Finished in " + (System.currentTimeMillis() - startTime) + "ms");
		}
	}

	private int RemoveRecipes(RecipearEvent event) 
	{
		List recipelist = CraftingManager.getInstance().getRecipeList();
		
		if(event.isOutput()) {
			RecipearOutput.add("-- CRAFTING --");
		} else {
			RecipearLogger.info("Scanning " + recipelist.size() + " Crafting recipe(s)");
		}
		
		int deleted = 0, index = 0, NBTTAGSCOUNT = 0, ITEMID, METADATA;
		String DISPLAYNAME = "";
		ItemStack RECIPE_OUTPUT;

		for (Iterator<Object> itr = recipelist.iterator(); itr.hasNext();) {
			Object recipe = itr.next();

			if (!(recipe instanceof IRecipe)) continue;

			IRecipe iRecipe = (IRecipe) recipe;
			RECIPE_OUTPUT = iRecipe.getRecipeOutput();

			if (RECIPE_OUTPUT == null) {
				continue;
			}

			ITEMID = RECIPE_OUTPUT.itemID;
			METADATA = RECIPE_OUTPUT.getItemDamage();
			if(RECIPE_OUTPUT.getTagCompound() != null) {
				NBTTAGSCOUNT = RECIPE_OUTPUT.getTagCompound().getTags().size();
			}
				
			DISPLAYNAME = RecipearUtil.getLanguageRegistryEntry(RECIPE_OUTPUT);

			if(event.isOutput()) {
				RecipearOutput.add(DISPLAYNAME + ", ID: " + ITEMID + ", METADATA: " + METADATA + ", NBTCOUNT: " + NBTTAGSCOUNT + ", INDEX: " + index);
			} else {
				if(BannedRecipes.Check(ITEMID, METADATA, "CRAFTING") || BannedRecipes.Check(DISPLAYNAME.replaceAll("\\s+","").toLowerCase(), "CRAFTING")) {
					if (!event.isServer() && !RecipearConfig.removeclient) {
						RecipearLogger.info("Placeholding: " + DISPLAYNAME + ", ID: " + ITEMID + ", METADATA: " + METADATA);
						RecipearUtil.setCraftingRecipeOutput(iRecipe, RECIPE_OUTPUT);
						deleted++;
					} else {
						RecipearLogger.info("Removing: " + DISPLAYNAME + ", ID: " + ITEMID + ", METADATA: " + METADATA);
						deleted++;
						itr.remove();
					}
				}
			}
			
			index++;
		}

		return deleted;
	}


	private int RemoveFurnaceRecipes(RecipearEvent event) 
	{
		if(event.isOutput()) {
			RecipearOutput.add("-- FURNACE --");
		} else {
			RecipearLogger.info("Scanning " + (FurnaceRecipes.smelting().getMetaSmeltingList().size() + FurnaceRecipes.smelting().getSmeltingList().size()) + " Furnace Recipe(s)");
		}
		
		int deleted = 0, index = 0, NBTTAGSCOUNT = 0, ITEMID, METADATA;
		String DISPLAYNAME = "";
		ItemStack RECIPE_OUTPUT;

		for (Iterator itr = FurnaceRecipes.smelting().getMetaSmeltingList().values().iterator(); itr.hasNext();) 
		{
			RECIPE_OUTPUT = (ItemStack) itr.next();

			if (RECIPE_OUTPUT == null) {
				continue;
			}

			NBTTAGSCOUNT = 0;
			ITEMID = RECIPE_OUTPUT.itemID;
			METADATA = RECIPE_OUTPUT.getItemDamage();
			if(RECIPE_OUTPUT.getTagCompound() != null) {
				NBTTAGSCOUNT = RECIPE_OUTPUT.getTagCompound().getTags().size();
			}
				
			DISPLAYNAME = RecipearUtil.getLanguageRegistryEntry(RECIPE_OUTPUT);
			
			if(event.isOutput()) {
				RecipearOutput.add(DISPLAYNAME + ", ID: " + ITEMID + ", METADATA: " + METADATA + ", NBTCOUNT: " + NBTTAGSCOUNT + ", INDEX: " + index);
			} else {
				if (BannedRecipes.Check(ITEMID, METADATA, "FURNACE") || BannedRecipes.Check(DISPLAYNAME.replaceAll("\\s+","").toLowerCase(), "FURNACE")) {
					RecipearLogger.info("Removing: " + DISPLAYNAME + ", ID: " + ITEMID + ", METADATA: " + METADATA);
					itr.remove();
					deleted++;
				}
			}
			
			index++;
		}

		for (Iterator itr = FurnaceRecipes.smelting().getSmeltingList().values().iterator(); itr.hasNext();) {
			RECIPE_OUTPUT = (ItemStack) itr.next();

			if (RECIPE_OUTPUT == null) {
				continue;
			}

			NBTTAGSCOUNT = 0;
			ITEMID = RECIPE_OUTPUT.itemID;
			METADATA = RECIPE_OUTPUT.getItemDamage();
			if(RECIPE_OUTPUT.getTagCompound() != null) {
				NBTTAGSCOUNT = RECIPE_OUTPUT.getTagCompound().getTags().size();
			}
				
			DISPLAYNAME = RecipearUtil.getLanguageRegistryEntry(RECIPE_OUTPUT);

			if(event.isOutput()) {
				RecipearOutput.add(DISPLAYNAME + ", ID: " + ITEMID + ", METADATA: " + METADATA + ", NBTCOUNT: " + NBTTAGSCOUNT + ", INDEX: " + index);
			} else {
				if (BannedRecipes.Check(ITEMID, METADATA, "FURNACE") || BannedRecipes.Check(DISPLAYNAME.replaceAll("\\s+","").toLowerCase(), "FURNACE")) {
					RecipearLogger.info("Removing: " + DISPLAYNAME + ", ID: " + ITEMID + ", METADATA: " + METADATA);
					itr.remove();
					deleted++;
				}
			}
			
			index++;
		}
		
		return deleted;
	}
}
