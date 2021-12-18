package mrfinger.gothicgamemod.entity.animation;

import mrfinger.gothicgamemod.entity.animation.instance.IAnimation;

public interface IAnimationPlayer<Animation extends IAnimation>
{

    Animation getActiveAnimation();

    /*
     * Returns true if after this method activeAnimationHelper
     * is agrument.
     */
    boolean tryChangeAnimation(Animation animation);

    void setActiveAnimationDirectly(Animation animation);

    void clearAnimation();

}
