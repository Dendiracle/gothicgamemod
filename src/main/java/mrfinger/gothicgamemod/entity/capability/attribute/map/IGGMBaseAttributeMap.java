package mrfinger.gothicgamemod.entity.capability.attribute.map;

import com.google.common.collect.Multimap;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;

import java.util.Collection;

public interface IGGMBaseAttributeMap
{

    IGGMEntityLivingBase getEntity();

    void setEntity(IGGMEntityLivingBase entity);


    IAttributeInstance getAttributeInstance(IAttribute attribute);

    IAttributeInstance getAttributeInstanceByName(String unlocalizedName);


    IAttributeInstance registerAttribute(IAttribute attribute);

    default void resizeCollections() {}


    Collection getAllAttributes();


    void addAttributeInstance(ModifiableAttributeInstance modifiableAttributeInstance);


    void applyAttributeModifiers(Multimap map);

    void removeAttributeModifiers(Multimap map);

}
