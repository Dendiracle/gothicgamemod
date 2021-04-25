package mrfinger.gothicgamemod.entity.animals;

import net.minecraft.world.World;

public class EntityGiantRat extends EntityGothicAnimal {

	public EntityGiantRat(World world) {
		super(world, true);
	}
	
	@Override
	protected String getLivingSound() {
		if (this.getStareTimer() > 0) return null;
        return null;
    }
	
	@Override
	protected String aggrSound() {		
		return null;
	}

	@Override
	protected float getAMaxHealth() {
		return 15.0F;
	}

	@Override
	protected float getAAttackDamage() {
		return 5.0F;
	}

	@Override
	protected float getAMovementSpeed() {
		return 0.5F;
	}

	@Override
	protected float getAKnockbackresistance() {
		return 0F;
	}

	@Override
	protected float getAFollowRange() {
		return 16.0F;
	}

	@Override
	protected float getAWidth() {
		return 0.8F;
	}

	@Override
	protected float getAHeight() {
		return 0.5F;
	}

	@Override
	protected String attackSound() {
		return null;
	}

}
