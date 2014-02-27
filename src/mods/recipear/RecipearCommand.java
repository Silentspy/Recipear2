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
			
			if((astring.length > 0) && (astring[0].equals("output"))) 
			{
				if(RecipearUtil.isOP(player)) {
					if(!outputting) {
						RecipearUtil.msgPlayer(player, "$eOutputting recipe list to $aRecipear.log$e...");
						outputting = true;
						Recipear.recipeEvents.trigger(new RecipearEvent(Side.SERVER, false));
						outputting = false;
					}
				}
			}
			else if((astring.length > 0) && (astring[0].equals("reload"))) 
			{
				if(RecipearUtil.isOP(player)) {
					if(!reloading) {
						RecipearUtil.msgPlayer(player, "$eReloading $aRecipear$e..");
						reloading = true;
						Recipear.recipearconfig.reload();
						Recipear.recipeEvents.trigger(new RecipearEvent(Side.SERVER, true));
						
						RecipearUtil.msgPlayer(player, "$eSending update to all players..");
						PacketDispatcher.sendPacketToAllPlayers(BannedRecipes.getPacket());
						
						reloading = false;
					}
				}
			}
			else 
			{
				RecipearUtil.msgPlayer(player, "$a/recipear $eoutput$a|$ereload");
				RecipearUtil.msgPlayer(player, "$aoutput $eOutputs known recipe lists to $aRecipear.log");
				RecipearUtil.msgPlayer(player, "$areload $eReloads config");
			}
		}
	}

	

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		return null;
	}
	
	
}
