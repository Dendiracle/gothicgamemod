package mrfinger.gothicgamemod.mixin.common.entity.ai.attributes;

import mrfinger.gothicgamemod.entity.capability.attribute.IGGMAttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AttributeModifier.class)
public abstract class GGMAttributeModifier implements IGGMAttributeModifier
{

    @Shadow protected double amount;


    @Override
    public IGGMAttributeModifier setAmount(double amount)
    {
        this.amount = amount;
        return this;
    }

}
