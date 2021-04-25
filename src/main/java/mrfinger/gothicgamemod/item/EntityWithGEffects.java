package mrfinger.gothicgamemod.item;

import java.util.ArrayList;
import java.util.List;

import mrfinger.gothicgamemod.entity.capability.effects.GEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class EntityWithGEffects implements IExtendedEntityProperties {
		
	public static final String ID = "G_Effects";
		
	protected Entity entity;
	
	private List<GEffect> effectsList = new ArrayList<GEffect>();
		
	public EntityWithGEffects(Entity entity) {
		this.entity = entity;
	}
		
	public static final void register(Entity entity) {
		entity.registerExtendedProperties(EntityWithGEffects.ID, new EntityWithGEffects(entity));
	}
		
	public static final EntityWithGEffects get(EntityLivingBase target) {
		return (EntityWithGEffects) target.getExtendedProperties(ID);
	}

	@Override
	public void saveNBTData(NBTTagCompound compound) {
			
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
			
	}

	@Override
	public void init(Entity entity, World world) {
		
	}
	
	public void addEffect(GEffect effect) {
		this.effectsList.add(effect);
	}
	
	public void effectsTick() {
		for (int i = 0; i < this.effectsList.size(); i++) {
			if (this.effectsList.get(i).effectTick(this.entity)) {
				this.effectsList.remove(i);
				i--;
			}
		}
	}
	

}
