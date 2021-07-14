package mrfinger.gothicgamemod.entity.animals;

import mrfinger.gothicgamemod.GothicMain;
import mrfinger.gothicgamemod.block.BlockAnimalEggs;
import mrfinger.gothicgamemod.client.model.ModelAnimal;
import mrfinger.gothicgamemod.client.model.ModelScavenger;
import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.animations.episodes.AbstractAnimationEpisodeWithDur;
import mrfinger.gothicgamemod.entity.animations.episodes.AbstractAnimationEpisodeWithDurAndMultiplier;
import mrfinger.gothicgamemod.entity.animations.episodes.IAnimationEpisode;
import mrfinger.gothicgamemod.entity.animations.episodes.IAnimationHit;
import mrfinger.gothicgamemod.entity.capability.attributes.GGMDPAttributeInfo;
import mrfinger.gothicgamemod.entity.packentities.EntityGothicAnimal;
import mrfinger.gothicgamemod.entity.packentities.IEntityGothicAnimal;
import mrfinger.gothicgamemod.fractions.PackFraction;
import mrfinger.gothicgamemod.init.GGMBlocks;
import mrfinger.gothicgamemod.init.GGMCapabilities;
import mrfinger.gothicgamemod.init.GGMFractions;
import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class EntityScavenger extends EntityGothicAnimal
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

		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
		this.setSize(0.5F, 1.2F);
	}

	@Override
	protected GGMDPAttributeInfo getNewStaminaAI() {
		return GGMCapabilities.maxStaminaAIScavenger;
	}

	@Override
	protected int addTasks(int priority)
	{
		this.tasks.addTask(priority++, new EntityAISwimming(this));
		return super.addTasks(priority);
	}


	@Override
	public double getWanderSpeedModifier()
	{
		return 0.5D;
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
	protected String getLivingSound() {
		return GothicMain.MODID + ":scavenger_living";
	}


	@Override
	public float getAttackRangeSquare()
	{
		return 2.25F;
	}

	@Override
	public boolean startAttack(IAnimationHit hitType)
	{
		if (super.startAttack(hitType))
		{
			this.playSound(GothicMain.MODID + ":scavenger_attack", 1.0F, 1.0F);
			return true;
		}

		return false;
	}


	@Override
	public int getNewBornGrowthAge() {
		return -72;
	}

	@Override
	public int getMaxGrowth() {
		return 24;
	}

	@Override
	public int getChildBirthNeedsGrowth() {
		return 12;
	}

	@Override
	public boolean isViviparous()
	{
		return false;
	}

	@Override
	public BlockAnimalEggs getBlockEgg()
	{
		return GGMBlocks.scavengerEgg;
	}

	@Override
	protected int getEggHatchingTime()
	{
		return 100;
	}

	@Override
	protected int getEggDestroyAfterHathingTime()
	{
		return 100;
	}

	@Override
	public float getStandartWidth() {
		return 0.5F;
	}

	@Override
	public float getStandartHeight() {
		return 1.2F;
	}


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
