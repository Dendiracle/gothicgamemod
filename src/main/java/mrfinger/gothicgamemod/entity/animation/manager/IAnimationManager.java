package mrfinger.gothicgamemod.entity.animation.manager;

import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.animation.instance.IAnimation;

import java.util.UUID;

public interface IAnimationManager<Entity extends IGGMEntityLivingBase>
{

    UUID getID();


    IAnimation<Entity> getNewAnimationInstance();

}
