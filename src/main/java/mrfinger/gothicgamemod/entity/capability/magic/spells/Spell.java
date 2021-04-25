package mrfinger.gothicgamemod.entity.capability.magic.spells;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public abstract class Spell {
	
	public Spell() {
		
	}
	
	public abstract void createSpell();
	
	public abstract void createSpell(World world, EntityLivingBase entity);
	
}
