package mods.recipear;

import java.util.Iterator;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.EnumChatFormatting;
import cpw.mods.fml.relauncher.Side;

/* import thaumcraft.api.crafting.ShapedArcaneCraftingRecipes;
 import thaumcraft.api.crafting.ShapedInfusionCraftingRecipes;
 import thaumcraft.api.crafting.ShapelessArcaneCraftingRecipes;
 import thaumcraft.api.crafting.ShapelessInfusionCraftingRecipes;
 */

public class RecipeRemover {

	/*public int RemoveRecipesIC2() {
		int itemsremoved = 0;

		if (mod_recipear.IC2Detected) {
			Map l = null;

			int recipe_count = Recipes.macerator.getRecipes()
					.size();
			recipe_count += Recipes.compressor.getRecipes()
					.size();
			recipe_count += Recipes.extractor.getRecipes()
					.size();

			RecipearLogger.info("Checking " + recipe_count + " IC2 recipe(s)");

			String ic2_placeholder_text = "";

			for (int i = 0; i < 3; i++) {
				if (i == 0) {
					l = Recipes.macerator.getRecipes();
					ic2_placeholder_text = " for Macerator";
				} else if (i == 1) {
					l = Recipes.compressor.getRecipes();
					ic2_placeholder_text = " for Compressor";
				} else if (i == 2) {
					l = Recipes.extractor.getRecipes();
					ic2_placeholder_text = " for Extractor";
				}

				for (Iterator itr = l.entrySet().iterator(); itr.hasNext();) {
					Map.Entry entry = (Map.Entry) itr.next();
					ItemStack Item = (ItemStack) entry.getValue();
					int ItemID = Item.itemID;
					int ItemMetaData = Item.getItemDamage();

					RecipearLogger.debug("Checking Recipe"
							+ ic2_placeholder_text + ": " + ItemID + ":"
							+ ItemMetaData);

					if (Check(ItemID, ItemMetaData,
							mod_recipear.getBannedrecipesic2())) {
						RecipearLogger.debug("Removing " + ItemID + ":"
								+ ItemMetaData);
						itr.remove();
						itemsremoved++;
					} else if (Check(ItemID, ItemMetaData,
							mod_recipear.getBannedrecipes())) {
						RecipearLogger.debug("Removing " + ItemID + ":"
								+ ItemMetaData);
						itr.remove();
						itemsremoved++;
					}
				}
			}
		}
		return itemsremoved;
	}
	 * public int RemoveRecipesTC3() { int itemsremoved = 0; if
	 * (mod_recipear.Thaumcraft3Detected) {
	 * 
	 * List l = thaumcraft.api.ThaumcraftApi.getCraftingRecipes();
	 * 
	 * RecipearLogger.info("Checking " + l.size() + " Thaumcraft recipe(s)");
	 * 
	 * for (Iterator itr = l.iterator(); itr.hasNext();) { Object recipeoutput =
	 * itr.next();
	 * 
	 * int ItemID = 0; int ItemMetaData = 0; String recipetype = "";
	 * 
	 * if (recipeoutput instanceof ShapedArcaneCraftingRecipes) {
	 * ShapedArcaneCraftingRecipes recipe = (ShapedArcaneCraftingRecipes)
	 * recipeoutput; ItemID = recipe.getRecipeOutput().itemID; ItemMetaData =
	 * recipe.getRecipeOutput().getItemDamage(); recipetype = "Shaped Arcane"; }
	 * else if (recipeoutput instanceof ShapelessArcaneCraftingRecipes) {
	 * ShapelessArcaneCraftingRecipes recipe = (ShapelessArcaneCraftingRecipes)
	 * recipeoutput; ItemID = recipe.getRecipeOutput().itemID; ItemMetaData =
	 * recipe.getRecipeOutput().getItemDamage(); recipetype =
	 * "Shapeless Arcane"; } else if (recipeoutput instanceof
	 * ShapedInfusionCraftingRecipes) { ShapedInfusionCraftingRecipes recipe =
	 * (ShapedInfusionCraftingRecipes) recipeoutput; ItemID =
	 * recipe.getRecipeOutput().itemID; ItemMetaData =
	 * recipe.getRecipeOutput().getItemDamage(); recipetype = "Shaped Infusion";
	 * } else if (recipeoutput instanceof ShapelessInfusionCraftingRecipes) {
	 * ShapelessInfusionCraftingRecipes recipe =
	 * (ShapelessInfusionCraftingRecipes) recipeoutput; ItemID =
	 * recipe.getRecipeOutput().itemID; ItemMetaData =
	 * recipe.getRecipeOutput().getItemDamage(); recipetype =
	 * "Shapeless Infusion"; }
	 * 
	 * RecipearLogger.debug("Checking Recipe for: " + ItemID + ":" +
	 * ItemMetaData + " " + recipetype);
	 * 
	 * if (Check(ItemID, ItemMetaData)) { RecipearLogger.debug("Removing " +
	 * ItemID + ":" + ItemMetaData); itr.remove(); itemsremoved++; } } } return
	 * itemsremoved; }

	public int RemoveRecipes(Side side) {

		int itemsremoved = 0;

		List recipelist = CraftingManager.getInstance().getRecipeList();

		RecipearLogger.info("Checking " + recipelist.size() + " recipe(s)");

		for (Iterator<Object> itr = recipelist.iterator(); itr.hasNext();) {
			Object recipe = itr.next();

			int ItemID = 0;
			int ItemMetaData = 0;
			boolean ic2 = false;

			if (!(recipe instanceof IRecipe)) continue;

			if (mod_recipear.IC2Detected) {
				if (recipe instanceof AdvRecipe
						|| recipe instanceof AdvShapelessRecipe) {
					ic2 = true;
				}
			}

			IRecipe iRecipe = (IRecipe) recipe;
			ItemStack output = iRecipe.getRecipeOutput();

			if (output == null) continue;

			ItemID = output.itemID;
			ItemMetaData = output.getItemDamage();

			String suffix = "";

			if (ic2) suffix = " - IC2";

			RecipearLogger.debug("Checking Recipe" + suffix + ":" + ItemID
					+ ":" + ItemMetaData);

			if ((ic2 && Check(ItemID, ItemMetaData,
					mod_recipear.getBannedrecipesic2()))
					|| Check(ItemID, ItemMetaData,
							mod_recipear.getBannedrecipes())) {
				if (side == Side.CLIENT) {
					RecipearLogger.debug("Placeholding " + ItemID + ":"
							+ ItemMetaData + suffix);
					output.setItemName(EnumChatFormatting.RED
							+ RecipearConfig.placeholderName);
					setCraftingRecipeOutput(iRecipe, output);
					itemsremoved++;
					continue;
				} else {
					RecipearLogger.debug("Removing " + ItemID + ":"
							+ ItemMetaData + suffix);
					itr.remove();
					itemsremoved++;
					continue;
				}
			}
		}

		return itemsremoved;
	}

	public int RemoveFurnaceRecipes() {
		RecipearLogger.info("Checking " + (FurnaceRecipes.smelting().getMetaSmeltingList().size() + FurnaceRecipes.smelting().getSmeltingList().size()) + " Furnace Recipe(s)");
		int itemsremoved = 0;

		for (Iterator itr = FurnaceRecipes.smelting().getMetaSmeltingList()
				.values().iterator(); itr.hasNext();) {
			ItemStack recipeitem = (ItemStack) itr.next();

			if (recipeitem == null)
				continue;

			int ItemID = recipeitem.itemID;
			int ItemMetaData = recipeitem.getItemDamage();

			RecipearLogger.debug("Checking Recipe " + ItemID + ":"
					+ ItemMetaData);

			if (Check(ItemID, ItemMetaData, mod_recipear.getBannedrecipes())) {
				RecipearLogger.debug("Removing " + ItemID + ":" + ItemMetaData);
				itr.remove();
				itemsremoved++;
			}
		}

		for (Iterator itr = FurnaceRecipes.smelting().getSmeltingList().values().iterator(); itr.hasNext();) {
			ItemStack recipeitem = (ItemStack) itr.next();

			if (recipeitem == null)
				continue;

			int ItemID = recipeitem.itemID;
			int ItemMetaData = recipeitem.getItemDamage();

			RecipearLogger.debug("Checking Recipe " + ItemID + ":" + ItemMetaData);

			if (Check(ItemID, 0, mod_recipear.getBannedrecipes())) {
				RecipearLogger.debug("Removing " + ItemID + ":" + ItemMetaData);
				itr.remove();
				itemsremoved++;
			}
		}

		return itemsremoved;
	}
	*/
}
