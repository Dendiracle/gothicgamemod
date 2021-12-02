package mrfinger.gothicgamemod.init;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import mrfinger.gothicgamemod.event.EventHandlerEntity;
import mrfinger.gothicgamemod.event.EventHandlerItem;
import mrfinger.gothicgamemod.event.EventHandlerServer;
import net.minecraftforge.common.MinecraftForge;

public class GGMEvents {
	
	 public static void load(FMLInitializationEvent e) {		 
		 registerEvent(new EventHandlerEntity());
		 registerEvent(new EventHandlerItem());
		 registerEvent(new EventHandlerServer());
	 }
	 
	 public static void registerEvent(Object obj)
	    {
	     FMLCommonHandler.instance().bus().register(obj);
	     MinecraftForge.EVENT_BUS.register(obj);
	    }

}
