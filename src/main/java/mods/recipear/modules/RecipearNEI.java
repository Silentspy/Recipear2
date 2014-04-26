package mods.recipear.modules;

import mods.recipear.BannedRecipe;
import mods.recipear.BannedRecipes;
import mods.recipear.Recipear;
import mods.recipear.RecipearLogger;
import mods.recipear.RecipearUtil;
import mods.recipear.api.IRecipear;
import mods.recipear.api.RecipearEvent;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;

@Mod(modid = "Recipear2|NEI", name = "NEI", version = "2.3.1", dependencies="required-after:Recipear2@[2.3,)")
public class RecipearNEI implements IRecipear{
	
	String prefix = "[" + getName() + "] ";
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) 
	{
		Recipear.events.add(this);
	}
	
	public void trigger(RecipearEvent event) {
		
		if(Loader.isModLoaded(getModID())) 
		{
			if(BannedRecipes.GetBannedRecipeAmount() > 0) 
			{
				for(BannedRecipe recipe : BannedRecipes.getBannedRecipes()) {
					if(recipe.type.equals("NEI")) {
						codechicken.nei.api.API.hideItem(recipe.id);
						
						for(ItemStack itemstack : RecipearUtil.getitem(recipe.id)) {
							RecipearLogger.info(prefix + "Removing " + RecipearUtil.getFancyItemStackInfo(itemstack) + " from NEI");
						}
					}
				}
			}
		}
		else 
		{
			RecipearLogger.info(prefix + "Could not find " + getModID());
		}
	}

	@Override
	public String[] getTypes() {
		return new String[] {"NEI"};
	}

	@Override
	public String getName() {
		return "NEI";
	}

	@Override
	public String getFullName() {
		return "NotEnoughItems";
	}

	@Override
	public String getModID() {
		return getFullName();
	}
}