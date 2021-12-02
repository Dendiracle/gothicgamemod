package mrfinger.gothicgamemod.proxy;


import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import mrfinger.gothicgamemod.event.EventHandlerClient;
import mrfinger.gothicgamemod.init.GGMCapabilities;
import mrfinger.gothicgamemod.init.GGMGui;
import mrfinger.gothicgamemod.init.GGMKeyBindings;
import mrfinger.gothicgamemod.init.GGMRenderers;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class ClientProxy extends CommonProxy{
	
		
	@Override
	public void registerClientStuff() {
		FMLCommonHandler.instance().bus().register(new EventHandlerClient());
		GGMKeyBindings.registerKeys();
	}
			
	@Override
    public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		GGMGui.preInitClient(event);
		GGMCapabilities.loadClient();
	}
	
	@Override
    public void init(FMLInitializationEvent e) {
		super.init(e);
		GGMRenderers.load(e);
	}
	
	@Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
    }

	@Override
	public EntityPlayer getPlayerEntity(MessageContext ctx) {
		return (ctx.side.isClient() ? Minecraft.getMinecraft().thePlayer : super.getPlayerEntity(ctx));
	}
   
}
