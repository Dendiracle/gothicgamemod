package mrfinger.gothicgamemod.entity;

import mrfinger.gothicgamemod.wolrd.IGGMWorld;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public interface IGGMEntity {


    int getEntityId();

    IGGMWorld getEntityWorld();


    boolean isEntityAlive();


    double getPosX();

    double getPosY();

    double getPosZ();


    float getDistanceToEntity(Entity entity);


    boolean canEntityBeSeen(Entity entity);

}
