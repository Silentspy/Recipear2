package mods.recipear;

import java.util.Iterator;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.EnumChatFormatting;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;

public class RecipearVanilla {

	public int RemoveRecipes(Side side) {

		int itemsremoved = 0;

		List recipelist = CraftingManager.getInstance().getRecipeList();

		RecipearLogger.info("Scanning " + recipelist.size() + " recipe(s)");

		int NBTTAGSCOUNT = 0, ITEMID, METADATA;
		String DISPLAYNAME;
		ItemStack RECIPE_OUTPUT;

		for (Iterator<Object> itr = recipelist.iterator(); itr.hasNext();) {
			Object recipe = itr.next();

			if (!(recipe instanceof IRecipe)) continue;

			IRecipe iRecipe = (IRecipe) recipe;
			RECIPE_OUTPUT = iRecipe.getRecipeOutput();

			if (RECIPE_OUTPUT == null) continue;

			ITEMID = RECIPE_OUTPUT.itemID;
			METADATA = RECIPE_OUTPUT.getItemDamage();
			if(RECIPE_OUTPUT.getTagCompound() != null)
				NBTTAGSCOUNT = RECIPE_OUTPUT.getTagCompound().getTags().size();

			DISPLAYNAME = RECIPE_OUTPUT.getDisplayName();

			if(LanguageRegistry.instance().getStringLocalization(DISPLAYNAME).length() > 0)
				DISPLAYNAME = LanguageRegistry.instance().getStringLocalization(DISPLAYNAME);

			RecipearLogger.debug("OUTPUT: " + DISPLAYNAME + ", ID: " + ITEMID + ", METADATA: " + METADATA + ", NBTCOUNT: " + NBTTAGSCOUNT);

			if(BannedRecipes.Check(ITEMID, METADATA, "WORKBENCH") || BannedRecipes.Check(DISPLAYNAME.replaceAll("\\s+","").toLowerCase(), "WORKBENCH")) {
				if (side == Side.CLIENT) {
					RecipearLogger.info("Placeholding: " + DISPLAYNAME + ", ID: " + ITEMID + ", METADATA: " + METADATA);
					RecipearUtil.setCraftingRecipeOutput(iRecipe, RECIPE_OUTPUT);
					itemsremoved++;
					continue;
				} else {
					RecipearLogger.info("Removing: " + DISPLAYNAME + ", ID: " + ITEMID + ", METADATA: " + METADATA);
					itemsremoved++;
					itr.remove();
					continue;
				}
			}
		}

		return itemsremoved;
	}


	public int RemoveFurnaceRecipes() {
		RecipearLogger.info("Scanning " + (FurnaceRecipes.smelting().getMetaSmeltingList().size() + FurnaceRecipes.smelting().getSmeltingList().size()) + " Furnace Recipe(s)");
		int itemsremoved = 0;

		int NBTTAGSCOUNT = 0, ITEMID, METADATA;
		String DISPLAYNAME;
		ItemStack RECIPE_OUTPUT;

		for (Iterator itr = FurnaceRecipes.smelting().getMetaSmeltingList().values().iterator(); itr.hasNext();) 
		{
			RECIPE_OUTPUT = (ItemStack) itr.next();

			if (RECIPE_OUTPUT == null)
				continue;

			NBTTAGSCOUNT = 0;
			ITEMID = RECIPE_OUTPUT.itemID;
			METADATA = RECIPE_OUTPUT.getItemDamage();
			DISPLAYNAME = RECIPE_OUTPUT.getDisplayName();
			if(RECIPE_OUTPUT.getTagCompound() != null)
				NBTTAGSCOUNT = RECIPE_OUTPUT.getTagCompound().getTags().size();

			if(LanguageRegistry.instance().getStringLocalization(DISPLAYNAME).length() > 0)
				DISPLAYNAME = LanguageRegistry.instance().getStringLocalization(DISPLAYNAME);

			RecipearLogger.debug("OUTPUT: " + DISPLAYNAME + ", ID: " + ITEMID + ", METADATA: " + METADATA + ", NBTCOUNT: " + NBTTAGSCOUNT);

			if (BannedRecipes.Check(ITEMID, METADATA, "FURNACE") || BannedRecipes.Check(DISPLAYNAME.replaceAll("\\s+","").toLowerCase(), "FURNACE")) {
				RecipearLogger.info("Removing: " + DISPLAYNAME + ", ID: " + ITEMID + ", METADATA: " + METADATA);
				itr.remove();
				itemsremoved++;
			}
		}

		for (Iterator itr = FurnaceRecipes.smelting().getSmeltingList().values().iterator(); itr.hasNext();) {
			RECIPE_OUTPUT = (ItemStack) itr.next();

			if (RECIPE_OUTPUT == null)
				continue;

			NBTTAGSCOUNT = 0;
			ITEMID = RECIPE_OUTPUT.itemID;
			METADATA = RECIPE_OUTPUT.getItemDamage();
			DISPLAYNAME = RECIPE_OUTPUT.getDisplayName();
			if(RECIPE_OUTPUT.getTagCompound() != null)
				NBTTAGSCOUNT = RECIPE_OUTPUT.getTagCompound().getTags().size();

			if(LanguageRegistry.instance().getStringLocalization(DISPLAYNAME).length() > 0)
				DISPLAYNAME = LanguageRegistry.instance().getStringLocalization(DISPLAYNAME);

			RecipearLogger.debug("OUTPUT: " + DISPLAYNAME + ", ID: " + ITEMID + ", METADATA: " + METADATA + ", NBTCOUNT: " + NBTTAGSCOUNT);

			if (BannedRecipes.Check(ITEMID, METADATA, "FURNACE") || BannedRecipes.Check(DISPLAYNAME.replaceAll("\\s+","").toLowerCase(), "FURNACE")) {
				RecipearLogger.info("Removing: " + DISPLAYNAME + ", ID: " + ITEMID + ", METADATA: " + METADATA);
				itr.remove();
				itemsremoved++;
			}
		}

		return itemsremoved;
	}
}
