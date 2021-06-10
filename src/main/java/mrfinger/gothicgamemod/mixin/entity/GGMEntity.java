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

import java.util.Random;

@Mixin(Entity.class)
public abstract class GGMEntity implements IGGMEntity {


    @Shadow private int entityId;

    @Shadow public World worldObj;

    @Shadow public double posX;
    @Shadow public double posY;
    @Shadow public double posZ;

    @Shadow public float rotationYaw;
    @Shadow public float rotationPitch;

    @Shadow public double motionY;
    @Shadow public double motionZ;
    @Shadow public double motionX;

    @Shadow public float stepHeight;

    @Shadow public boolean isDead;

    @Shadow protected Random rand;

    @Shadow public Entity ridingEntity;

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

    @Override
    public float getRotationYaw()
    {
        return this.rotationYaw;
    }

    @Override
    public float getRotationPitch()
    {
        return this.rotationPitch;
    }


    @Override
    public double getMotionX()
    {
        return motionX;
    }

    @Override
    public double getMotionY()
    {
        return motionY;
    }

    @Override
    public double getMotionZ()
    {
        return motionZ;
    }


    @Override
    public Entity getRidingEntity()
    {
        return this.ridingEntity;
    }

    @Override
    public Random getRNG() {
        return this.rand;
    }


    @Inject(method = "mountEntity", at = @At("HEAD"), cancellable = true)
    private void onMountEntity(Entity entity, CallbackInfo ci)
    {
        if (this.cancelMount((IGGMEntity) entity)) ci.cancel();
    }

    protected boolean cancelMount(IGGMEntity entity)
    {
        return false;
    }
}
