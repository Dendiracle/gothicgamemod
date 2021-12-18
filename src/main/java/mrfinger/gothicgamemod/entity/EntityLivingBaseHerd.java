package mrfinger.gothicgamemod.entity;

/*
public abstract class EntityLivingBaseHerd extends EntityLivingBase implements IEntityHerd
{

    protected PackEntity pack;

    protected IGGMEntity entityToAttack;

    protected int chaseCount;

    protected PathEntity pathToEntity;


    public EntityLivingBaseHerd(World world)
    {
        super(world);

        this.setDefaulAnimationHelper(new AnimationGothicAnimalLiving(this));
        this.setActiveAnimationDirectly(this.getDefaultAnimationHelper());
    }


    @Override
    public PackFraction getCurrentFraction() {
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
    public void onRemoveFromPack(PackEntity pack)
    {
        if (this.chaseCount == 0)
        {
            this.findNewPack();
        }
    }


    @Override
    public PackEntity findNewPack()
    {
        PackEntity pack = this.getEntityWorld().findRightPack(this);

        if (this.pack != pack)
        {
            if (this.pack != null) this.pack.removeEntityFromPack(this);
            pack.addEntity(this);
            this.pack = pack;
        }

        return pack;
    }


    @Override
    public float getNeedSpaceInHabitat()
    {
        return 1.0F;
    }

    @Override
    public void onLivingUpdate()
    {
        if (this.jumpTicks > 0)
        {
            --this.jumpTicks;
        }

        if (this.newPosRotationIncrements > 0)
        {
            double d0 = this.posX + (this.newPosX - this.posX) / (double)this.newPosRotationIncrements;
            double d1 = this.posY + (this.newPosY - this.posY) / (double)this.newPosRotationIncrements;
            double d2 = this.posZ + (this.newPosZ - this.posZ) / (double)this.newPosRotationIncrements;
            double d3 = MathHelper.wrapAngleTo180_double(this.newRotationYaw - (double)this.rotationYaw);
            this.rotationYaw = (float)((double)this.rotationYaw + d3 / (double)this.newPosRotationIncrements);
            this.rotationPitch = (float)((double)this.rotationPitch + (this.newRotationPitch - (double)this.rotationPitch) / (double)this.newPosRotationIncrements);
            --this.newPosRotationIncrements;
            this.setPosition(d0, d1, d2);
            this.setRotation(this.rotationYaw, this.rotationPitch);
        }
        else if (this.worldObj.isRemote)
        {
            this.motionX *= 0.98D;
            this.motionY *= 0.98D;
            this.motionZ *= 0.98D;
        }

        if (Math.abs(this.motionX) < 0.005D)
        {
            this.motionX = 0.0D;
        }

        if (Math.abs(this.motionY) < 0.005D)
        {
            this.motionY = 0.0D;
        }

        if (Math.abs(this.motionZ) < 0.005D)
        {
            this.motionZ = 0.0D;
        }

        this.worldObj.theProfiler.startSection("ai");

        if (this.isMovementBlocked())
        {
            this.isJumping = false;
            this.moveStrafing = 0.0F;
            this.moveForward = 0.0F;
            this.randomYawVelocity = 0.0F;
        }
        else if (!this.worldObj.isRemote)
        {
            this.worldObj.theProfiler.startSection("newAi");
            this.updateAITasks();
            this.worldObj.theProfiler.endSection();
        }

        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("jump");

        if (this.isJumping)
        {
            if (!this.isInWater() && !this.handleLavaMovement())
            {
                if (this.onGround && this.jumpTicks == 0)
                {
                    this.jump();
                    this.jumpTicks = 10;
                }
            }
            else
            {
                this.motionY += 0.03999999910593033D;
            }
        }
        else
        {
            this.jumpTicks = 0;
        }

        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("travel");
        this.moveStrafing *= 0.98F;
        this.moveForward *= 0.98F;
        this.randomYawVelocity *= 0.9F;
        this.moveEntityWithHeading(this.moveStrafing, this.moveForward);
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("push");

        if (!this.worldObj.isRemote)
        {
            this.collideWithNearbyEntities();
        }

        this.worldObj.theProfiler.endSection();
    }


    @Override
    public int getDefaultChaseDuration()
    {
        return 100;
    }

    @Override
    public IGGMEntity getEntityToAttack()
    {
        return this.entityToAttack;
    }

    @Override
    public void setAttackTarget(IGGMEntity entity)
    {
        this.entityToAttack = entity;
        if (entity != null)
        {
            this.chaseCount = this.getDefaultChaseDuration();
            this.setSprinting(true);
        }
        else
        {
            this.chaseCount = 0;
            this.setSprinting(false);
        }
    }

    @Override
    public void setAttackTarget(IGGMEntity entity, int chaseDuration)
    {
        this.entityToAttack = entity;
        if (entity != null) this.chaseCount = chaseDuration;
        else this.chaseCount = 0;
    }

    @Override
    protected void updateEntityActionState()
    {
        this.worldObj.theProfiler.startSection("ai");

        if (this.pack == null)
        {
            this.findNewPack();
        }

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

                    if (this.entityToAttack == null && this.getActiveAnimation().getAnimationEpisode() == null)
                    {
                        IAnimationEpisode episode = this.getRandomJustLivingEpisode();
                        this.getActiveAnimation().setAnimationEpisode(episode, episode.getStandartDuration());
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
                this.moveForward = (float)this.getEntityAttributeInstance(SharedMonsterAttributes.movementSpeed).getAttributeValue();

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
    }


    public void faceEntity(Entity p_70625_1_, float p_70625_2_, float p_70625_3_)
    {
        double d0 = p_70625_1_.posX - this.posX;
        double d2 = p_70625_1_.posZ - this.posZ;
        double d1;

        if (p_70625_1_ instanceof EntityLivingBase)
        {
            EntityLivingBase entitylivingbase = (EntityLivingBase)p_70625_1_;
            d1 = entitylivingbase.posY + (double)entitylivingbase.getEyeHeight() - (this.posY + (double)this.getEyeHeight());
        }
        else
        {
            d1 = (p_70625_1_.boundingBox.minY + p_70625_1_.boundingBox.maxY) / 2.0D - (this.posY + (double)this.getEyeHeight());
        }

        double d3 = (double)MathHelper.sqrt_double(d0 * d0 + d2 * d2);
        float f2 = (float)(Math.atan2(d2, d0) * 57.2957795131D) - 90.0F;
        float f3 = (float)(-(Math.atan2(d1, d3) * 57.2957795131D));
        this.rotationPitch = this.updateRotation(this.rotationPitch, f3, p_70625_3_);
        this.rotationYaw = this.updateRotation(this.rotationYaw, f2, p_70625_2_);
    }

    private float updateRotation(float p_70663_1_, float p_70663_2_, float p_70663_3_)
    {
        float f3 = MathHelper.wrapAngleTo180_float(p_70663_2_ - p_70663_1_);

        if (f3 > p_70663_3_)
        {
            f3 = p_70663_3_;
        }

        if (f3 < -p_70663_3_)
        {
            f3 = -p_70663_3_;
        }

        return p_70663_1_ + f3;
    }


    @Override
    public void nullifyEntityToAttack()
    {
        this.entityToAttack = null;
        this.chaseCount = 0;
        this.pathToEntity = null;

        if (this.pack == null)
        {
            this.findNewPack();
        }
    }


    @Override
    public void setPath(PathEntity path)
    {
        this.pathToEntity = path;
    }

    @Override
    public void setPath(int x, int y, int z)
    {
        this.pathToEntity = this.worldObj.getEntityPathToXYZ(this, x, y, z, (float) this.getEntityAttributeInstance(SharedMonsterAttributes.followRange).getAttributeValue(), true, false, this.isAvoidsWater(), this.isCanSwim());
    }

    @Override
    public void updatePathFindingToEntityToAttack()
    {
        this.pathToEntity = this.worldObj.getPathEntityToEntity(this, (Entity) this.entityToAttack, (float) this.getEntityAttributeInstance(SharedMonsterAttributes.followRange).getAttributeValue(), true, false, this.isAvoidsWater(), this.isCanSwim());
    }


    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float damage)
    {
        boolean flag = super.attackEntityFrom(damageSource, damage);

        if (flag)
        {
            this.setAttackTarget((IGGMEntity) damageSource.getSourceOfDamage());
        }

        return flag;
    }

    @Override
    public float getBlockPathWeight(int x, int y, int z)
    {
        return 0.0F;
    }


    @Override
    public boolean isCanJustLive()
    {
        return this.pathToEntity == null && this.getActiveAnimation().getAnimationEpisode() == null;
    }

    @Override
    public IAnimationEpisode getRandomJustLivingEpisode()
    {
        int i = this.rand.nextInt(1);

        switch (i)
        {
            case 0:
                return GGMEntityAnimations.ScavLiving0;
        }

        return null;
    }

    public Map<String, IAnimationEpisode> getAnimationEpisodesMap()
    {
        return null;
    }


    protected String getLivingSound() {
        return GothicMain.MODID + ":scavenger_living";
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
*/