package mods.recipear;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import mods.recipear.api.RecipearEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.EnumChatFormatting;

public class RecipearCommand extends CommandBase implements ICommand {

	private boolean reloading = false;
	private boolean outputting = false;

	public int getRequiredPermissionLevel()
	{
		return 3;
	}

	@Override
	public String getCommandName() {
		return "recipear";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] astring) 
	{
		String name = sender.getCommandSenderName();
	
		if((astring.length > 0) && (astring[0].equals("reload"))) 
		{
			if(!reloading) {
					RecipearLogger.info("Config reloaded by " + name);
					reloading = true;
					long startTime = System.currentTimeMillis();
					Recipear.config.reload();
					
					if(RecipearConfig.removeIngame) {
						if(!Recipear.playertracker.active) {
							Recipear.playertracker.active = true;
							GameRegistry.registerPlayerTracker(Recipear.playertracker);
						}
						
						if(!Recipear.playertick.active) {
							Recipear.playertick.active = true;
							TickRegistry.registerScheduledTickHandler(Recipear.playertick, Side.SERVER);
						}
					}
					
					Recipear.events.trigger(new RecipearEvent(Side.SERVER, false));
					
					ConfigPacket configpacket = new ConfigPacket(RecipearConfig.debug,
							RecipearConfig.removeclient,
							RecipearConfig.placeholderDescription,
							BannedRecipes.getBannedRecipes());

					PacketDispatcher.sendPacketToAllPlayers(Recipear.getPacket(configpacket));
					long endTime = System.currentTimeMillis() - startTime;
					reloading = false;
					RecipearLogger.info("Config reloaded by " + name + " in " + endTime + "ms");
					notifyAdmins(sender, "Recipear reloaded by %s in " + endTime + "ms", new Object[] {sender.getCommandSenderName()});
				}
		}
		else if((astring.length > 0) && (astring[0].equals("output"))) 
		{
			if(!outputting) {
				outputting = true;
				long startTime = System.currentTimeMillis();
				RecipearOutput.clear();
				Recipear.events.trigger(new RecipearEvent(Side.SERVER, true));
				if(RecipearOutput.save()) {
					long endTime = System.currentTimeMillis() - startTime;
					notifyAdmins(sender, "%s outputted all recipes to Recipear-output.log in " + endTime + "ms", new Object[] {sender.getCommandSenderName()});
					RecipearLogger.info(name + " outputted all recipes to Recipear-output.log in " + endTime + "ms");
				} else {
					notifyAdmins(sender, "Recipear encountered an error when saving output", new Object[] {sender.getCommandSenderName()});
					RecipearLogger.severe("Recipear encountered an error when saving output");
				}
				outputting = false;
			}
		}
		else
        {
            throw new WrongUsageException("/recipear <reload/output>", new Object[0]);
        }
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		return "/recipear <reload/output>";
	}
	
	public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        return par2ArrayOfStr.length == 1 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, new String[] {"reload", "output"}): null;
    }
}
