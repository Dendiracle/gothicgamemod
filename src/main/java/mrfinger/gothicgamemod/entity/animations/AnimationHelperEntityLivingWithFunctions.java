package mrfinger.gothicgamemod.entity.animations;

import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.animations.episodes.IAnimationEpisode;
import net.minecraft.client.model.ModelBase;
import net.minecraft.item.ItemStack;

import java.util.Map;

public class AnimationHelperEntityLivingWithFunctions<Entity extends IGGMEntityLivingBase, Episode extends IAnimationEpisode> extends AbstractAnimationHelper<Entity, Episode> implements IAnimationHelper<Entity, Episode>
{

    protected Episode animationEpisode;
    protected short episodeDuration;

    protected short episodeCountdown;
    protected short episodeCulminationTick;


    public AnimationHelperEntityLivingWithFunctions(Entity entity)
    {
        super(entity);
    }


    @Override
    public String getUnlocalizedName()
    {
        return "main";
    }


    @Override
    public void updateAnimation()
    {
        if (this.animationEpisode != null)
        {
            this.animationEpisode.onUpdate(this.entity, this);

            if (this.episodeCountdown > 0)
            {
                if (this.episodeCountdown == this.episodeCulminationTick)
                {
                    this.animationEpisode.onCulmination(this.entity, this);
                }

                --this.episodeCountdown;

                if (this.episodeCountdown == 0)
                {
                    this.clearAnimationEpisode();
                }

            }
        }
    }


    @Override
    public boolean isCanAnimationHelperWillChanged()
    {
        return this.animationEpisode == null;
    }


    @Override
    public Episode getAnimationEpisode() {
        return this.animationEpisode;
    }

    @Override
    public int getEpisodeDuration()
    {
        return this.episodeDuration;
    }

    @Override
    public int getEpisodeCountdown()
    {
        return this.episodeCountdown;
    }


    @Override
    public boolean setAnimationEpisode(Episode animationEpisode)
    {
        return this.setAnimationEpisode(animationEpisode, animationEpisode != null ? animationEpisode.getStandartDuration() : 0);
    }

    @Override
    public boolean setAnimationEpisode(Episode animationEpisode, int count)
    {
        if (this.animationEpisode == null)
        {
            if (animationEpisode != null)
            {
                this.setAnimationEpisodeDirectly(animationEpisode, count);
            }
            return true;
        }
        else if (this.isAllowChangeAnimationEpisode())
        {
            this.animationEpisode.onEnd(this.entity, this);

            if (animationEpisode != null)
            {
                this.setAnimationEpisodeDirectly(animationEpisode, count);
            }

            return true;
        }

        return false;
    }

    protected boolean isAllowChangeAnimationEpisode()
    {
        return this.animationEpisode.isCanBreak(this.entity, this);
    }

    private void setAnimationEpisodeDirectly(Episode animationEpisode, int count)
    {
        this.animationEpisode = animationEpisode;
        this.setEpisodeCountdown(count);
        this.episodeDuration = this.episodeCountdown;
        this.episodeCulminationTick = (short) (this.episodeCountdown * animationEpisode.getCulminationTickMultiplier());
        animationEpisode.onSet(this.entity, this);
        this.entity.flagForAnimSync();
    }

    @Override
    public boolean setAnimationEpisode(String episodeName, int duration)
    {
        if (episodeName == null)
        {
            this.setAnimationEpisode((Episode) null, 0);
            return true;
        }
        else
        {
            Map<String, IAnimationEpisode> map = this.entity.getAnimationEpisodesMap();

            if (map != null)
            {
                this.setAnimationEpisode((Episode) map.get(episodeName), duration);

                return true;
            }
        }

        return false;
    }


    @Override
    public void setEpisodeCountdown(int countDown)
    {
        this.episodeCountdown = countDown > Short.MAX_VALUE ? Short.MAX_VALUE : (short) countDown;
    }

    @Override
    public boolean breakAnimationEpisode()
    {
        if (this.animationEpisode == null) return true;

        if (this.animationEpisode.isCanBreak(this.entity, this))
        {
            this.clearAnimationEpisode();
            return true;
        }

        return false;
    }

    @Override
    public void clearAnimationEpisode()
    {
        this.animationEpisode.onEnd(this.entity, this);
        this.animationEpisode = null;
        this.episodeDuration = 0;
        this.episodeCountdown = 0;
        this.episodeCulminationTick = 0;

    }


    @Override
    public void setRotationControl(float yaw, float pitch)
    {

    }

    @Override
    public void controlMove()
    {
        if (this.animationEpisode != null)
        {
            this.animationEpisode.controlEntityMotion(entity, this);
        }
    }


    @Override
    public boolean denyMovement()
    {
        return this.animationEpisode != null;
    }


    @Override
    public boolean allowDropItems()
    {
        return this.animationEpisode == null;
    }

    @Override
    public boolean allowDigging()
    {
        return this.animationEpisode == null;
    }

    @Override
    public boolean allowUsingItems()
    {
        return this.animationEpisode == null;
    }

    @Override
    public boolean denySetItemInUse(ItemStack itemStack, int duration)
    {
        return this.animationEpisode != null;
    }

    @Override
    public boolean denyChangeItem()
    {
        return this.animationEpisode != null;
    }


    @Override
    public boolean denyMount(IGGMEntity entity, boolean flag)
    {
        return this.animationEpisode != null;
    }

    @Override
    public boolean allowJump()
    {
        return this.animationEpisode != null;
    }




    @Override
    public void modifyModel(ModelBase model, float f0, float f1, float tickRate)
    {
        if (this.animationEpisode != null)
        {
            this.animationEpisode.updateModel(this.entity, model, ((this.episodeDuration - this.episodeCountdown) + tickRate) / this.episodeDuration);
        }
    }

    @Override
    public String toString()
    {
        return "AnimationHelperFightStance:" + this.getUnlocalizedName();
    }

}
