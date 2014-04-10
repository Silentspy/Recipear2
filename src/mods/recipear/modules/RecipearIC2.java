package mods.recipear.modules;

import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeOutput;
import ic2.api.recipe.Recipes;

import java.util.Iterator;
import java.util.Map;

import mods.recipear.BannedRecipes;
import mods.recipear.Recipear;
import mods.recipear.RecipearLogger;
import mods.recipear.RecipearOutput;
import mods.recipear.RecipearUtil;
import mods.recipear.api.IRecipear;
import mods.recipear.api.RecipearEvent;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "Recipear2|IC2", name = "IC2", version = "1.0", dependencies="required-after:Recipear2@[2.1,)")
public class RecipearIC2 implements IRecipear{
	
	String modid = "IC2";
	String prefix = "[" + modid + "] ";
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) 
	{
		Recipear.events.add(this);
		RecipearLogger.info(modid + "module loaded");
	}
	
	public void trigger(RecipearEvent event) {
		
		if(Loader.isModLoaded(modid)) 
		{
			if(event.isOutput()) 
			{
				RemoveFromMachines(Recipes.centrifuge.getRecipes(), "CENTRIFUGE", event);
				RemoveFromMachines(Recipes.compressor.getRecipes(), "COMPRESSOR", event);
				RemoveFromMachines(Recipes.extractor.getRecipes(), "EXTRACTOR", event);
				RemoveFromMachines(Recipes.macerator.getRecipes(), "MACERATOR", event);
				RemoveFromMachines(Recipes.oreWashing.getRecipes(), "OREWASHING", event);
				RemoveFromMachines(Recipes.oreWashing.getRecipes(), "", event);
			} 
			else if(BannedRecipes.GetBannedRecipeAmount() > 0) 
			{
				long startTime = System.currentTimeMillis();
				RecipearLogger.info(prefix + "Starting in " + event.getSide().toString() + " Mode");
				RecipearLogger.info(prefix + "Removed " + RemoveFromMachines(Recipes.centrifuge.getRecipes(), "CENTRIFUGE", event)  + " CENTRIFUGE recipe(s)");
				RecipearLogger.info(prefix + "Removed " + RemoveFromMachines(Recipes.compressor.getRecipes(), "COMPRESSOR", event)  + " COMPRESSOR recipe(s)");
				RecipearLogger.info(prefix + "Removed " + RemoveFromMachines(Recipes.extractor.getRecipes(), "EXTRACTOR", event)  + " EXTRACTOR recipe(s)");
				RecipearLogger.info(prefix + "Removed " + RemoveFromMachines(Recipes.macerator.getRecipes(), "MACERATOR", event)  + " MACERATOR recipe(s)");
				RecipearLogger.info(prefix + "Removed " + RemoveFromMachines(Recipes.oreWashing.getRecipes(), "OREWASHING", event)  + " OREWASHING recipe(s)");
				RecipearLogger.info(prefix + "Finished in " + (System.currentTimeMillis() - startTime) + "ms");
			}
		}
		else 
		{
			RecipearLogger.info(prefix + "Could not find " + modid);
		}
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) 
	{
		BannedRecipes.AddBannedRecipeType("CENTRIFUGE", "COMPRESSOR", "EXTRACTOR", "MACERATOR", "OREWASHING", "SCRAPBOX");
	}

	public int RemoveFromMachines(Map recipes, String machine, RecipearEvent event) 
	{
		if(event.isOutput()) {
			RecipearOutput.add("-- " + machine + " --");
		} else {
			RecipearLogger.info(prefix + "Scanning through " + recipes.size() + " recipe(s) for " + machine);
		}
		
		int countRemoved = 0, index = 0, NBTTAGSCOUNT = 0, ITEMID, METADATA;
		String DISPLAYNAME = "Unknown";
		RecipeOutput RECIPE_OUTPUT_RAW = null, RECIPE_OUTPUT_NEW = null;
		ItemStack RECIPE_OUTPUT = null;
		
		for (Iterator itr = recipes.entrySet().iterator(); itr.hasNext();) {
			try {
			Map.Entry entry = (Map.Entry) itr.next();
			Object RECIPE_INPUT_RAW = (Object) entry.getKey();
			RECIPE_OUTPUT_RAW = (RecipeOutput) entry.getValue();
			
			if(RECIPE_INPUT_RAW == null) { 
				continue;
			}
			
			if(RECIPE_OUTPUT_RAW == null) {
				continue;
			}

			String RECIPE_INPUT_DISPLAYNAME = "N/A";

			if(RECIPE_INPUT_RAW instanceof IRecipeInput) {
				IRecipeInput recipe = (IRecipeInput)RECIPE_INPUT_RAW;
				
				for(ItemStack itemstack : recipe.getInputs()) {
					
					try {
					
					if(itemstack == null) {
						continue;
					}
					
					if(itemstack.getUnlocalizedName() == null) {
						continue;
					}
					
					String temp = "[" + RecipearUtil.getLanguageRegistryEntry(itemstack) + ":" + itemstack.getItemDamage() + ", AMOUNT: " + recipe.getAmount() + "]"; 	

					RECIPE_INPUT_DISPLAYNAME = (RECIPE_INPUT_DISPLAYNAME.equals("N/A")) ? temp : ", " + temp;
					
					} catch (Exception ex) {
						RecipearLogger.warning(prefix + "Failed to fetch name for " + itemstack.itemID + ":" + itemstack.getItemDamage());
					}
				}
			}

			if(!event.isOutput()) {
				RecipearLogger.debug(prefix + "INPUT: " + RECIPE_INPUT_DISPLAYNAME);
			}
			
			boolean found = false;

			for(Iterator<ItemStack> items_itr  = RECIPE_OUTPUT_RAW.items.iterator(); items_itr.hasNext();) {
				RECIPE_OUTPUT = items_itr.next();

				if (RECIPE_OUTPUT == null) {
					continue;
				}

				ITEMID = RECIPE_OUTPUT.itemID;
				METADATA = RECIPE_OUTPUT.getItemDamage();
				if(RECIPE_OUTPUT.getTagCompound() != null) {
					NBTTAGSCOUNT = RECIPE_OUTPUT.getTagCompound().getTags().size();
				}
				
				try {
					DISPLAYNAME = RecipearUtil.getLanguageRegistryEntry(RECIPE_OUTPUT);
				} catch (Exception ex) {
					RecipearLogger.warning(prefix + "Failed to fetch name for " + ITEMID + ":" + METADATA);
				}
				
				if(event.isOutput()) {
					RecipearOutput.add(prefix + DISPLAYNAME + ", ID: " + ITEMID + ", METADATA: " + METADATA + ", NBTCOUNT: " + NBTTAGSCOUNT + ", INDEX: " + index);
				} else {
					if(BannedRecipes.Check(ITEMID, METADATA, machine) || 
							BannedRecipes.Check(DISPLAYNAME.replaceAll("\\s+","").toLowerCase(), machine)) {
						RecipearLogger.info(prefix + "Removing entry(" + RECIPE_INPUT_DISPLAYNAME + ") to craft: " + DISPLAYNAME + ", ID: " + ITEMID + ", METADATA: " + METADATA);
						found = true;
						countRemoved++;
					}
				}
			}
			
			index++;

			if(found) {
				itr.remove();
			}
			
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return countRemoved;
	}
}
