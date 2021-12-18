package mrfinger.gothicgamemod.entity.player;

import mrfinger.gothicgamemod.entity.animation.IAnimationHumanoidFightStance;
import mrfinger.gothicgamemod.entity.animation.episodes.AbstractPlayerAnimationHit;
import mrfinger.gothicgamemod.entity.inventory.IGGMHumanoidInventory;

public interface IGGMPlayerEquipmentAnimationFightStance<Entity extends IGGMEntityPlayer, Episode extends AbstractPlayerAnimationHit> extends IGGMHumanoidInventory, IAnimationHumanoidFightStance<Entity, Episode>
{

    void changeCurrentItem(boolean toMore);

}
