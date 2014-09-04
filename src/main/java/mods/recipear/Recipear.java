package mods.recipear;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Logger;

import mods.recipear.api.RecipearEvent;
import mods.recipear.api.RecipearListener;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = "Recipear2", name = "Recipear2", version = "2.3.1", dependencies="required-after:Forge@[9.10,)")
@NetworkMod(clientSideRequired = false, serverSideRequired = false, channels = {"recipear"}, packetHandler = PacketManager.class)
public class Recipear 
{
	public static RecipearListener events = new RecipearListener();
	
	@SidedProxy(clientSide="mods.recipear.RecipearClientProxy", serverSide="mods.recipear.RecipearCommonProxy")
	public static RecipearCommonProxy proxy;
	public static RecipearConfig config;
	public static RecipearPlayerTick playertick = new RecipearPlayerTick();
	public static RecipearPlayerTracker playertracker = new RecipearPlayerTracker();
	
	public static String mcDataDir = "";
	public static RecipearTooltip tooltip = new RecipearTooltip();
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) 
	{
		String date = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
		
		mcDataDir = event.getModConfigurationDirectory().getParentFile().getAbsolutePath();
		
		BannedRecipes.AddBannedRecipeType("INVENTORY");
		RecipearLogger.setLogger(Logger.getLogger("Recipear"), new File(mcDataDir, "Recipear-" + (proxy.isServer() ? "server" : "client") + ".log").getAbsolutePath());
		if(proxy.isServer()) {
			config = new RecipearConfig();
			config.init(event);
		}
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		if(!proxy.isServer()) {
	    	MinecraftForge.EVENT_BUS.register(tooltip);
		}
	}

	@EventHandler
	void ServerStartingEvent(FMLServerStartingEvent event) {
		if (!proxy.isSinglePlayer()) {
			events.trigger(new RecipearEvent(Side.SERVER, false));
			
			if(RecipearConfig.removeIngame) {
				playertracker.active = true;
				GameRegistry.registerPlayerTracker(playertracker);
				playertick.active = true;
				TickRegistry.registerScheduledTickHandler(playertick, Side.SERVER);
			}
			
			ServerCommandManager serverCommand = (ServerCommandManager)MinecraftServer.getServer().getCommandManager();
	        serverCommand.registerCommand(new RecipearCommand());
			NetworkRegistry.instance().registerConnectionHandler(new ConnectionHandler());
		}
	}
	
	public static Packet250CustomPayload getPacket(ConfigPacket object) {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		ObjectOutputStream data = null;
		try {
			data = new ObjectOutputStream(bytes);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
        try {
			data.writeObject(object);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "recipear";
		packet.data = bytes.toByteArray();
		packet.length = bytes.size();
		
		return packet;
	}
}
