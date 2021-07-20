package mrfinger.gothicgamemod.entity.animations;

import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.animations.episodes.IAnimationEpisode;
import mrfinger.gothicgamemod.entity.packentities.IEntityGothicAnimal;
import mrfinger.gothicgamemod.init.GGMEntityAnimations;
import mrfinger.gothicgamemod.util.IGGMDamageSource;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;

import java.util.Map;

public class AnimationHelperGothicAnimalLiving extends EntityAIBase implements IAnimationHelper<IEntityGothicAnimal, IAnimationEpisode>
{

    protected IEntityGothicAnimal entity;

    protected IAnimationEpisode episode;
    protected short episodeDuration;
    protected short episodeCount;
    protected short episodeCulminationTick;

    protected boolean isNeedControlMove;
    protected float moveForward;
    protected float moveStrafe;


    protected boolean isEntityHavePath;
    protected byte episodesSeries;


    public AnimationHelperGothicAnimalLiving(IEntityGothicAnimal entity)
    {
        this.entity = entity;
    }

    @Override
    public IEntityGothicAnimal getEntity()
    {
        return this.entity;
    }

    @Override
    public void setEntity(IEntityGothicAnimal entity)
    {
        this.entity = entity;
    }

    @Override
    public String getUnlocalizedName()
    {
        return "main";
    }


    @Override
    public void updateAnimation()
    {
        if (this.episode != null)
        {
            if (this.episodeCount > 0)
            {
                this.episode.onUpdate(this.entity, this.episodeDuration, this.episodeCount);

                if (this.episodeCount == this.episodeCulminationTick)
                {
                    this.episode.onCulmination(this.entity, this.episodeDuration, this.episodeCount, (byte) 0);
                }

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
        return this.episode == null;
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
    public boolean setAnimationEpisode(IAnimationEpisode episode)
    {
        return this.setAnimationEpisode(episode, this.entity.getEpisodeDuration(episode));
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
            this.episodeCulminationTick = (short) (duration * episode.getCulminationTickMultiplier());
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
            Map<String, IAnimationEpisode> map = this.entity.getAnimationEpisodesMap();
            if (map != null)
            {
                IAnimationEpisode episode = map.get(episodeName);
                this.setAnimationEpisode(episode, duration);
                return episode != this.getEpisode();
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
        if (this.episode != null)
        {
            this.episode = null;
            this.episodeCount = 0;
            this.entity.flagForAnimSync();
        }
    }


    @Override
    public void setMoveControl(float forward, float strafe)
    {
        this.isNeedControlMove = true;
        this.moveForward = forward;
        this.moveStrafe = strafe;
    }

    @Override
    public void controlMove()
    {
        if (this.isNeedControlMove)
        {
            ((EntityLivingBase) this.entity).moveForward = this.moveForward;
            this.entity.setAIMoveSpeed(this.moveForward);
            ((EntityLivingBase) this.entity).moveStrafing = this.moveStrafe;
            this.isNeedControlMove = false;
        }
    }


    @Override
    public boolean denyMovement()
    {
        return this.episode != null;
    }

    @Override
    public boolean denyDropItems()
    {
        return this.episode != null;
    }

    @Override
    public boolean denyDigging()
    {
        return this.episode != null;
    }

    @Override
    public boolean denyUsingItems()
    {
        return this.episode != null;
    }

    @Override
    public boolean denySetItemInUse(ItemStack itemStack, int duration)
    {
        return this.episode != null;
    }

    @Override
    public void onItemUseSetted(ItemStack itemStack, int duration) {}

    @Override
    public void onUsingItem(ItemStack itemStack, int duration) {}

    @Override
    public boolean denyChangeItem()
    {
        return this.episode != null;
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
        this.clearAnimationEpisode();
    }

    @Override
    public boolean denyMount(IGGMEntity entity, boolean flag)
    {
        return this.episode != null;
    }

    @Override
    public boolean denyJump()
    {
        return this.episode != null;
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


    @Override
    public boolean shouldExecute()
    {
        return this.entity.getCurrentAnimation() == this;
    }

    @Override
    public void resetTask()
    {
        this.clearAnimationEpisode();
        super.resetTask();
    }


    @Override
    public void updateTask()
    {
        if (this.entity.getNavigator().noPath())
        {
            if (this.isEntityHavePath)
            {
                this.setAnimationEpisode(GGMEntityAnimations.AnimationLookingAroundEntityGothicAnimal);
                this.isEntityHavePath = false;
            }
            else if (this.episode == null && !this.entity.isNeedWander())
            {
                if (this.entity.isBlockAvailableToLiving() && (this.entity.getRNG().nextInt(25) + 1) > this.episodesSeries)
                {
                    IAnimationEpisode episode = this.entity.getRandomJustLivingEpisode();

                    if (episode != null)
                    {
                        this.setAnimationEpisode(episode);
                        ++this.episodesSeries;
                    }
                    else
                    {
                        this.entity.setIsNeedWander(true);
                        this.episodesSeries = 0;
                    }
                }
                else
                {
                    this.entity.setIsNeedWander(true);
                    this.episodesSeries = 0;
                }
            }
        }
        else
        {
            this.isEntityHavePath = true;
        }

    }
}
