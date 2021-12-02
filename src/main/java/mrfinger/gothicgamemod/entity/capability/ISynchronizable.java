package mrfinger.gothicgamemod.entity.capability;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;

public interface ISynchronizable {	
	

	@SideOnly(Side.CLIENT)
	void loadFromPacketCustom(NBTTagCompound compound);

}
