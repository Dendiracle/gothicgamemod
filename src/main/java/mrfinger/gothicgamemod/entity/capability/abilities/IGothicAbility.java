package mrfinger.gothicgamemod.entity.capability.abilities;

import net.minecraft.nbt.NBTTagCompound;

public interface IGothicAbility {
	
	void saveAbilities(NBTTagCompound compound);
	
	void loadAbilities(NBTTagCompound compound);
	
	
	
	
	
	void requestForChangeActivity();
	
	void setActivity(boolean activity);
	
	void setCanActivate(boolean canActivate);
	
	void switchActivity();
	
	
	
	
	
	String getName();
	
	boolean isActive();
	
	boolean isCanActivate();
	
}
