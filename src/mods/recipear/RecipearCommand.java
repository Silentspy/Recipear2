package mods.recipear;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import mods.recipear.api.RecipearEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
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
	public void processCommand(ICommandSender icommandsender, String[] astring) {

		if(icommandsender instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)icommandsender;
			
			if((astring.length > 0) && (astring[0].equals("reload"))) 
			{
				if(!reloading) {
					if(RecipearUtil.isOP(player)) {
						RecipearLogger.info("Config reloaded by " + player.username);
						RecipearUtil.msgPlayer(player, "$eReloading $aRecipear$e..");
						reloading = true;
						Recipear.config.reload();
						Recipear.events.trigger(new RecipearEvent(Side.SERVER, false));
						
						RecipearUtil.msgPlayer(player, "$aRecipear $ewon't restore old recipes, to do so you need a full server restart");
						RecipearUtil.msgPlayer(player, "$eSending update to all players..");
						ConfigPacket configpacket = new ConfigPacket(RecipearConfig.debug,
								RecipearConfig.removeclient, RecipearConfig.placeholderName,
								RecipearConfig.placeholderDescription,
								BannedRecipes.getBannedRecipes());

						PacketDispatcher.sendPacketToAllPlayers(Recipear.getPacket(configpacket));
						
						reloading = false;
					}
				}
			}
			else if((astring.length > 0) && (astring[0].equals("output"))) 
			{
				if(!outputting) {
					if(RecipearUtil.isOP(player)) {
						outputting = true;
						RecipearOutput.clear();
						Recipear.events.trigger(new RecipearEvent(Side.SERVER, true));
						RecipearUtil.msgPlayer(player, "$eOutputted all recipes to $aRecipear-output.log");
						RecipearLogger.info(player.username + " outputted all recipes to Recipear-output.log");
						RecipearOutput.save();
						outputting = false;
					}
				}
			}
			else 
			{
				RecipearUtil.msgPlayer(player, "$a/$crecipear $ereload$a, $eoutput");
			}
		}
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		return null;
	}
}
