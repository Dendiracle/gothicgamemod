package mrfinger.gothicgamemod.entity;

import mrfinger.gothicgamemod.util.IGGMDamageSource;
import mrfinger.gothicgamemod.wolrd.IGGMWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;

import java.util.Random;

public interface IGGMEntity
{

    default int getEntityID()
    {
        return 0;
    }

    default IGGMWorld getEntityWorld()
    {
        return null;
    }


    default boolean entityAlive()
    {
        return false;
    }


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

    default float eyeHeight()
    {
        return 0F;
    }

    default float getRotationYaw()
    {
        return 0.0F;
    }

    default float getHeadRotationYaw()
    {
        return 0F;
    }

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


    default boolean onGround()
    {
        return true;
    }


    //void setLocationAndAngles(double p_70012_1_, double p_70012_3_, double p_70012_5_, float p_70012_7_, float p_70012_8_);


    default float getDistanceToEntity(IGGMEntity entity)
    {
        return 0F;
    }

    //double getDistanceSq(double x, double y, double z);

    //double getDistance(double x, double y, double z);

    default double getDistanceSqToEntity(IGGMEntity entity)
    {
        return 0D;
    }


    default float getWidth()
    {
        return 0F;
    }

    default float getHeight()
    {
        return 0F;
    }


    default boolean canEntityBeSeen(IGGMEntity entity)
    {
        return false;
    }

    default void faceEntity(Entity p_70625_1_, float p_70625_2_, float p_70625_3_) {}


    default Random getRNG()
    {
        return null;
    }

    //void playSound(String name, float volume, float pitch);


    default boolean isCanMount(Entity entity)
    {
        return true;
    }

    default boolean isOnMount()
    {
        return false;
    }

    default Entity getRidingEntity()
    {
        return null;
    }


    default boolean attackEntityFrom(IGGMDamageSource damageSource, float damage)
    {
        return false;
    }


    default void onKilledEntity(EntityLivingBase entity)
    {
        this.onKilledEntityBase(entity);
    }

    default void onKilledEntityBase(EntityLivingBase entityLivingBase) {}


    default boolean inCreative()
    {
        return false;
    }


    void writeToNBTGGM(NBTTagCompound nbt);

    void readFromNBTGGM(NBTTagCompound nbt);

}
