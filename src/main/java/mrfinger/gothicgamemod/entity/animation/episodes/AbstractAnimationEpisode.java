package mrfinger.gothicgamemod.entity.animation.episodes;

import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import net.minecraft.client.model.ModelBase;

public abstract class AbstractAnimationEpisode<Entity extends IGGMEntityLivingBase, Model extends ModelBase> implements IAnimationEpisode<Entity, Model>
{

    protected final String unlocalizedName;


    public AbstractAnimationEpisode(String unlocalizedName)
    {
        this.unlocalizedName = unlocalizedName;
    }

    @Override
    public String getUnlocalizedName()
    {
        return this.unlocalizedName;
    }


    @Override
    public String toString()
    {
        return "AnimationEpisode:" + this.unlocalizedName;
    }

}
