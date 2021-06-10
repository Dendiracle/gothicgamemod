package mrfinger.gothicgamemod.entity.animals;

import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.animations.episodes.IAnimationEpisode;
import mrfinger.gothicgamemod.entity.packentities.EntityHerd;
import mrfinger.gothicgamemod.fractions.PackFraction;
import mrfinger.gothicgamemod.init.GGMFractions;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class EntityScavenger extends EntityHerd
{

	public static final Map<String, IAnimationEpisode> livingEpisodesMap = new HashMap<>();


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

		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.4D);
		this.setSize(0.5F, 1.2F);
	}


	@Override
	public void onUpdate()
	{
		super.onUpdate();
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

	@Override
	public void tryAttack(IGGMEntity entity, float distance)
	{

	}


	@Override
	public Map<String, IAnimationEpisode> getLivingEpisodesMap()
	{
		return livingEpisodesMap;
	}

	int i;
	@Override
	public void writeEntityToNBT(NBTTagCompound p_70014_1_)
	{
		super.writeEntityToNBT(p_70014_1_);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt)
	{
		super.readEntityFromNBT(nbt);
	}

}
