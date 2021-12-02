package mrfinger.gothicgamemod.entity.animals;

import mrfinger.gothicgamemod.GothicMain;
import net.minecraft.world.World;

public class EntityNikita extends EntityGothicAnimalOld {

	public EntityNikita(World world) {
		super(world, true);
		/*this.maxHealthDynamic = 400.0F;
		this.movementSpeed = 8.5F;
		this.knockbackResistance = 1.0F;
		this.attackDamage = 8.0F;
		this.aWidth = 1.0F;
		this.aHeight = 3.6F;*/
	}
	
	@Override
	protected String getLivingSound() {
		if (this.entityToAttack != null) return null;
        return GothicMain.MODID+":nikita_living";
    }
	
	@Override
	protected String aggrSound() {
		return GothicMain.MODID+":nikita_aggr_anim";
	}
	
	@Override
	protected String attackSound() {
		return GothicMain.MODID+":nikita_hit";
	}
	
	protected float getAMaxHealth() {
		return 100.0F;
	}
	
	protected float getAAttackDamage() {
		return 20.0F;
	}
	
	protected float getAMovementSpeed() {
		return 3.5F;
	}
	
	protected float getAKnockbackresistance() {
		return 1.0F;
	}
	
	protected float getAFollowRange() {
		return 20.0F;
	}
	
	protected float getAWidth() {
		return 0.9F;
	}
	
	protected float getAHeight() {
		return 3.5F;
	}

}
