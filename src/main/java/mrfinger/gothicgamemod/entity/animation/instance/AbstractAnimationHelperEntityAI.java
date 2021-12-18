package mrfinger.gothicgamemod.entity.animation.instance;

import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;

public abstract class AbstractAnimationHelperEntityAI<Entity extends IGGMEntityLivingBase, Animation extends IAnimation<Entity>> extends AbstractAnimationEntityAI<Entity> implements IAnimationHelper<Entity, Animation>
{

    protected Animation subAnimation;


    @Override
    public void setEntity(Entity entity)
    {
        super.setEntity(entity);
        IAnimationHelper.super.setEntity(entity);
    }


    @Override
    public Animation getActiveAnimation()
    {
        return subAnimation;
    }


    @Override
    public void onRemoveAnimation(Entity entity)
    {
        super.onRemoveAnimation(entity);
        IAnimationHelper.super.onRemoveAnimation(entity);
    }


    @Override
    public boolean tryChangeAnimation(Animation animation)
    {
        if (this.subAnimation == null || this.subAnimation.isCanAnimationHelperWillChanged())
        {
            this.setActiveAnimationDirectly(animation);
            return true;
        }

        return false;
    }

    @Override
    public void setActiveAnimationDirectly(Animation animation)
    {
        if (animation != null )
        {
            animation.setEntity(entity);

            if (this.subAnimation != null)
            {
                IAnimation oldAnimation = this.subAnimation;
                this.subAnimation = animation;
                oldAnimation.onRemoveAnimation(entity);
            }
            else
            {
                this.subAnimation = animation;
            }
        }
        else
        {
            if (this.subAnimation != null)
            {
                IAnimation oldAnimation = this.subAnimation;
                this.subAnimation = null;
                oldAnimation.onRemoveAnimation(entity);
            }
        }
    }

    @Override
    public void clearAnimation()
    {
        if (this.subAnimation != null)
        {
            this.setActiveAnimationDirectly(null);
        }
    }

}
