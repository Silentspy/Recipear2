package mods.recipear.modules;

import ic2.core.AdvRecipe;
import ic2.core.AdvShapelessRecipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cpw.mods.fml.common.Loader;
import mods.recipear.BannedRecipes;
import mods.recipear.RecipearConfig;
import mods.recipear.RecipearLogger;
import mods.recipear.RecipearOutput;
import mods.recipear.RecipearUtil;
import mods.recipear.api.IRecipear;
import mods.recipear.api.RecipearEvent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class RecipearVanilla implements IRecipear {
	
	public void trigger(RecipearEvent event) 
	{
		if(event.isOutput()) 
		{
				RemoveRecipes(event, "CRAFTING");
				RemoveFurnaceRecipes(event, "FURNACE");
		} 
		else if (BannedRecipes.GetBannedRecipeAmount() > 0)
		{
			long startTime = System.currentTimeMillis();
			
			RecipearLogger.info("Starting in " + event.getSide().toString() + " Mode");
			RecipearLogger.info(RemoveRecipes(event, "CRAFTING"));
			RecipearLogger.info(RemoveFurnaceRecipes(event, "FURNACE"));
			RecipearLogger.info("Finished in " + (System.currentTimeMillis() - startTime) + "ms");
		}
	}

	private String RemoveRecipes(RecipearEvent event, String type) 
	{
		List recipelist = CraftingManager.getInstance().getRecipeList();
		
		if(event.isOutput()) {
			RecipearOutput.add("-- " + type + " --");
		} else {
			RecipearLogger.info("Scanning " + recipelist.size() + " " + type + " recipe(s)");
		}
		
		int deleted = 0, index = 0;
		ItemStack RECIPE_OUTPUT;
		
		for (Iterator<Object> itr = recipelist.iterator(); itr.hasNext();) {
			Object recipe = itr.next();
			
			String recipe_inputs = "N/A";
			String recipe_type = "Unknown";
			boolean match = false;
			
			if (!(recipe instanceof IRecipe)) {
				index++;
				continue;
			}
			
			IRecipe iRecipe = (IRecipe) recipe;
			RECIPE_OUTPUT = iRecipe.getRecipeOutput();

			if (RECIPE_OUTPUT == null) {
				index++;
				continue;
			}
			
			if(BannedRecipes.Check(RECIPE_OUTPUT.itemID, RECIPE_OUTPUT.getItemDamage(), type) || 
					BannedRecipes.Check(RecipearUtil.getLanguageRegistryEntry(RECIPE_OUTPUT), type)) {
				match = true;
			}
			
			if(event.isOutput() || match) 
			{
				if (recipe instanceof ShapedRecipes) 
				{
					recipe_type = "Shaped";
					
					ShapedRecipes shapedrecipe = (ShapedRecipes) recipe;
					for(ItemStack item : shapedrecipe.recipeItems) {
						
						String temp = "X"; 	
						
						if(item != null) {
							temp = RecipearUtil.getFancyItemStackInfo(item);
						}
						
						if(recipe_inputs.equals("N/A")) {
							recipe_inputs = temp;
						} else {
							recipe_inputs += ", " + temp;
						}
					}
				}
				else if (recipe instanceof ShapelessRecipes)
				{
					recipe_type = "Shapeless";
					
					ShapelessRecipes recipe_real = (ShapelessRecipes) recipe;
					for(ItemStack item : (List<ItemStack>)recipe_real.recipeItems) {
						String temp = "X"; 	
						
						if(item != null) {
							temp = RecipearUtil.getFancyItemStackInfo(item);
						}
						
						if(recipe_inputs.equals("N/A")) {
							recipe_inputs = temp;
						} else {
							recipe_inputs += ", " + temp;
						}
					}
				}
				else if (recipe instanceof ShapedOreRecipe)
				{
					recipe_type = "ShapedOre";
					
					ShapedOreRecipe recipe_real = (ShapedOreRecipe) recipe;
					for(Object object : recipe_real.getInput()) {
						
						String temp = "X"; 	
						
						if(object instanceof ItemStack) {
							if(object != null) {
								temp = RecipearUtil.getFancyItemStackInfo((ItemStack)object);
							}
						} else if (object instanceof ArrayList<?>) {
							if(((ArrayList<ItemStack>)object).size() > 0) {
								temp = OreDictionary.getOreName(OreDictionary.getOreID(((ArrayList<ItemStack>)object).get(0)));
							}
						}
						
						if(recipe_inputs.equals("N/A")) {
							recipe_inputs = temp;
						} else {
							recipe_inputs += ", " + temp;
						}
					}
				}
				else if (recipe instanceof ShapelessOreRecipe)
				{
					recipe_type = "ShapelessOre";
					
					ShapelessOreRecipe recipe_real = (ShapelessOreRecipe) recipe;
					for(Object object : recipe_real.getInput()) {
						
						String temp = "X"; 	
						
						if(object instanceof ItemStack) {
							if(object != null) {
								temp = RecipearUtil.getFancyItemStackInfo((ItemStack)object);
							}
						} else if (object instanceof ArrayList<?>) {
							if(((ArrayList<ItemStack>)object).size() > 0) {
								temp = OreDictionary.getOreName(OreDictionary.getOreID(((ArrayList<ItemStack>)object).get(0)));
							}
						}
						
						if(recipe_inputs.equals("N/A")) {
							recipe_inputs = temp;
						} else {
							recipe_inputs += ", " + temp;
						}
					}
				} else if (Loader.isModLoaded("IC2") && (recipe instanceof AdvRecipe)) {
						recipe_type = "AdvRecipe";
						
						AdvRecipe recipe_real = (AdvRecipe) recipe;
						for(Object object : recipe_real.input) {
							
							String temp = "X"; 	
							
							if(object instanceof ItemStack) {
								if(object != null) {
									temp = RecipearUtil.getFancyItemStackInfo((ItemStack)object);
								}
							} else if (object instanceof ArrayList<?>) {
								if(((ArrayList<ItemStack>)object).size() > 0) {
									temp = RecipearUtil.getFancyItemStackInfo(((ArrayList<ItemStack>)object).get(0));
								}
							} else if (object instanceof String) {
								temp = (String)object;
							}
							
							if(recipe_inputs.equals("N/A")) {
								recipe_inputs = temp;
							} else {
								recipe_inputs += ", " + temp;
							}
						}
				} else if (Loader.isModLoaded("IC2") && (recipe instanceof AdvRecipe)) {
					recipe_type = "AdvShapeless";
					
					AdvShapelessRecipe recipe_real = (AdvShapelessRecipe) recipe;
					for(Object object : recipe_real.input) {
						
						String temp = "X"; 	
						
						if(object instanceof ItemStack) {
							if(object != null) {
								temp = RecipearUtil.getFancyItemStackInfo((ItemStack)object);
							}
						} else if (object instanceof ArrayList<?>) {
							if(((ArrayList<ItemStack>)object).size() > 0) {
								temp = RecipearUtil.getFancyItemStackInfo(((ArrayList<ItemStack>)object).get(0));
							}
						} else if (object instanceof String) {
							temp = (String)object;
						}
						
						if(recipe_inputs.equals("N/A")) {
							recipe_inputs = temp;
						} else {
							recipe_inputs += ", " + temp;
						}
					}
				}
				
				if(event.isOutput()) {
					RecipearOutput.add("i(" + index + ") " + recipe_type + " Recipe");
					RecipearOutput.add("INPUT[" + recipe_inputs + "]");
					RecipearOutput.add("OUTPUT[" + RecipearUtil.getFancyItemStackInfo(RECIPE_OUTPUT) + "]");
				} 
				else 
				{
					RecipearLogger.info("i(" + index + ") " + recipe_type + " Recipe");
					RecipearLogger.info("INPUT[" + recipe_inputs + "]");
					RecipearLogger.info("OUTPUT[" + RecipearUtil.getFancyItemStackInfo(RECIPE_OUTPUT) + "]");
					deleted++;
					itr.remove();
				}
			}
			
			index++;
		}

		return "Removed " + deleted + " " + type + " recipe(s)";
	}


	private String RemoveFurnaceRecipes(RecipearEvent event, String type) 
	{
		if(event.isOutput()) {
			RecipearOutput.add("-- " + type + " --");
		} else {
			RecipearLogger.info("Scanning " + (FurnaceRecipes.smelting().getMetaSmeltingList().size() + FurnaceRecipes.smelting().getSmeltingList().size()) + " " + type + " recipe(s)");
		}
		
		int deleted = 0, index = 0;
		ItemStack RECIPE_OUTPUT;

		for (Iterator itr = (FurnaceRecipes.smelting().getMetaSmeltingList()).entrySet().iterator(); itr.hasNext();) 
		{
			Map.Entry pairs = (Map.Entry)itr.next();
			List<Integer> RECIPE_INPUT_LIST = (List<Integer>) pairs.getKey();
			RECIPE_OUTPUT = (ItemStack) pairs.getValue();
			ItemStack RECIPE_INPUT = new ItemStack(Item.itemsList[RECIPE_INPUT_LIST.get(0)], 0, RECIPE_INPUT_LIST.get(1));
			
			if (RECIPE_OUTPUT == null) {
				index++;
				continue;
			}
			
			if(event.isOutput()) {
				RecipearOutput.add("i(" + index + ") MetaSmelting Recipe");
				RecipearOutput.add("INPUT[" + RecipearUtil.getFancyItemStackInfo(RECIPE_INPUT) +", EXPERIENCE(" + FurnaceRecipes.smelting().getExperience(RECIPE_OUTPUT) + ")]");
				RecipearOutput.add("OUTPUT[" + RecipearUtil.getFancyItemStackInfo(RECIPE_OUTPUT) + "]");
			} else {
				if (BannedRecipes.Check(RECIPE_OUTPUT.itemID, RECIPE_OUTPUT.getItemDamage(), type) || BannedRecipes.Check(RecipearUtil.getLanguageRegistryEntry(RECIPE_OUTPUT), type)) {
					RecipearLogger.info("i(" + index + ") MetaSmelting Recipe");
					RecipearLogger.info("INPUT[" + RecipearUtil.getFancyItemStackInfo(RECIPE_INPUT) +", EXPERIENCE(" + FurnaceRecipes.smelting().getExperience(RECIPE_OUTPUT) + ")]");
					RecipearLogger.info("OUTPUT[" + RecipearUtil.getFancyItemStackInfo(RECIPE_OUTPUT) + "]");
					itr.remove();
					deleted++;
				}
			}
			
			index++;
		}

		for (Iterator itr = FurnaceRecipes.smelting().getSmeltingList().entrySet().iterator(); itr.hasNext();) {
			Map.Entry pairs = (Map.Entry)itr.next();
			RECIPE_OUTPUT = (ItemStack) pairs.getValue();
			ItemStack RECIPE_INPUT = new ItemStack(Item.itemsList[(Integer)pairs.getKey()]);
			
			if (RECIPE_OUTPUT == null) {
				index++;
				continue;
			}

			if(event.isOutput()) {
				RecipearOutput.add("i(" + index + ") Smelting Recipe");
				RecipearOutput.add("INPUT[" + RecipearUtil.getFancyItemStackInfo(RECIPE_INPUT) +", EXPERIENCE(" + FurnaceRecipes.smelting().getExperience(RECIPE_OUTPUT) + ")]");
				RecipearOutput.add("OUTPUT[" + RecipearUtil.getFancyItemStackInfo(RECIPE_OUTPUT) + "]");
			} else {
				if (BannedRecipes.Check(RECIPE_OUTPUT.itemID, RECIPE_OUTPUT.getItemDamage(), type) || BannedRecipes.Check(RecipearUtil.getLanguageRegistryEntry(RECIPE_OUTPUT), type)) {
					RecipearLogger.info("i(" + index + ") Smelting Recipe");
					RecipearLogger.info("INPUT[" + RecipearUtil.getFancyItemStackInfo(RECIPE_INPUT) +", EXPERIENCE(" + FurnaceRecipes.smelting().getExperience(RECIPE_OUTPUT) + ")]");
					RecipearLogger.info("OUTPUT[" + RecipearUtil.getFancyItemStackInfo(RECIPE_OUTPUT) + "]");
					itr.remove();
					deleted++;
				}
			}
			
			index++;
		}
		
		return "Removed " + deleted + " " + type + " recipe(s)";
	}
}
