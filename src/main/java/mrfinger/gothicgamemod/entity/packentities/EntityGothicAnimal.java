package mrfinger.gothicgamemod.entity.packentities;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mrfinger.gothicgamemod.block.BlockAnimalEggs;
import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.IGGMEntityLiving;
import mrfinger.gothicgamemod.entity.animations.*;
import mrfinger.gothicgamemod.entity.animations.AnimationHelperGothicAnimalLiving;
import mrfinger.gothicgamemod.entity.animations.IAnimationHelperFightStance;
import mrfinger.gothicgamemod.entity.animations.episodes.IAnimationEpisode;
import mrfinger.gothicgamemod.entity.animations.episodes.IAnimationHit;
import mrfinger.gothicgamemod.entity.capability.attribute.map.IGGMBaseAttributeMap;
import mrfinger.gothicgamemod.entity.capability.attribute.instance.IGGMDynamicAttributeInstance;
import mrfinger.gothicgamemod.entity.capability.attribute.attributeinfo.IAttributeInfoDynamic;
import mrfinger.gothicgamemod.init.GGMCapabilities;
import mrfinger.gothicgamemod.init.GGMEntityAnimations;
import mrfinger.gothicgamemod.tileentity.TileEntityAnimalEgg;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Map;

public abstract class EntityGothicAnimal extends EntityHerd implements IEntityGothicAnimal
{

    protected final IAnimationHelperFightStance<EntityGothicAnimal, IAnimationHit> fightStanceAnimationHelper;

    protected final IGGMDynamicAttributeInstance stamina;

    @SideOnly(Side.CLIENT)
    protected int growthAge;


    public EntityGothicAnimal(World world)
    {
        super(world);

        this.fightStanceAnimationHelper = this.getNewAnimationFightStance();
        this.stamina = (IGGMDynamicAttributeInstance) this.getAttributeMap().getAttributeInstance(GGMCapabilities.maxStamina);

        this.addTasks(1);
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(12, 0);
    }


    @Override
    protected void applyEntityAttributes()
    {
        this.setSize(this.getStandartWidth(), this.getStandartHeight());

        super.applyEntityAttributes();

        ((IGGMBaseAttributeMap) this.getAttributeMap()).registerAttribute(GGMCapabilities.maxStamina, this.getNewStaminaAI());
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
    }

    protected abstract IAttributeInfoDynamic getNewStaminaAI();


    @Override
    public IAnimationHelper getNewDefaultAnimationHelper()
    {
        return new AnimationHelperGothicAnimalLiving(this);
    }


    protected IAnimationHelperFightStance getNewAnimationFightStance()
    {
        return new AnimationHelperFSWithAIGothicMob(this);
    }

    protected int addTasks(int priority)
    {
        return this.addLivingAI(this.addFightAI(priority));
    }

    protected int addFightAI(int priority)
    {
        this.tasks.addTask(priority++, (EntityAIBase) this.fightStanceAnimationHelper);
        return priority;
    }

    protected int addLivingAI(int priority)
    {
        this.tasks.addTask(priority++, (EntityAIBase) this.getDefaultAnimationHelper());
        return priority;
    }


    @Override
    public void onEntityUpdate()
    {
        if (!this.worldObj.isRemote)
        {

        }
        else
        {
            if (this.growthAge != this.getGrowingAge())
            {
                this.growthAge = this.getGrowingAge();
                this.setGrowingAge(this.growthAge);
            }
        }

        super.onEntityUpdate();
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
    }


    @Override
    public IAnimationHelperFightStance getFightStanceAnimationHelper()
    {
        return this.fightStanceAnimationHelper;
    }

    @Override
    public boolean setActiveAnimationHelperDirectly(String animationName)
    {
        if (animationName.equals(this.fightStanceAnimationHelper.getUnlocalizedName()))
        {
            if (this.getActiveAnimationHelper() != this.fightStanceAnimationHelper)
            {
                this.tryChangeAnimationHelper(this.fightStanceAnimationHelper);
            }

            return true;
        }

        return super.setActiveAnimationHelperDirectly(animationName);
    }

