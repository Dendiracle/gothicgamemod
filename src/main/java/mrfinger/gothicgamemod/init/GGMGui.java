package mrfinger.gothicgamemod.init;

import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import mrfinger.gothicgamemod.GothicMain;
import mrfinger.gothicgamemod.client.gui.GGMGuiInGame;
import mrfinger.gothicgamemod.handlers.GuiHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;

public class GGMGui {

	public static final GGMGuiInGame guiInGame = new GGMGuiInGame();
	private static int guiCount;
	public static final int GGMGuiInvenory = 10;


	public static void preInit(FMLPreInitializationEvent event)
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(GothicMain.instance, new GuiHandler());
	}

	public static void preInitClient(FMLPreInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(guiInGame);
	}

}
