package mrfinger.gothicgamemod.entity.packentities;

import mrfinger.gothicgamemod.entity.IGGMEntityLiving;
import mrfinger.gothicgamemod.entity.animation.episodes.IAnimationEpisode;
import mrfinger.gothicgamemod.fractions.PackFraction;

public interface IEntityHerd extends IGGMEntityLiving
{

    PackFraction getCurrentFraction();

    PackFraction getStandartFraction();


    void onAddToPack(IPackEntity pack);

    void onRemoveFromPack(IPackEntity pack);


    boolean isPackLeader();

    boolean isOnFight();


    IPackEntity getPack();

    void setPackEntity(IPackEntity pack);

    IPackEntity findNewPack();


    float getNeedSpaceInHabitat();


    boolean isNeedWander();

    void setIsNeedWander(boolean b);

    default double getWanderSpeedModifier()
    {
        return 0.5D;
    }

    float getBlockPathWeight(int x, int y, int z);

    boolean isBlockAvailableToLiving();


    boolean isCanJustLive();

    IAnimationEpisode getRandomJustLivingEpisode();

}
