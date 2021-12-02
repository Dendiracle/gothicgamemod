package mrfinger.gothicgamemod.entity.capability.attribute.attributeinfo;

import mrfinger.gothicgamemod.entity.capability.attribute.instance.IGGMDynamicAttributeInstance;

public interface IAttributeInfoDynamic<Instance extends IGGMDynamicAttributeInstance> extends IAttributeInfo<Instance>
{

    float getDefaultNaturalRegenValue();

    float getMaxNaturalRegenValue();

    float getNaturalRegenIncreasingValue();

    float getValueAfterNaturalRegenIncreasingValueDecreases();

    byte getMaxNaturalRegenIncreasesNeedLP();

}
