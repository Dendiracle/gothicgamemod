package mrfinger.gothicgamemod.entity.animations;

import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.animations.episodes.IAnimationEpisode;
import mrfinger.gothicgamemod.network.PacketDispatcher;

public abstract class AbstractAnimation implements IAnimation
{

    protected IGGMEntityLivingBase entity;


    public AbstractAnimation()
    {

    }

    public AbstractAnimation(IGGMEntityLivingBase entity)
    {
        this.entity = entity;
    }

    @Override
    public IGGMEntityLivingBase getEntity()
    {
        return this.entity;
    }

    @Override
    public void setEntity(IGGMEntityLivingBase entity)
    {
        this.entity = entity;
    }


    @Override
    public String toString()
    {
        return "Animation:" + this.getUnlocalizedName();
    }
}
