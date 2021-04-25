package mrfinger.gothicgamemod.entity.capability.abilities;

import mrfinger.gothicgamemod.util.Packet;
import net.minecraft.nbt.NBTTagCompound;

/*public class Switchable implements IGothicAbility {
	
	private boolean active;
	
	private boolean canActivate;
	
	public final String name;
	
	
	
	public Switchable(String name) {
		this.name = name;
	}
	
	
	
	
	
	@Override
	public void saveAbilities(NBTTagCompound compound) {
		compound.setBoolean("Activity", this.active);
		compound.setBoolean("canActivitate", this.canActivate);
	}

	@Override
	public void loadAbilities(NBTTagCompound compound) {
		this.setCanActivate(compound.getBoolean("canActivitate"));
		this.setActivity(compound.getBoolean("Activity"));
	}
	
	
	
	
	
	@Override
	//@SideOnly(Side.CLIENT)
	public void requestForChangeActivity() {
		NBTTagCompound compound = new NBTTagCompound();
		compound.setString("name", this.name);
		Packet.createPacket(Packet.sAbilitySwitch).writeNBTTagCompound(compound).sendToServer();
	}
	
	@Override
	public void setActivity(boolean activity) {
		this.active = this.canActivate ? activity : false;
	}
	
	@Override
	public void setCanActivate(boolean canActivate) {
		this.canActivate = canActivate;
		if (!canActivate) this.active = false;
	}
	
	@Override
	public void switchActivity() {
		System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB   " + this.active + this.canActivate);
		this.setActivity(this.active ? false : true);
	}
	
	
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public boolean isActive() {
		return active;
	}	
	
	@Override
	public boolean isCanActivate() {
		return canActivate;
	}




	
	
}*/
