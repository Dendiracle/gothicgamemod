package mrfinger.gothicgamemod.entity.animation.instance;

import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.animation.IAnimationPlayer;
import mrfinger.gothicgamemod.entity.animation.manager.IAnimationManager;

public abstract class AbstractAnimationWithDuration<Entity extends IGGMEntityLivingBase> extends AbstractAnimation<Entity>
{

    protected final short duration;
    protected short countdown;


    public AbstractAnimationWithDuration(short duration)
    {
        this.duration = duration--;
        this.countdown = duration;
    }


    @Override
    public void updateAnimation(IAnimationPlayer animationPlayer)
    {
        --this.countdown;

        if (this.countdown <= 0)
        {
            animationPlayer.clearAnimation();
        }
    }


    @Override
    public boolean isCanAnimationHelperWillChanged()
    {
        return false;
    }


    public  AbstractAnimationWithDuration setAnimationCountdown(int countDown)
    {
        this.countdown = countDown > Short.MAX_VALUE ? Short.MAX_VALUE : (short) countDown;
        return this;
    }

}
