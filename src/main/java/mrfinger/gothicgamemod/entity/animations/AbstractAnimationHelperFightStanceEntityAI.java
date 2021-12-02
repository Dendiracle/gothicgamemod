package mrfinger.gothicgamemod.entity.animations;

import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.animations.episodes.IAnimationHit;
import mrfinger.gothicgamemod.entity.capability.data.IGGMEntityWithAnimAttack;
import net.minecraft.client.model.ModelBase;
import net.minecraft.item.ItemStack;

public abstract class AbstractAnimationHelperFightStanceEntityAI<Entity extends IGGMEntityWithAnimAttack, Episode extends IAnimationHit> extends AbstractAnimationHelperEntityAI<Entity, Episode> implements IAnimationHelperFightStance<Entity, Episode>
{

    public AbstractAnimationHelperFightStanceEntityAI()
    {
        this(null);
    }

    public AbstractAnimationHelperFightStanceEntityAI(Entity entity)
    {
        this.entity = entity;
    }


    @Override
    public String getUnlocalizedName()
    {
        return "fight";
    }


    @Override
    public void updateAnimation()
    {
        if (this.episodeCountdown > -this.episodeDuration)
        {
            this.updateAttack();
            --this.episodeCountdown;

            if (this.episodeCountdown == -this.episodeDuration)
            {
                this.clearAnimationEpisode();
            }
        }
    }

    protected void updateAttack()
    {
        if (this.episodeCountdown == this.episodeCulminationTick)
        {
            this.animationEpisode.onCulmination(this.entity, this);

            net.minecraft.entity.Entity[] targets = this.animationEpisode.getAttackTargets(this.entity, this);

            if (targets != null)
            {
                for (net.minecraft.entity.Entity target : targets)
                {
                    if (target != null && target.isEntityAlive()) this.entity.attackEntityAsMob(target);
                }
            }
        }
    }


    @Override
    public boolean isCanAnimationHelperWillChanged()
    {
        return super.isCanAnimationHelperWillChanged() && !this.isUsingItem();
    }


    @Override
    public boolean setAnimationEpisode(Episode animationEpisode)
    {
        return this.setAnimationEpisode(animationEpisode, animationEpisode != null ? this.entity.getNewAttackDuration(animationEpisode) : 0);
    }

    @Override
    protected boolean isAllowChangeAnimationEpisode()
    {
        return super.isAllowChangeAnimationEpisode() && !this.isUsingItem();
    }


    @Override
    public boolean allowDropItems()
    {
        return super.allowDropItems() && !this.isUsingItem();
    }

    @Override
    public boolean allowDigging()
    {
        return super.allowDigging() && !this.isUsingItem();
    }

    @Override
    public boolean allowUsingItems()
    {
        return super.allowUsingItems() && !this.isUsingItem();
    }

    @Override
    public boolean denySetItemInUse(ItemStack itemStack, int duration)
    {
        return super.denySetItemInUse(itemStack, duration) || this.isUsingItem();
    }

    @Override
    public boolean denyChangeItem()
    {
        return super.denyChangeItem() || this.isUsingItem();
    }


    @Override
    public boolean denyMount(IGGMEntity entity, boolean flag)
    {
        return this.episodeCountdown >= 0 || this.isUsingItem();
    }


    @Override
    public void modifyModel(ModelBase model, float f0, float f1, float tickRate)
    {
        if (this.episodeCountdown > -this.episodeDuration)
        {
            this.animationEpisode.updateModel(this.entity, model, ((this.episodeDuration - episodeCountdown) + tickRate) / this.episodeDuration);
        }
    }

    @Override
    public String toString()
    {
        return "AnimationHelperFightStance:" + this.getUnlocalizedName();
    }
}
