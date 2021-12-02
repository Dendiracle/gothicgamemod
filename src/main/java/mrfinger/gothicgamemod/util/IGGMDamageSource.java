package mrfinger.gothicgamemod.util;

import mrfinger.gothicgamemod.battle.DamageType;

import java.util.Map;

public interface IGGMDamageSource {


    void setDamageValues(Map<DamageType, Float> map);

    boolean isSettedValues();

    Map<DamageType, Float> getDamageValuesMap();

}
