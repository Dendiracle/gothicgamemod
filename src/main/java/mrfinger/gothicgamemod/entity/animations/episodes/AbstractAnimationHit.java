package mrfinger.gothicgamemod.entity.animations.episodes;

import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import net.minecraft.client.model.ModelBase;

public abstract class AbstractAnimationHit extends AbstractAnimationEpisode
{

    protected final float attackTickMultiplier;


    public AbstractAnimationHit(String unlocalizedName, int standartDuration, float attackTickMultiplier)
    {
        super(unlocalizedName, standartDuration);

        if (attackTickMultiplier < 0.0F || attackTickMultiplier > 1.0F) throw new IllegalArgumentException("attackTickMultiplier must have range from 0 to 1");
        this.attackTickMultiplier = attackTickMultiplier;
    }


    @Override
    public float getCulminationTickMultiplier()
    {
        return this.attackTickMultiplier;
    }


    @Override
    public void onUpdate(IGGMEntityLivingBase entity, int duration, int count)
    {

    }

    @Override
    public void onCulmination(IGGMEntityLivingBase entity, int duration, int count, byte attackSeries)
    {

    }
}
