package mrfinger.gothicgamemod.entity.animation.episodes;

import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import net.minecraft.client.model.ModelBase;

public abstract class AbstractAnimationEpisodeWithDur<Entity extends IGGMEntityLivingBase, Model extends ModelBase> extends AbstractAnimationEpisode<Entity, Model>
{

    protected final int standartDuration;


    public AbstractAnimationEpisodeWithDur(String unlocalizedName, int standartDuration)
    {
        super(unlocalizedName);
        this.standartDuration = standartDuration;
    }


    @Override
    public int getStandartDuration()
    {
        return standartDuration;
    }

    @Override
    public float getCulminationTickMultiplier()
    {
        return 0.0F;
    }

}
