package mrfinger.gothicgamemod.entity.animations;

import mrfinger.gothicgamemod.entity.animations.episodes.AbstractPlayerAnimationHit;
import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.player.GGMPlayerEquipmentAnimationHelperFightStance;
import mrfinger.gothicgamemod.entity.player.IGGMEntityPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Collection;

public class AnimationHelperPlayerFightStanceMP extends GGMPlayerEquipmentAnimationHelperFightStance<IGGMEntityPlayerMP, AbstractPlayerAnimationHit>
{

    protected IGGMEntity[] targets;

    protected AbstractPlayerAnimationHit repeatAttackHitType;


    public AnimationHelperPlayerFightStanceMP(IGGMEntityPlayerMP entity)
    {
        super(entity);
    }


    @Override
    public boolean setAnimationEpisode(AbstractPlayerAnimationHit animationEpisode, int count)
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
        if (this.targets != null && this.episodeCountdown <= this.episodeCulminationTick)
        {
            for (IGGMEntity target : this.targets)
            {
                if (target != null && target.isEntityAlive()) ((EntityPlayer) this.entity).attackTargetEntityWithCurrentItem((Entity) target);
            }

            this.targets = null;
        }

        if (this.episodeCountdown <= 0 && this.repeatAttackHitType != null)
        {
            this.setAnimationEpisode(this.repeatAttackHitType, this.entity.getNewAttackDuration(this.repeatAttackHitType));
            this.repeatAttackHitType = null;
        }
    }


    public IGGMEntity[] getTargets()
    {
        return targets;
    }

    public void setTargets(IGGMEntity[] targets)
    {
        if (this.episodeCountdown >= 0) this.targets = targets;
    }

    public void setTargets(Collection<IGGMEntity> entities)
    {
        this.setTargets((IGGMEntity[]) entities.toArray());
    }

}
