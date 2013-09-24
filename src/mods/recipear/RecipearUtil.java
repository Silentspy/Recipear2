package mods.recipear;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.Loader;
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

		String ShapedRecipesOutput = "recipeOutput";
		String ShapelessRecipesOutput = "recipeOutput";

		if (!Recipear.debug) {
			ShapedRecipesOutput = "field_77575_e";
			ShapelessRecipesOutput = "field_77580_a";
		}

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
