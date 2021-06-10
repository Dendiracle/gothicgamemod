package mrfinger.gothicgamemod.entity.animations.episodes;

import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import net.minecraft.client.model.ModelBase;

public class HittypeSplash implements IAnimationEpisode
{

    @Override
    public String getUnlocalizedName() {
        return "splash";
    }

    @Override
    public int getStandartDuration()
    {
        return 20;
    }

    @Override
    public float getCulminationTickMultiplier() {
        return 0.8F;
    }

    @Override
    public void updateModel(IGGMEntityLivingBase entity, ModelBase model, float progress)
    {

    }
}
