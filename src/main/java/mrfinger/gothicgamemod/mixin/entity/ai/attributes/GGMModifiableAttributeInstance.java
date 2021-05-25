package mrfinger.gothicgamemod.mixin.entity.ai.attributes;

import mrfinger.gothicgamemod.entity.capability.attributes.IGGMAttributeModifier;
import mrfinger.gothicgamemod.entity.capability.attributes.IGGMModifiableAttributeInstance;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.UUID;

@Mixin(ModifiableAttributeInstance.class)
public abstract class GGMModifiableAttributeInstance implements IGGMModifiableAttributeInstance {


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


    @Shadow @Final protected BaseAttributeMap attributeMap;

    @Shadow @Final protected IAttribute genericAttribute;

    @Override
    public void changeBaseValue(double changeValue)
    {
        this.setBaseValue(this.getBaseValue() + changeValue);
    }


    @Override
    public double increaseAttribute(double value)
    {
        this.changeBaseValue(value);
        return value;
    }

    @Override
    public void saveNBTData(NBTTagCompound nbt) {}

    @Override
    public void loadNBTData(NBTTagCompound nbt) {}

}
