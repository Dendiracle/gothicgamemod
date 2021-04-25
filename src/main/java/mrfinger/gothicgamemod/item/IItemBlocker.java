package mrfinger.gothicgamemod.item;

import mrfinger.gothicgamemod.battle.UseSpendings;
import mrfinger.gothicgamemod.battle.DamageType;
import mrfinger.gothicgamemod.entity.capability.attributes.IGGMAttribute;

import java.util.Map;

public interface IItemBlocker extends IItemEquip {


    Map<DamageType, Map<IGGMAttribute, UseSpendings>> getBlockersMap();


    void setDamageBlocker(Map<DamageType, Map<IGGMAttribute, UseSpendings>> map);

    void setDamageBlocker(DamageType damageType, IGGMAttribute attribute, IGGMAttribute dynamicAttriibute, float attributeMultiplier, float blocksFromDA);

    void setSustainability(float value);

}
