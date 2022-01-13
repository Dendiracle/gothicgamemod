package mrfinger.gothicgamemod.entity.animation;

import mrfinger.gothicgamemod.entity.animation.episodes.AbstractPlayerAnimationHit;
import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.player.GGMPlayerEquipmentAnimationFightStance;
import mrfinger.gothicgamemod.entity.player.IGGMEntityPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Collection;

public class AnimationPlayerFightStanceMP extends GGMPlayerEquipmentAnimationFightStance<IGGMEntityPlayerMP, AbstractPlayerAnimationHit>
{

    protected IGGMEntity[] targets;

    protected AbstractPlayerAnimationHit repeatAttackHitType;


    public AnimationPlayerFightStanceMP(IGGMEntityPlayerMP entity)
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
        if (this.targets != null && this.countdown <= this.culminationTick)
        {
            for (IGGMEntity target : this.targets)
            {
                if (target != null && target.entityAlive()) ((EntityPlayer) this.entity).attackTargetEntityWithCurrentItem((Entity) target);
            }

            this.targets = null;
        }

        if (this.countdown <= 0 && this.repeatAttackHitType != null)
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
        if (this.countdown >= 0) this.targets = targets;
    }

    public void setTargets(Collection<IGGMEntity> entities)
    {
        this.setTargets((IGGMEntity[]) entities.toArray());
    }

}
