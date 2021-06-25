package mrfinger.gothicgamemod.init;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import mrfinger.gothicgamemod.block.BlockAnimalEggs;
import net.minecraft.block.material.Material;

import java.util.ArrayList;
import java.util.List;

public class GGMBlocks
{

    public static final BlockAnimalEggs eggGeneral = new BlockAnimalEggs("egg_general", 0.5F,0.5F);
    public static final BlockAnimalEggs scavengerEgg = new BlockAnimalEggs("egg_scavenger", 0.3F,0.4F);


    public static void preLoad(FMLPreInitializationEvent event)
    {
        GameRegistry.registerBlock(eggGeneral, eggGeneral.getUnlocalizedName());
        GameRegistry.registerBlock(scavengerEgg, scavengerEgg.getUnlocalizedName());
    }

}
