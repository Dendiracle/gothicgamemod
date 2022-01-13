package mrfinger.gothicgamemod.entity.animation;

import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.animation.episodes.IAnimationHit;
import mrfinger.gothicgamemod.entity.animation.instance.IAnimation;
import mrfinger.gothicgamemod.entity.animation.instance.IAnimationHelper;

public interface IAnimationFightStance<Entity extends IGGMEntityLivingBase, Animation extends IAnimation<Entity>> extends IAnimationHelper<Entity, Animation>
{

}
