package mrfinger.gothicgamemod.entity;

import mrfinger.gothicgamemod.battle.DamageType;
import mrfinger.gothicgamemod.entity.animations.AnimationHelperEntityLivingBase;
import mrfinger.gothicgamemod.entity.animations.IAnimationHelper;
import mrfinger.gothicgamemod.entity.animations.episodes.IAnimationEpisode;
import mrfinger.gothicgamemod.entity.capability.attribute.instance.IGGMAttributeInstance;
import mrfinger.gothicgamemod.entity.effect.generic.IGGMEffect;
import mrfinger.gothicgamemod.entity.effect.instance.IGGMEffectInstance;
import mrfinger.gothicgamemod.entity.properties.IEntityProperties;
import mrfinger.gothicgamemod.fractions.Fraction;
import mrfinger.gothicgamemod.init.GGMBattleSystem;
import mrfinger.gothicgamemod.init.GGMCapabilities;
import mrfinger.gothicgamemod.init.GGMEntities;
import mrfinger.gothicgamemod.init.GGMFractions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;

import java.util.Collection;
import java.util.Map;

public interface IGGMEntityLivingBase extends IGGMEntity
{

	/*
	* Responsible for the level at init.
	*/
	default int initialLevel()
	{
		float l = 0.0F;
		Collection<IAttributeInstance> attributes = this.getAttributeMap().getAllAttributes();

		for (IAttributeInstance attributeInstance : attributes)
		{
			Float o = GGMCapabilities.expGainFromAttributesMap.get(attributeInstance.getAttribute());
			if (o != null && o > 0.0F) l += attributeInstance.getBaseValue() * o;
		}

		return Math.round(l);
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
		return this.getLvl() * GGMEntities.EXPFromLvlModifier;
	}
	

	default void restoreCurrentValuesFull()
	{
		this.setHealth(this.getMaxHealth());
	}


	default Fraction getCurrentFraction()
	{
		return this.getStandartFraction();
	}

	default Fraction getStandartFraction()
	{
		return GGMFractions.neutralFraction;
	}


	/*
	 * Responsible for the default animation helper at init.
	 */
	default IAnimationHelper getNewDefaultAnimationHelper()
	{
		return new AnimationHelperEntityLivingBase(this);
	}

	default IAnimationHelper getDefaultAnimationHelper()
	{
		return null;
	}

	default void setDefaulAnimationHelper(IAnimationHelper defaulAnimation) {}


	BaseAttributeMap getAttributeMap();

	IAttributeInstance getEntityAttribute(IAttribute attribute);

	default IGGMAttributeInstance getEntityAttribute(String name)
	{
		return (IGGMAttributeInstance) this.getAttributeMap().getAttributeInstanceByName(name);
	}


	default void setEntityProperties(IEntityProperties properties)
	{
		properties.setProperties(this);
	}


	void onLivingUpdate();

	default void moveUpdate() {}


	default IAnimationHelper getActiveAnimationHelper()
	{
		return null;
	}

	default IAnimationHelper getAnimationHelperToSet()
	{
		return null;
	}

	default void setActiveAnimationHelperDirectly(IAnimationHelper animation) {}

	default boolean setActiveAnimationHelperDirectly(String animationName)
	{
		return false;
	}

	/*
	* Returns true if after this method activeAnimationHelper
	* is default.
	*/
	default boolean tryChangeAnimationHelperToDefault()
	{
		return true;
	}

	/*
	 * Returns true if after this method activeAnimationHelper
	 * is agrument or default.
	 */
	default boolean tryChangeAnimationHelper(IAnimationHelper animation)
	{
		return true;
	}


	default Map<String, IAnimationEpisode> getAnimationEpisodesMap()
	{
		return null;
	}


	default void flagForAnimSync() {}

	default boolean isNeedSyncAnimation()
	{
		return false;
	}


	default void flagForLvlUpdate() {}

	default boolean isNeedExpUpdate()
	{
		return false;
	}


	default int getMaxAir()
	{
		return 300;
	}


	default IGGMEffectInstance getEffect(IGGMEffect effect)
	{
		return null;
	}

	default IGGMEffectInstance applyEffect(IGGMEffectInstance effectInstance)
	{
		return null;
	}

	default IGGMEffectInstance removeEffect(IGGMEffect effect)
	{
		return null;
	}

	default Map<IGGMEffect, IGGMEffectInstance> getEffectsMap()
	{
		return null;
	}

	/*default Map<IGGMEffect, IGGMEffectInstance> getBattleEffectsMap()
	{
		return null;
	}

	default Map<IGGMEffect, IGGMEffectInstance> getOtherEffectsMap()
	{
		return null;
	}
*/

	default IGGMAttributeInstance getHealthAttribute()
	{
		return (IGGMAttributeInstance) this.getEntityAttribute(GGMCapabilities.maxHealthDynamic);
	}

	float getHealth();

	void setHealth(float value);

	float getMaxHealth();


	default void increaseAttribute(IAttribute attribute, float value)
	{
		if (value < 0F)
		{
			throw new IllegalArgumentException("Increasing value cannot be less than 0");
		}

		IGGMAttributeInstance ai = (IGGMAttributeInstance) this.getEntityAttribute(attribute);

		if (ai != null) ai.changeBaseValue(value);
	}


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


	ItemStack getHeldItem();

	ItemStack getEquipmentInSlot(int slot);

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


	float getAIMoveSpeed();

	void setAIMoveSpeed(float speed);


	void moveEntityWithHeading(float strafe, float forward);


	default float getStandartRotationSpeed()
	{
		return 30.0F;
	}


	default boolean canJump()
	{
		return this.getActiveAnimationHelper().allowJump();
	}

	default double getJumpHeight()
	{
		return 0.41999998688697815D;
	}

	default void onJump()
	{
		this.getActiveAnimationHelper().onJumped();
	}


	default boolean isCanSprint()
	{
		return true;
	}

	boolean isSprinting();

	void setSprinting(boolean sprinting);


	default float waterMovementModifier()
	{
		return 0.800000011920929F;
	}


	default boolean isEntitySleeping()
	{
		return false;
	}

	default void wakeUpEntity() {}


	EntityLivingBase getAITarget();


	default int getTotalArmorValue()
	{
		return 0;
	}


	default DamageType getStandartMeleeDamageType()
	{
		return GGMBattleSystem.crushing;
	}

	default boolean meleeAttack(Entity entity, float distanceSQ)
	{
		float attackRange = ((EntityLivingBase) this).width * 0.5F + this.getMeleeAttackDistance() + entity.width * 0.5F;
		attackRange *= attackRange;

		if (distanceSQ <= attackRange && this.attackEntityAsMob(entity))
		{
			this.onAnimatedMeleeAttack();
			return true;
		}

		return false;
	}

	boolean attackEntityAsMob(Entity entity);

	default short attackBreak()
	{
		return 20;
	}

	default float getMeleeAttackDistance()
	{
		return 1.0F;
	}

	default void onAnimatedMeleeAttack() {}


	boolean isClientWorld();


	default void saveExp(NBTTagCompound nbt) {}

	default void loadExp(NBTTagCompound nbt) {}


	default EntityLivingBase thisEntity() {
		return (EntityLivingBase) this;
	}

}
