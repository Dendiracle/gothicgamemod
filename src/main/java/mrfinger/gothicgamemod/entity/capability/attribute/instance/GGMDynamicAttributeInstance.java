package mrfinger.gothicgamemod.entity.capability.attribute.instance;

import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.capability.attribute.modifier.IRegenModifierInstance;
import mrfinger.gothicgamemod.entity.capability.attribute.modifier.RegenModifier;
import mrfinger.gothicgamemod.entity.capability.attribute.generic.GGMDynamicRangedAttribute;
import mrfinger.gothicgamemod.entity.capability.attribute.map.IGGMBaseAttributeMap;
import mrfinger.gothicgamemod.entity.effect.instance.GGMEffectInstanceDynamicAttributeController;
import mrfinger.gothicgamemod.init.GGMEffects;
import mrfinger.gothicgamemod.data.entity.EntityCapabilitiesData;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.*;

public class GGMDynamicAttributeInstance extends ModifiableAttributeInstance implements IGGMDynamicAttributeInstance
{

    protected double dynamicValue;
    protected float naturalRegen;
    protected float cachedRegenValue;
    protected byte bitSet;

    NavigableMap<RegenModifier, IRegenModifierInstance> regenModifiersMap;


    public GGMDynamicAttributeInstance(IGGMBaseAttributeMap map, GGMDynamicRangedAttribute attribute)
    {
        super((BaseAttributeMap) map, attribute);
    }


    @Override
    public IGGMEntityLivingBase getEntity()
    {
        return this.getAttributeMap() != null ? this.getAttributeMap().getEntity() : null;
    }

    @Override
    public void onSetEntity(IGGMEntityLivingBase oldEntity, IGGMEntityLivingBase newEntity)
    {
        if (oldEntity != null)
        {
            oldEntity.removeEffect(GGMEffects.DynamicAttributeController);
        }

        if (newEntity != null)
        {
            GGMEffectInstanceDynamicAttributeController controller = (GGMEffectInstanceDynamicAttributeController) newEntity.getEffect(GGMEffects.DynamicAttributeController);

            if (controller == null)
            {
                controller = new GGMEffectInstanceDynamicAttributeController();
                newEntity.applyEffect(controller);
            }

            controller.addAttribute(this);
        }
    }


    @Override
    protected void flagForUpdate()
    {
        this.flagForRegenUpdate();

        super.flagForUpdate();
    }


    @Override
    public double getDynamicValue()
    {
        return this.dynamicValue;
    }

    @Override
    public float getNaturalRegen()
    {
        return this.naturalRegen;
    }

    /*
     * Returns regular regeneration value according to regenerate rate.
    */
    @Override
    public float getCachedRegenValue()
    {
        if (this.isNeedRegenUpdate())
        {
            this.updateRegen();
            this.dismissRegenUpdate();
        }

        return this.cachedRegenValue;
    }


    @Override
    public void setDynamicValue(double value)
    {
        if (this.dynamicValue != value)
        {
            double attributeValue = this.getAttributeValue();
            this.dynamicValue = value < 0.0D ? 0.0D : ((value >= attributeValue) ? attributeValue : value);
            this.flagForDynamicValueUpdate();
        }
    }

    /*protected boolean isNeedSyncDynamicValueOnly()
    {
        return (this.bitSet & 0x10) == 0x10;
    }

    protected void dismissSyncDynamicValueOnly()
    {
        this.bitSet &= 0x11111101;
    }*/


    protected void flagForDynamicValueUpdate()
    {
        //this.bitSet |= 0x10;
        //this.attributeMap.addAttributeInstance(this);

        GGMEffectInstanceDynamicAttributeController controller = (GGMEffectInstanceDynamicAttributeController) this.getEntity().getEffect(GGMEffects.DynamicAttributeController);

        if (controller == null)
        {
            controller = new GGMEffectInstanceDynamicAttributeController();
            this.getEntity().applyEffect(controller);
            controller.addAttribute(this);
        }

        controller.addToUpdate(this);
    }

    @Override
    public void changeCurrentValue(double value)
    {
        this.setDynamicValue(this.dynamicValue + value);
    }

    @Override
    public void restore()
    {
        this.setDynamicValue(this.getAttributeValue());
    }


    @Override
    public void setNaturalRegen(float value)
    {
        if (this.naturalRegen != value)
        {
            this.naturalRegen = value < 0.0F ? 0.0F : value;
            this.flagForRegenUpdate();
        }
    }

    protected boolean isNeedRegenUpdate()
    {
        return (this.bitSet & 0x1) == 0x1;
    }

    protected void flagForRegenUpdate()
    {
        this.bitSet |= 0x1;
    }

