package mrfinger.gothicgamemod.entity;

import mrfinger.gothicgamemod.wolrd.IGGMWorld;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;

import java.util.Random;

public interface IGGMEntity {


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


    float getDistanceToEntity(Entity entity);

    double getDistanceSq(double x, double y, double z);

    double getDistance(double x, double y, double z);

    double getDistanceSqToEntity(Entity entity);


    boolean canEntityBeSeen(Entity entity);


    default Random getRand()
    {
        return null;
    }


    boolean isRiding();

    default Entity getRidingEntity()
    {
        return null;
    }


    boolean attackEntityFrom(DamageSource damageSource, float damage);


    default boolean inCreative()
    {
        return false;
    }

}
