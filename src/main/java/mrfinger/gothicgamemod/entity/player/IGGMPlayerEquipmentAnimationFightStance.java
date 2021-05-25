package mrfinger.gothicgamemod.entity.player;

import mrfinger.gothicgamemod.entity.animations.IAnimationHumanoidFightStance;
import mrfinger.gothicgamemod.entity.inventory.IGGMHumanoidInventory;

public interface IGGMPlayerEquipmentAnimationFightStance extends IGGMHumanoidInventory, IAnimationHumanoidFightStance
{


    void changeCurrentItem(boolean toMore);

}
