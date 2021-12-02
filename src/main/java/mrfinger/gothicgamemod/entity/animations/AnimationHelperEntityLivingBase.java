package mrfinger.gothicgamemod.entity.animations;

import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.animations.episodes.IAnimationEpisode;
import net.minecraft.item.ItemStack;

public class AnimationHelperEntityLivingBase<Entity extends IGGMEntityLivingBase, Episode extends IAnimationEpisode> extends AbstractAnimationHelper<Entity, Episode>
{

    public AnimationHelperEntityLivingBase(Entity entityLivingBase)
    {
        super(entityLivingBase);
    }


    @Override
    public String getUnlocalizedName() {
        return "main";
    }

    @Override
    public void updateAnimation() {}


    @Override
    public boolean isCanAnimationHelperWillChanged()
    {
        return true;
    }

    @Override
    public void onChangeAnimationHelper() {}

    @Override
    public Episode getAnimationEpisode()
    {
        return null;
    }

    @Override
    public int getEpisodeDuration() {
        return 0;
    }

    @Override
    public int getEpisodeCountdown() {
        return 0;
    }

    @Override
    public boolean setAnimationEpisode(Episode animationEpisode)
    {
        return false;
    }


    @Override
    public boolean setAnimationEpisode(IAnimationEpisode animationEpisode, int duration)
    {
        return false;
    }

    @Override
    public boolean setAnimationEpisode(String episodeName, int duration)
    {
        return false;
    }

    @Override
    public void setEpisodeCountdown(int countDown) {}

    @Override
    public void clearAnimationEpisode() {}

    @Override
    public boolean breakAnimationEpisode()
    {
        return true;
    }


    @Override
    public boolean denyMovement()
    {
        return false;
    }



}
