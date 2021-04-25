package mrfinger.gothicgamemod.mixin.entity;

import mrfinger.gothicgamemod.entity.IGGMEntity;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Entity.class)
public abstract class GGMEntity implements IGGMEntity {


    @Shadow private int entityId;

    @Shadow public World worldObj;

    @Shadow public double posX;

    @Shadow public double posY;

    @Shadow public double posZ;

    @Shadow public boolean isDead;

    @Override
    public int getEntityId() {
        return this.entityId;
    }

    @Override
    public World getEntityWorld() {
        return this.worldObj;
    }

}
