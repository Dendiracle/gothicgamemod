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
public abstract class GGMEntityMob implements IGGMEntityMob {


    /*@Inject(method = "attackEntity", at = @At("HEAD"), cancellable = true)
    private void onAttackEntity(Entity entity, float distance, CallbackInfo ci) {

        if (this.isAnimedAttack()) {

            if (distance <= this.attackDistance() && entity.boundingBox.maxY > thisEntity().boundingBox.minY && entity.boundingBox.minY < thisEntity().boundingBox.maxY) {
                this.chargeAttack();
            }
            ci.cancel();
        }
    }*/


    @Redirect(method = "attackEntityAsMob", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;attackEntityFrom(Lnet/minecraft/util/DamageSource;F)Z"))
    private boolean onAttackEntityAsMob(Entity entity, DamageSource ds, float damage) {

        if (thisEntity().getHeldItem() != null && thisEntity().getHeldItem().getItem() instanceof IItemMeleeWeapon) {

            damage = 0.0F;
            IItemMeleeWeapon tool = (IItemMeleeWeapon) thisEntity().getHeldItem().getItem();
            Map<DamageType, Map<IGGMAttribute, UseSpendings>> itemMap = tool.getDamageValuesMap();
            Map<DamageType, Float> map = new HashMap<>(itemMap.size(), 1.0F);

            for (Map.Entry<DamageType, Map<IGGMAttribute, UseSpendings>> e : itemMap.entrySet()) {

                float damage1 = 0.0F;

                for(Map.Entry<IGGMAttribute, UseSpendings> ee : e.getValue().entrySet()) {

                    IAttributeInstance mainAttribute = this.getEntityAttribute(ee.getKey());
                    IAttributeInstance spendAttribute = this.getEntityAttribute(ee.getValue().getDynamicAttribute());

                    if (mainAttribute != null && (ee.getValue().getSpendsFromD() <= 0.0D || spendAttribute instanceof IGGMDynamicAttributeInstance)) {

                        double curr = (float) ((IGGMDynamicAttributeInstance) spendAttribute).getCurrentValue();
                        if (curr <= 0.0D && ee.getValue().getSpendsFromD() > 0.0D) continue;
                        double dam = mainAttribute.getAttributeValue() * ee.getValue().getAttributeMultiplier();
                        double toSpend = dam * ee.getValue().getSpendsFromD();

                        if (curr < toSpend) {

                            dam *= curr / toSpend;
                            toSpend = curr;
                        }

                        if (toSpend > 0.0D) ((IGGMDynamicAttributeInstance) spendAttribute).changeCurrentValue(-toSpend);
                        damage1 += (float) dam;
                    }

                    damage += damage1;
                    map.put(e.getKey(), damage1);
                }
            }

            ((IGGMDamageSource) ds).setDamageValues(map);
        }

        else {

            damage = (float) thisEntity().getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
            Map<DamageType, Float> map = new HashMap<>(1, 1.0F);
            map.put(GGMBattleSystem.crushing, damage);
            ((IGGMDamageSource) ds).setDamageValues(map);
        }

        return entity.attackEntityFrom(ds, damage);
    }



    public EntityMob thisEntity() {
        return (EntityMob) (Object) this;
    }

}
