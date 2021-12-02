package mrfinger.gothicgamemod.entity;

import mrfinger.gothicgamemod.wolrd.IGGMWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;

import java.util.Random;

public interface IGGMEntity
{

    int getEntityId();

    default IGGMWorld getEntityWorld()
    {
        return null;
    }


    boolean isEntityAlive();


    default double getPosX()
    {
        return 0.0D;
    }

    default double getPosY()
    {
        return 0.0D;
    }

    default double getPosZ()
    {
        return 0.0D;
    }

    float getEyeHeight();

    default float getRotationYaw()
    {
        return 0.0F;
    }

    float getRotationYawHead();

    default float getRotationPitch()
    {
        return 0.0F;
    }


    default double getMotionX()
    {
        return 0.0D;
    }

    default double getMotionY()
    {
        return 0.0D;
    }

    default double getMotionZ()
    {
        return 0.0D;
    }


    void setLocationAndAngles(double p_70012_1_, double p_70012_3_, double p_70012_5_, float p_70012_7_, float p_70012_8_);


    float getDistanceToEntity(Entity entity);

    double getDistanceSq(double x, double y, double z);

    double getDistance(double x, double y, double z);

    double getDistanceSqToEntity(Entity entity);


    default float getWidth()
    {
        return 0F;
    }

    default float getHeight()
    {
        return 0F;
    }


    boolean canEntityBeSeen(Entity entity);

    default void faceEntity(Entity p_70625_1_, float p_70625_2_, float p_70625_3_) {}


    default Random getRNG()
    {
        return null;
    }

    void playSound(String name, float volume, float pitch);


    default boolean isCanMount(Entity entity)
    {
        return true;
    }

    boolean isRiding();

    default Entity getRidingEntity()
    {
        return null;
    }


    boolean attackEntityFrom(DamageSource damageSource, float damage);


    default void onKilledEntity(EntityLivingBase entity)
    {
        this.onKilledEntityBase(entity);
    }

    default void onKilledEntityBase(EntityLivingBase entityLivingBase) {}


    default boolean inCreative()
    {
        return false;
    }


    void writeToNBT(NBTTagCompound nbt);

    void readFromNBT(NBTTagCompound nbt);

}
