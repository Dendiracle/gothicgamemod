package mrfinger.gothicgamemod.entity.capability.attributes;

import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface IGGMBaseAttributeMap
{

    IAttributeInstance getAttributeInstance(IAttribute attribute);

    IAttributeInstance getAttributeInstanceByName(String unlocalizedName);


    IAttributeInstance registerAttribute(IAttribute attribute);

    IGGMIncreasableAttributeInstance registerAttribute(IGGMAttribute attribute, GGMIncreasableAttributeInfo attributeInfo);

    IGGMBaseAttributeMap resizeCollections();


    Map<IAttribute, IAttributeInstance> getAllAttributesMap();

    Collection getAllAttributes();


    IGGMDynamicAttributeInstance getDPI(IGGMAttribute attribute);

    Collection<IGGMDynamicAttributeInstance> getDPIColl();

    Map<IAttribute, IGGMDynamicAttributeInstance> getDpiMap();


    void addDPToUpdate(IGGMDynamicAttributeInstance dp);

    Set<IGGMDynamicAttributeInstance> getToUpdateSet();
}
