package mods.recipear;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import mods.recipear.api.RecipearEvent;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.util.EnumChatFormatting;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;

public class PacketManager implements IPacketHandler
{
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player)
	{
		ObjectInputStream inputStream = null;
		try {
			inputStream = new ObjectInputStream(new ByteArrayInputStream(packet.data));
			
			if(packet.channel.equals("recipear") && (inputStream != null))
			{
				RecipearLogger.info("[CLIENT] Received update from server!");
				ConfigPacket configpacket = (ConfigPacket) inputStream.readObject();
				
				RecipearConfig.debug = configpacket.debug;
				RecipearConfig.removeclient = configpacket.removeclient;
				RecipearConfig.placeholderDescription = configpacket.placeholderDescription;
				
				if(configpacket.recipes.size() > 0) {
					BannedRecipes.setBannedRecipes(configpacket.recipes);
					
					Recipear.tooltip.setActive(true);
					Recipear.tooltip.setDescription(EnumChatFormatting.RESET + RecipearConfig.placeholderDescription.replace("$", "\u00A7") + EnumChatFormatting.RESET);
					
					if(configpacket.removeclient) {
						Recipear.events.trigger(new RecipearEvent(Side.CLIENT, false));
					}
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
