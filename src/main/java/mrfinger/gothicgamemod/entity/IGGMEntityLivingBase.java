package mrfinger.gothicgamemod.entity;

import mrfinger.gothicgamemod.entity.animation.IAnimationPlayer;
import mrfinger.gothicgamemod.entity.animation.instance.IAnimation;
import mrfinger.gothicgamemod.entity.animation.episodes.IAnimationEpisode;
import mrfinger.gothicgamemod.entity.capability.attribute.instance.IGGMAttributeInstance;
import mrfinger.gothicgamemod.entity.effect.IGGMEffectManager;
import mrfinger.gothicgamemod.entity.effect.instance.IGGMEffectInstance;
import mrfinger.gothicgamemod.entity.properties.IEntityProperties;
import mrfinger.gothicgamemod.fractions.Fraction;
import mrfinger.gothicgamemod.init.GGMFractions;
import mrfinger.gothicgamemod.mixin.common.entity.IGGMEntityLivingBaseAccessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;

import java.util.Map;

public interface IGGMEntityLivingBase extends IGGMEntityLivingBaseAccessor, IAnimationPlayer
{

	default Fraction getCurrentFraction()
	{
		return this.getStandartFraction();
	}

	default Fraction getStandartFraction()
	{
		return GGMFractions.neutralFraction;
	}


	default void restoreCurrentValuesFull()	{}


	default IGGMAttributeInstance getEntityAttributeInstance(IAttribute attribute)
	{
		return null;
	}

	default IGGMAttributeInstance getEntityAttributeInstance(String name)
	{
		return null;
	}

	/*
	 * Responsible for the level at init.
	 */
	default int initialLevel()
	{
		return 0;
	}

	default int getLvl()
	{
		return 0;
	}

	default void setLvl(int lvl)
	{
		this.onSetLvl();
	}

	default void onSetLvl() {}

	default int getGainingExperience()
	{
		return 0;
	}


	default void setEntityProperties(IEntityProperties properties) {}


	default void moveUpdate() {}


	@Override
	default IAnimation getActiveAnimation()
	{
		return null;
	}

	@Override
	default boolean tryEndAnimation()
	{
		return true;
	}

	@Override
	default void setActiveAnimationDirectly(IAnimation animation) {}

	@Override
	default void clearAnimation() {}


	default Map<String, IAnimationEpisode> getAnimationEpisodesMap()
	{
		return null;
	}


	default void flagForAnimSync() {}

	default boolean checkToNeedSyncAnimation()
	{
		return false;
	}


	default void flagForLvlUpdate() {}

	default boolean checkNeedExpUpdate()
	{
		return false;
	}


	default int getMaxAir()
	{
		return 300;
	}


	default IGGMEffectInstance getEffect(IGGMEffectManager effect)
	{
		return null;
	}

	default IGGMEffectInstance applyEffect(IGGMEffectInstance effectInstance)
	{
		return null;
	}

	default IGGMEffectInstance removeEffect(IGGMEffectManager effect)
	{
		return null;
	}

	default Map<IGGMEffectManager, IGGMEffectInstance> getEffectsMap()
	{
		return null;
	}

	/*default Map<IGGMEffectManager, IGGMEffectInstance> getBattleEffectsMap()
	{
		return null;
	}

	default Map<IGGMEffectManager, IGGMEffectInstance> getOtherEffectsMap()
	{
		return null;
	}
*/

	default IGGMAttributeInstance getHealthAttribute()
	{
		return null;
	}

	default double getHealtH()
	{
		return ((EntityLivingBase) this).getHealth();
	}

	default double getMaxHealtH()
	{
		return ((EntityLivingBase) this).getMaxHealth();
	}

	default void setHealth(double health)
	{
		((EntityLivingBase) this).setHealth((float) health);
	}

	default void changeHealth(double value)
	{
		((EntityLivingBase) this).setHealth((float) (this.getHealtH() + value));
	}


	default void increaseAttribute(IAttribute attribute, float value) {}


