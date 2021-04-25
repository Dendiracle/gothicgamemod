package mrfinger.gothicgamemod.mixin.item;

import com.google.common.collect.Multimap;
import mrfinger.gothicgamemod.battle.DamageType;
import mrfinger.gothicgamemod.battle.UseSpendings;
import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.capability.attributes.IGGMAttribute;
import mrfinger.gothicgamemod.init.GGMBattleSystem;
import mrfinger.gothicgamemod.init.GGMCapabilities;
import mrfinger.gothicgamemod.item.IItemBlocker;
import mrfinger.gothicgamemod.item.IItemMeleeWeapon;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin({ItemTool.class, ItemSword.class})
public class GGMItemTool implements IItemMeleeWeapon, IItemBlocker {


    protected DamageType baseDamageType;

    protected IGGMAttribute baseAttribute;

    protected IGGMAttribute dynamicAttribute;

    protected float baseAttributeMultiplier;

    protected float baseSpendsFromD;


    protected Map<DamageType, Map<IGGMAttribute, UseSpendings>> damageValues;


    protected Map<DamageType, Map<IGGMAttribute, UseSpendings>> blockerMap;


    protected float sustainability;


    @Inject(method = "<init>*", at = @At("RETURN"))
    private void onInit(CallbackInfo ci) {

        this.baseDamageType = GGMBattleSystem.cutting;
        this.baseAttribute = (IGGMAttribute) SharedMonsterAttributes.attackDamage;
        this.dynamicAttribute = GGMCapabilities.maxStamina;
        this.baseAttributeMultiplier = 1.0F;
        this.baseSpendsFromD = 0.25F;
    }


    @Override
    public Map<DamageType, Map<IGGMAttribute, UseSpendings>> getDamageValuesMap() {

        Map<DamageType, Map<IGGMAttribute, UseSpendings>> map;
        Map<IGGMAttribute, UseSpendings> m;

        if (this.damageValues == null) {

            map = new HashMap<>(1, 1.0F);
            m = new HashMap<>(1, 1.0F);
        }
        else {

            map = new HashMap<>(this.damageValues.size() + 1, 1.0F);
            map.putAll(this.damageValues);
            m = this.damageValues.get(this.baseDamageType);

            if (m == null) {
                m = new HashMap<>(1, 1.0F);
            }
            else {

                Map<IGGMAttribute, UseSpendings> old = m;
                m = new HashMap<>(1 + old.size(), 1.0F);
                m.putAll(old);
            }
        }

        m.put(this.baseAttribute, new UseSpendings(this.dynamicAttribute, this.baseAttributeMultiplier, this.baseSpendsFromD));
        map.put(this.baseDamageType, m);
        return this.damageValues;
    }

    @Override
    public Map<DamageType, Map<IGGMAttribute, UseSpendings>> getDamageValuesMapToRed() {

        return this.damageValues;
    }


    @Override
    public void setBaseDamage(DamageType type, float attributeMultiplier) {

        this.baseDamageType = type;
        this.baseAttributeMultiplier = attributeMultiplier;
    }

    @Override
    public void setBaseDamage(DamageType type, IGGMAttribute baseAttribute, IGGMAttribute dynamicAttribute, float attributeMultiplier, float spendsFromDynamicAttribute) {

        this.setBaseDamage(type, attributeMultiplier);
        this.baseAttribute = baseAttribute;
        this.dynamicAttribute = dynamicAttribute;
        this.baseSpendsFromD = spendsFromDynamicAttribute;
    }

    @Override
    public void setExtraDamage(DamageType type, IGGMAttribute baseAttribute, IGGMAttribute dynamicAttribute, float attributeMultiplier, float spendsFromDynamicAttribute) {

        Map<IGGMAttribute, UseSpendings> map;

        if (this.damageValues != null) {

            map = this.damageValues.get(type);

            if (map == null) {
                map = new HashMap(1, 1.0F);
            }
            else {

                Map<IGGMAttribute, UseSpendings> old = map;
                map = new HashMap<>(old.size() + 1, 1.0F);
                map.putAll(old);
            }

            map.put(baseAttribute, new UseSpendings(dynamicAttribute, attributeMultiplier, spendsFromDynamicAttribute));
        }
        else {

            this.damageValues = new HashMap(1, 1.0F);
            map = new HashMap(1, 1.0F);
            map.put(baseAttribute, new UseSpendings(dynamicAttribute, attributeMultiplier, spendsFromDynamicAttribute));
        }
        this.damageValues.put(type, map);
    }


    @Override
    public void onAttack(IGGMEntityLivingBase attacker, Map<DamageType, Float> damageValues, IGGMEntity victim) {

    }


    @Override
    public Map<DamageType, Map<IGGMAttribute, UseSpendings>> getBlockersMap() {
        return null;
    }


    @Override
    public void setDamageBlocker(Map<DamageType, Map<IGGMAttribute, UseSpendings>> map) {

        if (this.blockerMap == null) {
            this.blockerMap = new HashMap<>(map.size(), 1.0F);
        }
        else {

            Map<DamageType, Map<IGGMAttribute, UseSpendings>> oldMap = this.blockerMap;
            this.blockerMap = new HashMap<>(oldMap.size() + map.size(), 1.0F);
            this.blockerMap.putAll(oldMap);
        }
        this.blockerMap.putAll(map);
    }

    @Override
    public void setDamageBlocker(DamageType damageType, IGGMAttribute attribute, IGGMAttribute dynamicAttriibute, float attributeMultiplier, float blocksFromDA) {

        Map<IGGMAttribute, UseSpendings> m = new HashMap<>(1, 1.0F);
        m.put(attribute, new UseSpendings(dynamicAttriibute, attributeMultiplier, blocksFromDA));
        Map<DamageType, Map<IGGMAttribute, UseSpendings>> map = new HashMap<>();
        this.setDamageBlocker(map);
    }


    @Override
    public void setSustainability(float sustainability) {
        this.sustainability = sustainability;
    }


    @Override
    public float getWeight() {
        return 0;
    }

    @Override
    public float getSustainability() {
        return this.sustainability;
    }


    @Redirect(method = "getItemAttributeModifiers", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/Multimap;put(Ljava/lang/Object;Ljava/lang/Object;)Z"))
    private boolean removeModifierPut(Multimap map, Object attributeName, Object modifier) {
        return false;
    }
}
