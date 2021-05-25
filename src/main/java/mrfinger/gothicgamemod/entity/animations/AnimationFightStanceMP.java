package mrfinger.gothicgamemod.entity.animations;

import mrfinger.gothicgamemod.battle.hittypes.IHitType;
import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.player.GGMPlayerEquipmentAnimationFightStance;
import mrfinger.gothicgamemod.entity.player.IGGMEntityPlayer;
import net.minecraft.entity.Entity;

import java.util.Collection;

public class AnimationFightStanceMP extends GGMPlayerEquipmentAnimationFightStance
{

    protected IGGMEntity[] targets;

    protected IHitType repeatAttackHitType;


    public AnimationFightStanceMP(IGGMEntityPlayer entity)
    {
        super(entity);
    }


    @Override
    public boolean setAnimationHit(IHitType hitType, short count)
    {
        if (super.setAnimationHit(hitType, count))
        {
            return true;
        }

        this.repeatAttackHitType = hitType;

        return false;
    }


    @Override
    protected void updateAttack()
    {
        if (this.targets != null && this.count <= this.attackTick)
        {
            for (IGGMEntity target : this.targets)
            {
                this.entity.attackEntityAsMob((Entity) target);
            }

            this.targets = null;
        }

        if (this.count <= 0 && this.repeatAttackHitType != null)
        {
            this.setAnimationHit(this.repeatAttackHitType, this.entity.getNewAttackDuration(this.repeatAttackHitType));
            this.repeatAttackHitType = null;
        }
    }


    @Override
    public IGGMEntity[] getTargets()
    {
        return targets;
    }

    @Override
    public void setTargets(IGGMEntity[] targets)
    {
        if (this.count >= 0) this.targets = targets;
    }

    @Override
    public void setTargets(Collection<IGGMEntity> entities)
    {
        this.setTargets((IGGMEntity[]) entities.toArray());
    }

}
