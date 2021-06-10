package mrfinger.gothicgamemod.entity.capability.data;

import mrfinger.gothicgamemod.entity.animations.episodes.IAnimationEpisode;
import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;

public interface IGGMEntityWithAttackAnim extends IGGMEntityLivingBase {


    short getNewAttackDuration(IAnimationEpisode hitType);

    IAnimationEpisode getLastAttackHitTYpe();

    short getLastAttackDuration();

    short getAttackCount();

    short getAttackTick();

    byte getAttackSeries();


    IGGMEntity getEntityToAttack();


    void startAttack(IAnimationEpisode hitType);

}
