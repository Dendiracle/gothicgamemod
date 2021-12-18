package mrfinger.gothicgamemod.entity.animals;

import mrfinger.gothicgamemod.GothicMain;
import mrfinger.gothicgamemod.block.BlockAnimalEggs;
import mrfinger.gothicgamemod.entity.animation.episodes.IAnimationHit;
import mrfinger.gothicgamemod.entity.capability.attribute.attributeinfo.IAttributeInfoDynamic;
import mrfinger.gothicgamemod.entity.packentities.EntityGothicAnimal;
import mrfinger.gothicgamemod.fractions.PackFraction;
import mrfinger.gothicgamemod.init.GGMBlocks;
import mrfinger.gothicgamemod.init.GGMCapabilities;
import mrfinger.gothicgamemod.init.GGMFractions;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

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
	}

	@Override
	protected IAttributeInfoDynamic getNewStaminaAI()
	{
		return GGMCapabilities.maxStaminaAIScavenger;
	}

	@Override
	protected int addTasks(int priority)
	{
		this.tasks.addTask(priority++, new EntityAISwimming(this));
		return super.addTasks(priority);
	}

	@Override
	protected String getEatSound()
	{
		return GothicMain.MODID + ":scavenger_eat";
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
	protected String getLivingSound()
	{
		return this.getActiveAnimation() == null ? GothicMain.MODID + ":scavenger_living" : null;
	}

	@Override
	protected String getWarnSound()
	{
		return GothicMain.MODID + ":scavenger_warn";
	}

	@Override
	protected String getHurtSound()
	{
		return GothicMain.MODID + ":scavenger_hurt";
	}

	@Override
	protected String getDeathSound()
	{
		return GothicMain.MODID + ":scavenger_die";
	}


	@Override
	public int getNewBornGrowthAge() {
		return -480;
	}

	@Override
	public int getMaxGrowth() {
		return 144;
	}

	@Override
	public int getChildBirthNeedsGrowth() {
		return 72;
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
		return 12000;
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

}
