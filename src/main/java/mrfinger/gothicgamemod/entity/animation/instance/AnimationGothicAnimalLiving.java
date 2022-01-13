package mrfinger.gothicgamemod.entity.animation.instance;

import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.animation.instance.AbstractAnimationHelperEntityAI;
import mrfinger.gothicgamemod.entity.animation.instance.IAnimationManager;
import mrfinger.gothicgamemod.entity.packentities.IEntityGothicAnimal;
import mrfinger.gothicgamemod.init.GGMEntityAnimations;
import net.minecraft.entity.Entity;

public class AnimationGothicAnimalLiving extends AbstractAnimationHelperEntityAI
{

    protected IEntityGothicAnimal entity;
    protected boolean isEntityHavePath;
    protected boolean isEntityHaunts;
    protected byte episodesSeries;


    public AnimationGothicAnimalLiving()
    {
    }


    @Override
    public IAnimationManager getAnimationManager()
    {
        return GGMEntityAnimations.ManagerGothicAnimalLiving;
    }


    @Override
    public boolean shouldExecute()
    {
        return this.entity.getAttackTarget() == null;
    }


    @Override
    public void updateTask()
    {
        if (this.entity.getAttackTarget() != null)
        {
            double distanceSQToAnnoyer = this.entity.getDistanceSqToEntity((IGGMEntity) this.entity.getAttackTarget());
            float warnDistanceSQ = this.entity.getAnnoyersWarnDistance();
            warnDistanceSQ *= warnDistanceSQ;

            if (distanceSQToAnnoyer < warnDistanceSQ)
            {
                this.entity.cleartPath();
                if (this.entity.isEntityLookingFor(this.entity.getAttackTarget(), 30F, 30F))
                {
                    this.entity.tryChangeAnimation(GGMEntityAnimations.AnimationManagerAggrEntityGothicAnimal);
                }
                else
                {
                    this.entity.getLookHelper().setLookPositionWithEntity((Entity) this.entity.getAttackTarget(), 30F, 30F);
                }
            }
            else if (!this.isEntityHaunts)
            {
                this.entity.getNavigator().tryMoveToEntityLiving((Entity) this.entity.getAttackTarget(), 1D);
            }

            this.episodesSeries = 0;
        }
        else if (this.entity.getNavigator().noPath())
        {
            if (this.isEntityHavePath)
            {
                this.tryChangeAnimation(GGMEntityAnimations.AnimationManagerLookingAroundEntityGothicAnimal);
                this.isEntityHavePath = false;
            }
            else if (this.subAnimation == null && !this.entity.isNeedWander())
            {
                if (this.entity.isBlockAvailableToLiving() && (this.entity.getRNG().nextInt(25) + 1) > this.episodesSeries)
                {
                    IAnimationManager animationManager = this.entity.getRandomJustLivingEpisode();

                    if (animationManager != null)
                    {
                        this.tryChangeAnimation(animationManager);
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
