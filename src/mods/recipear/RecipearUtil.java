package mods.recipear;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.ReflectionHelper;

public class RecipearUtil {

	public static boolean isOP(EntityPlayer player) {
		return (MinecraftServer.getServer().getServerOwner() == player.username) ? true
				: (MinecraftServer.getServer().getConfigurationManager()
						.getOps().contains(player.username.toLowerCase()) ? true
								: false);
	}

	public static ItemStack[] concat(ItemStack[] A, ItemStack[] B) {
		int aLen = A.length;
		int bLen = B.length;
		ItemStack[] C= new ItemStack[aLen+bLen];
		System.arraycopy(A, 0, C, 0, aLen);
		System.arraycopy(B, 0, C, aLen, bLen);
		return C;
	}

	public static void setCraftingRecipeOutput(IRecipe iRecipe, ItemStack output) {

		if (output.stackTagCompound == null)
		{
			output.stackTagCompound = new NBTTagCompound("tag");
		}

		if (!output.stackTagCompound.hasKey("display"))
		{
			output.stackTagCompound.setCompoundTag("display", new NBTTagCompound());
		}

		String ChatSpecial = "\u00A7";
		String name = EnumChatFormatting.RESET + RecipearConfig.placeholderName.replace("$", ChatSpecial) + EnumChatFormatting.RESET;
		String description = EnumChatFormatting.RESET + RecipearConfig.placeholderDescription.replace("$", ChatSpecial) + EnumChatFormatting.RESET;


		NBTTagList description_list = new NBTTagList();
		description_list.appendTag(new NBTTagString("description", description));

		output.stackTagCompound.getCompoundTag("display").setString("Name", name);
		output.stackTagCompound.getCompoundTag("display").setTag("Lore", description_list);

		String ShapedRecipesOutput = (Recipear.debug) ? "recipeOutput" : "field_77575_e";
		String ShapelessRecipesOutput = (Recipear.debug) ? "recipeOutput" : "field_77580_a";

		if (iRecipe instanceof ShapedRecipes) {
			ReflectionHelper.setPrivateValue(ShapedRecipes.class,
					(ShapedRecipes) iRecipe, output, ShapedRecipesOutput);
		} else if (iRecipe instanceof ShapelessRecipes) {
			ReflectionHelper.setPrivateValue(ShapelessRecipes.class,
					(ShapelessRecipes) iRecipe, output, ShapelessRecipesOutput);
		} else if (iRecipe instanceof ShapelessOreRecipe) {
			ReflectionHelper.setPrivateValue(ShapelessOreRecipe.class,
					(ShapelessOreRecipe) iRecipe, output, "output");
		} else if (iRecipe instanceof ShapedOreRecipe) {
			ReflectionHelper.setPrivateValue(ShapedOreRecipe.class,
					(ShapedOreRecipe) iRecipe, output, "output");
		} else if ((Loader.isModLoaded("IC2")) && (iRecipe instanceof ic2.core.AdvRecipe)) {
			((ic2.core.AdvRecipe)iRecipe).output = output; 
		} else if ((Loader.isModLoaded("IC2")) && (iRecipe instanceof ic2.core.AdvShapelessRecipe)) { 
			((ic2.core.AdvShapelessRecipe)iRecipe).output = output; 
		}
	}

	public static String getLanguageRegistryEntry (String entry) {
		
		if(entry == null) return "Unknown";
		
		return entry;
		
		/*
		if(Recipear.server) return entry; 
		
		if(LanguageRegistry.instance().getStringLocalization(entry).length() > 0)
			entry = LanguageRegistry.instance().getStringLocalization(entry);
		
		return entry; */
	}

	public static boolean isInteger(String s) {
		try 
		{ 
			Integer.parseInt(s); 
		} 
		catch(NumberFormatException e)
		{ 
			return false; 
		}
		return true;
	}
}
