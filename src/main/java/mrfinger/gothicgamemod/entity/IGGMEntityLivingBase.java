package mrfinger.gothicgamemod.entity;

import mrfinger.gothicgamemod.entity.animations.AnimationHelperEntityLivingBase;
import mrfinger.gothicgamemod.entity.animations.IAnimationHelper;
import mrfinger.gothicgamemod.entity.capability.attributes.IGGMAttribute;
import mrfinger.gothicgamemod.entity.capability.attributes.IGGMBaseAttributeMap;
import mrfinger.gothicgamemod.entity.capability.attributes.IGGMDynamicAttributeInstance;
import mrfinger.gothicgamemod.entity.capability.attributes.IGGMModifiableAttributeInstance;
import mrfinger.gothicgamemod.entity.capability.effects.IGGMEffect;
import mrfinger.gothicgamemod.entity.capability.effects.IGGMEffectInstance;
import mrfinger.gothicgamemod.fractions.Fraction;
import mrfinger.gothicgamemod.init.GGMCapabilities;
import mrfinger.gothicgamemod.init.GGMFractions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;

import java.util.Collection;
import java.util.Map;

public interface IGGMEntityLivingBase extends IGGMEntity
{

	default int initialLevel()
	{
		float l = 0.0F;
		Map<IAttribute, IAttributeInstance> map = ((IGGMBaseAttributeMap) this.getAttributeMap()).getAllAttributesMap();

		for (Map.Entry<IAttribute, IAttributeInstance> e : map.entrySet()) {

			Float o = GGMCapabilities.expGainFromAttributesMap.get(e.getKey());

			if (o != null && o > 0.0F) l += e.getValue().getBaseValue() / o;
		}

		return Math.round(l);
	}


	default int getLvl()
	{
		return 0;
	}

	default void setLvl(int lvl) {}


	default void saveExp(NBTTagCompound nbt) {}

	default void loadExp(NBTTagCompound nbt) {}
	
	
	default void restoreCurrentValuesFull()
	{
		Collection<IGGMDynamicAttributeInstance> dpiColl = ((IGGMBaseAttributeMap) this.getAttributeMap()).getDPIColl();

		for (IGGMDynamicAttributeInstance ai : dpiColl)
		{
			ai.restore();
		}
	}


	default Fraction getFraction()
	{
		return this.getStandartFraction();
	}

	default Fraction getStandartFraction()
	{
		return GGMFractions.neutralFraction;
	}


	void onLivingUpdate();


	default IAnimationHelper getNewDefaultAnimation()
	{
		return new AnimationHelperEntityLivingBase(this);
	}

	default IAnimationHelper getCurrentAnimation()
	{
		return null;
	}

	default IAnimationHelper getDefaultAnimation()
	{
		return null;
	}

	default IAnimationHelper getAnimationToSet()
	{
		return null;
	}

	default void setDefaulAnimation(IAnimationHelper defaulAnimation) {}

	default void setAnimationHelper(IAnimationHelper animation) {}

	default boolean setAnimationHelper(String animationName)
	{
		return false;
	}

	/*
	* Returns true if after this method currentAnimation
	* is null or default.
	 */
	default boolean tryEndAnimation()
	{
		return true;
	}

	default boolean tryEndAnimation(IAnimationHelper animation)
	{
		return true;
	}

	default void clearAnimation() {}


	default void flagForAnimSync() {}

	default boolean isNeedSyncAnimation()
	{
		return false;
	}


	default int getMaxAir()
	{
		return 300;
	}


	default Map<IGGMEffect, IGGMEffectInstance> getEffectsMap()
	{
		return null;
	}

	default Map<IGGMEffect, IGGMEffectInstance> getAttackEffectsMap()
	{
		return null;
	}

	default Map<IGGMEffect, IGGMEffectInstance> getOtherEffectsMap()
	{
		return null;
	}


	ItemStack getHeldItem();


	BaseAttributeMap getAttributeMap();

	IAttributeInstance getEntityAttribute(IAttribute attribute);

	default IGGMDynamicAttributeInstance getDP(IGGMAttribute attribute)
	{
		return ((IGGMBaseAttributeMap) this.getAttributeMap()).getDPI(attribute);
	}


	default IAttributeInstance getHealthAttribute()
	{
		return this.getDP(GGMCapabilities.maxHealth);
	}

	float getHealth();

	void setHealth(float value);

	float getMaxHealth();


	default void inreaseAttribute(IAttribute attribute, float value)
	{
		IGGMModifiableAttributeInstance ai = (IGGMModifiableAttributeInstance) this.getEntityAttribute(attribute);

		ai.increaseAttribute(value);
	}


	default boolean isDeniedDropItems()
	{
		return false;
	}

	default boolean isDeniedDigging()
	{
		return false;
	}

	default boolean isDeniedUsingItems()
	{
		return false;
	}


	float getAIMoveSpeed();

	void setAIMoveSpeed(float speed);


	void moveEntityWithHeading(float strafe, float forward);


	default float getStandartRotationSpeed()
	{
		return 30.0F;
	}

	default void faceEntity(Entity p_70625_1_, float p_70625_2_, float p_70625_3_) {}


	default double getJumpHeight()
	{
		return 0.41999998688697815D;
	}


	default double getWanderSpeedModifier()
	{
		return 0.5D;
	}


	default boolean isCanSprint() {
		return this.getDisallowSprintTimer() <= 0;
	}

	boolean isSprinting();

	void setSprinting(boolean sprinting);

	default void setDisallowSprintTimer(short timer) {}

	default int getDisallowSprintTimer()
	{
		return 0;
	}


	default void setPath(PathEntity path) {}

	default void setPath(int x, int y, int z) {}

	default void setPathToEntity(IGGMEntity entity) {}

	default void cleartPath() {}


	default float waterMovementModifier()
	{
		return 0.800000011920929F;
	}


	default boolean isAvoidsWater()
	{
		return true;
	}

	default boolean isCanSwim()
	{
		return true;
	}


	EntityLivingBase getAITarget();


	default void justAttack(Entity entity, float distance) {}

	default void tryAttack(IGGMEntity entity, float distance) {}


	default int getTotalArmorValue()
	{
		return 0;
	}

	boolean attackEntityAsMob(Entity entity);

	default short attackDuration()
	{
		return 20;
	}

	default float attackDistance()
	{
		return 1.0F;
	}

	ItemStack getEquipmentInSlot(int slot);


	default void flagForLvlUpdate()
	{

	}

	default boolean isNeedExpUpdate()
	{
		return false;
	}


	boolean isClientWorld();


	default EntityLivingBase thisEntity() {
		return (EntityLivingBase) this;
	}

}
