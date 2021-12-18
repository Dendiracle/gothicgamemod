package mrfinger.gothicgamemod.entity.animation;

import mrfinger.gothicgamemod.entity.animation.episodes.IAnimationHit;
import mrfinger.gothicgamemod.entity.animation.instance.IAnimation;
import mrfinger.gothicgamemod.entity.animation.instance.IAnimationHelper;
import mrfinger.gothicgamemod.entity.capability.data.IGGMEntityWithAnimAttack;

public interface IAnimationFightStance<Entity extends IGGMEntityWithAnimAttack, Episode extends IAnimationHit> extends IAnimationHelper<Entity, Episode>
{

    boolean isUsingItem();

    boolean isBlocking();

}
