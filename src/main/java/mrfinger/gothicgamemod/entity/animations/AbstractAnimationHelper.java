package mrfinger.gothicgamemod.entity.animations;

import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.animations.episodes.IAnimationEpisode;

public abstract class AbstractAnimationHelper<Entity extends IGGMEntityLivingBase, Episode extends IAnimationEpisode> implements IAnimationHelper<Entity, Episode>
{

    protected Entity entity;


    public AbstractAnimationHelper()
    {
        this(null);
    }

    public AbstractAnimationHelper(Entity entity)
    {
        this.entity = entity;
    }

    @Override
    public Entity getEntity()
    {
        return this.entity;
    }

    @Override
    public void setEntity(Entity entity)
    {
        this.entity = entity;
    }


    @Override
    public String toString()
    {
        return "AnimationHelper:" + this.getUnlocalizedName();
    }
}
