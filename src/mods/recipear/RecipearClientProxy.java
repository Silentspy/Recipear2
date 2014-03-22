package mods.recipear;

import net.minecraft.client.Minecraft;

public class RecipearClientProxy extends RecipearCommonProxy{
	@Override
	public boolean isSinglePlayer() {
		return Minecraft.getMinecraft().isSingleplayer();
	}
	
	@Override
	public boolean isServer() {
		return false;
	}
}
