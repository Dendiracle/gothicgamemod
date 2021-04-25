package mrfinger.gothicgamemod.entity.animals;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;

public class EntityMeatBug extends EntityCreature{

	public EntityMeatBug(World p_i1594_1_) {
		super(p_i1594_1_);
	}
	
	@Override
	protected void applyEntityAttributes() {		
	  super.applyEntityAttributes();
	  this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(5.0F);
	  this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.001F);
	  this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0F);
	  this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(0F); 
	  
	  this.setSize(0.3F, 0.15F);
	}

	

}
