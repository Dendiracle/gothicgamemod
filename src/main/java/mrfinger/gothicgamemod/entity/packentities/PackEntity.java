package mrfinger.gothicgamemod.entity.packentities;

import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.fractions.PackFraction;
import mrfinger.gothicgamemod.init.GGMFractions;
import mrfinger.gothicgamemod.wolrd.IGGMWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;

import java.util.*;

public class PackEntity implements IPackEntity
{

    protected IGGMWorld world;
    protected final UUID id;
    protected final PackFraction fraction;

    protected double posX;
    protected double posY;
    protected double posZ;

    protected float rad;
    protected float height;

    protected AxisAlignedBB aabb;

    protected IEntityHerd leader;
    protected Set<IEntityHerd> entitiesSet;
    protected Map<IGGMEntity, EnemyEntry> enemiesMap;

    protected float aggrLevel;
    protected float aggressiveness;


    public PackEntity(IGGMWorld world)
    {
        this(world, GGMFractions.neutralPackFraction);
    }

    public PackEntity(IGGMWorld world, PackFraction fraction)
    {
        this(world, fraction, null);
    }

    public PackEntity(IGGMWorld world, PackFraction fraction, IEntityHerd leader)
    {
        this(world, UUID.randomUUID(), fraction, leader);
    }

    public PackEntity(IGGMWorld world, UUID id, PackFraction fraction, IEntityHerd leader)
    {
        this.world = world;
        this.id = id;
        this.fraction = fraction;

        this.aabb = AxisAlignedBB.getBoundingBox(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);

        this.leader = leader;
        this.entitiesSet = new HashSet<>();
        this.enemiesMap = new HashMap<>();

        this.aggressiveness = 0.3F;
    }


    @Override
    public IGGMWorld getWorld()
    {
        return this.world;
    }

    @Override
    public UUID getId()
    {
        return this.id;
    }

    @Override
    public PackFraction getFraction()
    {
        return fraction;
    }


    @Override
    public void setPos(double x, double y, double z)
    {
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        this.aabb.setBounds(x - this.rad, y, z - this.rad, x + this.rad, y + this.height, z + this.rad);
    }


    @Override
    public IEntityHerd getLeader()
    {
        return leader;
    }

    @Override
    public boolean setLeader(IEntityHerd leader)
    {
        if (this.entitiesSet.contains(leader))
        {
            this.leader = leader;
            return true;
        }

        return false;
    }

    protected void reChooseLeader()
    {
        for (IEntityHerd entity : this.entitiesSet)
        {
            if (entity.isEntityAlive())
            {
                this.leader = entity;
                break;
            }
        }
    }


    @Override
    public Set<IEntityHerd> getEntitiesSet() {
        return entitiesSet;
    }

    @Override
    public void addEntityToPack(IEntityHerd entity)
    {
        if (this.entitiesSet.add(entity))
        {
            if (this.entitiesSet.size() == 1)
            {
                this.setLeader(entity);
            }
            entity.onAddToPack(this);
            this.setSize((float) calculatePackRadiusWithAdd(this.rad, entity.getNeedSpaceMultiplier() * this.fraction.getSpaceForEntity()), this.height);
        }
    }

    @Override
    public void removeEntityFromPack(IEntityHerd entity)
    {
        if (this.entitiesSet.remove(entity))
        {
            entity.onRemoveFromPack(this);
            if (entity == this.leader)
            {
                this.reChooseLeader();
            }
            this.setSize((float) calculatePackRadiusWithout(this.rad, entity.getNeedSpaceMultiplier() * this.fraction.getSpaceForEntity()), this.height);
        }
    }


    @Override
    public boolean isPackToRemove()
    {
        return this.entitiesSet.isEmpty();
    }


    @Override
    public void addEnemy(IGGMEntity entity)
    {
        this.addEnemy(entity, this.fraction.getStandartRevengeDuration());
    }

    @Override
    public void addEnemy(IGGMEntity entity, int count)
    {
        this.enemiesMap.put(entity, new EnemyEntry(entity, count));
    }


    @Override
    public float getRadius()
    {
        return rad;
    }

