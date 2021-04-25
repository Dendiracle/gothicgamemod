package mrfinger.gothicgamemod.entity.capability.magic.spells;

import mrfinger.gothicgamemod.entity.effects.EntityFireBolt;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class SpellFireBolt extends Spell {
	
	private World world;
	
	private EntityLivingBase entity;
	
	public SpellFireBolt () {
		
	}
	
	public SpellFireBolt (World world, EntityLivingBase entity) {
		this.world = world;
		this.entity = entity;
	}
	
	@Override
	public void createSpell() {		
		
		spawnFireBolt(this.world, this.entity);
		
	}

	@Override
	public void createSpell(World world, EntityLivingBase player) {
		
		spawnFireBolt(world, player);
		
	}

	public static void spawnFireBolt (World world, EntityLivingBase entity) {
		if (!world.isRemote) world.spawnEntityInWorld(new EntityFireBolt(world, entity));
	}

	

}
