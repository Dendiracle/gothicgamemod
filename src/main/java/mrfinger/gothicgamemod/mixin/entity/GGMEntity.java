package mrfinger.gothicgamemod.mixin.entity;

import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.wolrd.IGGMWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
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


    public void faceEntity(Entity p_70625_1_, float p_70625_2_, float p_70625_3_)
    {
        double d0 = p_70625_1_.posX - this.posX;
        double d2 = p_70625_1_.posZ - this.posZ;
        double d1;

        if (p_70625_1_ instanceof EntityLivingBase)
        {
            EntityLivingBase entitylivingbase = (EntityLivingBase)p_70625_1_;
            d1 = entitylivingbase.posY + (double)entitylivingbase.getEyeHeight() - (this.posY + (double)this.getEyeHeight());
        }
        else
        {
            d1 = (p_70625_1_.boundingBox.minY + p_70625_1_.boundingBox.maxY) / 2.0D - (this.posY + (double)this.getEyeHeight());
        }

        double d3 = (double) MathHelper.sqrt_double(d0 * d0 + d2 * d2);
        float f2 = (float)(Math.atan2(d2, d0) * 57.2957795131) - 90.0F;
        float f3 = (float)-(Math.atan2(d1, d3) * 57.2957795131);
        this.rotationPitch = this.updateRotation(this.rotationPitch, f3, p_70625_3_);
        this.rotationYaw = this.updateRotation(this.rotationYaw, f2, p_70625_2_);
    }

    protected float updateRotation(float p_70663_1_, float p_70663_2_, float p_70663_3_)
    {
        float f3 = MathHelper.wrapAngleTo180_float(p_70663_2_ - p_70663_1_);

        if (f3 > p_70663_3_)
        {
            f3 = p_70663_3_;
        }

        if (f3 < -p_70663_3_)
        {
            f3 = -p_70663_3_;
        }

        return p_70663_1_ + f3;
    }

}
