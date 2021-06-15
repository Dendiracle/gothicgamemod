package mrfinger.gothicgamemod.entity.animations;

import mrfinger.gothicgamemod.client.model.IGGMModelBase;
import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.animations.episodes.IAnimationEpisode;
import mrfinger.gothicgamemod.entity.packentities.IEntityHerd;
import mrfinger.gothicgamemod.util.IGGMDamageSource;
import net.minecraft.client.model.ModelBase;
import net.minecraft.item.ItemStack;

import java.util.Map;

public class AnimationEntityHerdLiving extends AbstractAnimation
{

    protected IAnimationEpisode episode;
    protected short episodeDuration;
    protected short episodeCount;


    public AnimationEntityHerdLiving(IEntityHerd entity)
    {
        super(entity);
    }

    @Override
    public String getUnlocalizedName()
    {
        return "main";
    }


    @Override
    public void onUpdate()
    {
        if (this.episode != null)
        {
            if (this.episodeCount > 0)
            {
                --this.episodeCount;

                if (this.episodeCount == 0)
                {
                    this.clearAnimationEpisode();
                }
            }
        }
    }

    @Override
    public boolean tryEndAnimation()
    {
        return false;
    }

    @Override
    public void onEndAnimation()
    {
        this.clearAnimationEpisode();
    }


    @Override
    public IAnimationEpisode getEpisode()
    {
        return episode;
    }

    public int getEpisodeDuration()
    {
        return episodeDuration;
    }

    public int getEpisodeCount()
    {
        return episodeCount;
    }

    @Override
    public boolean setAnimationEpisode(IAnimationEpisode episode, int duration)
    {
        this.clearAnimationEpisode();
        if (episode != null)
        {
            this.episode = episode;
            this.episodeDuration = (short) duration;
            this.episodeCount = episodeDuration;
        }

        this.entity.flagForAnimSync();

        return true;
    }

    @Override
    public boolean setAnimationEpisode(String episodeName, int duration)
    {
        if (episodeName == null)
        {
            this.setAnimationEpisode((IAnimationEpisode) null, 0);
            return true;
        }
        else
        {
            Map<String, IAnimationEpisode> map = ((IEntityHerd) this.entity).getLivingEpisodesMap();

            if (map != null)
            {
                IAnimationEpisode episode = map.get(episodeName);
                this.setAnimationEpisode(episode, duration);

                return episode != null;
            }
        }

        return false;
    }

    @Override
    public void setEpisodeCount(int count)
    {
        this.episodeCount = (short) count;
    }

    @Override
    public void clearAnimationEpisode()
    {
        this.episode = null;
        this.episodeCount = 0;
        this.entity.flagForAnimSync();
    }


    @Override
    public boolean denyMovement()
    {
        return this.episode != null;
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
    public boolean denyChangeItem()
    {
        return false;
    }

    @Override
    public void onTakingDamage(IGGMDamageSource damageSource, float damage)
    {
        if (this.episode != null && damage > 0.0F)
        {
            this.clearAnimationEpisode();
        }
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


    @Override
    public void modifyModel(ModelBase model, float f0, float f1, float tickRate)
    {
        if (this.episodeCount > 0)
        {

            this.episode.updateModel(this.entity, model, ((this.episodeDuration - this.episodeCount) + tickRate) / this.episodeDuration);
        }
    }
}
