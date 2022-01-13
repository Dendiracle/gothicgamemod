package mrfinger.gothicgamemod.entity.player;

import mrfinger.gothicgamemod.entity.animation.IAnimationHumanoidFightStance;
import mrfinger.gothicgamemod.entity.animation.instance.IAnimation;
import mrfinger.gothicgamemod.entity.inventory.IGGMHumanoidInventory;

public interface IGGMPlayerEquipmentAnimationFightStance<Entity extends IGGMEntityPlayer, Animation extends IAnimation<Entity>> extends IGGMHumanoidInventory, IAnimationHumanoidFightStance<Entity, Animation>
{

    void changeCurrentItem(boolean toMore);

}
