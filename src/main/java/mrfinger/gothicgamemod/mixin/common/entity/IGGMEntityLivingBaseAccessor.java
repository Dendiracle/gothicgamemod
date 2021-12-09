package mrfinger.gothicgamemod.mixin.common.entity;

import mrfinger.gothicgamemod.entity.IGGMEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EntityLivingBase.class)
public interface IGGMEntityLivingBaseAccessor extends IGGMEntity
{

    @Accessor
    BaseAttributeMap getAttributeMap();

}