    @Override
    public float getMaxRangeToMembers()
    {
        return this.getRadius() + 128.0F;
    }

    @Override
    public void setSize(float rad, float height)
    {
        this.rad = rad;
        this.height = height;
        this.aabb.setBounds(this.posX - this.rad, this.posY, this.posZ - this.rad, this.posX + this.rad, this.posY + this.height, this.posZ + this.rad);
    }


    @Override
    public void onUpdatePackAI()
    {
        this.checkDeads();

        this.updateEnemiesMap();

        List<IGGMEntity> entitiesToAttackList = new ArrayList<>();
        float squareRad = this.rad * this.rad;

        for (IGGMEntity enemy : this.enemiesMap.keySet())
        {
            if (this.getDistanceSQToEntity(enemy) < squareRad)
            {
                entitiesToAttackList.add(enemy);
                this.aggrLevel = 1.0F;
            }
        }


        {
            List<IGGMEntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.aabb);

            if (list != null)
            {
                list.removeAll(this.entitiesSet);

                for (IGGMEntityLivingBase annoyer : list)
                {

                    if (this.fraction.enemiesSet.contains(annoyer.getFraction()) && !annoyer.inCreative())
                    {
                        float squareDist = (float) this.getDistanceSQToEntity(annoyer);

                        if (squareDist < squareRad)
                        {
                            for (IEntityHerd packMember : this.entitiesSet)
                            {
                                if (packMember.canEntityBeSeen((Entity) annoyer))
                                {
                                    if (this.aggrLevel < 1.0F)
                                    {
                                        this.aggrLevel += this.aggressiveness * ((squareRad - squareDist) / squareRad);
                                    }

                                    entitiesToAttackList.add(annoyer);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }

        if (entitiesToAttackList.isEmpty())
        {
            if (this.aggrLevel > 0F)
            {
                this.aggrLevel -= this.aggressiveness;
                if (this.aggrLevel < 0F) this.aggrLevel = 0F;
                this.clearAttackTargets();
            }
        }
        else if (this.aggrLevel > 0F)
        {
            if (this.aggrLevel > 1F) this.aggrLevel = 1.0F;

            if (entitiesToAttackList.size() == 1)
            {
                IGGMEntity entityToAttack = entitiesToAttackList.get(0);

                for (IEntityHerd entity : this.entitiesSet)
                {
                    entity.setAttackTarget((EntityLivingBase) entityToAttack);
                }
            }
            else
            {
                for (IEntityHerd entity : this.entitiesSet)
                {
                    int index = 0;
                    float minDistance = entity.getDistanceToEntity((Entity) entitiesToAttackList.get(0));

                    for (int i = 1; i < entitiesToAttackList.size(); ++i)
                    {
                        float distance = entity.getDistanceToEntity((Entity) entitiesToAttackList.get(i));

                        if (distance < minDistance)
                        {
                            index = i;
                            minDistance = distance;
                        }
                    }

                    entity.setAttackTarget((EntityLivingBase) entitiesToAttackList.get(index));
                }
            }
        }

        if (this.isJustLiving())
        {
            this.updateAwaykers();
            this.updateLiving();
        }
    }

    protected void checkDeads()
    {
        /*List<IEntityHerd> toRemove = new ArrayList();

        for (IEntityHerd entity : this.entitiesSet)
        {
            if (!entity.isEntityAlive())
            {
                toRemove.add(entity);
            }
        }

        for (IEntityHerd entity: toRemove)
        {
            this.removeEntityFromPack(entity);
        }*/
    }

    protected void updateEnemiesMap()
    {
        List<IGGMEntity> toRemove = new ArrayList<>();

        for (Map.Entry<IGGMEntity, EnemyEntry> e : this.enemiesMap.entrySet())
        {
            e.getValue().updateEntry();

            if (e.getValue().needRemoveEntry())
            {
                toRemove.add(e.getKey());
            }
        }

        for (IGGMEntity entity : toRemove)
        {
            this.enemiesMap.remove(entity);
        }
    }


    protected void clearAttackTargets()
    {
        for (IEntityHerd entity : this.entitiesSet)
        {
            entity.setAttackTarget(null);
        }
    }


    @Override
    public boolean isJustLiving()
    {
        return this.aggrLevel <= 0.0F;
    }

    @Override
    public boolean isAttacking()
    {
        return this.aggrLevel >= 1F;
    }


    protected void updateAwaykers()
    {
        float squareMaxRangeToMembers = this.getMaxRangeToMembers();
        squareMaxRangeToMembers *= squareMaxRangeToMembers;

        List<IEntityHerd> awaykers = new LinkedList();
        for (IEntityHerd entity : this.entitiesSet)
        {
            if (!entity.isEntityAlive())
            {
                awaykers.add(entity);
                continue;
            }

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
    }

    protected void updateLiving()
    {
        int size = this.entitiesSet.size();
        int i = size * 10;
        float range = this.rad - this.fraction.getSimplePackRange() * 0.5F;
        float rangeSQ = range * range;
        int doubleRange = MathHelper.floor_float(range * 2);
        int height = (int) (this.height - this.fraction.getSimplePackHeight() * 0.5F);

        for (IEntityHerd entity : this.entitiesSet)
        {
            if (entity.isCanJustWander() && (entity.isNeedWander() || this.getDistanceSQToEntity(entity) > rangeSQ))
            {
                Random random = entity.getRNG();
                int x = 0;
                int y = 0;
                int z = 0;
                float blockWeight = -0.1F;

                for (int l = 0; l < 5; ++l)
                {
                    int x1 = (int) this.posX + random.nextInt(doubleRange) - (int) range;
                    int y1 = (int) this.posY + random.nextInt(height);
                    int z1 = (int) this.posZ + random.nextInt(doubleRange) - (int) range;

                    float blockWeight1 = entity.getBlockPathWeight(x1, y1, z1);

                    if (blockWeight1 > blockWeight)
                    {
                        x = x1;
                        y = y1;
                        z = z1;
                        blockWeight = blockWeight1;
                    }
                }

                if (blockWeight >= 0.0F)
                {
                    entity.getNavigator().tryMoveToXYZ(x, y, z, entity.getWanderSpeedModifier());
                    entity.setIsNeedWander(false);
                }
            }
        }
    }


    @Override
    public double getDistanceSQ(double x, double y, double z)
    {
        x -= this.posX;
        y -= this.posY;
        z -= this.posZ;

        return x * x + y * y + z * z;
    }

    @Override
    public double getDistanceTo(double x, double y, double z)
    {
        return Math.sqrt(this.getDistanceSQ(x, y, z));
    }

    @Override
    public double getDistanceSQToEntity(IGGMEntity entity)
    {
        return this.getDistanceSQ(entity.getPosX(), entity.getPosY(), entity.getPosZ());
    }

    @Override
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


    @Override
    public void writePackToNBT(NBTTagCompound nbt)
    {
        nbt.setDouble("posx", this.posX);
        nbt.setDouble("posy", this.posY);
        nbt.setDouble("posz", this.posZ);
        nbt.setFloat("aggres", this.aggressiveness);
        nbt.setFloat("aggr", this.aggrLevel);
    }

    @Override
    public void readPackFromNBT(NBTTagCompound nbt)
    {
        this.posX = nbt.getDouble("posx");
        this.posY = nbt.getDouble("posy");
        this.posZ = nbt.getDouble("posz");
        this.aggressiveness = nbt.getFloat("aggres");
        this.aggrLevel = nbt.getFloat("aggr");
    }


    protected static class EnemyEntry
    {

        public final IGGMEntity enemy;
        private int count;


        public EnemyEntry(IGGMEntity enemy)
        {
            this(enemy, -1);
        }

        public EnemyEntry(IGGMEntity enemy, int duration)
        {
            this.enemy = enemy;
            this.count = duration;
        }


        public void updateEntry()
        {
            --this.count;
        }

        public boolean needRemoveEntry()
        {
            return this.count == 0 || !this.enemy.isEntityAlive();
        }
    }


    @Override
    public String toString()
    {
        return "EntityPack id: " + this.id + " coords: " + this.posX + " " + this.posY + " " + this.posZ;
    }
}
