package mrfinger.gothicgamemod.entity.capability.data;

import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.animations.IAnimationHelperFightStance;
import mrfinger.gothicgamemod.entity.animations.episodes.IAnimationHit;

public interface IGGMEntityWithAnimAttack extends IGGMEntityLivingBase
{

    default boolean inFightStance()
    {
        return this.getActiveAnimationHelper() == this.getFightStanceAnimationHelper();
    }

    default boolean swicthToFightStance()
    {
        return this.tryChangeAnimationHelper(this.getFightStanceAnimationHelper());
    }


    IAnimationHelperFightStance getFightStanceAnimationHelper();

    default short getNewAttackDuration(IAnimationHit hitType)
    {
        return hitType.getStandartDuration() > Short.MAX_VALUE ? Short.MAX_VALUE : (short) hitType.getStandartDuration();
    }

    default IAnimationHit getCurrentAttackHitTYpe()
    {
        return this.inFightStance() ? (IAnimationHit) this.getActiveAnimationHelper().getAnimationEpisode() : null;
    }

    default short getCurrentAttackDuration()
    {
        return this.inFightStance() ? (short) this.getActiveAnimationHelper().getEpisodeDuration() : 0;
    }

    default short getAttackCountdown()
    {
        return this.inFightStance() ? (short) this.getActiveAnimationHelper().getEpisodeCountdown() : 0;
    }


    IGGMEntity getEntityToAttack();


    default boolean canMeleeAttackAnimatedly()
    {
        return true;
    }

    default boolean startAttack(IAnimationHit hitType)
    {
        return (this.inFightStance() || this.swicthToFightStance()) && this.canMeleeAttackAnimatedly() && this.getActiveAnimationHelper().setAnimationEpisode(hitType, this.getNewAttackDuration(hitType));
    }

}
