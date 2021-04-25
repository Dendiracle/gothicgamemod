package mrfinger.gothicgamemod.init;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import mrfinger.gothicgamemod.battle.DamageType;

import java.util.HashSet;
import java.util.Set;

public class GGMBattleSystem {


    public static final DamageType cutting = new DamageType("cutt");
    public static final DamageType crushing = new DamageType("crush");
    public static final DamageType piersing = new DamageType("piers");
    public static final DamageType fire = new DamageType("fire");
    public static final DamageType cold = new DamageType("cold");
    public static final DamageType electricity = new DamageType("elect");


    public static final Set<DamageType> set = new HashSet<>();


    public static void load(FMLPreInitializationEvent e) {

        set.add(cutting);
        set.add(crushing);
        set.add(piersing);
        set.add(fire);
        set.add(cold);
        set.add(electricity);
    }

}
