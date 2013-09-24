package mods.recipear;

import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.IPlayerTracker;

public class RecipearPlayerEvent implements IPlayerTracker {

	@Override
	public void onPlayerLogin(EntityPlayer player) 
	{
		Recipear.RemoveBannedItemsFromInventory(player);
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
