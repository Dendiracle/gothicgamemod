package mrfinger.gothicgamemod.mixin.entity;

import mrfinger.gothicgamemod.battle.DamageType;
import mrfinger.gothicgamemod.battle.UseSpendings;
import mrfinger.gothicgamemod.entity.IGGMEntityMob;
import mrfinger.gothicgamemod.entity.capability.attributes.IGGMAttribute;
import mrfinger.gothicgamemod.entity.capability.attributes.IGGMDynamicAttributeInstance;
import mrfinger.gothicgamemod.init.GGMBattleSystem;
import mrfinger.gothicgamemod.item.IItemMeleeWeapon;
import mrfinger.gothicgamemod.util.IGGMDamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.util.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.HashMap;
import java.util.Map;

@Mixin(EntityMob.class)
public abstract class GGMEntityMob extends GGMEntityCreature implements IGGMEntityMob {

    int aaa;


    @Redirect(method = "attackEntityAsMob", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;attackEntityFrom(Lnet/minecraft/util/DamageSource;F)Z"))
    private boolean onAttackEntityAsMob(Entity entity, DamageSource ds, float damage) {

        Map<DamageType, Float> map;

        if (thisEntity().getHeldItem() != null && thisEntity().getHeldItem().getItem() instanceof IItemMeleeWeapon)
        {
            IItemMeleeWeapon tool = (IItemMeleeWeapon) thisEntity().getHeldItem().getItem();
            Map<DamageType, UseSpendings> itemMap = tool.getDamageValuesMap();
            map = new HashMap<>(itemMap.size(), 1.0F);

            for (Map.Entry<DamageType, UseSpendings> e : itemMap.entrySet())
            {
                IAttributeInstance ai = thisEntity().getEntityAttribute(e.getValue().getAttribute());

                if (ai != null) {
                    float d = ((float) ai.getAttributeValue() * e.getValue().getAttributeMultiplier()) + e.getValue().getSpendsFromD();
                    map.put(e.getKey(), d);
                    damage += d;
                }
            }
        }
        else
        {
            damage = (float) thisEntity().getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
            map = new HashMap<>(1, 1.0F);
            map.put(GGMBattleSystem.crushing, damage);
        }

        ((IGGMDamageSource) ds).setDamageValues(map);
        return entity.attackEntityFrom(ds, damage);
    }


    public EntityMob thisEntity() {
        return (EntityMob) (Object) this;
    }

}
