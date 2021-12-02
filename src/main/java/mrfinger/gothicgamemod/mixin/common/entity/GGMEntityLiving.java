package mrfinger.gothicgamemod.mixin.common.entity;

import mrfinger.gothicgamemod.entity.IGGMEntityLiving;
import net.minecraft.entity.EntityLiving;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityLiving.class)
public abstract class GGMEntityLiving extends GGMEntityLivingBase implements IGGMEntityLiving {



}
