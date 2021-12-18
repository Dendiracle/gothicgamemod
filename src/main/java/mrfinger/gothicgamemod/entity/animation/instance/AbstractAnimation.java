package mrfinger.gothicgamemod.entity.animation.instance;

import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;

public abstract class AbstractAnimation<Entity extends IGGMEntityLivingBase> implements IAnimation<Entity>
{

    protected Entity entity;


    @Override
    public Entity getEntity()
    {
        return entity;
    }

    @Override
    public void setEntity(Entity entity)
    {
        this.entity = entity;
    }

    @Override
    public void onRemoveAnimation(Entity entity)
    {
        if (this.entity == entity) this.entity = null;
    }
}
