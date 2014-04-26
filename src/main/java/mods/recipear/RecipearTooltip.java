package mods.recipear;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public class RecipearTooltip {
	public static boolean active = false;
	public static String description = "";
	
	@ForgeSubscribe
	public void onItemTooltip(ItemTooltipEvent event)
	{
		if (!active || event.entityPlayer == null) {
			return;
	    }
		
		ItemStack itemstack = event.itemStack;
		String DISPLAYNAME = RecipearUtil.getLanguageRegistryEntry(itemstack);
		
		
		if(BannedRecipes.Check(itemstack.itemID, itemstack.getItemDamage(), "DEFAULT") || BannedRecipes.Check(DISPLAYNAME.replaceAll("\\s+","").toLowerCase(), "DEFAULT")) {
			event.toolTip.add(description);
		}
	}

	public static boolean isActive() {
		return active;
	}

	public static void setActive(boolean active) {
		RecipearTooltip.active = active;
	}

	public static void setDescription(String description) {
		RecipearTooltip.description = description;
	}
}
