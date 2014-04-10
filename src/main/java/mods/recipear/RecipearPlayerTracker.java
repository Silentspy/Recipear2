package mods.recipear;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import cpw.mods.fml.common.IPlayerTracker;

public class RecipearPlayerTracker implements IPlayerTracker {

	public static boolean active = false;
	
	@Override
	public void onPlayerLogin(EntityPlayer player) 
	{
		if(RecipearConfig.removeIngame) {
			EntityPlayerMP player_mp = (EntityPlayerMP) player;
			RecipearUtil.RemoveBannedItemsFromInventory(player_mp);
		}
	}

	@Override
	public void onPlayerLogout(EntityPlayer player) 
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onPlayerChangedDimension(EntityPlayer player) 
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onPlayerRespawn(EntityPlayer player) 
	{
		// TODO Auto-generated method stub
	}

}
