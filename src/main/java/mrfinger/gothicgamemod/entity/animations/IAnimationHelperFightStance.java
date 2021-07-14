package mrfinger.gothicgamemod.entity.animations;

import mrfinger.gothicgamemod.entity.animations.episodes.IAnimationHit;
import mrfinger.gothicgamemod.entity.capability.data.IGGMEntityWithAttackAnim;

public interface IAnimationHelperFightStance<Entity extends IGGMEntityWithAttackAnim, Episode extends IAnimationHit> extends IAnimationHelper<Entity, Episode>
{

    byte getAttackSeries();


    boolean isUsingItem();

    boolean isBlocking();

}
