package mrfinger.gothicgamemod.entity.capability.attribute;

import mrfinger.gothicgamemod.entity.capability.attribute.attributeinfo.IAttributeInfo;
import mrfinger.gothicgamemod.entity.capability.attribute.generic.IGGMAttribute;
import mrfinger.gothicgamemod.entity.capability.attribute.instance.IGGMAttributeInstance;
import mrfinger.gothicgamemod.entity.capability.attribute.map.IGGMBaseAttributeMap;
import net.minecraft.entity.ai.attributes.*;

import java.util.*;
/*
public class GGMGothicAttributeMap extends ServersideAttributeMap implements IGGMBaseAttributeMap
{


    protected void afterRegisterAttribute(IGGMAttributeInstance attributeInstance, IAttributeInfo attributeInfo)
    {
        if (attributeInfo.hasBonus())
        {
            UUID id = ((IGGMAttribute) attributeInstance.getAttribute()).getUUID();
            Map<IGGMAttribute, Float> map = attributeInfo.getAttributeModifiersMap();
            double attributeValue = attributeInstance.getAttributeValue();

            for (Map.Entry<IGGMAttribute, Float> entry : map.entrySet())
            {
                ((IGGMAttributeInstance) this.attributes.get(entry.getKey())).applyModifier(new AttributeModifier(id, "AttBonus", attributeValue * entry.getValue(), 0).setSaved(false));
            }
        }
    }



    @Override
    public void addAttributeInstance(ModifiableAttributeInstance attributeInstance)
    {
        super.addAttributeInstance(attributeInstance);

        IAttributeInfo attributeInfo = this.getAttributeInfo(attributeInstance.getAttribute());

        if (attributeInfo.hasBonus())
        {
            UUID id = ((IGGMAttribute) attributeInstance.getAttribute()).getUUID();
            Map<IGGMAttribute, Float> map = attributeInfo.getAttributeModifiersMap();
            double attributeValue = attributeInstance.getAttributeValue();

            for (Map.Entry<IGGMAttribute, Float> entry : map.entrySet())
            {
                ((IGGMAttributeInstance) this.attributes.get(entry.getKey())).setModifierAmount(id, attributeValue * entry.getValue());
            }
        }
    }

}
*/