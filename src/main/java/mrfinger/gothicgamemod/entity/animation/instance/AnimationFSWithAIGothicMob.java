package mrfinger.gothicgamemod.entity.animation.instance;

import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.animation.instance.AbstractAnimationHelperEntityAI;
import mrfinger.gothicgamemod.entity.animation.instance.IAnimation;
import mrfinger.gothicgamemod.entity.animation.manager.IAnimationManager;
import mrfinger.gothicgamemod.entity.packentities.IEntityGothicAnimal;
import mrfinger.gothicgamemod.init.GGMEntityAnimations;
import net.minecraft.entity.EntityLivingBase;

public class AnimationFSWithAIGothicMob<Entity extends IEntityGothicAnimal, Animation extends IAnimation<Entity>> extends AbstractAnimationHelperEntityAI<Entity, Animation>
{

    protected byte isNeedControls;
    protected float rotationYaw;
    protected float rotationPitch;


    @Override
    public IAnimationManager getAnimationManager()
    {
        return GGMEntityAnimations.ManagerGothicMobFightStance;
    }

    @Override
    public boolean shouldExecute()
    {
        return this.entity.isOnFight() && this.entity.getEntityToAttack() != null && this.entity.getEntityToAttack().isEntityAlive() && (this.entity.getActiveAnimation() == this || this.entity.tryChangeAnimation(this));
    }

    @Override
    public void startExecuting()
    {
        this.entity.getNavigator().tryMoveToEntityLiving((net.minecraft.entity.Entity) this.entity.getEntityToAttack(), 1.0D);
    }

    @Override
    public void resetTask()
    {
        this.entity.getNavigator().clearPathEntity();

        if (this.entity.getActiveAnimation() == this)
        {
            this.entity.clearAnimation();
        }
    }

    @Override
    public void updateTask()
    {
        IGGMEntity gtarget = this.entity.getEntityToAttack();
        net.minecraft.entity.Entity target = (net.minecraft.entity.Entity) gtarget;
        this.entity.getLookHelper().setLookPositionWithEntity(target, 10F, this.entity.getVerticalFaceSpeed());
        double distanceToEnemy = this.entity.getDistanceSqToEntity(target);
        double d1 = this.entity.getMeleeAttackDistance() + (((net.minecraft.entity.Entity) this.entity).width + target.width) * 0.5F;
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
