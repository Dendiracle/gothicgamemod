package mrfinger.gothicgamemod.entity.animation;

import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.animation.instance.IAnimation;
import mrfinger.gothicgamemod.entity.animation.instance.IAnimationManager;

public interface IAnimationPlayer<Entity extends IGGMEntityLivingBase, Animation extends IAnimation<Entity>>
{

    Entity getEntity();

    Animation getActiveAnimation();


    default boolean tryChangeAnimation(IAnimationManager<Entity, Animation> manager)
    {
        if (manager == null)
        {
            return this.tryEndAnimation();
        }
        if (this.getActiveAnimation() == null || this.getActiveAnimation().isCanAnimationWillChangedFor(manager))
        {
            IAnimationManager.IStartAnimationData data = manager.getAnimationData(this.getEntity(), this);

            if (data.canStartAnimation())
            {
                Animation animation = manager.getNewAnimationInstance();
                this.setActiveAnimationDirectly(animation);
                data.onAnimationStarted(animation);
                return true;
            }
        }

        return false;
    }

    /*
     * Returns true if after this method activeAnimation
     * is agrument.
     */
    default boolean tryChangeAnimation(Animation animation)
    {
        if (this.getActiveAnimation() == null || this.getActiveAnimation().isCanAnimationWillChangedFor(animation))
        {
            this.setActiveAnimationDirectly(animation);
            return true;
        }

        return false;
    }

    /*
     * Return's true if after this method
     * playable animation is null
     */
    boolean tryEndAnimation();

    void setActiveAnimationDirectly(Animation animation);

    void clearAnimation();

}
