package mrfinger.gothicgamemod.entity.animals;

import mrfinger.gothicgamemod.GothicMain;
import net.minecraft.world.World;

public class EntityScavenger extends EntityGothicAnimal {	
		
	public EntityScavenger(World p_i1738_1_) {
		super(p_i1738_1_, true);
		this.herdAnimal = true;		
	}
	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		//System.out.println("YAW   "+this.rotationYaw);
		//System.out.println("Pitch "+this.rotationPitch);
	}
	
	@Override
	protected String getLivingSound() {
		if (this.getStareTimer() > 0) return null;
        return null;
    }
	
	@Override
	protected String aggrSound() {		
		return GothicMain.MODID+":scavenger_aggr_anim";
	}
	
	protected float getAMaxHealth() {
		return 20.0F;
	}
	
	protected float getAAttackDamage() {
		return 2.5F;
	}
	
	protected float getAMovementSpeed() {
		return 3.0F;
	}
	
	protected float getAKnockbackresistance() {
		return 0F;
	}
	
	protected float getAFollowRange() {
		return 16.0F;
	}
	
	protected float getAWidth() {
		return 0.8F;
	}
	
	protected float getAHeight() {
		return 1.5F;
	}

	@Override
	protected String attackSound() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	

}
