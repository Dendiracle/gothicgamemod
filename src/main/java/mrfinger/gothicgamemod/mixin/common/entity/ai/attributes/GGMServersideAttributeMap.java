package mrfinger.gothicgamemod.mixin.common.entity.ai.attributes;

import mrfinger.gothicgamemod.entity.capability.attribute.generic.IGGMAttribute;
import mrfinger.gothicgamemod.entity.capability.attribute.instance.IGGMAttributeInstance;
import net.minecraft.entity.ai.attributes.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Map;
import java.util.Set;

@Mixin(ServersideAttributeMap.class)
public abstract class GGMServersideAttributeMap extends GGMBaseAttributeMap
{

    @Shadow
    @Final
    protected Map descriptionToAttributeInstanceMap;
    @Shadow
    @Final
    private Set attributeInstanceSet;


    @Redirect(method = "registerAttribute", at = @At(value = "NEW", target = "net/minecraft/entity/ai/attributes/ModifiableAttributeInstance", ordinal = 0))
    private ModifiableAttributeInstance fixAttributeInstanceCeation(BaseAttributeMap attributeMap, IAttribute attribute)
    {
        if (attribute instanceof IGGMAttribute)
        {
            IGGMAttributeInstance attributeInstance = ((IGGMAttribute) attribute).getNewAttributeInstance(this);
            attributeInstance.onSetEntity(null, this.entity);
            return (ModifiableAttributeInstance) attributeInstance;
        }

        return new ModifiableAttributeInstance(attributeMap, attribute);
    }

}
