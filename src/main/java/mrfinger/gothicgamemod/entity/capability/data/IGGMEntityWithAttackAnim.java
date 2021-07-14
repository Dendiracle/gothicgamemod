package mrfinger.gothicgamemod.entity.capability.data;

import mrfinger.gothicgamemod.entity.animations.episodes.IAnimationEpisode;
import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.animations.episodes.IAnimationHit;

public interface IGGMEntityWithAttackAnim extends IGGMEntityLivingBase
{

    short getNewAttackDuration(IAnimationHit hitType);

    IAnimationEpisode getLastAttackHitTYpe();

    short getLastAttackDuration();

    short getAttackCount();

    float getAttackRangeSquare();


    IGGMEntity getEntityToAttack();


    boolean startAttack(IAnimationHit hitType);

    float getMeleeAttackDistance();

}
