package mrfinger.gothicgamemod.entity.animations;

import mrfinger.gothicgamemod.entity.animations.episodes.IAnimationEpisode;
import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.capability.data.IGGMEntityWithAttackAnim;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.EntityLivingBase;

public abstract class AnimationFightStance implements IAnimationFightStance
{

    protected IGGMEntityWithAttackAnim entity;

    protected IAnimationEpisode hitType;
    protected short attackDuration;

    protected short count;
    protected short attackTick;

    protected byte attackSeries;

    protected boolean isNeedControlMove;
    protected float moveForward;
    protected float moveStrafe;


    public AnimationFightStance(IGGMEntityWithAttackAnim entity)
    {
        this.entity = entity;
        this.attackDuration = 1;
        this.count = -20;
        this.attackTick = -20;
    }


    @Override
    public IGGMEntityWithAttackAnim getEntity() {
        return this.entity;
    }

    @Override
    public void setEntity(IGGMEntityLivingBase entity) {
        this.entity = (IGGMEntityWithAttackAnim) entity;
    }


    @Override
    public String getUnlocalizedName()
    {
        return "fight";
    }


    @Override
    public byte getAttackSeries()
    {
        return attackSeries;
    }

    @Override
    public boolean denyMovement()
    {
        return false;
    }

    @Override
    public boolean denyChangeItem()
    {
        return this.count >= 0 || this.isUsingItem();
    }

    @Override
    public boolean denyJump()
    {
        return this.count >= 0;
    }

    @Override
    public boolean denyMount(IGGMEntity entity, boolean flag)
    {
        return this.count >= 0 || this.isUsingItem();
    }

    @Override
    public void updateAnimation()
    {
        if (this.count > -20)
        {
            this.updateAttack();
            --this.count;
        }
        else
        {
            this.attackSeries = 0;
        }
    }


    @Override
    public IAnimationEpisode getEpisode() {
        return this.hitType;
    }

    @Override
    public int getEpisodeDuration()
    {
        return this.attackDuration;
    }

    @Override
    public int getEpisodeCount()
    {
        return this.count;
    }


    @Override
    public boolean setAnimationEpisode(IAnimationEpisode animationEpisode, int count)
    {
        if (this.allowHit())
        {
            this.hitType = animationEpisode;
            this.attackDuration = (short) count;
            this.count = (short) count;
            this.attackTick = (short) (count * animationEpisode.getCulminationTickMultiplier());
            ++this.attackSeries;
            this.entity.flagForAnimSync();
            return true;
        }

        return false;
    }

    protected boolean allowHit()
    {
        return this.count <= 0 && !this.isUsingItem();
    }

    @Override
    public void clearAnimationEpisode()
    {
        this.count = -20;
        this.attackTick = -20;
        this.attackSeries = 0;
    }


    protected abstract void updateAttack();


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
    public boolean tryEndAnimation()
    {
        return this.count < 0;
    }


    @Override
    public void onEndAnimation()
    {
        this.clearAnimationEpisode();
    }


    @Override
    public void modifyModel(ModelBase model, float f0, float f1, float tickRate)
    {
        if (this.count > -20)
        {
            short count = this.count > 0 ? this.count : 0;
            this.hitType.updateModel(this.entity, model, ((this.attackDuration - count) + tickRate) / this.attackDuration);
        }
    }

    @Override
    public String toString()
    {
        return "AnimationFightStance:" + this.getUnlocalizedName();
    }

}
