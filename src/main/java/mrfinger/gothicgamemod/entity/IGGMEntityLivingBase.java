package mrfinger.gothicgamemod.entity;

import mrfinger.gothicgamemod.entity.capability.EntitySkills;
import mrfinger.gothicgamemod.entity.capability.attributes.IGGMDynamicAttributeInstance;
import mrfinger.gothicgamemod.entity.capability.attributes.IGGMModifiableAttributeInstance;
import mrfinger.gothicgamemod.entity.capability.skills.IGGMSkillInstance;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;

public interface IGGMEntityLivingBase extends IGGMEntity {


	int initialLevel();
	
	
	void saveExp(NBTTagCompound nbt);
	
	void loadExp(NBTTagCompound nbt);
	
	
	void saveSkills(NBTTagCompound compound);
	
	void loadSkills(NBTTagCompound compound);
	
	
	void saveAbilities(NBTTagCompound compound);
	
	void loadAbilities(NBTTagCompound compound);
	
	
	void restoreCurrentValuesFull();


	int getLvl();

	void setLvl(int lvl);


	void onLivingUpdate();

	int getMaxAir();


	ItemStack getHeldItem();


	BaseAttributeMap getAttributeMap();

	IAttributeInstance getEntityAttribute(IAttribute attribute);

	IGGMDynamicAttributeInstance getDP(IAttribute attribute);


	IAttributeInstance getHealthAttribute();

	float getHealth();

	void setHealth(float value);

	float getMaxHealth();

	
	IGGMSkillInstance getSkill(EntitySkills name);


	void inreaseAttribute(IAttribute attribute, float value);


	void jump();

	double jumpHeight();


	default boolean isCanSprint() {
		return this.getDisallowSprintTimer() <= 0;
	}

	boolean isSprinting();

	void setSprinting(boolean sprinting);

	void setDisallowSprintTimer(int timer);

	int getDisallowSprintTimer();


	void justAttack(Entity entity, float distance);


	int getTotalArmorValue();

	float applyArmorCalculations(DamageSource ds, float damage);

	boolean attackEntityAsMob(Entity entity);

	int attackDuration();

	float attackDistance();

	ItemStack getEquipmentInSlot(int slot);


	void flagForLvlUpdate();

	boolean isNeedExpUpdate();


	default EntityLivingBase thisEntity() {
		return (EntityLivingBase) this;
	}

}
