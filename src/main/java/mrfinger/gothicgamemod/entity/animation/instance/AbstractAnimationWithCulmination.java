package mrfinger.gothicgamemod.entity.animation.instance;

import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.animation.IAnimationPlayer;

public abstract class AbstractAnimationWithCulmination<Entity extends IGGMEntityLivingBase> extends AbstractAnimationWithDuration<Entity>
{

    protected short culminationTick;


    public AbstractAnimationWithCulmination(short duration, short culminationTick)
    {
        super(duration);

        this.culminationTick = culminationTick;
    }


    public AbstractAnimationWithCulmination(short duration)
    {
        this(duration, (short) 0);
    }


    @Override
    public void updateAnimation(IAnimationPlayer animationPlayer)
    {
        --this.countdown;

        if (this.countdown == this.culminationTick)
        {
            this.onCulmination();
        }

        if (this.countdown <= 0)
        {
            animationPlayer.clearAnimation();
        }
    }

    protected abstract void onCulmination();


    @Override
    public void setDuration(int value)
    {
        boolean culminationWas = this.countdown <= this.culminationTick;
        this.culminationTick *= ((float) value) / this.duration;
        super.setDuration(value);

        if (this.countdown == this.culminationTick)
        {
            if (!culminationWas)
            {
                --this.culminationTick;
            }
        }
    }
}
