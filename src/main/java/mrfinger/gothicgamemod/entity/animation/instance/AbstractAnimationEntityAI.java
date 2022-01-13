package mrfinger.gothicgamemod.entity.animation.instance;

import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.animation.IAnimationPlayer;
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
    public void onSet(Entity entity, IAnimationPlayer animationPlayer)
    {
        this.entity = entity;
    }

    @Override
    public void onRemoveAnimation(Entity entity, IAnimationPlayer animationPlayer)
    {
        if (this.entity == entity) this.entity = null;
    }

}