	default boolean isEntityLookingFor(Entity target, float deltaLookYaw, float deltaLookPitch)
	{
		return this.isEntityLookingFor(target.posX, target.posY, target.posZ, deltaLookYaw, deltaLookPitch);
	}

	default boolean isEntityLookingFor(double posX, double posY, double posZ, float deltaLookYaw, float deltaLookPitch)
	{
		posX = posX - ((EntityLivingBase) this).posX;
		posZ = posZ - ((EntityLivingBase) this).posZ;
		float f = MathHelper.wrapAngleTo180_float(((float) (Math.atan2(posZ, posX)) * 57.2957795131F) - 90.0F - ((EntityLivingBase) this).rotationYawHead);
		float f1 = MathHelper.wrapAngleTo180_float(-((float)Math.atan2(posY - ((EntityLivingBase) this).posY + ((EntityLivingBase) this).getEyeHeight(), MathHelper.sqrt_double(posX * posX + posZ * posZ)) * 57.2957795131F) - ((EntityLivingBase) this).rotationPitch);

		return (f == 0F || f > 0F && f < deltaLookYaw || f < 0F && f > -deltaLookYaw) && (f1 == 0F || f1 > 0F && f1 < deltaLookPitch || f1 < 0F && f1 > -deltaLookPitch);
	}


	default boolean isCanDropItems()
	{
		return true;
	}

	default boolean isCanDigging()
	{
		return true;
	}

	default boolean isCanUsingItems()
	{
		return true;
	}


	default float getStandartRotationSpeed()
	{
		return 30.0F;
	}


	default boolean isCanJump()
	{
		return this.getActiveAnimation() == null || this.getActiveAnimation().allowJump();
	}

	default void onJumped()
	{
		for (IGGMEffectInstance effect : this.getEffectsMap().values())
		{
			effect.onJumped();
		}

		if (this.getActiveAnimation() != null) this.getActiveAnimation().onJumped();
	}

	default double getJumpBaseHeight()
	{
		return 0.41999998688697815D;
	}


	default boolean isCanSprint()
	{
		return true;
	}


	default float waterMovementModifier()
	{
		return 0.800000011920929F;
	}


	default boolean isEntitySleeping()
	{
		return false;
	}

	default void wakeUpEntity() {}


	default int getTotalArmorValue()
	{
		return 0;
	}


	/*default boolean meleeAttack(Entity entity, float distanceSQ)
	{
		float attackRange = ((EntityLivingBase) this).width * 0.5F + this.getMeleeAttackDistance() + entity.width * 0.5F;
		attackRange *= attackRange;

		if (distanceSQ <= attackRange && ((EntityLivingBase) this).attackEntityAsMob(entity))
		{
			this.onAnimatedMeleeAttack();
			return true;
		}

		return false;
	}*/


	default boolean inFightStance()
	{
		return false;
	}

	default boolean swicthToFightStance()
	{
		return false;
	}


	default int getCurrentAttackDuration()
	{
		IAnimation animation = this.getActiveAnimation();
		return animation != null && animation.isHurtingAnimation() ? animation.getDuration() : 0;
	}

	default int getAttackCountdown()
	{
		IAnimation animation = this.getActiveAnimation();
		return animation != null && animation.isHurtingAnimation() ? animation.getCountdown() : 0;
	}

/*
	default boolean startAnimatedAttack(IAnimationHitManager manager)
	{
		return (this.inFightStance() || this.swicthToFightStance()) && this.tryChangeAnimation(manager);
	}
*/
	default boolean startAttack(Entity entity, float distance)
	{
		return false;
	}

	default boolean hitEntity(Entity entity, ItemStack itemStack, float damageMultiplier)
	{
		return false;
	}


	default int attackBreak()
	{
		return 20;
	}

	default float getMeleeAttackDistance()
	{
		return 1F;
	}


	default void saveExp(NBTTagCompound nbt) {}

	default void loadExp(NBTTagCompound nbt) {}


	default EntityLivingBase thisEntity() {
		return (EntityLivingBase) this;
	}

}
