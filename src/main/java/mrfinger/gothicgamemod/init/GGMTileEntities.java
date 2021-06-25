package mrfinger.gothicgamemod.init;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import mrfinger.gothicgamemod.GothicMain;
import mrfinger.gothicgamemod.tileentity.TileEntityAnimalEgg;

public class GGMTileEntities
{

    public static void preLoad(FMLPreInitializationEvent event)
    {
        GameRegistry.registerTileEntity(TileEntityAnimalEgg.class, GothicMain.MODID + "." + "tileentity.animalegg");
    }

}
