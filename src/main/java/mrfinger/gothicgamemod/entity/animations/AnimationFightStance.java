package mrfinger.gothicgamemod.entity.animations;

import mrfinger.gothicgamemod.battle.hittypes.IHitType;
import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.capability.data.IGGMEntityWithAttackAnim;

public abstract class AnimationFightStance implements IAnimationFightStance
{

    protected IGGMEntityWithAttackAnim entity;

    protected IHitType hitType;
    protected short attackDuration;

    protected short count;
    protected short attackTick;

    protected byte attackSeries;


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
    public void onUpdate()
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
    public IHitType getLastHitType() {
        return this.hitType;
    }

    @Override
    public short getAttackDuration()
    {
        return this.attackDuration;
    }

    @Override
    public short getAtackCount()
    {
        return this.count;
    }

    @Override
    public short getAttackTick()
    {
        return this.attackTick;
    }

    @Override
    public byte getAttackSeries()
    {
        return this.attackSeries;
    }


    @Override
    public boolean setAnimationHit(IHitType hitType, short count)
    {
        if (this.allowHit())
        {
            this.hitType = hitType;
            this.attackDuration = count;
            this.count = count;
            this.attackTick = (short) (count * hitType.getAttackTickHitMultiplier());
            return true;
        }

        return false;
    }

    protected boolean allowHit()
    {
        return this.count <= 0 && !this.isUsingItem();
    }

    @Override
    public void clearAnimationHit()
    {
        this.count = -20;
        this.attackTick = -20;
        this.attackSeries = 0;
    }


    protected abstract void updateAttack();


    @Override
    public boolean tryEndAnimation()
    {
        return this.count < 0;
    }


    @Override
    public void onEndAnimation()
    {
        this.clearAnimationHit();
    }

}
