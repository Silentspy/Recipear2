package mods.recipear;

import java.util.EnumSet;

import net.minecraft.entity.player.EntityPlayerMP;
import cpw.mods.fml.common.IScheduledTickHandler;
import cpw.mods.fml.common.TickType;

public class RecipearPlayerTick implements IScheduledTickHandler{

	public static boolean active = false;
	
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) 
	{
		// not in use
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData)
	{
		if(RecipearConfig.removeIngame) {
			RecipearUtil.RemoveBannedItemsFromInventory((EntityPlayerMP)tickData[0]);
		}
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.PLAYER);
	}

	@Override
	public String getLabel() {
		return "RecipearPlayerTick";
	}

	@Override
	public int nextTickSpacing() {
		if(RecipearConfig.removeIngameInterval >= 10) {
			return 20*RecipearConfig.removeIngameInterval;
		} else {
			return 20*60;
		}
	}
}
