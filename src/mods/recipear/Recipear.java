package mods.recipear;

import net.minecraft.command.ServerCommandManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.EnumChatFormatting;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = "Recipear2", name = "Recipear2", version = "2.0.3", dependencies="required-after:Forge@[9.10,)")
public class Recipear 
{
	public static boolean debug = true;
	public static boolean server = false; 
	public static boolean outputting = false;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) 
	{
		server = (event.getSide().equals(Side.SERVER)) ? true : false;
		BannedRecipes.AddBannedRecipeType("CRAFTING","FURNACE","INVENTORY");
		RecipearLogger.setLogger(event.getModLog());
		new RecipearConfig(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) 
	{
		String supported_types = "Supported Recipe Types are";
		
		for(String type : BannedRecipes.getBannedRecipeTypes()) {
			supported_types += " " + type;
		}
		
		if(debug) RecipearConfig.debug = true;
		
		RecipearLogger.info(supported_types);
		
		//RecipearConfig.debug = true;
		if(BannedRecipes.GetBannedRecipeAmount() > 0) {
			long startTime = System.currentTimeMillis();
			
			RecipearLogger.info("Starting in " + event.getSide().toString() + " Mode");
			RecipearVanilla recipear = new RecipearVanilla();
			RecipearLogger.info("Removed " + recipear.RemoveRecipes() + " Crafting recipe(s)");
			RecipearLogger.info("Removed " + recipear.RemoveFurnaceRecipes() + " Furnace recipe(s)");
			RecipearLogger.info("Finished in " + (System.currentTimeMillis() - startTime) + "ms");
		}
	}

	@EventHandler
	void ServerStartedEvent(FMLServerStartedEvent event) {
		//ServerCommandManager serverCommand = (ServerCommandManager)MinecraftServer.getServer().getCommandManager();
        //serverCommand.registerCommand(new RecipearCommand());
		if((BannedRecipes.GetBannedRecipeAmount() > 0) && (RecipearConfig.removeingame)) {
			RecipearLogger.info("Starting Player Tracker and Tick Handler for Removing items from player");
			GameRegistry.registerPlayerTracker(new RecipearPlayerEvent());
			TickRegistry.registerScheduledTickHandler(new RecipearPlayerTick(), Side.SERVER);
		}
	}

	public static void RemoveBannedItemsFromInventory(EntityPlayer player) {

		if (!RecipearUtil.isOP(player)) {

			ItemStack[] whole_inventory = RecipearUtil.concat(
					player.inventory.mainInventory,
					player.inventory.armorInventory);

			for (ItemStack RECIPE_OUTPUT : whole_inventory) {
				if (RECIPE_OUTPUT != null) 
				{
					String DISPLAYNAME = RECIPE_OUTPUT.getDisplayName();
					RecipearUtil.getLanguageRegistryEntry(DISPLAYNAME);

					if (BannedRecipes.Check(RECIPE_OUTPUT.itemID, RECIPE_OUTPUT.getItemDamage(), "INVENTORY") 
							|| BannedRecipes.Check(DISPLAYNAME, "INVENTORY"))
					{
						player.sendChatToPlayer(
								ChatMessageComponent.createFromText(EnumChatFormatting.YELLOW + DISPLAYNAME
										+ " is banned, removing from inventory..."));
						player.inventory.clearInventory(RECIPE_OUTPUT.itemID, RECIPE_OUTPUT.getItemDamage());
					}
				}
			}

			whole_inventory = null;
		}
	}
}
