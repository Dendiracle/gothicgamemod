package mrfinger.gothicgamemod.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import mrfinger.gothicgamemod.command.CommandGGMSet;
import mrfinger.gothicgamemod.command.CommandGothicStats;
import mrfinger.gothicgamemod.init.*;
import mrfinger.gothicgamemod.network.PacketDispatcher;
import net.minecraft.entity.player.EntityPlayer;

public class CommonProxy {

	
	public void registerClientStuff() {}
		
	public void preInit(FMLPreInitializationEvent event)
	{
		GGMBattleSystem.load(event);
		GGMGui.preInit(event);
		GGMCapabilities.load(event);
		GGMFractions.preLoad(event);
		GGMEntityAnimations.preLoad(event);
		PacketDispatcher.registerClientPackets();
		PacketDispatcher.registerServerPackets();
		PacketDispatcher.registerBiPackets();
		GGMItems.load(event);
		GGMBlocks.preLoad(event);
		GGMTileEntities.preLoad(event);
	}
	
		
	public void init(FMLInitializationEvent e)
	{
		GGMEntities.load(e);
		GGMEvents.load(e);
	}
	
	public void postInit(FMLPostInitializationEvent e) {
		GGMCapabilities.postLoad(e);
	}	    

	public void serverStarting(FMLServerStartingEvent event)
	{
		event.registerServerCommand(new CommandGothicStats());
		event.registerServerCommand(new CommandGGMSet());
	} 
	
	public Side getSide() {
        return this instanceof ClientProxy ? Side.CLIENT : Side.SERVER;
    }

	public EntityPlayer getPlayerEntity(MessageContext ctx) {
		return ctx.getServerHandler().playerEntity;
	}
       
}
