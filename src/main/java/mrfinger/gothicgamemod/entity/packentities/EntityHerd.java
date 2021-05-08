package mrfinger.gothicgamemod.entity.packentities;

import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.fractions.PackFraction;
import mrfinger.gothicgamemod.init.GGMFractions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public abstract class EntityHerd extends EntityLiving implements IEntityHerd {


    protected PackEntity pack;

    protected IGGMEntity entityToAttack;

    protected int chaseCount;

    protected PathEntity pathToEntity;


    public EntityHerd(World world)
    {
        super(world);
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
    public int getDefaultChaseDuration()
    {
        return 100;
    }


    @Override
    public IGGMEntity getEntityToAttack() {
        return this.entityToAttack;
    }

    @Override
    public void setEntityToAttack(IGGMEntity entity)
    {
        this.entityToAttack = entity;
        if (entity != null) this.chaseCount = this.getDefaultChaseDuration();
        else this.chaseCount = 0;
    }

    @Override
    public void setEntityToAttack(IGGMEntity entity, int chaseDuration)
    {
        this.entityToAttack = entity;
        if (entity != null) this.chaseCount = chaseDuration;
        else this.chaseCount = 0;
    }

    @Override
    protected void updateEntityActionState()
    {
        this.worldObj.theProfiler.startSection("ai");

        if (this.entityToAttack != null && this.entityToAttack.isEntityAlive())
        {
            if (this.canEntityBeSeen((Entity) this.entityToAttack))
            {
                this.tryAttack(this.entityToAttack);
            }
        }
        else
        {
            this.entityToAttack = null;
        }

        if (this.entityToAttack instanceof EntityPlayerMP && ((EntityPlayerMP)this.entityToAttack).theItemInWorldManager.isCreative())
        {
            this.entityToAttack = null;
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
            this.pathToEntity = null;
        }
    }


    @Override
    public void updatePathFindingToEntityToAttack()
    {
        this.pathToEntity = this.worldObj.getPathEntityToEntity(this, (Entity) this.entityToAttack, 16.0F, true, false, false, true);
    }
}
