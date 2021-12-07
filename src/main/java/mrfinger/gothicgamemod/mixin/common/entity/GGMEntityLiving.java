package mrfinger.gothicgamemod.mixin.common.entity;

import mrfinger.gothicgamemod.entity.IGGMEntityLiving;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityLiving.class)
public abstract class GGMEntityLiving extends GGMEntityLivingBase implements IGGMEntityLiving
{

    @Shadow protected EntityLivingBase attackTarget;

    @Override
    public EntityLivingBase getAttackTarget()
    {
        return this.attackTarget;
    }
}
