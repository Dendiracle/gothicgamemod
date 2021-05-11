package mrfinger.gothicgamemod.entity;

import mrfinger.gothicgamemod.wolrd.IGGMWorld;
import net.minecraft.entity.Entity;

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


    float getDistanceToEntity(Entity entity);

    double getDistanceSq(double x, double y, double z);

    double getDistance(double x, double y, double z);

    double getDistanceSqToEntity(Entity entity);


    boolean canEntityBeSeen(Entity entity);


    default Random getRand()
    {
        return null;
    }


    default boolean inCreative()
    {
        return false;
    }

}
