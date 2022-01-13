package mrfinger.gothicgamemod.mixin.common.entity;

import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.util.IGGMDamageSource;
import mrfinger.gothicgamemod.wolrd.IGGMWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(Entity.class)
public abstract class GGMEntity implements IGGMEntity
{


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

    @Shadow public boolean onGround;

    @Shadow public float width;
    @Shadow public float height;

    @Shadow public float stepHeight;

    @Shadow public boolean isDead;

    @Shadow protected Random rand;

    @Shadow public Entity ridingEntity;

    @Shadow public int ticksExisted;


    @Override
    public int getEntityID() {
        return this.entityId;
    }

    @Override
    public IGGMWorld getEntityWorld()
    {
        return (IGGMWorld) this.worldObj;
    }


    @Override
    public boolean entityAlive()
    {
        return this.isEntityAlive();
    }

    @Shadow public abstract boolean isEntityAlive();


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
    public float eyeHeight()
    {
        return this.getEyeHeight();
    }

    @Shadow public abstract float getEyeHeight();

    @Override
    public float getRotationYaw()
    {
        return this.rotationYaw;
    }

    @Override
    public float getHeadRotationYaw()
    {
        return this.getRotationYawHead();
    }

    @Shadow public abstract float getRotationYawHead();

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
    public boolean onGround()
    {
        return this.onGround;
    }

    @Override
    public float getDistanceToEntity(IGGMEntity entity)
    {
        return this.getDistanceToEntity((Entity) entity);
    }

    @Shadow public abstract float getDistanceToEntity(Entity p_70032_1_);

    @Override
    public double getDistanceSqToEntity(IGGMEntity entity)
    {
        return this.getDistanceSqToEntity((Entity) entity);
    }

    @Shadow public abstract double getDistanceSqToEntity(Entity p_70068_1_);


    @Override
    public float getWidth()
    {
        return this.width;
    }

    @Override
    public float getHeight()
    {
        return this.height;
    }


    @Override
    public boolean canEntityBeSeen(IGGMEntity entity)
    {
        return this.worldObj.rayTraceBlocks(Vec3.createVectorHelper(this.posX, this.posY + (double)this.getEyeHeight(), this.posZ), Vec3.createVectorHelper(entity.getPosX(), entity.getPosY() + (double)entity.eyeHeight(), entity.getPosZ())) == null;
    }

    @Override
    public Random getRNG() {
        return this.rand;
    }



    @Override
    public boolean isCanMount(Entity entity)
    {
        return true;
    }

    @Override
    public boolean isOnMount()
    {
        return this.isRiding();
    }

    @Shadow public abstract boolean isRiding();

    @Override
    public Entity getRidingEntity()
    {
        return this.ridingEntity;
    }

    @Inject(method = "mountEntity", at = @At("HEAD"), cancellable = true)
    private void onMountEntity(Entity entity, CallbackInfo ci)
    {
        if (!this.isCanMount(entity)) ci.cancel();
    }


    @Override
    public boolean attackEntityFrom(IGGMDamageSource damageSource, float damage)
    {
        return this.attackEntityFrom((DamageSource) damageSource, damage);
    }

    @Shadow public abstract boolean attackEntityFrom(DamageSource p_70097_1_, float p_70097_2_);


    @Override
    public void faceEntity(Entity p_70625_1_, float p_70625_2_, float p_70625_3_)
    {
        double d0 = p_70625_1_.posX - this.posX;
        double d2 = p_70625_1_.posZ - this.posZ;
        double d1;

        if (p_70625_1_ instanceof EntityLivingBase)
        {
            EntityLivingBase entitylivingbase = (EntityLivingBase)p_70625_1_;
            d1 = entitylivingbase.posY + (double)entitylivingbase.getEyeHeight() - (this.posY + (double)this.eyeHeight());
        }
        else
        {
            d1 = (p_70625_1_.boundingBox.minY + p_70625_1_.boundingBox.maxY) / 2.0D - (this.posY + (double)this.eyeHeight());
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


    @Inject(method = "onKillEntity", at = @At("TAIL"))
    private void onKillEntityHook(EntityLivingBase entity, CallbackInfo ci)
    {
        this.onKilledEntity(entity);
    }


    @Override
    public void writeToNBTGGM(NBTTagCompound nbt)
    {
        this.writeEntityToNBT(nbt);
    }

    @Shadow protected abstract void writeEntityToNBT(NBTTagCompound p_70014_1_);

    @Override
    public void readFromNBTGGM(NBTTagCompound nbt)
    {
        this.readEntityFromNBT(nbt);
    }

    @Shadow protected abstract void readEntityFromNBT(NBTTagCompound p_70037_1_);

}
