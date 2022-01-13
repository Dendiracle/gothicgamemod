package mrfinger.gothicgamemod.entity.animation.manager;

import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.animation.instance.IAnimationHit;
import mrfinger.gothicgamemod.entity.animation.instance.IAnimationManager;

public interface IAnimationHitManager<Entity extends IGGMEntityLivingBase, Animation extends IAnimationHit<Entity>> extends IAnimationManager<Entity, Animation>
{
}
