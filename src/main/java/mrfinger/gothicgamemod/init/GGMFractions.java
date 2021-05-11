package mrfinger.gothicgamemod.init;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import mrfinger.gothicgamemod.fractions.Fraction;
import mrfinger.gothicgamemod.fractions.PackFraction;

public class GGMFractions {


    public static final Fraction neutralFraction = new Fraction("neutral");
    public static final PackFraction neutralPackFraction = new PackFraction("neutral_pack", 4.0F, 4.0F);

    public static final Fraction humans = new Fraction("human");

    public static final PackFraction scavengers = new PackFraction("scavenger", 12.0F, 6.0F);
    public static final PackFraction wolves = new PackFraction("wolf", 16.0F, 6.0F);


    public static void preLoad(FMLPreInitializationEvent event)
    {
        loadFractions();
    }



    private static void loadFractions()
    {
        scavengers.enemiesSet.add(humans);
        wolves.enemiesSet.add(humans);

        squarrelFractions(scavengers, wolves);
    }

    private static void squarrelFractions(Fraction fraction1, Fraction fraction2)
    {
        fraction1.enemiesSet.add(fraction2);
        fraction2.enemiesSet.add(fraction1);
    }

}
