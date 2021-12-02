package mrfinger.gothicgamemod.entity.capability.attribute.attributeinfo;

import mrfinger.gothicgamemod.entity.capability.attribute.generic.IGGMAttribute;
import mrfinger.gothicgamemod.entity.capability.attribute.instance.IGGMDynamicAttributeInstance;
import mrfinger.gothicgamemod.entity.capability.attribute.instance.GGMDynamicAttributeInstance;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;

public class DynamicIncreasableAttributeInfo<Instance extends IGGMDynamicAttributeInstance> extends IncreasableAttributeInfo<Instance> implements IAttributeInfoDynamic<Instance>
{

    protected final float   defaultNaturalRegenValue;
    protected final float   maxNaturalRegenValue;
    protected final float 	naturalRegenIncreasingValue;
    protected final float	valueAfterNaturalRegenIncreasingValueDecreases;
    protected final byte 	maxNaturalRegenIncreasesNeedLP;


    public DynamicIncreasableAttributeInfo(float defaultValue, double maxValue, float increasingValue, float valueAfterIncreasingValueDecreases, byte maxIncreasesNeedLP, float defaultNaturalRegenValue, float maxNaturalRegenValue, float naturalRegenIncreasingValue, float valueAfterNaturalRegenIncreasingValueDecreases, byte maxNaturalRegenIncreasesNeedLP) {
        super(defaultValue, maxValue, increasingValue, valueAfterIncreasingValueDecreases, maxIncreasesNeedLP);

        this.defaultNaturalRegenValue = defaultNaturalRegenValue;
        this.maxNaturalRegenValue = maxNaturalRegenValue;
        this.naturalRegenIncreasingValue = naturalRegenIncreasingValue;
        this.valueAfterNaturalRegenIncreasingValueDecreases = valueAfterNaturalRegenIncreasingValueDecreases;
        this.maxNaturalRegenIncreasesNeedLP = maxNaturalRegenIncreasesNeedLP;
    }

    public DynamicIncreasableAttributeInfo(float defaultValue, float defaultNaturalRegenValue) {
        this(defaultValue, defaultValue, 0.0F, 0.0F, (byte) 0, defaultNaturalRegenValue, defaultNaturalRegenValue, 0.0F, 0.0F, (byte) 0);
    }


    @Override
    public float getDefaultNaturalRegenValue()
    {
        return defaultNaturalRegenValue;
    }

    @Override
    public float getMaxNaturalRegenValue()
    {
        return maxNaturalRegenValue;
    }

    @Override
    public float getNaturalRegenIncreasingValue()
    {
        return naturalRegenIncreasingValue;
    }

    @Override
    public float getValueAfterNaturalRegenIncreasingValueDecreases()
    {
        return valueAfterNaturalRegenIncreasingValueDecreases;
    }

    @Override
    public byte getMaxNaturalRegenIncreasesNeedLP()
    {
        return maxNaturalRegenIncreasesNeedLP;
    }


    @Override
    public Instance getNewAttributeInstance(BaseAttributeMap attributeMap, IAttribute attribute)
    {
        return (Instance) new GGMDynamicAttributeInstance(attributeMap, (IGGMAttribute) attribute, this);
    }
}
