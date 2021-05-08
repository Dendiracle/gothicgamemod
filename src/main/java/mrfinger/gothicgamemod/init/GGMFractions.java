package mrfinger.gothicgamemod.init;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import mrfinger.gothicgamemod.entity.animals.EntityGWolf;
import mrfinger.gothicgamemod.entity.animals.EntityScavenger;
import mrfinger.gothicgamemod.fractions.Fraction;
import mrfinger.gothicgamemod.fractions.PackFraction;
import net.minecraft.entity.player.EntityPlayer;

public class GGMFractions {


    public static final Fraction neutralFraction = new Fraction("neutral");

    public static final PackFraction neutralPackFraction = new PackFraction("neutral_pack", 4.0F, 4.0F);

    public static final PackFraction scavengers = new PackFraction("scavengers", 8.0F, 4.0F);


    public static void preLoad(FMLPreInitializationEvent event)
    {
        loadFractions();
    }



    private static void loadFractions()
    {
        scavengers.fractionSet.add(EntityScavenger.class);
        scavengers.enemiesSet.add(EntityPlayer.class);
        scavengers.enemiesSet.add(EntityGWolf.class);
    }

}
