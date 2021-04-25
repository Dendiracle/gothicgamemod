package mrfinger.gothicgamemod.item;

import mrfinger.gothicgamemod.battle.DamageType;
import mrfinger.gothicgamemod.battle.UseSpendings;
import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.capability.attributes.IGGMAttribute;
import net.minecraft.entity.ai.attributes.IAttribute;

import java.util.Map;

public interface IItemMeleeWeapon extends IItemEquip {


    Map<DamageType, Map<IGGMAttribute, UseSpendings>> getDamageValuesMap();

    Map<DamageType, Map<IGGMAttribute, UseSpendings>> getDamageValuesMapToRed();


    void setBaseDamage(DamageType type, float value);

    void setBaseDamage(DamageType type, IGGMAttribute baseAttribute, IGGMAttribute dynamicAttribute, float value, float modifier);

    void setExtraDamage(DamageType type, IGGMAttribute baseAttribute, IGGMAttribute dynamicAttribute, float attributeMultiplier, float spendsFromDynamicAttribute);


    void onAttack(IGGMEntityLivingBase attacker, Map<DamageType, Float> damageValues, IGGMEntity victim);

}
