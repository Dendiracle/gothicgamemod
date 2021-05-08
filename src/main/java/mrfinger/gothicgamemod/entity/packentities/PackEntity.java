package mrfinger.gothicgamemod.entity.packentities;

import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.fractions.PackFraction;
import mrfinger.gothicgamemod.init.GGMFractions;
import mrfinger.gothicgamemod.wolrd.IGGMWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PackEntity {


    protected IGGMWorld world;

    protected double posX;
    protected double posY;
    protected double posZ;

    protected float rad;
    protected float height;

    protected AxisAlignedBB aabb;

    protected PackFraction fraction;

    protected Set<IEntityHerd> entitiesSet;

    protected Set<IGGMEntityLivingBase> enemiesSet;

    protected float aggrLevel;
    protected float aggressiveness;


    public PackEntity(IGGMWorld world)
    {
        this(world, GGMFractions.neutralPackFraction);
    }

    public PackEntity(IGGMWorld world, PackFraction fraction)
    {
        System.out.println("Debug in PackEntity init");
        this.world = world;
        this.fraction = fraction;

        this.entitiesSet = new HashSet<>();
        this.enemiesSet = new HashSet<>();
    }


    public void setPos(double x, double y, double z)
    {
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        this.aabb.setBounds(x - this.rad, y, z - this.rad, x + this.rad, y + this.height, z + this.rad);
    }

    public void addEntityToPack(IEntityHerd entity)
    {
        if (this.entitiesSet.add(entity))
        {
            this.setSize((float) calculatePackRadiusWithAdd(this.rad, entity.getNeedSpaceMultiplier() * this.fraction.getSpaceForEntity()), this.height);
        }
    }

    public void removeEntityFromPack(IEntityHerd entity)
    {
        if (this.entitiesSet.remove(entity))
        {
            this.setSize((float) calculatePackRadiusWithout(this.rad, entity.getNeedSpaceMultiplier() * this.fraction.getSpaceForEntity()), this.height);
        }
    }


    public float getRadius()
    {
        return rad;
    }

    public float getMaxRangeToMembers()
    {
        return this.getRadius() * 4.0F;
    }

    public void setSize(float rad, float height)
    {
        this.rad = rad;
        this.height = height;
        this.aabb.setBounds(this.posX - this.rad, this.posY, this.posZ - this.rad, this.posX + this.rad, this.posY + this.height, this.posZ + this.rad);
    }


    public PackFraction getFraction() {
        return fraction;
    }

    public void setFraction(PackFraction fraction)
    {
        this.fraction = fraction;
    }

    public void addEnemy(IGGMEntityLivingBase entity)
    {
        this.enemiesSet.add(entity);
    }


    public void onHalfSecUpdate()
    {
        List<IGGMEntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.aabb);

        if (list != null)
        {
            list.removeAll(this.entitiesSet);

            if (list.isEmpty())
            {
                this.aggrLevel = 0.0F;
            }
            else if (this.aggrLevel < 1.0F)
            {
                for (IGGMEntityLivingBase annoyer : list) {

                    if (this.fraction.enemiesSet.contains(annoyer.getClass()))
                    {
                        float d = (float) this.getDistanceTo(annoyer.getPosX(), annoyer.getPosY(), annoyer.getPosZ());

                        if (d < this.rad) {

                            for (IEntityHerd entity : this.entitiesSet)
                            {
                                if (entity.canEntityBeSeen((Entity) annoyer))
                                {
                                    this.aggrLevel += this.aggressiveness * ((this.rad - d) / this.rad);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        else
        {
            this.aggrLevel = 0.0F;
        }

        if (this.aggrLevel >= 1.0F)
        {
            this.aggrLevel = 1.0F;

            if (list.size() == 1)
            {
                IGGMEntityLivingBase entityToAttack = list.get(0);

                for (IEntityHerd entity : this.entitiesSet)
                {
                    entity.setEntityToAttack(entityToAttack, this.getChaseDuration(entity));
                }
            }
            else
            {
                for (IEntityHerd entity : this.entitiesSet)
                {
                    int index = 0;
                    float minDistance = entity.getDistanceToEntity((Entity) list.get(0));

                    for (int i = 1; i < list.size(); ++i)
                    {
                        float distance = entity.getDistanceToEntity((Entity) list.get(i));

                        if (distance < minDistance)
                        {
                            index = i;
                            minDistance = distance;
                        }
                    }

                    entity.setEntityToAttack(list.get(index), this.getChaseDuration(entity));
                }
            }

            for (IEntityHerd entity : this.entitiesSet)
            {
                entity.updatePathFindingToEntityToAttack();
            }
        }
    }


    public int getChaseDuration(IEntityHerd entity)
    {
        return 100;
    }


    public double getDistanceSQ(double x, double y, double z)
    {
        x -= this.posX;
        y -= this.posZ;
        z -= this.posZ;

        return x * x + y * y + z * z;
    }

    public double getDistanceTo(double x, double y, double z)
    {
        return Math.sqrt(this.getDistanceSQ(x, y, z));
    }


    public double getDistanceSQToEntity(IGGMEntity entity)
    {
        return this.getDistanceSQ(entity.getPosX(), entity.getPosY(), entity.getPosZ());
    }

    public double getDistanceToEntity(IGGMEntity entity)
    {
        return this.getDistanceTo(entity.getPosX(), entity.getPosY(), entity.getPosZ());
    }


    public double calculatePackRadiusWithAdd(double currentRadius, double addedSpace)
    {
        return Math.sqrt((currentRadius * currentRadius * Math.PI + addedSpace) / Math.PI);
    }

    public double calculatePackRadiusWithout(double currentRadius, double removedSpace)
    {
        return Math.sqrt((currentRadius * currentRadius * Math.PI - removedSpace) / Math.PI);
    }

}
