package mrfinger.gothicgamemod.entity.capability.attribute.instance;

import mrfinger.gothicgamemod.entity.capability.attribute.modifier.IRegenModifierInstance;
import mrfinger.gothicgamemod.entity.capability.attribute.modifier.RegenModifier;

public interface IGGMDynamicAttributeInstance extends IGGMAttributeInstance
{

    double getDynamicValue();

    float getNaturalRegen();

    float getCachedRegenValue();


    void setDynamicValue(double value);

    void changeCurrentValue(double changeValue);

    void restore();

    void setNaturalRegen(float value);

    void changeNaturalRegen(float value);


    IRegenModifierInstance getRegenModifier(RegenModifier regenModifier);

    void applyRegenModifier(IRegenModifierInstance regenModifierInstance);

    IRegenModifierInstance removeRegenModifier(RegenModifier regenModifier);


    void updateRegen();

}
