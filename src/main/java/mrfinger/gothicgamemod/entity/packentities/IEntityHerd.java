package mrfinger.gothicgamemod.entity.packentities;

import mrfinger.gothicgamemod.client.model.ModelAnimal;
import mrfinger.gothicgamemod.client.model.ModelEntityHerd;
import mrfinger.gothicgamemod.entity.IGGMEntityLiving;
import mrfinger.gothicgamemod.entity.animations.episodes.IAnimationEpisode;
import mrfinger.gothicgamemod.fractions.PackFraction;

import java.util.Map;

public interface IEntityHerd extends IGGMEntityLiving
{

    PackFraction getFraction();

    PackFraction getStandartFraction();


    void onAddToPack(IPackEntity pack);

    void onRemoveFromPack(IPackEntity pack);


    boolean isPackLeader();

    boolean isAggressive();


    IPackEntity getPack();

    void setPackEntity(IPackEntity pack);

    IPackEntity findNewPack();


    float getNeedSpaceMultiplier();


    boolean isNeedWander();

    void setIsNeedWander(boolean b);

    float getBlockPathWeight(int x, int y, int z);

    boolean isBlockAvailableToLiving();


    boolean isCanJustWander();

    IAnimationEpisode getRandomJustLivingEpisode();

    Map<String, IAnimationEpisode> getAnimationEpisodesMap();

}
