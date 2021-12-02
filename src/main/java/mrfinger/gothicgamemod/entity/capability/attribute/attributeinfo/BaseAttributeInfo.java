package mrfinger.gothicgamemod.entity.capability.attribute.attributeinfo;

import mrfinger.gothicgamemod.entity.capability.attribute.generic.IGGMAttribute;
import mrfinger.gothicgamemod.entity.capability.attribute.instance.IGGMAttributeInstance;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;

import java.util.HashMap;
import java.util.Map;

public class BaseAttributeInfo<Instance extends IGGMAttributeInstance> implements IAttributeInfo<Instance>
{

    protected final double 	defaultValue;
    protected final double 	maxValue;

    protected Map<IGGMAttribute, Float> attributeModifiersMap;


    public BaseAttributeInfo(double defaultValue, double maxValue)
    {
        this.defaultValue = defaultValue;
        this.maxValue = maxValue;
    }


    @Override
    public double getDefaultValue() {
        return this.defaultValue;
    }

    @Override
    public double getMaxIncreasableValue() {
        return this.maxValue;
    }

    @Override
    public float getIncreasingValue() {
        return 0F;
    }

    @Override
    public float getValueAfterIncreasingValueDecreases() {
        return 0F;
    }

    @Override
    public byte getMaxIncreasesNeedLP() {
        return 0;
    }


    @Override
    public boolean hasBonus()
    {
        return this.attributeModifiersMap != null;
    }

    @Override
    public Map<IGGMAttribute, Float> getAttributeModifiersMap()
    {
        return this.attributeModifiersMap;
    }

    @Override
    public IAttributeInfo addBonus(IGGMAttribute attribute, float mul)
    {
        if (this.attributeModifiersMap == null)
        {
            this.attributeModifiersMap = new HashMap<>(1, 1F);
        }

        this.attributeModifiersMap.put(attribute, mul);

        return this;
    }

    @Override
    public IAttributeInfo addBonus(IAttribute attribute, float mul) {
        return this.addBonus((IGGMAttribute) attribute, mul);
    }

    @Override
    public IAttributeInfo setBonuses(Map map)
    {
        this.attributeModifiersMap = map;
        return this;
    }


    @Override
    public Instance getNewAttributeInstance(BaseAttributeMap attributeMap, IAttribute attribute)
    {
        return (Instance) new ModifiableAttributeInstance(attributeMap, attribute);
    }


}
