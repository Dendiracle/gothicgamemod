package mrfinger.gothicgamemod.entity.animals;

import net.minecraft.world.World;

public class EntityGWolf extends EntityGothicAnimal {
	
	
	public EntityGWolf(World p_i1738_1_) {
		super(p_i1738_1_, true);
	}
	
	@Override
	protected void applyEntityAttributes() {		
	  super.applyEntityAttributes();
	}

	@Override
	protected String aggrSound() {
		return null;
	}

	@Override
	protected String attackSound() {
		return null;
	}

}
