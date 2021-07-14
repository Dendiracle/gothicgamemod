package mrfinger.gothicgamemod.entity.animations;

import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.animations.episodes.IAnimationEpisode;

public abstract class AbstractAnimationHelper<Entity extends IGGMEntityLivingBase, Episode extends IAnimationEpisode> implements IAnimationHelper<Entity, Episode>
{

    protected IGGMEntityLivingBase entity;


    public AbstractAnimationHelper()
    {

    }

    public AbstractAnimationHelper(IGGMEntityLivingBase entity)
    {
        this.entity = entity;
    }

    @Override
    public Entity getEntity()
    {
        return (Entity) this.entity;
    }

    @Override
    public void setEntity(IGGMEntityLivingBase entity)
    {
        this.entity = entity;
    }


    @Override
    public boolean setAnimationEpisode(Episode animationEpisode)
    {
        return this.setAnimationEpisode(animationEpisode, animationEpisode.getStandartDuration());
    }


    @Override
    public String toString()
    {
        return "Animation:" + this.getUnlocalizedName();
    }
}
