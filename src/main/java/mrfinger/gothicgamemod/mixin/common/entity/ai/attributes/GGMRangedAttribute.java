package mrfinger.gothicgamemod.mixin.common.entity.ai.attributes;

import net.minecraft.entity.ai.attributes.RangedAttribute;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(RangedAttribute.class)
public abstract class GGMRangedAttribute extends GGMBaseAttribute
{

    @Shadow public double maximumValue;


    @Override
    public double getMaxValue()
    {
        return this.maximumValue;
    }

    @Override
    public void setMaxValue(double value)
    {
        this.maximumValue = value;
    }
}
