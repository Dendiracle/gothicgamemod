package mrfinger.gothicgamemod.entity.animations;

import mrfinger.gothicgamemod.entity.animations.episodes.IAnimationEpisode;
import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.player.GGMPlayerEquipmentAnimationFightStance;
import mrfinger.gothicgamemod.entity.player.IGGMEntityPlayer;
import net.minecraft.entity.Entity;

import java.util.Collection;

public class AnimationFightStanceMP extends GGMPlayerEquipmentAnimationFightStance
{

    protected IGGMEntity[] targets;

    protected IAnimationEpisode repeatAttackHitType;


    public AnimationFightStanceMP(IGGMEntityPlayer entity)
    {
        super(entity);
    }


    @Override
    public boolean setAnimationEpisode(IAnimationEpisode animationEpisode, int count)
    {
        if (super.setAnimationEpisode(animationEpisode, count))
        {
            return true;
        }

        this.repeatAttackHitType = animationEpisode;

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
            this.setAnimationEpisode(this.repeatAttackHitType, this.entity.getNewAttackDuration(this.repeatAttackHitType));
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
