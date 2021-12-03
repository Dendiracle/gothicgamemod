package mrfinger.gothicgamemod.init;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import mrfinger.gothicgamemod.battle.DamageType;
import mrfinger.gothicgamemod.battle.UseSpendings;
import mrfinger.gothicgamemod.entity.capability.attribute.generic.IGGMAttribute;
import mrfinger.gothicgamemod.mixin.common.entity.player.GGMEntityPlayer;
import net.minecraft.entity.SharedMonsterAttributes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GGMBattleSystem {


    public static final DamageType cutting = new DamageType("cutt");
    public static final DamageType crushing = new DamageType("crush");
    public static final DamageType piersing = new DamageType("piers");
    public static final DamageType fire = new DamageType("fire");
    public static final DamageType cold = new DamageType("cold");
    public static final DamageType electricity = new DamageType("elect");


    public static final Set<DamageType> set = new HashSet<>();


    public static final Map<DamageType, UseSpendings> standartDamageValuesBlockModifiers = new HashMap<>(6, 1.0F);
    static
    {
        standartDamageValuesBlockModifiers.put(cutting, new UseSpendings((IGGMAttribute) SharedMonsterAttributes.attackDamage, 0.16F, 1.0F));
        standartDamageValuesBlockModifiers.put(crushing, new UseSpendings((IGGMAttribute) SharedMonsterAttributes.attackDamage, 0.1F, 1.2F));
        standartDamageValuesBlockModifiers.put(piersing, new UseSpendings((IGGMAttribute) SharedMonsterAttributes.attackDamage, 0.2F, 0.8F));
        standartDamageValuesBlockModifiers.put(fire, new UseSpendings(GGMCapabilities.intelligence, 0.03F, 1.0F));
        standartDamageValuesBlockModifiers.put(cold, new UseSpendings(GGMCapabilities.intelligence, 0.03F, 1.0F));
        standartDamageValuesBlockModifiers.put(electricity, new UseSpendings(GGMCapabilities.intelligence, 0.03F, 1.0F));
    }


    public static void load(FMLPreInitializationEvent e) {

        set.add(cutting);
        set.add(crushing);
        set.add(piersing);
        set.add(fire);
        set.add(cold);
        set.add(electricity);


    }

}
