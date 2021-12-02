package mrfinger.gothicgamemod.entity.capability.attribute.attributeinfo;

import mrfinger.gothicgamemod.entity.capability.attribute.generic.IGGMAttribute;
import mrfinger.gothicgamemod.entity.capability.attribute.instance.IGGMAttributeInstance;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;

import java.util.Map;

public interface IAttributeInfo<Instance extends IGGMAttributeInstance>
{

    double getDefaultValue();

    double getMaxIncreasableValue();

    float getIncreasingValue();

    float getValueAfterIncreasingValueDecreases();

    byte getMaxIncreasesNeedLP();


    boolean hasBonus();

    Map<IGGMAttribute, Float> getAttributeModifiersMap();


    IAttributeInfo addBonus(IGGMAttribute attribute, float mul);

    IAttributeInfo addBonus(IAttribute attribute, float mul);

    IAttributeInfo setBonuses(Map<IGGMAttribute, Float> map);


    Instance getNewAttributeInstance(BaseAttributeMap attributeMap, IAttribute attribute);

}
