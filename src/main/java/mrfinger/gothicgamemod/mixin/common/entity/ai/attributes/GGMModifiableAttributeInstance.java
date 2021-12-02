package mrfinger.gothicgamemod.mixin.common.entity.ai.attributes;

import io.netty.buffer.ByteBuf;
import mrfinger.gothicgamemod.entity.capability.attribute.generic.IGGMAttribute;
import mrfinger.gothicgamemod.entity.capability.attribute.instance.IGGMAttributeInstance;
import mrfinger.gothicgamemod.entity.capability.attribute.IGGMAttributeModifier;
import mrfinger.gothicgamemod.entity.capability.attribute.map.IGGMBaseAttributeMap;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Collection;
import java.util.UUID;

@Mixin(ModifiableAttributeInstance.class)
public abstract class GGMModifiableAttributeInstance implements IGGMAttributeInstance
{

    @Shadow @Final protected IAttribute genericAttribute;
    @Shadow @Final protected BaseAttributeMap attributeMap;
    @Shadow protected double baseValue;


    @Shadow public abstract IAttribute getAttribute();

    @Override
    public IGGMBaseAttributeMap getAttributeMap()
    {
        return (IGGMBaseAttributeMap) attributeMap;
    }


    @Shadow public abstract double getAttributeValue();

    @Shadow public abstract void setBaseValue(double p_111128_1_);

    @Override
    public void changeBaseValue(double changeValue)
    {
        this.setBaseValue(this.getBaseValue() + changeValue);
    }


    @Shadow public abstract AttributeModifier getModifier(UUID p_111127_1_);

    @Override
    public void setModifierAmount(UUID id, double amount)
    {
        IGGMAttributeModifier am = (IGGMAttributeModifier) this.getModifier(id);

        if (am != null)
        {
            am.setAmount(amount);
            this.flagForUpdate();
        }
    }


    @Shadow protected abstract void flagForUpdate();

    @Shadow public abstract Collection func_111122_c();


    @Override
    public IAttributeSnapshot getShapshot()
    {
        return new IGGMAttributeInstance.ModifiableAttributeShapshot(this.baseValue, this.func_111122_c());
    }


    @Override
    public void saveNBTData(NBTTagCompound nbt) {}

    @Override
    public void loadNBTData(NBTTagCompound nbt) {}

}
