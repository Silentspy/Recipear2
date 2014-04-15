package mods.recipear.modules;

import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeOutput;
import ic2.api.recipe.Recipes;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Map;

import mods.recipear.BannedRecipes;
import mods.recipear.Recipear;
import mods.recipear.RecipearLogger;
import mods.recipear.RecipearOutput;
import mods.recipear.RecipearUtil;
import mods.recipear.api.IRecipear;
import mods.recipear.api.RecipearEvent;
import net.minecraft.item.Item;
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
		RecipearLogger.info(modid + " module loaded");
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
				RemoveFromMachines(Recipes.metalformerCutting.getRecipes(), "METALFORMER_CUTTING", event);
				RemoveFromMachines(Recipes.metalformerExtruding.getRecipes(), "METALFORMER_EXTRUDING", event);
				RemoveFromMachines(Recipes.metalformerRolling.getRecipes(), "METALFORMER_ROLLING", event);
				ScrapBox(Recipes.scrapboxDrops.getDrops(), "SCRAPBOX", event);
			} 
			else if(BannedRecipes.GetBannedRecipeAmount() > 0) 
			{
				long startTime = System.currentTimeMillis();
				RecipearLogger.info(prefix + "Starting in " + event.getSide().toString() + " Mode");
				RecipearLogger.info(RemoveFromMachines(Recipes.centrifuge.getRecipes(), "CENTRIFUGE", event));
				RecipearLogger.info(RemoveFromMachines(Recipes.compressor.getRecipes(), "COMPRESSOR", event));
				RecipearLogger.info(RemoveFromMachines(Recipes.extractor.getRecipes(), "EXTRACTOR", event));
				RecipearLogger.info(RemoveFromMachines(Recipes.macerator.getRecipes(), "MACERATOR", event));
				RecipearLogger.info(RemoveFromMachines(Recipes.oreWashing.getRecipes(), "OREWASHING", event));
				RecipearLogger.info(RemoveFromMachines(Recipes.metalformerCutting.getRecipes(), "METALFORMER_CUTTING", event));
				RecipearLogger.info(RemoveFromMachines(Recipes.metalformerExtruding.getRecipes(), "METALFORMER_EXTRUDING", event));
				RecipearLogger.info(RemoveFromMachines(Recipes.metalformerRolling.getRecipes(), "METALFORMER_ROLLING", event));
				RecipearLogger.info(ScrapBox(Recipes.scrapboxDrops.getDrops(), "SCRAPBOX", event));
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
		BannedRecipes.AddBannedRecipeType("CENTRIFUGE", "COMPRESSOR", "EXTRACTOR", "MACERATOR", "OREWASHING", "METALFORMER_CUTTING", "METALFORMER_EXTRUDING", "METALFORMER_ROLLING", "SCRAPBOX");
	}
	
	public String ScrapBox(Map<ItemStack, Float> drops, String machine, RecipearEvent event) {
		DecimalFormat liquidAmountFormat = new DecimalFormat("0.##%");
		
		if(event.isOutput()) {
			RecipearOutput.add("-- " + machine + " --");
		} else {
			RecipearLogger.info(prefix + "Scanning through " + drops.size() + " recipe(s) for " + machine);
		}
		
		int countRemoved = 0, index = 0;
		
		for (Iterator itr = drops.entrySet().iterator(); itr.hasNext();) {
			try {
			Map.Entry entry = (Map.Entry) itr.next();
			ItemStack itemstack = (ItemStack) entry.getKey();
			Float chance = (Float) entry.getValue();
			
			if((itemstack == null) || (chance == null)) { 
				index++;
				continue;
			}
			
			boolean found = false;
			String recipe_output = RecipearUtil.getFancyItemStackInfo(itemstack) + ", CHANCE: " + liquidAmountFormat.format(chance);
			
			if(BannedRecipes.Check(itemstack.itemID, itemstack.getItemDamage(), machine) || 
					BannedRecipes.Check(RecipearUtil.getLanguageRegistryEntry(itemstack), machine)) {
				found = true;
			}
			
			if(event.isOutput()) {
				RecipearOutput.add("i(" + index + ") OUTPUT[" + recipe_output + "]");
			} else if (found) {
				RecipearOutput.add("i(" + index + ") OUTPUT[" + recipe_output + "]");
			}
			
			if(found) {
				itr.remove();
				countRemoved++;
			}
			
			index++;
			
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
		return prefix + "Removed " + countRemoved + " " + machine + " recipe(s)";
	}

	public String RemoveFromMachines(Map recipes, String machine, RecipearEvent event) 
	{
		if(event.isOutput()) {
			RecipearOutput.add("-- " + machine + " --");
		} else {
			RecipearLogger.info(prefix + "Scanning through " + recipes.size() + " recipe(s) for " + machine);
		}
		
		int countRemoved = 0, index = 0;
		
		for (Iterator itr = recipes.entrySet().iterator(); itr.hasNext();) {
			try {
			Map.Entry entry = (Map.Entry) itr.next();
			IRecipeInput recipe_input = (IRecipeInput) entry.getKey();
			RecipeOutput recipe_output = (RecipeOutput) entry.getValue();
			
			if((recipe_input == null) || (recipe_output == null)) { 
				index++;
				continue;
			}

			String recipe_inputs = "N/A";
			
			for(ItemStack itemstack : recipe_input.getInputs()) {
				
				if(itemstack == null) {
					continue;
				}
				
				String temp = RecipearUtil.getLanguageRegistryEntry(itemstack) + "@" + itemstack.itemID + ":" + itemstack.getItemDamage() + " x " + recipe_input.getAmount(); 	
	
				if(recipe_inputs.equals("N/A")) {
					recipe_inputs = temp;
				} else {
					recipe_inputs += ", " + temp;
				}
			}
			
			boolean found = false;
			String recipe_outputs = "N/A";
			
			for(Iterator<ItemStack> items_itr  = recipe_output.items.iterator(); items_itr.hasNext();) {
				ItemStack itemstack = items_itr.next();

				if (itemstack == null) {
					continue;
				}
					
				String temp = RecipearUtil.getFancyItemStackInfo(itemstack);
				
				if(recipe_outputs.equals("N/A")) {
					recipe_outputs = temp;
				} else {
					recipe_outputs += ", " + temp;
				}
				
				if(BannedRecipes.Check(itemstack.itemID, itemstack.getItemDamage(), machine) || 
						BannedRecipes.Check(RecipearUtil.getLanguageRegistryEntry(itemstack), machine)) {
					found = true;
				}
			}
			
			if(event.isOutput()) {
				RecipearOutput.add("i(" + index + ") " + machine + " Recipe");
				RecipearOutput.add("INPUT[" + recipe_inputs + "]");
				RecipearOutput.add("OUTPUT[" + recipe_outputs + "]");
			} else if (found) {
				RecipearLogger.info("i(" + index + ") " + machine + " Recipe");
				RecipearLogger.info("INPUT[" + recipe_inputs + "]");
				RecipearLogger.info("OUTPUT[" + recipe_outputs + "]");
			}
			
			if(found) {
				itr.remove();
				countRemoved++;
			}
			
			index++;
			
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
		return prefix + "Removed " + countRemoved + " " + machine + " recipe(s)";
	}
}
