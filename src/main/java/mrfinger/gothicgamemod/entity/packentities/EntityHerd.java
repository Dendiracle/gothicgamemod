package mrfinger.gothicgamemod.entity.packentities;

import mrfinger.gothicgamemod.GothicMain;
import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.ai.GGMPathNavigate;
import mrfinger.gothicgamemod.entity.animations.AnimationEntityHerdLiving;
import mrfinger.gothicgamemod.entity.animations.IAnimation;
import mrfinger.gothicgamemod.entity.animations.episodes.IAnimationEpisode;
import mrfinger.gothicgamemod.fractions.PackFraction;
import mrfinger.gothicgamemod.init.GGMEntityAnimations;
import mrfinger.gothicgamemod.init.GGMFractions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import java.util.Map;

public abstract class EntityHerd extends EntityLiving implements IEntityHerd
{

    protected PackEntity pack;


    public EntityHerd(World world)
    {
        super(world);

        this.navigator = new GGMPathNavigate(this, world);
        this.getNavigator().setAvoidsWater(this.isAvoidsWater());
    }


    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();

        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(128D);
    }

    @Override
    public IAnimation getNewDefaultAnimation()
    {
        return new AnimationEntityHerdLiving(this);
    }

    @Override
    public PackFraction getFraction() {
        return this.getStandartFraction();
    }

    @Override
    public PackFraction getStandartFraction()
    {
        return GGMFractions.neutralPackFraction;
    }


    @Override
    public void onAddToPack(PackEntity pack)
    {
    }

    @Override
    public void onRemoveFromPack(PackEntity pack) {}


    @Override
    public PackEntity findNewPack()
    {
        PackEntity pack = this.getEntityWorld().findRightPack(this);

        if (this.pack != pack)
        {
            if (this.pack != null) this.pack.removeEntityFromPack(this);
            pack.addEntityToPack(this);
            this.pack = pack;
        }

        return pack;
    }


    @Override
    public float getNeedSpaceMultiplier()
    {
        return 1.0F;
    }


    @Override
    protected boolean isAIEnabled()
    {
        return true;
    }

    @Override
    protected void updateAITasks()
    {
        super.updateAITasks();
    }

    @Override
    public int getDefaultChaseDuration()
    {
        return 100;
    }


    @Override
    public void setAttackTarget(EntityLivingBase entity)
    {
        super.setAttackTarget(entity);

        if (entity != null && entity.isEntityAlive())
        {
            this.setSprinting(true);
        }
        else
        {
            this.setSprinting(false);
        }
    }


    @Override
    public void onEntityUpdate()
    {
        if (!this.worldObj.isRemote && this.pack == null)
        {
            this.findNewPack();
            System.out.println("Debug in EntityHerd pack " + pack);
        }

        super.onEntityUpdate();
    }

    /*@Override
    protected void updateEntityActionState()
    {
        this.worldObj.theProfiler.startSection("ai");

        if (this.entityToAttack != null)
        {
            if (this.entityToAttack.inCreative())
            {
                this.nullifyEntityToAttack();
            }
            else if (this.entityToAttack.isEntityAlive())
            {
                if (this.canEntityBeSeen((Entity) this.entityToAttack))
                {
                    float distance = (float) this.entityToAttack.getDistanceSqToEntity(this);

                    if (distance < 9.0F)
                    {
                        this.chaseCount = this.getDefaultChaseDuration();
                    }

                    this.updatePathFindingToEntityToAttack();

                    this.tryAttack(this.entityToAttack, distance);
                }

                if (--this.chaseCount == 0)
                {
                    this.nullifyEntityToAttack();
                }
            }
            else
            {
                this.nullifyEntityToAttack();
            }
        }

        this.worldObj.theProfiler.endSection();

        int i = MathHelper.floor_double(this.boundingBox.minY + 0.5D);
        boolean flag = this.isInWater();
        boolean flag1 = this.handleLavaMovement();
        this.rotationPitch = 0.0F;

        if (this.pathToEntity != null)
        {
            this.worldObj.theProfiler.startSection("followpath");
            Vec3 vec3 = this.pathToEntity.getPosition(this);
            double d0 = (double)(this.width * 2.0F);

            while (vec3 != null && vec3.squareDistanceTo(this.posX, vec3.yCoord, this.posZ) < d0 * d0)
            {
                this.pathToEntity.incrementPathIndex();

                if (this.pathToEntity.isFinished())
                {
                    vec3 = null;
                    this.pathToEntity = null;

                    if (this.isCanJustWander())
                    {
                        IAnimationEpisode episode = this.getRandomJustLivingEpisode();
                        this.getCurrentAnimation().setAnimationEpisode(episode, episode.getStandartDuration());
                    }
                }
                else
                {
                    vec3 = this.pathToEntity.getPosition(this);
                }
            }

            this.isJumping = false;

            if (vec3 != null)
            {
                double d1 = vec3.xCoord - this.posX;
                double d2 = vec3.zCoord - this.posZ;
                double d3 = vec3.yCoord - (double)i;
                float f1 = (float)(Math.atan2(d2, d1) * 180.0D / Math.PI) - 90.0F;
                float f2 = MathHelper.wrapAngleTo180_float(f1 - this.rotationYaw);
                this.moveForward = (float)this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();

                if (f2 > 30.0F)
                {
                    f2 = 30.0F;
                }

                if (f2 < -30.0F)
                {
                    f2 = -30.0F;
                }

                this.rotationYaw += f2;

                if (d3 > 0.0D)
                {
                    this.isJumping = true;
                }
            }

            if (this.entityToAttack != null)
            {
                this.faceEntity((Entity) this.entityToAttack, 30.0F, 30.0F);
            }

            if (this.isCollidedHorizontally && this.pathToEntity != null || (this.rand.nextFloat() < 0.8F && (flag || flag1)))
            {
                this.isJumping = true;
            }

            this.worldObj.theProfiler.endSection();
        }
        else
        {
            super.updateEntityActionState();
        }
        this.rotationYaw = 0.0F;
    }*/


    @Override
    public void setPath(PathEntity path)
    {
        this.getNavigator().setPath(path, 1.0D);
    }

    @Override
    public void setPath(int x, int y, int z)
    {
        this.getNavigator().tryMoveToXYZ(x, y, z, 1.0D);
    }

    @Override
    public void setPathToEntity(IGGMEntity entity)
    {
        this.getNavigator().tryMoveToEntityLiving((Entity) entity, 1.0D);
    }

    @Override
    public void cleartPath()
    {
        this.getNavigator().clearPathEntity();
    }

    @Override
    public float getBlockPathWeight(int x, int y, int z)
    {
        return 0.0F;
    }


    @Override
    public boolean isCanJustWander()
    {
        return this.onGround && this.isEntityAlive() && this.getNavigator().noPath() && this.getCurrentAnimation().getEpisode() == null;
    }

    public Map<String, IAnimationEpisode> getAnimationEpisodesMap()
    {
        return null;
    }

    @Override
    public void onNewAIFinishedPath()
    {
        if (this.getCurrentAnimation() == this.getDefaultAnimation() && this.getCurrentAnimation().getEpisode() == null)
        {
            IAnimationEpisode episode = this.getRandomJustLivingEpisode();
            this.getCurrentAnimation().setAnimationEpisode(episode, episode.getStandartDuration());
        }
    }


    @Override
    public boolean allowLeashing()
    {
        return !this.getLeashed();
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt)
    {
        super.writeEntityToNBT(nbt);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt)
    {
        super.readEntityFromNBT(nbt);
    }

}
