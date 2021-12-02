package mrfinger.gothicgamemod.entity.capability.attribute.modifier;

import mrfinger.gothicgamemod.entity.capability.attribute.instance.GGMDynamicAttributeInstance;
import net.minecraft.nbt.NBTTagCompound;

public interface IRegenModifierInstance
{

    RegenModifier getGenericRegenModifier();


    float getAddedValue(GGMDynamicAttributeInstance attributeInstance, float calculatedValue);


    boolean isSaved();

    String getUniqueClassID();

    void saveNBTData(NBTTagCompound nbt);

    void loadNBTData(NBTTagCompound nbt);

}
