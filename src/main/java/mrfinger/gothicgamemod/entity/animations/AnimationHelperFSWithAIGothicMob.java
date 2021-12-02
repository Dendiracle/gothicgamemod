package mrfinger.gothicgamemod.entity.animations;

import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.animations.episodes.IAnimationHit;
import mrfinger.gothicgamemod.entity.packentities.IEntityGothicAnimal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class AnimationHelperFSWithAIGothicMob<Animal extends IEntityGothicAnimal, Episode extends IAnimationHit> extends AbstractAnimationHelperFightStanceEntityAI<Animal, Episode>
{

    protected byte isNeedControls;
    protected float rotationYaw;
    protected float rotationPitch;


    public AnimationHelperFSWithAIGothicMob(Animal entity)
    {
        super(entity);
    }

    @Override
    public void setRotationControl(float yaw, float pitch)
    {
        this.isNeedControls |= 0b10;
        this.rotationYaw = yaw;
        this.rotationPitch = pitch;
    }

    @Override
    public void controlRotation()
    {
        if ((this.isNeedControls & 0b10) == 0b10)
        {
            ((EntityLivingBase) this.entity).rotationYaw = this.rotationYaw;
            ((EntityLivingBase) this.entity).rotationPitch = this.rotationPitch;
            this.isNeedControls &= 0b11111101;
        }
    }


    @Override
    public boolean isUsingItem() {
        return false;
    }

    @Override
    public boolean isBlocking() {
        return false;
    }


    @Override
    public String toString()
    {
        return "AnimationHelperFSWithAIGothicMob:" + this.getUnlocalizedName();
    }


    @Override
    public boolean shouldExecute()
    {
        return this.entity.isOnFight() && this.entity.getEntityToAttack() != null && this.entity.getEntityToAttack().isEntityAlive() && (this.entity.getActiveAnimationHelper() == this || this.entity.tryChangeAnimationHelper(this));
    }

    @Override
    public void startExecuting()
    {
        this.entity.getNavigator().tryMoveToEntityLiving((Entity) this.entity.getEntityToAttack(), 1.0D);
    }

    @Override
    public void resetTask()
    {
        if (this.animationEpisode != null) this.clearAnimationEpisode();
        this.entity.getNavigator().clearPathEntity();
        if (this.entity.getActiveAnimationHelper() == this)
        {
            this.entity.tryChangeAnimationHelperToDefault();
        }
    }

    @Override
    public void updateTask()
    {
        IGGMEntity gtarget = this.entity.getEntityToAttack();
        Entity target = (Entity) gtarget;
        this.entity.getLookHelper().setLookPositionWithEntity(target, 10F, this.entity.getVerticalFaceSpeed());
        double distanceToEnemy = this.entity.getDistanceSqToEntity(target);
        double d1 = this.entity.getMeleeAttackDistance() + (((Entity) this.entity).width + target.width) * 0.5F;
        d1 *= d1 * 3.0F;

        if (distanceToEnemy <= d1)
        {
            this.entity.getNavigator().clearPathEntity();
            if (this.entity.getAttackCountdown() <= 0)
            {
                this.entity.startAttack(this.entity.getHitAnimation((float) distanceToEnemy));
            }
        }
        else
        {
            this.entity.getNavigator().tryMoveToEntityLiving(target, 1.0D);
        }
    }

}
