package mrfinger.gothicgamemod.entity.animation.instance;

import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.animation.IAnimationPlayer;

public abstract class AbstractAnimationWithDuration<Entity extends IGGMEntityLivingBase> extends AbstractAnimation<Entity>
{

    protected short duration;
    protected short countdown;


    public AbstractAnimationWithDuration(short duration)
    {
        this.duration = duration;
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
    public boolean isCanAnimationWillChangedFor(IAnimationManager manager)
    {
        return this.countdown <= 0;
    }

    @Override
    public boolean isCanAnimationWillChangedFor(IAnimation animation)
    {
        return this.countdown <= 0;
    }


    @Override
    public int getDuration()
    {
        return duration;
    }

    @Override
    public int getCountdown()
    {
        return countdown;
    }

    @Override
    public void setDuration(int value)
    {
        short i = this.duration;
        this.duration = value > Short.MAX_VALUE ? Short.MAX_VALUE : (short) value;
        this.setCountdown((int) (this.countdown * (((float) this.duration) / i)));
    }

    @Override
    public void resizeDuration(float value)
    {
        this.setDuration((int) (this.duration * value));
    }

    @Override
    public void setCountdown(int countDown)
    {
        this.countdown = countDown > this.duration ? this.duration : (short) countDown;
    }


    protected float getAnimationProgress(float tickRate)
    {
        return ((this.duration - this.countdown + tickRate) / this.duration);
    }

}
