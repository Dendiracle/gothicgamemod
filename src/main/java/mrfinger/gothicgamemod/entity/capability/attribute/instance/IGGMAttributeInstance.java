package mrfinger.gothicgamemod.entity.capability.attribute.instance;

import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.capability.attribute.map.IGGMBaseAttributeMap;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Collection;
import java.util.UUID;

public interface IGGMAttributeInstance extends IAttributeInstance
{

	default void onSetEntity(IGGMEntityLivingBase oldEntity, IGGMEntityLivingBase newEntity) {}

	default IGGMEntityLivingBase getEntity()
	{
		return this.getAttributeMap() != null ? this.getAttributeMap().getEntity() : null;
	}

	default IGGMBaseAttributeMap getAttributeMap()
	{
		return null;
	}


	default void changeBaseValue(double changeValue)
	{
		this.setBaseValue(this.getBaseValue() + changeValue);
	}


	default void setModifierAmount(UUID id, double amount) {}


	IAttributeSnapshot getShapshot();


	void saveNBTData(NBTTagCompound nbt);

	void loadNBTData(NBTTagCompound nbt);


	interface IAttributeSnapshot
	{

		void setAttribute(IGGMAttributeInstance attributeInstance);


		double getBaseValue();

		Collection<AttributeModifier> getAttributeModifiers();

	}


	class ModifiableAttributeShapshot implements IAttributeSnapshot
	{

		protected double baseValue;
		protected Collection<AttributeModifier> modificators;


		public ModifiableAttributeShapshot(double baseValue, Collection<AttributeModifier> modificators)
		{
			this.baseValue = baseValue;
			this.modificators = modificators;
		}


		@Override
		public void setAttribute(IGGMAttributeInstance attributeInstance)
		{
			attributeInstance.setBaseValue(this.baseValue);
			attributeInstance.removeAllModifiers();

			for (AttributeModifier attributeModifier : this.modificators)
			{
				attributeInstance.applyModifier(attributeModifier);
			}
		}


		@Override
		public double getBaseValue()
		{
			return this.baseValue;
		}

		@Override
		public Collection<AttributeModifier> getAttributeModifiers()
		{
			return this.modificators;
		}

	}

}


