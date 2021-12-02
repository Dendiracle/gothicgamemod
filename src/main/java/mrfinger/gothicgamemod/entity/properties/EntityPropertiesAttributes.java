package mrfinger.gothicgamemod.entity.properties;

import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.capability.attribute.instance.IGGMAttributeInstance;
import mrfinger.gothicgamemod.entity.capability.attribute.attributeinfo.IAttributeInfo;
import net.minecraft.entity.ai.attributes.IAttribute;

import java.util.Collection;
import java.util.Map;

public class EntityPropertiesAttributes implements IEntityProperties
{

    protected Map<IAttribute, IAttributeInfo> attributeInfoMap;


    public EntityPropertiesAttributes(Map<IAttribute, IAttributeInfo> attributeInfoMap)
    {
        this.attributeInfoMap = attributeInfoMap;
    }


    @Override
    public void setProperties(IGGMEntityLivingBase entity)
    {
        for (IGGMAttributeInstance attributeInstance : (Collection<IGGMAttributeInstance>) entity.getAttributeMap().getAllAttributes())
        {
            IAttributeInfo attributeInfo = this.attributeInfoMap.get(attributeInstance.getAttribute());

            if (attributeInfo != null)
            {
                attributeInstance.setParametrs(attributeInfo);
            }
        }
    }
}
