package mrfinger.gothicgamemod.entity.capability.attributes;

import net.minecraft.entity.ai.attributes.AttributeModifier;

import java.util.UUID;

public interface IGGMDynamicAttributeInstance extends IGGMIncreasableAttributeInstance {


    @Override
    GGMDPAttributeInfo getAttributeInfo();

    double getCurrentValue();

    float getNaturalRegen();

    float getCachedRegenValue();


    float getDefaultNaturalRegenValue();

    float getMaxNaturalRegenValue();

    float getNaturalRegenIncreasingValue();


    void setCurrentValue(double value);

    void changeCurrentValue(double changeValue);

    void restore();

    void setNaturalRegen(float value);

    void changeNaturalRegen(float value);


    float increaseNaturalRegen(float value);

    int increaseNaturalRegen(int amount);


    float calculateIncreasingRegenValue(float value);

    float calculateIncreasingRegenValueWithAdded(float added, float value);


    RegenModifier getRegenModifier(int id, int operation, boolean isSaved);

    RegenModifier getRegenModifierByFullID(int id);

    RegenModifier applyRegenModifier(RegenModifier modifier);

    RegenModifier removeRegenModifier(int id, int operation, boolean isSaved);

    RegenModifier removeRegenModifierByFullID(int id);

    RegenModifier removeRegenModifier(RegenModifier modifier);


    void onUpdate();

    void updateRegen();

}
