package mrfinger.gothicgamemod.entity.capability.data;

import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.capability.attribute.instance.IGGMDynamicAttributeInstance;

public interface IGGMEntityCaster extends IGGMEntityLivingBase
{

    IGGMDynamicAttributeInstance getManaAttribute();

}
