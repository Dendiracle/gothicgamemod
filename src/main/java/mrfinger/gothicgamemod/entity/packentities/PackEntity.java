package mrfinger.gothicgamemod.entity.packentities;

import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.fractions.PackFraction;
import mrfinger.gothicgamemod.init.GGMFractions;
import mrfinger.gothicgamemod.wolrd.IGGMWorld;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;

import java.util.*;

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
        this.aabb = AxisAlignedBB.getBoundingBox(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);

        this.entitiesSet = new HashSet<>();

        this.aggressiveness = 0.3F;
    }


    public void setPos(double x, double y, double z)
    {
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        this.aabb.setBounds(x - this.rad, y, z - this.rad, x + this.rad, y + this.height, z + this.rad);
    }


    public Set<IEntityHerd> getEntitiesSet() {
        return entitiesSet;
    }

    public void addEntityToPack(IEntityHerd entity)
    {
        if (this.entitiesSet.add(entity))
        {
            entity.onAddToPack(this);
            this.setSize((float) calculatePackRadiusWithAdd(this.rad, entity.getNeedSpaceMultiplier() * this.fraction.getSpaceForEntity()), this.height);
        }
    }

    public void removeEntityFromPack(IEntityHerd entity)
    {
        if (this.entitiesSet.remove(entity))
        {
            entity.onRemoveFromPack(this);
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
                int countOfNotEnemies = 0;
                for (IGGMEntityLivingBase annoyer : list)
                {
                    if (!annoyer.inCreative() && this.fraction.enemiesSet.contains(annoyer.getFraction()))
                    {
                        float squareDist = (float) this.getDistanceSQToEntity(annoyer);
                        float squareRad = this.rad * this.rad;

                        if (squareDist < squareRad)
                        {
                            for (IEntityHerd entity : this.entitiesSet)
                            {
                                if (entity.canEntityBeSeen((Entity) annoyer))
                                {
                                    this.aggrLevel += this.aggressiveness * ((squareRad - squareDist) / squareRad);
                                    break;
                                }
                                else
                                {
                                    ++countOfNotEnemies;

                                    if (countOfNotEnemies == list.size())
                                    {
                                        this.aggrLevel = 0.0F;
                                    }
                                }
                            }
                        }
                        else
                        {
                            ++countOfNotEnemies;

                            if (countOfNotEnemies == list.size())
                            {
                                this.aggrLevel = 0.0F;
                            }
                        }
                    }
                    else
                    {
                        ++countOfNotEnemies;

                        if (countOfNotEnemies == list.size())
                        {
                            this.aggrLevel = 0.0F;
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
        }

        float squareMaxRangeToMembers = this.getMaxRangeToMembers();
        squareMaxRangeToMembers *= squareMaxRangeToMembers;

        List<IEntityHerd> awaykers = new LinkedList();
        for (IEntityHerd entity : this.entitiesSet)
        {
            double squareDistance = this.getDistanceSQToEntity(entity);

            if (squareDistance > squareMaxRangeToMembers)
            {
                awaykers.add(entity);
            }
        }

        for (IEntityHerd awayker : awaykers)
        {
            this.removeEntityFromPack(awayker);
        }

        if (this.aggrLevel <= 0.0F)
        {
            int size = this.entitiesSet.size();
            int i = size * 10;
            int range = MathHelper.floor_float(this.rad - this.fraction.getSimplePackRange() * 0.5F);
            int doubleRange = range * 2;
            int height = (int) (this.height - this.fraction.getSimplePackHeight() * 0.5F);
            //System.out.println("Debug in PackEntity just living");
            //System.out.println(" packsize " + size + " rad " + this.rad + " range " + range + " height " + height);
            for (IEntityHerd entity : this.entitiesSet)
            {
                Random random = entity.getRand();

                if (entity.canJustLive() && random.nextInt(i) < size)
                {
                    int x = 0;
                    int y = 0;
                    int z = 0;
                    float blockWeight = -0.1F;

                    for (int l = 0; l < 5; ++l)
                    {
                        int x1 = (int) this.posX + random.nextInt(MathHelper.floor_float(doubleRange)) - range;
                        int y1 = (int) this.posY + random.nextInt(height);
                        int z1 = (int) this.posZ + random.nextInt(MathHelper.floor_float(doubleRange)) - range;

                        float blockWeight1 = entity.getBlockPathWeight(x1, y1, z1);

                        if (blockWeight1 > blockWeight)
                        {
                            x = x1;
                            y = y1;
                            z = z1;
                            blockWeight = blockWeight1;
                        }
                    }
                    //System.out.println(x + " " + y + " " + z + " blockeweight " + blockWeight);
                    if (blockWeight >= 0.0F)
                    {
                        entity.setPath(this.world.getEntityPathToXYZ((Entity) entity, x, y, z, 10.0F, true, false, false, true));
                    }
                }



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
        y -= this.posY;
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
