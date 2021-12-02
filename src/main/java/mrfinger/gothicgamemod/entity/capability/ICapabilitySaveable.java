package mrfinger.gothicgamemod.entity.capability;

import net.minecraft.nbt.NBTTagCompound;

public interface ICapabilitySaveable
{

    void saveNBTData(NBTTagCompound nbt);

    void loadNBTData(NBTTagCompound nbt);

}