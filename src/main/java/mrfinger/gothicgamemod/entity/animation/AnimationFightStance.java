package mrfinger.gothicgamemod.entity.animation;

import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.animation.episodes.IAnimationHit;
import mrfinger.gothicgamemod.entity.animation.instance.AbstractAnimationWithCulmination;
import mrfinger.gothicgamemod.entity.animation.instance.AbtractAnimationHelper;
import mrfinger.gothicgamemod.entity.capability.data.IGGMEntityWithAnimAttack;
import net.minecraft.client.model.ModelBase;
import net.minecraft.item.ItemStack;

public abstract class AnimationFightStance<Entity extends IGGMEntityWithAnimAttack, Episode extends IAnimationHit> extends AbtractAnimationHelper<Entity, Episode> implements IAnimationFightStance<Entity, Episode>
{

    public AnimationFightStance(Entity entity)
    {
        super(entity);
    }



    @Override
    public String getUnlocalizedName()
    {
        return "fight";
    }


    @Override
    public void updateAnimation()
    {
        if (this.countdown > -this.duration)
        {
            this.updateAttack();
            --this.countdown;

            if (this.countdown == -this.duration)
            {
                this.clearAnimationEpisode();
            }
        }
    }

    protected abstract void updateAttack();


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
    public boolean allowSetItemInUse(ItemStack itemStack, int duration)
    {
        return super.allowSetItemInUse(itemStack, duration) || !this.isUsingItem();
    }

    @Override
    public boolean allowChangeItem()
    {
        return super.allowChangeItem() || !this.isUsingItem();
    }


    @Override
    public boolean allowMount(IGGMEntity entity, boolean flag)
    {
        return this.countdown >= 0 || !this.isUsingItem();
    }


    @Override
    public void modifyModel(ModelBase model, float f0, float f1, float tickRate)
    {
        if (this.countdown > -this.duration)
        {
            short count = this.countdown > 0 ? this.countdown : 0;
            this.animationEpisode.updateModel(this.entity, model, ((this.duration - count) + tickRate) / this.duration);
        }
    }

    @Override
    public String toString()
    {
        return "AnimationFightStance:" + this.getUnlocalizedName();
    }

}
