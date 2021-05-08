package mrfinger.gothicgamemod.entity.packentities;

import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.IGGMEntityLiving;
import mrfinger.gothicgamemod.fractions.PackFraction;

public interface IEntityHerd extends IGGMEntityLiving {


    PackFraction getFraction();

    PackFraction getStandartFraction();


    PackEntity findNewPack();


    float getNeedSpaceMultiplier();


    int getDefaultChaseDuration();


    IGGMEntity getEntityToAttack();


    void setEntityToAttack(IGGMEntity entity);

    void setEntityToAttack(IGGMEntity entity, int chaseDuration);


    void updatePathFindingToEntityToAttack();

}
