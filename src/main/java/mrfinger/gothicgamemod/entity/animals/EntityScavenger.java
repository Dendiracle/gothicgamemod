package mrfinger.gothicgamemod.entity.animals;

import mrfinger.gothicgamemod.entity.packentities.EntityHerd;
import mrfinger.gothicgamemod.fractions.PackFraction;
import mrfinger.gothicgamemod.init.GGMFractions;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class EntityScavenger extends EntityHerd
{
		
	public EntityScavenger(World world)
	{
		super(world);

	}


	@Override
	public PackFraction getStandartFraction()
	{
		return GGMFractions.scavengers;
	}


	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.9D);
		this.setSize(0.5F, 0.9F);
	}


	@Override
	public float getBlockPathWeight(int x, int y, int z)
	{
		Block block = this.worldObj.getBlock(x, y - 1, z);

		if (block == Blocks.grass)
		{
			return 10.0F;
		}
		else if (block == Blocks.dirt)
		{
			return 8.0F;
		}

		return -1.0F;
	}
}
