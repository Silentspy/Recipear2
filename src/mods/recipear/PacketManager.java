package mods.recipear;

import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import mods.recipear.api.RecipearEvent;
import mods.recipear.modules.RecipearVanilla;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

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
				RecipearConfig.placeholderName = configpacket.placeholderName;
				RecipearConfig.placeholderDescription = configpacket.placeholderDescription;
				
				BannedRecipes.setBannedRecipes(configpacket.recipes);
				Recipear.events.trigger(new RecipearEvent(Side.CLIENT, false));
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
