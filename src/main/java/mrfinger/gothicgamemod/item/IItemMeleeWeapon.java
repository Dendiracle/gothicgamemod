package mrfinger.gothicgamemod.item;

import mrfinger.gothicgamemod.battle.DamageType;
import mrfinger.gothicgamemod.battle.UseSpendings;
import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.capability.attributes.IGGMAttribute;
import net.minecraft.entity.ai.attributes.IAttribute;

import java.util.Map;

public interface IItemMeleeWeapon extends IItemEquip, IItemTool {


    Map<DamageType, UseSpendings> getDamageValuesMap();

    Map<DamageType, UseSpendings> getDamageValuesMapToRed();


    void setBaseDamage(DamageType type, float value);

    void setBaseDamage(DamageType type, IGGMAttribute baseAttribute, float attributeMultiplier,  float damage);

    void setExtraDamage(DamageType type, IGGMAttribute baseAttribute, float attributeMultiplier, float damage);


    void onAttack(IGGMEntityLivingBase attacker, Map<DamageType, Float> damageValues, IGGMEntity victim);

}
