package mrfinger.gothicgamemod.entity.player;

import mrfinger.gothicgamemod.entity.animations.IAnimationHelperHumanoidFightStance;
import mrfinger.gothicgamemod.entity.animations.episodes.AbstractPlayerAnimationHit;
import mrfinger.gothicgamemod.entity.animations.episodes.IAnimationHit;
import mrfinger.gothicgamemod.entity.capability.data.IGGMEntityHumanoid;
import mrfinger.gothicgamemod.entity.inventory.IGGMHumanoidInventory;

public interface IGGMPlayerEquipmentAnimationHelperFightStance<Entity extends IGGMEntityPlayer, Episode extends AbstractPlayerAnimationHit> extends IGGMHumanoidInventory, IAnimationHelperHumanoidFightStance<Entity, Episode>
{

    void changeCurrentItem(boolean toMore);

}
