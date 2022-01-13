package mrfinger.gothicgamemod.entity.animation.instance;

import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.animation.IAnimationPlayer;

public abstract class AbstractAnimation<Entity extends IGGMEntityLivingBase> implements IAnimation<Entity>
{

    protected Entity entity;


    @Override
    public Entity getEntity()
    {
        return entity;
    }

    @Override
    public void onSet(Entity entity, IAnimationPlayer animationPlayer)
    {
        this.entity = entity;
    }


    @Override
    public boolean isCanAnimationWillChangedFor(IAnimationManager manager)
    {
        return true;
    }

    @Override
    public boolean isCanAnimationWillChangedFor(IAnimation animation)
    {
        return true;
    }

    @Override
    public void onRemoveAnimation(Entity entity, IAnimationPlayer animationPlayer)
    {
        if (this.entity == entity) this.entity = null;
    }

}
