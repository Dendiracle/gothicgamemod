package mrfinger.gothicgamemod.entity.animation.instance;

import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.animation.IAnimationPlayer;

public abstract class AbtractAnimationHelper<Entity extends IGGMEntityLivingBase, Animation extends IAnimation<Entity>> extends AbstractAnimation<Entity> implements IAnimationHelper<Entity, Animation>
{

    protected Animation subAnimation;


    @Override
    public void onSet(Entity entity, IAnimationPlayer animationPlayer)
    {
        super.onSet(entity, animationPlayer);
        IAnimationHelper.super.onSet(entity, animationPlayer);
    }


    @Override
    public Animation getActiveAnimation()
    {
        return subAnimation;
    }


    @Override
    public void onRemoveAnimation(Entity entity, IAnimationPlayer animationPlayer)
    {
        super.onRemoveAnimation(entity, animationPlayer);
        IAnimationHelper.super.onRemoveAnimation(entity, animationPlayer);
    }


    @Override
    public boolean tryChangeAnimation(Animation animation)
    {
        if (this.subAnimation == null || this.subAnimation.isCanAnimationWillChangedFor(animation))
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
            animation.onSet(entity, this);

            if (this.subAnimation != null)
            {
                IAnimation oldAnimation = this.subAnimation;
                this.subAnimation = animation;
                oldAnimation.onRemoveAnimation(entity, this);
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
                oldAnimation.onRemoveAnimation(entity, this);
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
