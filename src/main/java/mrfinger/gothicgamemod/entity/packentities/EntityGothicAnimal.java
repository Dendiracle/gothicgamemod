package mrfinger.gothicgamemod.entity.packentities;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mrfinger.gothicgamemod.block.BlockAnimalEggs;
import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.animations.*;
import mrfinger.gothicgamemod.entity.animations.AnimationHelperGothicAnimalLiving;
import mrfinger.gothicgamemod.entity.animations.IAnimationHelperFightStance;
import mrfinger.gothicgamemod.entity.animations.episodes.IAnimationEpisode;
import mrfinger.gothicgamemod.entity.animations.episodes.IAnimationHit;
import mrfinger.gothicgamemod.entity.capability.attributes.GGMDPAttributeInfo;
import mrfinger.gothicgamemod.entity.capability.attributes.IGGMBaseAttributeMap;
import mrfinger.gothicgamemod.entity.capability.attributes.IGGMDynamicAttributeInstance;
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

    protected final IAnimationHelperFightStance animationFightStance;

    protected final IGGMDynamicAttributeInstance stamina;

    @SideOnly(Side.CLIENT)
    protected int growthAge;


    public EntityGothicAnimal(World world)
    {
        super(world);

        this.animationFightStance = this.getNewAnimationFightStance();
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
        super.applyEntityAttributes();

        ((IGGMBaseAttributeMap) this.getAttributeMap()).registerAttribute(GGMCapabilities.maxStamina, this.getNewStaminaAI());
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
    }

    protected abstract GGMDPAttributeInfo getNewStaminaAI();


    @Override
    public IAnimationHelper getNewDefaultAnimation()
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
        this.tasks.addTask(priority++, (EntityAIBase) this.animationFightStance);
        return priority;
    }

    protected int addLivingAI(int priority)
    {
        this.tasks.addTask(priority++, (EntityAIBase) this.getDefaultAnimation());
        return priority;
    }


    @Override
    public void onEntityUpdate()
    {
        if (!this.worldObj.isRemote)
        {
            if (this.isSprinting())
            {
                this.sprintUpdate();
            }
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
    public boolean setAnimationHelper(String animationName)
    {
        if (animationName.equals(this.animationFightStance.getUnlocalizedName()))
        {
            if (this.getCurrentAnimation() != this.animationFightStance)
            {
                this.tryEndAnimation(this.animationFightStance);
            }

            return true;
        }

        return super.setAnimationHelper(animationName);
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
    public IAnimationEpisode getLastAttackHitTYpe()
    {
        return this.animationFightStance.getEpisode();
    }

    @Override
    public short getLastAttackDuration()
    {
        return (short) this.animationFightStance.getEpisodeDuration();
    }

    @Override
    public short getAttackCount()
    {
        return (short) this.animationFightStance.getEpisodeCount();
    }


    @Override
    public IGGMEntity getEntityToAttack()
    {
        return (IGGMEntity) this.attackTarget;
    }


    @Override
    public Map<String, IAnimationEpisode> getAnimationEpisodesMap()
    {
        return GGMEntityAnimations.GothicAnimalLivingEpisodesMap;
    }

    @Override
    public boolean startAttack(IAnimationHit hitType)
    {
        if (this.getCurrentAnimation() != this.animationFightStance)
        {
            this.tryEndAnimation(this.animationFightStance);
        }

        return this.getCurrentAnimation() == this.animationFightStance && this.canAttack() && this.animationFightStance.setAnimationEpisode(hitType, this.getNewAttackDuration(hitType));
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
        if (!this.getCurrentAnimation().denyJump() && this.canJump()) super.jump();
    }

    @Override
    public float getStaminaSpendingOnSprint() {
        return 0.1F;
    }

    @Override
    public float getStaminaSpendingFromAttack() {
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
        if (this.getGrowingAge() > this.getChildBirthNeedsGrowth() && this.rand.nextInt(4) == 0) return GGMEntityAnimations.AnimationChildBirthEntityGothicAnimal;

        return GGMEntityAnimations.AnimationEatEntityGothicAnimal;
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

}
