package mrfinger.gothicgamemod.entity.animations;

import mrfinger.gothicgamemod.entity.animations.episodes.IAnimationEpisode;
import mrfinger.gothicgamemod.entity.IGGMEntity;

import java.util.Collection;

public interface IAnimationFightStance extends IAnimation
{

    short getAttackTick();

    byte getAttackSeries();


    IGGMEntity[] getTargets();

    void setTargets(IGGMEntity[] entities);

    void setTargets(Collection<IGGMEntity> entities);


    boolean isUsingItem();

    boolean isBlocking();

}
