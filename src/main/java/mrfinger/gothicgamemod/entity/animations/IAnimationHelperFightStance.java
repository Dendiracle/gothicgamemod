package mrfinger.gothicgamemod.entity.animations;

import mrfinger.gothicgamemod.entity.animations.episodes.IAnimationHit;
import mrfinger.gothicgamemod.entity.capability.data.IGGMEntityWithAnimAttack;

public interface IAnimationHelperFightStance<Entity extends IGGMEntityWithAnimAttack, Episode extends IAnimationHit> extends IAnimationHelper<Entity, Episode>
{

    boolean isUsingItem();

    boolean isBlocking();

}
