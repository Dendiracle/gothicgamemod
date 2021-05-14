package mrfinger.gothicgamemod.entity;

import mrfinger.gothicgamemod.entity.animations.IAnimation;
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

import java.util.Collection;
import java.util.Map;

public interface IGGMEntityLivingBase extends IGGMEntity {


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


	default IAnimation getCurrentAnimation()
	{
		return null;
	}

	default IAnimation getDefaultAnimation()
	{
		return null;
	}

	default void setAnimation(IAnimation animation) {}

	default boolean endAnimation()
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


	default double jumpHeight()
	{
		return 0.41999998688697815D;
	}


	default boolean isCanSprint() {
		return this.getDisallowSprintTimer() <= 0;
	}

	boolean isSprinting();

	void setSprinting(boolean sprinting);

	default void setDisallowSprintTimer(int timer) {}

	default int getDisallowSprintTimer()
	{
		return 0;
	}


	default void justAttack(Entity entity, float distance) {}

	default void tryAttack(IGGMEntity entity, float distance) {}


	default int getTotalArmorValue()
	{
		return 0;
	}

	boolean attackEntityAsMob(Entity entity);

	default int attackDuration()
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
