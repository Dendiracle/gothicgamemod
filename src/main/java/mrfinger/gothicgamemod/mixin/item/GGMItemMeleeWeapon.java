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
import mrfinger.gothicgamemod.item.equipment.IItemRequiring;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Mixin({ItemTool.class, ItemSword.class})
public abstract class GGMItemMeleeWeapon extends GGMItem implements IItemMeleeWeapon, IItemBlocker {


    protected Map<IAttribute, Float> requiredsMap;


    protected DamageType baseDamageType;

    protected IGGMAttribute baseAttribute;

    protected float baseAttributeMultiplier;

    protected float baseDamage;


    protected Map<DamageType, UseSpendings> damageValues;


    protected Map<DamageType, Map<IGGMAttribute, UseSpendings>> blockerMap;


    protected float weight;


    protected float sustainability;


    @Inject(method = "<init>*", at = @At("RETURN"))
    private void onInit(CallbackInfo ci)
    {
        this.requiredsMap = new HashMap<>();
        this.baseDamageType = GGMBattleSystem.cutting;
        this.baseAttribute = (IGGMAttribute) SharedMonsterAttributes.attackDamage;
        this.baseAttributeMultiplier = this.getDamageVsEntity() / 6.0F;
        this.baseDamage = this.getDamageVsEntity();
        this.blockerMap = new HashMap<>(GGMBattleSystem.standartDamageValuesBlockModifiers.size(), 1.0F);

        for (Map.Entry<DamageType, UseSpendings> e : GGMBattleSystem.standartDamageValuesBlockModifiers.entrySet())
        {
            Map<IGGMAttribute, UseSpendings> usMap = new HashMap<>(1, 1.0F);
            usMap.put(e.getValue().getAttribute(), new UseSpendings(GGMCapabilities.maxStamina, this.getDamageVsEntity() * e.getValue().getAttributeMultiplier(), e.getValue().getSpendsFromD()));
            this.blockerMap.put(e.getKey(), usMap);
        }

        this.weight = (float) this.getMaxDamage() / 100.0F;
        this.sustainability = this.weight * 10.0F;
    }


    @Override
    public Map<IAttribute, Float> getRequiredsMap()
    {
        return this.requiredsMap;
    }


    @Override
    public IItemMeleeWeapon setRequireds(Map<IAttribute, Float> requireds)
    {
        this.requiredsMap.putAll(requireds);
        return this;
    }


    @Override
    public boolean isMayEquip(IGGMEntityLivingBase entity)
    {
        for (Map.Entry<IAttribute, Float> e : this.requiredsMap.entrySet())
        {
            if (entity.getEntityAttribute(e.getKey()) == null || e.getValue() > entity.getEntityAttribute(e.getKey()).getBaseValue())
            {
                return false;
            }
        }

        return true;
    }


    @Override
    public Map<DamageType, UseSpendings> getDamageValuesMap()
    {
        Map<DamageType, UseSpendings> map;

        if (this.damageValues == null) {

            map = new HashMap<>(1, 1.0F);
        }
        else {

            map = new HashMap<>(this.damageValues.size() + 1, 1.0F);
            map.putAll(this.damageValues);
        }

        map.put(this.baseDamageType, new UseSpendings(this.baseAttribute, this.baseAttributeMultiplier, this.baseDamage));
        return map;
    }

    @Override
    public Map<DamageType, UseSpendings> getDamageValuesMapToRed()
    {
        return this.damageValues;
    }


    @Override
    public void setBaseDamage(DamageType type, float damage)
    {
        this.baseDamageType = type;
        this.baseDamage = damage;
    }

    @Override
    public void setBaseDamage(DamageType type, IGGMAttribute baseAttribute, float attributeMultiplier, float damage)
    {
        this.setBaseDamage(type, damage);
        this.baseAttribute = baseAttribute;
        this.baseAttributeMultiplier = attributeMultiplier;
    }

    @Override
    public void setExtraDamage(DamageType type, IGGMAttribute baseAttribute, float attributeMultiplier, float damage)
    {
        if (this.damageValues == null)
        {
            this.damageValues = new HashMap<>(1, 1.0F);
        }
        else
        {
            Map<DamageType, UseSpendings> old = this.damageValues;
            this.damageValues = new HashMap<>(old.size() + 1, 1.0F);
            this.damageValues.putAll(old);
        }

        this.damageValues.put(type, new UseSpendings(baseAttribute, attributeMultiplier, damage));
    }


    @Override
    public void onAttack(IGGMEntityLivingBase attacker, Map<DamageType, Float> damageValues, IGGMEntity victim) {}


    @Override
    public Map<DamageType, Map<IGGMAttribute, UseSpendings>> getBlockersMap() {
        return Collections.unmodifiableMap(this.blockerMap);
    }

    @Override
    public Map<DamageType, Map<IGGMAttribute, UseSpendings>> getBlockersMapToRed() {
        return this.blockerMap;
    }

    @Override
    public void setDamageBlocker(Map<DamageType, Map<IGGMAttribute, UseSpendings>> map)
    {
        for (Map.Entry<DamageType, Map<IGGMAttribute, UseSpendings>> e : map.entrySet())
        {
            this.blockerMap.get(e.getKey()).putAll(e.getValue());
        }
    }

    @Override
    public void setDamageBlocker(DamageType damageType, IGGMAttribute attribute, IGGMAttribute dynamicAttriibute, float attributeMultiplier, float blocksFromDA)
    {
        Map<IGGMAttribute, UseSpendings> m = new HashMap<>(1, 1.0F);
        m.put(attribute, new UseSpendings(dynamicAttriibute, attributeMultiplier, blocksFromDA));
        Map<DamageType, Map<IGGMAttribute, UseSpendings>> map = new HashMap<>();
        map.put(damageType, m);
        this.setDamageBlocker(map);
    }


    @Override
    public float getWeight() {
        return this.weight;
    }

    @Override
    public float getSustainability() {
        return this.sustainability;
    }


    @Override
    public void setWeight(float weight) {
        this.weight = weight;
    }

    @Override
    public void setSustainability(float sustainability) {
        this.sustainability = sustainability;
    }


    @Redirect(method = "getItemAttributeModifiers", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/Multimap;put(Ljava/lang/Object;Ljava/lang/Object;)Z"))
    private boolean removeModifierPut(Multimap map, Object attributeName, Object modifier) {
        return false;
    }

}
