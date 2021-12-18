package mrfinger.gothicgamemod.entity.animation.instance;

import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.animation.IAnimationPlayer;
import mrfinger.gothicgamemod.entity.animation.manager.IAnimationManager;

public abstract class AbstractAnimationWithCulmination<Entity extends IGGMEntityLivingBase> extends AbstractAnimationWithDuration<Entity>
{

    protected final short culminationTick;


    public AbstractAnimationWithCulmination(IAnimationManager manager, short duration, short culminationTick)
    {
        super(duration);

        this.culminationTick = culminationTick;
    }


    @Override
    public void updateAnimation(IAnimationPlayer animationPlayer)
    {
        if (this.countdown == this.culminationTick)
        {
            this.onCulmination();
        }

        super.updateAnimation(animationPlayer);
    }

    protected abstract void onCulmination();

}
