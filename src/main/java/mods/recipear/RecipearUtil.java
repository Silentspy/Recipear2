package mods.recipear;

import java.util.ArrayList;
import java.util.HashSet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatMessageComponent;
import net.minecraftforge.oredict.OreDictionary;

public class RecipearUtil {

	public static boolean isOP(EntityPlayer player) {
		return (MinecraftServer.getServer().getConfigurationManager().getOps().contains(player.username.toLowerCase()) ? true : false);
	}

	public static ItemStack[] concat(ItemStack[] A, ItemStack[] B) {
		int aLen = A.length;
		int bLen = B.length;
		ItemStack[] C= new ItemStack[aLen+bLen];
		System.arraycopy(A, 0, C, 0, aLen);
		System.arraycopy(B, 0, C, aLen, bLen);
		return C;
	}

	public static String getLanguageRegistryEntry (ItemStack itemstack) {
		String name = "Unknown";
		
		try {
			if(itemstack.getUnlocalizedName() != null) {
				name = itemstack.getUnlocalizedName(); 
			}
		}
		catch (NullPointerException ex) { 
			return "Unknown";
		}
		
		return name;
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
	
	public static void RemoveBannedItemsFromInventory(EntityPlayer player) {

		if (!isOP(player) && (BannedRecipes.GetBannedRecipeAmount() > 0)) {

			ItemStack[] whole_inventory = concat(
					player.inventory.mainInventory,
					player.inventory.armorInventory);

			for (ItemStack ITEM : whole_inventory) {
				if (ITEM != null) 
				{
					String DISPLAYNAME = RecipearUtil.getLanguageRegistryEntry(ITEM);

					if (BannedRecipes.Check(ITEM.itemID, ITEM.getItemDamage(), "INVENTORY") || BannedRecipes.Check(DISPLAYNAME, "INVENTORY"))
					{
						player.inventory.clearInventory(ITEM.itemID, ITEM.getItemDamage());
						msgPlayer(player, String.format(RecipearConfig.removeIngameMsg.replace("$", "\u00A7"), DISPLAYNAME));
						RecipearLogger.info(String.format("Removing %s(%s:%s) from " + player.username, DISPLAYNAME, ITEM.itemID, ITEM.getItemDamage()));
					}
				}
			}

			whole_inventory = null;
		}
	}
	
	public static String getOreName(ItemStack itemstack) {
		int oreid = OreDictionary.getOreID(itemstack);
		
		if(oreid >= 0) {
			return OreDictionary.getOreName(oreid);
		}
		
		return "Unknown";
	}
	
	public static void msgPlayer(EntityPlayer player, String msg) {
		player.sendChatToPlayer(new ChatMessageComponent().createFromText(msg.replace("$", "\u00A7")));
	}
	
	public static String getFancyItemStackInfo(ItemStack itemstack) {
		return getLanguageRegistryEntry(itemstack) + "@" + itemstack.itemID + ":" + itemstack.getItemDamage() + " x " + itemstack.stackSize;
	}
	
	public static ItemStack[] getitem(int id) {
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		
		if(id <= 32000) {
			int index = 0;
		
			while(index < 16) {
				ItemStack itemstack = new ItemStack(id, 1, index);
				
				if(itemstack != null) {
					if((index > 0) && !(getLanguageRegistryEntry(items.get(0)).equals(getLanguageRegistryEntry(itemstack)))) {
						items.add(itemstack);
					} else if (index == 0) {
						items.add(itemstack);
					}
				}
				index++;
			}
		}
		
		return items.toArray(new ItemStack[0]);
	}
}