    protected void dismissRegenUpdate()
    {
        this.bitSet &= 0x11111110;
    }

    @Override
    public void changeNaturalRegen(float value)
    {
        this.setNaturalRegen(this.naturalRegen + value);
    }


    @Override
    public IRegenModifierInstance getRegenModifier(RegenModifier regenModifier)
    {
        return this.regenModifiersMap.get(regenModifier);
    }

    @Override
    public void applyRegenModifier(IRegenModifierInstance regenModifierInstance)
    {
        this.flagForRegenUpdate();
        if (this.regenModifiersMap == null) this.regenModifiersMap = new TreeMap<>();
        this.regenModifiersMap.put(regenModifierInstance.getGenericRegenModifier(), regenModifierInstance);
    }

    @Override
    public IRegenModifierInstance removeRegenModifier(RegenModifier regenModifier)
    {
        this.flagForRegenUpdate();
        return this.regenModifiersMap != null ? this.regenModifiersMap.remove(regenModifier) : null;
    }


    @Override
    public void updateRegen()
    {
        float regen = 0F;

        if (!this.regenModifiersMap.isEmpty())
        {
            for (IRegenModifierInstance regenModifierInstance : this.regenModifiersMap.values())
            {
                regen += regenModifierInstance.getAddedValue(this, regen);
            }
        }

        this.cachedRegenValue = ((regen + this.getNaturalRegen()) * ((GGMDynamicRangedAttribute) this.genericAttribute).getUpdateInfrequency()) / 20F;
    }


    @Override
    public void removeAllModifiers()
    {
        super.removeAllModifiers();

        if (this.regenModifiersMap != null) this.regenModifiersMap.clear();
    }

    @Override
    public IAttributeSnapshot getShapshot()
    {
        return new GGMDynamicAttributeShapshot(this.baseValue, this.func_111122_c(), this.dynamicValue, this.naturalRegen, new ArrayList<>(this.regenModifiersMap.values()));
    }


    @Override
    public void saveNBTData(NBTTagCompound nbt)
    {
        nbt.setDouble("Curr", this.getDynamicValue());
        nbt.setFloat("NReg", this.getNaturalRegen());

        if (!this.regenModifiersMap.isEmpty())
        {

            NBTTagList regenModifiersList = new NBTTagList();

            for (Map.Entry<RegenModifier, IRegenModifierInstance> entry : this.regenModifiersMap.entrySet())
            {
                if (entry.getValue().isSaved())
                {
                    NBTTagCompound rmCompound = new NBTTagCompound();
                    EntityCapabilitiesData.saveRegenModifier(entry.getValue(), rmCompound);
                    regenModifiersList.appendTag(rmCompound);
                }
            }

            nbt.setTag("RegModifiers", regenModifiersList);
        }
    }

    @Override
    public void loadNBTData(NBTTagCompound nbt)
    {
        this.setDynamicValue(nbt.getDouble("Curr"));
        this.setNaturalRegen(nbt.getFloat("NReg"));

        NBTTagList regenModifiersList = nbt.getTagList("RegModifiers", 10);

        for (int i = 0; i < regenModifiersList.tagCount(); ++i)
        {
            this.applyRegenModifier(EntityCapabilitiesData.loadRegenModifierInstanceFromNBT(regenModifiersList.getCompoundTagAt(i)));
        }
    }


    public static class GGMDynamicAttributeShapshot extends ModifiableAttributeShapshot
    {

        protected double dynamicValue;
        protected float naturalRegen;
        protected Collection<IRegenModifierInstance> regenModifiersCollection;


        public GGMDynamicAttributeShapshot(double baseValue, Collection<AttributeModifier> modificators, double dynamicValue, float naturalRegenValue, Collection<IRegenModifierInstance> regenModifiersCollection)
        {
            super(baseValue, modificators);

            this.dynamicValue = dynamicValue;
            this.naturalRegen = naturalRegenValue;
            this.regenModifiersCollection = regenModifiersCollection;
        }


        @Override
        public void setAttribute(IGGMAttributeInstance attributeInstance)
        {
            IGGMDynamicAttributeInstance dynamicAttributeInstance = (IGGMDynamicAttributeInstance) attributeInstance;

            super.setAttribute(attributeInstance);

            dynamicAttributeInstance.setDynamicValue(this.dynamicValue);
            dynamicAttributeInstance.setNaturalRegen(this.naturalRegen);
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

        public double getDynamicValue()
        {
            return dynamicValue;
        }

        public float getNaturalRegen()
        {
            return naturalRegen;
        }

        public Collection<IRegenModifierInstance> getRegenModifiersCollection()
        {
            return regenModifiersCollection;
        }

    }


}

