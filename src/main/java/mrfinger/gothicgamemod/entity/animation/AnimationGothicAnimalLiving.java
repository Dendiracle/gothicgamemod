package mrfinger.gothicgamemod.entity.animation;

import mrfinger.gothicgamemod.entity.animation.episodes.IAnimationEpisode;
import mrfinger.gothicgamemod.entity.animation.instance.AbstractAnimationEntityAI;
import mrfinger.gothicgamemod.entity.animation.instance.IAnimation;
import mrfinger.gothicgamemod.entity.packentities.IEntityGothicAnimal;
import mrfinger.gothicgamemod.init.GGMEntityAnimations;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;

public class AnimationGothicAnimalLiving extends EntityAIBase
{

    protected IEntityGothicAnimal entity;
    protected boolean isEntityHavePath;
    protected boolean isEntityHaunts;
    protected byte episodesSeries;


    public AnimationGothicAnimalLiving(IEntityGothicAnimal entity)
    {
        this.entity = entity;
    }


    @Override
    public boolean shouldExecute()
    {
        return this.entity.getEntityToAttack() == null;
    }


    @Override
    public void updateTask()
    {
        if (this.entity.getEntityToAttack() != null)
        {
            double distanceSQToAnnoyer = this.entity.getDistanceSqToEntity((Entity) this.entity.getEntityToAttack());
            float warnDistanceSQ = this.entity.getAnnoyersWarnDistance();
            warnDistanceSQ *= warnDistanceSQ;

            if (distanceSQToAnnoyer < warnDistanceSQ)
            {
                this.entity.cleartPath();
                if (this.entity.isEntityLookingFor((Entity) this.entity.getEntityToAttack(), 30F, 30F))
                {
                    this.entity.setAnimationEpisode(GGMEntityAnimations.AnimationAggrEntityGothicAnimal);
                }
                else
                {
                    this.entity.getLookHelper().setLookPositionWithEntity((Entity) this.entity.getEntityToAttack(), 30F, 30F);
                }
            }
            else if (!this.isEntityHaunts)
            {
                this.entity.getNavigator().tryMoveToEntityLiving((Entity) this.entity.getEntityToAttack(), 1D);
            }

        }
        else if (this.entity.getNavigator().noPath())
        {
            if (this.isEntityHavePath)
            {
                this.setAnimationEpisode(GGMEntityAnimations.AnimationLookingAroundEntityGothicAnimal);
                this.isEntityHavePath = false;
            }
            else if (this.animationEpisode == null && !this.entity.isNeedWander())
            {
                if (this.entity.isBlockAvailableToLiving() && (this.entity.getRNG().nextInt(25) + 1) > this.episodesSeries)
                {
                    IAnimationEpisode episode = this.entity.getRandomJustLivingEpisode();

                    if (episode != null)
                    {
                        this.setAnimationEpisode(episode);
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