    @Override
    public boolean attackEntityAsMob(Entity target)
    {
        float f = (float)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
        return target.attackEntityFrom(DamageSource.causeMobDamage(this), f);
    }

    @Override
    public short getNewAttackDuration(IAnimationHit hitType)
    {
        return (short) hitType.getStandartDuration();
    }

    @Override
    public IAnimationHit getCurrentAttackHitTYpe()
    {
        return this.fightStanceAnimationHelper.getAnimationEpisode();
    }

    @Override
    public short getCurrentAttackDuration()
    {
        return (short) this.fightStanceAnimationHelper.getEpisodeDuration();
    }

    @Override
    public short getAttackCountdown()
    {
        return (short) this.fightStanceAnimationHelper.getEpisodeCountdown();
    }


    @Override
    public IGGMEntity getEntityToAttack()
    {
        return (IGGMEntity) ((IGGMEntityLiving) this).getAttackTarget();
    }


    @Override
    public void playEatSound()
    {
        String sound = this.getEatSound();

        if (sound != null)
        {
            this.playSound(this.getEatSound(), this.getSoundVolume(), this.getSoundPitch());
        }
    }

    protected abstract String getEatSound();


    @Override
    public Map<String, IAnimationEpisode> getAnimationEpisodesMap()
    {
        return GGMEntityAnimations.GothicAnimalLivingEpisodesMap;
    }

    @Override
    public boolean startAttack(IAnimationHit hitType)
    {
        return (this.getActiveAnimationHelper() == this.fightStanceAnimationHelper || (this.tryChangeAnimationHelper(this.fightStanceAnimationHelper))) && this.canMeleeAttackAnimatedly() && this.fightStanceAnimationHelper.setAnimationEpisode(hitType, this.getNewAttackDuration(hitType));
    }

    @Override
    public IAnimationHit getHitAnimation(float distance)
    {
        return distance > this.getMeleeAttackDistance() ? GGMEntityAnimations.AnimationHitGothicAnimal : GGMEntityAnimations.AnimationHitOnRunGothicAnimal;
    }

    @Override
    public float getMeleeAttackDistance()
    {
        return 1.0F;
    }


    @Override
    public IGGMDynamicAttributeInstance getStaminaAttribute()
    {
        return this.stamina;
    }

    @Override
    public int getMaxAir() {
        return super.getMaxAir() + (int) this.stamina.getAttributeValue();
    }

    @Override
    public float getStaminaSpendingFromJump() {
        return 1.2F;
    }

    @Override
    protected void jump()
    {
        if (this.canJump())
        {
            this.stamina.changeCurrentValue(-this.getStaminaSpendingFromJump());
            super.jump();
        }
    }

    @Override
    public float getStaminaSpendingOnSprint() {
        return 0.1F;
    }

    @Override
    public float getStaminaSpendingFromAnimatedAttack() {
        return 2.0F;
    }


    public void writeEntityToNBT(NBTTagCompound p_70014_1_)
    {
        super.writeEntityToNBT(p_70014_1_);
        p_70014_1_.setInteger("Age", this.getGrowingAge());
    }

    public void readEntityFromNBT(NBTTagCompound p_70037_1_)
    {
        super.readEntityFromNBT(p_70037_1_);
        this.setGrowingAge(p_70037_1_.getInteger("Age"));
    }


    @Override
    public int getEpisodeDuration(IAnimationEpisode episode)
    {
        return episode.getStandartDuration();
    }


    @Override
    public IAnimationEpisode getRandomJustLivingEpisode()
    {
        if (this.isNeedGoToSleep())
        {
            return GGMEntityAnimations.AnimationSleepingEntityGothicAnimal;
        }
        int rand = this.rand.nextInt(100);
        if (rand > 95) return GGMEntityAnimations.AnimationLookingAroundEntityGothicAnimal;
        if (this.getGrowingAge() > this.getChildBirthNeedsGrowth() &&  rand > 75) return GGMEntityAnimations.AnimationChildBirthEntityGothicAnimal;

        return GGMEntityAnimations.AnimationEatEntityGothicAnimal;
    }


    @Override
    public boolean isEntitySleeping()
    {
        return this.getActiveAnimationHelper().getAnimationEpisode() == GGMEntityAnimations.AnimationSleepingEntityGothicAnimal;
    }

