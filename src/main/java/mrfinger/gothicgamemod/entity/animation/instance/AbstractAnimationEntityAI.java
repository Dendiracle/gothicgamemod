package mrfinger.gothicgamemod.entity.animation.instance;

import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.animation.manager.IAnimationManager;
import mrfinger.gothicgamemod.entity.animation.instance.IAnimation;
import net.minecraft.entity.ai.EntityAIBase;

public abstract class AbstractAnimationEntityAI<Entity extends IGGMEntityLivingBase> extends EntityAIBase implements IAnimation<Entity>
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
