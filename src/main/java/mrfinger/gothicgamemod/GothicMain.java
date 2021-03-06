package mrfinger.gothicgamemod;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import mrfinger.gothicgamemod.proxy.CommonProxy;

@Mod(modid                     = GothicMain.MODID,
     name                      = GothicMain.MODNAME,
     version                   = GothicMain.VERSION,
     acceptedMinecraftVersions  = GothicMain.ACCEPTED_VERSIONS,
     dependencies              = "required-after:Forge")
public class GothicMain {
	
	public static final String MODID            = "gothicgamemod";
	public static final String MODNAME          = "GothicGameMod";	
	public static final String VERSION          = "${version}";
	public static final String ACCEPTED_VERSIONS = "[1.7.10]";
	
	@Instance(GothicMain.MODID)
    public static GothicMain instance = new GothicMain();
	
	public static final SimpleNetworkWrapper network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
	
	public static GCreativeTabs tabs;
			
	@SidedProxy(clientSide = "mrfinger.gothicgamemod.proxy.ClientProxy",
				serverSide = "mrfinger.gothicgamemod.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	@EventHandler
    public void preInit(FMLPreInitializationEvent event) {
		//tabs.tabGothicMagic;
		proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {    	
        proxy.init(event);
        proxy.registerClientStuff();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    	proxy.postInit(event);
    }
    
    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {  
    	this.proxy.serverStarting(event);
    }
	
}