    @Override
    public boolean isNeedWakeUp()
    {
        return this.worldObj.getWorldTime() > this.wakeUpTime() &&  this.worldObj.getWorldTime() < this.goToSleepTime();
    }

    @Override
    public boolean isNeedGoToSleep()
    {
        return !this.isNeedWakeUp();
    }

    protected int wakeUpTime()
    {
        return 0;
    }

    protected int goToSleepTime()
    {
        return 13000;
    }

    @Override
    public void wakeUpEntity()
    {
        this.getActiveAnimationHelper().breakAnimationEpisode();
    }

    @Override
    public void goToSleep()
    {
        if (this.getActiveAnimationHelper() == this.getDefaultAnimationHelper())
        {
            this.getActiveAnimationHelper().setAnimationEpisode(GGMEntityAnimations.AnimationSleepingEntityGothicAnimal);
        }
    }


    public int getGrowingAge()
    {
        return this.dataWatcher.getWatchableObjectInt(12);
    }

    public void setGrowingAge(int value)
    {
        this.dataWatcher.updateObject(12, value);

        if (value < 0)
        {
            float f0 = this.getNewBornGrowthAge();
            f0 = (f0 - value) / f0;

            float f1 = this.getNewBornSizeScale();
            f1 = (1F - f1) * f0 + f1;

            this.setSize(this.getStandartWidth() * f1, this.getStandartHeight() * f1);
        }
        else
        {
            this.setSize(this.getStandartWidth(), this.getStandartHeight());
        }
    }

    public void changeGrowth(int value)
    {
        int i = this.getMaxGrowth();

        if (value < i)
        {
            if (value + this.getGrowingAge() > i)
            {
                value = i;
            }
            else
            {
                value += this.getGrowingAge();
            }

            this.setGrowingAge(value);
        }
    }


    @Override
    public IEntityGothicAnimal getNewChild()
    {
        IEntityGothicAnimal child = (IEntityGothicAnimal) EntityList.createEntityByName(EntityList.getEntityString(this), this.worldObj);
        child.setGrowingAge(this.getNewBornGrowthAge());
        this.pack.addEntity(child);
        return child;
    }

    @Override
    public void birthChild()
    {
        if (!this.worldObj.isRemote)
        {
            if (this.isViviparous())
            {
                EntityGothicAnimal baby = (EntityGothicAnimal) this.getNewChild();
                baby.setLocationAndAngles(this.posX, this.posY, this.posZ, MathHelper.wrapAngleTo180_float(this.worldObj.rand.nextFloat() * 360.0F), 0.0F);
                baby.rotationYawHead = baby.rotationYaw;
                baby.renderYawOffset = baby.rotationYaw;
                this.worldObj.spawnEntityInWorld(baby);
                this.changeGrowth(-this.getChildBirthNeedsGrowth());
                baby.setGrowingAge(baby.getGrowingAge());
            }
            else if (this.worldObj.getBlock((int) this.posX, (int) this.posY, (int) this.posZ) == Blocks.air)
            {
                BlockAnimalEggs block = this.getBlockEgg();
                this.worldObj.setBlock((int) this.posX, (int) this.posY, (int) this.posZ, block);
                block.onBlockPlacedBy(this.worldObj, (int) this.posX, (int) this.posY, (int) this.posZ, this, null);
                this.changeGrowth(-this.getChildBirthNeedsGrowth());
            }

        }
    }

    @Override
    public void setEggParametrs(TileEntityAnimalEgg egg)
    {
        egg.setParametrs(this.getNewChild(), this.getEggHatchingTime(), this.getEggDestroyAfterHathingTime());
    }

    protected int getEggHatchingTime()
    {
        return 72000;
    }

    protected int getEggDestroyAfterHathingTime()
    {
        return 1200;
    }


    @Override
    public void playWarnSound()
    {
        String sound = this.getWarnSound();

        if (sound != null)
        {
            this.playSound(this.getWarnSound(), this.getSoundVolume(), this.getSoundPitch());
        }
    }

    protected abstract String getWarnSound();
}
