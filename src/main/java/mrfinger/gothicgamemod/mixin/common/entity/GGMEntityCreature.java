package mrfinger.gothicgamemod.mixin.common.entity;

import mrfinger.gothicgamemod.entity.IGGMEntityCreature;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityCreature.class)
public abstract class GGMEntityCreature extends GGMEntityLiving implements IGGMEntityCreature {


    @Shadow protected Entity            entityToAttack;

    @Shadow protected abstract void attackEntity(Entity p_70785_1_, float p_70785_2_);


    public EntityCreature thisEntity() {
        return (EntityCreature) (Object) this;
    }

}
