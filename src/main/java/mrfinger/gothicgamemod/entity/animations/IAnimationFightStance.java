package mrfinger.gothicgamemod.entity.animations;

import mrfinger.gothicgamemod.entity.animations.episodes.IAnimationEpisode;
import mrfinger.gothicgamemod.entity.IGGMEntity;

import java.util.Collection;

public interface IAnimationFightStance extends IAnimation
{

    byte getAttackSeries();


    boolean isUsingItem();

    boolean isBlocking();

}
