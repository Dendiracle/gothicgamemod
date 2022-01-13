package mrfinger.gothicgamemod.entity.animation.instance;

import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;

public interface IAnimationHit<Entity extends IGGMEntityLivingBase> extends IAnimation<Entity>
{

    float staminaModifier();


}
