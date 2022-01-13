package mrfinger.gothicgamemod.entity.animation.instance;

import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.packentities.IEntityGothicAnimal;
import mrfinger.gothicgamemod.init.GGMEntityAnimations;

public class AnimationFSWithAIGothicMob<Entity extends IEntityGothicAnimal, Animation extends IAnimation<Entity>> extends AbstractAnimationHelperEntityAI<Entity, Animation>
{

    @Override
    public IAnimationManager getAnimationManager()
    {
        return GGMEntityAnimations.ManagerGothicAnimalFightStance;
    }

    @Override
    public boolean shouldExecute()
    {
        return this.entity.isOnFight() && this.entity.getAttackTarget() != null && this.entity.getAttackTarget().isEntityAlive() && (this.entity.getActiveAnimation() == this || this.entity.tryChangeAnimation(this));
    }

    @Override
    public void startExecuting()
    {
        this.entity.getNavigator().tryMoveToEntityLiving(this.entity.getAttackTarget(), 1.0D);
    }

    @Override
    public void resetTask()
    {
        this.entity.getNavigator().clearPathEntity();

        if (this.entity.getActiveAnimation() == this)
        {
            this.entity.tryEndAnimation();
        }
    }

    @Override
    public void updateTask()
    {
        net.minecraft.entity.Entity target = this.entity.getAttackTarget();
        this.entity.getLookHelper().setLookPositionWithEntity(target, 10F, this.entity.getVerticalFaceSpeed());
        double distanceToEnemy = this.entity.getDistanceSqToEntity((IGGMEntity) target);
        double d1 = this.entity.getMeleeAttackDistance() + (((net.minecraft.entity.Entity) this.entity).width + target.width) * 0.5F;
        d1 *= d1 * 3.0F;

        if (distanceToEnemy <= d1)
        {
            this.entity.getNavigator().clearPathEntity();
            if (this.entity.getAttackCountdown() <= 0)
            {
                this.entity.startAttack(target, (float) distanceToEnemy);
            }
        }
        else
        {
            this.entity.getNavigator().tryMoveToEntityLiving(target, 1.0D);
        }
    }

}
