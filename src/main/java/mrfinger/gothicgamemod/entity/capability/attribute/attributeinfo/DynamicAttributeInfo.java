package mrfinger.gothicgamemod.entity.capability.attribute.attributeinfo;

import mrfinger.gothicgamemod.entity.capability.attribute.generic.IGGMAttribute;
import mrfinger.gothicgamemod.entity.capability.attribute.instance.IGGMDynamicAttributeInstance;
import mrfinger.gothicgamemod.entity.capability.attribute.instance.GGMDynamicAttributeInstance;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;

public class DynamicAttributeInfo<Instance extends IGGMDynamicAttributeInstance> extends BaseAttributeInfo<Instance> implements IAttributeInfoDynamic<Instance>
{

    protected final float   defaultNaturalRegenValue;
    protected final float   maxNaturalRegenValue;


    public DynamicAttributeInfo(double defaultValue, double maxValue, float defaultNaturalRegenValue, float maxNaturalRegenValue) {
        super(defaultValue, maxValue);

        this.defaultNaturalRegenValue = defaultNaturalRegenValue;
        this.maxNaturalRegenValue = maxNaturalRegenValue;
    }

    public DynamicAttributeInfo(double defaultValue, float defaultNaturalRegenValue)
    {
        this(defaultValue, defaultValue, defaultNaturalRegenValue, defaultNaturalRegenValue);
    }


    @Override
    public float getDefaultNaturalRegenValue() {
        return this.defaultNaturalRegenValue;
    }

    @Override
    public float getMaxNaturalRegenValue() {
        return Float.MAX_VALUE;
    }

    @Override
    public float getNaturalRegenIncreasingValue() {
        return 0F;
    }

    @Override
    public float getValueAfterNaturalRegenIncreasingValueDecreases() {
        return 0F;
    }

    @Override
    public byte getMaxNaturalRegenIncreasesNeedLP() {
        return 0;
    }

}
