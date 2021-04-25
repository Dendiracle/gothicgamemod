package mrfinger.gothicgamemod.mixin.entity.ai.attributes;

import mrfinger.gothicgamemod.entity.capability.attributes.GGMIncreasableAttributeInfo;
import mrfinger.gothicgamemod.entity.capability.attributes.IGGMAttribute;
import mrfinger.gothicgamemod.entity.capability.attributes.IGGMIncreasableAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.ai.attributes.ServersideAttributeMap;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

@Mixin(ServersideAttributeMap.class)
public abstract class GGMServersideAttributeMap extends GGMBaseAttributeMap {


    @Shadow @Final protected Map descriptionToAttributeInstanceMap;

    @Override
    public IGGMIncreasableAttributeInstance registerAttribute(IGGMAttribute attribute, GGMIncreasableAttributeInfo attributeInfo) {

        IGGMIncreasableAttributeInstance ai = super.registerAttribute(attribute, attributeInfo);

        if (attribute instanceof RangedAttribute && ((RangedAttribute) attribute).getDescription() != null) {

            this.descriptionToAttributeInstanceMap.put(((RangedAttribute) attribute).getDescription(), ai);
        }

        return ai;
    }
}
