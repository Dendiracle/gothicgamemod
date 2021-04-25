package mrfinger.gothicgamemod.entity.capability.effects;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;

public class DamageEffect extends GEffect{
	
	
	private final String ID;
	//private final EntityLivingBase entity;
	//private final EntityLivingBase damager;
	private final DamageSource source;
	
	
	
	private int ticks; 
	
	private float damagePerSec;
	
	
	public DamageEffect (String ID, DamageSource source) {
		this.ID = ID;
		this.source = source;
	}
	
	
	public DamageEffect setTime(int seconds) {
		this.ticks = seconds * 20 - 1;
		return this;
	}
	
	
	public DamageEffect setDamagePerSec(float damage) {					
		this.damagePerSec = damage;
		return this;		
	}
	
	@Override
	public boolean effectTick(Entity entity) {
		
		this.ticks--;
		
		if (this.ticks % 20 == 0) entity.attackEntityFrom(this.source , this.damagePerSec);
		
		if (this.ticks <= 0) {
			return true;
		}
		
		return false;		
	}
	
}
