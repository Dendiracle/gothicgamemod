package mrfinger.gothicgamemod.entity.animations;

import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.animations.episodes.IAnimationEpisode;
import net.minecraft.item.ItemStack;

public class AnimationHelperEntityLivingBase<Entity extends IGGMEntityLivingBase, Episode extends IAnimationEpisode> extends AbstractAnimationHelper<Entity, Episode>
{

    public AnimationHelperEntityLivingBase(IGGMEntityLivingBase entityLivingBase)
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
    public IAnimationHelper onSetNewAnimation(IAnimationHelper animation) {
        return animation;
    }

    @Override
    public boolean tryEndAnimation()
    {
        return true;
    }

    @Override
    public void onEndAnimation() {}

    @Override
    public Episode getEpisode()
    {
        return null;
    }

    @Override
    public int getEpisodeDuration() {
        return 0;
    }

    @Override
    public int getEpisodeCount() {
        return 0;
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
    public void clearAnimationEpisode() {}


    @Override
    public boolean denyMovement()
    {
        return false;
    }

    @Override
    public boolean denyDropItems()
    {
        return false;
    }

    @Override
    public boolean denyDigging()
    {
        return false;
    }

    @Override
    public boolean denyUsingItems()
    {
        return false;
    }

    @Override
    public boolean denySetItemInUse(ItemStack itemStack, int duration)
    {
        return false;
    }

    @Override
    public boolean denyChangeItem()
    {
        return false;
    }

    @Override
    public boolean denyMount(IGGMEntity entity, boolean flag)
    {
        return false;
    }

    @Override
    public boolean denyJump()
    {
        return false;
    }
}
