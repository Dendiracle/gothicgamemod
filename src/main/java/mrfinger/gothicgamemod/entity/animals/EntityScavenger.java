package mrfinger.gothicgamemod.entity.animals;

import mrfinger.gothicgamemod.GothicMain;
import mrfinger.gothicgamemod.client.model.ModelScavenger;
import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.animations.episodes.AbstractAnimationEpisode;
import mrfinger.gothicgamemod.entity.animations.episodes.AbstractAnimationHit;
import mrfinger.gothicgamemod.entity.animations.episodes.IAnimationEpisode;
import mrfinger.gothicgamemod.entity.capability.attributes.GGMDPAttributeInfo;
import mrfinger.gothicgamemod.entity.packentities.EntityGothicAnimal;
import mrfinger.gothicgamemod.entity.packentities.IEntityGothicAnimal;
import mrfinger.gothicgamemod.fractions.PackFraction;
import mrfinger.gothicgamemod.init.GGMBlocks;
import mrfinger.gothicgamemod.init.GGMCapabilities;
import mrfinger.gothicgamemod.init.GGMFractions;
import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class EntityScavenger extends EntityGothicAnimal
{

	public static final Map<String, IAnimationEpisode> AnimationEpisodesMap = new HashMap<>();
	public static boolean keks = false;

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
	public Map<String, IAnimationEpisode> getAnimationEpisodesMap()
	{
		return AnimationEpisodesMap;
	}

	@Override
	public IAnimationEpisode getRandomJustLivingEpisode()
	{
		int i = this.rand.nextInt(1);

		switch (i)
		{
			case 0:
				return ScavChildbirth0;
		}

		return null;
	}


	@Override
	public boolean startAttack(IAnimationEpisode hitType)
	{
		boolean b = super.startAttack(hitType);

		if (b)
		{
			this.playSound(GothicMain.MODID + ":scavenger_attack", 1.0F, 1.0F);
		}

		return b;
	}

	@Override
	public IAnimationEpisode getHitAnimation(float distance)
	{
		return distance > 3.0F ? ScavHitOnRun : ScavHit0;
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
		keks = !keks;
		return keks;
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


	public static void loadAnimations()
	{
		AnimationEpisodesMap.put(EntityScavenger.ScavLiving0.getUnlocalizedName(), EntityScavenger.ScavLiving0);
		AnimationEpisodesMap.put(ScavChildbirth0.getUnlocalizedName(), ScavChildbirth0);
		AnimationEpisodesMap.put(ScavHit0.getUnlocalizedName(), ScavHit0);
		AnimationEpisodesMap.put(ScavHitOnRun.getUnlocalizedName(), ScavHitOnRun);
	}



	public static final AbstractAnimationEpisode ScavLiving0 = new AbstractAnimationEpisode("Scav_Living0", 100)
	{
		@Override
		public float getCulminationTickMultiplier()
		{
			return 0.1F;
		}


		@Override
		public void updateModel(IGGMEntityLivingBase entity, ModelBase model, float progress)
		{
			ModelScavenger mdl = (ModelScavenger) model;

			mdl.updateAnimationEat(entity, this, progress);
		}

		@Override
		public void onCulmination(IGGMEntityLivingBase entity, int duration, int count, byte series)
		{
			if (entity.isClientWorld()) ((EntityScavenger) entity).changeGrowth(1);
		}
	};

	public static final AbstractAnimationEpisode ScavChildbirth0 = new AbstractAnimationEpisode("Scav_Childbirth0", 60)
	{
		@Override
		public float getCulminationTickMultiplier()
		{
			return 0.4F;
		}


		@Override
		public void updateModel(IGGMEntityLivingBase entity, ModelBase model, float progress)
		{
			ModelScavenger mdl = (ModelScavenger) model;

			mdl.updateAnimationSitting(progress);
		}

		@Override
		public void onCulmination(IGGMEntityLivingBase entity, int duration, int count, byte series)
		{
			((IEntityGothicAnimal) entity).birthChild();
		}
	};

	public static final AbstractAnimationHit ScavHit0 = new AbstractAnimationHit("Scav_Hit0", 10, 0.5F)
	{
		@Override
		public void updateModel(IGGMEntityLivingBase entity, ModelBase model, float progress)
		{
			if (entity.getCurrentAnimation().getEpisodeCount() > 0) ((ModelScavenger) model).updateAnimationHit(progress);
		}

		@Override
		public void onCulmination(IGGMEntityLivingBase entity, int duration, int count, byte attackSeries)
		{
			if (entity.isClientWorld())
			{
				EntityScavenger scavenger = (EntityScavenger) entity;

				if (scavenger.getDistanceSqToEntity(scavenger.getAttackTarget()) < 3.0D)
				{
					scavenger.attackEntityAsMob(scavenger.getAttackTarget());
				}
			}
		}
	};

	public static final AbstractAnimationHit ScavHitOnRun = new AbstractAnimationHit("Scav_Hit_OnRun", 20, 0.4F)
	{
		@Override
		public void updateModel(IGGMEntityLivingBase entity, ModelBase model, float progress)
		{
			if (entity.getCurrentAnimation().getEpisodeCount() > 0) ((ModelScavenger) model).updateAnimationHit(progress);
		}

		@Override
		public void onUpdate(IGGMEntityLivingBase entity, int duration, int count)
		{
			if (entity.getCurrentAnimation().getEpisodeCount() > 0)
			{
				entity.getCurrentAnimation().setMoveControl((float) entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue(), 0F);
				entity.getCurrentAnimation().setRotationControl(entity.getRotationYaw(), 0F);
			}
		}

		@Override
		public void onCulmination(IGGMEntityLivingBase entity, int duration, int count, byte attackSeries)
		{
			if (entity.isClientWorld())
			{
				EntityScavenger scavenger = (EntityScavenger) entity;

				if (scavenger.getDistanceSqToEntity(scavenger.getAttackTarget()) < 3.0D)
				{
					scavenger.attackEntityAsMob(scavenger.getAttackTarget());
				}
			}
		}
	};

}
