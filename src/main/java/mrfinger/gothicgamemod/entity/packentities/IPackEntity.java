package mrfinger.gothicgamemod.entity.packentities;

import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.fractions.PackFraction;
import mrfinger.gothicgamemod.wolrd.IGGMWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;

import java.util.*;

public interface IPackEntity
{

    IGGMWorld getWorld();

    UUID getId();

    PackFraction getFraction();


    void setWorld(IGGMWorld world);


    void setPos(double x, double y, double z);


    IEntityHerd getLeader();

    boolean setLeader(IEntityHerd leader);

    Set<IEntityHerd> getEntitiesSet();

    void addEntity(IEntityHerd entity);

    void addEntities(Collection<IEntityHerd> entities);

    void removeEntityFromPack(IEntityHerd entity);

    void removeEntities(Collection<IEntityHerd> entities);


    boolean isPackToRemove();


    void addEnemy(IGGMEntity entity);

    void addEnemy(IGGMEntity entity, int count);


    float getRadius();

    float getMaxRangeToMembers();

    void setSize(float rad, float height);


    void onUpdatePackAI();


    boolean isJustLiving();

    boolean isAttacking();


    double getDistanceSQ(double x, double y, double z);

    public double getDistanceTo(double x, double y, double z);

    public double getDistanceSQToEntity(IGGMEntity entity);

    public double getDistanceToEntity(IGGMEntity entity);


    void writePackToNBT(NBTTagCompound nbt);

    void readPackFromNBT(NBTTagCompound nbt);
}
