package mrfinger.gothicgamemod.entity.capability.data;

import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.animation.IAnimationFightStance;
import mrfinger.gothicgamemod.entity.animation.episodes.IAnimationHit;

public interface IGGMEntityWithAnimAttack extends IGGMEntityLivingBase
{

    default boolean inFightStance()
    {
        return this.getActiveAnimation() == this.getFightStanceAnimationHelper();
    }

    default boolean swicthToFightStance()
    {
        return this.tryChangeAnimation(this.getFightStanceAnimationHelper());
    }


    IAnimationFightStance getFightStanceAnimationHelper();

    default short getNewAttackDuration(IAnimationHit hitType)
    {
        return hitType.getStandartDuration() > Short.MAX_VALUE ? Short.MAX_VALUE : (short) hitType.getStandartDuration();
    }

    default IAnimationHit getCurrentAttackHitTYpe()
    {
        return this.inFightStance() ? (IAnimationHit) this.getActiveAnimation().getAnimationEpisode() : null;
    }

    default short getCurrentAttackDuration()
    {
        return this.inFightStance() ? (short) this.getActiveAnimation().getAnimationDuration() : 0;
    }

    default short getAttackCountdown()
    {
        return this.inFightStance() ? (short) this.getActiveAnimation().getAnimationCountdown() : 0;
    }


    IGGMEntity getEntityToAttack();


    default boolean canMeleeAttackAnimatedly()
    {
        return true;
    }

    default boolean startAttack(IAnimationHit hitType)
    {
        return (this.inFightStance() || this.swicthToFightStance()) && this.canMeleeAttackAnimatedly() && this.getActiveAnimation().setAnimationEpisode(hitType, this.getNewAttackDuration(hitType));
    }

}
