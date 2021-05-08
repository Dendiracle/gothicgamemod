package mrfinger.gothicgamemod.mixin.entity;

import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.wolrd.IGGMWorld;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class GGMEntity implements IGGMEntity {


    @Shadow private int entityId;

    @Shadow public World worldObj;

    @Shadow public double posX;

    @Shadow public double posY;

    @Shadow public double posZ;

    @Shadow public boolean isDead;

    @Shadow public double motionY;

    @Shadow public double motionZ;

    @Shadow public double motionX;

    @Shadow public abstract void setEating(boolean p_70019_1_);

    @Shadow public float stepHeight;

    @Override
    public int getEntityId() {
        return this.entityId;
    }

    @Override
    public IGGMWorld getEntityWorld()
    {
        return (IGGMWorld) this.worldObj;
    }


    @Override
    public double getPosX() {
        return posX;
    }

    @Override
    public double getPosY() {
        return posY;
    }

    @Override
    public double getPosZ() {
        return posZ;
    }
}
