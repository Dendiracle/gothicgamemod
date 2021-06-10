package mrfinger.gothicgamemod.entity.packentities;

import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.IGGMEntityLiving;
import mrfinger.gothicgamemod.entity.animations.episodes.IAnimationEpisode;
import mrfinger.gothicgamemod.fractions.PackFraction;
import net.minecraft.client.model.ModelBase;
import net.minecraft.pathfinding.PathEntity;

import java.util.Map;

public interface IEntityHerd extends IGGMEntityLiving {


    PackFraction getFraction();

    PackFraction getStandartFraction();


    void onAddToPack(PackEntity pack);

    void onRemoveFromPack(PackEntity pack);


    PackEntity findNewPack();


    float getNeedSpaceMultiplier();


    int getDefaultChaseDuration();


    IGGMEntity getEntityToAttack();


    void setEntityToAttack(IGGMEntity entity);

    void setEntityToAttack(IGGMEntity entity, int chaseDuration);


    void nullifyEntityToAttack();


    void setPath(PathEntity path);

    void setPath(int x, int y, int z);

    void updatePathFindingToEntityToAttack();


    float getBlockPathWeight(int x, int y, int z);


    boolean isCanJustWander();

    IAnimationEpisode getRandomJustLivingEpisode();

    Map<String, IAnimationEpisode> getLivingEpisodesMap();

}
