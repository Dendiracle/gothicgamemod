package mrfinger.gothicgamemod.entity.animations.episodes;

import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.animations.IAnimationHelper;
import net.minecraft.client.model.ModelBase;

public abstract class AbstractAnimationEpisodeWithDurAndMultiplier<Entity extends IGGMEntityLivingBase, Model extends ModelBase> extends AbstractAnimationEpisodeWithDur<Entity, Model>
{

    protected final float culminationTickMultiplier;


    public AbstractAnimationEpisodeWithDurAndMultiplier(String unlocalizedName, int standartDuration, float culminationTickMultiplier)
    {
        super(unlocalizedName, standartDuration);

        if (culminationTickMultiplier < 0.0F || culminationTickMultiplier > 1.0F) throw new IllegalArgumentException("culminationTickMultiplier must have range from 0 to 1");
        this.culminationTickMultiplier = culminationTickMultiplier;
    }


    @Override
    public float getCulminationTickMultiplier()
    {
        return this.culminationTickMultiplier;
    }


    @Override
    public void onUpdate(Entity entity, IAnimationHelper helper)
    {

    }

    @Override
    public void onCulmination(Entity entity, IAnimationHelper helpe)
    {

    }
}
