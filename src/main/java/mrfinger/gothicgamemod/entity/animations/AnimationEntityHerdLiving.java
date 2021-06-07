package mrfinger.gothicgamemod.entity.animations;

import mrfinger.gothicgamemod.battle.hittypes.IAnimationEpisode;
import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.packentities.IEntityHerd;
import mrfinger.gothicgamemod.util.IGGMDamageSource;
import net.minecraft.item.ItemStack;

public class AnimationEntityHerd extends AbstractAnimation
{

    protected IAnimationEpisode currentEpisode;
    protected short episodeCount;

    public AnimationEntityHerd(IEntityHerd entity)
    {
        super(entity);
    }


    @Override
    public void onUpdate()
    {
        if (this.currentEpisode != null)
        {
            if (this.episodeCount > 0)
            {
                this.currentEpisode.onUpdate();
            }
            else
            {
                this.clearAnimationEpisode();
            }
        }
    }

    @Override
    public boolean tryEndAnimation() {
        return false;
    }

    @Override
    public void onEndAnimation()
    {
        this.clearAnimationEpisode();
    }

    @Override
    public boolean setAnimationEpisode(IAnimationEpisode animationEpisode, short count)
    {
        this.clearAnimationEpisode();

        this.currentEpisode = animationEpisode;
        this.episodeCount = count;

        return true;
    }

    @Override
    public void clearAnimationEpisode()
    {

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
    public void onItemUseSetted(ItemStack itemStack, int duration)
    {

    }

    @Override
    public void onUsingItem(ItemStack itemStack, int duration)
    {

    }

    @Override
    public boolean denyChangeItem() {
        return false;
    }

    @Override
    public void onTakingDamage(IGGMDamageSource damageSource, float damage)
    {

    }

    @Override
    public void onDeath(IGGMDamageSource damageSource)
    {

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

    @Override
    public void onJumped()
    {

    }
}
